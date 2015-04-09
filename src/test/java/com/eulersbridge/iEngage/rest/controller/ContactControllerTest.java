/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestReadEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.services.ContactRequestService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class ContactControllerTest
{
    MockMvc mockMvc;
	
	@InjectMocks
	ContactController controller;
	
	@Mock
	UserService userService;
	
	@Mock
	ContactRequestService contactRequestService;
	
    private static Logger LOG = LoggerFactory.getLogger(ContactControllerTest.class);
    
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ContactController#addContact(java.lang.Long, java.lang.String)}.
	 */
	@Test
	public void testAddContactWithEmail() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingContact()");
		User friendor=DatabaseDataFixture.populateUserGnewitt();
		User friendee=DatabaseDataFixture.populateUserGnewitt2();
		Long userId=friendor.getNodeId();
		String contactInfo=friendee.getEmail();
		ReadUserEvent value=new ReadUserEvent(friendee.getEmail(), friendee.toUserDetails());
		when(userService.readUserByContactEmail(any(RequestReadUserEvent.class))).thenReturn(value);

		ContactRequest crd=DatabaseDataFixture.populateContactRequest1();
		ReadEvent value1=new ReadEvent(crd.getNodeId(), crd.toContactRequestDetails());
		when(contactRequestService.readContactRequestByUserIdContactNumber(any(ReadContactRequestEvent.class))).thenReturn(value1);

		this.mockMvc.perform(put(urlPrefix+"/{userId}"+ControllerConstants.CONTACT_LABEL+"/{contactInfo}/",userId,contactInfo).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(crd.getNodeId().intValue())))
		.andExpect(jsonPath("$.contactDetails",is(crd.getContactDetails())))
		.andExpect(jsonPath("$.requestDate",is(crd.getRequestDate())))
		.andExpect(jsonPath("$.responseDate",is(crd.getResponseDate())))
		.andExpect(jsonPath("$.accepted",is(crd.getAccepted())))
		.andExpect(jsonPath("$.rejected",is(crd.getRejected())))
		.andExpect(jsonPath("$.userId",is(crd.getUser().getNodeId().intValue())))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void testAddContactWithPhoneNumber() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("addingContact()");
		User friendor=DatabaseDataFixture.populateUserGnewitt();
		User friendee=DatabaseDataFixture.populateUserGnewitt2();
		Long userId=friendor.getNodeId();
		String contactInfo=friendee.getContactNumber();
		ReadUserEvent value=new ReadUserEvent(friendee.getContactNumber(), friendee.toUserDetails());
		when(userService.readUserByContactNumber(any(RequestReadUserEvent.class))).thenReturn(value);

		ContactRequest crd=DatabaseDataFixture.populateContactRequest1();
		crd.setContactDetails(contactInfo);
		ReadEvent value1=new ReadEvent(crd.getNodeId(), crd.toContactRequestDetails());
		when(contactRequestService.readContactRequestByUserIdContactNumber(any(ReadContactRequestEvent.class))).thenReturn(value1);

		this.mockMvc.perform(put(urlPrefix+"/{userId}"+ControllerConstants.CONTACT_LABEL+"/{contactInfo}/",userId,contactInfo).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(crd.getNodeId().intValue())))
		.andExpect(jsonPath("$.contactDetails",is(crd.getContactDetails())))
		.andExpect(jsonPath("$.requestDate",is(crd.getRequestDate())))
		.andExpect(jsonPath("$.responseDate",is(crd.getResponseDate())))
		.andExpect(jsonPath("$.accepted",is(crd.getAccepted())))
		.andExpect(jsonPath("$.rejected",is(crd.getRejected())))
		.andExpect(jsonPath("$.userId",is(crd.getUser().getNodeId().intValue())))
		.andExpect(status().isAccepted());
	}
	

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ContactController#acceptContact(java.lang.Long)}.
	 */
	@Test
	public final void testAcceptContact() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("acceptingContact()");
		ContactRequest cr = DatabaseDataFixture.populateContactRequest1();
		Long timeStamp=Calendar.getInstance().getTimeInMillis();
		ContactDetails cDets = new ContactDetails(453l, cr.getUser().getNodeId(), 432l, timeStamp);
		Long contactRequestId=cr.getNodeId();
		ContactRequestDetails crDets = cr.toContactRequestDetails();
		
		ReadEvent value=new ContactRequestReadEvent(contactRequestId,crDets);
		when(contactRequestService.readContactRequest(any(ReadContactRequestEvent.class))).thenReturn(value);
		UpdatedEvent updEvt=new UpdatedEvent(crDets.getNodeId(),cDets);
		when(contactRequestService.acceptContactRequest(any(AcceptContactRequestEvent.class))).thenReturn(updEvt);
		this.mockMvc.perform(put(urlPrefix2+"/{contactRequestId}/",contactRequestId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(cDets.getNodeId().intValue())))
		.andExpect(jsonPath("$.contactorId",is(cDets.getContactorId().intValue())))
		.andExpect(jsonPath("$.contacteeId",is(cDets.getContacteeId().intValue())))
		.andExpect(jsonPath("$.timestamp",is(cDets.getTimestamp())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isCreated())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ContactController#rejectContact(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testRejectContact() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("rejectingContact()");
		ContactRequest cr = DatabaseDataFixture.populateContactRequest1();
		Long timeStamp=Calendar.getInstance().getTimeInMillis();
		ContactDetails cDets = new ContactDetails(453l, cr.getUser().getNodeId(), 432l, timeStamp);
		Long contactRequestId=cr.getNodeId();
		ContactRequestDetails crDets = cr.toContactRequestDetails();
		
		ReadEvent value=new ContactRequestReadEvent(contactRequestId,crDets);
		when(contactRequestService.readContactRequest(any(ReadContactRequestEvent.class))).thenReturn(value);
		UpdatedEvent updEvt=new UpdatedEvent(crDets.getNodeId(),crDets);
		when(contactRequestService.rejectContactRequest(any(UpdateEvent.class))).thenReturn(updEvt);
		this.mockMvc.perform(delete(urlPrefix2+"/{contactRequestId}/",contactRequestId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(crDets.getNodeId().intValue())))
		.andExpect(jsonPath("$.requestDate",is(crDets.getRequestDate().longValue())))
		.andExpect(jsonPath("$.responseDate",is(crDets.getResponseDate().longValue())))
		.andExpect(jsonPath("$.contactDetails",is(crDets.getContactDetails())))
		.andExpect(jsonPath("$.accepted",is(crDets.getAccepted())))
		.andExpect(jsonPath("$.rejected",is(crDets.getRejected())))
		.andExpect(jsonPath("$.userId",is(crDets.getUserId().intValue())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ContactController#findContactRequestsReceived(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindContactRequestsReceived() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindContactRequestsReceived()");
		Long userId=1l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.ContactRequest> dets=DatabaseDataFixture.populateContactRequests();
		Iterable<com.eulersbridge.iEngage.database.domain.ContactRequest> contactRequests=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.ContactRequest> iter=contactRequests.iterator();
		ArrayList<ContactRequestDetails> contactRequestDets=new ArrayList<ContactRequestDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.ContactRequest article=iter.next();
			contactRequestDets.add(article.toContactRequestDetails());
		}
		AllReadEvent testData=new AllReadEvent(userId,contactRequestDets);
		when (contactRequestService.readContactRequestsReceived(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix3+"/{parentId}/",userId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$[0].nodeId",is(contactRequestDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$[0].contactDetails",is(contactRequestDets.get(0).getContactDetails())))
		.andExpect(jsonPath("$[0].userId",is(contactRequestDets.get(0).getUserId().intValue())))
		.andExpect(jsonPath("$[0].accepted",is(contactRequestDets.get(0).getAccepted())))
		.andExpect(jsonPath("$[0].rejected",is(contactRequestDets.get(0).getRejected())))
		.andExpect(jsonPath("$[0].requestDate",is(contactRequestDets.get(0).getRequestDate().longValue())))
		.andExpect(jsonPath("$[0].responseDate",is(contactRequestDets.get(0).getResponseDate().longValue())))
//		.andExpect(jsonPath("$[0].links[0].rel",is("self")))
		.andExpect(jsonPath("$[1].nodeId",is(contactRequestDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$[1].contactDetails",is(contactRequestDets.get(1).getContactDetails())))
		.andExpect(jsonPath("$[1].userId",is(contactRequestDets.get(1).getUserId().intValue())))
		.andExpect(jsonPath("$[1].accepted",is(contactRequestDets.get(1).getAccepted())))
		.andExpect(jsonPath("$[1].rejected",is(contactRequestDets.get(1).getRejected())))
		.andExpect(jsonPath("$[1].requestDate",is(contactRequestDets.get(1).getRequestDate().longValue())))
		.andExpect(jsonPath("$[1].responseDate",is(contactRequestDets.get(1).getResponseDate().longValue())))
//		.andExpect(jsonPath("$[1].links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ContactController#findContactRequestsMade(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindContactRequestsMade() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindContactRequestsMade()");
		Long userId=1l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.ContactRequest> dets=DatabaseDataFixture.populateContactRequests();
		Iterable<com.eulersbridge.iEngage.database.domain.ContactRequest> contactRequests=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.ContactRequest> iter=contactRequests.iterator();
		ArrayList<ContactRequestDetails> contactRequestDets=new ArrayList<ContactRequestDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.ContactRequest article=iter.next();
			contactRequestDets.add(article.toContactRequestDetails());
		}
		AllReadEvent testData=new AllReadEvent(userId,contactRequestDets);
		when (contactRequestService.readContactRequestsMade(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{parentId}/"+ControllerConstants.CONTACT_REQUESTS_LABEL,userId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$[0].nodeId",is(contactRequestDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$[0].contactDetails",is(contactRequestDets.get(0).getContactDetails())))
		.andExpect(jsonPath("$[0].userId",is(contactRequestDets.get(0).getUserId().intValue())))
		.andExpect(jsonPath("$[0].accepted",is(contactRequestDets.get(0).getAccepted())))
		.andExpect(jsonPath("$[0].rejected",is(contactRequestDets.get(0).getRejected())))
		.andExpect(jsonPath("$[0].requestDate",is(contactRequestDets.get(0).getRequestDate().longValue())))
		.andExpect(jsonPath("$[0].responseDate",is(contactRequestDets.get(0).getResponseDate().longValue())))
//		.andExpect(jsonPath("$[0].links[0].rel",is("self")))
		.andExpect(jsonPath("$[1].nodeId",is(contactRequestDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$[1].contactDetails",is(contactRequestDets.get(1).getContactDetails())))
		.andExpect(jsonPath("$[1].userId",is(contactRequestDets.get(1).getUserId().intValue())))
		.andExpect(jsonPath("$[1].accepted",is(contactRequestDets.get(1).getAccepted())))
		.andExpect(jsonPath("$[1].rejected",is(contactRequestDets.get(1).getRejected())))
		.andExpect(jsonPath("$[1].requestDate",is(contactRequestDets.get(1).getRequestDate().longValue())))
		.andExpect(jsonPath("$[1].responseDate",is(contactRequestDets.get(1).getResponseDate().longValue())))
//		.andExpect(jsonPath("$[1].links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public void testAcceptContactAcceptNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("acceptingContact()");
		ContactRequest cr = DatabaseDataFixture.populateContactRequest1();
		Long contactRequestId=cr.getNodeId();
		ContactRequestDetails crDets = cr.toContactRequestDetails();
		
		ReadEvent value=new ContactRequestReadEvent(contactRequestId,crDets);
		when(contactRequestService.readContactRequest(any(ReadContactRequestEvent.class))).thenReturn(value);
		UpdatedEvent updEvt=UpdatedEvent.notFound(crDets.getNodeId());
		when(contactRequestService.acceptContactRequest(any(AcceptContactRequestEvent.class))).thenReturn(updEvt);
		this.mockMvc.perform(put(urlPrefix2+"/{contactRequestId}/",contactRequestId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}
	
	@Test
	public void testAcceptContactCRNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("acceptingContact()");
		ContactRequest cr = DatabaseDataFixture.populateContactRequest1();
		Long contactRequestId=cr.getNodeId();
		
		ReadEvent value=ContactRequestReadEvent.notFound(contactRequestId);
		when(contactRequestService.readContactRequest(any(ReadContactRequestEvent.class))).thenReturn(value);
		this.mockMvc.perform(put(urlPrefix2+"/{contactRequestId}/",contactRequestId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}
	

}
