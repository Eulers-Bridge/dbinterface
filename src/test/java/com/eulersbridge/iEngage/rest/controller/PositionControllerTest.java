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
		String content="{\"positionId\":1,\"name\":\"Test Name\",\"description\":\"Test description\",\"electionId\":123756}";
		String returnedContent="{\"positionId\":"+dets.getNodeId().intValue()+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+"\",\"electionId\":"+dets.getElectionId().intValue()+
								",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/positions\"}]}";
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
		String returnedContent="{\"positionId\":"+dets.getNodeId().intValue()+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+"\",\"electionId\":"+dets.getElectionId().intValue()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/positions\"}]}";
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
		String content="{\"positionId\":1,\"name\":\"Test Name\",\"description\":\"Test description\",\"electionId\":123756}";
		String returnedContent="{\"positionId\":"+dets.getNodeId().intValue()+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+"\",\"electionId\":"+dets.getElectionId().intValue()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/position/"+dets.getNodeId().intValue()+"/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/positions\"}]}";
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

}
