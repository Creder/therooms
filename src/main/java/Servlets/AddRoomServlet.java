package Servlets;

import DAO.RoomsDAO;
import objects.Room;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import java.io.IOException;

@WebServlet("/addroom")
public class AddRoomServlet extends HttpServlet {
    RoomsDAO roomsDAO;
    @Override
    public void init() throws ServletException {
        roomsDAO = new RoomsDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String roomName = req.getParameter("roomName");
        String country = req.getParameter("country");
        Room room = new Room(roomName, country);
        try {
            roomsDAO.addRoom(room);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("index");

    }
}
