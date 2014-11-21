
package com.eulersbridge.iEngage.rest.controller;

import java.util.ArrayList;

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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoDetails;
import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoReadEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.GiCountry;
import com.eulersbridge.iEngage.core.events.generalInfo.GiInstitution;
import com.eulersbridge.iEngage.core.events.generalInfo.ReadGeneralInfoEvent;
import com.eulersbridge.iEngage.core.events.institutions.CreateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionCreatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDeletedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionsReadEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionsEvent;
import com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.CreateNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedReadEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.ReadNewsFeedEvent;
import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;


public class ViewInstitutionIntegrationTest
{
    private static Logger LOG = LoggerFactory.getLogger(ViewInstitutionIntegrationTest.class);

    MockMvc mockMvc;
	
	@InjectMocks
	InstitutionController controller;
	
	@Mock
	InstitutionService instService;
	
	
	@Before
	public void setup() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	@Test
	public void getShouldReturnGeneralInfoCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingGeneralInfo()");
		ArrayList<GiInstitution> insts=new ArrayList<GiInstitution>();
		GiInstitution inst=new GiInstitution(1l, "University of Melbourne");
		insts.add(inst);
		inst=new GiInstitution(2l, "Curtin University");
		insts.add(inst);
		inst=new GiInstitution(3l, "Monash University");
		insts.add(inst);
		ArrayList<GiCountry> countries=new ArrayList<GiCountry>();
		GiCountry country=new GiCountry(1l, "Australia", insts.iterator());
		countries.add(country);
		GeneralInfoDetails info=new GeneralInfoDetails(countries.iterator());
		GeneralInfoReadEvent testData=new GeneralInfoReadEvent(info);
//		String returnedContent="[{\"institutionId\":1,\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}]";
		when (instService.getGeneralInfo(any(ReadGeneralInfoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/general-info/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.countrys[0].countryName",is(countries.get(0).getCountryName())))
		.andExpect(jsonPath("$.countrys[0].countryId",is(countries.get(0).getCountryId().intValue())))
		.andExpect(jsonPath("$.countrys[0].institutions[0].institutionId",is(insts.get(0).getInstId().intValue())))
		.andExpect(jsonPath("$.countrys[0].institutions[0].institutionName",is(insts.get(0).getInstName())))
		.andExpect(jsonPath("$.countrys[0].institutions[1].institutionId",is(insts.get(1).getInstId().intValue())))
		.andExpect(jsonPath("$.countrys[0].institutions[1].institutionName",is(insts.get(1).getInstName())))
		.andExpect(jsonPath("$.countrys[0].institutions[2].institutionId",is(insts.get(2).getInstId().intValue())))
		.andExpect(jsonPath("$.countrys[0].institutions[2].institutionName",is(insts.get(2).getInstName())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void getShouldReturnInstitutionsCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		ArrayList <InstitutionDetails> insts=new ArrayList<InstitutionDetails>();
		InstitutionDetails inst=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		insts.add(inst);
		InstitutionsReadEvent testData=new InstitutionsReadEvent(insts);
		String returnedContent="[{\"institutionId\":1,\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}]";
		when (instService.readInstitutions(any(ReadInstitutionsEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/institutions/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.[0].name",is(insts.get(0).getName())))
		.andExpect(jsonPath("$.[0].state",is(insts.get(0).getState())))
		.andExpect(jsonPath("$.[0].campus",is(insts.get(0).getCampus())))
		.andExpect(jsonPath("$.[0].country",is(insts.get(0).getCountryName())))
		.andExpect(jsonPath("$.[0].institutionId",is(insts.get(0).getInstitutionId().intValue())))
		.andExpect(jsonPath("$.[0].links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void getShouldReturnUserNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		Long instId=1l;
		ReadInstitutionEvent testData=ReadInstitutionEvent.notFound(instId);
		when (instService.requestReadInstitution(any(RequestReadInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/institution/{instId}/",instId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}
	
	@Test
	public void getShouldReturnInstitutionsCorrectlyCountrySet() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		Long countryId=1l;
		ArrayList <InstitutionDetails> insts=new ArrayList<InstitutionDetails>();
		InstitutionDetails inst=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		insts.add(inst);
		InstitutionsReadEvent testData=new InstitutionsReadEvent(insts);
		String returnedContent="[{\"institutionId\":1,\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}]";
		when (instService.readInstitutions(any(ReadInstitutionsEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/institutions/{countryId}",countryId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.[0].name",is(insts.get(0).getName())))
		.andExpect(jsonPath("$.[0].state",is(insts.get(0).getState())))
		.andExpect(jsonPath("$.[0].campus",is(insts.get(0).getCampus())))
		.andExpect(jsonPath("$.[0].country",is(insts.get(0).getCountryName())))
		.andExpect(jsonPath("$.[0].institutionId",is(insts.get(0).getInstitutionId().intValue())))
		.andExpect(jsonPath("$.[0].links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void getShouldReturnTrue() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDisplayInstParams()");
		Long id=1l;
		String content="{\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\"}";
		String returnedContent="true";
		this.mockMvc.perform(post("/api/displayInstParams/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void getShouldReturnInstitutionCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		Long id=1l;
		InstitutionDetails dets=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		ReadInstitutionEvent testData=new ReadInstitutionEvent(id, dets);
		String returnedContent="{\"institutionId\":1,\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}";
		when (instService.requestReadInstitution(any(RequestReadInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/institution/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.state",is(dets.getState())))
		.andExpect(jsonPath("$.campus",is(dets.getCampus())))
		.andExpect(jsonPath("$.country",is(dets.getCountryName())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void deleteShouldReturnUserNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		Long instId=1l;
		InstitutionDeletedEvent testData=(InstitutionDeletedEvent) InstitutionDeletedEvent.notFound(instId);
		when (instService.deleteInstitution(any(DeleteInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete("/api/institution/{instId}/",instId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}
	
	@Test
	public void deleteShouldReturnInstitutionCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		Long id=1l;
		InstitutionDetails dets=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		InstitutionDeletedEvent testData=new InstitutionDeletedEvent(id, dets);
		String returnedContent="{\"institutionId\":1,\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}";
		when (instService.deleteInstitution(any(DeleteInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete("/api/institution/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.state",is(dets.getState())))
		.andExpect(jsonPath("$.campus",is(dets.getCampus())))
		.andExpect(jsonPath("$.country",is(dets.getCountryName())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void getShouldReadNewsFeedCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingReadNewsFeed()");
		Long id=1l;
		NewsFeedDetails dets=new NewsFeedDetails(id);
		dets.setNodeId(1l);
		NewsFeedReadEvent testData=new NewsFeedReadEvent(id, dets);
		String returnedContent="{\"nodeId\":1,\"institutionId\":1,\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}";
		when (instService.readNewsFeed(any(ReadNewsFeedEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/institution/{id}/newsFeed",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getShouldNotReadNewsFeedCorrectlyEntityNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingReadNewsFeed()");
		Long id=1l;
		NewsFeedReadEvent testData=NewsFeedReadEvent.notFound(id);
		when (instService.readNewsFeed(any(ReadNewsFeedEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/institution/{id}/newsFeed",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void putShouldCreateNewsFeedCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsFeed()");
		Long id=1l;
		NewsFeedDetails dets=new NewsFeedDetails(id);
		dets.setNodeId(1l);
		NewsFeedCreatedEvent testData=new NewsFeedCreatedEvent(id, dets);
		String content="{\"institutionId\":1}";
		String returnedContent="{\"nodeId\":1,\"institutionId\":1,\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}";
		when (instService.createNewsFeed(any(CreateNewsFeedEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/institution/{id}/newsFeed",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void putShouldNotCreateNewsFeedCorrectlyIdNull() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsFeed()");
		Long id=1l;
		NewsFeedDetails dets=new NewsFeedDetails(id);
		NewsFeedCreatedEvent testData=new NewsFeedCreatedEvent(null, dets);
		String content="{\"institutionId\":1}";
		when (instService.createNewsFeed(any(CreateNewsFeedEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/institution/{id}/newsFeed",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putShouldNotCreateNewsFeedCorrectlyInstNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsFeed()");
		Long id=1l;
		NewsFeedCreatedEvent testData=NewsFeedCreatedEvent.institutionNotFound(id);
		String content="{\"institutionId\":1}";
		when (instService.createNewsFeed(any(CreateNewsFeedEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/institution/{id}/newsFeed",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isFailedDependency());
	}
	
	@Test
	public void putShouldReturnInstitutionCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdate()");
		Long id=1l;
		InstitutionDetails dets=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		InstitutionUpdatedEvent testData=new InstitutionUpdatedEvent(id, dets);
		String content="{\"institutionId\":1,\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\"}";
		String returnedContent="{\"institutionId\":1,\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}";
		when (instService.updateInstitution(any(UpdateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/institution/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.state",is(dets.getState())))
		.andExpect(jsonPath("$.campus",is(dets.getCampus())))
		.andExpect(jsonPath("$.country",is(dets.getCountryName())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}
	
	@Test
	public void putShouldReturnBadRequestNoContent() throws Exception
	{	// Empty content.
		Long id=1l;
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadRequest()");
		InstitutionUpdatedEvent testData=InstitutionUpdatedEvent.countryNotFound(id);
		when (instService.updateInstitution(any(UpdateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/institution/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void putShouldReturnBadRequest() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadRequest()");
		Long id=1l;
		InstitutionDetails dets=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		InstitutionUpdatedEvent testData=new InstitutionUpdatedEvent(id, dets);
		String content="{\"givenName2\":\"Greg\",\"familyName2\":\"Newitt\",\"gender\":\"Male\",\"nationality\":\"Australian\",\"yearOfBirth\":\"1971\",\"password\":\"password\",\"accountVerified\":false,\"institutionId\":26,\"email2\":\"greg.newitt@unimelb.edu.au\"}";
		when (instService.updateInstitution(any(UpdateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/institution/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void putShouldReturnFailedDepPutEvtNull() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateBadRequest()");
		Long id=1l;
		InstitutionUpdatedEvent testData=null;
		String content="{\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Canadia\"}";
		when (instService.updateInstitution(any(UpdateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/institution/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isFailedDependency())	;
	}
	
	@Test
	public void putShouldReturnFailedDepCountryNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdate()");
		Long id=1l;
		InstitutionUpdatedEvent testData=InstitutionUpdatedEvent.countryNotFound(id);
		String content="{\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Canadia\"}";
		when (instService.updateInstitution(any(UpdateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/institution/{id}",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isFailedDependency())	;
	}
	
	@Test
	public void postShouldReturnInstitutionCorrectly() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreate()");
		Long id=1l;
		InstitutionDetails dets=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		InstitutionCreatedEvent testData=new InstitutionCreatedEvent(id, dets);
		String content="{\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\"}";
		String returnedContent="{\"institutionId\":1,\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Australia\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/institution/1\"}]}";
		when (instService.createInstitution(any(CreateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/institution/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.state",is(dets.getState())))
		.andExpect(jsonPath("$.campus",is(dets.getCampus())))
		.andExpect(jsonPath("$.country",is(dets.getCountryName())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void postShouldReturnBadRequestNoContent() throws Exception
	{	// Empty content.
		Long id=1l;
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		InstitutionCreatedEvent testData=InstitutionCreatedEvent.countryNotFound(id);
		when (instService.createInstitution(any(CreateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/institution/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void postShouldReturnBadRequest() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		Long id=1l;
		InstitutionDetails dets=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		InstitutionCreatedEvent testData=new InstitutionCreatedEvent(id, dets);
		String content="{\"givenName2\":\"Greg\",\"familyName2\":\"Newitt\",\"gender\":\"Male\",\"nationality\":\"Australian\",\"yearOfBirth\":\"1971\",\"password\":\"password\",\"accountVerified\":false,\"institutionId\":26,\"email2\":\"greg.newitt@unimelb.edu.au\"}";
		when (instService.createInstitution(any(CreateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/institution/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void postShouldReturnBadRequestIDNull() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		InstitutionDetails dets=DatabaseDataFixture.populateInstUniMelb().toInstDetails();
		InstitutionCreatedEvent testData=new InstitutionCreatedEvent(null, dets);
		String content="{\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Canadia\"}";
		when (instService.createInstitution(any(CreateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/institution/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;
	}
	
	@Test
	public void postShouldReturnFailedDependency() throws Exception
	{	// Empty content.
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateBadRequest()");
		Long id=1l;
		InstitutionCreatedEvent testData=InstitutionCreatedEvent.countryNotFound(id);
		String content="{\"name\":\"University of Melbourne\",\"state\":\"Victoria\",\"campus\":\"Parkville\",\"country\":\"Canadia\"}";
		when (instService.createInstitution(any(CreateInstitutionEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/institution/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isFailedDependency())	;
	}	
}
