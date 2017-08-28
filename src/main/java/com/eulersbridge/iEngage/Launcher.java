package com.eulersbridge.iEngage;

import com.eulersbridge.iEngage.core.domain.Login;
import com.eulersbridge.iEngage.security.ApplicationEventListener;
import com.eulersbridge.iEngage.security.UserPermissionEvaluator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.access.PermissionEvaluator;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class Launcher extends SpringBootServletInitializer {
  private static Logger LOG = LoggerFactory.getLogger(Launcher.class);

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Launcher.class, args);
//    app.setBannerMode(Banner.Mode.LOG);
    app.run(args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Launcher.class);
  }
}
