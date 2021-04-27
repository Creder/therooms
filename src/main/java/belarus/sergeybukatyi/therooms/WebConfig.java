package belarus.sergeybukatyi.therooms;

import belarus.sergeybukatyi.therooms.filters.CountryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

  private final CountryFilter countryFilter;

  @Autowired
  public WebConfig(CountryFilter countryFilter) {
    this.countryFilter = countryFilter;
  }

  @Bean
  public FilterRegistrationBean<CountryFilter> filterRegistrationBean() {
    FilterRegistrationBean<CountryFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(countryFilter);
    registrationBean.addUrlPatterns("/room/*");
    return registrationBean;
  }
}

