package com.eulersbridge.iEngage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
@PropertySource("classpath:application.properties")
public class Launcher extends SpringBootServletInitializer {
  private static Logger LOG = LoggerFactory.getLogger(Launcher.class);

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Launcher.class);
//    app.setBannerMode(Banner.Mode.LOG);
    app.run(args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Launcher.class);
  }
}
