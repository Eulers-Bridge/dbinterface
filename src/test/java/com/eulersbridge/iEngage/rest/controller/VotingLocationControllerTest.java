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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

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

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.DeleteVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.UpdateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationCreatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDeletedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationReadEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationUpdatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationsReadEvent;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.VotingLocationService;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(VotingLocationControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.VOTING_LOCATION_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	VotingLocationController controller;
	
	@Mock
	VotingLocationService votingLocationService;
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#VotingLocationController()}.
	 */
	@Test
	public final void testVotingLocationController()
	{
		VotingLocationController vc=new VotingLocationController();
		assertNotNull("Not yet implemented",vc);
	}

	String setupContent(VotingLocationDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"votingLocationId\":"+evtId+",\"name\":\""+dets.getName()+"\",\"information\":\""+dets.getInformation()+"\",\"ownerId\":"+dets.getOwnerId()+"}";
		return content;
	}
	
	String setupInvalidContent(VotingLocationDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"votingLocationId1\":"+evtId+",\"name\":\""+dets.getName()+"\",\"information\":\""+dets.getInformation()+"\",\"logo\":"+dets.getOwnerId()+"}";
		return content;
	}
	
	String setupReturnedContent(VotingLocationDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"votingLocationId\":"+evtId+",\"name\":\""+dets.getName()+
						"\",\"information\":\""+dets.getInformation()+"\",\"ownerId\":"+dets.getOwnerId()+
						",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"},"+
//						"{\"rel\":\"Previous\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/previous\"},"+
//						"{\"rel\":\"Next\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/next\"},"+
						"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}]}";	
		 return content;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#createVotingLocation(com.eulersbridge.iEngage.rest.domain.VotingLocation)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateVotingLocation() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		VotingLocationCreatedEvent testData=new VotingLocationCreatedEvent(dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (votingLocationService.createVotingLocation(any(CreateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.votingLocationId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[1].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreateVotingLocationNullEvt() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		String content=setupContent(dets);
		when (votingLocationService.createVotingLocation(any(CreateVotingLocationEvent.class))).thenReturn(null);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateVotingLocationInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateVotingLocation()");
		VotingLocationCreatedEvent testData=null;
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		String content=setupInvalidContent(dets);
		when (votingLocationService.createVotingLocation(any(CreateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateEventNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateVotingLocation()");
		VotingLocationCreatedEvent testData=null;
		when (votingLocationService.createVotingLocation(any(CreateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateVotingLocationNullNodeId() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		String content=setupContent(dets);
		VotingLocationCreatedEvent testData=new VotingLocationCreatedEvent(dets);
		testData.setNodeId(null);
		when (votingLocationService.createVotingLocation(any(CreateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateVotingLocationFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		String content=setupContent(dets);
		CreatedEvent testData=VotingLocationCreatedEvent.failed(dets);
		testData.setNodeId(null);
		when (votingLocationService.createVotingLocation(any(CreateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateVotingLocationOwnerNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		VotingLocationCreatedEvent testData=VotingLocationCreatedEvent.ownerNotFound(dets.getOwnerId());
		String content=setupContent(dets);
		when (votingLocationService.createVotingLocation(any(CreateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#findVotingLocation(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindVotingLocation() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		VotingLocationReadEvent testData=new VotingLocationReadEvent(dets.getNodeId(),dets);
		String returnedContent=setupReturnedContent(dets);
		when (votingLocationService.readVotingLocation(any(ReadVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{votingLocationId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.votingLocationId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindVotingLocationNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		ReadEvent testData=VotingLocationReadEvent.notFound(dets.getNodeId());
		when (votingLocationService.readVotingLocation(any(ReadVotingLocationEvent.class))).thenReturn(testData);
		if (LOG.isDebugEnabled()) LOG.debug("testData - "+testData);
		this.mockMvc.perform(get(urlPrefix+"/{votingLocationId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#findVotingLocations(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindVotingLocations() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindVotingLocations()");
		Long electionId=1l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.VotingLocation> dets=DatabaseDataFixture.populateVotingLocations();
		Iterable<com.eulersbridge.iEngage.database.domain.VotingLocation> votingLocations=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.VotingLocation> iter=votingLocations.iterator();
		ArrayList<VotingLocationDetails> votingLocationDets=new ArrayList<VotingLocationDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.VotingLocation article=iter.next();
			votingLocationDets.add(article.toVotingLocationDetails());
		}
		Long numElements=(long) votingLocationDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(electionId,votingLocationDets,numElements,numPages);
		when (votingLocationService.findVotingLocations(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",electionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("$totalPages",is(numPages)))
		.andExpect(jsonPath("$foundObjects[0].name",is(votingLocationDets.get(0).getName())))
		.andExpect(jsonPath("$foundObjects[0].information",is(votingLocationDets.get(0).getInformation())))
		.andExpect(jsonPath("$foundObjects[0].votingLocationId",is(votingLocationDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$foundObjects[0].ownerId",is(votingLocationDets.get(0).getOwnerId().intValue())))
		.andExpect(jsonPath("$foundObjects[0].links[0].rel",is("self")))
		.andExpect(jsonPath("$foundObjects[1].name",is(votingLocationDets.get(1).getName())))
		.andExpect(jsonPath("$foundObjects[1].information",is(votingLocationDets.get(1).getInformation())))
		.andExpect(jsonPath("$foundObjects[1].votingLocationId",is(votingLocationDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].ownerId",is(votingLocationDets.get(1).getOwnerId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindVotingLocationsZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindVotingLocations()");
		Long electionId=11l;
		ArrayList<VotingLocationDetails> votingLocationDets=new ArrayList<VotingLocationDetails>(); 
		Long numElements=(long) votingLocationDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(electionId,votingLocationDets,numElements,numPages);
		when (votingLocationService.findVotingLocations(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",electionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("$totalPages",is(numPages)))
		.andDo(print())
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindVotingLocationsNoElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindVotingLocations()");
		Long ownerId=11l;
		VotingLocationsReadEvent testData=VotingLocationsReadEvent.notFound(ownerId);
		when (votingLocationService.findVotingLocations(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",ownerId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#updateVotingLocation(java.lang.Long, com.eulersbridge.iEngage.rest.domain.VotingLocation)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdateVotingLocation() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateVotingLocation()");
		Long id=1L;
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		dets.setName("Test Name2");
		VotingLocationUpdatedEvent testData=new VotingLocationUpdatedEvent(id, dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (votingLocationService.updateVotingLocation(any(UpdateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.votingLocationId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testUpdateVotingLocationNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateVotingLocation()");
		Long id=1L;
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		String content=setupContent(dets);
		when (votingLocationService.updateVotingLocation(any(UpdateVotingLocationEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateVotingLocationBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateVotingLocation()");
		Long id=1L;
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		VotingLocationUpdatedEvent testData=new VotingLocationUpdatedEvent(id, dets);
		String content=setupInvalidContent(dets);
		when (votingLocationService.updateVotingLocation(any(UpdateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateVotingLocationEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateVotingLocation()");
		Long id=1L;
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		VotingLocationUpdatedEvent testData=new VotingLocationUpdatedEvent(id, dets);
		when (votingLocationService.updateVotingLocation(any(UpdateVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateVotingLocationNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateVotingLocation()");
		Long id=1L;
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		String content=setupContent(dets);
		when (votingLocationService.updateVotingLocation(any(UpdateVotingLocationEvent.class))).thenReturn(VotingLocationUpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#deleteVotingLocation(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteVotingLocation() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		VotingLocationDeletedEvent testData=new VotingLocationDeletedEvent(dets.getNodeId());
		when (votingLocationService.deleteVotingLocation(any(DeleteVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{votingLocationId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testDeleteVotingLocationNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		DeletedEvent testData=VotingLocationDeletedEvent.notFound(dets.getNodeId());
		when (votingLocationService.deleteVotingLocation(any(DeleteVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{votingLocationId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteVotingLocationForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteVotingLocation()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		DeletedEvent testData=VotingLocationDeletedEvent.deletionForbidden(dets.getNodeId());
		when (votingLocationService.deleteVotingLocation(any(DeleteVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{votingLocationId}/",dets.getNodeId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#likeVotingLocation(java.lang.Long, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testLikeVotingLocation() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=new LikedEvent(id, user.getEmail(), true);
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);

		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#unlikeVotingLocation(java.lang.Long, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUnlikeVotingLocation() throws Exception
	{
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt= new LikedEvent(id, user.getEmail(), true);

		when(likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isOk())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.VotingLocationController#findLikes(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
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

}
