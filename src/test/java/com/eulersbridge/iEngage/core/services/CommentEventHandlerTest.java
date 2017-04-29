package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.comments.*;
import com.eulersbridge.iEngage.database.domain.Comment;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.CommentRepository;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Yikai Gong
 */

public class CommentEventHandlerTest {
    private static Logger LOG = LoggerFactory.getLogger(CommentEventHandlerTest.class);

    @Mock
    UserRepository userRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    OwnerRepository ownerRepository;

    CommentEventHandler service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new CommentEventHandler(userRepository, commentRepository, ownerRepository);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCommentEventHandler() throws Exception{
        assertNotNull("Not yet implemented", service);
    }

    @Test
    public void testCreateComment() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("CreatingComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner1();
        Comment testComment = DatabaseDataFixture.populateComment1();

        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        CommentDetails commentDetails = testComment.toCommentDetails();
        CreatedEvent commentCreatedEvent = service.createComment(new CreateCommentEvent(commentDetails));
        Details retDetails = commentCreatedEvent.getDetails();
        assertEquals(commentDetails, retDetails);
        assertEquals(commentDetails.getNodeId(), retDetails.getNodeId());
        assertNotNull(commentCreatedEvent.getNodeId());
    }

    @Test
    public void testCreateCommentUserNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("CreatingComment()");
        Owner testObject = DatabaseDataFixture.populateOwner1();
        Comment testComment = DatabaseDataFixture.populateComment1();

        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        CommentDetails commentDetails = testComment.toCommentDetails();
        CreatedEvent commentCreatedEvent = service.createComment(new CreateCommentEvent(commentDetails));
        Details retDetails = commentCreatedEvent.getDetails();
        assertNull(retDetails);
        assertTrue(commentCreatedEvent.isFailed());
        assertFalse(((CommentCreatedEvent)commentCreatedEvent).isUserFound());
        assertEquals(((CommentCreatedEvent)commentCreatedEvent).getFailedId(), null);
    }

    @Test
    public void testCreateCommentTargetNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("CreatingComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Comment testComment = DatabaseDataFixture.populateComment1();

        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(null);
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        CommentDetails commentDetails = testComment.toCommentDetails();
        CreatedEvent commentCreatedEvent = service.createComment(new CreateCommentEvent(commentDetails));
        Details retDetails = commentCreatedEvent.getDetails();
        assertNull(retDetails);
        assertTrue(commentCreatedEvent.isFailed());
        assertFalse(((CommentCreatedEvent)commentCreatedEvent).isTargetFound());
        assertEquals(((CommentCreatedEvent)commentCreatedEvent).getFailedId(), testComment.getTarget().getNodeId());
    }

    @Test
    public void testCreateCommentFailed() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("CreatingComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner1();
        Comment testComment = DatabaseDataFixture.populateComment1();

        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.save(any(Comment.class))).thenReturn(null);

        CommentDetails commentDetails = testComment.toCommentDetails();
        CreatedEvent commentCreatedEvent = service.createComment(new CreateCommentEvent(commentDetails));
        Details retDetails = commentCreatedEvent.getDetails();
        assertEquals(retDetails, testComment.toCommentDetails());
        assertTrue(commentCreatedEvent.isFailed());
    }

    @Test
    public void testRequestReadComment() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("ReadingComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner1();
        Comment testComment = DatabaseDataFixture.populateComment1();

        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.findOne(any(Long.class))).thenReturn(testComment);

        ReadEvent commentReadEvent = service.requestReadComment(new RequestReadCommentEvent(testComment.getNodeId()));
        assertNotNull(commentReadEvent);
        assertEquals(commentReadEvent.getNodeId(), testComment.getNodeId());
        assertEquals(commentReadEvent.getDetails(), testComment.toCommentDetails());
    }

    @Test
    public void testRequestReadCommentNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("ReadingComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner1();
        Comment testComment = DatabaseDataFixture.populateComment1();

        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.findOne(any(Long.class))).thenReturn(null);

        ReadEvent commentReadEvent = service.requestReadComment(new RequestReadCommentEvent(testComment.getNodeId()));
        assertNotNull(commentReadEvent);
        assertEquals(commentReadEvent.getNodeId(), testComment.getNodeId());
        assertFalse(commentReadEvent.isEntityFound());
    }

    @Test
    public void testDeleteComment() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("DeletingComment()");
        Comment testComment = DatabaseDataFixture.populateComment1();

        when(commentRepository.findOne(any(Long.class))).thenReturn(testComment);
        doNothing().when(commentRepository).delete(any(Comment.class));

        DeletedEvent commentDeletedEvent = service.deleteComment(new DeleteCommentEvent(testComment.getNodeId()));
        assertNotNull(commentDeletedEvent);
        assertTrue(commentDeletedEvent.isEntityFound());
        assertTrue(commentDeletedEvent.isDeletionCompleted());
        assertEquals(commentDeletedEvent.getNodeId(), testComment.getNodeId());
    }

    @Test
    public void testDeleteCommentNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("DeletingComment()");
        Comment testComment = DatabaseDataFixture.populateComment1();

        when(commentRepository.findOne(any(Long.class))).thenReturn(null);
        doNothing().when(commentRepository).delete(any(Comment.class));

        DeletedEvent commentDeletedEvent = service.deleteComment(new DeleteCommentEvent(testComment.getNodeId()));
        assertNotNull(commentDeletedEvent);
        assertFalse(commentDeletedEvent.isEntityFound());
        assertFalse(commentDeletedEvent.isDeletionCompleted());
        assertEquals(commentDeletedEvent.getNodeId(), testComment.getNodeId());
    }

    @Test
    public void testReadComments() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("ReadingComments()");
        Owner testObject = DatabaseDataFixture.populateOwner1();
        Comment testComment1 = DatabaseDataFixture.populateComment1();
        Comment testComment2 = DatabaseDataFixture.populateComment2();
        ArrayList<Comment> testCommentsArray = new ArrayList<>();
        testCommentsArray.add(testComment1);
        testCommentsArray.add(testComment2);

        Long targetId = testObject.getNodeId();
        RequestReadCommentsEvent requestReadCommentsEvent = new RequestReadCommentsEvent(targetId);
        int pageLength=10;
        int pageNumber=0;
        Pageable pageable=new PageRequest(pageNumber, pageLength, Sort.Direction.ASC, "r.timestamp");
        Page<Comment> testComments = new PageImpl<Comment>(testCommentsArray, pageable, testCommentsArray.size());
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.findByTargetId(any(Long.class), any(Pageable.class))).thenReturn(testComments);

        AllReadEvent commentsReadEvent = service.readComments(requestReadCommentsEvent, Sort.Direction.ASC,
                pageNumber, pageLength);
        assertNotNull(commentsReadEvent);
        assertEquals(commentsReadEvent.getTotalPages(), new Integer(1));
        assertEquals(commentsReadEvent.getTotalItems(),new Long(testCommentsArray.size()));
    }

    @Test
    public void testReadCommentsNoneAvailable() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("ReadingComments()");
        Owner testObject = DatabaseDataFixture.populateOwner1();
        ArrayList<Comment> testCommentsArray = new ArrayList<>();

        Long targetId = testObject.getNodeId();
        RequestReadCommentsEvent requestReadCommentsEvent = new RequestReadCommentsEvent(targetId);
        int pageLength=10;
        int pageNumber=0;
        Pageable pageable=new PageRequest(pageNumber, pageLength, Sort.Direction.ASC, "r.timestamp");
        Page<Comment> testComments = new PageImpl<Comment>(testCommentsArray, pageable, testCommentsArray.size());
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.findByTargetId(any(Long.class), any(Pageable.class))).thenReturn(testComments);

        AllReadEvent commentsReadEvent = service.readComments(requestReadCommentsEvent, Sort.Direction.ASC,
                pageNumber, pageLength);
        assertNotNull(commentsReadEvent);
        assertEquals(commentsReadEvent.getTotalPages(), new Integer(0));
        assertEquals(commentsReadEvent.getTotalItems(),new Long(0));
    }

    @Test
    public void testReadCommentsNoValidTarget() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("ReadingComments()");
        Owner testObject = DatabaseDataFixture.populateOwner1();
        ArrayList<Comment> testCommentsArray = new ArrayList<>();

        Long targetId = testObject.getNodeId();
        RequestReadCommentsEvent requestReadCommentsEvent = new RequestReadCommentsEvent(targetId);
        int pageLength=10;
        int pageNumber=0;
        Pageable pageable=new PageRequest(pageNumber, pageLength, Sort.Direction.ASC, "r.timestamp");
        Page<Comment> testComments = new PageImpl<Comment>(testCommentsArray, pageable, testCommentsArray.size());
        when(ownerRepository.findOne(any(Long.class))).thenReturn(null);
        when(commentRepository.findByTargetId(any(Long.class), any(Pageable.class))).thenReturn(testComments);

        AllReadEvent commentsReadEvent = service.readComments(requestReadCommentsEvent, Sort.Direction.ASC,
                pageNumber, pageLength);
        assertNotNull(commentsReadEvent);
        assertFalse(commentsReadEvent.isEntityFound());
        assertEquals(commentsReadEvent.getTotalPages(), null);
        assertEquals(commentsReadEvent.getTotalItems(), null);
    }

    @Test
    public void testReadCommentsNullReturned() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("ReadingComments()");
        Owner testObject = DatabaseDataFixture.populateOwner1();

        Long targetId = testObject.getNodeId();
        RequestReadCommentsEvent requestReadCommentsEvent = new RequestReadCommentsEvent(targetId);
        int pageLength=10;
        int pageNumber=0;
        when(commentRepository.findByTargetId(any(Long.class), any(Pageable.class))).thenReturn(null);

        AllReadEvent commentsReadEvent = service.readComments(requestReadCommentsEvent, Sort.Direction.ASC,
                pageNumber, pageLength);
        assertNotNull(commentsReadEvent);
        assertEquals(commentsReadEvent.getTotalPages(), null);
        assertEquals(commentsReadEvent.getTotalItems(), null);
    }

    @Test
    public void testUpdateComment() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("UpdatingComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner1();
        Comment testComment = DatabaseDataFixture.populateComment1();
        Comment testComment1 = DatabaseDataFixture.populateComment2();

        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.findOne(any(Long.class))).thenReturn(testComment);
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment1);

        UpdatedEvent commentUpdatedEvent = service.updateComment(new UpdateCommentEvent(testComment1.getNodeId(),
                testComment1.toCommentDetails()));

        assertNotNull(commentUpdatedEvent);
        assertEquals(commentUpdatedEvent.getDetails(), testComment1.toCommentDetails());
        assertEquals(commentUpdatedEvent.getNodeId(), commentUpdatedEvent.getDetails().getNodeId());
        assertTrue(commentUpdatedEvent.isEntityFound());
        assertEquals(commentUpdatedEvent.getNodeId(), testComment1.getNodeId());
    }

    @Test
    public void testUpdateCommentNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("UpdatingComment()");
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Owner testObject = DatabaseDataFixture.populateOwner1();
        Comment testComment1 = DatabaseDataFixture.populateComment1();

        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.findOne(any(Long.class))).thenReturn(null);
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment1);

        UpdatedEvent commentUpdatedEvent = service.updateComment(new UpdateCommentEvent(testComment1.getNodeId(),
                testComment1.toCommentDetails()));

        assertNotNull(commentUpdatedEvent);
        assertFalse(commentUpdatedEvent.isEntityFound());
        assertEquals(commentUpdatedEvent.getNodeId(), testComment1.getNodeId());
    }
}