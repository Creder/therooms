package DAO;

import objects.Room;
import objects.Rooms;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RoomsDAO {
    public List<Room> getRooms() throws IOException {
        List<Room> roomList = new ArrayList<>();
        File file = new File(Utils.getRoomFileProp());

        try {
            Document document = Utils.parseXML(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("room");

            for(int i = 0; i < nodeList.getLength(); i++ ){
                Node node = nodeList.item(i);
                Room room = new Room();
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    room.setRoomName(element.getElementsByTagName("room_name").item(0).getTextContent());
                    room.setLampSwitched(Boolean.parseBoolean(element.getElementsByTagName("lampSwitched").item(0).getTextContent()));
                    room.setCountry(element.getElementsByTagName("country").item(0).getTextContent());
                    roomList.add(room);
                }
            }
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    public Room getOneRoom(String roomName) throws IOException {
        List<Room> rooms = getRooms();
        roomName = roomName.replace("_", " ");
            for(Room room : rooms){
                if(room.getRoomName().compareToIgnoreCase(roomName) == 0){
                    return room;
                }
        }
        return null;
    }

    public void addRoom(Room room) throws JAXBException {
        File file = new File(Utils.getRoomFileProp());

        try {
            Document document = Utils.parseXML(file);
            Element root = document.getDocumentElement();

            Element newRoom = document.createElement("room");
            Element roomName = document.createElement("room_name");
            roomName.appendChild(document.createTextNode(room.getRoomName()));
            newRoom.appendChild(roomName);

            Element coutry = document.createElement("country");
            coutry.appendChild(document.createTextNode(room.getCountry()));
            newRoom.appendChild(coutry);

            Element lampSwitched = document.createElement("lampSwitched");
            lampSwitched.appendChild(document.createTextNode(String.valueOf(room.isLampSwitched())));
            newRoom.appendChild(lampSwitched);

            root.appendChild(newRoom);
            writeXml(document, file);

            
        }catch (SAXException | IOException e){
            JAXBContext context = JAXBContext.newInstance(Rooms.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Rooms rooms = new Rooms();
            rooms.add(room);
            marshaller.marshal(rooms, file);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void writeXml(Document document, File file){
        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }



    public void switchLamp(String roomName) throws IOException, SAXException, ParserConfigurationException {
        File file = new File(Utils.getRoomFileProp());
        roomName = roomName.replace("_", " ");
        Document document = Utils.parseXML(file);

        document.getDocumentElement().normalize();

        NodeList rooms = document.getElementsByTagName("room");
        Element room;
        for (int i = 0; i < rooms.getLength(); i++){
            room = (Element) rooms.item(i);
            if(room.getElementsByTagName("room_name").item(0).getTextContent().compareToIgnoreCase(roomName) == 0){
                Node lampSwitched = room.getElementsByTagName("lampSwitched").item(0);
                lampSwitched.setTextContent(String.valueOf(!Boolean.parseBoolean(lampSwitched.getTextContent())));

                writeXml(document, file);
            }
        }
    }

}
