package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserMemoryRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class LikesEventHandlerTest {
    LikesService likesService;
    UserMemoryRepository userMemoryRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        Map<Long, User> users= DatabaseDataFixture.populateUsers();
        userMemoryRepository = new UserMemoryRepository(users);

        likesService = new LikesEventHandler(userMemoryRepository);
        
        assertNotNull("likesService is null", likesService);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLikes() throws Exception {
        LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(new LikesLikeableObjectEvent(new Long(0)), Sort.Direction.DESC, 0, 10);
        assertNotNull("likeableObjectLikesEvent is null", likeableObjectLikesEvent);
    }
}