package Servlets;

import DAO.RoomsDAO;
import objects.Room;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getroom")
public class RoomServlet extends HttpServlet {
    RoomsDAO roomsDAO;
    @Override
    public void init() throws ServletException {
        roomsDAO = new RoomsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoomsDAO roomsDAO = new RoomsDAO();
        Room room = roomsDAO.getOneRoom(req.getParameter("roomName"));

        JSONObject roomJson = new JSONObject();
        roomJson.put("name", room.getRoomName());
        roomJson.put("lamp", room.isLampSwitched());
        resp.setContentType("application/json");
        resp.getWriter().write(roomJson.toString());
    }


}
