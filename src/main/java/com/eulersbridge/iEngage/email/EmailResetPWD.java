package com.eulersbridge.iEngage.email;

import com.eulersbridge.iEngage.database.domain.User;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.io.StringWriter;


/**
 * @author Yikai Gong
 */

public class EmailResetPWD extends Email implements Serializable {
  public static final String emailTitle = "Reset your Password - from Eulersbridge";
  public static final String senderAddress = "support@eulersbridge.com";
  private static final String templatePath = EmailConstants.EmailResetPWDTemplate;

  private final String token;

  public EmailResetPWD(VelocityEngine velocityEngine, User user, String token) {
    super(velocityEngine, user.getEmail(),
      user.getGivenName() + " " + user.getFamilyName(),
      senderAddress, emailTitle);
    this.token = token;
    if (this.velocityEngine != null)
      this.velocityEngine.init();
  }

  @Override
  public MimeMessagePreparator generatePreparator() throws MessagingException {
    MimeMessagePreparator preparator = mimeMessage -> {
      MimeMessageHelper message =
        new MimeMessageHelper(mimeMessage,
          MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
      message.setTo(new InternetAddress(getRecipientEmailAddress()));
      message.setReplyTo(new InternetAddress(getSenderEmailAddress()));
      message.setFrom(new InternetAddress(getSenderEmailAddress(), "Eulers Bridge"));
      message.setSubject(getSubject());

      VelocityContext vc = new VelocityContext();
      vc.put("recipientName", getRecipientName());
      vc.put("emailAddress", getRecipientEmailAddress());
      vc.put("token", token);
      StringWriter result = new StringWriter();
      velocityEngine.mergeTemplate("templates" + templatePath, "UTF-8", vc, result);
      String body = result.toString();
      message.setText(body, true);

      message.addInline("logo",
        new ClassPathResource("templates/images/logo.png"));
      message.addInline("watermark",
        new ClassPathResource("templates/images/watermark.png"));
    };
    return preparator;
  }


}
