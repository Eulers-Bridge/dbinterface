/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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

import com.eulersbridge.iEngage.core.events.elections.CreateElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionCreatedEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionDeletedEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import com.eulersbridge.iEngage.core.events.elections.ReadElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent;
import com.eulersbridge.iEngage.core.services.ElectionService;
import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class ElectionControllerTest {

    private static Logger LOG = LoggerFactory.getLogger(ElectionControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.ELECTION_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	ElectionController controller;
	
	@Mock
	ElectionService electionService;
	@Mock
	InstitutionService instService;
	
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
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#ElectionController()}.
	 */
	@Test
	public final void testElectionController() 
	{
		ElectionController elecController=new ElectionController();
		assertNotNull("Not yet implemented",elecController);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#findElection(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ReadElectionEvent testData=new ReadElectionEvent(dets.getElectionId(), dets);
		when (electionService.requestReadElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{electionId}/",dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.start",is(dets.getStart().intValue())))
		.andExpect(jsonPath("$.end",is(dets.getEnd().intValue())))
		.andExpect(jsonPath("$.startVoting",is(dets.getStartVoting().intValue())))
		.andExpect(jsonPath("$.endVoting",is(dets.getEndVoting().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindElectionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ReadElectionEvent testData=ReadElectionEvent.notFound(dets.getElectionId());
		when (electionService.requestReadElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{electionId}/",dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#createElection(com.eulersbridge.iEngage.rest.domain.Election)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionCreatedEvent testData=new ElectionCreatedEvent(dets.getElectionId(), dets);
		String content="{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
		String returnedContent="{\"electionId\":"+dets.getElectionId().intValue()+",\"title\":\""+dets.getTitle()+"\",\"start\":"+dets.getStart().intValue()+",\"end\":"+dets.getEnd().intValue()+
								",\"startVoting\":"+dets.getStartVoting().intValue()+",\"endVoting\":"+dets.getEndVoting()+",\"institutionId\":"+dets.getInstitutionId()+
								",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/election/2\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/election/2/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/election/2/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/elections\"}]}";
		when (electionService.createElection(any(CreateElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.start",is(dets.getStart().intValue())))
		.andExpect(jsonPath("$.end",is(dets.getEnd().intValue())))
		.andExpect(jsonPath("$.startVoting",is(dets.getStartVoting().intValue())))
		.andExpect(jsonPath("$.endVoting",is(dets.getEndVoting().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreateElectionInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		ElectionCreatedEvent testData=null;
		String content="{\"electionId1\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
		when (electionService.createElection(any(CreateElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateElectionNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		ElectionCreatedEvent testData=null;
		when (electionService.createElection(any(CreateElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateElectionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionCreatedEvent testData=ElectionCreatedEvent.institutionNotFound(dets.getInstitutionId());
		String content="{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
		when (electionService.createElection(any(CreateElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCreateElectionNullIdReturned() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionCreatedEvent testData=new ElectionCreatedEvent(null, dets);
		String content="{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
		when (electionService.createElection(any(CreateElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#findPreviousElection(java.lang.Long)}.
	 */
	@Test
	public final void testFindPreviousElection() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#findNextElection(java.lang.Long)}.
	 */
	@Test
	public final void testFindNextElection() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#deleteElection(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionDeletedEvent testData=new ElectionDeletedEvent(dets.getElectionId());
		when (electionService.deleteElection(any(DeleteElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{electionId}/",dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testDeleteElectionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionDeletedEvent testData=ElectionDeletedEvent.notFound(dets.getElectionId());
		when (electionService.deleteElection(any(DeleteElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{electionId}/",dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteElectionForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionDeletedEvent testData=ElectionDeletedEvent.deletionForbidden(dets.getElectionId());
		when (electionService.deleteElection(any(DeleteElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{electionId}/",dets.getElectionId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#updateElection(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Election)}.
	 */
	@Test
	public final void testUpdateElection() 
	{
		fail("Not yet implemented"); // TODO
	}

}