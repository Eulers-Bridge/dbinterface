package com.eulersbridge.iEngage.email;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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
  public MimeMessagePreparator generatePreparator() throws MessagingException {
    MimeMessagePreparator preparator = mimeMessage -> {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
      message.setTo(new InternetAddress(getRecipientEmailAddress()));
      String body = null;
      message.setReplyTo(new InternetAddress(getSenderEmailAddress()));
      message.setFrom(new InternetAddress(getSenderEmailAddress(), "Eulers Bridge"));
      message.setSubject(getSubject());
      VelocityContext velocityContext = new VelocityContext();
//    			velocityContext.put("email", this);
      velocityContext.put("recipientName", getRecipientName());
      velocityContext.put("emailAddress", getRecipientEmailAddress());
      velocityContext.put("verificationToken", getEncodedToken());
      velocityContext.put("serverIp", serverIp);

      if (LOG.isDebugEnabled()) LOG.debug("Velocity engine :" + velocityEngine);
      StringWriter sw = new StringWriter();
      velocityEngine.mergeTemplate(resourceName, "UTF-8", velocityContext, sw);
      body = sw.toString();
//    			body="Dear "+getRecipientName()+", your token is "+getToken()+" your url is \nhttp://eulersbridge.com:8080/dbInterface/api/emailVerification/"+getRecipientEmailAddress()+"/"+getToken()+" Thankyou.";
      if (LOG.isDebugEnabled()) LOG.debug("body={}", body);
      message.setText(body, true);

      ClassPathResource logoResource = new ClassPathResource("templates/images/logo.png");
      if (LOG.isDebugEnabled())
        LOG.debug("class path resource exists" + logoResource.exists());
      message.addInline("logo", logoResource);

      ClassPathResource watermarkResource = new ClassPathResource("templates/images/watermark.png");
      if (LOG.isDebugEnabled())
        LOG.debug("class path resource exists" + watermarkResource.exists());
      message.addInline("watermark", watermarkResource);

         /*for(String resourceIdentifier: hTemplateVariables.keySet()) {
           //Inline resources are added to the mime message using the specified Content-ID.
            //The order in which you are adding the text and the resource are very important.
            //Be sure to first add the text and after that the resources.
            //If you are doing it the other way around, it won't work!
           addInlineResource(message, hTemplateVariables.get(resourceIdentifier).toString(), resourceIdentifier);
         }*/

    };

    return preparator;
  }

  public String toString() {
    return super.toString() + " token: " + token.toString();
  }

}
