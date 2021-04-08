package org.example.therooms.servlets;

import org.example.therooms.dao.RoomsDAO;
import org.example.therooms.objects.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private RoomsDAO roomsDAO;
    private Map<String, String> countries;
    @Override
    public void init(){
        roomsDAO = new RoomsDAO();
        countries = new TreeMap<>();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes){
            Locale locale = new Locale("", countryCode);
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();
            countries.put(code, name);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Room> rooms = roomsDAO.getRooms();
        req.setAttribute("rooms", rooms);
        req.setAttribute("countries", countries);
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
