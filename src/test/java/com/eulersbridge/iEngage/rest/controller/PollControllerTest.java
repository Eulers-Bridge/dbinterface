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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.CreatePollEvent;
import com.eulersbridge.iEngage.core.events.polls.DeletePollEvent;
import com.eulersbridge.iEngage.core.events.polls.PollCreatedEvent;
import com.eulersbridge.iEngage.core.events.polls.PollDeletedEvent;
import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.core.events.polls.PollUpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.ReadPollEvent;
import com.eulersbridge.iEngage.core.events.polls.RequestReadPollEvent;
import com.eulersbridge.iEngage.core.events.polls.UpdatePollEvent;
import com.eulersbridge.iEngage.core.services.PollService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;


/**
 * @author Greg Newitt
 *
 */
public class PollControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(PollControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.POLL_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	PollController controller;
	
	@Mock
	PollService pollService;
	

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
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#PollController()}.
	 */
	@Test
	public final void testPollController()
	{
		PollController pollController=new PollController();
		assertNotNull("Not yet implemented",pollController);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#findPoll(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindPoll() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		ReadPollEvent testData=new ReadPollEvent(dets.getNodeId(), dets);
		when (pollService.requestReadPoll(any(RequestReadPollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{pollId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.question",is(dets.getQuestion())))
		.andExpect(jsonPath("$.answers",is(dets.getAnswers())))
		.andExpect(jsonPath("$.start",is(dets.getStart().intValue())))
		.andExpect(jsonPath("$.duration",is(dets.getDuration().intValue())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.creatorId",is(dets.getCreatorId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(status().isOk())	;
	}
	@Test
	public final void testFindPollNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		ReadEvent testData=ReadPollEvent.notFound(dets.getNodeId());
		when (pollService.requestReadPoll(any(RequestReadPollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{pollId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#createPoll(com.eulersbridge.iEngage.rest.domain.Poll)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreatePoll() throws Exception
	{
		LOG.debug("performingCreatePoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		PollCreatedEvent testData=new PollCreatedEvent(dets);
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		String returnedContent="{\"nodeId\":"+dets.getNodeId().intValue()+",\"question\":\""+dets.getQuestion()+"\",\"answers\":\""+dets.getAnswers()+
								"\",\"start\":"+dets.getStart()+",\"duration\":"+dets.getDuration()+",\"ownerId\":"+dets.getOwnerId()+",\"creatorId\":"+dets.getCreatorId()+
								",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/poll/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/poll/1/previous\"},"+
								"{\"rel\":\"Next\",\"href\":\"http://localhost/api/poll/1/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/poll/1/likedBy/USERID\"},"+
								"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/poll/1/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/poll/1/likes\"},"+
								"{\"rel\":\"Read all\",\"href\":\"http://localhost/api/polls\"}]}";
		when (pollService.createPoll(any(CreatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.question",is(dets.getQuestion())))
		.andExpect(jsonPath("$.answers",is(dets.getAnswers())))
		.andExpect(jsonPath("$.start",is(dets.getStart().intValue())))
		.andExpect(jsonPath("$.duration",is(dets.getDuration().intValue())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.creatorId",is(dets.getCreatorId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreatePollInvalidContent() throws Exception 
	{
		LOG.debug("performingCreatePoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		PollCreatedEvent testData=new PollCreatedEvent(dets);
		String content="{\"question1\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		when (pollService.createPoll(any(CreatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePollNoContent() throws Exception 
	{
		LOG.debug("performingCreatePoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		PollCreatedEvent testData=new PollCreatedEvent(dets);
		when (pollService.createPoll(any(CreatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePollOwnerNotFound() throws Exception 
	{
		LOG.debug("performingCreatePoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		PollCreatedEvent testData=PollCreatedEvent.ownerNotFound(dets.getOwnerId());
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		when (pollService.createPoll(any(CreatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCreatePollCreatorNotFound() throws Exception 
	{
		LOG.debug("performingCreatePoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		PollCreatedEvent testData=PollCreatedEvent.creatorNotFound(dets.getCreatorId());
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		when (pollService.createPoll(any(CreatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCreatePollNullIdReturned() throws Exception 
	{
		LOG.debug("performingCreatePoll()");
		PollDetails pollDetails=null;
		PollCreatedEvent testData=new PollCreatedEvent(pollDetails);
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		when (pollService.createPoll(any(CreatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePollNullEventReturned() throws Exception 
	{
		LOG.debug("performingCreatePoll()");
		PollCreatedEvent testData=null;
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		when (pollService.createPoll(any(CreatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#updatePoll(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Poll)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdatePoll() throws Exception
	{
		LOG.debug("performingUpdatePoll()");
		Long id=1L;
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		dets.setQuestion("New Question");
		PollUpdatedEvent testData=new PollUpdatedEvent(id, dets);
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		String returnedContent="{\"nodeId\":"+dets.getNodeId().intValue()+",\"question\":\""+dets.getQuestion()+"\",\"answers\":\""+dets.getAnswers()+
				"\",\"start\":"+dets.getStart()+",\"duration\":"+dets.getDuration()+",\"ownerId\":"+dets.getOwnerId()+",\"creatorId\":"+dets.getCreatorId()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/poll/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/poll/1/previous\"},"+
				"{\"rel\":\"Next\",\"href\":\"http://localhost/api/poll/1/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/poll/1/likedBy/USERID\"},"+
				"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/poll/1/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/poll/1/likes\"},"+
				"{\"rel\":\"Read all\",\"href\":\"http://localhost/api/polls\"}]}";
		when (pollService.updatePoll(any(UpdatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.question",is(dets.getQuestion())))
		.andExpect(jsonPath("$.answers",is(dets.getAnswers())))
		.andExpect(jsonPath("$.start",is(dets.getStart().intValue())))
		.andExpect(jsonPath("$.duration",is(dets.getDuration().intValue())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.creatorId",is(dets.getCreatorId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testUpdatePollNotFound() throws Exception
	{
		LOG.debug("performingUpdatePoll()");
		Long id=1L;
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		dets.setQuestion("New Question");
		UpdatedEvent testData=PollUpdatedEvent.notFound(id);
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		when (pollService.updatePoll(any(UpdatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testUpdatePollNullEventReturned() throws Exception
	{
		LOG.debug("performingUpdatePoll()");
		Long id=1L;
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		dets.setQuestion("New Question");
		UpdatedEvent testData=null;
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		when (pollService.updatePoll(any(UpdatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#deletePoll(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeletePoll() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		PollDeletedEvent testData=new PollDeletedEvent(dets.getNodeId());
		when (pollService.deletePoll(any(DeletePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{pollId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}


	@Test
	public final void testDeletePollNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		DeletedEvent testData=PollDeletedEvent.notFound(dets.getNodeId());
		when (pollService.deletePoll(any(DeletePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{pollId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeletePollForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		DeletedEvent testData=PollDeletedEvent.deletionForbidden(dets.getNodeId());
		when (pollService.deletePoll(any(DeletePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{pollId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#likePoll(java.lang.Long, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testLikePoll()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#unlikePoll(java.lang.Long, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testUnlikePoll()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#findLikes(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testFindLikes()
	{
		fail("Not yet implemented"); // TODO
	}

}
