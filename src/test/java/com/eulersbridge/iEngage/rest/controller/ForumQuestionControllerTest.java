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

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.CreateForumQuestionEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.DeleteForumQuestionEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionCreatedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDeletedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails;
import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionReadEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionsReadEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ReadForumQuestionEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ReadForumQuestionsEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.UpdateForumQuestionEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.ForumQuestionService;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class ForumQuestionControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(ForumQuestionControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.FORUM_QUESTION_LABEL;
    private String urlPrefix2=ControllerConstants.API_PREFIX+ControllerConstants.FORUM_QUESTIONS_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	ForumQuestionController controller;
	
	@Mock
	ForumQuestionService service;
	
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#ForumQuestionController()}.
	 */
	@Test
	public final void testForumQuestionController()
	{
		ForumQuestionController fqc=new ForumQuestionController();
		assertNotNull("Not yet implemented",fqc);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#createForumQuestion(com.eulersbridge.iEngage.rest.domain.ForumQuestion)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateForumQuestion() throws Exception
	{
		LOG.debug("performingCreateForumQuestion()");
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		ForumQuestionCreatedEvent testData=new ForumQuestionCreatedEvent(dets);
		String content="{\"question\":\"This question.\"}";
		String returnedContent="{\"forumQuestionId\":"+dets.getNodeId().intValue()+",\"question\":\""+dets.getQuestion()+
								"\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/forumQuestion/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/forumQuestion/1/previous\"},"+
								"{\"rel\":\"Next\",\"href\":\"http://localhost/api/forumQuestion/1/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/forumQuestion/1/likedBy/USERID\"},"+
								"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/forumQuestion/1/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/forumQuestion/1/likes\"},"+
								"{\"rel\":\"Read all\",\"href\":\"http://localhost/api/forumQuestions\"}]}";
		when (service.createForumQuestion(any(CreateForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.forumQuestionId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.question",is(dets.getQuestion())))
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
	public final void testCreateForumQuestionInvalidContent() throws Exception 
	{
		LOG.debug("performingCreateForumQuestion()");
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		ForumQuestionCreatedEvent testData=new ForumQuestionCreatedEvent(dets);
		String content="{\"forumQuestion1\":\"This question.\"}";
		when (service.createForumQuestion(any(CreateForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateForumQuestionNoContent() throws Exception 
	{
		LOG.debug("performingCreateForumQuestion()");
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		ForumQuestionCreatedEvent testData=new ForumQuestionCreatedEvent(dets);
		when (service.createForumQuestion(any(CreateForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateForumQuestionNullIdReturned() throws Exception 
	{
		LOG.debug("performingCreateForumQuestion()");
		ForumQuestionDetails dets=null;
		ForumQuestionCreatedEvent testData=new ForumQuestionCreatedEvent(dets);
		String content="{\"question\":\"This question.\"}";
		when (service.createForumQuestion(any(CreateForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateForumQuestionNullEventReturned() throws Exception 
	{
		LOG.debug("performingCreateForumQuestion()");
		ForumQuestionCreatedEvent testData=null;
		String content="{\"question\":\"This question.\"}";
		when (service.createForumQuestion(any(CreateForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#findForumQuestions(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindForumQuestions() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindForumQuestions()");
		Long nfId=1l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.ForumQuestion> dets=DatabaseDataFixture.populateForumQuestions();
		Iterable<com.eulersbridge.iEngage.database.domain.ForumQuestion> theQuestions=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.ForumQuestion> iter=theQuestions.iterator();
		ArrayList<ForumQuestionDetails> fqDets=new ArrayList<ForumQuestionDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.ForumQuestion fQuest=iter.next();
			fqDets.add(fQuest.toForumQuestionDetails());
		}
		ForumQuestionsReadEvent testData=new ForumQuestionsReadEvent(nfId,fqDets);
		testData.setTotalPages(1);
		testData.setTotalEvents(new Long(fqDets.size()));
		when (service.findForumQuestions(any(ReadForumQuestionsEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix2+"/{nfId}",nfId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$totalForumQuestions",is(testData.getTotalEvents().intValue())))
		.andExpect(jsonPath("$totalPages",is(testData.getTotalPages())))
		.andExpect(jsonPath("$forumQuestions[0].forumQuestionId",is(fqDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$forumQuestions[0].question",is(fqDets.get(0).getQuestion())))
		.andExpect(jsonPath("$forumQuestions[1].forumQuestionId",is(fqDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$forumQuestions[1].question",is(fqDets.get(1).getQuestion())))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindForumQuestionsZeroPolls() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindForumQuestions()");
		Long instId=11l;
		ArrayList<ForumQuestionDetails> eleDets=new ArrayList<ForumQuestionDetails>(); 
		ForumQuestionsReadEvent testData=new ForumQuestionsReadEvent(instId,eleDets);
		when (service.findForumQuestions(any(ReadForumQuestionsEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindForumQuestionsNoNewsFeed() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindForumQuestions()");
		Long instId=11l;
		ForumQuestionsReadEvent testData=ForumQuestionsReadEvent.newsFeedNotFound();
		when (service.findForumQuestions(any(ReadForumQuestionsEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testFindForumQuestionsNoInstitution() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindForumQuestions()");
		Long instId=11l;
		ForumQuestionsReadEvent testData=ForumQuestionsReadEvent.institutionNotFound();
		when (service.findForumQuestions(any(ReadForumQuestionsEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#readForumQuestion(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testReadForumQuestion() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingReadForumQuestion()");
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		ForumQuestionReadEvent testData=new ForumQuestionReadEvent(dets.getNodeId(), dets);
		when (service.readForumQuestion(any(ReadForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{forumQuestionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.forumQuestionId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.question",is(dets.getQuestion())))
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
	public final void testReadForumQuestionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingReadForumQuestion()");
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		ReadEvent testData=ForumQuestionReadEvent.notFound(dets.getNodeId());
		when (service.readForumQuestion(any(ReadForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{forumQuestionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#updateForumQuestion(java.lang.Long, com.eulersbridge.iEngage.rest.domain.ForumQuestion)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdateForumQuestion() throws Exception
	{
		LOG.debug("performingUpdateForumQuestion()");
		Long id=1L;
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		dets.setQuestion("New Question");
		ForumQuestionUpdatedEvent testData=new ForumQuestionUpdatedEvent(id, dets);
		String content="{\"question\":\"This question.\"}";
		String returnedContent="{\"forumQuestionId\":"+dets.getNodeId().intValue()+",\"question\":\""+dets.getQuestion()+
								"\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/forumQuestion/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/forumQuestion/1/previous\"},"+
								"{\"rel\":\"Next\",\"href\":\"http://localhost/api/forumQuestion/1/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/forumQuestion/1/likedBy/USERID\"},"+
								"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/forumQuestion/1/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/forumQuestion/1/likes\"},"+
								"{\"rel\":\"Read all\",\"href\":\"http://localhost/api/forumQuestions\"}]}";
		when (service.updateForumQuestion(any(UpdateForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.forumQuestionId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.question",is(dets.getQuestion())))
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
	public final void testUpdateForumQuestionNotFound() throws Exception
	{
		LOG.debug("performingUpdateForumQuestion()");
		Long id=1L;
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		dets.setQuestion("New Question");
		UpdatedEvent testData=ForumQuestionUpdatedEvent.notFound(id);
		String content="{\"question\":\"This question.\"}";
		when (service.updateForumQuestion(any(UpdateForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testUpdateForumQuestionNullEventReturned() throws Exception
	{
		LOG.debug("performingUpdateForumQuestion()");
		Long id=1L;
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		dets.setQuestion("New Question");
		UpdatedEvent testData=null;
		String content="{\"question\":\"This question.\"}";
		when (service.updateForumQuestion(any(UpdateForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#deleteForumQuestion(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteForumQuestion() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteForumQuestion()");
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		ForumQuestionDeletedEvent testData=new ForumQuestionDeletedEvent(dets.getNodeId());
		when (service.deleteForumQuestion(any(DeleteForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{forumQuestionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}
	@Test
	public final void testDeleteForumQuestionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteForumQuestion()");
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		DeletedEvent testData=ForumQuestionDeletedEvent.notFound(dets.getNodeId());
		when (service.deleteForumQuestion(any(DeleteForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{forumQuestionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteForumQuestionForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteForumQuestion()");
		ForumQuestionDetails dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		DeletedEvent testData=ForumQuestionDeletedEvent.deletionForbidden(dets.getNodeId());
		when (service.deleteForumQuestion(any(DeleteForumQuestionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{forumQuestionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isGone())	;
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#like(java.lang.Long, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testLike() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=new LikedEvent(id, user.getEmail(), true);
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);

		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$success",is(evt.isResultSuccess())))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testLikeFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=new LikedEvent(id, user.getEmail(), false);
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);

		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$success",is(evt.isResultSuccess())))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testLikedByForumQuestionNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());
		
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testLikedByForumQuestionGone() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());
		
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#unlike(java.lang.Long, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUnlike() throws Exception
	{
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt= new LikedEvent(id, user.getEmail(), true);

		when(likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$success",is(evt.isResultSuccess())))
                .andExpect(status().isOk())	;
	}

	@Test
	public final void testUnlikeFailed() throws Exception
	{
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt= new LikedEvent(id, user.getEmail(), false);

		when(likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$success",is(evt.isResultSuccess())))
                .andExpect(status().isOk())	;
	}

    @Test
    public final void testUnLikedByForumQuestionNotFound() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());

        when (likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())	;
    }

    @Test
    public final void testUnLikedByForumQuestionGone() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());

        when (likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isGone())	;
    }

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ForumQuestionController#findLikes(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
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
        when (service.readForumQuestion(any(ReadForumQuestionEvent.class))).thenReturn(readPollEvent);
        this.mockMvc.perform(get(urlPrefix+"/{id}/likes/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
