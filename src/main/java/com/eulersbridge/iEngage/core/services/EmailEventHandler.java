package com.eulersbridge.iEngage.core.services;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.eulersbridge.iEngage.email.Email;
import com.eulersbridge.iEngage.email.EmailSender;
import com.eulersbridge.iEngage.email.EmailVerification;

public class EmailEventHandler implements EmailService
{
	@Autowired
    private JavaMailSender emailSender;
    private static Logger LOG = LoggerFactory.getLogger(EmailEventHandler.class);

    public EmailEventHandler() 
    {
    	if (LOG.isTraceEnabled()) LOG.trace("Constructor()");
    }

    public EmailEventHandler(JavaMailSender emailSender)
    {
    	if (LOG.isTraceEnabled()) LOG.trace("Constructor(emailSender)");
        this.emailSender = emailSender;
    }

	@Override
	public void sendEmail(Email email) {
    	if (LOG.isTraceEnabled()) LOG.trace("sendEmail()");
    	
    	MimeMessagePreparator preparator=email.getPreparator();
    	
    	MimeMessage message=emailSender.createMimeMessage();
    	MimeMessageHelper helper=new MimeMessageHelper(message);
    	
    	try
    	{
        	InternetAddress to=new InternetAddress(email.getEmailAddress());
    		helper.setTo(to);
    		helper.setSubject(email.getSubject());
    		helper.setFrom(new InternetAddress(email.getFrom()));
    		helper.setText(email.getBody());
    		emailSender.send(message);
//    		emailSender.send(preparator);
    	}
    	catch (MailException | MessagingException ex)
    	{
    		LOG.error(ex.toString());
    	}
    	
        return ;
	}
}