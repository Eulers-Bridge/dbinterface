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

import com.eulersbridge.iEngage.core.events.task.CreateTaskEvent;
import com.eulersbridge.iEngage.core.events.task.DeleteTaskEvent;
import com.eulersbridge.iEngage.core.events.task.ReadTaskEvent;
import com.eulersbridge.iEngage.core.events.task.RequestReadTaskEvent;
import com.eulersbridge.iEngage.core.events.task.TaskCreatedEvent;
import com.eulersbridge.iEngage.core.events.task.TaskDeletedEvent;
import com.eulersbridge.iEngage.core.events.task.TaskDetails;
import com.eulersbridge.iEngage.core.events.task.TaskUpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.UpdateTaskEvent;
import com.eulersbridge.iEngage.core.services.TaskService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class TaskControllerTest
{
    private static Logger LOG = LoggerFactory.getLogger(TaskControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.TASK_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	TaskController controller;
	
	@Mock
	TaskService taskService;
	

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

	String setupContent(TaskDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"taskId\":"+evtId+",\"action\":\""+dets.getAction()+"\",\"xpValue\":"+dets.getXpValue()+"}";
		return content;
	}
	
	String setupInvalidContent(TaskDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"taskId1\":"+evtId+",\"action\":\""+dets.getAction()+"\",\"xpValue\":"+dets.getXpValue()+"}";
		return content;
	}
	
	String setupReturnedContent(TaskDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"taskId\":"+evtId+",\"action\":\""+dets.getAction()+"\",\"xpValue\":"+dets.getXpValue()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"}"+
//				",{\"rel\":\"Previous\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/previous\"},"+
//				"{\"rel\":\"Next\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/next\"},"+
//				"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}"+
				"]}";	
		 return content;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TaskController#TaskController()}.
	 */
	@Test
	public final void testTaskController()
	{
		TaskController tc=new TaskController();
		assertNotNull("Not yet implemented",tc);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TaskController#createTask(com.eulersbridge.iEngage.rest.domain.Task)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateTask() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePosition()");
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		TaskCreatedEvent testData=new TaskCreatedEvent(dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (taskService.createTask(any(CreateTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.taskId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.action",is(dets.getAction())))
		.andExpect(jsonPath("$.xpValue",is(dets.getXpValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TaskController#findTask(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindTask() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindTask()");
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		ReadTaskEvent testData=new ReadTaskEvent(dets.getNodeId(),dets);
		String returnedContent=setupReturnedContent(dets);
		when (taskService.requestReadTask(any(RequestReadTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.taskId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.action",is(dets.getAction())))
		.andExpect(jsonPath("$.xpValue",is(dets.getXpValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TaskController#updateTask(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Task)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdateTask() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTask()");
		Long id=1L;
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		dets.setAction("Test Action2");
		TaskUpdatedEvent testData=new TaskUpdatedEvent(id, dets);
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (taskService.updateTask(any(UpdateTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.taskId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.action",is(dets.getAction())))
		.andExpect(jsonPath("$.xpValue",is(dets.getXpValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TaskController#deleteTask(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteTask() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteTask()");
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		TaskDeletedEvent testData=new TaskDeletedEvent(dets.getNodeId());
		when (taskService.deleteTask(any(DeleteTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{positionId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}

}
