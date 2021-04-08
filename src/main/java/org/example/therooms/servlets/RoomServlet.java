package org.example.therooms.servlets;

import org.apache.logging.log4j.LogManager;
import org.example.therooms.dao.RoomsDAO;
import org.example.therooms.objects.Room;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getroom")
public class RoomServlet extends HttpServlet {
    RoomsDAO roomsDAO;

    @Override
    public void init() {
        roomsDAO = new RoomsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Room room = roomsDAO.getOneRoom(req.getParameter("roomName"));
            if (room !=null){
                JSONObject roomJson = new JSONObject();
                roomJson.put("name", room.getRoomName());
                roomJson.put("lamp", room.isLampSwitched());
                resp.setContentType("application/json");
                resp.getWriter().write(roomJson.toString());
            }
        } catch (IOException e) {
            LogManager.getLogger().fatal(e.getMessage());
        }
    }


}
