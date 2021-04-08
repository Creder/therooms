package org.example.therooms.filters;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.apache.logging.log4j.LogManager;
import org.example.therooms.dao.RoomsDAO;
import org.example.therooms.objects.Room;
import org.example.therooms.utils.Utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;

public class CountryFilter implements Filter {
    private DatabaseReader dbReader;
    RoomsDAO roomsDAO;
    private URL url;

    @Override
    public void init(FilterConfig filterConfig)throws ServletException{
        roomsDAO = new RoomsDAO();
        try {
            url = new URL(Utils.getIpCheckerURL());

        } catch (IOException e) {
            LogManager.getLogger().fatal(e.getMessage());
            throw new ServletException();
        }

        ClassLoader classLoader = Utils.class.getClassLoader();
        try {
            File database = new File(classLoader.getResource("GeoLite2-Country.mmdb").toURI());
            dbReader = new DatabaseReader.Builder(database).build();
        }catch (IOException | URISyntaxException e){
            LogManager.getLogger().fatal(e.getMessage());
            throw new ServletException();
        }


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        try {
            Room room = roomsDAO.getOneRoom(request.getPathInfo().substring(1));
            CountryResponse countryResponse = readIP();
            if (room.getCountry().compareToIgnoreCase(countryResponse.getCountry().getIsoCode()) == 0) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                throw new SecurityException();
            }
        } catch (ServletException | IOException | GeoIp2Exception e) {
            LogManager.getLogger().fatal(e.getMessage());
        }


    }

    private CountryResponse readIP() throws IOException, GeoIp2Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String visitorIp = br.readLine();
        return dbReader.country(InetAddress.getByName(visitorIp));
    }

    public void destroy() {
    }
}
