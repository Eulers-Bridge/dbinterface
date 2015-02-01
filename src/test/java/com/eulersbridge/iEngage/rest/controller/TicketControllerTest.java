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

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.ticket.CreateTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.DeleteTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.ReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.RequestReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketCreatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketDeletedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.core.events.ticket.TicketUpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.UpdateTicketEvent;
import com.eulersbridge.iEngage.core.services.TicketService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class TicketControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(TicketControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.TICKET_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	TicketController controller;
	
	@Mock
	TicketService ticketService;
	

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

	String setupContent(TicketDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"ticketId\":"+evtId+",\"name\":\""+dets.getName()+"\",\"information\":\""+dets.getInformation()+"\",\"logo\":\""+dets.getLogo()+"\",\"electionId\":"+dets.getElectionId()+"}";
		return content;
	}
	
	String setupInvalidContent(TicketDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"ticketId1\":"+evtId+",\"name\":\""+dets.getName()+"\",\"information\":\""+dets.getInformation()+"\",\"logo\":\""+dets.getLogo()+"\",\"electionId\":"+dets.getElectionId()+"}";
		return content;
	}
	
	String setupReturnedContent(TicketDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"ticketId\":"+evtId+",\"name\":\""+dets.getName()+"\",\"logo\":\""+dets.getLogo()+
						"\",\"pictures\":null,\"information\":\""+dets.getInformation()+
						"\",\"candidateIds\":"+dets.getCandidateIds()+",\"electionId\":"+dets.getElectionId()+
						",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"},"+
//						"{\"rel\":\"Previous\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/previous\"},"+
//						"{\"rel\":\"Next\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/next\"},"+
						"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}]}";	
		 return content;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TicketController#TicketController()}.
	 */
	@Test
	public final void testTicketController()
	{
		TicketController tc=new TicketController();
		assertNotNull("Not yet implemented",tc);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TicketController#createTicket(com.eulersbridge.iEngage.rest.domain.Ticket)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateTicket() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		TicketCreatedEvent testData=new TicketCreatedEvent(dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (ticketService.createTicket(any(CreateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.ticketId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.logo",is(dets.getLogo())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[1].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreateTicketNullEvt() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		String content=setupContent(dets);
		when (ticketService.createTicket(any(CreateTicketEvent.class))).thenReturn(null);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateTicketInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTicket()");
		TicketCreatedEvent testData=null;
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		String content=setupInvalidContent(dets);
		when (ticketService.createTicket(any(CreateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateEventNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTicket()");
		TicketCreatedEvent testData=null;
		when (ticketService.createTicket(any(CreateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateTicketNullNodeId() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		String content=setupContent(dets);
		TicketCreatedEvent testData=new TicketCreatedEvent(dets);
		testData.setNodeId(null);
		when (ticketService.createTicket(any(CreateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateTicketFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		String content=setupContent(dets);
		CreatedEvent testData=TicketCreatedEvent.failed(dets);
		testData.setNodeId(null);
		when (ticketService.createTicket(any(CreateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateElectionNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateEvent()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		TicketCreatedEvent testData=TicketCreatedEvent.electionNotFound(dets.getElectionId());
		String content=setupContent(dets);
		when (ticketService.createTicket(any(CreateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TicketController#findTicket(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindTicket() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		ReadTicketEvent testData=new ReadTicketEvent(dets.getNodeId(),dets);
		String returnedContent=setupReturnedContent(dets);
		when (ticketService.requestReadTicket(any(RequestReadTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{ticketId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.ticketId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.logo",is(dets.getLogo())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindTicketNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		ReadEvent testData=ReadTicketEvent.notFound(dets.getNodeId());
		when (ticketService.requestReadTicket(any(RequestReadTicketEvent.class))).thenReturn(testData);
		if (LOG.isDebugEnabled()) LOG.debug("testData - "+testData);
		this.mockMvc.perform(get(urlPrefix+"/{ticketId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TicketController#updateTicket(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Ticket)}.
	 */
	@Test
	public final void testUpdateTicket() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTicket()");
		Long id=1L;
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		dets.setName("Test Name2");
		TicketUpdatedEvent testData=new TicketUpdatedEvent(id, dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (ticketService.updateTicket(any(UpdateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.name",is(dets.getName())))
		.andExpect(jsonPath("$.information",is(dets.getInformation())))
		.andExpect(jsonPath("$.ticketId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.logo",is(dets.getLogo())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testUpdateTicketNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTicket()");
		Long id=1L;
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		String content=setupContent(dets);
		when (ticketService.updateTicket(any(UpdateTicketEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateTicketBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTicket()");
		Long id=1L;
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		TicketUpdatedEvent testData=new TicketUpdatedEvent(id, dets);
		String content=setupInvalidContent(dets);
		when (ticketService.updateTicket(any(UpdateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateTicketEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTicket()");
		Long id=1L;
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		TicketUpdatedEvent testData=new TicketUpdatedEvent(id, dets);
		when (ticketService.updateTicket(any(UpdateTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateTicketNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTicket()");
		Long id=1L;
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		String content=setupContent(dets);
		when (ticketService.updateTicket(any(UpdateTicketEvent.class))).thenReturn(TicketUpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TicketController#deleteTicket(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteTicket() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		TicketDeletedEvent testData=new TicketDeletedEvent(dets.getNodeId());
		when (ticketService.deleteTicket(any(DeleteTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{ticketId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}
	@Test
	public final void testDeleteTicketNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		DeletedEvent testData=TicketDeletedEvent.notFound(dets.getNodeId());
		when (ticketService.deleteTicket(any(DeleteTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{ticketId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteTicketForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteTicket()");
		TicketDetails dets=DatabaseDataFixture.populateTicket1().toTicketDetails();
		DeletedEvent testData=TicketDeletedEvent.deletionForbidden(dets.getNodeId());
		when (ticketService.deleteTicket(any(DeleteTicketEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{ticketId}/",dets.getNodeId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}
}