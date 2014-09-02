package com.eulersbridge.iEngage.email;

import static org.junit.Assert.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.config.TestEmailConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestEmailConfig.class})
@EnableAutoConfiguration
public class EmailTest 
{
    private static Logger LOG = LoggerFactory.getLogger(EmailTest.class);

 	String toEmail="gnewitt@hotmail.com",fromEmail="info@eulersbridge.com";
	String toName="Greg Newitt",subject="Test";
	Email email;
	@Autowired
    private JavaMailSenderImpl sender;
	
	
	@Before
	public void setUp() throws Exception 
	{
		email=new Email(null,toEmail,toName,fromEmail,subject);
	}
	
	@Test
	public void testEmail() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("email - "+email);
	}

	@Test
	public void testGetRecipientEmailAddress() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("recipient - "+email.getRecipientEmailAddress()+" "+toEmail);
		assertEquals("",email.getRecipientEmailAddress(),toEmail);
	}

	@Test
	public void testGetRecipientName() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("recipient - "+email.getRecipientName()+" "+toName);
		assertEquals("",email.getRecipientName(),toName);
	}

	@Test
	public void testGetSenderEmailAddress() 
	{
		assertEquals("Sender address does not match",email.getSenderEmailAddress(),fromEmail);
	}

	@Test
	public void testGeneratePreparator() throws Exception 
	{
		MimeMessagePreparator mp=email.generatePreparator();
		if (LOG.isDebugEnabled()) LOG.debug("host = "+sender.getHost()+" port = "+sender.getPort());
		if (LOG.isDebugEnabled()) LOG.debug("Sender = "+sender);
    	MimeMessage message=sender.createMimeMessage();
    	MimeMessageHelper helper=new MimeMessageHelper(message);
    	
    	InternetAddress to=new InternetAddress("gnewitt@hotmail.com");
		helper.setTo(to);
		helper.setSubject("Testing");
		helper.setFrom(new InternetAddress("greg.newitt@eulersbridge.com"));
		helper.setText("This is a test message.");
 		mp.prepare(message);
 		if (LOG.isDebugEnabled()) LOG.debug(message.toString());
	}

	@Test
	public void testGetSubject() {
		if (LOG.isDebugEnabled()) LOG.debug("recipient - "+email.getSubject()+" "+subject);
		assertEquals("Subject doesn't match",email.getSubject(),subject);
	}

}
