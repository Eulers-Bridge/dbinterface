package com.eulersbridge.iEngage.config;

import com.eulersbridge.iEngage.database.domain.converters.*;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.neo4j.conversion.MetaDataDrivenConversionService;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * @author Yikai Gong
 */

@Configuration
@EnableNeo4jRepositories(basePackages = {"com.eulersbridge.iEngage.database.repository"})
public class SDNConfig extends Neo4jDataAutoConfiguration {

  // Additional mapping service
  @Bean
  public ConversionService entityConversionService(SessionFactory sessionFactory) {
    ConversionService service =
      new MetaDataDrivenConversionService(sessionFactory.metaData());
    ConverterRegistry reg = (ConverterRegistry) service;
    reg.addConverter(new EventToOwnerConverter());
    reg.addConverter(new NewsArticleToOwnerConverter());
    reg.addConverter(new PhotoAlbumToOwnerConverter());
    reg.addConverter(new UserToOwnerConverter());
    reg.addConverter(new CandidateToOwnerConverter());
    reg.addConverter(new TicketToOwnerConverter());
    return service;
  }

  // Setup auto-index strategy
  @Override
  public org.neo4j.ogm.config.Configuration configuration(Neo4jProperties properties) {
    org.neo4j.ogm.config.Configuration configuration =  super.configuration(properties);
    // Can using "assert" during dev
    configuration.autoIndexConfiguration().setAutoIndex("validate");
    return configuration;
  }

}
