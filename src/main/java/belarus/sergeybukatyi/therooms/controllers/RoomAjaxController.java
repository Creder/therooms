package belarus.sergeybukatyi.therooms.controllers;

import belarus.sergeybukatyi.therooms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoomAjaxController {

  private final RoomService roomService;

  @Autowired
  public RoomAjaxController(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping(value = "/room/{roomName}/getLamp", produces = {MediaType.APPLICATION_JSON_VALUE})
  public Boolean getRoomLampAjax(@PathVariable("roomName") String roomName) {
    return roomService.getRoom(roomName).isLampSwitched();
  }

  @GetMapping("/room/{roomName}/switch")
  public int switchTheLamp(@PathVariable("roomName") String roomName) {
    return roomService.switchLamp(roomName);
  }
}
