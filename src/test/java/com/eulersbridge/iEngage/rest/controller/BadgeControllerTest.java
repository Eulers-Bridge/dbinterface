package com.eulersbridge.iEngage.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.badge.BadgeCreatedEvent;
import com.eulersbridge.iEngage.core.events.badge.BadgeDeletedEvent;
import com.eulersbridge.iEngage.core.events.badge.BadgeDetails;
import com.eulersbridge.iEngage.core.events.badge.BadgeUpdatedEvent;
import com.eulersbridge.iEngage.core.events.badge.BadgesReadEvent;
import com.eulersbridge.iEngage.core.events.badge.CreateBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.DeleteBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.ReadBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.ReadBadgesEvent;
import com.eulersbridge.iEngage.core.events.badge.RequestReadBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.UpdateBadgeEvent;
import com.eulersbridge.iEngage.core.services.BadgeService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class BadgeControllerTest {
    private static Logger LOG = LoggerFactory.getLogger(BadgeControllerTest.class);

    private String urlPrefix = ControllerConstants.API_PREFIX + ControllerConstants.BADGE_LABEL;

    MockMvc mockMvc;

    @InjectMocks
    BadgeController controller;

    @Mock
    BadgeService badgeService;

    @Before
    public void setUp() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("setup()");
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

	String setupContent(BadgeDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"badgeId\":"+evtId+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+"\",\"xpValue\":"+dets.getXpValue().intValue()+"}";
		return content;
	}
	
	String setupInvalidContent(BadgeDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"badgeId1\":"+evtId+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+"\",\"xpValue\":"+dets.getXpValue().intValue()+"}";
		return content;
	}
	
	String setupReturnedContent(BadgeDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"badgeId\":"+evtId+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+
				"\",\"xpValue\":"+dets.getXpValue().intValue()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"},"+
				"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}]}";	
		 return content;
	}

    @Test
    public void testBadgeController() throws Exception {
        BadgeController badgeController = new BadgeController();
        assertNotNull("Not yet implemented", badgeController);
    }

    @Test
    public void testCreateBadge() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadge()");
        BadgeDetails dets = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        BadgeCreatedEvent testData = new BadgeCreatedEvent(dets.getNodeId(), dets);
        String content = setupContent(dets);
        String returnedContent = setupReturnedContent(dets);
        when (badgeService.createBadge(any(CreateBadgeEvent.class))).thenReturn(testData);
        this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
            .andDo(print())
            .andExpect(jsonPath("$.badgeId", is(dets.getNodeId().intValue())))
            .andExpect(jsonPath("$.name", is(dets.getName())))
            .andExpect(jsonPath("$.description", is(dets.getDescription())))
            .andExpect(jsonPath("$.xpValue", is(dets.getXpValue().intValue())))
            .andExpect(jsonPath("$.links[0].rel",is("self")))
            .andExpect(jsonPath("$.links[1].rel",is("Read all")))
            .andExpect(content().string(returnedContent))
            .andExpect(status().isCreated());
    }

    @Test
    public final void testCreateBadgeInvalidContent() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadge()");
        BadgeDetails dets = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        BadgeCreatedEvent testData=new BadgeCreatedEvent(dets.getNodeId(), dets);
        String content = setupInvalidContent(dets);
        when (badgeService.createBadge(any(CreateBadgeEvent.class))).thenReturn(testData);
        this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())	;
    }

    @Test
    public final void testCreateBadgeNoContent() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadge()");
        BadgeDetails dets = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        BadgeCreatedEvent testData=new BadgeCreatedEvent(dets.getNodeId(), dets);
        when (badgeService.createBadge(any(CreateBadgeEvent.class))).thenReturn(testData);
        this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())	;
    }

    @Test
    public final void testCreateBadgeNullIdReturned() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadge()");
        BadgeDetails dets = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        String content=setupContent(dets);
        dets.setNodeId(null);
        BadgeCreatedEvent testData=new BadgeCreatedEvent(null, dets);
        when (badgeService.createBadge(any(CreateBadgeEvent.class))).thenReturn(testData);
        this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())	;
    }

    @Test
    public void testFindBadge() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindBadge()");
        BadgeDetails badgeDetails = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        ReadBadgeEvent testData = new ReadBadgeEvent(badgeDetails.getNodeId(), badgeDetails);
        String returnedContent = setupReturnedContent(badgeDetails);
        when (badgeService.requestReadBadge(any(RequestReadBadgeEvent.class))).thenReturn(testData);
        this.mockMvc.perform(get(urlPrefix + "/{badgeId}", badgeDetails.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(jsonPath("$.badgeId", is(badgeDetails.getNodeId().intValue())))
            .andExpect(jsonPath("$.name", is(badgeDetails.getName())))
            .andExpect(jsonPath("$.description", is(badgeDetails.getDescription())))
            .andExpect(jsonPath("$.xpValue", is(badgeDetails.getXpValue().intValue())))
            .andExpect(content().string(returnedContent))
            .andExpect(status().isOk());
    }

    @Test
    public void testFindBadgeNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindBadge()");
        BadgeDetails dets = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        ReadEvent testData = ReadBadgeEvent.notFound(dets.getNodeId());
        when (badgeService.requestReadBadge(any(RequestReadBadgeEvent.class))).thenReturn(testData);
        this.mockMvc.perform(get(urlPrefix + "/{badgeId}", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }


	@Test
	public final void testFindBadges() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindBadges()");
		HashMap<Long, com.eulersbridge.iEngage.database.domain.Badge> dets=DatabaseDataFixture.populateBadges();
		Iterable<com.eulersbridge.iEngage.database.domain.Badge> badges=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.Badge> iter=badges.iterator();
		ArrayList<BadgeDetails> badgeDets=new ArrayList<BadgeDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.Badge article=iter.next();
			badgeDets.add(article.toBadgeDetails());
		}
		BadgesReadEvent testData=new BadgesReadEvent(badgeDets);
		when (badgeService.readBadges(any(ReadBadgesEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$[0].badgeId",is(badgeDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$[0].name",is(badgeDets.get(0).getName())))
		.andExpect(jsonPath("$[0].xpValue",is(badgeDets.get(0).getXpValue().intValue())))
		.andExpect(jsonPath("$[0].description",is(badgeDets.get(0).getDescription())))
		.andExpect(jsonPath("$[1].badgeId",is(badgeDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$[1].name",is(badgeDets.get(1).getName())))
		.andExpect(jsonPath("$[1].xpValue",is(badgeDets.get(1).getXpValue().intValue())))
		.andExpect(jsonPath("$[1].description",is(badgeDets.get(1).getDescription())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindBadgesZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindBadges()");
		ArrayList<BadgeDetails> eleDets=new ArrayList<BadgeDetails>(); 
		BadgesReadEvent testData=new BadgesReadEvent(eleDets);
		when (badgeService.readBadges(any(ReadBadgesEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.UpdateBadgeController#updateBadge(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Badge)}.
	 * @throws Exception 
	 */
	@Test
    public void testUpdateBadge() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadge()");
		Long id=1L;
		BadgeDetails dets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		dets.setName("Test Badge2");
		BadgeUpdatedEvent testData=new BadgeUpdatedEvent(id, dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (badgeService.updateBadge(any(UpdateBadgeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
        .andExpect(jsonPath("$.badgeId", is(dets.getNodeId().intValue())))
        .andExpect(jsonPath("$.name", is(dets.getName())))
        .andExpect(jsonPath("$.description", is(dets.getDescription())))
        .andExpect(jsonPath("$.xpValue", is(dets.getXpValue().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testUpdateBadgeNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadge()");
		Long id=1L;
		BadgeDetails dets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		String content=setupContent(dets);
		when (badgeService.updateBadge(any(UpdateBadgeEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateBadgeBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadge()");
		Long id=1L;
		BadgeDetails dets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		BadgeUpdatedEvent testData=new BadgeUpdatedEvent(id, dets);
		String content=setupInvalidContent(dets);
		when (badgeService.updateBadge(any(UpdateBadgeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateBadgeEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadge()");
		Long id=1L;
		BadgeDetails dets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		BadgeUpdatedEvent testData=new BadgeUpdatedEvent(id, dets);
		when (badgeService.updateBadge(any(UpdateBadgeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateBadgeNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadge()");
		Long id=1L;
		BadgeDetails dets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		String content=setupContent(dets);
		when (badgeService.updateBadge(any(UpdateBadgeEvent.class))).thenReturn(BadgeUpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.BadgeController#deleteBadge(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	   public void testDeleteBadge() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteBadge()");
		BadgeDetails dets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		BadgeDeletedEvent testData=new BadgeDeletedEvent(dets.getNodeId());
		when (badgeService.deleteBadge(any(DeleteBadgeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{badgeId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}
	@Test
	public final void testDeleteBadgeNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteBadge()");
		BadgeDetails dets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		DeletedEvent testData=BadgeDeletedEvent.notFound(dets.getNodeId());
		when (badgeService.deleteBadge(any(DeleteBadgeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{badgeId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteBadgeForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteBadge()");
		BadgeDetails dets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		DeletedEvent testData=BadgeDeletedEvent.deletionForbidden(dets.getNodeId());
		when (badgeService.deleteBadge(any(DeleteBadgeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{badgeId}/",dets.getNodeId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}

}