///**
// *
// */
//package com.eulersbridge.iEngage.core.services;
//
//import com.eulersbridge.iEngage.core.events.*;
//import com.eulersbridge.iEngage.core.events.elections.*;
//import com.eulersbridge.iEngage.database.domain.Election;
//import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
//import com.eulersbridge.iEngage.database.domain.Institution;
//import com.eulersbridge.iEngage.database.repository.ElectionRepository;
//import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
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
//public class ElectionDomainEventHandlerTest {
//  private static Logger LOG = LoggerFactory.getLogger(ElectionDomainEventHandlerTest.class);
//
//  @Mock
//  ElectionRepository electionRepository;
//  @Mock
//  InstitutionRepository institutionRepository;
//
//  ElectionEventHandler service;
//
//  /**
//   * @throws java.lang.Exception
//   */
//  @Before
//  public void setUp() throws Exception {
//    MockitoAnnotations.initMocks(this);
//
//    service = new ElectionEventHandler(electionRepository, institutionRepository);
//  }
//
//
//  @Test
//  public final void testElectionEventHandler() {
//    assertNotNull("Not yet implemented", service);
//  }
//
//
//
//
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#readPreviousElection(com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent)}.
//   */
//  @Test
//  public final void testReadPreviousElection() {
//    if (LOG.isDebugEnabled()) LOG.debug("ReadingPreviousElection()");
//    Election testData = DatabaseDataFixture.populateElection1();
//    when(electionRepository.findPreviousElection(any(Long.class))).thenReturn(testData);
//    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(testData.getNodeId());
//    ReadElectionEvent evtData = (ReadElectionEvent) service.readPreviousElection(requestReadElectionEvent);
//    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
//    assertEquals(returnedDets, testData.toElectionDetails());
//    assertEquals(evtData.getNodeId(), returnedDets.getElectionId());
//    assertTrue(evtData.isEntityFound());
//  }
//
//  @Test
//  public final void testReadPreviousElectionNotFound() {
//    if (LOG.isDebugEnabled()) LOG.debug("ReadingPreviousElection()");
//    Election testData = null;
//    Long nodeId = 1l;
//    when(electionRepository.findPreviousElection(any(Long.class))).thenReturn(testData);
//    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(nodeId);
//    ReadEvent evtData = service.readPreviousElection(requestReadElectionEvent);
//    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
//    assertNull(returnedDets);
//    assertEquals(nodeId, evtData.getNodeId());
//    assertFalse(evtData.isEntityFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#readNextElection(com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent)}.
//   */
//  @Test
//  public final void testReadNextElection() {
//    if (LOG.isDebugEnabled()) LOG.debug("ReadingNextElection()");
//    Election testData = DatabaseDataFixture.populateElection1();
//    when(electionRepository.findNextElection(any(Long.class))).thenReturn(testData);
//    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(testData.getNodeId());
//    ReadElectionEvent evtData = (ReadElectionEvent) service.readNextElection(requestReadElectionEvent);
//    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
//    assertEquals(returnedDets, testData.toElectionDetails());
//    assertEquals(evtData.getNodeId(), returnedDets.getElectionId());
//    assertTrue(evtData.isEntityFound());
//  }
//
//  @Test
//  public final void testReadNextElectionNotFound() {
//    if (LOG.isDebugEnabled()) LOG.debug("ReadingNextElection()");
//    Election testData = null;
//    Long nodeId = 1l;
//    when(electionRepository.findNextElection(any(Long.class))).thenReturn(testData);
//    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(nodeId);
//    ReadEvent evtData = service.readNextElection(requestReadElectionEvent);
//    assertNull(evtData.getDetails());
//    assertEquals(nodeId, evtData.getNodeId());
//    assertFalse(evtData.isEntityFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#deleteElection(com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent)}.
//   */
//  @Test
//  public final void testDeleteElection() {
//    if (LOG.isDebugEnabled()) LOG.debug("DeletingElection()");
//    Election testData = DatabaseDataFixture.populateElection1();
//    when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
//    doNothing().when(electionRepository).delete((any(Long.class)));
//    DeleteElectionEvent deleteElectionEvent = new DeleteElectionEvent(testData.getNodeId());
//    DeletedEvent evtData = service.deleteElection(deleteElectionEvent);
//    assertTrue(evtData.isEntityFound());
//    assertTrue(evtData.isDeletionCompleted());
//    assertEquals(testData.getNodeId(), evtData.getNodeId());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#deleteElection(com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent)}.
//   */
//  @Test
//  public final void testDeleteElectionNotFound() {
//    if (LOG.isDebugEnabled()) LOG.debug("DeletingElection()");
//    Election testData = DatabaseDataFixture.populateElection1();
//    when(electionRepository.findOne(any(Long.class))).thenReturn(null);
//    doNothing().when(electionRepository).delete((any(Long.class)));
//    DeleteElectionEvent deleteElectionEvent = new DeleteElectionEvent(testData.getNodeId());
//    DeletedEvent evtData = service.deleteElection(deleteElectionEvent);
//    assertFalse(evtData.isEntityFound());
//    assertFalse(evtData.isDeletionCompleted());
//    assertEquals(testData.getNodeId(), evtData.getNodeId());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#updateElection(com.eulersbridge.iEngage.core.events.elections.UpdateElectionEvent)}.
//   */
//  @Test
//  public final void testUpdateElection() {
//    if (LOG.isDebugEnabled()) LOG.debug("UpdatingElection()");
//    Election testData = DatabaseDataFixture.populateElection1();
//    when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
//    when(electionRepository.save(any(Election.class), anyInt())).thenReturn(testData);
//    ElectionDetails dets = testData.toElectionDetails();
//    UpdateElectionEvent createElectionEvent = new UpdateElectionEvent(dets.getElectionId(), dets);
//    UpdatedEvent evtData = service.updateElection(createElectionEvent);
//    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
//    assertEquals(returnedDets, testData.toElectionDetails());
//    assertEquals(evtData.getNodeId(), returnedDets.getElectionId());
//    assertTrue(evtData.isEntityFound());
//    assertNotNull(evtData.getNodeId());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#updateElection(com.eulersbridge.iEngage.core.events.elections.UpdateElectionEvent)}.
//   */
//  @Test
//  public final void testUpdateElectionNotFound() {
//    if (LOG.isDebugEnabled()) LOG.debug("UpdatingElection()");
//    Election testData = DatabaseDataFixture.populateElection1();
//    when(electionRepository.findOne(any(Long.class))).thenReturn(null);
//    when(electionRepository.save(any(Election.class))).thenReturn(testData);
//    ElectionDetails dets = testData.toElectionDetails();
//    UpdateElectionEvent createElectionEvent = new UpdateElectionEvent(dets.getElectionId(), dets);
//    UpdatedEvent evtData = service.updateElection(createElectionEvent);
//    assertNull(evtData.getDetails());
//    assertEquals(evtData.getNodeId(), testData.getNodeId());
//    assertFalse(evtData.isEntityFound());
//    assertNotNull(evtData.getNodeId());
//  }
//}
