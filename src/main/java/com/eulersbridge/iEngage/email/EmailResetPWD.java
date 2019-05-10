package com.eulersbridge.iEngage.email;

import com.eulersbridge.iEngage.database.domain.User;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
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
import java.util.Calendar;


/**
 * @author Yikai Gong
 */

public class EmailResetPWD extends Email implements Serializable {
  public static final String emailTitle = "Reset your Password - from Eulersbridge";
  public static final String senderAddress = "support@eulersbridge.com";
  public static final String domainUrl = "https://isegoria.app/forgot-password";
  private static final String templatePath = EmailConstants.EmailResetPWDTemplate;

  private final String token;
  private final Long institutionID;
  private final String institution_name;

  public EmailResetPWD(VelocityEngine velocityEngine, User user, String token) {
    super(velocityEngine, user.getEmail(),
      user.getGivenName() + " " + user.getFamilyName(),
      senderAddress, emailTitle);
    this.token = token;
    this.institutionID = user.getInstitution().getNodeId();
    this.institution_name = user.getInstitution$().getName().toLowerCase();
//    if (this.velocityEngine != null)
//      this.velocityEngine.init();
  }

  @Override
  public MimeMessage generatePreparator(JavaMailSender sender) throws MessagingException {
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = setupMimeMessageHelper(message);

    VelocityContext vc = new VelocityContext();
    vc.put("domainUrl", domainUrl);
    vc.put("year", Integer.toString(Year.now().getValue()));
    vc.put("recipientName", getRecipientName());
    vc.put("emailAddress", getRecipientEmailAddress());
    vc.put("token", token);
    vc.put("institution", institution_name);
    StringWriter result = new StringWriter();
    velocityEngine.mergeTemplate(templatePath, "UTF-8", vc, result);
    String body = result.toString();
    helper.setText(body, true);

    helper.addInline("logo",
      new ClassPathResource("templates/images/logo.png"));
    helper.addInline("watermark",
      new ClassPathResource("templates/images/watermark.png"));

    return message;
  }


}
