package com.eulersbridge.iEngage.security;

import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Yikai Gong
 * This class is used as a event listener bean (@Component) in Spring for
 * executing sometasks after API is setup.
 * <p>
 * By default, this listener should be turned off !
 */

@Component
public class ApplicationEventListener implements ApplicationListener<ContextRefreshedEvent> {
  private static Logger LOG = LoggerFactory.getLogger(ApplicationEventListener.class);
  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  CountryRepository countryRepository;
  @Autowired
  InstitutionRepository institutionRepository;
  @Autowired
  BadgeRepository badgeRepository;
  @Autowired
  TaskRepository taskRepository;
  
  @Value("${institution.name}")
  String institutionName;
  @Value("${institution.state}")
  String institutionState;
  @Value("${institution.campus}")
  String institutionCampus;
  @Value("${country.name}")
  String countryName;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    ApplicationContext ctx = event.getApplicationContext();
    // Use thread if task can be completed after service start.
    // Else, task will be done before service port being opened
    // (new Thread(() -> {   })).start();

    LOG.info("Task starts at ApplicationEventListener");
    checkOrCreateInitialNodes();

  }

  public void encodeUserPwd() {
    Iterable<User> userItr = userRepository.findAll(0);
    userItr.forEach(u -> {
      u.setPassword(passwordEncoder.encode(u.getPassword()));
      userRepository.save(u, 0);
    });
  }

  public void checkOrCreateInitialNodes() {
    Country country = new Country(countryName);
    persistNode(country, countryRepository, countryRepository.findByCountryName(country.getCountryName(), 0));
    Institution institution = new Institution(institutionName, institutionCampus, institutionState, country.toNode());
    persistNode(institution, institutionRepository, institutionRepository.findByName(institution.getName(), 0));

  }

  public <N extends Node, R extends GraphRepository<N>> void persistNode(N newNode, R repo, N entity){
    if (entity == null){
      LOG.info(newNode.getClass().getSimpleName() + " node does not exist");
      repo.save(newNode, 0);
    }
    else {
      LOG.info("OK.. " + newNode.getClass().getSimpleName() + " node exists");
    }
  }
}
