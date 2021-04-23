package belarus.sergeybukatyi.therooms.dao;

import belarus.sergeybukatyi.therooms.objects.Room;
import belarus.sergeybukatyi.therooms.utils.JSONWorker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoomsDAO {
   private final JSONWorker JSONWorker;
   private final ObjectMapper objectMapper;
   private final Logger logger;

    @Autowired
    public RoomsDAO(JSONWorker JSONWorker, ObjectMapper objectMapper, Logger logger)
    {
        this.JSONWorker = JSONWorker;
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    public List<Room> getRooms() {
        String result = JSONWorker.parseJSON();
        if(result != null && !result.equals(""))
            try {
                return objectMapper.readValue(result, new TypeReference<List<Room>>(){});
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        else{
            return new ArrayList<>();
        }
    }

    public Room getOneRoom(String roomName) {
        List<Room> rooms = getRooms();
        if (rooms != null) {
            roomName = roomName.replace("_", " ");
            for (Room room : rooms) {
                if (room.getRoomName().compareToIgnoreCase(roomName) == 0) {
                    return room;
                }
            }
        }
        return null;
    }

    public boolean addRoom(Room room) {
        List<Room> roomList= getRooms();
        if(roomList == null){
            roomList = new ArrayList<>();
        }
        if(getOneRoom(room.getRoomName()) == null){
            roomList.add(room);
            String arrayToJson = null;
            try {
                arrayToJson = objectMapper.writeValueAsString(roomList);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
            JSONWorker.writeJSON(arrayToJson);
            return true;
        }
        else return false;
    }

    public int switchLamp(String roomName) {
        List<Room> rooms = getRooms();
        if (rooms != null) {
            roomName = roomName.replace("_", " ");
            for (Room room : rooms) {
                if (room.getRoomName().compareToIgnoreCase(roomName) == 0) {
                    room.setLampSwitched(!room.isLampSwitched());
                    try {
                        String arrayToJson = objectMapper.writeValueAsString(rooms);
                        JSONWorker.writeJSON(arrayToJson);
                        return  0;
                    } catch (JsonProcessingException e) {
                        logger.error(e.getMessage());
                        return 1;
                    }

                }
            }
        }
        return -1;
    }
}
