package com.eulersbridge.iEngage.core.beans;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.eulersbridge.iEngage.config.CoreConfig;
import com.eulersbridge.iEngage.core.notification.SNSNotification;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.function.Function;

/**
 * @author Yikai Gong
 */

@Component
public class Util {
  private static Logger LOG = LoggerFactory.getLogger(Util.class);

  private final AmazonSNS sns;
  private Long defaultInstitutionId = -1l;
  public static String serverIp;
  static  {
    serverIp = getPublicIP();
  }
  @Autowired
  public Util(AmazonSNS sns) {
    this.sns = sns;
  }

  // @Async method must be public non-static and cannot be be called in the same
  // class. Therefore I put this method in a separate util bean.
  @Async(value = "threadPoolTaskExecutor")
  public <T> void  asyncExecUserTask(T input, Function<T, Object> func) {
    try {
      LOG.debug("Execute method asynchronously. " + Thread.currentThread().getName());
      func.apply(input);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Async(value = "threadPoolTaskExecutor")
  public void sendNotification(SNSNotification notification) {
    if (!notification.isValid()) {
      LOG.warn("Send notification abort due to invalid SNSNotification");
      return;
    }
    CreatePlatformEndpointRequest endpointRequest = new CreatePlatformEndpointRequest();
//    System.out.println("DeviceToken: " + notification.getDeviceToken());
    endpointRequest.setToken(notification.getDeviceToken());
//    System.out.println("TopicArn: " + notification.getTopicArn());
    endpointRequest.setPlatformApplicationArn(notification.getTopicArn());
    try {
      CreatePlatformEndpointResult creEndResult = sns.createPlatformEndpoint(endpointRequest);
      PublishRequest pubRequest = new PublishRequest();
      pubRequest.setTargetArn(creEndResult.getEndpointArn());
      pubRequest.setSubject(notification.getSubject());
      pubRequest.setMessage(notification.getMessage());
      sns.publish(pubRequest);
    } catch (Exception e) {
      LOG.error("Failed in sending Notification. " + e.getMessage());
    }
  }

  @Async(value = "threadPoolTaskExecutor")
  public void sendNotifications(List<SNSNotification> notifications) {
    notifications.forEach(this::sendNotification);
  }

  public static String getUserEmailFromSession() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = "";
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      userEmail = auth.getName();
    }
    return userEmail;
  }

  public static String getUserFullName(User user) {
    String givenName = user.getGivenName();
    String familyName = user.getFamilyName();
    String fullName = "";
    if (givenName != null && !givenName.isEmpty())
      fullName = givenName;
    if (familyName != null && !familyName.isEmpty())
      fullName = fullName + " " + familyName;
    if (fullName.isEmpty())
      fullName = "Someone anonymous";
    return fullName;
  }


  public static SNSNotification buildFriReqNotif(User receiver, User sender) {
    String subject = "New Friend Request";
    String senderName = getUserFullName(sender);
    String msg = senderName + " has sent an invitation to connect \uD83E\uDD1D";

    String arn = receiver.getArn();
    String devToken = receiver.getDeviceToken();
    SNSNotification noti = new SNSNotification(devToken, arn, subject, msg);
    return noti;
  }

  public static SNSNotification buildAddedVoteRemindNotifi(User sender, User receiver, Election election) {
    String subject = "Your friend's Vote Reminder";
    String senderName = getUserFullName(sender);
    String msg = senderName + " will be a voter in the \""+ election.getTitle() +"\". Play your part \uD83D\uDDF3️";
    String arn = receiver.getArn();
    String devToken = receiver.getDeviceToken();
    SNSNotification noti = new SNSNotification(devToken, arn, subject, msg);
    return noti;
  }

  public void boardcastNotifiToFriends(User user, Election election, UserRepository userRepo){
    List<User> friendsList = userRepo.findContactsZeroDepth(user.getEmail());
    friendsList.forEach(receiver->{
      this.sendNotification(Util.buildAddedVoteRemindNotifi(user, receiver, election));
    });
  }

  public void loadDefaultInstitutionId (InstitutionRepository institutionRepository){
    defaultInstitutionId = institutionRepository.findDefaultInstitution().getNodeId();
  }

  public Long getDefaultInstitutionId() {
    return defaultInstitutionId;
  }

  private static String getPublicIP() {
    String ip = null;
    try {
      URL whatismyip = new URL("http://checkip.amazonaws.com");
      BufferedReader in = new BufferedReader(new InputStreamReader(
        whatismyip.openStream()));
      ip = in.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ip;
  }
}
