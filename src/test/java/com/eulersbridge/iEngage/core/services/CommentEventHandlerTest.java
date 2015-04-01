package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.comments.CommentDetails;
import com.eulersbridge.iEngage.core.events.comments.CreateCommentEvent;
import com.eulersbridge.iEngage.database.domain.Comment;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Ticket;
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

import static org.junit.Assert.assertNotNull;


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
        Owner testObject = DatabaseDataFixture.populateOwner();
        Comment testComment = DatabaseDataFixture.populateComment(testUser, testObject);

        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ownerRepository.findOne(any(Long.class))).thenReturn(testObject);
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        CommentDetails commentDetails = testComment.toCommentDetails();
        CreatedEvent CommentCreatedEvent = service.createComment(new CreateCommentEvent(commentDetails));
        Details retDetails = CommentCreatedEvent.getDetails();
        assertEquals(commentDetails, retDetails);
        assertEquals(commentDetails.getNodeId(), retDetails.getNodeId());
        assertNotNull(CommentCreatedEvent.getNodeId());
    }

    @Test
    public void testRequestReadComment() throws Exception {

    }

    @Test
    public void testDeleteComment() throws Exception {

    }

    @Test
    public void testReadComments() throws Exception {

    }

    @Test
    public void testUpdateComment() throws Exception {

    }
}