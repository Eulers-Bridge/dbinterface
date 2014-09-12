package com.eulersbridge.iEngage.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spockframework.mock.runtime.MockController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;


public class ViewUserIntegrationTest 
{
    private static Logger LOG = LoggerFactory.getLogger(ViewUserIntegrationTest.class);

    MockMvc mockMvc;
	
	@InjectMocks
	UserController controller;
	
	@Mock
	UserService userService;
	
	String email = "greg.newitt@unimelb.edu.au";
	String email2 = "graeme.newitt@unimelb.edu.au";
	
	@Before
	public void setup() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	@Test
	public void shouldReturnUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		ReadUserEvent testData=RestDataFixture.customEmailUser2(email);
		UserDetails dets=testData.getReadUserDetails();
		when (userService.requestReadUser(any(RequestReadUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/user/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
		.andExpect(jsonPath("$.personality",is(dets.getPersonality())))
		.andExpect(jsonPath("$.password",is(dets.getPassword())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void shouldReturnUserNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		when (userService.requestReadUser(any(RequestReadUserEvent.class))).thenReturn(ReadUserEvent.notFound(email2));
		this.mockMvc.perform(get("/api/user/{email}/",email2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
}
