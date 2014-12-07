package com.eulersbridge.iEngage.rest.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryCreatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDeletedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.core.events.countrys.CountryReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryUpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountrysReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CreateCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.DeleteCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountrysEvent;
import com.eulersbridge.iEngage.core.events.countrys.UpdateCountryEvent;
import com.eulersbridge.iEngage.core.services.CountryService;
import com.eulersbridge.iEngage.rest.domain.Institution;
import com.eulersbridge.iEngage.rest.domain.Country;

public class ViewCountryIntegrationTest 
{
    private static Logger LOG = LoggerFactory.getLogger(ViewCountryIntegrationTest.class);

    MockMvc mockMvc;
	
	@InjectMocks
	CountryController controller;
	
	@Mock
	CountryService countryService;
	
	@Before
	public void setUp() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	@Test
	public void testAlterCountry() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingAlterCountry()");
		Long id=1L;
		CountryDetails dets=new CountryDetails(id);
		dets.setCountryName("Australia");
		CountryUpdatedEvent testData=new CountryUpdatedEvent(id, dets);
		String content="{\"countryName\":\"Australia\",\"institutions\":null,\"countryId\":"+id.intValue()+"}";
		String returnedContent="{\"countryId\":"+id.intValue()+",\"countryName\":\"Australia\",\"institutions\":null,\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/country/"+id.intValue()+
							   "\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/countrys\"}]}";
		when (countryService.updateCountry(any(UpdateCountryEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put("/api/country/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.countryName",is(dets.getCountryName())))
//TODO		.andExpect(jsonPath("$.universities",is(dets.get())))
		.andExpect(jsonPath("$.countryId",is(id.intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testAlterCountryNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingAlterCountry()");
		Long id=1L;
		CountryDetails dets=new CountryDetails(id);
		dets.setCountryName("Australia");
		String content="{\"countryName\":\"Australia\",\"institutions\":null,\"countryId\":"+id.intValue()+"}";
		when (countryService.updateCountry(any(UpdateCountryEvent.class))).thenReturn(null);
		this.mockMvc.perform(put("/api/country/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isFailedDependency())	;		
	}

	@Test
	public void testFindCountry() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingRead()");
		Long id=1L;
		CountryDetails countryDetails=new CountryDetails(id);
		countryDetails.setCountryName("Australia");
		String returnedContent="{\"countryId\":"+id.intValue()+",\"countryName\":\"Australia\",\"institutions\":null,\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/country/"+id.intValue()+
								"\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/countrys\"}]}";
		CountryReadEvent testData=new CountryReadEvent(id, countryDetails);
		when (countryService.readCountry(any(ReadCountryEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/country/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.countryName",is(countryDetails.getCountryName())))
		.andExpect(jsonPath("$.countryId",is(countryDetails.getCountryId().intValue())))
		.andExpect(content().string(returnedContent))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public void testFindCountryNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingReadNotFound()");
		Long id=1L;
		CountryDetails countryDetails=new CountryDetails(id);
		countryDetails.setCountryName("Australia");
		ReadEvent testData=CountryReadEvent.notFound(id);
		when (countryService.readCountry(any(ReadCountryEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/country/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public void testDeleteCountry() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDelete()");
		Long id=1L;
		CountryDetails countryDetails=new CountryDetails(id);
		countryDetails.setCountryName("Australia");
		String returnedContent="{\"countryId\":"+id.intValue()+",\"countryName\":\"Australia\",\"institutions\":null,\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/country/"+id.intValue()+
							   "\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/countrys\"}]}";
		CountryDeletedEvent testData=new CountryDeletedEvent(id, countryDetails);
		when (countryService.deleteCountry(any(DeleteCountryEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete("/api/country/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.countryName",is(countryDetails.getCountryName())))
		.andExpect(jsonPath("$.countryId",is(countryDetails.getCountryId().intValue())))
		.andExpect(content().string(returnedContent))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public void testDeleteCountryNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteNotFound()");
		Long id=1L;
		CountryDetails countryDetails=new CountryDetails(id);
		countryDetails.setCountryName("Australia");
		DeletedEvent testData=CountryDeletedEvent.notFound(id);
		when (countryService.deleteCountry(any(DeleteCountryEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete("/api/country/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public void testCreateCountry() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCountry()");
		Long id=1L;
		CountryDetails dets=new CountryDetails(id);
		dets.setCountryName("Australia");
		CountryCreatedEvent testData=new CountryCreatedEvent(id, dets);
		String content="{\"countryName\":\"Australia\",\"institutions\":null}";
		String returnedContent="{\"countryId\":"+id.intValue()+",\"countryName\":\"Australia\",\"institutions\":null,\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/country/"+id.intValue()+
								"\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/countrys\"}]}";
		when (countryService.createCountry(any(CreateCountryEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/country/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.countryName",is(dets.getCountryName())))
//TODO		.andExpect(jsonPath("$.universities",is(dets.get())))
		.andExpect(jsonPath("$.countryId",is(id.intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public void testCreateCountryBadRequestNullId() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCountry()");
		Long id=null;
		CountryDetails dets=new CountryDetails(id);
		dets.setCountryName("Australia");
		CountryCreatedEvent testData=new CountryCreatedEvent(id, dets);
		String content="{\"countryName\":\"Australia\",\"institutions\":null}";
		when (countryService.createCountry(any(CreateCountryEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post("/api/country/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testCreateCountryBadRequestNullEvent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateCountry()");
		String content="{\"countryName\":\"Australia\",\"institutions\":null}";
		when (countryService.createCountry(any(CreateCountryEvent.class))).thenReturn(null);
		this.mockMvc.perform(post("/api/country/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testDisplayDetails() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingTestDisplayDetails()");
		String content="{\"countryName\":\"Australia\",\"institutions\":null}";
		this.mockMvc.perform(post("/api/displayCountryParams/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testGetCountrys() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingReadAll()");
		Long id=1L;
		String countryName="Australia";
		String returnedContent="[{\"countryId\":"+id.intValue()+",\"countryName\":\""+countryName+"\",\"institutions\":null,\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/country/"+id.intValue()+
								"\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/countrys\"}]}]";
		ArrayList<CountryDetails> countrys=new ArrayList<CountryDetails>();
		Institution institution=new Institution(id, "University of Melbourne");
		Institution institutions[]={institution};
		Country country=new Country(id, countryName, institutions);
		CountryDetails countryDetails=country.toCountryDetails();
		countrys.add(countryDetails);
		CountrysReadEvent testData=new CountrysReadEvent(countrys);
		when (countryService.readCountrys(any(ReadCountrysEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get("/api/countrys").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].countryName",is(countryDetails.getCountryName())))
		.andExpect(jsonPath("$[0].countryId",is(countryDetails.getCountryId().intValue())))
		.andExpect(content().string(returnedContent))
		.andExpect(jsonPath("$[0].links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

}
