package org.example.therooms.objects;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "rooms")
public class Rooms implements Serializable {
    private List<Room> rooms;

    public List<Room> getRooms() {
        return rooms;
    }

    @XmlElement(name = "room")
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void add(Room room) {
        if (this.rooms == null) {
            this.rooms = new ArrayList<>();
        }
        this.rooms.add(room);
    }
}
