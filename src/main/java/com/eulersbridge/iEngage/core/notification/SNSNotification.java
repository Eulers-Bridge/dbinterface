package com.eulersbridge.iEngage.core.notification;


/**
 * @author Yikai Gong
 */

public class SNSNotification {
  private String deviceToken;
  private String topicArn;
  private String subject;
  private String message;

  public SNSNotification(String deviceToken, String topicArn, String subject, String message) {
    this.deviceToken = deviceToken;
    this.topicArn = topicArn;
    this.subject = subject;
    this.message = message;
  }

  public String getDeviceToken() {
    return deviceToken;
  }

  public String getTopicArn() {
    return topicArn;
  }

  public String getSubject() {
    return subject;
  }

  public String getMessage() {
    return message;
  }

  public boolean isValid() {
    return deviceToken != null && topicArn != null && subject != null && message != null
      && !deviceToken.isEmpty() && !topicArn.isEmpty();
  }
}
