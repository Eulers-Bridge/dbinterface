/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.CandidateCreatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import com.eulersbridge.iEngage.core.events.candidate.CandidateReadEvent;
import com.eulersbridge.iEngage.core.events.candidate.CreateCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.DeleteCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.RequestReadCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.UpdateCandidateEvent;
import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.Position;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.CandidateRepository;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.PositionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

/**
 * @author Greg Newitt
 *
 */
public class CandidateEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(CandidateEventHandlerTest.class);

    @Mock
	PositionRepository positionRepository;
    @Mock
	UserRepository userRepository;
    @Mock
	CandidateRepository candidateRepository;
    @Mock
	ElectionRepository electionRepository;

    CandidateEventHandler service;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new CandidateEventHandler(candidateRepository, userRepository, positionRepository, electionRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#CandidateEventHandler(com.eulersbridge.iEngage.database.repository.CandidateRepository)}.
	 */
	@Test
	public final void testCandidateEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#createCandidate(com.eulersbridge.iEngage.core.events.candidate.CreateCandidateEvent)}.
	 */
	@Test
	public final void testCreateCandidate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		Position testPosition=DatabaseDataFixture.populatePosition1();
		User testUser=DatabaseDataFixture.populateUserGnewitt();
		when(positionRepository.findOne(any(Long.class))).thenReturn(testPosition);
		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
		when(candidateRepository.save(any(Candidate.class))).thenReturn(testData);
		CandidateDetails dets=testData.toCandidateDetails();
		CreateCandidateEvent createPositionEvent=new CreateCandidateEvent(dets);
		CreatedEvent evtData = service.createCandidate(createPositionEvent);
		CandidateDetails returnedDets = (CandidateDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toCandidateDetails());
		assertNotNull(evtData.getNodeId());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#createCandidate(com.eulersbridge.iEngage.core.events.candidate.CreateCandidateEvent)}.
	 */
	@Test
	public final void testCreateCandidateFailed()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		Position testPosition=DatabaseDataFixture.populatePosition1();
		User testUser=DatabaseDataFixture.populateUserGnewitt();
		when(positionRepository.findOne(any(Long.class))).thenReturn(testPosition);
		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
		when(candidateRepository.save(any(Candidate.class))).thenReturn(null);
		CandidateDetails dets=testData.toCandidateDetails();
		CreateCandidateEvent createPositionEvent=new CreateCandidateEvent(dets);
		CreatedEvent evtData = service.createCandidate(createPositionEvent);
		CandidateDetails returnedDets = (CandidateDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toCandidateDetails());
		assertTrue(evtData.isFailed());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#createPosition(com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent)}.
	 */
	@Test
	public final void testCreateCandidatePositionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		Position testPosition=null;
		User testUser=DatabaseDataFixture.populateUserGnewitt();
		when(positionRepository.findOne(any(Long.class))).thenReturn(testPosition);
		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
		when(candidateRepository.save(any(Candidate.class))).thenReturn(testData);
		CandidateDetails dets=testData.toCandidateDetails();
		CreateCandidateEvent createPositionEvent=new CreateCandidateEvent(dets);
		CandidateCreatedEvent evtData = (CandidateCreatedEvent) service.createCandidate(createPositionEvent);
		assertFalse(evtData.isPositionFound());
		assertEquals(evtData.getFailedId(),testData.getPosition().getNodeId());
		assertNull(evtData.getDetails());
	}

	@Test
	public final void testCreateCandidatePositionIdNull() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		Position testPosition=testData.getPosition();
		testPosition.setNodeId(null);
		testData.setPosition(testPosition);
		User testUser=DatabaseDataFixture.populateUserGnewitt();
		when(positionRepository.findOne(any(Long.class))).thenReturn(testPosition);
		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
		when(candidateRepository.save(any(Candidate.class))).thenReturn(testData);
		CandidateDetails dets=testData.toCandidateDetails();
		CreateCandidateEvent createPositionEvent=new CreateCandidateEvent(dets);
		CandidateCreatedEvent evtData = (CandidateCreatedEvent) service.createCandidate(createPositionEvent);
		assertFalse(evtData.isPositionFound());
		assertEquals(evtData.getFailedId(),testData.getPosition().getNodeId());
		assertNull(evtData.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#createPosition(com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent)}.
	 */
	@Test
	public final void testCreateCandidateUserNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		Position testInst=null;
		when(positionRepository.findOne(any(Long.class))).thenReturn(testInst);
		when(candidateRepository.save(any(Candidate.class))).thenReturn(testData);
		CandidateDetails dets=testData.toCandidateDetails();
		CreateCandidateEvent createPositionEvent=new CreateCandidateEvent(dets);
		CandidateCreatedEvent evtData = (CandidateCreatedEvent) service.createCandidate(createPositionEvent);
		assertFalse(evtData.isUserFound());
		assertEquals(evtData.getFailedId(),testData.getUser().getNodeId());
		assertNull(evtData.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#createPosition(com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent)}.
	 */
	@Test
	public final void testCreateCandidateUserIDNull() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		User user=testData.getUser();
		user.setNodeId(null);
		testData.setUser(user);
		when(candidateRepository.save(any(Candidate.class))).thenReturn(testData);
		CandidateDetails dets=testData.toCandidateDetails();
		CreateCandidateEvent createPositionEvent=new CreateCandidateEvent(dets);
		CandidateCreatedEvent evtData = (CandidateCreatedEvent) service.createCandidate(createPositionEvent);
		assertFalse(evtData.isUserFound());
		assertEquals(evtData.getFailedId(),testData.getUser().getNodeId());
		assertNull(evtData.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#requestReadCandidate(com.eulersbridge.iEngage.core.events.candidate.RequestReadCandidateEvent)}.
	 */
	@Test
	public final void testRequestReadCandidate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		when(candidateRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadCandidateEvent requestReadCandidateEvent=new RequestReadCandidateEvent(testData.getNodeId());
		CandidateReadEvent evtData = (CandidateReadEvent) service.requestReadCandidate(requestReadCandidateEvent);
		CandidateDetails returnedDets = (CandidateDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toCandidateDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#requestReadCandidate(com.eulersbridge.iEngage.core.events.candidate.RequestReadCandidateEvent)}.
	 */
	@Test
	public final void testReadCandidateNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingCandidate()");
		Candidate testData=null;
		Long nodeId=23l;
		when(candidateRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadCandidateEvent requestReadCandidateEvent=new RequestReadCandidateEvent(nodeId);
		ReadEvent evtData = service.requestReadCandidate(requestReadCandidateEvent);
		CandidateDetails returnedDets = (CandidateDetails)evtData.getDetails();
		assertNull(returnedDets);
		assertEquals(evtData.getNodeId(),nodeId);
		assertFalse(evtData.isEntityFound());
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#updateCandidate(com.eulersbridge.iEngage.core.events.candidate.UpdateCandidateEvent)}.
	 */
	@Test
	public final void testUpdateCandidate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		when(candidateRepository.findOne(any(Long.class))).thenReturn(testData);
		when(candidateRepository.save(any(Candidate.class))).thenReturn(testData);
		CandidateDetails dets=testData.toCandidateDetails();
		UpdateCandidateEvent createElectionEvent=new UpdateCandidateEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateCandidate(createElectionEvent);
		CandidateDetails returnedDets = (CandidateDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toCandidateDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#updateCandidate(com.eulersbridge.iEngage.core.events.candidates.UpdateCandidateEvent)}.
	 */
	@Test
	public final void testUpdateCandidateNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		when(candidateRepository.findOne(any(Long.class))).thenReturn(null);
		when(candidateRepository.save(any(Candidate.class))).thenReturn(testData);
		CandidateDetails dets=testData.toCandidateDetails();
		UpdateCandidateEvent createCandidateEvent=new UpdateCandidateEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateCandidate(createCandidateEvent);
		assertNull(evtData.getDetails());
		assertEquals(evtData.getNodeId(),testData.getNodeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#deleteCandidate(com.eulersbridge.iEngage.core.events.candidate.DeleteCandidateEvent)}.
	 */
	@Test
	public final void testDeleteCandidate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		when(candidateRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(candidateRepository).delete((any(Long.class)));
		DeleteCandidateEvent deleteCandidateEvent=new DeleteCandidateEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteCandidate(deleteCandidateEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.CandidateEventHandler#deleteCandidate(com.eulersbridge.iEngage.core.events.candidates.DeleteCandidateEvent)}.
	 */
	@Test
	public final void testDeleteCandidateNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingCandidate()");
		Candidate testData=DatabaseDataFixture.populateCandidate1();
		when(candidateRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(candidateRepository).delete((any(Long.class)));
		DeleteCandidateEvent deleteCandidateEvent=new DeleteCandidateEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteCandidate(deleteCandidateEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}


}
