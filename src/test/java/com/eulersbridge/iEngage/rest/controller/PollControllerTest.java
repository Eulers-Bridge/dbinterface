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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.polls.CreatePollAnswerEvent;
import com.eulersbridge.iEngage.core.events.polls.CreatePollEvent;
import com.eulersbridge.iEngage.core.events.polls.DeletePollEvent;
import com.eulersbridge.iEngage.core.events.polls.PollAnswerCreatedEvent;
import com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails;
import com.eulersbridge.iEngage.core.events.polls.PollCreatedEvent;
import com.eulersbridge.iEngage.core.events.polls.PollDeletedEvent;
import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.core.events.polls.PollResultDetails;
import com.eulersbridge.iEngage.core.events.polls.PollResultReadEvent;
import com.eulersbridge.iEngage.core.events.polls.PollUpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.ReadPollEvent;
import com.eulersbridge.iEngage.core.events.polls.ReadPollResultEvent;
import com.eulersbridge.iEngage.core.events.polls.RequestReadPollEvent;
import com.eulersbridge.iEngage.core.events.polls.UpdatePollEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.PollService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.domain.PollResultTemplate;
import com.eulersbridge.iEngage.database.domain.User;
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
	
	@Mock
	UserService userService;
	
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
	
	private String setupReturnedContent(PollDetails dets)
	{
		String returnedContent="{\"nodeId\":"+dets.getNodeId().intValue()+",\"question\":\""+dets.getQuestion()+"\",\"answers\":\""+dets.getAnswers()+
				"\",\"start\":"+dets.getStart()+",\"duration\":"+dets.getDuration()+",\"ownerId\":"+dets.getOwnerId()+",\"creatorId\":"+dets.getCreatorId()+
				",\"numOfComments\":"+dets.getNumOfComments()+",\"numOfAnswers\":"+dets.getNumOfAnswers()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/poll/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/poll/1/previous\"},"+
				"{\"rel\":\"Next\",\"href\":\"http://localhost/api/poll/1/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/poll/1/likedBy/USERID\"},"+
				"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/poll/1/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/poll/1/likes\"},"+
				"{\"rel\":\"Read all\",\"href\":\"http://localhost/api/polls\"}]}";
		return returnedContent;
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
		.andExpect(status().isNotFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#getPollResults(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testGetPollResults() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingGetPollResults()");
		Poll poll=DatabaseDataFixture.populatePoll1();
		Long pollId=poll.getNodeId();
		String answers[]=poll.getAnswers().split(",");
		int numAnswers=answers.length;

		Result<PollResultTemplate> prd = DatabaseDataFixture.populatePollResultDetails1();
		List<com.eulersbridge.iEngage.core.events.polls.PollResult> other=PollResultDetails.toPollResultList(prd.iterator(), numAnswers);
		PollResultDetails dets=new PollResultDetails(pollId,other );
		PollResultReadEvent testData=new PollResultReadEvent(dets.getPollId(),dets);
		when (pollService.readPollResult(any(ReadPollResultEvent.class))).thenReturn(testData);
		if (LOG.isDebugEnabled()) LOG.debug("pollId = "+dets.getNodeId());
		this.mockMvc.perform(get(urlPrefix+"/{pollId}/results/",pollId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.pollId",is(dets.getPollId().intValue())))
		.andExpect(jsonPath("$.answers[0].answer",is(dets.getAnswers().get(0).getAnswer())))
		.andExpect(jsonPath("$.answers[0].count",is(dets.getAnswers().get(0).getCount())))
		.andExpect(jsonPath("$.answers[1].answer",is(dets.getAnswers().get(1).getAnswer())))
		.andExpect(jsonPath("$.answers[1].count",is(dets.getAnswers().get(1).getCount())))
		.andExpect(jsonPath("$.answers[2].answer",is(dets.getAnswers().get(2).getAnswer())))
		.andExpect(jsonPath("$.answers[2].count",is(dets.getAnswers().get(2).getCount())))
		.andExpect(jsonPath("$.answers[3].answer",is(dets.getAnswers().get(3).getAnswer())))
		.andExpect(jsonPath("$.answers[3].count",is(dets.getAnswers().get(3).getCount())))
		.andExpect(jsonPath("$.answers[4].answer",is(dets.getAnswers().get(4).getAnswer())))
		.andExpect(jsonPath("$.answers[4].count",is(dets.getAnswers().get(4).getCount())))
		.andExpect(status().isOk())	;
	}
	@Test
	public final void testGetPollResultsNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPoll()");
		PollDetails dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		ReadEvent testData=ReadPollEvent.notFound(dets.getNodeId());
		when (pollService.readPollResult(any(ReadPollResultEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{pollId}/results/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#createPoll(com.eulersbridge.iEngage.rest.domain.Poll)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreatePoll() throws Exception
	{
		LOG.debug("performingCreatePoll()");
		Poll poll=DatabaseDataFixture.populatePoll1();
		poll.setNumberOfAnswers(623432l);
		poll.setNumberOfComments(543543l);
		PollDetails dets=poll.toPollDetails();
		PollCreatedEvent testData=new PollCreatedEvent(dets);
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		String returnedContent=setupReturnedContent(dets);
		when (pollService.createPoll(any(CreatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.question",is(dets.getQuestion())))
		.andExpect(jsonPath("$.answers",is(dets.getAnswers())))
		.andExpect(jsonPath("$.start",is(dets.getStart().intValue())))
		.andExpect(jsonPath("$.duration",is(dets.getDuration().intValue())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.creatorId",is(dets.getCreatorId().intValue())))
		.andExpect(jsonPath("$.numOfAnswers", is(dets.getNumOfAnswers().intValue())))
		.andExpect(jsonPath("$.numOfComments", is(dets.getNumOfComments().intValue())))
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
		Poll poll=DatabaseDataFixture.populatePoll1();
		poll.setNumberOfAnswers(5l);
		poll.setNumberOfComments(3l);
		PollDetails dets=poll.toPollDetails();
		dets.setQuestion("New Question");
		PollUpdatedEvent testData=new PollUpdatedEvent(id, dets);
		String content="{\"question\":\"http://localhost:8080/\",\"answers\":\"Test Photo\",\"duration\":12345,\"start\":123456,\"ownerId\":3214}";
		String returnedContent=setupReturnedContent(dets);
		when (pollService.updatePoll(any(UpdatePollEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.question",is(dets.getQuestion())))
		.andExpect(jsonPath("$.answers",is(dets.getAnswers())))
		.andExpect(jsonPath("$.start",is(dets.getStart().intValue())))
		.andExpect(jsonPath("$.duration",is(dets.getDuration().intValue())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.creatorId",is(dets.getCreatorId().intValue())))
		.andExpect(jsonPath("$.numOfAnswers", is(dets.getNumOfAnswers().intValue())))
		.andExpect(jsonPath("$.numOfComments", is(dets.getNumOfComments().intValue())))
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
		.andExpect(status().isGone())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#answerPoll(com.eulersbridge.iEngage.rest.domain.PollAnswer)}.
	 * @throws Exception 
	 */
	@Test
	public final void testAnswerPoll() throws Exception
	{
		LOG.debug("performingAnswerPoll()");
		PollAnswerDetails dets=DatabaseDataFixture.populatePollAnswer1().toPollAnswerDetails();
		PollAnswerCreatedEvent testData=new PollAnswerCreatedEvent(dets);
		String content="{\"answererId\":12,\"answerIndex\":3,\"timeStamp\":12345,\"pollId\":123}";
		String returnedContent="{\"nodeId\":"+dets.getNodeId().intValue()+",\"answerIndex\":"+dets.getAnswerIndex()+
								",\"timeStamp\":"+dets.getTimeStamp()+",\"answererId\":"+dets.getAnswererId()+",\"pollId\":"+dets.getPollId()+
								",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/poll/"+dets.getNodeId().intValue()+"\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/poll/"+dets.getNodeId().intValue()+"/previous\"},"+
								"{\"rel\":\"Next\",\"href\":\"http://localhost/api/poll/"+dets.getNodeId().intValue()+"/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/poll/"+dets.getNodeId().intValue()+"/likedBy/USERID\"},"+
								"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/poll/"+dets.getNodeId().intValue()+"/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/poll/"+dets.getNodeId().intValue()+"/likes\"}]}";
		when (pollService.answerPoll(any(CreatePollAnswerEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.answererId",is(dets.getAnswererId().intValue())))
		.andExpect(jsonPath("$.answerIndex",is(dets.getAnswerIndex())))
		.andExpect(jsonPath("$.timeStamp",is(dets.getTimeStamp())))
		.andExpect(jsonPath("$.pollId",is(dets.getPollId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated());		
	}
	@Test
	public final void testAnswerPollInvalidContent() throws Exception
	{
		LOG.debug("performingAnswerPoll()");
		String content="{\"answererId1\":12,\"answerIndex\":3,\"timeStamp\":12345,\"pollId\":123}";
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}
	@Test
	public final void testAnswerPollBadContent() throws Exception
	{
		LOG.debug("performingAnswerPoll()");
		String content="{\"answererId\":null,\"answerIndex\":null,\"timeStamp\":null,\"pollId\":null}";
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}
	@Test
	public final void testAnswerPollNoContent() throws Exception 
	{
		LOG.debug("performingAnswerPoll()");
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())	;		
	}
	@Test
	public final void testAnswerPollAnswererNotFound() throws Exception 
	{
		LOG.debug("performingAnswerPoll()");
		PollAnswerDetails dets=DatabaseDataFixture.populatePollAnswer1().toPollAnswerDetails();
		PollAnswerCreatedEvent testData=PollAnswerCreatedEvent.answererNotFound(dets.getAnswererId());
		String content="{\"answererId\":12,\"answerIndex\":3,\"timeStamp\":12345,\"pollId\":123}";
		when (pollService.answerPoll(any(CreatePollAnswerEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isNotFound())	;		
	}
	@Test
	public final void testAnswerPollPollNotFound() throws Exception 
	{
		LOG.debug("performingAnswerPoll()");
		PollAnswerDetails dets=DatabaseDataFixture.populatePollAnswer1().toPollAnswerDetails();
		PollAnswerCreatedEvent testData=PollAnswerCreatedEvent.pollNotFound(dets.getPollId());
		String content="{\"answererId\":12,\"answerIndex\":3,\"timeStamp\":12345,\"pollId\":123}";
		when (pollService.answerPoll(any(CreatePollAnswerEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isNotFound())	;		
	}
	@Test
	public final void testAnswerPollBadAnswer() throws Exception 
	{
		LOG.debug("performingAnswerPoll()");
		PollAnswerDetails dets=DatabaseDataFixture.populatePollAnswer1().toPollAnswerDetails();
		PollAnswerCreatedEvent testData=PollAnswerCreatedEvent.badAnswer(dets.getAnswerIndex());
		String content="{\"answererId\":12,\"answerIndex\":3,\"timeStamp\":12345,\"pollId\":123}";
		when (pollService.answerPoll(any(CreatePollAnswerEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}
	@Test
	public final void testAnswerPollNullIdReturned() throws Exception 
	{
		LOG.debug("performingAnswerPoll()");
		PollAnswerDetails dets=null;
		PollAnswerCreatedEvent testData=new PollAnswerCreatedEvent(dets);
		String content="{\"answererId\":12,\"answerIndex\":3,\"timeStamp\":12345,\"pollId\":123}";
		when (pollService.answerPoll(any(CreatePollAnswerEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testAnswerPollNullEventReturned() throws Exception 
	{
		LOG.debug("performingAnswerPoll()");
		PollAnswerCreatedEvent testData=null;
		String content="{\"answererId\":12,\"answerIndex\":3,\"timeStamp\":12345,\"pollId\":123}";
		when (pollService.answerPoll(any(CreatePollAnswerEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/123/answer/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#likePoll(java.lang.Long, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testLikePoll() throws Exception
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

	@Test
	public final void testLikedByPollNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());
		
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testLikedByPollGone() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());
		
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#unlikePoll(java.lang.Long, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUnlikePoll() throws Exception
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

    @Test
    public final void testUnLikedByPollNotFound() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());

        when (likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())	;
    }

    @Test
    public final void testUnLikedByPollGone() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());

        when (likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isGone())	;
    }

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#findLikes(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
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

    @Test
    public final void testFindLikesNotFound() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindLikes()");
        Long id=1L;
        Collection<UserDetails> userDetails = new ArrayList<>();
        ReadEvent readPollEvent = ReadEvent.notFound(id);

        LikeableObjectLikesEvent likeableObjectLikesEvent = new LikeableObjectLikesEvent(id, userDetails);


        when (likesService.likes(any(LikesLikeableObjectEvent.class), any(Sort.Direction.class), any(int.class), any(int.class))).thenReturn(likeableObjectLikesEvent);
        when (pollService.requestReadPoll(any(RequestReadPollEvent.class))).thenReturn(readPollEvent);
        this.mockMvc.perform(get(urlPrefix+"/{id}/likes/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#findPhotos(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindPolls() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPolls()");
		Long instId=1l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.Poll> dets=DatabaseDataFixture.populatePolls();
		Iterable<com.eulersbridge.iEngage.database.domain.Poll> thePolls=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.Poll> iter=thePolls.iterator();
		ArrayList<PollDetails> pollDets=new ArrayList<PollDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.Poll poll=iter.next();
			pollDets.add(poll.toPollDetails());
		}
		Long numElements=(long) pollDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(instId,pollDets,numElements,numPages);
		when (pollService.findPolls(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$totalPolls",is(testData.getTotalItems().intValue())))
		.andExpect(jsonPath("$totalPages",is(testData.getTotalPages())))
		.andExpect(jsonPath("$polls[0].nodeId",is(pollDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$polls[0].question",is(pollDets.get(0).getQuestion())))
		.andExpect(jsonPath("$polls[0].answers",is(pollDets.get(0).getAnswers())))
		.andExpect(jsonPath("$polls[0].start",is(pollDets.get(0).getStart().intValue())))
		.andExpect(jsonPath("$polls[0].duration",is(pollDets.get(0).getDuration().intValue())))
		.andExpect(jsonPath("$polls[0].creatorId",is(pollDets.get(0).getCreatorId().intValue())))
		.andExpect(jsonPath("$polls[0].ownerId",is(pollDets.get(0).getOwnerId().intValue())))
		.andExpect(jsonPath("$polls[1].nodeId",is(pollDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$polls[1].question",is(pollDets.get(1).getQuestion())))
		.andExpect(jsonPath("$polls[1].answers",is(pollDets.get(1).getAnswers())))
		.andExpect(jsonPath("$polls[1].start",is(pollDets.get(1).getStart().intValue())))
		.andExpect(jsonPath("$polls[1].duration",is(pollDets.get(1).getDuration().intValue())))
		.andExpect(jsonPath("$polls[1].creatorId",is(pollDets.get(1).getCreatorId().intValue())))
		.andExpect(jsonPath("$polls[1].ownerId",is(pollDets.get(1).getOwnerId().intValue())))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindPollsZeroPolls() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPolls()");
		Long instId=11l;
		ArrayList<PollDetails> pollDets=new ArrayList<PollDetails>(); 
		Long numElements=(long) pollDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(instId,pollDets,numElements,numPages);
		when (pollService.findPolls(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$totalPolls",is(testData.getTotalItems().intValue())))
		.andExpect(jsonPath("$totalPages",is(testData.getTotalPages())))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindPollsNoNewsFeed() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPolls()");
		Long instId=11l;
		AllReadEvent testData=AllReadEvent.notFound(instId);
		when (pollService.findPolls(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testFindPollsNoInstitution() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPolls()");
		Long instId=11l;
		AllReadEvent testData=AllReadEvent.notFound(instId);
		when (pollService.findPolls(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}
}
