package belarus.sergeybukatyi.therooms.beans;

import belarus.sergeybukatyi.therooms.utils.JSONWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxmind.geoip2.DatabaseReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Beans {

  @Value("${checkerIpURL}")
  String checkerIPURL;

  @Bean
  public Map<String, String> getCountries() {
    Map<String, String> countries = new TreeMap<>();
    String[] countryCodes = Locale.getISOCountries();
    for (String countryCode : countryCodes) {
      Locale locale = new Locale("", countryCode);
      String code = locale.getCountry();
      String name = locale.getDisplayCountry();
      countries.put(code, name);
    }
    return countries;
  }

  @Bean
  public Logger getLogger() {
    return LoggerFactory.getLogger("Logger");
  }

  @Bean
  public DatabaseReader getDatabaseReader() {
    try {
      InputStream database = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("GeoLite2-Country.mmdb");
      return new DatabaseReader.Builder(database).build();
    } catch (IOException e) {
      return null;
    }
  }

  @Bean
  public URL getIpChecker() {
    try {
      return new URL(checkerIPURL);
    } catch (IOException e) {
      return null;
    }
  }

  @Bean
  public JSONWorker getUtils() {
    return new JSONWorker();
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

}
