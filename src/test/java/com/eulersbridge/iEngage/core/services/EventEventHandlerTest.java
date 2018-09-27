///**
// *
// */
//package com.eulersbridge.iEngage.core.services;
//
//import com.eulersbridge.iEngage.core.events.*;
//import com.eulersbridge.iEngage.core.events.events.*;
//import com.eulersbridge.iEngage.database.domain.Event;
//import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
//import com.eulersbridge.iEngage.database.domain.Institution;
//import com.eulersbridge.iEngage.database.domain.NewsFeed;
//import com.eulersbridge.iEngage.database.domain.User;
//import com.eulersbridge.iEngage.database.repository.EventRepository;
//import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
//import com.eulersbridge.iEngage.database.repository.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
///**
// * @author Greg Newitt
// */
//public class EventEventHandlerTest {
//  private static Logger LOG = LoggerFactory.getLogger(EventEventHandlerTest.class);
//
//  @Mock
//  EventRepository eventRepository;
//  @Mock
//  InstitutionRepository institutionRepository;
//  @Mock
//  UserRepository userRepo;
//
//  EventEventHandler service;
//
//  /**
//   * @throws java.lang.Exception
//   */
//  @Before
//  public void setUp() throws Exception {
//    MockitoAnnotations.initMocks(this);
//
//    service = new EventEventHandler(eventRepository, institutionRepository, userRepo);
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler(com.eulersbridge.iEngage.database.repository.EventRepository)}.
//   */
//  @Test
//  public final void testEventEventHandler() {
//    assertNotNull("Not yet implemented", service);
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#createEvent(com.eulersbridge.iEngage.core.events.events.CreateEventEvent)}.
//   */
//  @Test
//  public final void testCreateEvent() {
//    if (LOG.isDebugEnabled()) LOG.debug("CreatingEvent()");
//    Event testData = DatabaseDataFixture.populateEvent1();
//    Institution testInst = DatabaseDataFixture.populateInstUniMelb();
//    NewsFeed testNf = DatabaseDataFixture.populateNewsFeed1();
//    User testUser = DatabaseDataFixture.populateUserYikai();
//    when(institutionRepository.findOne(any(Long.class), anyInt())).thenReturn(testInst);
//    when(institutionRepository.findNewsFeedByInstitutionId(any(Long.class))).thenReturn(testNf);
//    when(userRepo.findByEmail(any(String.class), anyInt())).thenReturn(testUser);
//    when(eventRepository.save(any(Event.class))).thenReturn(testData);
//    EventDetails dets = testData.toEventDetails();
//    CreateEventEvent createEventEvent = new CreateEventEvent(dets);
//    EventCreatedEvent evtData = service.createEvent(createEventEvent);
//    EventDetails returnedDets = (EventDetails) evtData.getDetails();
//    assertEquals(returnedDets, testData.toEventDetails());
//    assertEquals(evtData.getEventId(), returnedDets.getEventId());
//    assertNotNull(evtData.getDetails());
//  }
//
//  @Test
//  public final void testCreateEventInstNotFound() {
//    if (LOG.isDebugEnabled()) LOG.debug("CreatingEvent()");
//    Event testData = DatabaseDataFixture.populateEvent1();
//    Institution testInst = null;
//    User testUser = DatabaseDataFixture.populateUserYikai();
//    when(institutionRepository.findOne(any(Long.class))).thenReturn(testInst);
//    when(eventRepository.save(any(Event.class))).thenReturn(testData);
//    when(userRepo.findByEmail(any(String.class), anyInt())).thenReturn(testUser);
//    EventDetails dets = testData.toEventDetails();
//    CreateEventEvent createEventEvent = new CreateEventEvent(dets);
//    EventCreatedEvent evtData = service.createEvent(createEventEvent);
//    assertFalse(evtData.isInstitutionFound());
//    assertEquals(evtData.getEventId(), testData.getNewsFeed$().getInstitution$().getNodeId());
//    assertNull(evtData.getDetails());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler(com.eulersbridge.iEngage.core.events.events.RequestReadEventEvent)}.
//   */
//  @Test
//  public final void testReadEvent() {
//    if (LOG.isDebugEnabled()) LOG.debug("ReadingEvent()");
//    Event testData = DatabaseDataFixture.populateEvent1();
//    when(eventRepository.findOne(any(Long.class))).thenReturn(testData);
//    RequestReadEventEvent readElectionEvent = new RequestReadEventEvent(testData.getNodeId());
//    ReadEventEvent evtData = (ReadEventEvent) service.readEvent(readElectionEvent);
//    EventDetails returnedDets = (EventDetails) evtData.getDetails();
//    assertEquals(returnedDets, testData.toEventDetails());
//    assertEquals(evtData.getNodeId(), returnedDets.getEventId());
//    assertTrue(evtData.isEntityFound());
//  }
//
//  @Test
//  public final void testReadEventNotFound() {
//    if (LOG.isDebugEnabled()) LOG.debug("ReadingEvent()");
//    Event testData = null;
//    Long nodeId = 1l;
//    when(eventRepository.findOne(any(Long.class))).thenReturn(testData);
//    RequestReadEventEvent readElectionEvent = new RequestReadEventEvent(nodeId);
//    ReadEvent evtData = service.readEvent(readElectionEvent);
//    assertNull(evtData.getDetails());
//    assertEquals(nodeId, evtData.getNodeId());
//    assertFalse(evtData.isEntityFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#updateEvent(com.eulersbridge.iEngage.core.events.events.UpdateEventEvent)}.
//   */
//  @Test
//  public final void testUpdateEvent() {
//    if (LOG.isDebugEnabled()) LOG.debug("UpdatingEvent()");
//    Event testData = DatabaseDataFixture.populateEvent1();
//    when(eventRepository.findOne(any(Long.class))).thenReturn(testData);
//    when(eventRepository.save(any(Event.class), anyInt())).thenReturn(testData);
//    EventDetails dets = testData.toEventDetails();
//    UpdateEventEvent createEventEvent = new UpdateEventEvent(dets.getEventId(), dets);
//    UpdatedEvent evtData = service.updateEvent(createEventEvent);
//    EventDetails returnedDets = (EventDetails) evtData.getDetails();
//    assertEquals(returnedDets, testData.toEventDetails());
//    assertEquals(evtData.getNodeId(), returnedDets.getEventId());
//    assertTrue(evtData.isEntityFound());
//    assertNotNull(evtData.getNodeId());
//  }
//
//  @Test
//  public final void testUpdateEventNotFound() {
//    if (LOG.isDebugEnabled()) LOG.debug("UpdatingEvent()");
//    Event testData = DatabaseDataFixture.populateEvent1();
//    when(eventRepository.findOne(any(Long.class))).thenReturn(null);
//    when(eventRepository.save(any(Event.class))).thenReturn(testData);
//    EventDetails dets = testData.toEventDetails();
//    UpdateEventEvent createEventEvent = new UpdateEventEvent(dets.getEventId(), dets);
//    UpdatedEvent evtData = service.updateEvent(createEventEvent);
//    assertNull(evtData.getDetails());
//    assertEquals(evtData.getNodeId(), testData.getNodeId());
//    assertFalse(evtData.isEntityFound());
//    assertNotNull(evtData.getNodeId());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#deleteEvent(com.eulersbridge.iEngage.core.events.events.DeleteEventEvent)}.
//   */
//  @Test
//  public final void testDeleteEvent() {
//    if (LOG.isDebugEnabled()) LOG.debug("DeletingEvent()");
//    Event testData = DatabaseDataFixture.populateEvent1();
//    when(eventRepository.findOne(any(Long.class))).thenReturn(testData);
//    doNothing().when(eventRepository).delete((any(Long.class)));
//    DeleteEventEvent deleteEventEvent = new DeleteEventEvent(testData.getNodeId());
//    DeletedEvent evtData = service.deleteEvent(deleteEventEvent);
//    assertTrue(evtData.isEntityFound());
//    assertTrue(evtData.isDeletionCompleted());
//    assertEquals(testData.getNodeId(), evtData.getNodeId());
//  }
//
//  @Test
//  public final void testDeleteElectionNotFound() {
//    if (LOG.isDebugEnabled()) LOG.debug("DeletingEvent()");
//    Event testData = DatabaseDataFixture.populateEvent1();
//    when(eventRepository.findOne(any(Long.class))).thenReturn(null);
//    doNothing().when(eventRepository).delete((any(Long.class)));
//    DeleteEventEvent deleteEventEvent = new DeleteEventEvent(testData.getNodeId());
//    DeletedEvent evtData = service.deleteEvent(deleteEventEvent);
//    assertFalse(evtData.isEntityFound());
//    assertFalse(evtData.isDeletionCompleted());
//    assertEquals(testData.getNodeId(), evtData.getNodeId());
//  }
//
//
//
//}
