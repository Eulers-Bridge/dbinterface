package com.eulersbridge.iEngage.email;

import static org.junit.Assert.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eulersbridge.iEngage.config.TestEmailConfig;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import com.eulersbridge.iEngage.database.domain.VerificationToken.VerificationTokenType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestEmailConfig.class})
@EnableAutoConfiguration
public class EmailVerificationTest 
{
	EmailVerification email;
    private static Logger LOG = LoggerFactory.getLogger(EmailVerificationTest.class);

 	String toEmail="gnewitt@hotmail.com",fromEmail="info@eulersbridge.com";
	String toName="Greg Newitt",subject="Test";
	VerificationToken token;
	@Autowired
    private JavaMailSenderImpl sender;
	@Autowired VelocityEngine velocityEngine;
	
	@Before
	public void setUp() throws Exception 
	{
		VerificationTokenType tokenType=VerificationTokenType.emailVerification;
		User user=new User("gnewitt@bigfoot.com","Greg","Newitt","","","","");
		token=new VerificationToken(tokenType,user, 60);
		email=new EmailVerification(velocityEngine,user, token);
		if (LOG.isDebugEnabled()) LOG.debug("verification email - "+email);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetToken() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("recipient - "+email.getToken()+" "+token.getToken());
		assertEquals("",email.getToken(),token);
	}

	@Test
	public void testGetTokenType() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("recipient - "+email.getTokenType()+" "+token.getTokenType());
		assertEquals("",email.getTokenType(),token.getTokenType());
	}

	@Test
	public void testGeneratePreparator() throws Exception 
	{
//		MimeMessagePreparator mp=email.generatePreparator();
		if (LOG.isDebugEnabled()) LOG.debug("host = "+sender.getHost()+" port = "+sender.getPort());
		if (LOG.isDebugEnabled()) LOG.debug("Sender = "+sender);
    	MimeMessage message=sender.createMimeMessage();
    	MimeMessageHelper helper=new MimeMessageHelper(message);
    	
    	InternetAddress to=new InternetAddress("gnewitt@hotmail.com");
		helper.setTo(to);
		helper.setSubject("Testing");
		helper.setFrom(new InternetAddress("greg.newitt@eulersbridge.com"));
		helper.setText("This is a test message.");
// 		mp.prepare(message);
 		if (LOG.isDebugEnabled()) LOG.debug(message.toString());
	}

}
