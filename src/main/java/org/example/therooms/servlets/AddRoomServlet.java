package org.example.therooms.servlets;

import org.apache.logging.log4j.LogManager;
import org.example.therooms.dao.RoomsDAO;
import org.example.therooms.objects.Room;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addroom")
public class AddRoomServlet extends HttpServlet {
    RoomsDAO roomsDAO;

    @Override
    public void init() {
        roomsDAO = new RoomsDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String roomName = req.getParameter("roomName");
            String country = req.getParameter("country");
            Room room = new Room(roomName, country);
            roomsDAO.addRoom(room);
            resp.sendRedirect("index");
        } catch (IOException e) {
            LogManager.getLogger().fatal(e);
        }
    }
}
