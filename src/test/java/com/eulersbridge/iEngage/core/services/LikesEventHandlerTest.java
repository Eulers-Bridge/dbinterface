package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Like;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.EventRepository;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

import org.junit.Before;
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
import static org.mockito.Mockito.doNothing;
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
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    EventRepository eventRepository;

	/**
	 * @throws java.lang.Exception
	 */
    @Before
    public void setUp() throws Exception
    {
		MockitoAnnotations.initMocks(this);

        service = new LikesEventHandler(userRepository,ownerRepository);
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
	
	@Test
	public void testShouldAddLikeForArticle()
	{
		NewsArticle newsArticle=DatabaseDataFixture.populateNewsArticle1();
		User user=DatabaseDataFixture.populateUserGnewitt();
		
		
		Like testData=new Like(user, newsArticle);
		LikeEvent likeNewsArticlesEvent=new LikeEvent(newsArticle.getNodeId(),user.getEmail());
		when(userRepository.like(any(String.class),any(Long.class))).thenReturn(testData);
		LikedEvent res = service.like(likeNewsArticlesEvent);
		assertNotNull(res);
		assertEquals(likeNewsArticlesEvent.getNodeId(),res.getNodeId());
		assertEquals(likeNewsArticlesEvent.getEmailAddress(),res.getUserEmail());
		assertTrue(res.isEntityFound());
		assertTrue(res.isUserFound());
		assertTrue(res.isResultSuccess());
	}
	
	@Test
	public void testShouldReturnFalseForSecondLikeForArticle()
	{
		NewsArticle newsArticle=DatabaseDataFixture.populateNewsArticle1();
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikeEvent likeNewsArticlesEvent=new LikeEvent(newsArticle.getNodeId(),user);
		when(userRepository.like(any(String.class),any(Long.class))).thenReturn(null);
		
		LikedEvent res = service.like(likeNewsArticlesEvent);
		assertNotNull(res);
		assertEquals(false,res.isResultSuccess());
	}
	
	@Test
	public void testShouldReturnUserNotFound()
	{
		NewsArticle newsArticle=DatabaseDataFixture.populateNewsArticle1();
		LikeEvent likeNewsArticlesEvent=new LikeEvent(newsArticle.getNodeId(),"test@hotmail.com");
		when(userRepository.like(any(String.class),any(Long.class))).thenReturn(null);
		LikedEvent res = service.like(likeNewsArticlesEvent);
		assertNotNull(res);
		assertEquals(false, res.isResultSuccess());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#likeEvent(com.eulersbridge.iEngage.core.events.events.LikeEvent)}.
	 */
	@Test
	public final void testLikeEvent()
	{
		if (LOG.isDebugEnabled()) LOG.debug("LikingEvent()");
		User liker=DatabaseDataFixture.populateUserGnewitt();
		Event liked=DatabaseDataFixture.populateEvent1();
		Like testData=new Like(liker, liked);
		LikeEvent evt=new LikeEvent(liked.getEventId(), liker.getEmail());
		when(userRepository.like(any(String.class),any(Long.class))).thenReturn(testData);
		
		LikedEvent evtData = service.like(evt);
		assertEquals(evtData.getNodeId(),liked.getEventId());
		assertEquals(evtData.getUserEmail(),liker.getEmail());
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isUserFound());
		assertTrue(evtData.isResultSuccess());
	}
	@Test
	public final void testLikeEventNullReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("LikingEvent()");
		User liker=DatabaseDataFixture.populateUserGnewitt();
		Event liked=DatabaseDataFixture.populateEvent1();
		Like testData=null;
		LikeEvent evt=new LikeEvent(liked.getEventId(), liker.getEmail());
		when(userRepository.like(any(String.class),any(Long.class))).thenReturn(testData);
		
		LikedEvent evtData = service.like(evt);
		assertEquals(evtData.getNodeId(),liked.getEventId());
		assertEquals(evtData.getUserEmail(),liker.getEmail());
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isUserFound());
		assertFalse(evtData.isResultSuccess());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#unLikeEvent(com.eulersbridge.iEngage.core.events.events.LikeEvent)}.
	 */
	@Test
	public final void testUnlikeEvent()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UnlikingEvent()");
		User liker=DatabaseDataFixture.populateUserGnewitt();
		Event liked=DatabaseDataFixture.populateEvent1();
		LikeEvent evt=new LikeEvent(liked.getEventId(), liker.getEmail());
		doNothing().when(userRepository).unlike(any(String.class),any(Long.class));
		
		LikedEvent evtData = service.unlike(evt);
		assertEquals(evtData.getNodeId(),liked.getEventId());
		assertEquals(evtData.getUserEmail(),liker.getEmail());
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isUserFound());
		assertTrue(evtData.isResultSuccess());
	}

}