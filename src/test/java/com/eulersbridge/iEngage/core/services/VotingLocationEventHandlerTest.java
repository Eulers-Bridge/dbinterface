/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import com.eulersbridge.iEngage.core.events.votingLocation.AddVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.DeleteVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.RemoveVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.UpdateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationAddedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationCreatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationReadEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationRemovedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationsReadEvent;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.VotingLocation;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.VotingLocationRepository;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(VotingLocationEventHandlerTest.class);

    @Mock
	VotingLocationRepository votingLocationRepository;
    @Mock
	OwnerRepository ownerRepository;
    @Mock
	ElectionRepository electionRepository;

    VotingLocationEventHandler service;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new VotingLocationEventHandler(votingLocationRepository, electionRepository, ownerRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#VotingLocationEventHandler(com.eulersbridge.iEngage.database.repository.VotingLocationRepository, com.eulersbridge.iEngage.database.repository.OwnerRepository)}.
	 */
	@Test
	public final void testVotingLocationEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#readVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationEvent)}.
	 */
	@Test
	public final void testReadVotingLocation()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		when(votingLocationRepository.findOne(any(Long.class))).thenReturn(testData);
		ReadVotingLocationEvent requestReadVotingLocationEvent=new ReadVotingLocationEvent(testData.getNodeId());
		VotingLocationReadEvent evtData = (VotingLocationReadEvent) service.readVotingLocation(requestReadVotingLocationEvent);
		VotingLocationDetails returnedDets = (VotingLocationDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toVotingLocationDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#requestReadVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.RequestReadVotingLocationEvent)}.
	 */
	@Test
	public final void testReadVotingLocationNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingLocation()");
		VotingLocation testData=null;
		Long nodeId=23l;
		when(votingLocationRepository.findOne(any(Long.class))).thenReturn(testData);
		ReadVotingLocationEvent requestReadVotingLocationEvent=new ReadVotingLocationEvent(nodeId);
		ReadEvent evtData = service.readVotingLocation(requestReadVotingLocationEvent);
		VotingLocationDetails returnedDets = (VotingLocationDetails)evtData.getDetails();
		assertNull(returnedDets);
		assertEquals(evtData.getNodeId(),nodeId);
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#createVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent)}.
	 */
	@Test
	public final void testCreateVotingLocation()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		Owner testOwner=new Owner(DatabaseDataFixture.populateUserGnewitt().getNodeId());
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
		when(votingLocationRepository.save(any(VotingLocation.class))).thenReturn(testData);
		VotingLocationDetails dets=testData.toVotingLocationDetails();
		CreateVotingLocationEvent createPositionEvent=new CreateVotingLocationEvent(dets);
		CreatedEvent evtData = service.createVotingLocation(createPositionEvent);
		VotingLocationDetails returnedDets = (VotingLocationDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toVotingLocationDetails());
		assertNotNull(evtData.getNodeId());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#createVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent)}.
	 */
	@Test
	public final void testCreateVotingLocationFailed()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		Owner testOwner=new Owner(DatabaseDataFixture.populateUserGnewitt().getNodeId());
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
		when(votingLocationRepository.save(any(VotingLocation.class))).thenReturn(null);
		VotingLocationDetails dets=testData.toVotingLocationDetails();
		CreateVotingLocationEvent createPositionEvent=new CreateVotingLocationEvent(dets);
		CreatedEvent evtData = service.createVotingLocation(createPositionEvent);
		VotingLocationDetails returnedDets = (VotingLocationDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toVotingLocationDetails());
		assertTrue(evtData.isFailed());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#createPosition(com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent)}.
	 */
	@Test
	public final void testCreateVotingLocationOwnerNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		Owner testInst=null;
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testInst);
		when(votingLocationRepository.save(any(VotingLocation.class))).thenReturn(testData);
		VotingLocationDetails dets=testData.toVotingLocationDetails();
		CreateVotingLocationEvent createPositionEvent=new CreateVotingLocationEvent(dets);
		VotingLocationCreatedEvent evtData = (VotingLocationCreatedEvent) service.createVotingLocation(createPositionEvent);
		assertFalse(evtData.isOwnerFound());
		assertNull(evtData.getNodeId());
		assertNull(evtData.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#createPosition(com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent)}.
	 */
	@Test
	public final void testCreateVotingLocationOwnerIDNull() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		Owner user=testData.getOwner();
		user.setNodeId(null);
		testData.setOwner(user);
		when(votingLocationRepository.save(any(VotingLocation.class))).thenReturn(testData);
		VotingLocationDetails dets=testData.toVotingLocationDetails();
		CreateVotingLocationEvent createPositionEvent=new CreateVotingLocationEvent(dets);
		VotingLocationCreatedEvent evtData = (VotingLocationCreatedEvent) service.createVotingLocation(createPositionEvent);
		assertFalse(evtData.isOwnerFound());
		assertEquals(evtData.getNodeId(),testData.getOwner().getNodeId());
		assertNull(evtData.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#deleteVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.DeleteVotingLocationEvent)}.
	 */
	@Test
	public final void testDeleteVotingLocation()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		when(votingLocationRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(votingLocationRepository).delete((any(Long.class)));
		DeleteVotingLocationEvent deleteVotingLocationEvent=new DeleteVotingLocationEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteVotingLocation(deleteVotingLocationEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#deleteVotingLocation(com.eulersbridge.iEngage.core.events.votingLocations.DeleteVotingLocationEvent)}.
	 */
	@Test
	public final void testDeleteVotingLocationNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		when(votingLocationRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(votingLocationRepository).delete((any(Long.class)));
		DeleteVotingLocationEvent deleteVotingLocationEvent=new DeleteVotingLocationEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteVotingLocation(deleteVotingLocationEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#updateVotingLocation(com.eulersbridge.iEngage.core.events.votingLocation.UpdateVotingLocationEvent)}.
	 */
	@Test
	public final void testUpdateVotingLocation()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		when(votingLocationRepository.findOne(any(Long.class))).thenReturn(testData);
		when(votingLocationRepository.save(any(VotingLocation.class))).thenReturn(testData);
		VotingLocationDetails dets=testData.toVotingLocationDetails();
		UpdateVotingLocationEvent createElectionEvent=new UpdateVotingLocationEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateVotingLocation(createElectionEvent);
		VotingLocationDetails returnedDets = (VotingLocationDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toVotingLocationDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#updateVotingLocation(com.eulersbridge.iEngage.core.events.votingLocations.UpdateVotingLocationEvent)}.
	 */
	@Test
	public final void testUpdateVotingLocationNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingVotingLocation()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		when(votingLocationRepository.findOne(any(Long.class))).thenReturn(null);
		when(votingLocationRepository.save(any(VotingLocation.class))).thenReturn(testData);
		VotingLocationDetails dets=testData.toVotingLocationDetails();
		UpdateVotingLocationEvent createVotingLocationEvent=new UpdateVotingLocationEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateVotingLocation(createVotingLocationEvent);
		assertNull(evtData.getDetails());
		assertEquals(evtData.getNodeId(),testData.getNodeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#findVotingLocations(com.eulersbridge.iEngage.core.events.ReadAllEvent, org.springframework.data.domain.Sort.Direction, int, int)}.
	 */
	@Test
	public final void testFindVotingLocations()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingLocations()");
		HashMap<Long, VotingLocation> events = DatabaseDataFixture.populateVotingLocations();
		ArrayList<VotingLocation> evts=new ArrayList<VotingLocation>();
		Iterator<VotingLocation> iter=events.values().iterator();
		while (iter.hasNext())
		{
			VotingLocation na=iter.next();
			evts.add(na);
		}

		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<VotingLocation> testData=new PageImpl<VotingLocation>(evts,pageable,evts.size());
		when(votingLocationRepository.findByInstitutionId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		VotingLocationsReadEvent evtData = service.findVotingLocations(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages(),new Integer(1));
		assertEquals(evtData.getTotalItems(),new Long(evts.size()));
	}

	@Test
	public final void testReadVotingLocationsNoneAvailable()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingLocations()");
		ArrayList<VotingLocation> evts=new ArrayList<VotingLocation>();
		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<VotingLocation> testData=new PageImpl<VotingLocation>(evts,pageable,evts.size());
		when(votingLocationRepository.findByInstitutionId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		Owner inst=new Owner(DatabaseDataFixture.populateElection1().getNodeId());
		when(ownerRepository.findOne(any(Long.class))).thenReturn(inst);
				
		VotingLocationsReadEvent evtData = service.findVotingLocations(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages().intValue(),0);
		assertEquals(evtData.getTotalItems().longValue(),0);
	}

	@Test
	public final void testReadVotingLocationsNoValidInst()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingLocations()");
		ArrayList<VotingLocation> evts=new ArrayList<VotingLocation>();
		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<VotingLocation> testData=new PageImpl<VotingLocation>(evts,pageable,evts.size());
		when(votingLocationRepository.findByInstitutionId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		when(ownerRepository.findOne(any(Long.class))).thenReturn(null);
				
		VotingLocationsReadEvent evtData = service.findVotingLocations(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertFalse(evtData.isEntityFound());
		assertEquals(evtData.getTotalPages(),null);
		assertEquals(evtData.getTotalItems(),null);
	}

	@Test
	public final void testReadVotingLocationsNullReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingLocations()");
		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		
		Page<VotingLocation> testData=null;
		when(votingLocationRepository.findByInstitutionId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		int pageLength=10;
		int pageNumber=0;
		VotingLocationsReadEvent evtData = service.findVotingLocations(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertFalse(evtData.isEntityFound());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.VotingLocationEventHandler#findVotingLocations(com.eulersbridge.iEngage.core.events.ReadAllEvent, org.springframework.data.domain.Sort.Direction, int, int)}.
	 */
	@Test
	public final void testFindVotingBooths()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingBooths()");
		HashMap<Long, VotingLocation> events = DatabaseDataFixture.populateVotingLocations();
		ArrayList<VotingLocation> evts=new ArrayList<VotingLocation>();
		Iterator<VotingLocation> iter=events.values().iterator();
		while (iter.hasNext())
		{
			VotingLocation na=iter.next();
			evts.add(na);
		}

		
		Long electionId=1l;
		ReadAllEvent evt=new ReadAllEvent(electionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<VotingLocation> testData=new PageImpl<VotingLocation>(evts,pageable,evts.size());
		when(votingLocationRepository.findByElectionId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		VotingLocationsReadEvent evtData = service.findVotingBooths(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages(),new Integer(1));
		assertEquals(evtData.getTotalItems(),new Long(evts.size()));
	}

	@Test
	public final void testFindVotingBoothsNoneAvailable()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingBooths()");
		ArrayList<VotingLocation> evts=new ArrayList<VotingLocation>();
		
		Long electionId=1l;
		ReadAllEvent evt=new ReadAllEvent(electionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<VotingLocation> testData=new PageImpl<VotingLocation>(evts,pageable,evts.size());
		when(votingLocationRepository.findByElectionId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		Owner inst=new Owner(DatabaseDataFixture.populateElection1().getNodeId());
		when(ownerRepository.findOne(any(Long.class))).thenReturn(inst);
				
		VotingLocationsReadEvent evtData = service.findVotingBooths(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages().intValue(),0);
		assertEquals(evtData.getTotalItems().longValue(),0);
	}

	@Test
	public final void testFindVotingBoothsNoValidInst()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingLocations()");
		ArrayList<VotingLocation> evts=new ArrayList<VotingLocation>();
		
		Long electionId=1l;
		ReadAllEvent evt=new ReadAllEvent(electionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<VotingLocation> testData=new PageImpl<VotingLocation>(evts,pageable,evts.size());
		when(votingLocationRepository.findByElectionId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		when(ownerRepository.findOne(any(Long.class))).thenReturn(null);
				
		VotingLocationsReadEvent evtData = service.findVotingBooths(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertFalse(evtData.isEntityFound());
		assertEquals(evtData.getTotalPages(),null);
		assertEquals(evtData.getTotalItems(),null);
	}

	@Test
	public final void testFindVotingBoothsNullReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingVotingLocations()");
		
		Long electionId=1l;
		ReadAllEvent evt=new ReadAllEvent(electionId);
		
		Page<VotingLocation> testData=null;
		when(votingLocationRepository.findByElectionId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		int pageLength=10;
		int pageNumber=0;
		VotingLocationsReadEvent evtData = service.findVotingBooths(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertFalse(evtData.isEntityFound());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#addVotingLocationToElection(com.eulersbridge.iEngage.core.events.votingLocations.AddVotingLocationEvent)}.
	 */
	@Test
	public final void testAddVotingLocationToElection()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AddingVotingLocationToElection()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		Election electionData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(electionData);
		when(votingLocationRepository.findOne(any(Long.class))).thenReturn(testData);
		when(votingLocationRepository.addElection(any(Long.class),any(Long.class))).thenReturn(testData);
		ElectionDetails dets=electionData.toElectionDetails();
		AddVotingLocationEvent createElectionEvent=new AddVotingLocationEvent(testData.getNodeId(), dets.getElectionId());
		UpdatedEvent evtData = service.addVotingLocationToElection(createElectionEvent);
		assertEquals(evtData.getDetails(),testData.toVotingLocationDetails());
		assertTrue(evtData.isEntityFound());
		assertTrue(((VotingLocationAddedEvent)evtData).isElectionFound());
		assertTrue(((VotingLocationAddedEvent)evtData).isVotingLocationFound());
		assertEquals(evtData.getNodeId(),testData.getNodeId());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#removeVotingLocationFromElection(com.eulersbridge.iEngage.core.events.votingLocations.RemoveVotingLocationEvent)}.
	 */
	@Test
	public final void testRemoveVotingLocationFromElection()
	{
		if (LOG.isDebugEnabled()) LOG.debug("RemovingVotingLocationToElection()");
		VotingLocation testData=DatabaseDataFixture.populateVotingLocation1();
		Election electionData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(electionData);
		when(votingLocationRepository.findOne(any(Long.class))).thenReturn(testData);
		when(votingLocationRepository.deleteElection(any(Long.class),any(Long.class))).thenReturn(testData);
		ElectionDetails dets=electionData.toElectionDetails();
		RemoveVotingLocationEvent createElectionEvent=new RemoveVotingLocationEvent(testData.getNodeId(), dets.getElectionId());
		DeletedEvent evtData = service.removeVotingLocationFromElection(createElectionEvent);
		assertNull(evtData.getDetails());
		assertTrue(((VotingLocationRemovedEvent)evtData).isElectionFound());
		assertTrue(((VotingLocationRemovedEvent)evtData).isVotingLocationFound());
		assertEquals(evtData.getNodeId(),electionData.getNodeId());
	}
	
}
