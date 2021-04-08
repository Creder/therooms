package org.example.therooms.utils;

import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class Utils {

    public static Document parseXML(File file) throws IOException {
                try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    return db.parse(file);
                }catch (ParserConfigurationException e) {
                    LogManager.getLogger().fatal(e.getMessage());
                    return null;
                }catch (IOException | SAXException e){
                    throw new IOException(e);
                }
    }

    public static void writeXml(Document document, File file){
        DOMSource source = new DOMSource(document);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }catch (TransformerException e) {
             LogManager.getLogger().fatal(e.getMessage());
        }
    }

    private static File getPropertyFile() throws IOException{
        String configPropertiesFilename= "config.properties";
        ClassLoader classLoader = Utils.class.getClassLoader();
        URL resource = classLoader.getResource(configPropertiesFilename);
        if (resource == null) {
            throw new FileNotFoundException(configPropertiesFilename+" not found!");
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new IOException("Wrong URI for "+configPropertiesFilename);
            }

        }
    }

    public static String getRoomFileProp() throws IOException{
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(getPropertyFile());
        properties.load(inputStream);
        return properties.getProperty("roomsFile");


    }

    public static String getIpCheckerURL() throws IOException{
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(getPropertyFile());
        properties.load(inputStream);
        return properties.getProperty("checkIpURL");
    }
}
