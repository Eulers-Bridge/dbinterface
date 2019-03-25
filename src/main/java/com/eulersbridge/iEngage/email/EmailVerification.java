package com.eulersbridge.iEngage.email;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.io.StringWriter;
import java.time.Year;

public class EmailVerification extends Email implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private final VerificationToken token;
  private final String tokenType;
  private final String resourceName;
  private final String serverIp;

  private static Logger LOG = LoggerFactory.getLogger(EmailVerification.class);

  public EmailVerification(VelocityEngine velocityEngine, User user, VerificationToken token, String serverIP) {
    super(velocityEngine, user.getEmail(), user.getGivenName() + " " + user.getFamilyName(), "support@eulersbridge.com", "Email Verification");
    resourceName = EmailConstants.EmailVerificationTemplate;
    this.token = token;
    this.tokenType = token.getTokenType();
    this.serverIp = serverIP;
    if (LOG.isDebugEnabled())
      LOG.debug("this.velocityEngine = " + this.velocityEngine);
//    if (this.velocityEngine != null)
//      this.velocityEngine.init();
  }

  public String getEncodedToken() {
    return token.getEncodedTokenString();
  }

  public VerificationToken getToken() {
    return token;
  }

  public String getTokenType() {
    return tokenType;
  }

  @Override
  public MimeMessage generatePreparator(JavaMailSender sender) throws MessagingException {
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = setupMimeMessageHelper(message);

    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("recipientName", getRecipientName());
    velocityContext.put("emailAddress", getRecipientEmailAddress());
    velocityContext.put("verificationToken", getEncodedToken());
    velocityContext.put("serverIp", serverIp);
    velocityContext.put("year", Integer.toString(Year.now().getValue()));
    StringWriter sw = new StringWriter();
    velocityEngine.mergeTemplate(resourceName, "UTF-8", velocityContext, sw);
    String body = sw.toString();
    helper.setText(body, true);  //true to enable html

    ClassPathResource logoResource = new ClassPathResource("templates/images/logo.png");
    helper.addInline("logo", logoResource);
    ClassPathResource watermarkResource = new ClassPathResource("templates/images/watermark.png");
    helper.addInline("watermark", watermarkResource);

    return message;
  }

  public String toString() {
    return super.toString() + " token: " + token.toString();
  }



}
