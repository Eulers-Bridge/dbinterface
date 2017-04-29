package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.email.Email;
import com.eulersbridge.iEngage.email.EmailVerification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
	public void sendEmail() {
    	if (LOG.isTraceEnabled()) LOG.trace("sendEmail()");
    	
    	MimeMessage message=emailSender.createMimeMessage();
    	MimeMessageHelper helper=new MimeMessageHelper(message);
    	
    	try
    	{
        	InternetAddress to=new InternetAddress("gnewitt@hotmail.com");
    		helper.setTo(to);
    		helper.setSubject("Testing");
    		helper.setFrom(new InternetAddress("greg.newitt@eulersbridge.com"));
    		helper.setText("This is a test message.");
    		emailSender.send(message);
    	}
    	catch (MailException | MessagingException ex)
    	{
    		LOG.error(ex.toString());
    	}
    	
        return ;
	}
    
    @Override
	public void sendEmail(Email email) {
    	if (LOG.isTraceEnabled()) LOG.trace("sendEmail(Email email)");
    	
    	try
    	{
    		emailSender.send(email.generatePreparator());
    	}
    	catch (MailException | MessagingException ex)
    	{
    		LOG.error(ex.toString());
    	}
    	
        return ;
	}

	@Override
	public void sendEmail(EmailVerification email) {
    	if (LOG.isTraceEnabled()) LOG.trace("sendEmail(EmailVerification email)");
    	
    	try
    	{
    		emailSender.send(email.generatePreparator());
    	}
    	catch (MailException | MessagingException ex)
    	{
    		LOG.error(ex.toString());
    	}
    	
        return ;
	}
}