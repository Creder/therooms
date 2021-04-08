package org.example.therooms.servlets;

import org.example.therooms.dao.RoomsDAO;
import org.example.therooms.objects.Room;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajax")
public class AjaxHandlerServlet extends HttpServlet {
    private RoomsDAO roomsDAO;

    @Override
    public void init() {
        roomsDAO = new RoomsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            Room room = roomsDAO.getOneRoom(req.getParameter("roomName"));
            JSONObject roomLampJson = new JSONObject();
            roomLampJson.put("lamp", room.isLampSwitched());
            resp.setContentType("application/json");
            resp.getWriter().write(roomLampJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
