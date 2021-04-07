package Filters;

import DAO.RoomsDAO;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import objects.Room;
import utils.Utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class CoutryFilter implements Filter {
    RoomsDAO roomsDAO;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request= (HttpServletRequest)servletRequest;
        Properties properties = Utils.getGeoProps();
        ClassLoader classLoader = Utils.class.getClassLoader();

        File database = null;
        try {
            database = new File(classLoader.getResource("GeoLite2-Country.mmdb").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        URL url = new URL(properties.getProperty("checkIpURL"));
        roomsDAO = new RoomsDAO();
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
        BufferedReader br =  new BufferedReader(new InputStreamReader(url.openStream()));

        String visitorIp = br.readLine();

        CountryResponse countryResponse = null;
        try {
            countryResponse = dbReader.country(InetAddress.getByName(visitorIp));
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        Room room = roomsDAO.getOneRoom(request.getPathInfo().substring(1));
        if(room.getCountry().compareToIgnoreCase(countryResponse.getCountry().getName()) == 0){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else{
            throw new SecurityException();
        }

    }

    public void destroy() {}
}
