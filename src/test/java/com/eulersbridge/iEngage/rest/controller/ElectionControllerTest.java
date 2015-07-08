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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.elections.CreateElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionCreatedEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionDeletedEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import com.eulersbridge.iEngage.core.events.elections.ElectionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent;
import com.eulersbridge.iEngage.core.events.elections.ReadElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.UpdateElectionEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.AddVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.core.services.ElectionService;
import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.core.services.VotingLocationService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class ElectionControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(ElectionControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.ELECTION_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	ElectionController controller;
	
	@Mock
	ElectionService electionService;
	@Mock
	InstitutionService instService;
	@Mock
	VotingLocationService votingLocationService;
	
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
//		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
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
		when (electionService.readElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
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
		ReadEvent testData=ReadElectionEvent.notFound(dets.getElectionId());
		when (electionService.readElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
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
		String content="{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1,\"introduction\":\"introduction 1\",\"process\":\"process 1\"}";
		String returnedContent="{\"electionId\":"+dets.getElectionId().intValue()+",\"title\":\""+dets.getTitle()+"\",\"start\":"+dets.getStart().intValue()+",\"end\":"+dets.getEnd().intValue()+
								",\"startVoting\":"+dets.getStartVoting().intValue()+",\"endVoting\":"+dets.getEndVoting()+",\"institutionId\":"+dets.getInstitutionId()+
								",\"introduction\":\""+dets.getIntroduction()+"\",\"process\":\""+dets.getProcess()+"\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/election/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/election/1/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/election/1/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/elections\"}]}";
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
		.andExpect(jsonPath("$.introduction",is(dets.getIntroduction())))
		.andExpect(jsonPath("$.process",is(dets.getProcess())))
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
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionCreatedEvent testData=new ElectionCreatedEvent(dets.getElectionId(), dets);
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
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionCreatedEvent testData=new ElectionCreatedEvent(dets.getElectionId(), dets);
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
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateElection()");
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
	 * @throws Exception 
	 */
	@Test
	public final void testFindPreviousElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPreviousElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ReadElectionEvent testData=new ReadElectionEvent(dets.getElectionId(), dets);
		when (electionService.readPreviousElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{electionId}"+ControllerConstants.PREVIOUS_LABEL,dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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
	public final void testFindPreviousElectionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPreviousElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ReadEvent testData=ReadElectionEvent.notFound(dets.getElectionId());
		when (electionService.readPreviousElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{electionId}"+ControllerConstants.PREVIOUS_LABEL,dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#findNextElection(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindNextElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindNextElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ReadElectionEvent testData=new ReadElectionEvent(dets.getElectionId(), dets);
		when (electionService.readNextElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{electionId}"+ControllerConstants.NEXT_LABEL,dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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
	public final void testFindNextElectionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindNextElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ReadEvent testData=ReadElectionEvent.notFound(dets.getElectionId());
		when (electionService.readNextElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{electionId}"+ControllerConstants.NEXT_LABEL,dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
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
		.andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
		.andExpect(status().isOk());
	}

	@Test
	public final void testDeleteElectionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteElection()");
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		DeletedEvent testData=ElectionDeletedEvent.notFound(dets.getElectionId());
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
		DeletedEvent testData=ElectionDeletedEvent.deletionForbidden(dets.getElectionId());
		when (electionService.deleteElection(any(DeleteElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{electionId}/",dets.getElectionId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#updateElection(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Election)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdateElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateElection()");
		Long id=1L;
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		dets.setTitle("Test Election2");
		ElectionUpdatedEvent testData=new ElectionUpdatedEvent(id, dets);
		String content="{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1,\"introduction\":\"introduction 1\",\"process\":\"process 1\"}";
		String returnedContent="{\"electionId\":"+dets.getElectionId().intValue()+",\"title\":\""+dets.getTitle()+"\",\"start\":"+dets.getStart().intValue()+",\"end\":"+dets.getEnd().intValue()+
								",\"startVoting\":"+dets.getStartVoting().intValue()+",\"endVoting\":"+dets.getEndVoting()+",\"institutionId\":"+dets.getInstitutionId()+
								",\"introduction\":\""+dets.getIntroduction()+"\",\"process\":\""+dets.getProcess()+"\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/election/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/election/1/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/election/1/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/elections\"}]}";
		when (electionService.updateElection(any(UpdateElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.start",is(dets.getStart().intValue())))
		.andExpect(jsonPath("$.end",is(dets.getEnd().intValue())))
		.andExpect(jsonPath("$.startVoting",is(dets.getStartVoting().intValue())))
		.andExpect(jsonPath("$.endVoting",is(dets.getEndVoting().intValue())))
		.andExpect(jsonPath("$.electionId",is(dets.getElectionId().intValue())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.introduction",is(dets.getIntroduction())))
		.andExpect(jsonPath("$.process",is(dets.getProcess())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}
	
	@Test
	public void testUpdateElectionNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateElection()");
		Long id=1L;
		String content="{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
		when (electionService.updateElection(any(UpdateElectionEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateElectionBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateElection()");
		Long id=1L;
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionUpdatedEvent testData=new ElectionUpdatedEvent(id, dets);
		String content="{\"electionId1\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
		when (electionService.updateElection(any(UpdateElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateElectionEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateElection()");
		Long id=1L;
		ElectionDetails dets=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionUpdatedEvent testData=new ElectionUpdatedEvent(id, dets);
		when (electionService.updateElection(any(UpdateElectionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateElectionNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateElection()");
		Long id=1L;
		String content="{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
		when (electionService.updateElection(any(UpdateElectionEvent.class))).thenReturn(ElectionUpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testFindElections() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindElections()");
		Long instId=1l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.Election> dets=DatabaseDataFixture.populateElections();
		Iterable<com.eulersbridge.iEngage.database.domain.Election> elections=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.Election> iter=elections.iterator();
		ArrayList<ElectionDetails> eleDets=new ArrayList<ElectionDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.Election article=iter.next();
			eleDets.add(article.toElectionDetails());
		}
		Long numElements=(long) eleDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(null,eleDets,numElements,numPages);
		when (electionService.readElections(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("$totalPages",is(numPages)))
		.andExpect(jsonPath("$foundObjects[0].title",is(eleDets.get(0).getTitle())))
		.andExpect(jsonPath("$foundObjects[0].start",is(eleDets.get(0).getStart().intValue())))
		.andExpect(jsonPath("$foundObjects[0].end",is(eleDets.get(0).getEnd().intValue())))
		.andExpect(jsonPath("$foundObjects[0].startVoting",is(eleDets.get(0).getStartVoting().intValue())))
		.andExpect(jsonPath("$foundObjects[0].endVoting",is(eleDets.get(0).getEndVoting().intValue())))
		.andExpect(jsonPath("$foundObjects[0].electionId",is(eleDets.get(0).getElectionId().intValue())))
		.andExpect(jsonPath("$foundObjects[0].institutionId",is(eleDets.get(0).getInstitutionId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].title",is(eleDets.get(1).getTitle())))
		.andExpect(jsonPath("$foundObjects[1].start",is(eleDets.get(1).getStart().intValue())))
		.andExpect(jsonPath("$foundObjects[1].end",is(eleDets.get(1).getEnd().intValue())))
		.andExpect(jsonPath("$foundObjects[1].startVoting",is(eleDets.get(1).getStartVoting().intValue())))
		.andExpect(jsonPath("$foundObjects[1].endVoting",is(eleDets.get(1).getEndVoting().intValue())))
		.andExpect(jsonPath("$foundObjects[1].electionId",is(eleDets.get(1).getElectionId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].institutionId",is(eleDets.get(1).getInstitutionId().intValue())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindElectionsZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindElections()");
		Long instId=11l;
		ArrayList<ElectionDetails> eleDets=new ArrayList<ElectionDetails>(); 
		Long numElements=(long) eleDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(null,eleDets,numElements,numPages);
		when (electionService.readElections(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("$totalPages",is(numPages)))
		.andDo(print())
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindElectionsNoInst() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindElections()");
		Long instId=11l;
		ElectionsReadEvent testData=ElectionsReadEvent.institutionNotFound();
		when (electionService.readElections(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#updateElection(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Election)}.
	 * @throws Exception 
	 */
	@Test
	public final void testAddLocationToElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingAddLocationToElection()");
		VotingLocationDetails dets=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		Long electionId=3l,votingLocationId=dets.getNodeId();
		
		UpdatedEvent testData=new UpdatedEvent(votingLocationId, dets);
		String returnedContent="{\"votingLocationId\":"+dets.getNodeId().intValue()+",\"name\":\""+dets.getName()+"\",\"information\":\""+dets.getInformation()+"\",\"ownerId\":"+dets.getOwnerId().intValue()+
								",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/votingLocation/237\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/votingLocations\"}]}";
		when (votingLocationService.addVotingLocationToElection(any(AddVotingLocationEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{electionId}/votingLocation/{votingLocationId}",electionId.intValue(),votingLocationId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.votingLocationId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}
}
