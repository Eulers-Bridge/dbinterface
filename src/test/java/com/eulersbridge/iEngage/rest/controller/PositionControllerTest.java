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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent;
import com.eulersbridge.iEngage.core.events.positions.DeletePositionEvent;
import com.eulersbridge.iEngage.core.events.positions.PositionCreatedEvent;
import com.eulersbridge.iEngage.core.events.positions.PositionDeletedEvent;
import com.eulersbridge.iEngage.core.events.positions.PositionDetails;
import com.eulersbridge.iEngage.core.events.positions.PositionReadEvent;
import com.eulersbridge.iEngage.core.events.positions.PositionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.positions.RequestReadPositionEvent;
import com.eulersbridge.iEngage.core.events.positions.UpdatePositionEvent;
import com.eulersbridge.iEngage.core.services.ElectionService;
import com.eulersbridge.iEngage.core.services.PositionService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

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
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
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
	public final void testCreateEventNoContent() throws Exception 
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
		.andExpect(content().string("true"))
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
