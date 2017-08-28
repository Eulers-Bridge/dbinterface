package com.eulersbridge.iEngage.security;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Yikai Gong
 * This class is used as a event listener bean (@Component) in Spring for
 * executing sometasks after API is setup.
 *
 * By default, this listener should be turned off !
 */

//@Component
public class ApplicationEventListener implements ApplicationListener<ContextRefreshedEvent> {
  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
//    ApplicationContext ctx = event.getApplicationContext();
//    (new Thread(() -> {
//
//      System.out.println("Thread start from ApplicationEventListener !");
//      Iterable<User> userItr = userRepository.findAll(0);
//      userItr.forEach(u->{
//        u.setPassword(passwordEncoder.encode(u.getPassword()));
//        userRepository.save(u, 0);
//      });
//
//    })).start();
  }
}
