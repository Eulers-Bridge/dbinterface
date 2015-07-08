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
import java.util.Calendar;
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
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.CompletedTaskEvent;
import com.eulersbridge.iEngage.core.events.task.CreateTaskEvent;
import com.eulersbridge.iEngage.core.events.task.DeleteTaskEvent;
import com.eulersbridge.iEngage.core.events.task.ReadCompletedTasksEvent;
import com.eulersbridge.iEngage.core.events.task.ReadTaskEvent;
import com.eulersbridge.iEngage.core.events.task.RequestReadTaskEvent;
import com.eulersbridge.iEngage.core.events.task.TaskCompleteDetails;
import com.eulersbridge.iEngage.core.events.task.TaskCreatedEvent;
import com.eulersbridge.iEngage.core.events.task.TaskDeletedEvent;
import com.eulersbridge.iEngage.core.events.task.TaskDetails;
import com.eulersbridge.iEngage.core.events.task.TaskUpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.UpdateTaskEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.TaskService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;

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
		
		MappingJackson2HttpMessageConverter converter=RestDataFixture.setUpConverter();
		this.mockMvc = standaloneSetup(controller).setMessageConverters(converter).build();
	}

	String setupContent(TaskDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"taskId\":"+evtId+",\"action\":\""+dets.getAction()+"\",\"description\":\""+dets.getDescription()+"\",\"xpValue\":"+dets.getXpValue()+"}";
		return content;
	}
	
	String setupInvalidContent(TaskDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"taskId1\":"+evtId+",\"action\":\""+dets.getAction()+"\",\"description\":\""+dets.getDescription()+"\",\"xpValue\":"+dets.getXpValue()+"}";
		return content;
	}
	
	String setupReturnedContent(TaskDetails dets)
	{
		int evtId=dets.getNodeId().intValue();
		String content="{\"taskId\":"+evtId+",\"action\":\""+dets.getAction()+"\",\"description\":\""+dets.getDescription()+"\",\"xpValue\":"+dets.getXpValue()+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"\"}"+
//				",{\"rel\":\"Previous\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/previous\"},"+
//				"{\"rel\":\"Next\",\"href\":\"http://localhost"+urlPrefix+"/"+evtId+"/next\"},"+
//				"{\"rel\":\"Read all\",\"href\":\"http://localhost"+urlPrefix+"s\"}"+
				"]}";	
		 return content;
	}

	String setupReturnedCompletedContent(TaskCompleteDetails dets)
	{
		int nodeId=dets.getNodeId().intValue();
		int taskId=dets.getTaskId().intValue();
		int userId=dets.getUserId().intValue();
		String content="{\"nodeId\":"+nodeId+",\"taskId\":"+taskId+",\"timestamp\":"+dets.getDate()+",\"userId\":"+userId+
				",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost"+urlPrefix+"/"+taskId+"/complete/"+userId+"\"}"+
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
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.xpValue",is(dets.getXpValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreateTaskNullEvt() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTask()");
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		String content=setupContent(dets);
		when (taskService.createTask(any(CreateTaskEvent.class))).thenReturn(null);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateTaskInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTask()");
		TaskCreatedEvent testData=null;
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		String content=setupInvalidContent(dets);
		when (taskService.createTask(any(CreateTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateEventNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTask()");
		TaskCreatedEvent testData=null;
		when (taskService.createTask(any(CreateTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateTaskNullNodeId() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTask()");
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		String content=setupContent(dets);
		TaskCreatedEvent testData=new TaskCreatedEvent(dets);
		testData.setNodeId(null);
		when (taskService.createTask(any(CreateTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateTaskFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateTask()");
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		String content=setupContent(dets);
		CreatedEvent testData=TaskCreatedEvent.failed(dets);
		testData.setNodeId(null);
		when (taskService.createTask(any(CreateTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
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
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.xpValue",is(dets.getXpValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;
	}

    @Test
    public void testFindTaskNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("performingFindTask()");
        TaskDetails dets = DatabaseDataFixture.populateTask1().toTaskDetails();
        ReadEvent testData = ReadTaskEvent.notFound(dets.getNodeId());
        when (taskService.requestReadTask(any(RequestReadTaskEvent.class))).thenReturn(testData);
        this.mockMvc.perform(get(urlPrefix + "/{badgeId}", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

	@Test
	public final void testFindTasks() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindTasks()");
		HashMap<Long, com.eulersbridge.iEngage.database.domain.Task> dets=DatabaseDataFixture.populateTasks();
		Iterable<com.eulersbridge.iEngage.database.domain.Task> tasks=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.Task> iter=tasks.iterator();
		ArrayList<TaskDetails> taskDets=new ArrayList<TaskDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.Task article=iter.next();
			taskDets.add(article.toTaskDetails());
		}
		Long numElements=(long) taskDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(null,taskDets,numElements,numPages);
		when (taskService.readTasks(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("$totalPages",is(numPages)))
		.andExpect(jsonPath("$foundObjects[0].action",is(taskDets.get(0).getAction())))
		.andExpect(jsonPath("$foundObjects[0].description",is(taskDets.get(0).getDescription())))
		.andExpect(jsonPath("$foundObjects[0].xpValue",is(taskDets.get(0).getXpValue())))
		.andExpect(jsonPath("$foundObjects[0].taskId",is(taskDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].action",is(taskDets.get(1).getAction())))
		.andExpect(jsonPath("$foundObjects[1].description",is(taskDets.get(1).getDescription())))
		.andExpect(jsonPath("$foundObjects[1].xpValue",is(taskDets.get(1).getXpValue())))
		.andExpect(jsonPath("$foundObjects[1].taskId",is(taskDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$foundObjects[0].links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindTasksZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindTasks()");
		ArrayList<TaskDetails> eleDets=new ArrayList<TaskDetails>(); 
		AllReadEvent testData=new AllReadEvent(null,eleDets);
		when (taskService.readTasks(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindCompletedTasks() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindCompletedTasks()");
		Long userId=345l;
		HashMap<Long, com.eulersbridge.iEngage.database.domain.Task> dets=DatabaseDataFixture.populateTasks();
		Iterable<com.eulersbridge.iEngage.database.domain.Task> tasks=dets.values();
		Iterator<com.eulersbridge.iEngage.database.domain.Task> iter=tasks.iterator();
		ArrayList<TaskDetails> taskDets=new ArrayList<TaskDetails>(); 
		while (iter.hasNext())
		{
			com.eulersbridge.iEngage.database.domain.Task article=iter.next();
			taskDets.add(article.toTaskDetails());
		}
		Long numElements=(long) taskDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		AllReadEvent testData=new AllReadEvent(null,taskDets,numElements,numPages);
		when (taskService.readCompletedTasks(any(ReadCompletedTasksEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/complete/{userId}",userId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("$totalPages",is(numPages)))
		.andExpect(jsonPath("$foundObjects[0].action",is(taskDets.get(0).getAction())))
		.andExpect(jsonPath("$foundObjects[0].description",is(taskDets.get(0).getDescription())))
		.andExpect(jsonPath("$foundObjects[0].xpValue",is(taskDets.get(0).getXpValue())))
		.andExpect(jsonPath("$foundObjects[0].taskId",is(taskDets.get(0).getNodeId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].action",is(taskDets.get(1).getAction())))
		.andExpect(jsonPath("$foundObjects[1].description",is(taskDets.get(1).getDescription())))
		.andExpect(jsonPath("$foundObjects[1].xpValue",is(taskDets.get(1).getXpValue())))
		.andExpect(jsonPath("$foundObjects[1].taskId",is(taskDets.get(1).getNodeId().intValue())))
		.andExpect(jsonPath("$foundObjects[0].links[0].rel",is("self")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindCompletedTasksZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindCompletedTasks()");
		Long userId=345l;
		ArrayList<TaskDetails> eleDets=new ArrayList<TaskDetails>(); 
		AllReadEvent testData=new AllReadEvent(null,eleDets);
		when (taskService.readCompletedTasks(any(ReadCompletedTasksEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/complete/{userId}",userId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
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
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.xpValue",is(dets.getXpValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
//		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
//		.andExpect(jsonPath("$.links[2].rel",is("Next")))
//		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testUpdateTaskNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTask()");
		Long id=1L;
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		String content=setupContent(dets);
		when (taskService.updateTask(any(UpdateTaskEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateTaskBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTask()");
		Long id=1L;
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		TaskUpdatedEvent testData=new TaskUpdatedEvent(id, dets);
		String content=setupInvalidContent(dets);
		when (taskService.updateTask(any(UpdateTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateTaskEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTask()");
		Long id=1L;
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		TaskUpdatedEvent testData=new TaskUpdatedEvent(id, dets);
		when (taskService.updateTask(any(UpdateTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testUpdateTaskNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTask()");
		Long id=1L;
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		String content=setupContent(dets);
		when (taskService.updateTask(any(UpdateTaskEvent.class))).thenReturn(TaskUpdatedEvent.notFound(id));
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
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
		.andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
		.andExpect(status().isOk())	;
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TaskController#deleteTask(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteTaskNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteTask()");
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		DeletedEvent testData=TaskDeletedEvent.notFound(dets.getNodeId());
		when (taskService.deleteTask(any(DeleteTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{taskId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TaskController#deleteTask(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeleteTaskForbidden() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteTask()");
		TaskDetails dets=DatabaseDataFixture.populateTask1().toTaskDetails();
		DeletedEvent testData=TaskDeletedEvent.deletionForbidden(dets.getNodeId());
		when (taskService.deleteTask(any(DeleteTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{taskId}",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.TaskController#completedTask(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Task)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCompletedTask() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTask()");
		TaskDetails taskDets=DatabaseDataFixture.populateTask1().toTaskDetails();
		UserDetails userDets=DatabaseDataFixture.populateUserGnewitt().toUserDetails();
		Long taskId=taskDets.getNodeId();
		Long userId=userDets.getNodeId();
		Long id=1453l;
		Long now=Calendar.getInstance().getTimeInMillis();
        TaskCompleteDetails dets=new TaskCompleteDetails(id, userId, taskId, now);
		UpdatedEvent testData=new UpdatedEvent(453l,dets);
		String returnedContent=setupReturnedCompletedContent(dets);
		when (taskService.completedTask(any(CompletedTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{taskId}/complete/{userId}",taskId.intValue(),userId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.taskId",is(dets.getTaskId().intValue())))
		.andExpect(jsonPath("$.userId",is(dets.getUserId().intValue())))
		.andExpect(jsonPath("$.timestamp",is(dets.getDate())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testCompletedTaskTaskNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTask()");
		TaskDetails taskDets=DatabaseDataFixture.populateTask1().toTaskDetails();
		UserDetails userDets=DatabaseDataFixture.populateUserGnewitt().toUserDetails();
		Long taskId=taskDets.getNodeId();
		Long userId=userDets.getNodeId();
		UpdatedEvent testData=UpdatedEvent.notFound(taskId);
		when (taskService.completedTask(any(CompletedTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{taskId}/complete/{userId}",taskId.intValue(),userId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCompletedTaskNullEvtReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdateTask()");
		TaskDetails taskDets=DatabaseDataFixture.populateTask1().toTaskDetails();
		UserDetails userDets=DatabaseDataFixture.populateUserGnewitt().toUserDetails();
		Long taskId=taskDets.getNodeId();
		Long userId=userDets.getNodeId();
		UpdatedEvent testData=null;
		when (taskService.completedTask(any(CompletedTaskEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{taskId}/complete/{userId}",taskId.intValue(),userId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}


}
