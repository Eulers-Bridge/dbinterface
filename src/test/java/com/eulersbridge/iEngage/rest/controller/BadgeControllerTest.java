package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.badge.*;
import com.eulersbridge.iEngage.core.services.BadgeService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.After;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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

    @After
    public void tearDown() throws Exception {

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
        String content = "{\"badgeId\":100,\"name\":\"Test Badge\",\"awarded\":true,\"timestamp\":100000,\"xpValue\":50}";
        String returnedContent = "{\"badgeId\":"+ dets.getNodeId().intValue()
                + ",\"name\":\""+ dets.getName() +"\",\"awarded\":"+ dets.isAwarded()
                +",\"timestamp\":" + dets.getTimestamp().intValue() + ",\"xpValue\":" + dets.getXpValue().intValue()
                +",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/badge/100\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/badges\"}]}";
        when (badgeService.createBadge(any(CreateBadgeEvent.class))).thenReturn(testData);
        this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
            .andDo(print())
            .andExpect(jsonPath("$.badgeId", is(dets.getNodeId().intValue())))
            .andExpect(jsonPath("$.name", is(dets.getName())))
            .andExpect(jsonPath("$.awarded", is(dets.isAwarded())))
            .andExpect(jsonPath("$.timestamp", is(dets.getTimestamp().intValue())))
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
        String content = "{\"badgeId1\":100,\"name\":\"Test Badge\",\"awarded\":true,\"timestamp\":100000,\"xpValue\":50}";
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
        BadgeCreatedEvent testData=new BadgeCreatedEvent(null, dets);
        String content="{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
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
        when (badgeService.requestReadBadge(any(RequestReadBadgeEvent.class))).thenReturn(testData);
        this.mockMvc.perform(get(urlPrefix + "/{badgeId}", badgeDetails.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(jsonPath("$.badgeId", is(badgeDetails.getNodeId().intValue())))
            .andExpect(jsonPath("$.name", is(badgeDetails.getName())))
            .andExpect(jsonPath("$.awarded", is(badgeDetails.isAwarded())))
            .andExpect(jsonPath("$.timestamp", is(badgeDetails.getTimestamp().intValue())))
            .andExpect(jsonPath("$.xpValue", is(badgeDetails.getXpValue().intValue())))
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
    public void testUpdateBadge() throws Exception {

    }

    @Test
    public void testDeleteBadge() throws Exception {

    }
}