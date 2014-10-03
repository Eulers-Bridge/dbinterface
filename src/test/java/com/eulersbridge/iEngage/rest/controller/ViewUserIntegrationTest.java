package com.eulersbridge.iEngage.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent;
import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityAddedEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityDetails;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UpdateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.users.UserUpdatedEvent;
import com.eulersbridge.iEngage.core.services.EmailService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.email.EmailVerification;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;
import com.eulersbridge.iEngage.rest.controller.fixture.RestEventFixtures;


public class ViewUserIntegrationTest 
{
    private static Logger LOG = LoggerFactory.getLogger(ViewUserIntegrationTest.class);

    MockMvc mockMvc;
	
	@InjectMocks
	UserController controller;
	
	@Mock
	UserService userService;
	
	@Mock
	EmailService emailService;
	
	String email = "greg.newitt@unimelb.edu.au";
	String email2 = "graeme.newitt@unimelb.edu.au";
	
	@Before
	public void setup() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	private String populatePersonalityContent(PersonalityDetails dets)
	{
		return "{\"personalityId\":\""+dets.getPersonalityId()+
				"\",\"agreeableness\":\""+dets.getAgreeableness()+
				"\",\"conscientiousness\":\""+dets.getConscientiousness()+
				"\",\"emotionalStability\":\""+dets.getEmotionalStability()+
				"\",\"extroversion\":\""+dets.getExtroversion()+
				"\",\"openess\":\""+dets.getOpeness()+
				"\"}";
	}
	
	private String populateContent(UserDetails dets)
	{
		return "{\"givenName\":\""+dets.getGivenName()+
				"\",\"familyName\":\""+dets.getFamilyName()+
				"\",\"gender\":\""+dets.getGender()+
				"\",\"nationality\":\""+dets.getNationality()+
				"\",\"yearOfBirth\":\""+dets.getYearOfBirth()+
				"\",\"password\":\""+dets.getPassword()+
				"\",\"accountVerified\":"+dets.isAccountVerified()+
				",\"institutionId\":"+dets.getInstitutionId().intValue()+
				",\"email\":\""+dets.getEmail()+"\"}";
	}
	
	private String populateReturnedContent(UserDetails dets)
	{
		return "{\"givenName\":\""+dets.getGivenName()+
				"\",\"familyName\":\""+dets.getFamilyName()+
				"\",\"gender\":\""+dets.getGender()+
				"\",\"nationality\":\""+dets.getNationality()+
				"\",\"yearOfBirth\":\""+dets.getYearOfBirth()+
				"\",\"password\":\""+dets.getPassword()+
				"\",\"accountVerified\":"+dets.isAccountVerified()+
				",\"institutionId\":"+dets.getInstitutionId().intValue()+
				",\"email\":\""+dets.getEmail()+
				"\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/user/"+dets.getEmail()+
				"\"},{\"rel\":\"User Status\",\"href\":\"http://localhost/api/user/"+dets.getEmail()+
				"/status\"},{\"rel\":\"User Details\",\"href\":\"http://localhost/api/user/"+dets.getEmail()+
				"/details\"}]}";
	}
	
	@Test
	public void putShouldAddPersonalityToUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingPersonality()");
		PersonalityDetails dets=new PersonalityDetails(3L,4.0F,3.0F,2.0F,4.0F,5.0F);
		PersonalityAddedEvent resEvt=new PersonalityAddedEvent();
		resEvt.setPersonalityDetails(dets);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.addPersonality(any(AddPersonalityEvent.class))).thenReturn(resEvt);
		String content=populatePersonalityContent(dets);
		this.mockMvc.perform(put("/api/user/{email}/personality",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.personalityId",is(dets.getPersonalityId().intValue())))
		.andExpect(jsonPath("$.agreeableness",is((dets.getAgreeableness().doubleValue()))))
		.andExpect(jsonPath("$.conscientiousness",is(dets.getConscientiousness().doubleValue())))
		.andExpect(jsonPath("$.emotionalStability",is(dets.getEmotionalStability().doubleValue())))
		.andExpect(jsonPath("$.extroversion",is(dets.getExtroversion().doubleValue())))
		.andExpect(jsonPath("$.openess",is(dets.getOpeness().doubleValue())))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void putShouldReturnUserNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingPersonality()");
		PersonalityDetails dets=new PersonalityDetails(3L,4.0F,3.0F,2.0F,4.0F,5.0F);
		PersonalityAddedEvent resEvt=PersonalityAddedEvent.userNotFound();
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.addPersonality(any(AddPersonalityEvent.class))).thenReturn(resEvt);
		String content=populatePersonalityContent(dets);
		this.mockMvc.perform(put("/api/user/{email}/personality",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isFailedDependency());
	}
	
	@Test
	public void putShouldReturnUserBadRequest() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingPersonality()");
		PersonalityDetails dets=new PersonalityDetails(3L,4.0F,3.0F,2.0F,4.0F,5.0F);
		when(userService.addPersonality(any(AddPersonalityEvent.class))).thenReturn(null);
		String content=populatePersonalityContent(dets);
		this.mockMvc.perform(put("/api/user/{email}/personality",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putShouldReturnUserBadRequest2() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingPersonality()");
		when(userService.addPersonality(any(AddPersonalityEvent.class))).thenReturn(null);
		this.mockMvc.perform(put("/api/user/{email}/personality",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getShouldReturnUserCorrectlyFromRead() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
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
		.andExpect(jsonPath("$.password",is(dets.getPassword())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void getShouldReturnUserNotFoundFromRead() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		when (userService.requestReadUser(any(RequestReadUserEvent.class))).thenReturn(ReadUserEvent.notFound(email2));
		this.mockMvc.perform(get("/api/user/{email}/",email2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void putShouldReturnUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdate()");
		ReadUserEvent readData=RestDataFixture.customEmailUser2(email);
		UserDetails dets=readData.getReadUserDetails();
		UserUpdatedEvent testData=new UserUpdatedEvent(email, dets);
		String content=populateContent(dets);
		String returnedContent=populateReturnedContent(dets);
		when (userService.updateUser(any(UpdateUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/user/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
		.andExpect(jsonPath("$.password",is(dets.getPassword())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void putShouldReturnInstitutionNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdate()");
		UserDetails dets=RestDataFixture.customEmailUser(email);
		UserUpdatedEvent testData=UserUpdatedEvent.instituteNotFound(email);
		String content=populateContent(dets);
		when (userService.updateUser(any(UpdateUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/user/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isFailedDependency())	;
	}
	
	@Test
	public void postShouldReturnUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreate()");
		UserCreatedEvent testData=RestEventFixtures.populateUserCreatedEvent();
		UserDetails dets=testData.getUserDetails();
		String content=populateContent(dets);
		String returnedContent=populateReturnedContent(dets);
		when (userService.signUpNewUser(any(CreateUserEvent.class))).thenReturn(testData);
		doNothing().when(emailService).sendEmail(any(EmailVerification.class));
		this.mockMvc.perform(post("/api/signUp/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
		.andExpect(jsonPath("$.password",is(dets.getPassword())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void postShouldReturnBadRequestNoContent() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		UserCreatedEvent testData=UserCreatedEvent.userNotUnique(email);
		when (userService.signUpNewUser(any(CreateUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/signUp/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void postShouldReturnBadRequest() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		UserCreatedEvent testData=RestEventFixtures.populateUserCreatedEvent();
		String content="{\"givenName2\":\"Greg\",\"familyName2\":\"Newitt\",\"gender\":\"Male\",\"nationality\":\"Australian\",\"yearOfBirth\":\"1971\",\"password\":\"password\",\"accountVerified\":false,\"institutionId\":26,\"email2\":\"greg.newitt@unimelb.edu.au\"}";
		when (userService.signUpNewUser(any(CreateUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/signUp/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void postShouldReturnUserNotUnique() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateUserNotUnique()");
		UserCreatedEvent testData=UserCreatedEvent.userNotUnique(email);
		String content="{\"givenName\":\"Greg\",\"familyName\":\"Newitt\",\"gender\":\"Male\",\"nationality\":\"Australian\",\"yearOfBirth\":\"1971\",\"password\":\"password\",\"accountVerified\":false,\"institutionId\":26,\"email\":\"greg.newitt@unimelb.edu.au\"}";
		when (userService.signUpNewUser(any(CreateUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/signUp/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isConflict())	;
	}
	
	@Test
	public void deleteShouldReturnUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDelete()");
		ReadUserEvent readData=RestDataFixture.customEmailUser2(email);
		UserDetails dets=readData.getReadUserDetails();
		UserDeletedEvent testData=new UserDeletedEvent(email, dets);
		when (userService.deleteUser(any(DeleteUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete("/api/user/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
		.andExpect(jsonPath("$.password",is(dets.getPassword())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void deleteShouldReturnUserNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDelete()");
		when (userService.deleteUser(any(DeleteUserEvent.class))).thenReturn(UserDeletedEvent.notFound(email2));
		this.mockMvc.perform(delete("/api/user/{email}/",email2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
}
