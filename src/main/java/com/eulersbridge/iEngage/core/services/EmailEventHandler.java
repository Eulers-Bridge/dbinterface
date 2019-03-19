package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.services.interfacePack.EmailService;
import com.eulersbridge.iEngage.email.Email;
import com.eulersbridge.iEngage.email.EmailVerification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
public class EmailEventHandler implements EmailService {

  private JavaMailSender emailSender;

  private static Logger LOG = LoggerFactory.getLogger(EmailEventHandler.class);

  @Autowired
  public EmailEventHandler(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public void sendEmail() {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("yikaig@student.unimelb.edu.au");
    message.setSubject("Testing");
    message.setFrom("support@eulersbridge.com");
    message.setText("This is a test message.");

    try {
      emailSender.send(message);
    } catch (MailException ex) {
      LOG.error(ex.toString());
    }
  }

  @Override
  public void sendEmail(Email email) {
    if (LOG.isTraceEnabled()) LOG.trace("sendEmail(Email email)");
    try {
      emailSender.send(email.generatePreparator(emailSender));
    } catch (MailException | MessagingException ex) {
      LOG.error(ex.toString());
    }
  }

  @Override
  public void sendEmail(EmailVerification email) {
    if (LOG.isTraceEnabled()) LOG.trace("sendEmail(EmailVerification email)");
    try {
      emailSender.send(email.generatePreparator(emailSender));
    } catch (MailException | MessagingException ex) {
      LOG.error(ex.toString());
    }
  }
}