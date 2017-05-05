/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.*;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Greg Newitt
 */
public class PollEventHandlerTest {
  private static Logger LOG = LoggerFactory.getLogger(PollEventHandlerTest.class);

  @Mock
  PollRepository pollRepository;
  @Mock
  PollAnswerRepository answerRepository;
  @Mock
  OwnerRepository ownerRepository;
  @Mock
  UserRepository userRepository;
  @Mock
  InstitutionRepository institutionRepository;

  PollEventHandler service;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    service = new PollEventHandler(pollRepository, answerRepository, ownerRepository, institutionRepository);
  }

  @Test
  public final void testPollEventHandler() {
    assertNotNull("Not yet implemented", service);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#requestReadPoll(com.eulersbridge.iEngage.core.events.polls.RequestReadPollEvent)}.
   */
  @Test
  public final void testRequestReadPoll() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
    RequestReadPollEvent readPollEvent = new RequestReadPollEvent(testData.getNodeId());
    ReadPollEvent evtData = (ReadPollEvent) service.requestReadPoll(readPollEvent);
    PollDetails returnedDets = (PollDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toPollDetails());
    assertEquals(evtData.getNodeId(), returnedDets.getNodeId());
    assertTrue(evtData.isEntityFound());
  }


  @Test
  public final void testReadPollNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingPoll()");
    Poll testData = null;
    Long nodeId = 1l;
    when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
    RequestReadPollEvent readPollEvent = new RequestReadPollEvent(nodeId);
    ReadEvent evtData = service.requestReadPoll(readPollEvent);
    assertNull(evtData.getDetails());
    assertEquals(nodeId, evtData.getNodeId());
    assertFalse(evtData.isEntityFound());
  }


  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#createPoll(com.eulersbridge.iEngage.core.events.polls.CreatePollEvent)}.
   */
  @Test
  public final void testCreatePoll() {
    if (LOG.isDebugEnabled()) LOG.debug("CreatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    Institution testOwner = new Institution();
    testOwner.setNodeId(testData.getInstitution().getNodeId());
    Owner testCreator = new Owner();
    testCreator.setNodeId(testData.getCreator().getNodeId());
    when(pollRepository.save(any(Poll.class))).thenReturn(testData);
    when(institutionRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testCreator);
    PollDetails dets = testData.toPollDetails();
    CreatePollEvent createPollEvent = new CreatePollEvent(dets);
    PollCreatedEvent evtData = service.createPoll(createPollEvent);
    Details returnedDets = evtData.getDetails();
    assertEquals(testData.toPollDetails(), returnedDets);
    assertEquals(testData.getNodeId(), returnedDets.getNodeId());
    assertNull(evtData.getFailedNodeId());
  }

  @Test
  public final void testCreatePollOwnerNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("CreatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    Owner testOwner = null;
    Owner testCreator = new Owner();
    testCreator.setNodeId(testData.getCreator().getNodeId());
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.save(any(Poll.class))).thenReturn(testData);
    PollDetails dets = testData.toPollDetails();
    CreatePollEvent createPollEvent = new CreatePollEvent(dets);
    PollCreatedEvent evtData = service.createPoll(createPollEvent);
    assertEquals(evtData.getFailedNodeId(), testData.getInstitution().getNodeId());
    assertFalse(evtData.isOwnerFound());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testCreatePollCreatorNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("CreatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    Institution testOwner = new Institution();
    testOwner.setNodeId(testData.getInstitution().getNodeId());
    Owner testCreator = null;
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testCreator);
    when(institutionRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.save(any(Poll.class))).thenReturn(testData);
    PollDetails dets = testData.toPollDetails();
    CreatePollEvent createPollEvent = new CreatePollEvent(dets);
    PollCreatedEvent evtData = service.createPoll(createPollEvent);
    assertEquals(evtData.getFailedNodeId(), testData.getCreator().getNodeId());
    assertFalse(evtData.isCreatorFound());
    assertNull(evtData.getDetails());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#deletePoll(com.eulersbridge.iEngage.core.events.polls.DeletePollEvent)}.
   */
  @Test
  public final void testDeletePoll() {
    LOG.debug("DeletingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
    doNothing().when(pollRepository).delete((any(Long.class)));
    DeletePollEvent deletePollEvent = new DeletePollEvent(testData.getNodeId());
    DeletedEvent evtData = service.deletePoll(deletePollEvent);
    assertTrue(evtData.isEntityFound());
    assertTrue(evtData.isDeletionCompleted());
    assertEquals(testData.getNodeId(), evtData.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#deletePoll(com.eulersbridge.iEngage.core.events.polls.DeletePollEvent)}.
   */
  @Test
  public final void testDeletePollNotFound() {
    LOG.debug("DeletingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    when(pollRepository.findOne(any(Long.class))).thenReturn(null);
    doNothing().when(pollRepository).delete((any(Long.class)));
    DeletePollEvent deletePollEvent = new DeletePollEvent(testData.getNodeId());
    DeletedEvent evtData = service.deletePoll(deletePollEvent);
    assertFalse(evtData.isEntityFound());
    assertFalse(evtData.isDeletionCompleted());
    assertEquals(testData.getNodeId(), evtData.getNodeId());
  }


  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#updatePoll(com.eulersbridge.iEngage.core.events.polls.UpdatePollEvent)}.
   */
  @Test
  public final void testUpdatePoll() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    Institution testOwner = new Institution();
    testOwner.setNodeId(testData.getInstitution().getNodeId());
    Owner testCreator = new Owner();
    testCreator.setNodeId(testData.getCreator().getNodeId());
    when(institutionRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testCreator);
    when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
    when(pollRepository.save(any(Poll.class), anyInt())).thenReturn(testData);
    PollDetails dets = testData.toPollDetails();
    UpdatePollEvent createPollEvent = new UpdatePollEvent(dets.getNodeId(), dets);
    UpdatedEvent evtData = service.updatePoll(createPollEvent);
    System.out.println(evtData);
    PollDetails returnedDets = (PollDetails) evtData.getDetails();
    assertEquals(testData.toPollDetails(), returnedDets);
    assertEquals(evtData.getNodeId(), returnedDets.getNodeId());
    assertTrue(evtData.isEntityFound());
    assertNotNull(evtData.getNodeId());
  }

  @Test
  public final void testUpdatePollNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    when(pollRepository.findOne(any(Long.class))).thenReturn(null);
    PollDetails dets = testData.toPollDetails();
    UpdatePollEvent createPollEvent = new UpdatePollEvent(dets.getNodeId(), dets);
    UpdatedEvent evtData = service.updatePoll(createPollEvent);
    assertNull(evtData.getDetails());
    assertEquals(testData.getNodeId(), evtData.getNodeId());
    assertFalse(evtData.isEntityFound());
    assertNotNull(evtData.getNodeId());
  }

  @Test
  public final void testUpdatePollOwnerNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    Owner testOwner = null;
    when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.save(any(Poll.class))).thenReturn(testData);
    PollDetails dets = testData.toPollDetails();
    UpdatePollEvent createPollEvent = new UpdatePollEvent(dets.getNodeId(), dets);
    UpdatedEvent evtData = service.updatePoll(createPollEvent);
    assertEquals(evtData.getNodeId(), testData.getInstitution().getNodeId());
    assertFalse(((PollUpdatedEvent) evtData).isOwnerFound());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testUpdatePollOwnerNotFoundNull() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    Institution testOwner = new Institution();
    testData.setInstitution(testOwner);
    when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
    when(pollRepository.save(any(Poll.class))).thenReturn(testData);
    PollDetails dets = testData.toPollDetails();
    UpdatePollEvent createPollEvent = new UpdatePollEvent(dets.getNodeId(), dets);
    UpdatedEvent evtData = service.updatePoll(createPollEvent);
    assertEquals(evtData.getNodeId(), testData.getInstitution().getNodeId());
    assertFalse(((PollUpdatedEvent) evtData).isOwnerFound());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testUpdatePollCreatorNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    Institution testOwner = new Institution();
    testOwner.setNodeId(testData.getInstitution().getNodeId());
    Owner testCreator = null;
    when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testCreator);
    when(institutionRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.save(any(Poll.class))).thenReturn(testData);
    PollDetails dets = testData.toPollDetails();
    UpdatePollEvent createPollEvent = new UpdatePollEvent(dets.getNodeId(), dets);
    UpdatedEvent evtData = service.updatePoll(createPollEvent);
    assertNotNull(evtData.getNodeId());
    assertEquals(evtData.getNodeId(), testData.getCreator().getNodeId());
    assertFalse(((PollUpdatedEvent) evtData).isCreatorFound());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testUpdatePollCreatorNotFoundNull() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
    Poll testData = DatabaseDataFixture.populatePoll1();
    Institution testOwner = new Institution();
    testOwner.setNodeId(testData.getInstitution().getNodeId());
    Owner testCreator = new Owner(null);
    testData.setCreator(new User(testCreator.getNodeId()));
    when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testCreator);
    when(institutionRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.save(any(Poll.class))).thenReturn(testData);
    PollDetails dets = testData.toPollDetails();
    UpdatePollEvent createPollEvent = new UpdatePollEvent(dets.getNodeId(), dets);
    UpdatedEvent evtData = service.updatePoll(createPollEvent);
    assertEquals(evtData.getNodeId(), testData.getCreator().getNodeId());
    assertFalse(((PollUpdatedEvent) evtData).isCreatorFound());
    assertNull(evtData.getDetails());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#answerPoll(com.eulersbridge.iEngage.core.events.polls.CreatePollAnswerEvent)}.
   */
  @Test
  public final void testAnswerPoll() {
    if (LOG.isDebugEnabled()) LOG.debug("AnsweringPoll()");
    PollAnswer testData = DatabaseDataFixture.populatePollAnswer1();
    Owner testOwner = testData.getUser();
    Poll testPoll = testData.getPoll();
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(pollRepository.addPollAnswer(testOwner.getNodeId(), testPoll.getNodeId(), testData.getAnswer())).thenReturn(testData);
    when(answerRepository.save(any(PollAnswer.class))).thenReturn(testData);
    PollAnswerDetails dets = testData.toPollAnswerDetails();
    CreatePollAnswerEvent createPollEvent = new CreatePollAnswerEvent(dets);
    PollAnswerCreatedEvent evtData = service.answerPoll(createPollEvent);
    Details returnedDets = evtData.getDetails();
    assertEquals(testData.toPollAnswerDetails(), returnedDets);
    assertEquals(testData.getNodeId(), returnedDets.getNodeId());
  }

  @Test
  public final void testAnswerPollOwnerNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("AnsweringPoll()");
    PollAnswer testData = DatabaseDataFixture.populatePollAnswer1();
    Owner testOwner = null;
    Poll testPoll = testData.getPoll();
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(answerRepository.save(any(PollAnswer.class))).thenReturn(testData);
    PollAnswerDetails dets = testData.toPollAnswerDetails();
    CreatePollAnswerEvent createPollEvent = new CreatePollAnswerEvent(dets);
    PollAnswerCreatedEvent evtData = service.answerPoll(createPollEvent);
    assertNotNull(evtData);
    assertEquals(evtData.getFailedNodeId(), testData.getUser().getNodeId());
    assertFalse(evtData.isAnswererFound());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testAnswerPollPollNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("AnsweringPoll()");
    PollAnswer testData = DatabaseDataFixture.populatePollAnswer1();
    Owner testOwner = testData.getUser();
    Poll testPoll = null;
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner).thenReturn(testOwner);
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(answerRepository.save(any(PollAnswer.class))).thenReturn(testData);
    PollAnswerDetails dets = testData.toPollAnswerDetails();
    CreatePollAnswerEvent createPollEvent = new CreatePollAnswerEvent(dets);
    PollAnswerCreatedEvent evtData = service.answerPoll(createPollEvent);
    assertNotNull(evtData);
    assertEquals(evtData.getFailedNodeId(), testData.getPoll().getNodeId());
    assertFalse(evtData.isPollFound());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testAnswerPollNullAnswerIndex() {
    if (LOG.isDebugEnabled()) LOG.debug("AnsweringPoll()");
    PollAnswer testData = DatabaseDataFixture.populatePollAnswer1();
    testData.setAnswer(null);
    Owner testOwner = testData.getUser();
    Poll testPoll = testData.getPoll();
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(answerRepository.save(any(PollAnswer.class))).thenReturn(testData);
    PollAnswerDetails dets = testData.toPollAnswerDetails();
    CreatePollAnswerEvent createPollEvent = new CreatePollAnswerEvent(dets);
    PollAnswerCreatedEvent evtData = service.answerPoll(createPollEvent);
    assertNotNull(evtData);
    assertEquals(evtData.getFailedNodeId(), testData.getAnswer());
    assertFalse(evtData.isAnswerValid());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testAnswerPollLowAnswerIndex() {
    if (LOG.isDebugEnabled()) LOG.debug("AnsweringPoll()");
    PollAnswer testData = DatabaseDataFixture.populatePollAnswer1();
    testData.setAnswer(-1);
    Owner testOwner = testData.getUser();
    Poll testPoll = testData.getPoll();
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(answerRepository.save(any(PollAnswer.class))).thenReturn(testData);
    PollAnswerDetails dets = testData.toPollAnswerDetails();
    CreatePollAnswerEvent createPollEvent = new CreatePollAnswerEvent(dets);
    PollAnswerCreatedEvent evtData = service.answerPoll(createPollEvent);
    assertNotNull(evtData);
    assertEquals(evtData.getFailedNodeId().intValue(), testData.getAnswer().intValue());
    assertFalse(evtData.isAnswerValid());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testAnswerPollHighAnswerIndex() {
    if (LOG.isDebugEnabled()) LOG.debug("AnsweringPoll()");
    PollAnswer testData = DatabaseDataFixture.populatePollAnswer1();
    testData.setAnswer(15);
    Owner testOwner = testData.getUser();
    Poll testPoll = testData.getPoll();
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(answerRepository.save(any(PollAnswer.class))).thenReturn(testData);
    PollAnswerDetails dets = testData.toPollAnswerDetails();
    CreatePollAnswerEvent createPollEvent = new CreatePollAnswerEvent(dets);
    PollAnswerCreatedEvent evtData = service.answerPoll(createPollEvent);
    assertNotNull(evtData);
    assertEquals(evtData.getFailedNodeId().intValue(), testData.getAnswer().intValue());
    assertFalse(evtData.isAnswerValid());
    assertNull(evtData.getDetails());
  }

  @Test
  public final void testReadPollResult() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingPollResult()");
    Long nodeId = 1l;
    List<PollResultTemplate> pollResultTemplates = DatabaseDataFixture.populatePollResultDetails1();
    Poll testPoll = DatabaseDataFixture.populatePoll1();
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(pollRepository.getPollResults(any(Long.class))).thenReturn(pollResultTemplates);
    ReadPollResultEvent readPollResultEvt = new ReadPollResultEvent(nodeId);
    ReadEvent evtData = service.readPollResult(readPollResultEvt);
    assertEquals(evtData.getNodeId(), nodeId);
    assertTrue(evtData.isEntityFound());

    String answers[] = testPoll.getAnswers().split(",");
    int numAnswers = answers.length;

    List<PollResult> prs = PollResultDetails.toPollResultList(pollResultTemplates.iterator(), numAnswers);
    assertEquals(prs, ((PollResultDetails) evtData.getDetails()).getAnswers());
  }

  @Test
  public final void testReadPollResult2() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingPollResult()");
    Long nodeId = 1l;
    List<PollResultTemplate> pollResultTemplates = DatabaseDataFixture.populatePollResultDetails2();
    Poll testPoll = DatabaseDataFixture.populatePoll1();
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(pollRepository.getPollResults(any(Long.class))).thenReturn(pollResultTemplates);
    ReadPollResultEvent readPollResultEvt = new ReadPollResultEvent(nodeId);
    ReadEvent evtData = service.readPollResult(readPollResultEvt);
    assertEquals(evtData.getNodeId(), nodeId);
    assertTrue(evtData.isEntityFound());

    String answers[] = testPoll.getAnswers().split(",");
    int numAnswers = answers.length;

    List<PollResult> prs = PollResultDetails.toPollResultList(pollResultTemplates.iterator(), numAnswers);
    assertEquals(prs, ((PollResultDetails) evtData.getDetails()).getAnswers());
  }

  @Test
  public final void testReadPollResultNoPoll() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingPollResult()");
    Long nodeId = 1l;
    Poll testPoll = null;
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    ReadPollResultEvent readPollResultEvt = new ReadPollResultEvent(nodeId);
    ReadEvent evtData = service.readPollResult(readPollResultEvt);
    assertEquals(evtData.getNodeId(), nodeId);
    assertFalse(evtData.isEntityFound());
    assertNull(((PollResultDetails) evtData.getDetails()));
  }

  @Test
  public final void testReadPollResultNoResults() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingPollResult()");
    Long nodeId = 1l;
    List<PollResultTemplate> pollResultTemplates = null;
    Poll testPoll = DatabaseDataFixture.populatePoll1();
    when(pollRepository.findOne(any(Long.class))).thenReturn(testPoll);
    when(pollRepository.getPollResults(any(Long.class))).thenReturn(pollResultTemplates);
    ReadPollResultEvent readPollResultEvt = new ReadPollResultEvent(nodeId);
    ReadEvent evtData = service.readPollResult(readPollResultEvt);
    assertEquals(evtData.getNodeId(), nodeId);
    assertFalse(evtData.isEntityFound());
    assertNull(((PollResultDetails) evtData.getDetails()));
  }

  @Test
  public final void testFindPolls() {
    if (LOG.isDebugEnabled()) LOG.debug("FindingPolls()");

    ArrayList<Poll> evts = new ArrayList<Poll>();
    evts.add(DatabaseDataFixture.populatePoll1());
    evts.add(DatabaseDataFixture.populatePoll2());

    Long ownerId = 1L;
    ReadAllEvent readPollsEvent = new ReadAllEvent(ownerId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable p = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<Poll> testData = new PageImpl<Poll>(evts, p, evts.size());
    when(pollRepository.findByOwnerId(any(Long.class), any(Pageable.class))).thenReturn(testData);
    AllReadEvent evtData = service.findPolls(readPollsEvent, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertEquals(evtData.getTotalPages(), new Integer(1));
    assertEquals(evtData.getTotalItems(), new Long(evts.size()));
    assertTrue(evtData.isEntityFound());
  }

  @Test
  public final void testFindPollsNoPolls() {
    if (LOG.isDebugEnabled()) LOG.debug("FindingPolls()");

    ArrayList<Poll> evts = new ArrayList<Poll>();

    Long ownerId = 1L;
    ReadAllEvent readPollsEvent = new ReadAllEvent(ownerId);
    int pageLength = 10;
    int pageNumber = 0;
    Poll testPoll = DatabaseDataFixture.populatePoll1();
    Owner testOwner = new Owner(testPoll.getNodeId());

    Pageable p = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<Poll> testData = new PageImpl<Poll>(evts, p, evts.size());
    when(pollRepository.findByOwnerId(any(Long.class), any(Pageable.class))).thenReturn(testData);
    when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
    AllReadEvent evtData = service.findPolls(readPollsEvent, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertEquals(evtData.getTotalPages(), new Integer(0));
    assertEquals(evtData.getTotalItems(), new Long(evts.size()));
    assertTrue(evtData.isEntityFound());
  }

  @Test
  public final void testFindPollsNoPollsNoOwner() {
    if (LOG.isDebugEnabled()) LOG.debug("FindingPolls()");

    ArrayList<Poll> evts = new ArrayList<Poll>();

    Long ownerId = 1L;
    ReadAllEvent readPollsEvent = new ReadAllEvent(ownerId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable p = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<Poll> testData = new PageImpl<Poll>(evts, p, evts.size());
    when(pollRepository.findByOwnerId(any(Long.class), any(Pageable.class))).thenReturn(testData);
    when(pollRepository.findOne(any(Long.class))).thenReturn(null);
    AllReadEvent evtData = service.findPolls(readPollsEvent, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertNull(evtData.getDetails());
    assertFalse(evtData.isEntityFound());
  }

  @Test
  public final void testFindPollsNullPolls() {
    if (LOG.isDebugEnabled()) LOG.debug("FindingPolls()");

    ArrayList<Poll> evts = new ArrayList<Poll>();
    evts.add(DatabaseDataFixture.populatePoll1());
    evts.add(DatabaseDataFixture.populatePoll2());

    Long ownerId = 1L;
    ReadAllEvent readPollsEvent = new ReadAllEvent(ownerId);
    int pageLength = 10;
    int pageNumber = 0;

    when(pollRepository.findByOwnerId(any(Long.class), any(Pageable.class))).thenReturn(null);
    AllReadEvent evtData = service.findPolls(readPollsEvent, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertNull(evtData.getDetails());
    assertFalse(evtData.isEntityFound());
  }


}
