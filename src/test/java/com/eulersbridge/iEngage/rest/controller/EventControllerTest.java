/**
 *
 */
package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.EventService;
import com.eulersbridge.iEngage.core.services.interfacePack.InstitutionService;
import com.eulersbridge.iEngage.core.services.interfacePack.LikesService;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Greg Newitt
 */
public class EventControllerTest {
  private static Logger LOG = LoggerFactory.getLogger(EventControllerTest.class);

  private String urlPrefix = ControllerConstants.API_PREFIX + ControllerConstants.EVENT_LABEL;

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
  public void setUp() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("setup()");
    MockitoAnnotations.initMocks(this);

    MappingJackson2HttpMessageConverter converter = RestDataFixture.setUpConverter();
    this.mockMvc = standaloneSetup(controller).setMessageConverters(converter).build();
//		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
  }

  String setupContent(EventDetails dets) {
    int evtId = dets.getEventId().intValue();
    return "{\"eventId\":" + evtId + ",\"name\":\"" + dets.getName() +
      "\",\"location\":\"" + dets.getLocation() + "\",\"starts\":" + dets.getStarts().intValue() + ",\"ends\":" + dets.getEnds().intValue() +
      ",\"description\":\"" + dets.getDescription() + "\",\"volunteerPositions\":" + dets.getVolunteerPositions() +
      ",\"created\":" + dets.getCreated() + ",\"organizer\":\"" + dets.getOrganizer() + "\",\"organizerEmail\":\"" + dets.getOrganizerEmail() +
      "\",\"institutionId\":" + dets.getInstitutionId() + ",\"modified\":" + dets.getModified() + '}';
  }

  String setupInvalidContent(EventDetails dets) {
    int evtId = dets.getEventId().intValue();
    return "{\"eventId1\":" + evtId + ",\"name\":\"" + dets.getName() +
      "\",\"location\":\"" + dets.getLocation() + "\",\"starts\":" + dets.getStarts().intValue() + ",\"ends\":" + dets.getEnds().intValue() +
      ",\"description\":\"" + dets.getDescription() + "\",\"volunteerPositions\":" + dets.getVolunteerPositions() +
      ",\"created\":" + dets.getCreated() + ",\"organizer\":\"" + dets.getOrganizer() + "\",\"organizerEmail\":\"" + dets.getOrganizerEmail() +
      "\",\"institutionId\":" + dets.getInstitutionId() + ",\"modified\":" + dets.getModified() + '}';
  }

  String setupReturnedContent(EventDetails dets) {
    int evtId = dets.getEventId().intValue();
    StringBuffer returnedContent = new StringBuffer("{\"eventId\":" + evtId + ",\"name\":\"" + dets.getName() +
      "\",\"location\":\"" + dets.getLocation() + "\",\"starts\":" +
      dets.getStarts().intValue() + ",\"ends\":" + dets.getEnds().intValue() +
      ",\"description\":\"" + dets.getDescription());
    returnedContent.append("\",\"photos\":");
//    returnedContent.append(NewsControllerTest.createPhotosString(dets.getPhotos().iterator()));

    returnedContent.append(",\"volunteerPositions\":" + dets.getVolunteerPositions() + ",\"created\":" + dets.getCreated() +
      ",\"organizer\":\"" + dets.getOrganizer() + "\",\"organizerEmail\":\"" + dets.getOrganizerEmail() +
      "\",\"institutionId\":" + dets.getInstitutionId() + ",\"modified\":" + dets.getModified() +
      ",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/event/" + evtId + "\"}," +
      "{\"rel\":\"Previous\",\"href\":\"http://localhost/api/event/" + evtId + "/previous\"}," +
      "{\"rel\":\"Next\",\"href\":\"http://localhost/api/event/" + evtId + "/next\"}," +
      "{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/event/" + evtId + "/likedBy/USERID\"}," +
      "{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/event/" + evtId + "/unlikedBy/USERID\"}," +
      "{\"rel\":\"Likes\",\"href\":\"http://localhost/api/event/" + evtId + "/likes\"}," +
      "{\"rel\":\"Read all\",\"href\":\"http://localhost/api/events\"}]}");
    return returnedContent.toString();
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#EventController()}.
   */
  @Test
  public final void testEventController() {
    EventController eventController = new EventController();
    assertNotNull("Not yet implemented", eventController);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#createEvent(com.eulersbridge.iEngage.rest.domain.Event)}.
   */
  @Test
  public final void testCreateEvent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    EventCreatedEvent testData = new EventCreatedEvent(dets.getEventId(), dets);
    String content = setupContent(dets);
    String returnedContent = setupReturnedContent(dets);

    when(eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
      .andDo(print())
      .andExpect(jsonPath("$.eventId", is(dets.getEventId().intValue())))
      .andExpect(jsonPath("$.name", is(dets.getName())))
      .andExpect(jsonPath("$.location", is(dets.getLocation())))
      .andExpect(jsonPath("$.starts", is(dets.getStarts().intValue())))
      .andExpect(jsonPath("$.ends", is(dets.getEnds().intValue())))
      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//		.andExpect(jsonPath("$.picture",is(dets.getPhotos())))
      .andExpect(jsonPath("$.volunteerPositions", is(dets.getVolunteerPositions())))
      .andExpect(jsonPath("$.created", is(dets.getCreated().intValue())))
      .andExpect(jsonPath("$.modified", is(dets.getModified().intValue())))
      .andExpect(jsonPath("$.organizer", is(dets.getOrganizer())))
      .andExpect(jsonPath("$.organizerEmail", is(dets.getOrganizerEmail())))
      .andExpect(jsonPath("$.institutionId", is(dets.getInstitutionId().intValue())))
      .andExpect(jsonPath("$.links[0].rel", is("self")))
      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
      .andExpect(jsonPath("$.links[2].rel", is("Next")))
      .andExpect(jsonPath("$.links[3].rel", is("Liked By")))
      .andExpect(jsonPath("$.links[4].rel", is("UnLiked By")))
      .andExpect(jsonPath("$.links[5].rel", is("Likes")))
      .andExpect(jsonPath("$.links[6].rel", is("Read all")))
//		.andExpect(content().string(returnedContent))
      .andExpect(status().isCreated());
  }

  @Test
  public final void testCreateEventInvalidContent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
    EventCreatedEvent testData = null;
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    String content = setupInvalidContent(dets);
    when(eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  @Test
  public final void testCreateEventNoContent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
    EventCreatedEvent testData = null;
    when(eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  @Test
  public final void testCreateEventNotFound() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    EventCreatedEvent testData = EventCreatedEvent.institutionNotFound(dets.getInstitutionId());
    String content = setupContent(dets);
    when(eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  public final void testCreateEventNullIdReturned() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    EventCreatedEvent testData = new EventCreatedEvent(null, dets);
    String content = setupContent(dets);
    when(eventService.createEvent(any(CreateEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }


  /**
   * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#findEvent(java.lang.Long)}.
   *
   * @throws Exception
   */
  @Test
  public final void testFindEvent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingFindElection()");
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    ReadEventEvent testData = new ReadEventEvent(dets.getEventId(), dets);
    when(eventService.readEvent(any(RequestReadEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(get(urlPrefix + "/{eventId}/", dets.getEventId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(jsonPath("$.eventId", is(dets.getEventId().intValue())))
      .andExpect(jsonPath("$.name", is(dets.getName())))
      .andExpect(jsonPath("$.location", is(dets.getLocation())))
      .andExpect(jsonPath("$.starts", is(dets.getStarts().intValue())))
      .andExpect(jsonPath("$.ends", is(dets.getEnds().intValue())))
      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//TODO		.andExpect(jsonPath("$.photos",is(dets.getPhotos())))
      .andExpect(jsonPath("$.volunteerPositions", is(dets.getVolunteerPositions())))
      .andExpect(jsonPath("$.created", is(dets.getCreated().intValue())))
      .andExpect(jsonPath("$.modified", is(dets.getModified().intValue())))
      .andExpect(jsonPath("$.organizer", is(dets.getOrganizer())))
      .andExpect(jsonPath("$.organizerEmail", is(dets.getOrganizerEmail())))
      .andExpect(jsonPath("$.institutionId", is(dets.getInstitutionId().intValue())))
      .andExpect(jsonPath("$.links[0].rel", is("self")))
      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
      .andExpect(jsonPath("$.links[2].rel", is("Next")))
      .andExpect(jsonPath("$.links[3].rel", is("Liked By")))
      .andExpect(jsonPath("$.links[4].rel", is("UnLiked By")))
      .andExpect(jsonPath("$.links[5].rel", is("Likes")))
      .andExpect(jsonPath("$.links[6].rel", is("Read all")))
      .andExpect(status().isOk());
  }

  @Test
  public final void testFindEventNotFound() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingFindElection()");
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    ReadEvent testData = ReadEventEvent.notFound(dets.getEventId());
    when(eventService.readEvent(any(RequestReadEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(get(urlPrefix + "/{electionId}/", dets.getEventId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }


  /**
   * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#findEvents(java.lang.Long)}.
   *
   * @throws Exception
   */
  @Test
  public final void testFindEvents() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingFindEvents()");
    Long instId = 1l;
    HashMap<Long, Event> dets = DatabaseDataFixture.populateEvents();

    Iterable<com.eulersbridge.iEngage.database.domain.Event> events = dets.values();
    Iterator<com.eulersbridge.iEngage.database.domain.Event> iter = events.iterator();
    ArrayList<EventDetails> eventDets = new ArrayList<EventDetails>();
    while (iter.hasNext()) {
      Event article = iter.next();
      eventDets.add(article.toEventDetails());
    }
    Long numElements = (long) eventDets.size();
    Integer numPages = (int) ((numElements / 10) + 1);
    AllReadEvent testData = new AllReadEvent(null, eventDets, numElements, numPages);
    when(eventService.readEvents(any(ReadAllEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);

    this.mockMvc.perform(get(urlPrefix + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(jsonPath("totalElements", is(numElements.intValue())))
      .andExpect(jsonPath("totalPages", is(numPages)))
      .andExpect(jsonPath("foundObjects[0].eventId", is(eventDets.get(0).getEventId().intValue())))
      .andExpect(jsonPath("foundObjects[0].name", is(eventDets.get(0).getName())))
      .andExpect(jsonPath("foundObjects[0].location", is(eventDets.get(0).getLocation())))
      .andExpect(jsonPath("foundObjects[0].starts", is(eventDets.get(0).getStarts().intValue())))
      .andExpect(jsonPath("foundObjects[0].ends", is(eventDets.get(0).getEnds().intValue())))
      .andExpect(jsonPath("foundObjects[0].description", is(eventDets.get(0).getDescription())))
//		.andExpect(jsonPath("$events[0].photos",is(eventDets.get(0).getPhotos())))
      .andExpect(jsonPath("foundObjects[0].volunteerPositions", is(eventDets.get(0).getVolunteerPositions())))
      .andExpect(jsonPath("foundObjects[0].created", is(eventDets.get(0).getCreated().intValue())))
      .andExpect(jsonPath("foundObjects[0].modified", is(eventDets.get(0).getModified().intValue())))
      .andExpect(jsonPath("foundObjects[0].organizer", is(eventDets.get(0).getOrganizer())))
      .andExpect(jsonPath("foundObjects[0].organizerEmail", is(eventDets.get(0).getOrganizerEmail())))
      .andExpect(jsonPath("foundObjects[0].institutionId", is(eventDets.get(0).getInstitutionId().intValue())))
      .andExpect(jsonPath("foundObjects[1].eventId", is(eventDets.get(1).getEventId().intValue())))
      .andExpect(jsonPath("foundObjects[1].name", is(eventDets.get(1).getName())))
      .andExpect(jsonPath("foundObjects[1].location", is(eventDets.get(1).getLocation())))
      .andExpect(jsonPath("foundObjects[1].starts", is(eventDets.get(1).getStarts().intValue())))
      .andExpect(jsonPath("foundObjects[1].ends", is(eventDets.get(1).getEnds().intValue())))
      .andExpect(jsonPath("foundObjects[1].description", is(eventDets.get(1).getDescription())))
//		.andExpect(jsonPath("$events[1].photos",is(eventDets.get(1).getPhotos())))
      .andExpect(jsonPath("foundObjects[1].volunteerPositions", is(eventDets.get(1).getVolunteerPositions())))
      .andExpect(jsonPath("foundObjects[1].created", is(eventDets.get(1).getCreated().intValue())))
      .andExpect(jsonPath("foundObjects[1].modified", is(eventDets.get(1).getModified().intValue())))
      .andExpect(jsonPath("foundObjects[1].organizer", is(eventDets.get(1).getOrganizer())))
      .andExpect(jsonPath("foundObjects[1].organizerEmail", is(eventDets.get(1).getOrganizerEmail())))
      .andExpect(jsonPath("foundObjects[1].institutionId", is(eventDets.get(1).getInstitutionId().intValue())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
      .andExpect(status().isOk());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#updateEvent(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Event)}.
   */
  @Test
  public final void testUpdateEvent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
    Long id = 1L;
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    dets.setName("Test EVENT2");
    EventUpdatedEvent testData = new EventUpdatedEvent(id, dets);
    String content = setupContent(dets);
    String returnedContent = setupReturnedContent(dets);

    when(eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
      .andDo(print())
      .andExpect(jsonPath("$.eventId", is(dets.getEventId().intValue())))
      .andExpect(jsonPath("$.name", is(dets.getName())))
      .andExpect(jsonPath("$.location", is(dets.getLocation())))
      .andExpect(jsonPath("$.starts", is(dets.getStarts().intValue())))
      .andExpect(jsonPath("$.ends", is(dets.getEnds().intValue())))
      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//		.andExpect(jsonPath("$.picture",is(dets.getPhotos())))
      .andExpect(jsonPath("$.volunteerPositions", is(dets.getVolunteerPositions())))
      .andExpect(jsonPath("$.created", is(dets.getCreated().intValue())))
      .andExpect(jsonPath("$.modified", is(dets.getModified().intValue())))
      .andExpect(jsonPath("$.organizer", is(dets.getOrganizer())))
      .andExpect(jsonPath("$.organizerEmail", is(dets.getOrganizerEmail())))
      .andExpect(jsonPath("$.institutionId", is(dets.getInstitutionId().intValue())))
      .andExpect(jsonPath("$.links[0].rel", is("self")))
      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
      .andExpect(jsonPath("$.links[2].rel", is("Next")))
      .andExpect(jsonPath("$.links[3].rel", is("Liked By")))
      .andExpect(jsonPath("$.links[4].rel", is("UnLiked By")))
      .andExpect(jsonPath("$.links[5].rel", is("Likes")))
      .andExpect(jsonPath("$.links[6].rel", is("Read all")))
//		.andExpect(content().string(returnedContent))
      .andExpect(status().isOk());
  }

  @Test
  public void testUpdateElectionNullEventReturned() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
    Long id = 1L;
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    String content = setupContent(dets);
    when(eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(null);
    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateElectionBadContent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
    Long id = 1L;
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    EventUpdatedEvent testData = new EventUpdatedEvent(id, dets);
    String content = setupInvalidContent(dets);
    when(eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateElectionEmptyContent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
    Long id = 1L;
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    EventUpdatedEvent testData = new EventUpdatedEvent(id, dets);
    when(eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateElectionNotFound() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingUpdateEvent()");
    Long id = 1L;
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    String content = setupContent(dets);
    when(eventService.updateEvent(any(UpdateEventEvent.class))).thenReturn(EventUpdatedEvent.notFound(id));
    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.rest.controller.EventController#deleteEvent(java.lang.Long)}.
   *
   * @throws Exception
   */
  @Test
  public final void testDeleteEvent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingDeleteEvent()");
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    EventDeletedEvent testData = new EventDeletedEvent(dets.getEventId());
    when(eventService.deleteEvent(any(DeleteEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(delete(urlPrefix + "/{eventId}/", dets.getEventId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
      .andExpect(status().isOk());
  }

  @Test
  public final void testDeleteEventNotFound() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingDeleteEvent()");
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    DeletedEvent testData = EventDeletedEvent.notFound(dets.getEventId());
    when(eventService.deleteEvent(any(DeleteEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(delete(urlPrefix + "/{eventId}/", dets.getEventId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  public final void testDeleteEventForbidden() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingDeleteEvent()");
    EventDetails dets = DatabaseDataFixture.populateEvent1().toEventDetails();
    DeletedEvent testData = EventDeletedEvent.deletionForbidden(dets.getEventId());
    when(eventService.deleteEvent(any(DeleteEventEvent.class))).thenReturn(testData);
    this.mockMvc.perform(delete(urlPrefix + "/{eventId}/", dets.getEventId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isGone());
  }

  @Test
  public final void testLikedByEvent() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
    Long id = 1L;
    User user = DatabaseDataFixture.populateUserGnewitt();
    LikedEvent evt = new LikedEvent(id, user.getEmail(), true);

    when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
    this.mockMvc.perform(put(urlPrefix + "/{id}" + ControllerConstants.LIKED_BY_LABEL + "/{userId}/", id.intValue(), user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(jsonPath("success", is(evt.isResultSuccess())))
      .andExpect(status().isOk());
  }

  @Test
  public final void testLikedByEventFailed() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
    Long id = 1L;
    User user = DatabaseDataFixture.populateUserGnewitt();
    LikedEvent evt = new LikedEvent(id, user.getEmail(), false);

    when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
    this.mockMvc.perform(put(urlPrefix + "/{id}" + ControllerConstants.LIKED_BY_LABEL + "/{userId}/", id.intValue(), user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(jsonPath("success", is(evt.isResultSuccess())))
      .andExpect(status().isOk());
  }

  @Test
  public final void testLikedByEventNotFound() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
    Long id = 1L;
    User user = DatabaseDataFixture.populateUserGnewitt();
    LikedEvent evt = LikedEvent.userNotFound(id, user.getEmail());

    when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
    this.mockMvc.perform(put(urlPrefix + "/{id}" + ControllerConstants.LIKED_BY_LABEL + "/{userId}/", id.intValue(), user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  public final void testLikedByEventGone() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
    Long id = 1L;
    User user = DatabaseDataFixture.populateUserGnewitt();
    LikedEvent evt = LikedEvent.entityNotFound(id, user.getEmail());

    when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
    this.mockMvc.perform(put(urlPrefix + "/{id}" + ControllerConstants.LIKED_BY_LABEL + "/{userId}/", id.intValue(), user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isGone());
  }


  @Test
  public final void testFindLikes() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingFindLikes()");
    Long id = 1L;
    User user = DatabaseDataFixture.populateUserGnewitt();
    Collection<UserDetails> userDetails = new ArrayList<>();
    userDetails.add(user.toUserDetails());

    LikeableObjectLikesEvent likeableObjectLikesEvent = new LikeableObjectLikesEvent(id, userDetails);


    when(likesService.likes(any(LikesLikeableObjectEvent.class), any(Sort.Direction.class), any(int.class), any(int.class))).thenReturn(likeableObjectLikesEvent);
    this.mockMvc.perform(get(urlPrefix + "/{id}/likes/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public final void testFindLikesNotFound() throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("performingFindLikes()");
    Long id = 1L;
    Collection<UserDetails> userDetails = new ArrayList<>();
    ReadEvent readPollEvent = ReadEvent.notFound(id);

    LikeableObjectLikesEvent likeableObjectLikesEvent = new LikeableObjectLikesEvent(id, userDetails);


    when(likesService.likes(any(LikesLikeableObjectEvent.class), any(Sort.Direction.class), any(int.class), any(int.class))).thenReturn(likeableObjectLikesEvent);
    when(eventService.readEvent(any(RequestReadEventEvent.class))).thenReturn(readPollEvent);
    this.mockMvc.perform(get(urlPrefix + "/{id}/likes/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

}
