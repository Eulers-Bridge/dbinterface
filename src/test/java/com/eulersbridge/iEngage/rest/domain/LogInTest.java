/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.users.LoginDetails;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class LogInTest
{
	LogIn login;
	UserDetails user;
	Long userId;

	@Mock
	private ServletRequestAttributes attrs;

	
	private static Logger LOG = LoggerFactory.getLogger(LogInTest.class);
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);
		MockHttpServletRequest request=new MockHttpServletRequest();
		
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Iterator<NewsArticleDetails> articles = null;
		user=RestDataFixture.customEmailUser();
		userId=1l;
		LoginDetails dets=new LoginDetails(articles, user, userId);
		login=LogIn.fromLoginDetails(dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.LogIn#fromLoginDetails(com.eulersbridge.iEngage.core.events.users.LoginDetails)}.
	 */
	@Test
	public final void testFromLoginDetails() 
	{
		assertEquals("",user.getInstitutionId(),login.getUser().getInstitutionId());
		LOG.debug(login.toString());
	}

}
