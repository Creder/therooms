package belarus.sergeybukatyi.therooms.filters;

import belarus.sergeybukatyi.therooms.objects.Room;
import belarus.sergeybukatyi.therooms.service.RoomService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryFilter implements Filter {

  private final DatabaseReader dbReader;
  private final RoomService roomService;
  private final URL IpChecker;
  private final Logger logger;

  @Autowired
  public CountryFilter(DatabaseReader dbReader, RoomService roomService, Logger logger,
      URL IpChecker) {
    this.dbReader = dbReader;
    this.roomService = roomService;
    this.logger = logger;
    this.IpChecker = IpChecker;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String[] arr = request.getRequestURI().split("/");
    String roomName = arr[2];

    try {
      Room room = roomService.getRoom(roomName);
      CountryResponse countryResponse = readIP();
      if (room != null) {
        if (room.getCountry().compareToIgnoreCase(countryResponse.getCountry().getIsoCode()) != 0) {
          ((HttpServletResponse) servletResponse).sendRedirect("/wrong_country");
        }
      } else {
        ((HttpServletResponse) servletResponse).sendRedirect("/404");
      }
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (ServletException | IOException | GeoIp2Exception e) {
      logger.error(e.getMessage());
    }
  }

  private CountryResponse readIP() throws IOException, GeoIp2Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(IpChecker.openStream()));
    String visitorIp = br.readLine();
    return dbReader.country(InetAddress.getByName(visitorIp));
  }

}
