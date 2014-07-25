/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Greg Newitt
 *
 */
public class EmailEventHandlerTest 
{
	EmailEventHandler emailService;


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#EmailEventHandler()}.
	 */
	@Test
	public void testEmailEventHandler() 
	{
		JavaMailSender emailSender=new JavaMailSenderImpl();
		emailService=new EmailEventHandler(emailSender);
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#EmailEventHandler(org.springframework.mail.javamail.JavaMailSender)}.
	 */
	@Test
	public void testEmailEventHandlerJavaMailSender() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#sendEmail()}.
	 */
	@Test
	public void testSendEmail() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#sendEmail(com.eulersbridge.iEngage.email.Email)}.
	 */
	@Test
	public void testSendEmailEmail() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#sendEmail(com.eulersbridge.iEngage.email.EmailVerification)}.
	 */
	@Test
	public void testSendEmailEmailVerification() {
		fail("Not yet implemented");
	}

}
