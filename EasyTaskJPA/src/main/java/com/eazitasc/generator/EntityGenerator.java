package com.eazitasc.generator;

import com.eazitasc.sax.Table;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityGenerator {
    private static final String PROJECT_ROOT_PATH = System.getProperty("user.dir");
    private static final String DATATYPE_FILE_PATH = "EasyTaskJPA/src/main/resources/datatypes.properties";
    private static final String ENTITY_XML_FOLDER_PATH = "EasyTask/src/main/resources/entities";

    public static void main(String[] args) {
        final List<String> changedXmlFiles = getChangedEntityXmlFiles();
        System.out.println("Generating entity for xml files: ");
        changedXmlFiles.forEach(EntityGenerator::readXmlFile);
    }

    private static void readXmlFile(final String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String saveDir = filePath.substring(filePath.lastIndexOf(ENTITY_XML_FOLDER_PATH) + ENTITY_XML_FOLDER_PATH.length(), filePath.lastIndexOf("/"));
        System.out.println(fileName);
        Table table = convertXmlToObject(filePath);
        if (table != null) {
            System.out.println(table.toString());
        }
    }

    private static Table convertXmlToObject(final String filePath) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Table.class);
            File file = new File(PROJECT_ROOT_PATH + "/" + filePath);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Table) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
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

    private static Properties getDatatypes() {
        final Properties properties = new Properties();
        System.out.println("Reading datatypes from: " + DATATYPE_FILE_PATH);
        try (final InputStream in = new FileInputStream(DATATYPE_FILE_PATH)) {
            properties.load(in);
            properties.stringPropertyNames().forEach(s -> System.out.println(s + ": " + properties.get(s)));
        } catch (IOException e) {
            System.out.println("Can't read datatypes file: " + DATATYPE_FILE_PATH);
        }
        return properties;
    }
}
