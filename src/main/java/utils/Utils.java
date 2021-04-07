package utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class Utils {
    public static Document parseXML(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return  db.parse(file);
    }

    private static File getPropertyFile() throws URISyntaxException {
        ClassLoader classLoader = Utils.class.getClassLoader();
        URL resource = classLoader.getResource("config.properties");
        if (resource == null) {
            throw new IllegalArgumentException("file not found! ");
        } else {
                        return new File(resource.toURI());
        }
    }

    public static String getRoomFileProp(){
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(getPropertyFile());
            properties.load(inputStream);

            return   properties.getProperty("roomsFile");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Properties getGeoProps(){
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(getPropertyFile());
            properties.load(inputStream);
            return properties;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
