/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import org.junit.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class EmailEventHandlerTest 
{
	EmailEventHandler emailService;
	JavaMailSender emailSender;


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
	public void setUp() throws Exception 
	{
		emailSender=new JavaMailSenderImpl();
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
		EmailEventHandler emailService2=new EmailEventHandler(emailSender);
		assertNotNull("null email service returned.",emailService2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#EmailEventHandler(org.springframework.mail.javamail.JavaMailSender)}.
	 */
	@Test
	public void testEmailEventHandlerJavaMailSender() 
	{
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#sendEmail()}.
	 */
	@Test
	public void testSendEmail() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#sendEmail(com.eulersbridge.iEngage.email.Email)}.
	 */
	@Test
	public void testSendEmailEmail() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EmailEventHandler#sendEmail(com.eulersbridge.iEngage.email.EmailVerification)}.
	 */
	@Test
	public void testSendEmailEmailVerification() {
//		fail("Not yet implemented");
	}

}
