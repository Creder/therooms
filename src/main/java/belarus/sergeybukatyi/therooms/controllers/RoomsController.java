package belarus.sergeybukatyi.therooms.controllers;

import belarus.sergeybukatyi.therooms.objects.Room;
import belarus.sergeybukatyi.therooms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
public class RoomsController {
    private final RoomService roomService;
    private final Map<String, String> countries;

    @Autowired
    RoomsController(RoomService roomService, Map<String, String> countries){
        this.roomService = roomService;
        this.countries = countries;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model, @ModelAttribute("Created") Object created)
    {
        List<Room> roomList  = roomService.getRooms();
        if(roomList.isEmpty()){
            model.addAttribute("Rooms", "empty");
        }
        else {
            model.addAttribute("Rooms", roomList);
        }
        model.addAttribute("Created", created);
        model.addAttribute("Countries", countries);
        return "rooms";
    }

    @GetMapping("/wrong_country")
    public String error405()
    {
        return "405";
    }

    @GetMapping("/404")
    public String error404()
    {
        return "404";
    }

    @PostMapping("/addRoom")
    public RedirectView addRoom(@RequestParam String roomName, @RequestParam String country, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("Created", roomService.addRoom(roomName, country));
        return new RedirectView("/index");
    }

    @GetMapping("/room/{roomName}")
    public String roomPage(@PathVariable("roomName") String roomName, Model model){
        model.addAttribute("room", roomService.getRoom(roomName));
        return "room";
    }
}
