package com.eulersbridge.iEngage.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertyResolver;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import javax.annotation.Resource;


@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
@EnableNeo4jRepositories(basePackages = {"com.eulersbridge.iEngage.database.repository"})
@EnableAutoConfiguration
public class ApplicationTest{
  @Qualifier("propertyResolver")
  @Resource
  private PropertyResolver propResolver;

  private static Logger LOG = LoggerFactory.getLogger(ApplicationTest.class);


}
