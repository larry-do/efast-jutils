package com.eazitasc;

import com.eazitasc.binding.Table;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DisplayTool;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityGenerator {
    private static final String PROJECT_ROOT_PATH = System.getProperty("user.dir");
    private static final String ENTITY_XML_FOLDER_PATH = "EasyTask/src/main/resources/entities";
    private static final String DATATYPE_FILE_PATH = "EasyTaskTools/src/main/resources/datatypes.properties";
    private static final String ENTITY_TEMPLATE_PATH = "EasyTaskTools/src/main/resources/Entity-template.vm";
    private static final String ENTITY_JAVA_FOLDER_PATH = "EasyTask/src/main/java/com/eazitasc/entity";

    private static final String EASYTASK_POM_PATH = "EasyTask/pom.xml";

    public static void main(String[] args) {
        final List<String> changedXmlFiles = getChangedEntityXmlFiles();
        System.out.println("Generating entity for xml files: ");
        changedXmlFiles.forEach(EntityGenerator::readXmlFile);
        // build maven compile/package EasyTaskJPA-1.0.jar and copy to EasyTask libs
        packageAndBuild();
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

    private static void readXmlFile(final String filePath) {
        final String fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
        System.out.println("----------------------------");
        System.out.println(fileName + ".xml");
        final Table table = bindXmlToObject(filePath);
        if (table != null) {
            generateEntity(table, fileName);
        }
    }

    private static Table bindXmlToObject(final String filePath) {
        final String fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Table.class);
            final File file = new File(PROJECT_ROOT_PATH + "/" + filePath);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Table table = (Table) jaxbUnmarshaller.unmarshal(file);

            // handle data in table
            if (table.getName() == null) {
                table.setName(fileName);
            }
            table.setName(table.getName().toLowerCase());
            table.setClassName(table.getName());
            table.setClassName(StringUtils.capitalize(table.getClassName()));
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
                    if (key.getMapping() != null) {
                        if (StringUtils.isNotEmpty(key.getMapping().getOrderBy())) {
                            key.getMapping().setOrderBy(key.getMapping().getOrderBy().toLowerCase());
                        }
                    }
                });
            }

            return table;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void generateEntity(final Table table, final String fileName)  {
        String fileNameWithExt = StringUtils.capitalize(fileName.toLowerCase().concat(".java"));
        System.out.println("Converting to object: " + fileNameWithExt);
        final VelocityEngine engine = new VelocityEngine();
        engine.init();
        VelocityContext context = new VelocityContext();
        context.put("display", new DisplayTool());
        context.put("table", table);

        LinkedHashSet<String> imports = new LinkedHashSet<>();
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
        if (table.getUniqueConstraints() != null && !table.getUniqueConstraints().isEmpty()) {
            imports.add("import javax.persistence.UniqueConstraint;");
        }

        context.put("imports", imports);

        try {
            FileWriter writer = new FileWriter(new File(ENTITY_JAVA_FOLDER_PATH + "/" + fileNameWithExt));
            Velocity.mergeTemplate(ENTITY_TEMPLATE_PATH, "UTF-8", context, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
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
                if (line == null) { break; }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
