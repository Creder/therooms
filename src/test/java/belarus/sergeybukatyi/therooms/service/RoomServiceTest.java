package belarus.sergeybukatyi.therooms.service;

import belarus.sergeybukatyi.therooms.objects.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest{

    @Autowired
    private RoomService roomService;

    @Test
    public void testGetRooms() {
        List<Room> roomList = roomService.getRooms();
        assertEquals(1, roomList.size());
    }

    @Test
    public void testAddRoom() {
        assertTrue(roomService.addRoom("Room from belarus 2", "BY"));
    }

    @Test
    public void testAddExistsRoom() {
        assertFalse(roomService.addRoom("Room from belarus 2", "BY"));
    }

    @Test
    public void testGetRoom() {
        assertNotNull(roomService.getRoom("Room_belarus"));
    }

    @Test
    public void testGetNotExistsRoom() {
        assertNull(roomService.getRoom("Room_bela"));
    }

    @Test
    public void testSwitchLamp() {
        assertEquals(0, roomService.switchLamp("Room_belarus"));
    }

    @Test
    public void testSwitchLampNotExistsRoom() {
        assertEquals(-1, roomService.switchLamp("Room_bel"));
    }
}