package com.eazitasc;

import com.eazitasc.binding.Key;
import com.eazitasc.binding.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityGenerator {
    private static final String PROJECT_ROOT_PATH = System.getProperty("user.dir");
    private static final String ENTITY_XML_FOLDER_PATH = "EasyTask/src/main/resources/entities";
    private static final String DATATYPE_FILE_PATH = "EasyTaskTools/src/main/resources/datatypes.properties";
    private static final String ENTITY_TEMPLATE_DIR = "EasyTaskTools/src/main/resources";
    private static final String ENTITY_TEMPLATE = "entity-template.ftl";
    private static final String ENTITY_JAVA_FOLDER_PATH = "EasyTask/src/main/java/com/eazitasc/entity";

    private static final String EASYTASK_POM_PATH = "EasyTask/pom.xml";

    public static void main(String[] args) {
        final List<String> changedXmlFiles = getChangedEntityXmlFiles();
        System.out.println("Generating entity for xml files: ");
        HashMap<String, Set<Key>> foreignKeys = new HashMap<>();
        changedXmlFiles.forEach(filePath -> {
            Set<Key> foreignKeysOfEntity = generateEntityObjectFromXmlFile(filePath);
            if (foreignKeysOfEntity != null && !foreignKeysOfEntity.isEmpty()) {
                foreignKeysOfEntity.forEach(key -> {
                    foreignKeys.computeIfAbsent(key.getTargetTable(), k -> new HashSet<>());
                    foreignKeys.get(key.getTargetTable()).add(key);
                });
            }
        });
        foreignKeys.forEach((targetName, keys) -> {
            final String filePath = ENTITY_XML_FOLDER_PATH + "/" + targetName + ".xml";
            Table table = bindXmlToObject(filePath);
            if (table != null) {
                table.setInverseMappings(new LinkedHashSet<>());
                keys.forEach(key -> {
                    table.getInverseMappings().add(key.getMapping());
                });
                generateEntity(table, targetName);
            }
        });

        // now, this will create metamodel files
        // packageAndBuild();
    }

    private static List<String> getChangedEntityXmlFiles() {
        try {
            System.out.println("Reading repo to get changed entity xml files...");
            final Git repo = Git.open(new File(PROJECT_ROOT_PATH));
            final Status status = repo.status().call();
            return Stream.concat(status.getUntracked().stream(), status.getModified().stream())
                    .filter(path -> path.startsWith(ENTITY_XML_FOLDER_PATH)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static Set<Key> generateEntityObjectFromXmlFile(final String filePath) {
        final String fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
        System.out.println(fileName + ".xml");
        final Table table = bindXmlToObject(filePath);
        if (table != null) {
            generateEntity(table, fileName);
            return table.getForeignKeys() == null ? new HashSet<>() : table.getForeignKeys().stream().filter(key -> key.getMapping() != null).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    private static Table bindXmlToObject(final String filePath) {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Table.class);
            final File file = new File(PROJECT_ROOT_PATH + "/" + filePath);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Table table = (Table) jaxbUnmarshaller.unmarshal(file);
            return preHandleTable(table, filePath);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Table preHandleTable(Table table, final String filePath) {
        final String fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
        if (table.getName() == null) {
            table.setName(fileName);
        }
        table.setName(table.getName().toLowerCase());
        table.setClassName(StringUtils.capitalize(table.getName()));
        if (table.getParent() != null) {
            table.setParent(StringUtils.capitalize(table.getParent().toLowerCase()));
        }

        if (table.getColumn() != null) {
            final Properties datatypes = getDatatypes(DATATYPE_FILE_PATH);
            table.getColumn().forEach(column -> {
                if (column.getName() != null) {
                    column.setName(column.getName().toLowerCase());
                }

                if (column.getPropertyName() == null) {
                    column.setPropertyName(column.getName());
                }
                if (column.getType().lastIndexOf("enum") != -1) {
                    column.setJvType("String");
                } else if (datatypes.getProperty(column.getType()) != null) {
                    column.setJvType(datatypes.getProperty(column.getType()));
                    column.setDbType(datatypes.getProperty("db." + column.getType()));
                } else {
                    // todo show error that column xxx of table xxx has invalid datatype
                }
            });
        }

        if (table.getKey() != null) {
            table.getKey().forEach(key -> {
                if (StringUtils.isNotEmpty(key.getColumns())) {
                    key.setColumns(key.getColumns().toLowerCase());
                }
            });
            table.setPrimaryKeys(table.getKey().stream().filter(k -> StringUtils.isNotEmpty(k.getType()) && k.getType().equalsIgnoreCase("primary")).collect(Collectors.toCollection(LinkedHashSet::new)));
            table.getPrimaryKeys().forEach(key -> key.getColumnsOfKey().forEach(keyCol -> table.getColumn().forEach(column -> {
                if (column.getName().equalsIgnoreCase(keyCol)) {
                    column.setIsPrimaryKey(true);
                }
            })));
            table.setForeignKeys(table.getKey().stream().filter(k -> StringUtils.isNotEmpty(k.getType()) && k.getType().equalsIgnoreCase("foreign")).collect(Collectors.toCollection(LinkedHashSet::new)));
            table.setUniqueConstraints(table.getKey().stream().filter(k -> StringUtils.isEmpty(k.getType())).collect(Collectors.toCollection(LinkedHashSet::new)));
            table.getForeignKeys().forEach(key -> {
                if (key.getFetch() == null) key.setFetch("");
                key.setFetch(key.getFetch().toUpperCase());
                if (key.getMapping() != null) {
                    if (StringUtils.isEmpty(key.getMapping().getFieldName())) {
                        key.getMapping().setFieldName("listOf" + table.getClassName());
                    }
                    key.getMapping().setFromTable(table.getClassName());
                    key.getMapping().setMappedBy(key.getFieldName());
                    if (StringUtils.isNotEmpty(key.getMapping().getOrderBy())) {
                        key.getMapping().setOrderBy(key.getMapping().getOrderBy().toLowerCase());
                    }
                    if (StringUtils.isNotEmpty(key.getMapping().getFetch())) {
                        key.getMapping().setFetch(key.getMapping().getFetch().toUpperCase());
                    }
                }
            });
        }
        return table;
    }

    private static void generateEntity(final Table table, final String fileName) {
        try {
            final String fileNameWithExt = StringUtils.capitalize(fileName.toLowerCase().concat(".java"));
            final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setDirectoryForTemplateLoading(new File(ENTITY_TEMPLATE_DIR));
            final Template template = cfg.getTemplate(ENTITY_TEMPLATE);
            Map<String, Object> params = new HashMap<>();
            params.put("table", table);

            TreeSet<String> imports = new TreeSet<>();
            if (table.getIsAbstract() != null && table.getIsAbstract()) {
                imports.add("import javax.persistence.MappedSuperclass;");
            } else {
                imports.add("import javax.persistence.Entity;");
                imports.add("import javax.persistence.Table;");
            }
            if (!table.getColumn().isEmpty()) {
                imports.add("import javax.persistence.Column;");
            }
            if (table.getColumn().stream().anyMatch(column -> column.getJvType().equals("Date"))) {
                imports.add("import java.util.Date;");
            }
            if (table.getColumn().stream().anyMatch(column -> column.getType().equals("version"))) {
                imports.add("import javax.persistence.Version;");
            }
            if (table.getUniqueConstraints() != null && !table.getKey().isEmpty()) {
                imports.add("import javax.persistence.UniqueConstraint;");
            }
            if (table.getPrimaryKeys() != null && !table.getPrimaryKeys().isEmpty()) {
                imports.add("import javax.persistence.Id;");
                imports.add("import javax.persistence.IdClass;");
                imports.add("import java.io.Serializable;");
            }
            if (table.getForeignKeys() != null && !table.getForeignKeys().isEmpty()) {
                imports.add("import javax.persistence.ManyToOne;");
                imports.add("import javax.persistence.FetchType;");
                imports.add("import javax.persistence.JoinColumn;");
                imports.add("import javax.persistence.JoinColumns;");
            }
            if (table.getInverseMappings() != null && !table.getInverseMappings().isEmpty()) {
                imports.add("import java.util.List;");
                imports.add("import javax.persistence.OneToMany;");
                if (table.getInverseMappings().stream().anyMatch(mapping -> StringUtils.isNotEmpty(mapping.getFetch()))) {
                    imports.add("import javax.persistence.FetchType;");
                }
                if (table.getInverseMappings().stream().anyMatch(mapping -> StringUtils.isNotEmpty(mapping.getOrderBy()))) {
                    imports.add("import javax.persistence.OrderBy;");
                }
            }

            params.put("imports", imports);

            FileWriter writer = new FileWriter(new File(ENTITY_JAVA_FOLDER_PATH + "/" + fileNameWithExt));
            template.process(params, writer);
            writer.flush();
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static Properties getDatatypes(final String filePath) {
        final Properties properties = new Properties();
        try (final InputStream in = new FileInputStream(filePath)) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static void packageAndBuild() {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "mvn compiler:compile --file " + EASYTASK_POM_PATH);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
