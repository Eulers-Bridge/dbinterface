/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.*;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.ForumQuestion;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.repository.ForumQuestionRepository;
import org.junit.Before;
import org.junit.Ignore;
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
public class ForumQuestionEventHandlerTest {
  private static Logger LOG = LoggerFactory.getLogger(EventEventHandlerTest.class);

  @Mock
  ForumQuestionRepository fqRepository;

  ForumQuestionEventHandler service;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    service = new ForumQuestionEventHandler(fqRepository);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ForumQuestionEventHandler#ForumQuestionEventHandler(com.eulersbridge.iEngage.database.repository.ForumQuestionRepository)}.
   */
  @Test
  public final void testForumQuestionEventHandler() {
    assertNotNull("Not yet implemented", service);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ForumQuestionEventHandler#createForumQuestion(com.eulersbridge.iEngage.core.events.forumQuestions.CreateForumQuestionEvent)}.
   */
  @Test
  public final void testCreateForumQuestion() {
    if (LOG.isDebugEnabled()) LOG.debug("CreatingForumQuestion()");
    ForumQuestion testData = DatabaseDataFixture.populateForumQuestion1();
    when(fqRepository.findOne(any(Long.class))).thenReturn(testData);
    when(fqRepository.save(any(ForumQuestion.class))).thenReturn(testData);
    ForumQuestionDetails dets = testData.toForumQuestionDetails();
    CreateForumQuestionEvent createForumQuestionEvent = new CreateForumQuestionEvent(dets);
    ForumQuestionCreatedEvent evtData = service.createForumQuestion(createForumQuestionEvent);
    ForumQuestionDetails returnedDets = (ForumQuestionDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toForumQuestionDetails());
    assertNotNull(evtData.getDetails());
    assertEquals(evtData.getDetails().getNodeId(), returnedDets.getNodeId());
  }

  @Ignore
  @Test
  public final void testCreateEventInstNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("CreatingForumQuestion()");
    ForumQuestion testData = DatabaseDataFixture.populateForumQuestion1();
    Institution testInst = null;
    when(fqRepository.findOne(any(Long.class))).thenReturn(testData);
    ForumQuestionDetails dets = testData.toForumQuestionDetails();
    CreateForumQuestionEvent createEventEvent = new CreateForumQuestionEvent(dets);
    ForumQuestionCreatedEvent evtData = service.createForumQuestion(createEventEvent);
    ForumQuestionDetails returnedDets = (ForumQuestionDetails) evtData.getDetails();
//		assertFalse(evtData.isInstitutionFound());
//		assertEquals(evtData.getEventId(),testData.getNewsFeed().getInstitution().getNodeId());
    assertNull(evtData.getDetails());
  }


  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ForumQuestionEventHandler#readForumQuestion(com.eulersbridge.iEngage.core.events.forumQuestions.ReadForumQuestionEvent)}.
   */
  @Test
  public final void testReadForumQuestion() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingForumQuestion()");
    ForumQuestion testData = DatabaseDataFixture.populateForumQuestion1();
    when(fqRepository.findOne(any(Long.class))).thenReturn(testData);
    ReadForumQuestionEvent readForumQuestionEvent = new ReadForumQuestionEvent(testData.getNodeId());
    ForumQuestionReadEvent evtData = (ForumQuestionReadEvent) service.readForumQuestion(readForumQuestionEvent);
    ForumQuestionDetails returnedDets = (ForumQuestionDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toForumQuestionDetails());
    assertEquals(evtData.getNodeId(), returnedDets.getNodeId());
    assertTrue(evtData.isEntityFound());
  }

  @Test
  public final void testReadForumQuestionNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingForumQuestion()");
    ForumQuestion testData = null;
    Long nodeId = 1l;
    when(fqRepository.findOne(any(Long.class))).thenReturn(testData);
    ReadForumQuestionEvent readForumQuestionEvent = new ReadForumQuestionEvent(nodeId);
    ReadEvent evtData = service.readForumQuestion(readForumQuestionEvent);
    assertNull(evtData.getDetails());
    assertEquals(nodeId, evtData.getNodeId());
    assertFalse(evtData.isEntityFound());
  }


  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ForumQuestionEventHandler#updateForumQuestion(com.eulersbridge.iEngage.core.events.forumQuestions.UpdateForumQuestionEvent)}.
   */
  @Test
  public final void testUpdateForumQuestion() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingForumQuestion()");
    ForumQuestion testData = DatabaseDataFixture.populateForumQuestion1();
    when(fqRepository.findOne(any(Long.class))).thenReturn(testData);
    when(fqRepository.save(any(ForumQuestion.class), anyInt())).thenReturn(testData);
    ForumQuestionDetails dets = testData.toForumQuestionDetails();
    UpdateForumQuestionEvent createForumQuestionEvent = new UpdateForumQuestionEvent(dets.getNodeId(), dets);
    UpdatedEvent evtData = service.updateForumQuestion(createForumQuestionEvent);
    ForumQuestionDetails returnedDets = (ForumQuestionDetails) evtData.getDetails();
    assertEquals(returnedDets, testData.toForumQuestionDetails());
    assertEquals(evtData.getNodeId(), returnedDets.getNodeId());
    assertTrue(evtData.isEntityFound());
    assertNotNull(evtData.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ForumQuestionEventHandler#deleteForumQuestion(com.eulersbridge.iEngage.core.events.forumQuestions.DeleteForumQuestionEvent)}.
   */
  @Test
  public final void testDeleteForumQuestion() {
    if (LOG.isDebugEnabled()) LOG.debug("DeletingForumQuestion()");
    ForumQuestion testData = DatabaseDataFixture.populateForumQuestion1();
    when(fqRepository.findOne(any(Long.class))).thenReturn(testData);
    doNothing().when(fqRepository).delete((any(Long.class)));
    DeleteForumQuestionEvent deleteForumQuestionEvent = new DeleteForumQuestionEvent(testData.getNodeId());
    DeletedEvent evtData = service.deleteForumQuestion(deleteForumQuestionEvent);
    assertTrue(evtData.isEntityFound());
    assertTrue(evtData.isDeletionCompleted());
    assertEquals(testData.getNodeId(), evtData.getNodeId());
  }

  @Test
  public final void testDeleteForumQuestionNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("DeletingForumQuestion()");
    ForumQuestion testData = DatabaseDataFixture.populateForumQuestion1();
    when(fqRepository.findOne(any(Long.class))).thenReturn(null);
    doNothing().when(fqRepository).delete((any(Long.class)));
    DeleteForumQuestionEvent deleteForumQuestionEvent = new DeleteForumQuestionEvent(testData.getNodeId());
    DeletedEvent evtData = service.deleteForumQuestion(deleteForumQuestionEvent);
    assertFalse(evtData.isEntityFound());
    assertFalse(evtData.isDeletionCompleted());
    assertEquals(testData.getNodeId(), evtData.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.ForumQuestionEventHandler#findForumQuestions(com.eulersbridge.iEngage.core.events.forumQuestions.ReadForumQuestionsEvent, org.springframework.data.domain.Sort.Direction, int, int)}.
   */
  @Test
  public final void testFindForumQuestions() {
    if (LOG.isDebugEnabled()) LOG.debug("ReadingForumQuestions()");
    HashMap<Long, ForumQuestion> events = DatabaseDataFixture.populateForumQuestions();
    ArrayList<ForumQuestion> evts = new ArrayList<ForumQuestion>();
    Iterator<ForumQuestion> iter = events.values().iterator();
    while (iter.hasNext()) {
      ForumQuestion na = iter.next();
      evts.add(na);
    }


    Long institutionId = 1l;
    ReadForumQuestionsEvent evt = new ReadForumQuestionsEvent(institutionId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable pageable = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<ForumQuestion> testData = new PageImpl<ForumQuestion>(evts, pageable, evts.size());
    when(fqRepository.findByOwnerId(any(Long.class), any(Pageable.class))).thenReturn(testData);

    ForumQuestionsReadEvent evtData = service.findForumQuestions(evt, Direction.ASC, pageNumber, pageLength);
    assertNotNull(evtData);
    assertEquals(evtData.getTotalPages(), new Integer(1));
    assertEquals(evtData.getTotalEvents(), new Long(evts.size()));
  }

}
