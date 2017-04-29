/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.positions.*;
import com.eulersbridge.iEngage.core.services.ElectionService;
import com.eulersbridge.iEngage.core.services.PositionService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
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
 *
 */
public class PositionControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(PositionControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.POSITION_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	PositionController controller;
	
	@Mock
	ElectionService electionService;
	@Mock
	PositionService positionService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		MappingJackson2HttpMessageConverter converter=RestDataFixture.setUpConverter();
		this.mockMvc = standaloneSetup(controller).setMessageConverters(converter).build();
	}

	String setupContent(PositionDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"positionId\":"+evtId+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+"\",\"electionId\":"+dets.getElectionId().intValue()+"}";
		return content;
	}
	
	String setupInvalidContent(PositionDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"positionId1\":"+evtId+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+"\",\"electionId\":"+dets.getElectionId().intValue()+"}";
		return content;
	}
	
	String setupReturnedContent(PositionDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"positionId\":"+evtId+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+
					   "\",\"electionId\":"+dets.getElectionId().intValue()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"},"+
				"{\"rel\":\"Previous\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/previous\"},"+
				"{\"rel\":\"Next\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/next\"},"+
//				"{\"rel\":\"Liked By\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/likedBy/USERID\"},"+
//				"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/unlikedBy/USERID\"},"+
//				"{\"rel\":\"Likes\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/likes\"},"+
				"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}]}";	
		 return content;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PositionController#PositionController()}.
	 */
	@Test
	public final void testPositionController()
	{
		PositionController positionController=new PositionController();
		assertNotNull("Not yet implemented",positionController);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PositionController#createPosition(com.eulersbridge.iEngage.rest.domain.Position)}.
	 */
	@Test
	public final void testCreatePosition() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		PositionCreatedEvent testData=new PositionCreatedEvent(dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (positionService.createPosition(any(CreatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.positionId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreatePositionNullEvt() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		String content=setupContent(dets);
		when (positionService.createPosition(any(CreatePositionEvent.class))).thenReturn(null);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateEventInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePosition()");
		PositionCreatedEvent testData=null;
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		String content=setupInvalidContent(dets);
		when (positionService.createPosition(any(CreatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePositionNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePosition()");
		PositionCreatedEvent testData=null;
		when (positionService.createPosition(any(CreatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePositionNullNodeId() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		String content=setupContent(dets);
		PositionCreatedEvent testData=new PositionCreatedEvent(dets);
		testData.setNodeId(null);
		when (positionService.createPosition(any(CreatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePositionFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		String content=setupContent(dets);
		CreatedEvent testData=PositionCreatedEvent.failed(dets);
		testData.setNodeId(null);
		when (positionService.createPosition(any(CreatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateElectionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		PositionCreatedEvent testData=PositionCreatedEvent.electionNotFound(dets.getElectionId());
		String content=setupContent(dets);
		when (positionService.createPosition(any(CreatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PositionController#findPosition(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindPosition() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		PositionReadEvent testData=new PositionReadEvent(dets.getNodeId(),dets);
		String returnedContent=setupReturnedContent(dets);
		when (positionService.readPosition(any(RequestReadPositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.positionId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindPositionNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		ReadEvent testData=PositionReadEvent.notFound(dets.getNodeId());
		when (positionService.readPosition(any(RequestReadPositionEvent.class))).thenReturn(testData);
		if (LOG.isDebugEnabled()) LOG.debug("testData - "+testData);
		this.mockMvc.perform(get(urlPrefix+"/{positionId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testFindPositions() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPositions()");
		Long electionId=1l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.Position> dets=DatabaseDataFixture.populatePositions();
		Iterable<com.eulersbridge.iEngage.database.domain.Position> positions=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.Position> iter=positions.iterator();
		ArrayList<PositionDetails> positionDets=new ArrayList<PositionDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.Position article=iter.next();
			positionDets.add(article.toPositionDetails());
		}
		Long numElements=(long) positionDets.size();
		Integer numPages=(positionDets.size()/10)+1;
		AllReadEvent testData=new AllReadEvent(electionId,positionDets,numElements,numPages);
		when (positionService.readPositions(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",electionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("totalPages",is(numPages)))
		.andExpect(jsonPath("foundObjects[0].name",is(positionDets.get(0).getName())))
		.andExpect(jsonPath("foundObjects[0].description",is(positionDets.get(0).getDescription())))
		.andExpect(jsonPath("foundObjects[0].electionId",is(positionDets.get(0).getElectionId().intValue())))
		.andExpect(jsonPath("foundObjects[0].positionId",is(positionDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("foundObjects[1].name",is(positionDets.get(1).getName())))
		.andExpect(jsonPath("foundObjects[1].description",is(positionDets.get(1).getDescription())))
		.andExpect(jsonPath("foundObjects[1].electionId",is(positionDets.get(1).getElectionId().intValue())))
		.andExpect(jsonPath("foundObjects[1].positionId",is(positionDets.get(1).getNodeId().intValue())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindPositionsZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPositions()");
		Long electionId=11l;
		ArrayList<PositionDetails> eleDets=new ArrayList<PositionDetails>(); 
		AllReadEvent testData=new AllReadEvent(electionId,eleDets);
		when (positionService.readPositions(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",electionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindPositionsNoElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPositions()");
		Long electionId=11l;
		AllReadEvent testData=AllReadEvent.notFound(null);
		when (positionService.readPositions(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",electionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PositionController#updatePosition(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Position)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdatePosition() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePosition()");
		Long id=1L;
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		dets.setName("Test Position2");
		PositionUpdatedEvent testData=new PositionUpdatedEvent(id, dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (positionService.updatePosition(any(UpdatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.positionId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testUpdatePositionNullContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePosition()");
		Long id=1L;
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdatePositionNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePosition()");
		Long id=1L;
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		String content=setupContent(dets);
		when (positionService.updatePosition(any(UpdatePositionEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdatePositionBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePosition()");
		Long id=1L;
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		PositionUpdatedEvent testData=new PositionUpdatedEvent(id, dets);
		String content=setupInvalidContent(dets);
		when (positionService.updatePosition(any(UpdatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdatePositionEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePosition()");
		Long id=1L;
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		PositionUpdatedEvent testData=new PositionUpdatedEvent(id, dets);
		when (positionService.updatePosition(any(UpdatePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdatePositionNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePosition()");
		Long id=1L;
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		String content=setupContent(dets);
		when (positionService.updatePosition(any(UpdatePositionEvent.class))).thenReturn(PositionUpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}
	
	@Test
	public void testUpdatePositionFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePosition()");
		Long id=1L;
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		String content=setupContent(dets);
		when (positionService.updatePosition(any(UpdatePositionEvent.class))).thenReturn(UpdatedEvent.failed(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isConflict())	;		
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PositionController#deletePosition(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeletePosition() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		PositionDeletedEvent testData=new PositionDeletedEvent(dets.getNodeId());
		when (positionService.deletePosition(any(DeletePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
		.andExpect(status().isOk())	;
	}
	@Test
	public final void testDeletePositionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		DeletedEvent testData=PositionDeletedEvent.notFound(dets.getNodeId());
		when (positionService.deletePosition(any(DeletePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeletePositionForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePosition()");
		PositionDetails dets=DatabaseDataFixture.populatePosition1().toPositionDetails();
		DeletedEvent testData=PositionDeletedEvent.deletionForbidden(dets.getNodeId());
		when (positionService.deletePosition(any(DeletePositionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{positionId}/",dets.getNodeId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}


}
