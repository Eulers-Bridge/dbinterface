/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.services.interfacePack.ContactRequestService;
import com.eulersbridge.iEngage.core.services.interfacePack.NotificationService;
import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Greg Newitt
 *
 */
public class ContactDomainControllerTest
{
    MockMvc mockMvc;
	
	@InjectMocks
	ContactController controller;
	
	@Mock
	UserService userService;
	
	@Mock
	ContactRequestService contactRequestService;
	
	@Mock
	NotificationService notificationService;
	
    private static Logger LOG = LoggerFactory.getLogger(ContactDomainControllerTest.class);
    
	String email = "greg.newitt@unimelb.edu.au";
	String email2 = "graeme.newitt@unimelb.edu.au";
	String urlPrefix = ControllerConstants.API_PREFIX+ControllerConstants.USER_LABEL;
	String urlPrefix2 = ControllerConstants.API_PREFIX+ControllerConstants.CONTACT_LABEL;
	String urlPrefix3 = ControllerConstants.API_PREFIX+ControllerConstants.CONTACT_REQUESTS_LABEL;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ContactController#ContactController()}.
	 */
	@Test
	public final void testContactController()
	{
		ContactController cc=new ContactController();
		assertNotNull("Not yet implemented",cc);
	}



}
