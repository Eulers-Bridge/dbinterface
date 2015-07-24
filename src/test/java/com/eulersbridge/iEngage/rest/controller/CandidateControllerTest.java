/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.candidate.CandidateCreatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.CandidateDeletedEvent;
import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import com.eulersbridge.iEngage.core.events.candidate.CandidateReadEvent;
import com.eulersbridge.iEngage.core.events.candidate.CandidateUpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.CreateCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.DeleteCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.RequestReadCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.UpdateCandidateEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.CandidateService;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.PositionService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class CandidateControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(CandidateControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.CANDIDATE_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	CandidateController controller;
	
	@Mock
	CandidateService candidateService;
	@Mock
	PositionService positionService;
	@Mock
	LikesService likesService;
	@Mock
	UserService userService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		MappingJackson2HttpMessageConverter converter=RestDataFixture.setUpConverter();
		this.mockMvc = standaloneSetup(controller).setMessageConverters(converter).build();
//		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	String setupContent(CandidateDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String tName=null;
		String tColour=null;
		Long tNodeId=null;
		if (null!=dets.getTicketDetails())
		{
			tName='\"'+dets.getTicketDetails().getName()+'\"';
			tColour=dets.getTicketDetails().getColour();
			tNodeId=dets.getTicketDetails().getNodeId();
		}
		return "{\"candidateId\":"+evtId+",\"information\":\""+dets.getInformation()+"\",\"policyStatement\":\""+dets.getPolicyStatement()+
				"\",\"userId\":"+dets.getUserId().intValue()+",\"positionId\":"+dets.getPositionId().intValue()+
				",\"ticketId\":"+tNodeId+"}";
//				",\"ticketName\":"+tName+",\"ticketColour\":"+tColour+"}";
	}
	
	String setupInvalidContent(CandidateDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String tName=null;
		String tColour=null;
		Long tNodeId=null;
		if (null!=dets.getTicketDetails())
		{
			tName='\"'+dets.getTicketDetails().getName()+'\"';
			tColour=dets.getTicketDetails().getColour();
			tNodeId=dets.getTicketDetails().getNodeId();
		}
		return "{\"candidateId1\":"+evtId+",\"information\":\""+dets.getInformation()+"\",\"policyStatement\":\""+dets.getPolicyStatement()+
				"\",\"userId\":"+dets.getUserId().intValue()+",\"positionId\":"+dets.getPositionId().intValue()+
				",\"ticketId\":"+tNodeId+"}";
//				",\"ticketName\":"+tName+",\"ticketColour\":"+tColour+"}";
	}
	
	String setupReturnedContent(CandidateDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String tName=null;
		String tColour=null;
		String email=dets.getEmail();
		if (email!=null) email='\"'+email+'\"';
		Long tNodeId=null;
		if (null!=dets.getTicketDetails())
		{
			tName='\"'+dets.getTicketDetails().getName()+'\"';
			tColour=dets.getTicketDetails().getColour();
			tNodeId=dets.getTicketDetails().getNodeId();
		}
		return "{\"candidateId\":"+evtId+",\"information\":"+RestDataFixture.quoteNonNullStrings(dets.getInformation())+
				",\"policyStatement\":"+RestDataFixture.quoteNonNullStrings(dets.getPolicyStatement())+
				",\"photos\":"+dets.getPhotos()+",\"userId\":"+dets.getUserId().intValue()+
				",\"email\":"+RestDataFixture.quoteNonNullStrings(dets.getEmail())+
				",\"givenName\":"+RestDataFixture.quoteNonNullStrings(dets.getGivenName())+
				",\"familyName\":"+RestDataFixture.quoteNonNullStrings(dets.getFamilyName())+
				",\"positionId\":"+dets.getPositionId().intValue()+",\"ticketId\":"+tNodeId+
//				",\"ticketName\":"+tName+",\"ticketColour\":"+tColour+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/candidate/"+evtId+"\"},"+
//				"{\"rel\":\"Previous\",\"href\":\"http://localhost/api/candidate/"+evtId+"/previous\"},"+
//				"{\"rel\":\"Next\",\"href\":\"http://localhost/api/candidate/"+evtId+"/next\"},"+
//				"{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/candidate/"+evtId+"/likedBy/USERID\"},"+
//				"{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/candidate/"+evtId+"/unlikedBy/USERID\"},"+
//				"{\"rel\":\"Likes\",\"href\":\"http://localhost/api/candidate/"+evtId+"/likes\"},"+
				"{\"rel\":\"Read all\",\"href\":\"http://localhost/api/candidates\"}]}";	
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#CandidateController()}.
	 */
	@Test
	public final void testCandidateController()
	{
		CandidateController candidateController=new CandidateController();
		assertNotNull("Not yet implemented",candidateController);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#createCandidate(com.eulersbridge.iEngage.rest.domain.Candidate)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateCandidate() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		CandidateCreatedEvent testData=new CandidateCreatedEvent(dets);
		String content=setupContent(dets);
//		String returnedContent="{\"candidateId\":"+dets.getNodeId().intValue()+",\"name\":\""+dets.getName()+"\",\"description\":\""+dets.getDescription()+"\",\"electionId\":"+dets.getElectionId().intValue()+
//								",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/candidate/"+dets.getNodeId().intValue()+"\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/candidate/"+dets.getNodeId().intValue()+"/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/candidate/"+dets.getNodeId().intValue()+"/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/candidates\"}]}";
		when (candidateService.createCandidate(any(CreateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.candidateId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.policyStatement",is(dets.getPolicyStatement())))
		.andExpect(jsonPath("$.userId",is(dets.getUserId().intValue())))
		.andExpect(jsonPath("$.positionId",is(dets.getPositionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[1].rel",is("Read all")))
/*		.andExpect(content().string(returnedContent))
*/		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreateCandidateNullEvt() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		String content=setupContent(dets);
		when (candidateService.createCandidate(any(CreateCandidateEvent.class))).thenReturn(null);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateEventInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCandidate()");
		CandidateCreatedEvent testData=null;
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		String content=setupInvalidContent(dets);
		when (candidateService.createCandidate(any(CreateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateEventNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCandidate()");
		CandidateCreatedEvent testData=null;
		when (candidateService.createCandidate(any(CreateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateCandidateNullNodeId() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		String content=setupContent(dets);
		CandidateCreatedEvent testData=new CandidateCreatedEvent(dets);
		testData.setNodeId(null);
		when (candidateService.createCandidate(any(CreateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateCandidateFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		String content=setupContent(dets);
		CreatedEvent testData=CandidateCreatedEvent.failed(dets);
		testData.setNodeId(null);
		when (candidateService.createCandidate(any(CreateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePositonNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		CandidateCreatedEvent testData=CandidateCreatedEvent.positionNotFound(dets.getPositionId());
		String content=setupContent(dets);
		when (candidateService.createCandidate(any(CreateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCreateUserNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		CandidateCreatedEvent testData=CandidateCreatedEvent.userNotFound(dets.getUserId());
		String content=setupContent(dets);
		when (candidateService.createCandidate(any(CreateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#findCandidate(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindCandidate() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		CandidateReadEvent testData=new CandidateReadEvent(dets.getNodeId(),dets);
		String returnedContent = setupReturnedContent(dets);
		when (candidateService.requestReadCandidate(any(RequestReadCandidateEvent.class))).thenReturn(testData);
		if (LOG.isDebugEnabled()) LOG.debug("testData - "+testData);
		this.mockMvc.perform(get(urlPrefix+"/{candidateId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.candidateId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.policyStatement",is(dets.getPolicyStatement())))
		.andExpect(jsonPath("$.userId",is(dets.getUserId().intValue())))
		.andExpect(jsonPath("$.ticketId",is(dets.getTicketDetails().getNodeId().intValue())))
		.andExpect(jsonPath("$.positionId",is(dets.getPositionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindCandidateNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		ReadEvent testData=CandidateReadEvent.notFound(dets.getNodeId());
		when (candidateService.requestReadCandidate(any(RequestReadCandidateEvent.class))).thenReturn(testData);
		if (LOG.isDebugEnabled()) LOG.debug("testData - "+testData);
		this.mockMvc.perform(get(urlPrefix+"/{candidateId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testFindCandidates() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindCandidates()");
		Long electionId=1l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.Candidate> dets=DatabaseDataFixture.populateCandidates();
		Iterable<com.eulersbridge.iEngage.database.domain.Candidate> candidates=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.Candidate> iter=candidates.iterator();
		ArrayList<CandidateDetails> candidateDets=new ArrayList<CandidateDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.Candidate article=iter.next();
			candidateDets.add(article.toCandidateDetails());
		}
		Long numElements=(long) candidateDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(electionId,candidateDets,numElements,numPages);
		when (candidateService.readCandidates(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",electionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$totalElements",is(testData.getTotalItems().intValue())))
		.andExpect(jsonPath("$totalPages",is(testData.getTotalPages())))
		.andExpect(jsonPath("$foundObjects[0].information",is(candidateDets.get(0).getInformation())))
		.andExpect(jsonPath("$foundObjects[0].policyStatement",is(candidateDets.get(0).getPolicyStatement())))
		.andExpect(jsonPath("$foundObjects[0].positionId",is(candidateDets.get(0).getPositionId().intValue())))
		.andExpect(jsonPath("$foundObjects[0].candidateId",is(candidateDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].information",is(candidateDets.get(1).getInformation())))
		.andExpect(jsonPath("$foundObjects[1].policyStatement",is(candidateDets.get(1).getPolicyStatement())))
		.andExpect(jsonPath("$foundObjects[1].positionId",is(candidateDets.get(1).getPositionId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].candidateId",is(candidateDets.get(1).getNodeId().intValue())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindCandidatesZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindCandidates()");
		Long electionId=11l;
		ArrayList<CandidateDetails> candidateDets=new ArrayList<CandidateDetails>(); 
		Long numElements=(long) candidateDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(electionId,candidateDets,numElements,numPages);
		when (candidateService.readCandidates(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",electionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindCandidatesNoElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindCandidates()");
		Long electionId=11l;
		AllReadEvent testData=AllReadEvent.notFound(null);
		when (candidateService.readCandidates(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",electionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#updateCandidate(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Candidate)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdateCandidate() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateCandidate()");
		Long id=1L;
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		dets.setInformation("Test Information that differs");
		CandidateUpdatedEvent testData=new CandidateUpdatedEvent(id, dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (candidateService.updateCandidate(any(UpdateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.candidateId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.policyStatement",is(dets.getPolicyStatement())))
		.andExpect(jsonPath("$.userId",is(dets.getUserId().intValue())))
		.andExpect(jsonPath("$.positionId",is(dets.getPositionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}
	@Test
	public void testUpdateCandidateNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateCandidate()");
		Long id=1L;
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		String content=setupContent(dets);
		when (candidateService.updateCandidate(any(UpdateCandidateEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateCandidateBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateCandidate()");
		Long id=1L;
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		CandidateUpdatedEvent testData=new CandidateUpdatedEvent(id, dets);
		String content=setupInvalidContent(dets);
		when (candidateService.updateCandidate(any(UpdateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateCandidateEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateCandidate()");
		Long id=1L;
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		CandidateUpdatedEvent testData=new CandidateUpdatedEvent(id, dets);
		when (candidateService.updateCandidate(any(UpdateCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateCandidateNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateCandidate()");
		Long id=1L;
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		String content=setupContent(dets);
		when (candidateService.updateCandidate(any(UpdateCandidateEvent.class))).thenReturn(CandidateUpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#deleteCandidate(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteCandidate() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		CandidateDeletedEvent testData=new CandidateDeletedEvent(dets.getNodeId());
		when (candidateService.deleteCandidate(any(DeleteCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{candidateId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
		.andExpect(status().isOk())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#deleteCandidate(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteCandidateNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		DeletedEvent testData=CandidateDeletedEvent.notFound(dets.getNodeId());
		when (candidateService.deleteCandidate(any(DeleteCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{candidateId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#deleteCandidate(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteCandidateForbidden() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteCandidate()");
		CandidateDetails dets=DatabaseDataFixture.populateCandidate1().toCandidateDetails();
		DeletedEvent testData=CandidateDeletedEvent.deletionForbidden(dets.getNodeId());
		when (candidateService.deleteCandidate(any(DeleteCandidateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{candidateId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#likeCandidate(java.lang.Long, java.lang.String)}.
	 */
	@Test
	public final void testLikeCandidate() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=new LikedEvent(id, user.getEmail(), true);
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);

		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$success",is(evt.isResultSuccess())))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testLikeCandidateFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=new LikedEvent(id, user.getEmail(), false);
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);

		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$success",is(evt.isResultSuccess())))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testLikedByCandidateNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());
		
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testLikedByCandidateGone() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());
		
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#unlikeCandidate(java.lang.Long, java.lang.String)}.
	 */
	@Test
	public final void testUnlikeCandidate() throws Exception
	{
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt= new LikedEvent(id, user.getEmail(), true);

		when(likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$success",is(evt.isResultSuccess())))
                .andExpect(status().isOk())	;
	}

	@Test
	public final void testUnlikeCandidateFailed() throws Exception
	{
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt= new LikedEvent(id, user.getEmail(), false);

		when(likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$success",is(evt.isResultSuccess())))
                .andExpect(status().isOk())	;
	}

    @Test
    public final void testUnLikedByCandidateNotFound() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());

        when (likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())	;
    }

    @Test
    public final void testUnLikedByCandidateGone() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());

        when (likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isGone())	;
    }

	/**
	 * 
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.CandidateController#findLikes(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindLikes() throws Exception
	{
        if (LOG.isDebugEnabled()) LOG.debug("performingFindLikes()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        Collection<UserDetails> userDetails = new ArrayList<>();
        userDetails.add(user.toUserDetails());

        LikeableObjectLikesEvent likeableObjectLikesEvent = new LikeableObjectLikesEvent(id, userDetails);


        when (likesService.likes(any(LikesLikeableObjectEvent.class), any(Sort.Direction.class), any(int.class), any(int.class))).thenReturn(likeableObjectLikesEvent);
        this.mockMvc.perform(get(urlPrefix+"/{id}/likes/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())	;
	}

    @Test
    public final void testFindLikesNotFound() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindLikes()");
        Long id=1L;
        Collection<UserDetails> userDetails = new ArrayList<>();
        ReadEvent readPollEvent = ReadEvent.notFound(id);

        LikeableObjectLikesEvent likeableObjectLikesEvent = new LikeableObjectLikesEvent(id, userDetails);


        when (likesService.likes(any(LikesLikeableObjectEvent.class), any(Sort.Direction.class), any(int.class), any(int.class))).thenReturn(likeableObjectLikesEvent);
        when (candidateService.requestReadCandidate(any(RequestReadCandidateEvent.class))).thenReturn(readPollEvent);
        this.mockMvc.perform(get(urlPrefix+"/{id}/likes/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
