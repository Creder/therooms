package belarus.sergeybukatyi.therooms.objects;

import java.io.Serializable;


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

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public boolean isLampSwitched() {
    return lampSwitched;
  }

  public void setLampSwitched(boolean lampSwitched) {
    this.lampSwitched = lampSwitched;
  }


}
