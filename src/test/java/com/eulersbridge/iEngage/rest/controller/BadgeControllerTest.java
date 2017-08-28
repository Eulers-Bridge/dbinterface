package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.badge.*;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.BadgeService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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

		MappingJackson2HttpMessageConverter converter=RestDataFixture.setUpConverter();
		this.mockMvc = standaloneSetup(controller).setMessageConverters(converter).build();
//		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
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
		String content="{\"badgeId\":"+evtId+",\"level\":"+dets.getLevel()+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+
				"\",\"xpValue\":"+dets.getXpValue().intValue()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"},"+
				"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}]}";	
		 return content;
	}

	String setupReturnedCompletedContent(BadgeCompleteDetails dets)
	{
		int nodeId=dets.getNodeId().intValue();
		int taskId=dets.getBadgeId().intValue();
		int userId=dets.getUserId().intValue();
		String content="{\"nodeId\":"+nodeId+",\"badgeId\":"+taskId+",\"timestamp\":"+dets.getDate()+",\"userId\":"+userId+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+taskId+"/complete/"+userId+"\"}"+
//				",{\"rel\":\"Previous\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/previous\"},"+
//				"{\"rel\":\"Next\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/next\"},"+
//				"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}"+
				"]}";	
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
		Long numElements=(long) badgeDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(null,badgeDets,numElements,numPages);
		when (badgeService.readBadges(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("totalPages",is(numPages)))
		.andExpect(jsonPath("foundObjects[0].badgeId",is(badgeDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("foundObjects[0].name",is(badgeDets.get(0).getName())))
		.andExpect(jsonPath("foundObjects[0].xpValue",is(badgeDets.get(0).getXpValue().intValue())))
		.andExpect(jsonPath("foundObjects[0].description",is(badgeDets.get(0).getDescription())))
		.andExpect(jsonPath("foundObjects[1].badgeId",is(badgeDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("foundObjects[1].name",is(badgeDets.get(1).getName())))
		.andExpect(jsonPath("foundObjects[1].xpValue",is(badgeDets.get(1).getXpValue().intValue())))
		.andExpect(jsonPath("foundObjects[1].description",is(badgeDets.get(1).getDescription())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindBadgesZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindBadges()");
		ArrayList<BadgeDetails> eleDets=new ArrayList<BadgeDetails>(); 
		AllReadEvent testData=new AllReadEvent(null,eleDets);
		when (badgeService.readBadges(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
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
		.andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.BadgeController#completedBadge(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Badge)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCompletedBadge() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCompletedBadge()");
		BadgeDetails badgeDets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		UserDetails userDets=DatabaseDataFixture.populateUserGnewitt().toUserDetails();
		Long badgeId=badgeDets.getNodeId();
		Long userId=userDets.getNodeId();
		Long id=1453l;
		Long now=Calendar.getInstance().getTimeInMillis();
        BadgeCompleteDetails dets=new BadgeCompleteDetails(id, userId, badgeId, now);
		UpdatedEvent testData=new UpdatedEvent(453l,dets);
		String returnedContent=setupReturnedCompletedContent(dets);
		when (badgeService.completedBadge(any(UpdateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{badgeId}/complete/{userId}",badgeId.intValue(),userId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.badgeId",is(dets.getBadgeId().intValue())))
		.andExpect(jsonPath("$.userId",is(dets.getUserId().intValue())))
		.andExpect(jsonPath("$.timestamp",is(dets.getDate())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testCompletedBadgeBadgeNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadge()");
		BadgeDetails badgeDets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		UserDetails userDets=DatabaseDataFixture.populateUserGnewitt().toUserDetails();
		Long badgeId=badgeDets.getNodeId();
		Long userId=userDets.getNodeId();
		UpdatedEvent testData=UpdatedEvent.notFound(badgeId);
		when (badgeService.completedBadge(any(UpdateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{badgeId}/complete/{userId}",badgeId.intValue(),userId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCompletedBadgeNullEvtReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadge()");
		BadgeDetails badgeDets=DatabaseDataFixture.populateBadge1().toBadgeDetails();
		UserDetails userDets=DatabaseDataFixture.populateUserGnewitt().toUserDetails();
		Long badgeId=badgeDets.getNodeId();
		Long userId=userDets.getNodeId();
		UpdatedEvent testData=null;
		when (badgeService.completedBadge(any(UpdateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{badgeId}/complete/{userId}",badgeId.intValue(),userId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}


}