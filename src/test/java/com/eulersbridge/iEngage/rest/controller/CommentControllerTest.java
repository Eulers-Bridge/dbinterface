package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.comments.*;
import com.eulersbridge.iEngage.core.services.CommentService;
import com.eulersbridge.iEngage.database.domain.Comment;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Yikai Gong
 */

public class CommentControllerTest {

    private static Logger LOG = LoggerFactory.getLogger(CommentControllerTest.class);

    private String urlPrefix = ControllerConstants.API_PREFIX + ControllerConstants.COMMENT_LABEL;

    MockMvc mockMvc;

    @InjectMocks
    CommentController controller;

    @Mock
    CommentService commentService;

    @Before
    public void setUp() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("setup()");
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public final void testCommentController(){
        CommentController commentController = new CommentController();
        assertNotNull("Not yet implemented", commentController);
    }

    public String setupContent(CommentDetails dets) {
        String evtId = String.valueOf(dets.getNodeId());
        String targetId = String.valueOf(dets.getTargetId());
        String timestamp = String.valueOf(dets.getTimestamp());
        String content="{\"commentId\":"+evtId+",\"targetId\":"+targetId+
                ",\"userName\":\""+dets.getUserName() +"\",\"userEmail\":\""+
                dets.getUserEmail()+"\",\"timestamp\":"+timestamp+",\"content\":\""+
                dets.getContent()+"\"}";
        return content;
    }

    public String setupInvalidContent(CommentDetails dets) {
        String evtId = String.valueOf(dets.getNodeId());
        String targetId = String.valueOf(dets.getTargetId());
        String timestamp = String.valueOf(dets.getTimestamp());
        String content="{\"commentId1\":"+evtId+",\"targetId\":"+targetId+
                ",\"userName\":\""+dets.getUserName() +"\",\"userEmail\":\""+
                dets.getUserEmail()+"\",\"timestamp\":"+timestamp+",\"content\":\""+
                dets.getContent()+"\"}";
        return content;
    }

    public String setupReturnedContent(CommentDetails dets){
        int evtId=dets.getNodeId().intValue();
        String targetId = String.valueOf(dets.getTargetId());
        String timestamp = String.valueOf(dets.getTimestamp());
        String content="{\"commentId\":"+evtId+",\"targetId\":"+targetId+
                ",\"userName\":\""+dets.getUserName() +"\",\"userEmail\":\""+
                dets.getUserEmail()+"\",\"timestamp\":"+timestamp+",\"content\":\""+
                dets.getContent()+"\""
                +",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"},"+
                "{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s/"+ targetId +"\"}]}";
        return content;
    }

    @Test
    public void testCreateComment() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        String content = setupContent(testCommentDetails);
        String exceptedReturn = setupReturnedContent(testCommentDetails);
        CommentCreatedEvent commentCreatedEvent = new CommentCreatedEvent(testCommentDetails);
        when (commentService.createComment(any(CreateCommentEvent.class))).thenReturn(commentCreatedEvent);
        this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(jsonPath("$.commentId", is(testCommentDetails.getNodeId().intValue())))
                .andExpect(jsonPath("$.targetId", is(testCommentDetails.getTargetId().intValue())))
                .andExpect(jsonPath("$.userName", is(testCommentDetails.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(testCommentDetails.getUserEmail())))
                .andExpect(jsonPath("$.timestamp", is(testCommentDetails.getTimestamp().intValue())))
                .andExpect(jsonPath("$.content", is(testCommentDetails.getContent())))
                .andExpect(content().string(exceptedReturn))
                .andExpect(status().isCreated());
    }

    @Test
    public final void testCreateCommentNullEvt() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        String content = setupContent(testCommentDetails);
        when (commentService.createComment(any(CreateCommentEvent.class))).thenReturn(null);
        this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public final void testCreateCommentInvalidContent() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        String content = setupInvalidContent(testCommentDetails);
        CommentCreatedEvent commentCreatedEvent = new CommentCreatedEvent(testCommentDetails);
        when (commentService.createComment(any(CreateCommentEvent.class))).thenReturn(commentCreatedEvent);
        this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public final void testCreateCommentNoContent() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        CommentCreatedEvent commentCreatedEvent = new CommentCreatedEvent(testCommentDetails);
        when (commentService.createComment(any(CreateCommentEvent.class))).thenReturn(commentCreatedEvent);
        this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public final void testCreateCommentNullNodeId() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        String content = setupContent(testCommentDetails);
        CommentCreatedEvent commentCreatedEvent = new CommentCreatedEvent(testCommentDetails);
        commentCreatedEvent.setNodeId(null);
        when(commentService.createComment(any(CreateCommentEvent.class))).thenReturn(commentCreatedEvent);
        this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public final void testCreateCommentFailed() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        String content = setupContent(testCommentDetails);
        CreatedEvent commentCreatedEvent = CommentCreatedEvent.failed(testCommentDetails);
        when (commentService.createComment(any(CreateCommentEvent.class))).thenReturn(commentCreatedEvent);
        this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public final void testCreateCommentTargetNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        String content = setupContent(testCommentDetails);
        CommentCreatedEvent commentCreatedEvent =  CommentCreatedEvent.targetNotFound(testCommentDetails.getNodeId());
        when (commentService.createComment(any(CreateCommentEvent.class))).thenReturn(commentCreatedEvent);
        this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public final void testCreateCommentUserNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        String content = setupContent(testCommentDetails);
        CommentCreatedEvent commentCreatedEvent =  CommentCreatedEvent.userNotFound();
        when (commentService.createComment(any(CreateCommentEvent.class))).thenReturn(commentCreatedEvent);
        this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteComment() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingDeleteComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();

        CommentDeletedEvent commentDeletedEvent = new CommentDeletedEvent(testCommentDetails.getNodeId());
        when (commentService.deleteComment(any(DeleteCommentEvent.class))).thenReturn(commentDeletedEvent);
        this.mockMvc.perform(delete(urlPrefix+"/{ticketId}/",testCommentDetails.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingDeleteComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        Long commentId = testCommentDetails.getNodeId();

        DeletedEvent commentDeletedEvent =  CommentDeletedEvent.notFound(commentId);
        when (commentService.deleteComment(any(DeleteCommentEvent.class))).thenReturn(commentDeletedEvent);
        this.mockMvc.perform(delete(urlPrefix + "/{ticketId}/", testCommentDetails.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCommentForbidden() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingDeleteComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        Long commentId = testCommentDetails.getNodeId();

        DeletedEvent commentDeletedEvent =  CommentDeletedEvent.deletionForbidden(commentId);
        when (commentService.deleteComment(any(DeleteCommentEvent.class))).thenReturn(commentDeletedEvent);
        this.mockMvc.perform(delete(urlPrefix+"/{ticketId}/",testCommentDetails.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isGone());
    }

    @Test
    public void testFindComments() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindComments()");
        Owner testObject = DatabaseDataFixture.populateOwner();
        Long targetId = testObject.getNodeId();
        User testUser1 = DatabaseDataFixture.populateUserGnewitt();
        User testUser2 = DatabaseDataFixture.populateUserYikai();
        CommentDetails testCommentDetails1 = DatabaseDataFixture.populateComment1(testUser1, testObject).toCommentDetails();
        CommentDetails testCommentDetails2 = DatabaseDataFixture.populateComment1(testUser2, testObject).toCommentDetails();
        ArrayList<CommentDetails> testCommentsDetails = new ArrayList<>();
        testCommentsDetails.add(testCommentDetails1);
        testCommentsDetails.add(testCommentDetails2);

        CommentsReadEvent commentsReadEvent = new CommentsReadEvent(targetId, testCommentsDetails);
        when (commentService.readComments(any(RequestReadCommentsEvent.class), any(Sort.Direction.class),
                any(int.class), any(int.class))).thenReturn(commentsReadEvent);
        this.mockMvc.perform(get(urlPrefix+"s/{targetId}/",targetId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].commentId", is(testCommentDetails1.getNodeId().intValue())))
                .andExpect(jsonPath("$[0].targetId", is(testCommentDetails1.getTargetId().intValue())))
                .andExpect(jsonPath("$[0].userName", is(testCommentDetails1.getUserName())))
                .andExpect(jsonPath("$[0].userEmail", is(testCommentDetails1.getUserEmail())))
                .andExpect(jsonPath("$[0].timestamp", is(testCommentDetails1.getTimestamp().intValue())))
                .andExpect(jsonPath("$[0].content", is(testCommentDetails1.getContent())))
                .andExpect(jsonPath("$[1].commentId", is(testCommentDetails2.getNodeId().intValue())))
                .andExpect(jsonPath("$[1].targetId", is(testCommentDetails2.getTargetId().intValue())))
                .andExpect(jsonPath("$[1].userName", is(testCommentDetails2.getUserName())))
                .andExpect(jsonPath("$[1].userEmail", is(testCommentDetails2.getUserEmail())))
                .andExpect(jsonPath("$[1].timestamp", is(testCommentDetails2.getTimestamp().intValue())))
                .andExpect(jsonPath("$[1].content", is(testCommentDetails2.getContent())))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindCommentsZeroComments() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindComments()");
        Owner testObject = DatabaseDataFixture.populateOwner();
        Long targetId = testObject.getNodeId();
        ArrayList<CommentDetails> testCommentsDetails = new ArrayList<>();
        CommentsReadEvent commentsReadEvent = new CommentsReadEvent(targetId, testCommentsDetails);
        when (commentService.readComments(any(RequestReadCommentsEvent.class), any(Sort.Direction.class),
                any(int.class), any(int.class))).thenReturn(commentsReadEvent);
        this.mockMvc.perform(get(urlPrefix+"s/{targetId}/",targetId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testFindCommentsTargetNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindComments()");
        Owner testObject = DatabaseDataFixture.populateOwner();
        Long targetId = testObject.getNodeId();
        CommentsReadEvent commentsReadEvent = CommentsReadEvent.targetNotFound(targetId);
        when (commentService.readComments(any(RequestReadCommentsEvent.class), any(Sort.Direction.class),
                any(int.class), any(int.class))).thenReturn(commentsReadEvent);
        this.mockMvc.perform(get(urlPrefix+"s/{targetId}/",targetId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateComment() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingUpdateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        Long commentId = testCommentDetails.getNodeId();
        String content = setupContent(testCommentDetails);
        String exceptedReturn = setupReturnedContent(testCommentDetails);

        CommentUpdatedEvent commentUpdatedEvent = new CommentUpdatedEvent(commentId, testCommentDetails);
        when (commentService.updateComment(any(UpdateCommentEvent.class))).thenReturn(commentUpdatedEvent);
        this.mockMvc.perform(put(urlPrefix+"/{id}/", commentId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(jsonPath("$.commentId", is(testCommentDetails.getNodeId().intValue())))
                .andExpect(jsonPath("$.targetId", is(testCommentDetails.getTargetId().intValue())))
                .andExpect(jsonPath("$.userName", is(testCommentDetails.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(testCommentDetails.getUserEmail())))
                .andExpect(jsonPath("$.timestamp", is(testCommentDetails.getTimestamp().intValue())))
                .andExpect(jsonPath("$.content", is(testCommentDetails.getContent())))
                .andExpect(content().string(exceptedReturn))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCommentNullEventReturned() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingUpdateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        Long commentId = testCommentDetails.getNodeId();
        String content = setupContent(testCommentDetails);

        when (commentService.updateComment(any(UpdateCommentEvent.class))).thenReturn(null);
        this.mockMvc.perform(put(urlPrefix + "/{id}/", commentId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCommentBadContent() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingUpdateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        Long commentId = testCommentDetails.getNodeId();
        String content = setupInvalidContent(testCommentDetails);

        CommentUpdatedEvent commentUpdatedEvent = new CommentUpdatedEvent(commentId, testCommentDetails);
        when (commentService.updateComment(any(UpdateCommentEvent.class))).thenReturn(commentUpdatedEvent);
        this.mockMvc.perform(put(urlPrefix + "/{id}/", commentId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCommentEmptyContent() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingUpdateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        Long commentId = testCommentDetails.getNodeId();

        CommentUpdatedEvent commentUpdatedEvent = new CommentUpdatedEvent(commentId, testCommentDetails);
        when (commentService.updateComment(any(UpdateCommentEvent.class))).thenReturn(commentUpdatedEvent);
        this.mockMvc.perform(put(urlPrefix+"/{id}/", commentId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCommentNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingUpdateComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        Long commentId = testCommentDetails.getNodeId();
        String content = setupContent(testCommentDetails);

        UpdatedEvent commentUpdatedEvent = CommentUpdatedEvent.notFound(commentId);
        when (commentService.updateComment(any(UpdateCommentEvent.class))).thenReturn(commentUpdatedEvent);
        this.mockMvc.perform(put(urlPrefix+"/{id}/", commentId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindComment() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        String exceptedReturn = setupReturnedContent(testCommentDetails);
        CommentReadEvent commentReadEvent = new CommentReadEvent(testCommentDetails.getNodeId(), testCommentDetails);
        when (commentService.requestReadComment(any(RequestReadCommentEvent.class))).thenReturn(commentReadEvent);
        this.mockMvc.perform(get(urlPrefix + "/{commentId}/", testCommentDetails.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.commentId", is(testCommentDetails.getNodeId().intValue())))
                .andExpect(jsonPath("$.targetId", is(testCommentDetails.getTargetId().intValue())))
                .andExpect(jsonPath("$.userName", is(testCommentDetails.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(testCommentDetails.getUserEmail())))
                .andExpect(jsonPath("$.timestamp", is(testCommentDetails.getTimestamp().intValue())))
                .andExpect(jsonPath("$.content", is(testCommentDetails.getContent())))
                .andExpect(content().string(exceptedReturn))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindCommentNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment1(testUser, testObject);
        CommentDetails testCommentDetails = testComment.toCommentDetails();
        ReadEvent commentReadEvent = CommentReadEvent.notFound(testCommentDetails.getNodeId());
        when (commentService.requestReadComment(any(RequestReadCommentEvent.class))).thenReturn(commentReadEvent);
        this.mockMvc.perform(get(urlPrefix + "/{commentId}/", testCommentDetails.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}