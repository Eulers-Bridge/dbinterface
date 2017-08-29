package com.eulersbridge.iEngage.core.beans;

import com.eulersbridge.iEngage.config.CoreConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Yikai Gong
 */

@Component
public class Util {
  private static Logger LOG = LoggerFactory.getLogger(Util.class);

  // @Async method must be public non-static and cannot be be called in the same
  // class. Therefore I put this method in a separate util bean.
  @Async(value = "threadPoolTaskExecutor")
  public void asyncExecUserTask(String userId, Function<String, Object> func) {
    try {
      LOG.debug("Execute method asynchronously. " + Thread.currentThread().getName());
      func.apply(userId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String getUserEmailFromSession(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = "";
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      userEmail = auth.getName();
    }
    return userEmail;
  }
}
