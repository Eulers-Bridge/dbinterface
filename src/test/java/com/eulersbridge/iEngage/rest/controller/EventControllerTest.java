/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.rest.domain.LikeInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.events.CreateEventEvent;
import com.eulersbridge.iEngage.core.events.events.DeleteEventEvent;
import com.eulersbridge.iEngage.core.events.events.EventCreatedEvent;
import com.eulersbridge.iEngage.core.events.events.EventDeletedEvent;
import com.eulersbridge.iEngage.core.events.events.EventDetails;
import com.eulersbridge.iEngage.core.events.events.EventUpdatedEvent;
import com.eulersbridge.iEngage.core.events.events.ReadEventEvent;
import com.eulersbridge.iEngage.core.events.events.RequestReadEventEvent;
import com.eulersbridge.iEngage.core.events.events.UpdateEventEvent;
import com.eulersbridge.iEngage.core.services.EventService;
import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Greg Newitt
 *
 */
public class EventControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(EventControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.EVENT_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	EventController controller;
	
	@Mock
	EventService eventService;
	@Mock
	InstitutionService instService;
    @Mock
    LikesService likesService;
	

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
	
	String setupContent(EventDetails dets)
	{
		int evtId=dets.getEventId().intValue();
		return "{\"eventId\":"+evtId+",\"name\":\""+dets.getName()+
				"\",\"location\":\""+dets.getLocation()+"\",\"starts\":"+dets.getStarts().intValue()+",\"ends\":"+dets.getEnds().intValue()+
				",\"description\":\""+dets.getDescription()+"\",\"picture\":"+dets.getPicture()+",\"volunteerPositions\":"+dets.getVolunteerPositions()+
				",\"created\":"+dets.getCreated()+",\"organizer\":\""+dets.getOrganizer()+"\",\"organizerEmail\":\""+dets.getOrganizerEmail()+
				"\",\"institutionId\":"+dets.getInstitutionId()+",\"modified\":"+dets.getModified()+'}';
	}
	
	String setupInvalidContent(EventDetails dets)
	{
		int evtId=dets.getEventId().intValue();
		return "{\"eventId1\":"+evtId+",\"name\":\""+dets.getName()+
				"\",\"location\":\""+dets.getLocation()+"\",\"starts\":"+dets.getStarts().intValue()+",\"ends\":"+dets.getEnds().intValue()+
				",\"description\":\""+dets.getDescription()+"\",\"picture\":"+dets.getPicture()+",\"volunteerPositions\":"+dets.getVolunteerPositions()+
				",\"created\":"+dets.getCreated()+",\"organizer\":\""+dets.getOrganizer()+"\",\"organizerEmail\":\""+dets.getOrganizerEmail()+
				"\",\"institutionId\":"+dets.getInstitutionId()+",\"modified\":"+dets.getModified()+'}';
	}
	
	String setupReturnedContent(EventDetails dets)
	{
		int evtId=dets.getEventId().intValue();
		return "{\"eventId\":"+evtId+",\"name\":\""+dets.getName()+
				"\",\"location\":\""+dets.getLocation()+"\",\"starts\":"+dets.getStarts().intValue()+",\"ends\":"+dets.getEnds().intValue()+
				",\"description\":\""+dets.getDescription()+"\",\"picture\":"+dets.getPicture()+",\"volunteerPositions\":"+dets.getVolunteerPositions()+
				",\"created\":"+dets.getCreated()+",\"organizer\":\""+dets.getOrganizer()+"\",\"organizerEmail\":\""+dets.getOrganizerEmail()+
				"\",\"institutionId\":"+dets.getInstitutionId()+",\"modified\":"+dets.getModified()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/event/"+evtId+"\"},"+
				"{\"rel\":\"Previous\",\"href\":\"http://localhost/api/event/"+evtId+"/previous\"},"+
				"{\"rel\":\"Next\",\"href\":\"http://localhost/api/event/"+evtId+"/next\"},"+
				"{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/event/"+evtId+"/likedBy/USERID\"},"+
				"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/event/"+evtId+"/unlikedBy/USERID\"},"+
				"{\"rel\":\"Likes\",\"href\":\"http://localhost/api/event/"+evtId+"/likes\"},"+
				"{\"rel\":\"Read all\",\"href\":\"http://localhost/api/events\"}]}";	
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#EventController()}.
	 */
	@Test
	public final void testEventController()
	{
		EventController eventController=new EventController();
		assertNotNull("Not yet implemented",eventController);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#createEvent(com.eulersbridge.iEngage.rest.domain.Event)}.
	 */
	@Test
	public final void testCreateEvent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		EventCreatedEvent testData=new EventCreatedEvent(dets.getEventId(), dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);		
		
		when (eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.eventId",is(dets.getEventId().intValue())))
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.starts",is(dets.getStarts().intValue())))
		.andExpect(jsonPath("$.ends",is(dets.getEnds().intValue())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.picture",is(dets.getPicture())))
		.andExpect(jsonPath("$.volunteerPositions",is(dets.getVolunteerPositions())))
		.andExpect(jsonPath("$.created",is(dets.getCreated().intValue())))
		.andExpect(jsonPath("$.modified",is(dets.getModified().intValue())))
		.andExpect(jsonPath("$.organizer",is(dets.getOrganizer())))
		.andExpect(jsonPath("$.organizerEmail",is(dets.getOrganizerEmail())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreateEventInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		EventCreatedEvent testData=null;
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		String content=setupInvalidContent(dets);
		when (eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateEventNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		EventCreatedEvent testData=null;
		when (eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateEventNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		EventCreatedEvent testData=EventCreatedEvent.institutionNotFound(dets.getInstitutionId());
		String content=setupContent(dets);
		when (eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCreateEventNullIdReturned() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		EventCreatedEvent testData=new EventCreatedEvent(null, dets);
		String content=setupContent(dets);
		when (eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#findEvent(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindEvent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindElection()");
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		ReadEventEvent testData=new ReadEventEvent(dets.getEventId(),dets);
		when (eventService.readEvent(any(RequestReadEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{eventId}/",dets.getEventId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.eventId",is(dets.getEventId().intValue())))
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.starts",is(dets.getStarts().intValue())))
		.andExpect(jsonPath("$.ends",is(dets.getEnds().intValue())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.picture",is(dets.getPicture())))
		.andExpect(jsonPath("$.volunteerPositions",is(dets.getVolunteerPositions())))
		.andExpect(jsonPath("$.created",is(dets.getCreated().intValue())))
		.andExpect(jsonPath("$.modified",is(dets.getModified().intValue())))
		.andExpect(jsonPath("$.organizer",is(dets.getOrganizer())))
		.andExpect(jsonPath("$.organizerEmail",is(dets.getOrganizerEmail())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindEventNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindElection()");
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		ReadEvent testData=ReadEventEvent.notFound(dets.getEventId());
		when (eventService.readEvent(any(RequestReadEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{electionId}/",dets.getEventId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#updateEvent(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Event)}.
	 */
	@Test
	public final void testUpdateEvent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
		Long id=1L;
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		dets.setName("Test EVENT2");
		EventUpdatedEvent testData=new EventUpdatedEvent(id, dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);

		when (eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.eventId",is(dets.getEventId().intValue())))
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.location",is(dets.getLocation())))
		.andExpect(jsonPath("$.starts",is(dets.getStarts().intValue())))
		.andExpect(jsonPath("$.ends",is(dets.getEnds().intValue())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.picture",is(dets.getPicture())))
		.andExpect(jsonPath("$.volunteerPositions",is(dets.getVolunteerPositions())))
		.andExpect(jsonPath("$.created",is(dets.getCreated().intValue())))
		.andExpect(jsonPath("$.modified",is(dets.getModified().intValue())))
		.andExpect(jsonPath("$.organizer",is(dets.getOrganizer())))
		.andExpect(jsonPath("$.organizerEmail",is(dets.getOrganizerEmail())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testUpdateElectionNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
		Long id=1L;
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		String content=setupContent(dets);
		when (eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateElectionBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
		Long id=1L;
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		EventUpdatedEvent testData=new EventUpdatedEvent(id, dets);
		String content=setupInvalidContent(dets);
		when (eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateElectionEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
		Long id=1L;
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		EventUpdatedEvent testData=new EventUpdatedEvent(id, dets);
		when (eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateElectionNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
		Long id=1L;
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		String content=setupContent(dets);
		when (eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(EventUpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#deleteEvent(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteEvent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteEvent()");
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		EventDeletedEvent testData=new EventDeletedEvent(dets.getEventId());
		when (eventService.deleteEvent(any(DeleteEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{eventId}/",dets.getEventId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}
	@Test
	public final void testDeleteEventNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteEvent()");
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		DeletedEvent testData=EventDeletedEvent.notFound(dets.getEventId());
		when (eventService.deleteEvent(any(DeleteEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{eventId}/",dets.getEventId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteEventForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteEvent()");
		EventDetails dets=DatabaseDataFixture.populateEvent1().toEventDetails();
		DeletedEvent testData=EventDeletedEvent.deletionForbidden(dets.getEventId());
		when (eventService.deleteEvent(any(DeleteEventEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{eventId}/",dets.getEventId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#likedByEvent(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Event)}.
	 */
	@Test
	public final void testLikedByEvent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=new LikedEvent(id, user.getEmail(), true);
		
		when (eventService.likeEvent(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;		
	}
	
	@Test
	public final void testLikedByEventNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());
		
		when (eventService.likeEvent(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testLikedByEventGone() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());
		
		when (eventService.likeEvent(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;		
	}

    @Test
    public final void testUnlikedBy() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt= new LikedEvent(id, user.getEmail(), true);

        when (eventService.unlikeEvent(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isOk())	;
    }

    @Test
    public final void testUnLikedByEventNotFound() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());

        when (eventService.unlikeEvent(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())	;
    }

    @Test
    public final void testUnLikedByEventGone() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());

        when (eventService.unlikeEvent(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isGone())	;
    }

    @Test
    public final void testFindLikes() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindLikes()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        Collection<UserDetails> userDetails = new ArrayList<>();
        userDetails.add(user.toUserDetails());

        LikeableObjectLikesEvent likeableObjectLikesEvent = new LikeableObjectLikesEvent(id, userDetails);


        when (likesService.likes(any(LikesLikeableObjectEvent.class), any(Sort.Direction.class), any(int.class), any(int.class))).thenReturn(likeableObjectLikesEvent);
        this.mockMvc.perform(get(urlPrefix+"/{id}/likes/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())	;
    }
    
    @Test
    public final void testFindLikesNotFound() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindLikes()");
        Long id=1L;
        Collection<UserDetails> userDetails = new ArrayList<>();
        ReadEvent readPollEvent = ReadEvent.notFound(id);

        LikeableObjectLikesEvent likeableObjectLikesEvent = new LikeableObjectLikesEvent(id, userDetails);


        when (likesService.likes(any(LikesLikeableObjectEvent.class), any(Sort.Direction.class), any(int.class), any(int.class))).thenReturn(likeableObjectLikesEvent);
        when (eventService.readEvent(any(RequestReadEventEvent.class))).thenReturn(readPollEvent);
        this.mockMvc.perform(get(urlPrefix+"/{id}/likes/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
