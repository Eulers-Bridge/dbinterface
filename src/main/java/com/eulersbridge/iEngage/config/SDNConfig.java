package com.eulersbridge.iEngage.config;

import com.eulersbridge.iEngage.core.beans.PreSaveEventListener;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.session.event.EventListener;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.neo4j.conversion.MetaDataDrivenConversionService;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.List;

/**
 * @author Yikai Gong
 */

@Configuration
@EnableNeo4jRepositories(basePackages = {"com.eulersbridge.iEngage.database.repository"})
public class SDNConfig extends Neo4jDataAutoConfiguration {

  final PreSaveEventListener preSaveEventListener;

  @Autowired
  public SDNConfig(PreSaveEventListener preSaveEventListener) {
    this.preSaveEventListener = preSaveEventListener;
  }


  // Additional mapping service
  @Bean
  public ConversionService entityConversionService(SessionFactory sessionFactory) {
    ConversionService service =
      new MetaDataDrivenConversionService(sessionFactory.metaData());
    ConverterRegistry reg = (ConverterRegistry) service;
//    reg.addConverter(new EventToOwnerConverter());
//    reg.addConverter(new NewsArticleToOwnerConverter());
//    reg.addConverter(new PhotoAlbumToOwnerConverter());
//    reg.addConverter(new UserToOwnerConverter());
//    reg.addConverter(new CandidateToOwnerConverter());
//    reg.addConverter(new TicketToOwnerConverter());
    return service;
  }

  // Setup auto-index strategy
  @Override
  public org.neo4j.ogm.config.Configuration configuration(Neo4jProperties properties) {
    org.neo4j.ogm.config.Configuration configuration =  super.configuration(properties);
    // Can using "assert" during dev validate  none/assert/validate/dump
    // Ref: https://neo4j.com/docs/ogm-manual/current/reference/#reference:indexing:creation
    configuration.autoIndexConfiguration().setAutoIndex("none");
    return configuration;
  }

  // Added Event listener to Neo4j Session.
  @Override
  public SessionFactory sessionFactory(
    org.neo4j.ogm.config.Configuration configuration,
    ApplicationContext applicationContext,
    ObjectProvider<List<EventListener>> eventListeners) {

    SessionFactory sessionFactory =
      super.sessionFactory(configuration, applicationContext, eventListeners);

    sessionFactory.register(preSaveEventListener);
    return sessionFactory;
  }

}
