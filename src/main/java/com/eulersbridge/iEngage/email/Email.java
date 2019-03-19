package com.eulersbridge.iEngage.email;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;

public class Email implements Serializable {
  private final String recipientEmailAddress;
  private final String recipientName;
  private final String senderEmailAddress;
  private final String subject;
  protected final transient VelocityEngine velocityEngine;
  private static transient Logger LOG = LoggerFactory.getLogger(Email.class);

  public Email(VelocityEngine velocityEngine, String recipientEmailAddress, String recipientName, String senderEmailAddress, String subject) {
    if (LOG.isDebugEnabled())
      LOG.debug("Constructor(" + recipientEmailAddress + "," + recipientName + "," + senderEmailAddress + "," + subject + ")");
    this.velocityEngine = velocityEngine;
    this.recipientEmailAddress = recipientEmailAddress;
    this.recipientName = recipientName;
    this.senderEmailAddress = senderEmailAddress;
    this.subject = subject;
  }

  public String getRecipientEmailAddress() {
    return recipientEmailAddress;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public String getSenderEmailAddress() {
    return senderEmailAddress;
  }

  public MimeMessage generatePreparator(JavaMailSender sender) throws MessagingException {
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(getRecipientEmailAddress());
    helper.setReplyTo(getSenderEmailAddress());
    helper.setFrom(getSenderEmailAddress());
    helper.setSubject(getSubject());
    String body = "This is a test email.";
    helper.setText(body);

    return message;
  }

  public String getSubject() {
    return subject;
  }

  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append("To:");
    buff.append(recipientEmailAddress);
    buff.append(" Recipient:");
    buff.append(recipientName);
    buff.append(" From:");
    buff.append(senderEmailAddress);
    buff.append(" Subject:");
    buff.append(subject);
    return buff.toString();
  }

  public MimeMessageHelper setupMimeMessageHelper(MimeMessage message) throws MessagingException {
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(getRecipientEmailAddress());
    helper.setFrom(getSenderEmailAddress());
    helper.setReplyTo(getSenderEmailAddress());
    helper.setSubject(getSubject());
    return helper;
  }

}
