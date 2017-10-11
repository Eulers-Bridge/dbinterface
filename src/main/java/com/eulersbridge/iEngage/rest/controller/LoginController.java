package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.domain.Logout;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.users.LoginDetails;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.notification.SNSNotification;
import com.eulersbridge.iEngage.core.services.interfacePack.NewsService;
import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
import com.eulersbridge.iEngage.rest.domain.LogIn;
import com.eulersbridge.iEngage.rest.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class LoginController {
  @Autowired
  UserService userService;
  @Autowired
  NewsService newsService;
  @Autowired
  Util util;

  private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

  private final AtomicLong counter = new AtomicLong();


  @RequestMapping(value = ControllerConstants.LOGIN_LABEL, method = RequestMethod.GET)
  public @ResponseBody
  ResponseEntity<LogIn> login(@RequestParam(value = "topicArn", required = false) String topicArn,
                              @RequestParam(value = "deviceToken", required = false) String deviceToken) {
    String userEmail = Util.getUserEmailFromSession();
    if (LOG.isInfoEnabled()) LOG.info(userEmail + " attempting to login. ");
    if (topicArn != null && deviceToken != null) {
      userService.updateSNSTokens(userEmail, topicArn, deviceToken);
    }
    ReadUserEvent userEvent = userService.readUser(new RequestReadUserEvent(userEmail));
    if (!userEvent.isEntityFound()) {
      return new ResponseEntity<LogIn>(HttpStatus.NOT_FOUND);
    }
    UserDetails userDetails = (UserDetails) userEvent.getDetails();
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(ControllerConstants.PAGE_NUMBER);
    pageLength = Integer.parseInt(ControllerConstants.PAGE_LENGTH);

    Long institutionId = userDetails.getInstitutionId();
    Long userId = userDetails.getNodeId();
    ReadAllEvent rnae = new ReadAllEvent(institutionId);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve news articles from institutionId. " + institutionId);
    Direction sortDirection = Direction.DESC;
    NewsArticlesReadEvent articleEvent = newsService.readNewsArticles(rnae, sortDirection, pageNumber, pageLength);

    if (!articleEvent.isEntityFound()) {
      return new ResponseEntity<LogIn>(HttpStatus.NOT_FOUND);
    }

    LoginDetails result = new LoginDetails(articleEvent.getArticles().iterator(), userDetails, userId);

    LogIn response = LogIn.fromLoginDetails(result);

    //TODO temp test code.
    //TODO Load the content of notification from application.properties via ParamBean
    SNSNotification notification = new SNSNotification(userDetails.getDeviceToken(), userDetails.getArn(), "Login Welcome", "Hi " + userDetails.getGivenName() + "! This is a testing notification on you login.");
    util.sendNotification(notification);

    return new ResponseEntity<LogIn>(response, HttpStatus.OK);
  }

  @RequestMapping(value = ControllerConstants.LOGOUT_LABEL)
  public @ResponseBody
  Response logout() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (LOG.isInfoEnabled()) LOG.info(username + " attempting to logout. ");
    Logout logout = new Logout(counter.incrementAndGet(), username);
    SecurityContextHolder.clearContext();
    Response response = logout.process();
    return response;
  }

  public static String getUserIdentity() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    String userIdentity = authentication.getName();
    return userIdentity;
  }

  public static boolean verifyUserIdentity(String email) {
    return email.equals(getUserIdentity());
  }
}