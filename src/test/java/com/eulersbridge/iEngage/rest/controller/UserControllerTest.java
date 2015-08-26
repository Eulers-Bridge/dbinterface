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
import com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.DeleteVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.ReadVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordAddedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDeletedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordReadEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.AddVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.ReadVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDeletedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDetails;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderReadEvent;
import com.eulersbridge.iEngage.core.services.ContactRequestService;
import com.eulersbridge.iEngage.core.services.EmailService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.email.EmailVerification;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;
import com.eulersbridge.iEngage.rest.controller.fixture.RestEventFixtures;


public class UserControllerTest 
{
    private static Logger LOG = LoggerFactory.getLogger(UserControllerTest.class);

    MockMvc mockMvc;
	
	@InjectMocks
	UserController controller;
	
	@Mock
	UserService userService;
	
	@Mock
	EmailService emailService;
	
	@Mock
	ContactRequestService contactRequestService;
	
	String email = "greg.newitt@unimelb.edu.au";
	String email2 = "graeme.newitt@unimelb.edu.au";
	String urlPrefix = ControllerConstants.API_PREFIX+ControllerConstants.USER_LABEL;
	String urlPrefix2 = ControllerConstants.API_PREFIX+ControllerConstants.CONTACT_LABEL;
	
	@Before
	public void setup() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		MappingJackson2HttpMessageConverter converter=RestDataFixture.setUpConverter();
		this.mockMvc = standaloneSetup(controller).setMessageConverters(converter).build();
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
	
	private String populateVoteReminderContent(VoteReminderDetails dets)
	{
		return "{\"nodeId\":"+dets.getNodeId().intValue()+",\"userEmail\":\""+dets.getUserId()+
				"\",\"electionId\":"+dets.getElectionId().intValue()+",\"date\":"+dets.getDate().intValue()+
				",\"location\":\""+dets.getLocation()+"\",\"timestamp\":"+dets.getTimestamp().intValue()+
				"}";
	}
	
	private String populateVoteReminderReturnedContent(VoteReminderDetails dets)
	{
		return "{\"nodeId\":"+dets.getNodeId().intValue()+",\"userEmail\":\""+dets.getUserId()+
				"\",\"electionId\":"+dets.getElectionId().intValue()+",\"date\":"+dets.getDate().intValue()+
				",\"location\":\""+dets.getLocation()+"\",\"timestamp\":"+dets.getTimestamp().intValue()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/user/"+dets.getUserId()+"/voteReminder\"}]}";
	}
	
	private String populateVoteRecordContent(VoteRecordDetails dets)
	{
		return "{\"nodeId\":"+dets.getNodeId().intValue()+",\"userEmail\":\""+dets.getVoterId()+
				"\",\"electionId\":"+dets.getElectionId().intValue()+",\"date\":"+dets.getDate().intValue()+
				",\"location\":\""+dets.getLocation()+"\",\"qrCode\":\""+dets.getQrCode()+"\"}";
	}
	
	private String populateVoteRecordReturnedContent(VoteRecordDetails dets)
	{
		return "{\"nodeId\":"+dets.getNodeId().intValue()+",\"userEmail\":\""+dets.getVoterId()+
				"\",\"electionId\":"+dets.getElectionId().intValue()+",\"date\":"+dets.getDate().intValue()+
				",\"location\":\""+dets.getLocation()+"\",\"qrCode\":\""+dets.getQrCode()+
				"\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/user/"+
				dets.getVoterId()+"/voteRecord\"}]}";
	}

	private String populateContent(UserDetails dets)
	{
		return "{\"givenName\":\""+dets.getGivenName()+
				"\",\"familyName\":\""+dets.getFamilyName()+
				"\",\"gender\":\""+dets.getGender()+
				"\",\"nationality\":\""+dets.getNationality()+
				"\",\"yearOfBirth\":\""+dets.getYearOfBirth()+
				"\",\"password\":\""+dets.getPassword()+
				"\",\"contactNumber\":\""+dets.getContactNumber()+
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
				"\",\"contactNumber\":\""+dets.getContactNumber()+
				"\",\"accountVerified\":"+dets.isAccountVerified()+
				",\"hasPersonality\":"+dets.hasPersonality()+
				",\"institutionId\":"+dets.getInstitutionId().intValue()+
				",\"email\":\""+dets.getEmail()+
				"\",\"photos\":"+dets.getPhotos()+
				",\"consentGiven\":"+dets.isConsentGiven()+
				",\"trackingOff\":"+dets.isTrackingOff()+
				",\"optOutDataCollection\":"+dets.isOptOutDataCollection()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/user/"+dets.getEmail()+
				"/\"}]}";
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
		this.mockMvc.perform(put(urlPrefix+"/{email}/personality",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
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
		this.mockMvc.perform(put(urlPrefix+"/{email}/personality",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isFailedDependency());
	}
	
	@Test
	public void putShouldReturnUserBadRequest() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingPersonality()");
		PersonalityDetails dets=new PersonalityDetails(3L,4.0F,3.0F,2.0F,4.0F,5.0F);
		when(userService.addPersonality(any(AddPersonalityEvent.class))).thenReturn(null);
		String content=populatePersonalityContent(dets);
		this.mockMvc.perform(put(urlPrefix+"/{email}/personality",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putShouldReturnUserBadRequest2() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingPersonality()");
		when(userService.addPersonality(any(AddPersonalityEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{email}/personality",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putShouldAddVoteReminderToUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingVoteReminder()");	
		VoteReminderDetails dets=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		VoteReminderAddedEvent resEvt=new VoteReminderAddedEvent();
		resEvt.setDetails(dets);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.addVoteReminder(any(AddVoteReminderEvent.class))).thenReturn(resEvt);
		String content=populateVoteReminderContent(dets);
		String returnedContent=populateVoteReminderReturnedContent(dets);
		this.mockMvc.perform(put(urlPrefix+"/{email}/voteReminder",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.date",is((dets.getDate().intValue()))))
		.andExpect(jsonPath("$.timestamp",is(dets.getTimestamp().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.userEmail",is(dets.getUserId())))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void putVoteReminderShouldReturnUserNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingVoteReminder()");	
		VoteReminderDetails dets=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		VoteReminderAddedEvent resEvt=VoteReminderAddedEvent.userNotFound();
		resEvt.setDetails(dets);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.addVoteReminder(any(AddVoteReminderEvent.class))).thenReturn(resEvt);
		String content=populateVoteReminderContent(dets);
		this.mockMvc.perform(put(urlPrefix+"/{email}/voteReminder",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isFailedDependency());
	}
	
	@Test
	public void putVoteReminderShouldReturnBadRequest() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingVoteReminder()");	
		VoteReminderDetails dets=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		when(userService.addVoteReminder(any(AddVoteReminderEvent.class))).thenReturn(null);
		String content=populateVoteReminderContent(dets);
		this.mockMvc.perform(put(urlPrefix+"/{email}/voteReminder",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putVoteReminderShouldReturnUserBadRequest2() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingVoteReminder()");	
		when(userService.addVoteReminder(any(AddVoteReminderEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{email}/voteReminder",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putShouldAddVoteRecordToUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingVoteRecord()");	
		VoteRecordDetails dets=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		VoteRecordAddedEvent resEvt=new VoteRecordAddedEvent();
		resEvt.setDetails(dets);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.addVoteRecord(any(AddVoteRecordEvent.class))).thenReturn(resEvt);
		String content=populateVoteRecordContent(dets);
		String returnedContent=populateVoteRecordReturnedContent(dets);
		this.mockMvc.perform(put(urlPrefix+"/{email}/voteRecord",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.date",is((dets.getDate().intValue()))))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.userEmail",is(dets.getVoterId())))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void putVoteRecordShouldReturnUserNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingVoteRecord()");	
		VoteRecordDetails dets=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		VoteRecordAddedEvent resEvt=VoteRecordAddedEvent.userNotFound();
		resEvt.setDetails(dets);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.addVoteRecord(any(AddVoteRecordEvent.class))).thenReturn(resEvt);
		String content="{\"nodeId\":"+dets.getNodeId().intValue()+",\"userEmail\":\""+dets.getVoterId()+
						"\",\"electionId\":"+dets.getElectionId().intValue()+",\"date\":"+dets.getDate().intValue()+
						",\"location\":\""+dets.getLocation()+"\"}";
		this.mockMvc.perform(put(urlPrefix+"/{email}/voteRecord",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isFailedDependency());
	}
	
	@Test
	public void putVoteRecordShouldReturnBadRequest() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingVoteRecord()");	
		VoteRecordDetails dets=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		when(userService.addVoteRecord(any(AddVoteRecordEvent.class))).thenReturn(null);
		String content=populateVoteRecordContent(dets);
		this.mockMvc.perform(put(urlPrefix+"/{email}/voteRecord",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putVoteRecordShouldReturnUserBadRequest2() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingVoteRecord()");	
		when(userService.addVoteRecord(any(AddVoteRecordEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{email}/voteRecord",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getShouldReadVoteRecordCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("readingVoteRecord()");	
		VoteRecordDetails dets=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		Long id=1l;
		VoteRecordReadEvent resEvt=new VoteRecordReadEvent(id, dets);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.readVoteRecord(any(ReadVoteRecordEvent.class))).thenReturn(resEvt);
		String content=populateVoteRecordContent(dets);
		String returnedContent=populateVoteRecordReturnedContent(dets);
		this.mockMvc.perform(get(urlPrefix+"/voteRecord/{id}",id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.date",is((dets.getDate().intValue()))))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.userEmail",is(dets.getVoterId())))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getShouldReadVoteReminderCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("readingVoteReminder()");	
		VoteReminderDetails dets=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		Long id=1l;
		VoteReminderReadEvent resEvt=new VoteReminderReadEvent(id, dets);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.readVoteReminder(any(ReadVoteReminderEvent.class))).thenReturn(resEvt);
		String content=populateVoteReminderContent(dets);
		String returnedContent=populateVoteReminderReturnedContent(dets);
		this.mockMvc.perform(get(urlPrefix+"/voteReminder/{id}",id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.date",is((dets.getDate().intValue()))))
		.andExpect(jsonPath("$.timestamp",is(dets.getTimestamp().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.userEmail",is(dets.getUserId())))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteShouldDeleteVoteRecordCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("deletingVoteRecord()");	
		Long id=1l;
		VoteRecordDeletedEvent resEvt=new VoteRecordDeletedEvent(id);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.deleteVoteRecord(any(DeleteVoteRecordEvent.class))).thenReturn(resEvt);
		this.mockMvc.perform(delete(urlPrefix+"/voteRecord/{id}",id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(id.intValue())))
/*		.andExpect(jsonPath("$.date",is((dets.getDate().intValue()))))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.userEmail",is(dets.getVoterId())))
*/		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteShouldDeleteVoteReminderCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("deletingVoteReminder()");	
		Long id=1l;
		VoteReminderDeletedEvent resEvt=new VoteReminderDeletedEvent(id);
		if (LOG.isDebugEnabled()) LOG.debug("resEvent - "+resEvt);
		when(userService.deleteVoteReminder(any(DeleteVoteReminderEvent.class))).thenReturn(resEvt);
		this.mockMvc.perform(delete(urlPrefix+"/voteReminder/{id}",id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(id.intValue())))
/*		.andExpect(jsonPath("$.date",is((dets.getDate().intValue()))))
		.andExpect(jsonPath("$.timestamp",is(dets.getTimestamp().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.userEmail",is(dets.getUserId())))
		.andExpect(content().string(returnedContent))
*/		.andExpect(status().isOk());
	}
	
	@Test
	public void getShouldReturnUserCorrectlyFromRead() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		ReadUserEvent testData=RestDataFixture.customEmailUser2(email);
		UserDetails dets=(UserDetails) testData.getDetails();
		when (userService.readUser(any(RequestReadUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.trackingOff",is(dets.isTrackingOff())))
		.andExpect(jsonPath("$.optOutDataCollection",is(dets.isOptOutDataCollection())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void testFindUserWithUserId() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		User user=DatabaseDataFixture.populateUserGnewitt();
		UserDetails dets=user.toUserDetails();
		ReadUserEvent testData=new ReadUserEvent(dets.getEmail(), dets);
		Long id=user.getNodeId();
		when (userService.readUserById(any(RequestReadUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{id}/",id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void testFindContactWithEmail() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		User user=DatabaseDataFixture.populateUserGnewitt();
		UserDetails dets=user.toUserDetails();
		String email=dets.getEmail();
		ReadUserEvent testData=new ReadUserEvent(email, dets);
		when (userService.readUserByContactEmail(any(RequestReadUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix2+"/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void testFindContactWithNumber() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("findingContact()");
		User user=DatabaseDataFixture.populateUserGnewitt();
		UserDetails dets=user.toUserDetails();
		String contactNumber=dets.getContactNumber();
		ReadUserEvent testData=new ReadUserEvent(email, dets);
		when (userService.readUserByContactNumber((any(RequestReadUserEvent.class)))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix2+"/{contactNumber}/",contactNumber).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void getShouldReturnUserNotFoundFromRead() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		when (userService.readUser(any(RequestReadUserEvent.class))).thenReturn(ReadUserEvent.notFound(email2));
		this.mockMvc.perform(get(urlPrefix+"/{email}/",email2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void putShouldReturnUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdate()");
		ReadUserEvent readData=RestDataFixture.customEmailUser2(email);
		UserDetails dets=(UserDetails) readData.getDetails();
		UserUpdatedEvent testData=new UserUpdatedEvent(email, dets);
		String content=populateContent(dets);
		String returnedContent=populateReturnedContent(dets);
		when (userService.updateUser(any(UpdateUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.trackingOff",is(dets.isTrackingOff())))
		.andExpect(jsonPath("$.optOutDataCollection",is(dets.isOptOutDataCollection())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
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
		this.mockMvc.perform(put(urlPrefix+"/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isFailedDependency())	;
	}
	
	@Test
	public void postShouldReturnUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreate()");
		UserCreatedEvent testData=RestEventFixtures.populateUserCreatedEvent();
		UserDetails dets=(UserDetails) testData.getDetails();
		String content=populateContent(dets);
		String returnedContent=populateReturnedContent(dets);
		when (userService.signUpNewUser(any(CreateUserEvent.class))).thenReturn(testData);
		doNothing().when(emailService).sendEmail(any(EmailVerification.class));
		this.mockMvc.perform(post("/api/signUp/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
		.andExpect(jsonPath("$.accountVerified",is(dets.isAccountVerified())))
		.andExpect(jsonPath("$.trackingOff",is(dets.isTrackingOff())))
		.andExpect(jsonPath("$.optOutDataCollection",is(dets.isOptOutDataCollection())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.email",is(dets.getEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void postShouldReturnBadRequestNoEmptyContent() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		String content="{}";
		this.mockMvc.perform(post("/api/signUp/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void postShouldReturnBadRequestNullEmail() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		String content="{\"email\":null}";
		this.mockMvc.perform(post("/api/signUp/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content)) 
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void postShouldReturnBadRequestNoContent() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		UserCreatedEvent testData=UserCreatedEvent.userNotUnique(email);
		when (userService.signUpNewUser(any(CreateUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/signUp/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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
		.andExpect(status().isConflict())	;
	}
	
	@Test
	public void deleteShouldReturnUserCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDelete()");
		ReadUserEvent readData=RestDataFixture.customEmailUser2(email);
		UserDetails dets=(UserDetails) readData.getDetails();
		UserDeletedEvent testData=new UserDeletedEvent(email, dets);
		when (userService.deleteUser(any(DeleteUserEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{email}/",email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))		
		.andExpect(jsonPath("$.givenName",is(dets.getGivenName())))
		.andExpect(jsonPath("$.familyName",is(dets.getFamilyName())))
		.andExpect(jsonPath("$.gender",is(dets.getGender())))
		.andExpect(jsonPath("$.nationality",is(dets.getNationality())))
		.andExpect(jsonPath("$.yearOfBirth",is(dets.getYearOfBirth())))
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
		this.mockMvc.perform(delete(urlPrefix+"/{email}/",email2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
}
