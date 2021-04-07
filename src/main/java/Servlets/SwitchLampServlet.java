package Servlets;

import DAO.RoomsDAO;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@WebServlet("/switch")
public class SwitchLampServlet extends HttpServlet {
    private RoomsDAO roomsDAO;

    @Override
    public void init() throws ServletException {
        roomsDAO = new RoomsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String roomName = req.getParameter("roomName");
        try {
            roomsDAO.switchLamp(roomName);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
