package org.example.therooms.dao;

import org.apache.logging.log4j.LogManager;
import org.example.therooms.objects.Room;
import org.example.therooms.objects.Rooms;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.example.therooms.utils.Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomsDAO {
     public List<Room> getRooms(){
        List<Room> roomList = new ArrayList<>();
         File file;
         try {
             file = new File(Utils.getRoomFileProp());
             Document document = Utils.parseXML(file);
             if (document != null) {
                 document.getDocumentElement().normalize();
                 NodeList nodeList = document.getElementsByTagName("room");

                 for (int i = 0; i < nodeList.getLength(); i++) {
                     Node node = nodeList.item(i);
                     Room room = new Room();
                     if (node.getNodeType() == Node.ELEMENT_NODE) {
                         Element element = (Element) node;
                         room.setRoomName(element.getElementsByTagName("room_name").item(0).getTextContent());
                         room.setLampSwitched(Boolean.parseBoolean(element.getElementsByTagName("lampSwitched").item(0).getTextContent()));
                         room.setCountry(element.getElementsByTagName("country").item(0).getTextContent());
                         roomList.add(room);
                     }
                 }
             }

         }catch (IOException e) {
             LogManager.getLogger().fatal(e.getMessage());
             return null;
         }

        return roomList;
    }

    public Room getOneRoom(String roomName){
        List<Room> rooms = getRooms();
        if(rooms !=null){
            roomName = roomName.replace("_", " ");
            for (Room room : rooms) {
                if (room.getRoomName().compareToIgnoreCase(roomName) == 0) {
                    return room;
                }
            }
        }
        return null;
    }

    public void addRoom(Room room){
         File file = null;
        try {
            file = new File(Utils.getRoomFileProp());
            Document document = Utils.parseXML(file);
            if ( document !=null){
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
                Utils.writeXml(document, file);
            }
        } catch (IOException e) {
            LogManager.getLogger().fatal(e.getMessage());
            try {
                JAXBContext context = JAXBContext.newInstance(Rooms.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Rooms rooms = new Rooms();
                rooms.add(room);
                marshaller.marshal(rooms, file);

            } catch (JAXBException jaxbException) {
                LogManager.getLogger().fatal(e.getMessage());
            }
        }
    }

    public void switchLamp(String roomName){
        roomName = roomName.replace("_", " ");
        try {
            File file = new File(Utils.getRoomFileProp());
            Document document = Utils.parseXML(file);
            if(document !=null) {
                document.getDocumentElement().normalize();
                NodeList rooms = document.getElementsByTagName("room");
                Element room;
                for (int i = 0; i < rooms.getLength(); i++) {
                    room = (Element) rooms.item(i);
                    if (room.getElementsByTagName("room_name").item(0).getTextContent().compareToIgnoreCase(roomName) == 0) {
                        Node lampSwitched = room.getElementsByTagName("lampSwitched").item(0);
                        lampSwitched.setTextContent(String.valueOf(!Boolean.parseBoolean(lampSwitched.getTextContent())));

                        Utils.writeXml(document, file);
                    }
                }
            }
        } catch (IOException e) {
            LogManager.getLogger().fatal(e.getMessage());
        }

    }

}
