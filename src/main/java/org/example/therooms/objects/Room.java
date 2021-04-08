package org.example.therooms.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "room")
public class Room implements Serializable {
    private String roomName;
    private String country;
    private boolean lampSwitched;

    public Room() {

    }

    public Room(String roomName, String country) {
        this.roomName = roomName;
        this.country = country;
        this.lampSwitched = false;
    }

    public String getRoomName() {
        return roomName;
    }

    @XmlElement(name = "room_name")
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCountry() {
        return country;
    }

    @XmlElement(name = "country")
    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isLampSwitched() {
        return lampSwitched;
    }

    @XmlElement(name = "lampSwitched")
    public void setLampSwitched(boolean lampSwitched) {
        this.lampSwitched = lampSwitched;
    }


}
