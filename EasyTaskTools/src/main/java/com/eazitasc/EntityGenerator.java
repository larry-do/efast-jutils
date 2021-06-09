package com.eazitasc;

import com.eazitasc.binding.ForeignKey;
import com.eazitasc.binding.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static final String ENTITY_XML_FOLDER_PATH = "EasyTask/src/main/resources/data/entities";
    private static final String ENTITY_JAVA_FOLDER_PATH = "EasyTask/src/main/java/com/eazitasc/entity";
    private static final String REPOSITORY_JAVA_FOLDER_PATH = "EasyTask/src/main/java/com/eazitasc/repository";
    private static final String ENTITY_JAVA_PACKAGE = "com.eazitasc.entity";
    private static final String REPOSITORY_JAVA_PACKAGE = "com.eazitasc.repository";

    private static final String ENUM_XML_FILE_PATH = "EasyTask/src/main/resources/data/enum-types.xml";
    private static final String ENUM_JAVA_FOLDER_PATH = "EasyTask/src/main/java/com/eazitasc/enumtype";
    private static final String ENUM_JAVA_PACKAGE = "com.eazitasc.enumtype";

    private static final String DATATYPE_FILE_PATH = "EasyTaskTools/src/main/resources/datatypes.properties";
    private static final String TEMPLATE_DIR = "EasyTaskTools/src/main/resources";
    private static final String ENTITY_TEMPLATE = "entity-template.ftl";
    private static final String REPOSITORY_INTERFACE_TEMPLATE = "repo-interface-template.ftl";
    private static final String REPOSITORY_IMPLEMENT_TEMPLATE = "repo-implement-template.ftl";
    private static final String ENUM_TEMPLATE = "enum-template.ftl";

    private static final String EASYTASK_POM_PATH = "EasyTask/pom.xml";

    private static final Properties DATATYPES = getDatatypes(DATATYPE_FILE_PATH);

    public static void main(String[] args) {
        final HashSet<String> changedXmlFiles = getChangedEntityXmlFiles();
        final HashSet<Table> generatedTables = parseToTables(changedXmlFiles);
        generatedTables.forEach(EntityGenerator::generateEntity);
        // now, this will create metamodel files
        // packageAndBuild();

        // generate repositories of entities
        System.out.println("-------------- GENERATING REPOSITORIES OF ENTITIES -------------------");
        generatedTables.stream().filter(table -> StringUtils.isNotEmpty(table.getRepository())).forEach(table -> {
            generateRepositoryInterface(table);
            generateRepositoryImplement(table);
        });

        // Generate Enum Types
        generateEnumTypes();
    }

    private static void generateRepositoryInterface(final Table table) {
        final String fileName = StringUtils.capitalize(table.getRepository().toLowerCase()) + "Repository";
        final String fileNameWithExt = fileName + ".java";
        System.out.println(fileNameWithExt);
        try {
            final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_DIR));
            final Template template = cfg.getTemplate(REPOSITORY_INTERFACE_TEMPLATE);
            Map<String, Object> params = new HashMap<>();
            params.put("package", REPOSITORY_JAVA_PACKAGE);
            params.put("fileName", fileName);
            params.put("table", table);

            TreeSet<String> imports = new TreeSet<>();
            imports.add("import " + ENTITY_JAVA_PACKAGE + "." + StringUtils.capitalize(table.getName()) + ";");
            params.put("imports", imports);

            FileWriter writer = new FileWriter(new File(REPOSITORY_JAVA_FOLDER_PATH + "/" + fileNameWithExt));
            template.process(params, writer);
            writer.flush();
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static void generateRepositoryImplement(final Table table) {
        final String repoName = StringUtils.capitalize(table.getRepository().toLowerCase()) + "Repository";
        final String fileName = repoName + "Impl";
        final String fileNameWithExt = fileName + ".java";
        System.out.println(fileNameWithExt);
        try {
            final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_DIR));
            final Template template = cfg.getTemplate(REPOSITORY_IMPLEMENT_TEMPLATE);
            Map<String, Object> params = new HashMap<>();
            params.put("package", REPOSITORY_JAVA_PACKAGE);
            params.put("fileName", fileName);
            params.put("repoName", repoName);
            params.put("table", table);

            TreeSet<String> imports = new TreeSet<>();
            imports.add("import " + ENTITY_JAVA_PACKAGE + "." + StringUtils.capitalize(table.getName()) + ";");
            imports.add("import org.springframework.stereotype.Repository;");
            imports.add("import org.springframework.transaction.annotation.Transactional;");
            if (table.getUniqueConstraints().size() > 0) {
                imports.add("import " + ENTITY_JAVA_PACKAGE + "." + StringUtils.capitalize(table.getName()) + "_;");
                imports.add("import javax.persistence.criteria.CriteriaBuilder;");
                imports.add("import javax.persistence.criteria.CriteriaQuery;");
                imports.add("import javax.persistence.criteria.Root;");
            }

            params.put("imports", imports);

            FileWriter writer = new FileWriter(new File(REPOSITORY_JAVA_FOLDER_PATH + "/" + fileNameWithExt));
            template.process(params, writer);
            writer.flush();
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static HashSet<String> getChangedEntityXmlFiles() {
        try {
            System.out.println("------------ READING REPO TO GET CHANGED ENTITY XML FILES -----------------");
            final Git repo = Git.open(new File(PROJECT_ROOT_PATH));
            final Status status = repo.status().call();
            return Stream.concat(status.getUntracked().stream(), status.getModified().stream())
                    .filter(path -> path.startsWith(ENTITY_XML_FOLDER_PATH)).collect(Collectors.toCollection(HashSet::new));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    private static HashSet<Table> parseToTables(HashSet<String> changedXmlFiles) {
        HashSet<Table> tables = new HashSet<>();
        HashMap<String, Set<ForeignKey>> foreignKeys = new HashMap<>();
        changedXmlFiles.forEach(filePath -> {
            final Table table = bindXmlToObject(filePath);
            if (table != null) {
                tables.add(table);
                if (table.getForeignKeys() != null && !table.getForeignKeys().isEmpty()) {
                    table.getForeignKeys().forEach(key -> {
                        foreignKeys.computeIfAbsent(key.getTarget(), k -> new HashSet<>());
                        foreignKeys.get(key.getTarget()).add(key);
                    });
                }
            }
        });
        foreignKeys.forEach((targetName, keys) -> {
            final String filePath = ENTITY_XML_FOLDER_PATH + "/" + targetName + ".xml";
            Table table = bindXmlToObject(filePath);
            if (table != null) {
                table.setInverseMappings(new LinkedHashSet<>());
                keys.forEach(key -> {
                    if (key.getTargetMapping() != null) {
                        table.getInverseMappings().add(key.getTargetMapping());
                    }
                });
                tables.removeIf(t -> t.getName().equals(table.getName()));
                tables.add(table);
            }
        });
        return tables;
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

        if (table.getParent() != null) {
            table.setParent(StringUtils.capitalize(table.getParent().toLowerCase()));
        }

        if (table.getColumns() != null) {
            table.getColumns().forEach(column -> {
                if (column.getName() != null) {
                    column.setName(column.getName().toLowerCase());
                }
                if (column.getType().lastIndexOf("enum") != -1) {
                    column.setIsEnum(Boolean.TRUE);
                    String enumName = "";
                    for (String s : column.getType().split("-")) {
                        enumName = enumName + StringUtils.capitalize(s);
                    }
                    column.setJvType(enumName);
                } else if (DATATYPES.getProperty(column.getType()) != null) {
                    column.setJvType(DATATYPES.getProperty(column.getType()));
                    column.setDbType(DATATYPES.getProperty("db." + column.getType()));
                } else {
                    // todo show error that column xxx of table xxx has invalid datatype
                }
                if (StringUtils.isNotEmpty(column.getDefaultValue()) && StringUtils.isNotEmpty(column.getJvType())) {
                    if (column.getJvType().equals("Date") && column.getDefaultValue().equalsIgnoreCase("current-time")) {
                        column.setDefaultValue("new Date()"); // todo: temparary value
                    }
                }

                if (column.getType().equalsIgnoreCase("version")) {
                    column.getOtherAnnotations().add("@Version");
                } else if (column.getType().equalsIgnoreCase("datetime")) {
                    column.getOtherAnnotations().add("@Temporal(TemporalType.TIMESTAMP)");
                } else if (column.getType().equalsIgnoreCase("date")) {
                    column.getOtherAnnotations().add("@Temporal(TemporalType.DATE)");
                }
            });
        }

       if (table.getPrimaryKey() != null) {
           table.getPrimaryKey().setColumns(table.getPrimaryKey().getColumns().toLowerCase());

           table.getPrimaryKey().getColumnsOfKey().forEach(keyCol -> table.getColumns().forEach(column -> {
               if (column.getName().equalsIgnoreCase(keyCol)) {
                   column.setIsPrimaryKey(true);
               }
           }));
       }

        if (table.getUniqueConstraints() != null) {
            table.getUniqueConstraints().forEach(constraint -> constraint.setColumns(constraint.getColumns().toLowerCase()));
        }

        if (table.getForeignKeys() != null) {
            table.getForeignKeys().forEach(key -> {
                if (StringUtils.isNotEmpty(key.getFetch())) {
                    key.setFetch(key.getFetch().toUpperCase());
                }

                if (StringUtils.isNotEmpty(key.getTarget())) {
                    key.setTarget(StringUtils.capitalize(key.getTarget().toLowerCase()));
                    if(StringUtils.isEmpty(key.getFieldName())) {
                        key.setFieldName(key.getTarget().toLowerCase());
                    }
                }

                if (key.getColumns() != null) {
                    key.getColumns().forEach(column -> {
                        column.setName(column.getName().toLowerCase());
                        column.setTarget(column.getTarget().toLowerCase());
                    });
                }

                if (key.getTargetMapping() != null) {
                    if (StringUtils.isEmpty(key.getTargetMapping().getFieldName())) {
                        key.getTargetMapping().setFieldName("listOf" + StringUtils.capitalize(table.getName()));
                    }
                    key.getTargetMapping().setFromTable(StringUtils.capitalize(table.getName()));
                    key.getTargetMapping().setMappedBy(key.getFieldName());
                    if (StringUtils.isNotEmpty(key.getTargetMapping().getOrderBy())) {
                        key.getTargetMapping().setOrderBy(key.getTargetMapping().getOrderBy().toLowerCase());
                    }
                    if (StringUtils.isNotEmpty(key.getTargetMapping().getFetch())) {
                        key.getTargetMapping().setFetch(key.getTargetMapping().getFetch().toUpperCase());
                    }
                }
            });
        }

        return table;
    }

    private static void generateEntity(final Table table) {
        try {
            final String fileNameWithExt = StringUtils.capitalize(table.getName().toLowerCase().concat(".java"));
            System.out.println(fileNameWithExt);
            final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_DIR));
            final Template template = cfg.getTemplate(ENTITY_TEMPLATE);
            Map<String, Object> params = new HashMap<>();
            params.put("table", table);
            params.put("package", ENTITY_JAVA_PACKAGE);

            TreeSet<String> imports = new TreeSet<>();
            if (table.getIsAbstract() != null && table.getIsAbstract()) {
                imports.add("import javax.persistence.MappedSuperclass;");
            } else {
                imports.add("import javax.persistence.Entity;");
                imports.add("import javax.persistence.Table;");
            }
            if (!table.getColumns().isEmpty()) {
                imports.add("import javax.persistence.Column;");
            }
            if (table.getColumns().stream().anyMatch(column -> column.getJvType().equals("Date"))) {
                imports.add("import java.util.Date;");
                imports.add("import javax.persistence.Temporal;");
                imports.add("import javax.persistence.TemporalType;");
            }
            if (table.getColumns().stream().anyMatch(column -> column.getIsEnum() != null && column.getIsEnum())) {
                imports.add("import javax.persistence.EnumType;");
                imports.add("import javax.persistence.Enumerated;");
                table.getColumns().stream().filter(column -> column.getIsEnum() != null && column.getIsEnum()).forEach(column -> {
                    imports.add("import " + ENUM_JAVA_PACKAGE + "." + column.getJvType() + ";");
                });
            }
            if (table.getColumns().stream().anyMatch(column -> column.getType().equals("version"))) {
                imports.add("import javax.persistence.Version;");
            }
            if (table.getUniqueConstraints() != null && !table.getUniqueConstraints().isEmpty()) {
                imports.add("import javax.persistence.UniqueConstraint;");
            }
            if (table.getPrimaryKey() != null) {
                imports.add("import javax.persistence.Id;");
                imports.add("import javax.persistence.IdClass;");
                imports.add("import java.io.Serializable;");
                imports.add("import java.util.Objects;");
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

    private static void generateEnumTypes() {
        try {
            System.out.println("-------------- GENERATING ENUM TYPES -------------");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(ENUM_XML_FILE_PATH));
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("enum");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    parseToEnumJavaType(element);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseToEnumJavaType(Element element) {
        String name = element.getAttribute("name");
        String[] values = element.getAttribute("values").replace(" ", "").toUpperCase().split(",");
        List<Pair<String, String>> pairs = new ArrayList<>();
        for (String value : values) {
            pairs.add(Pair.of(value.substring(0, value.indexOf('(')), value.substring(value.indexOf('(') + 1, value.lastIndexOf(')'))));
        }

        String fileName = "";
        for (String s : name.split("-")) {
            fileName = fileName + StringUtils.capitalize(s);
        }
        final String fileNameWithExt = StringUtils.capitalize(fileName.concat(".java"));
        System.out.print(fileName);
        System.out.println(Arrays.toString(values));
        try {
            final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_DIR));
            final Template template = cfg.getTemplate(ENUM_TEMPLATE);
            Map<String, Object> params = new HashMap<>();
            params.put("package", ENUM_JAVA_PACKAGE);
            params.put("fileName", fileName);
            params.put("values", pairs);
            FileWriter writer = new FileWriter(new File(ENUM_JAVA_FOLDER_PATH + "/" + fileNameWithExt));
            template.process(params, writer);
            writer.flush();
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
