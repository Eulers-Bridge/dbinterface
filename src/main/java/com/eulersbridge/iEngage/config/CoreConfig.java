package com.eulersbridge.iEngage.config;

import com.eulersbridge.iEngage.core.services.AspectServiceHandler;
import com.eulersbridge.iEngage.rest.domain.CountriesFactory;
import com.eulersbridge.iEngage.rest.domain.stubCountryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.Executor;


@Configuration
public class CoreConfig {
  private static Logger LOG = LoggerFactory.getLogger(CoreConfig.class);

  @Bean
  public CountriesFactory createCountryFactory() {
    if (LOG.isDebugEnabled()) LOG.debug("createCountryFactory()");
    return new stubCountryFactory();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean(name = "threadPoolTaskExecutor")
  public Executor threadPoolTaskExecutor() {
    // default size 1
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // TODO: read pool size from property file
    executor.setCorePoolSize(2);
    return executor;
  }

  //TODO: Looks not needed.
//  @Bean
//  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//    return new PropertySourcesPlaceholderConfigurer();
//  }

  //TODO: PermissionEvaluator has not been implemented. Could use it as an AOP service?
//  @Bean
//  public PermissionEvaluator permissionEvaluator() {
//    UserPermissionEvaluator bean = new UserPermissionEvaluator();
//    return bean;
//  }

  //TODO: Remove it. Looks like this is gpt mock service for testing?
//  @Primary
//  @Bean
//  public ObjectMapper objectMapper() {
//    ObjectMapper om = new ObjectMapper();
//    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//    return om;
//  }
}
