/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.elections.*;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Greg Newitt
 */
public class ElectionEventHandlerTest {
  private static Logger LOG = LoggerFactory.getLogger(ElectionEventHandlerTest.class);

  @Mock
  ElectionRepository electionRepository;
  @Mock
  InstitutionRepository institutionRepository;

  ElectionEventHandler service;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    service = new ElectionEventHandler(electionRepository, institutionRepository);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#ElectionEventHandler(com.eulersbridge.iEngage.database.repository.ElectionRepository)}.
   */
  @Test
  public final void testElectionEventHandler() {
    assertNotNull("Not yet implemented", service);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#requestReadElection(com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent)}.
   */
  @Test
  public final void testRequestReadElection() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(testData.getNodeId());
    ReadElectionEvent evtData = (ReadElectionEvent) service.readElection(requestReadElectionEvent);
    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toElectionDetails());
    assertEquals(evtData.getNodeId(), returnedDets.getElectionId());
    assertTrue(evtData.isEntityFound());
  }

  @Test
  public final void testRequestReadElectionNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingElection()");
    Election testData = null;
    Long nodeId = 1l;
    when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(nodeId);
    ReadEvent evtData = service.readElection(requestReadElectionEvent);
    assertNull(evtData.getDetails());
    assertEquals(nodeId, evtData.getNodeId());
    assertFalse(evtData.isEntityFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionPositionHandler#readElections(com.eulersbridge.iEngage.core.events.events.ReadAllPosition, Direction, int, int)}.
   */
  @Test
  public final void testReadElections() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingElections()");
    HashMap<Long, Election> events = DatabaseDataFixture.populateElections();
    ArrayList<Election> evts = new ArrayList<Election>();
    Iterator<Election> iter = events.values().iterator();
    while (iter.hasNext()) {
      Election na = iter.next();
      evts.add(na);
    }


    Long institutionId = 1l;
    ReadAllEvent evt = new ReadAllEvent(institutionId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable pageable = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<Election> testData = new PageImpl<Election>(evts, pageable, evts.size());
    when(electionRepository.findByInstitutionId(any(Long.class), any(Pageable.class))).thenReturn(testData);

    AllReadEvent evtData = service.readElections(evt, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertEquals(evtData.getTotalPages(), new Integer(1));
    assertEquals(evtData.getTotalItems(), new Long(evts.size()));
  }

  @Test
  public final void testReadElectionsNoneAvailable() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingElections()");
    ArrayList<Election> evts = new ArrayList<Election>();

    Long institutionId = 1l;
    ReadAllEvent evt = new ReadAllEvent(institutionId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable pageable = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<Election> testData = new PageImpl<Election>(evts, pageable, evts.size());
    when(electionRepository.findByInstitutionId(any(Long.class), any(Pageable.class))).thenReturn(testData);
    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    when(institutionRepository.findOne(any(Long.class))).thenReturn(inst);

    AllReadEvent evtData = service.readElections(evt, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertEquals(evtData.getTotalPages().intValue(), 0);
    assertEquals(evtData.getTotalItems().longValue(), 0);
  }

  @Test
  public final void testReadElectionsNoValidInst() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingElections()");
    ArrayList<Election> evts = new ArrayList<Election>();

    Long institutionId = 1l;
    ReadAllEvent evt = new ReadAllEvent(institutionId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable pageable = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<Election> testData = new PageImpl<Election>(evts, pageable, evts.size());
    when(electionRepository.findByInstitutionId(any(Long.class), any(Pageable.class))).thenReturn(testData);
    when(electionRepository.findOne(any(Long.class))).thenReturn(null);

    AllReadEvent evtData = service.readElections(evt, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertFalse(evtData.isEntityFound());
    assertEquals(evtData.getTotalPages(), null);
    assertEquals(evtData.getTotalItems(), null);
  }

  @Test
  public final void testReadElectionsNullReturned() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingElections()");

    Long institutionId = 1l;
    ReadAllEvent evt = new ReadAllEvent(institutionId);

    Page<Election> testData = null;
    when(electionRepository.findByInstitutionId(any(Long.class), any(Pageable.class))).thenReturn(testData);

    int pageLength = 10;
    int pageNumber = 0;
    AllReadEvent evtData = service.readElections(evt, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertFalse(evtData.isEntityFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#createElection(com.eulersbridge.iEngage.core.events.elections.CreateElectionEvent)}.
   */
  @Test
  public final void testCreateElection() {
    if (LOG.isDebugEnabled()) LOG.debug("CreatingElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    Institution testInst = DatabaseDataFixture.populateInstUniMelb();
    when(institutionRepository.findOne(any(Long.class), anyInt())).thenReturn(testInst);
    when(electionRepository.save(any(Election.class))).thenReturn(testData);
    ElectionDetails dets = testData.toElectionDetails();
    CreateElectionEvent createElectionEvent = new CreateElectionEvent(dets);
    ElectionCreatedEvent evtData = service.createElection(createElectionEvent);
    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toElectionDetails());
    assertNotNull(evtData.getElectionId());
    assertEquals(evtData.getElectionId(), returnedDets.getElectionId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#createElection(com.eulersbridge.iEngage.core.events.elections.CreateElectionEvent)}.
   */
  @Test
  public final void testCreateElectionInstNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("CreatingElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    Institution testInst = null;
    when(institutionRepository.findOne(any(Long.class))).thenReturn(testInst);
    when(electionRepository.save(any(Election.class))).thenReturn(testData);
    ElectionDetails dets = testData.toElectionDetails();
    CreateElectionEvent createElectionEvent = new CreateElectionEvent(dets);
    ElectionCreatedEvent evtData = service.createElection(createElectionEvent);
    assertFalse(evtData.isInstitutionFound());
    assertEquals(evtData.getElectionId(), testData.getInstitution().getNodeId());
    assertNull(evtData.getDetails());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#readPreviousElection(com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent)}.
   */
  @Test
  public final void testReadPreviousElection() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingPreviousElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    when(electionRepository.findPreviousElection(any(Long.class))).thenReturn(testData);
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(testData.getNodeId());
    ReadElectionEvent evtData = (ReadElectionEvent) service.readPreviousElection(requestReadElectionEvent);
    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toElectionDetails());
    assertEquals(evtData.getNodeId(), returnedDets.getElectionId());
    assertTrue(evtData.isEntityFound());
  }

  @Test
  public final void testReadPreviousElectionNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingPreviousElection()");
    Election testData = null;
    Long nodeId = 1l;
    when(electionRepository.findPreviousElection(any(Long.class))).thenReturn(testData);
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(nodeId);
    ReadEvent evtData = service.readPreviousElection(requestReadElectionEvent);
    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
    assertNull(returnedDets);
    assertEquals(nodeId, evtData.getNodeId());
    assertFalse(evtData.isEntityFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#readNextElection(com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent)}.
   */
  @Test
  public final void testReadNextElection() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingNextElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    when(electionRepository.findNextElection(any(Long.class))).thenReturn(testData);
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(testData.getNodeId());
    ReadElectionEvent evtData = (ReadElectionEvent) service.readNextElection(requestReadElectionEvent);
    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toElectionDetails());
    assertEquals(evtData.getNodeId(), returnedDets.getElectionId());
    assertTrue(evtData.isEntityFound());
  }

  @Test
  public final void testReadNextElectionNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingNextElection()");
    Election testData = null;
    Long nodeId = 1l;
    when(electionRepository.findNextElection(any(Long.class))).thenReturn(testData);
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(nodeId);
    ReadEvent evtData = service.readNextElection(requestReadElectionEvent);
    assertNull(evtData.getDetails());
    assertEquals(nodeId, evtData.getNodeId());
    assertFalse(evtData.isEntityFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#deleteElection(com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent)}.
   */
  @Test
  public final void testDeleteElection() {
    if (LOG.isDebugEnabled()) LOG.debug("DeletingElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
    doNothing().when(electionRepository).delete((any(Long.class)));
    DeleteElectionEvent deleteElectionEvent = new DeleteElectionEvent(testData.getNodeId());
    DeletedEvent evtData = service.deleteElection(deleteElectionEvent);
    assertTrue(evtData.isEntityFound());
    assertTrue(evtData.isDeletionCompleted());
    assertEquals(testData.getNodeId(), evtData.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#deleteElection(com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent)}.
   */
  @Test
  public final void testDeleteElectionNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("DeletingElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    when(electionRepository.findOne(any(Long.class))).thenReturn(null);
    doNothing().when(electionRepository).delete((any(Long.class)));
    DeleteElectionEvent deleteElectionEvent = new DeleteElectionEvent(testData.getNodeId());
    DeletedEvent evtData = service.deleteElection(deleteElectionEvent);
    assertFalse(evtData.isEntityFound());
    assertFalse(evtData.isDeletionCompleted());
    assertEquals(testData.getNodeId(), evtData.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#updateElection(com.eulersbridge.iEngage.core.events.elections.UpdateElectionEvent)}.
   */
  @Test
  public final void testUpdateElection() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
    when(electionRepository.save(any(Election.class), anyInt())).thenReturn(testData);
    ElectionDetails dets = testData.toElectionDetails();
    UpdateElectionEvent createElectionEvent = new UpdateElectionEvent(dets.getElectionId(), dets);
    UpdatedEvent evtData = service.updateElection(createElectionEvent);
    ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toElectionDetails());
    assertEquals(evtData.getNodeId(), returnedDets.getElectionId());
    assertTrue(evtData.isEntityFound());
    assertNotNull(evtData.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#updateElection(com.eulersbridge.iEngage.core.events.elections.UpdateElectionEvent)}.
   */
  @Test
  public final void testUpdateElectionNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingElection()");
    Election testData = DatabaseDataFixture.populateElection1();
    when(electionRepository.findOne(any(Long.class))).thenReturn(null);
    when(electionRepository.save(any(Election.class))).thenReturn(testData);
    ElectionDetails dets = testData.toElectionDetails();
    UpdateElectionEvent createElectionEvent = new UpdateElectionEvent(dets.getElectionId(), dets);
    UpdatedEvent evtData = service.updateElection(createElectionEvent);
    assertNull(evtData.getDetails());
    assertEquals(evtData.getNodeId(), testData.getNodeId());
    assertFalse(evtData.isEntityFound());
    assertNotNull(evtData.getNodeId());
  }
}
