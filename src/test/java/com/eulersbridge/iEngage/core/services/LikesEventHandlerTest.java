package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.UserRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Yikai Gong
 */

public class LikesEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(LikesEventHandlerTest.class);

    LikesService service;

    @Mock
    UserRepository userRepository;

	/**
	 * @throws java.lang.Exception
	 */
    @Before
    public void setUp() throws Exception
    {
		MockitoAnnotations.initMocks(this);

        service = new LikesEventHandler(userRepository);
    }

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.LikesEventHandler#LikesEventHandler(com.eulersbridge.iEngage.database.repository.UserRepository)}.
	 */
	@Test
	public final void testLikesEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

//TODO Fix this test to be useful.
	@Test
    public void testLikesNullDataReturned() throws Exception
    {
		if (LOG.isDebugEnabled()) LOG.debug("FindingLikes()");
		Page<User> testData=null;
//		testData=DatabaseDataFixture.populatePhoto1();  Need to create testData returned.  There is an example of this in code.
		when(userRepository.findByLikeableObjId(any(Long.class),any(Pageable.class))).thenReturn(testData);
   	
        LikeableObjectLikesEvent likeableObjectLikesEvent = service.likes(new LikesLikeableObjectEvent(new Long(0)), Sort.Direction.DESC, 0, 10);
        assertNotNull("likeableObjectLikesEvent is null", likeableObjectLikesEvent);
    }
}