package Servlets;

import DAO.RoomsDAO;
import objects.Room;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajax")
public class AjaxHandlerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RoomsDAO roomsDAO = new RoomsDAO();
        Room room = roomsDAO.getOneRoom(req.getParameter("roomName"));

        JSONObject roomLampJson = new JSONObject();
        roomLampJson.put("lamp", room.isLampSwitched());
        resp.setContentType("application/json");
        resp.getWriter().write(roomLampJson.toString());
    }
}
