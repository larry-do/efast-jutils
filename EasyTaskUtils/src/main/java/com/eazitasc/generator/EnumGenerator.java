package com.eazitasc.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EnumGenerator {
    private final File ENUM_XML_FILE_PATH;
    private final File ENUM_JAVA_FOLDER_PATH;
    private final String ENUM_JAVA_PACKAGE;

    private final String ENUM_TEMPLATE = "enum-template.ftl";

    /**
     * @param enumXmlFilePath Should be set correct Working Directory by main class
     * @param enumJavaFolderPath Should be set correct Working Directory by main class
     * */
    public EnumGenerator(final String enumXmlFilePath, final String enumJavaFolderPath, final String enumPackage) {
        ENUM_XML_FILE_PATH = new File(enumXmlFilePath).getAbsoluteFile();
        ENUM_JAVA_FOLDER_PATH = new File(enumJavaFolderPath).getAbsoluteFile();
        ENUM_JAVA_PACKAGE = enumPackage;
    }

    /**
     *  What user manually added will not be removed
     *
     * @param forceRefresh true -> Clear and re-generate all classes in target folder
     * */
    public void generate(final boolean forceRefresh) {
        try {
            System.out.println("-------------- GENERATING ENUM TYPES -------------");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(ENUM_XML_FILE_PATH);
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

    private void parseToEnumJavaType(Element element) {
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
            cfg.setDirectoryForTemplateLoading(new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("templates")).toString().replace("file:/", "")));
            final Template template = cfg.getTemplate(ENUM_TEMPLATE);
            Map<String, Object> params = new HashMap<>();
            params.put("package", ENUM_JAVA_PACKAGE);
            params.put("fileName", fileName);
            params.put("values", pairs);
            FileWriter writer = new FileWriter(ENUM_JAVA_FOLDER_PATH.getAbsolutePath() + File.separator + fileNameWithExt);
            template.process(params, writer);
            writer.flush();
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
