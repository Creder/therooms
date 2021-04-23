package belarus.sergeybukatyi.therooms.service;

import belarus.sergeybukatyi.therooms.dao.RoomsDAO;
import belarus.sergeybukatyi.therooms.objects.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomsDAO roomsDAO;

    @Autowired
    public RoomService(RoomsDAO roomsDAO){
        this.roomsDAO = roomsDAO;
    }

    public List<Room> getRooms(){
        return roomsDAO.getRooms();
    }

    public boolean addRoom(String roomName, String country)
    {
        Room newRoom = new Room(roomName, country);
        return roomsDAO.addRoom(newRoom);
    }

    public Room getRoom(String roomName){
        return roomsDAO.getOneRoom(roomName);
    }

    public int switchLamp(String roomName){
        return roomsDAO.switchLamp(roomName);
    }

}
