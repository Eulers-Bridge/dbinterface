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
import java.util.HashMap;
import java.util.Iterator;

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

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeleteEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.RequestReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.notifications.Message;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.core.services.NotificationService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.notifications.Notification;

/**
 * @author Greg Newitt
 *
 */
public class NotificationControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(NotificationControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.NOTIFICATION_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	NotificationController controller;
	
	@Mock
	NotificationService notificationService;
	
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

	String setupContent(NotificationDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		Message message=(Message)dets.getNotificationBody();
		String content="{\"nodeId\":"+evtId+",\"type\":\""+dets.getType()+"\",\"timestamp\":"+dets.getTimestamp()+
						",\"userId\":"+dets.getUserId()+",\"read\":"+dets.getRead()+",\"notificationBody\": {\"nodeId\":"+message.getNodeId()+",\"text\":\""+message.getText()+"\"}}";
		return content;
	}
	
	String setupInvalidContent(NotificationDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"nodeId1\":"+evtId+",\"type\":\""+dets.getType()+"\",\"timestamp\":"+dets.getTimestamp()+
					   ",\"userId\":"+dets.getUserId()+",\"read\":"+dets.getRead()+",\"notificationBody\":{"+dets.getNotificationBody()+"}}";
		return content;
	}
	
	String setupReturnedContent(NotificationDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		Message message=(Message)dets.getNotificationBody();
		String content="{\"nodeId\":"+evtId+",\"userId\":"+dets.getUserId()+",\"timestamp\":"+dets.getTimestamp()+",\"read\":"+dets.getRead()+
				",\"type\":\""+dets.getType()+"\",\"notificationBody\":{\"nodeId\":"+message.getNodeId()+",\"text\":\""+message.getText()+"\"},\"links\":["+
//				"{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"}"+
//				",{\"rel\":\"Previous\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/previous\"},"+
//				"{\"rel\":\"Next\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/next\"},"+
//				"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}"+
				"]}";	
		 return content;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.NotificationController#NotificationController()}.
	 */
	@Test
	public final void testNotificationController()
	{
		NotificationController tc=new NotificationController();
		assertNotNull("Not yet implemented",tc);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.NotificationController#createNotification(com.eulersbridge.iEngage.rest.domain.Notification)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateNotification() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		if (LOG.isDebugEnabled()) LOG.debug("dets - "+dets);
		CreatedEvent testData=new CreatedEvent(dets);
		String content=setupContent(dets);
		if (LOG.isDebugEnabled()) LOG.debug("content - "+content);
		String returnedContent=setupReturnedContent(dets);
		if (LOG.isDebugEnabled()) LOG.debug("returned content - "+returnedContent);
		when (notificationService.createNotification(any(CreateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.type",is(dets.getType())))
		.andExpect(jsonPath("$.timestamp",is(dets.getTimestamp())))
		.andExpect(jsonPath("$.userId",is(dets.getUserId().intValue())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreateNotificationNullNodeIdReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		CreatedEvent testData=new CreatedEvent(dets);
		String content=setupContent(dets);
		dets.setNodeId(null);
		when (notificationService.createNotification(any(CreateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateNotificationFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		CreatedEvent testData=CreatedEvent.failed(dets);
		String content=setupContent(dets);
		when (notificationService.createNotification(any(CreateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCreateNotificationNullReturnEvent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		String content=setupContent(dets);
		when (notificationService.createNotification(any(CreateEvent.class))).thenReturn(null);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.NotificationController#findNotification(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindNotification() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		ReadEvent testData=new ReadEvent(dets.getNodeId(),dets);
		String returnedContent=setupReturnedContent(dets);
		when (notificationService.readNotification(any(RequestReadEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.type",is(dets.getType())))
		.andExpect(jsonPath("$.timestamp",is(dets.getTimestamp())))
		.andExpect(jsonPath("$.userId",is(dets.getUserId().intValue())))
//		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindNotificationNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		ReadEvent testData=ReadEvent.notFound(dets.getNodeId());
		when (notificationService.readNotification(any(RequestReadEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testFindNotificationNotFoundNullEvent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		when (notificationService.readNotification(any(RequestReadEvent.class))).thenReturn(null);
		this.mockMvc.perform(get(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.NotificationController#findNotifications(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindNotifications() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindNotifications()");
		Long userId=1l;
		HashMap<Long, Notification> dets=DatabaseDataFixture.populateNotifications();
		Iterable<Notification> notifications=dets.values();
		Iterator<Notification> iter=notifications.iterator();
		ArrayList<NotificationDetails> notificationDets=new ArrayList<NotificationDetails>(); 
		while (iter.hasNext())
		{
			Notification notification=iter.next();
			if (LOG.isDebugEnabled()) LOG.debug("notification - "+notification);
			notificationDets.add(notification.toNotificationDetails());
		}
		AllReadEvent testData=new AllReadEvent(userId,notificationDets);
		when (notificationService.readNotifications(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",userId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$[0].nodeId",is(notificationDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$[0].type",is(notificationDets.get(0).getType())))
		.andExpect(jsonPath("$[0].timestamp",is(notificationDets.get(0).getTimestamp())))
		.andExpect(jsonPath("$[0].userId",is(notificationDets.get(0).getUserId().intValue())))
		.andExpect(jsonPath("$[1].nodeId",is(notificationDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$[1].type",is(notificationDets.get(1).getType())))
		.andExpect(jsonPath("$[1].timestamp",is(notificationDets.get(1).getTimestamp())))
		.andExpect(jsonPath("$[1].userId",is(notificationDets.get(1).getUserId().intValue())))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindNotificationsZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindNotifications()");
		Long userId=11l;
		ArrayList<NotificationDetails> eleDets=new ArrayList<NotificationDetails>(); 
		AllReadEvent testData=new AllReadEvent(userId,eleDets);
		when (notificationService.readNotifications(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",userId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindNotificationsNoElection() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindNotifications()");
		Long userId=11l;
		AllReadEvent testData=AllReadEvent.notFound(userId);
		when (notificationService.readNotifications(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{parentId}/",userId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.NotificationController#deleteNotification(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteNotification() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		if (LOG.isDebugEnabled()) LOG.debug("dets - "+dets);
		DeletedEvent testData=new DeletedEvent(dets.getNodeId(),dets);
		if (LOG.isDebugEnabled()) LOG.debug("testData - "+testData);
		String returnedContent=setupReturnedContent(dets);
		if (LOG.isDebugEnabled()) LOG.debug("returnedContent - "+returnedContent);
		when (notificationService.deleteNotification(any(DeleteEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testDeleteNotificationNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		if (LOG.isDebugEnabled()) LOG.debug("dets - "+dets);
		DeletedEvent testData=DeletedEvent.notFound(dets.getNodeId());
		when (notificationService.deleteNotification(any(DeleteEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("{\"success\":false,\"errorReason\":\"Not found\",\"responseObject\":null}"))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteNotificationNotFoundNullEvent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteNotification()");
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		when (notificationService.deleteNotification(any(DeleteEvent.class))).thenReturn(null);
		this.mockMvc.perform(delete(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("{\"success\":false,\"errorReason\":\"Not found\",\"responseObject\":null}"))
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.NotificationController#updateNotification(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Notification)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdateNotification() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateNotification()");
		Long id=1L;
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		dets.setTimestamp(dets.getTimestamp()+43);
		UpdatedEvent testData=new UpdatedEvent(id, dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (notificationService.updateNotification(any(UpdateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.type",is(dets.getType())))
		.andExpect(jsonPath("$.timestamp",is(dets.getTimestamp())))
		.andExpect(jsonPath("$.userId",is(dets.getUserId().intValue())))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}
	@Test
	public void testUpdateNotificationNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateNotification()");
		Long id=1L;
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		String content=setupContent(dets);
		when (notificationService.updateNotification(any(UpdateEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateNotificationBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateNotification()");
		Long id=1L;
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		UpdatedEvent testData=new UpdatedEvent(id, dets);
		String content=setupInvalidContent(dets);
		when (notificationService.updateNotification(any(UpdateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateNotificationEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateNotification()");
		Long id=1L;
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		UpdatedEvent testData=new UpdatedEvent(id, dets);
		when (notificationService.updateNotification(any(UpdateEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateNotificationNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateNotification()");
		Long id=1L;
		NotificationDetails dets=DatabaseDataFixture.populateNotification1().toNotificationDetails();
		String content=setupContent(dets);
		when (notificationService.updateNotification(any(UpdateEvent.class))).thenReturn(UpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

}
