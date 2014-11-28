/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.core.events.polls.ReadPollEvent;
import com.eulersbridge.iEngage.core.events.polls.RequestReadPollEvent;
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
		.andExpect(jsonPath("$.newsFeedId",is(dets.getOwnerId().intValue())))
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#createPoll(com.eulersbridge.iEngage.rest.domain.Poll)}.
	 */
	@Test
	public final void testCreatePoll()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#updatePoll(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Poll)}.
	 */
	@Test
	public final void testUpdatePoll()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#deletePoll(java.lang.Long)}.
	 */
	@Test
	public final void testDeletePoll()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#likePoll(java.lang.Long, java.lang.String)}.
	 */
	@Test
	public final void testLikePoll()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#unlikePoll(java.lang.Long, java.lang.String)}.
	 */
	@Test
	public final void testUnlikePoll()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PollController#findLikes(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testFindLikes()
	{
		fail("Not yet implemented"); // TODO
	}

}
