package org.example.therooms.servlets;

import org.example.therooms.dao.RoomsDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/switch")
public class SwitchLampServlet extends HttpServlet {
    private RoomsDAO roomsDAO;

    @Override
    public void init() {
        roomsDAO = new RoomsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String roomName = req.getParameter("roomName");
        roomsDAO.switchLamp(roomName);
    }
}
