package com.eulersbridge.iEngage.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;


public class ViewUserIntegrationTest 
{
    private static Logger LOG = LoggerFactory.getLogger(ViewUserIntegrationTest.class);

    MockMvc mockMvc;
	
	@InjectMocks
	@Autowired
	UserController controller;
	
	@Mock
	UserService userService;
	
	String email = "greg.newitt@unimelb.edu.au";
	
	@Before
	public void setup() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	@Test
	public void thatViewUserRendersCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		when (userService.requestReadUser(any(RequestReadUserEvent.class))).thenReturn(RestDataFixture.customEmailUser2(email));
		this.mockMvc.perform(get("/api/user/{email}",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print());
	}
}
