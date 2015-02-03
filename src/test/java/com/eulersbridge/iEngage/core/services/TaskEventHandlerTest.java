/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.CreateTaskEvent;
import com.eulersbridge.iEngage.core.events.task.DeleteTaskEvent;
import com.eulersbridge.iEngage.core.events.task.ReadTaskEvent;
import com.eulersbridge.iEngage.core.events.task.RequestReadTaskEvent;
import com.eulersbridge.iEngage.core.events.task.TaskDetails;
import com.eulersbridge.iEngage.core.events.task.TasksReadEvent;
import com.eulersbridge.iEngage.core.events.task.UpdateTaskEvent;
import com.eulersbridge.iEngage.database.domain.Task;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.CandidateRepository;
import com.eulersbridge.iEngage.database.repository.TaskRepository;

/**
 * @author Greg Newitt
 *
 */
public class TaskEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(TaskEventHandlerTest.class);

    @Mock
	TaskRepository taskRepository;
    @Mock
	CandidateRepository candidateRepository;

    TaskEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

//		service=new TaskEventHandler(taskRepository,candidateRepository);
		service=new TaskEventHandler(taskRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskEventHandler#TaskEventHandler(com.eulersbridge.iEngage.database.repository.TaskRepository)}.
	 */
	@Test
	public final void testTaskEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskEventHandler#createTask(com.eulersbridge.iEngage.core.events.task.CreateTaskEvent)}.
	 */
	@Test
	public final void testCreateTask()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingTask()");
		Task testData=DatabaseDataFixture.populateTask1();
		when(taskRepository.save(any(Task.class))).thenReturn(testData);
		TaskDetails dets=testData.toTaskDetails();
		CreateTaskEvent createTaskEvent=new CreateTaskEvent(dets);
		CreatedEvent evtData = service.createTask(createTaskEvent);
		Details returnedDets = evtData.getDetails();
		assertEquals(testData.toTaskDetails(),returnedDets);
		assertEquals(testData.getNodeId(),returnedDets.getNodeId());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskEventHandler#createTask(com.eulersbridge.iEngage.core.events.task.CreateTaskEvent)}.
	 */
	@Test
	public final void testCreateTaskFailed()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingTask()");
		Task testData=DatabaseDataFixture.populateTask1();
		when(taskRepository.save(any(Task.class))).thenReturn(null);
		TaskDetails dets=testData.toTaskDetails();
		CreateTaskEvent createPositionEvent=new CreateTaskEvent(dets);
		CreatedEvent evtData = service.createTask(createPositionEvent);
		TaskDetails returnedDets = (TaskDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toTaskDetails());
		assertTrue(evtData.isFailed());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskEventHandler#requestReadTask(com.eulersbridge.iEngage.core.events.task.RequestReadTaskEvent)}.
	 */
	@Test
	public final void testRequestReadTask()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTask()");
		Task testData=DatabaseDataFixture.populateTask1();
		when(taskRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadTaskEvent requestReadTaskEvent=new RequestReadTaskEvent(testData.getNodeId());
		ReadTaskEvent evtData = (ReadTaskEvent) service.requestReadTask(requestReadTaskEvent);
		TaskDetails returnedDets = (TaskDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toTaskDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskTaskHandler#readTasks(com.eulersbridge.iEngage.core.events.events.ReadAllTask,Direction,int,int)}.
	 */
	@Test
	public final void testReadTasks()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTasks()");
		HashMap<Long, Task> events = DatabaseDataFixture.populateTasks();
		ArrayList<Task> evts=new ArrayList<Task>();
		Iterator<Task> iter=events.values().iterator();
		while (iter.hasNext())
		{
			Task na=iter.next();
			evts.add(na);
		}

		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<Task> testData=new PageImpl<Task>(evts,pageable,evts.size());
		when(taskRepository.findAll(any(Pageable.class))).thenReturn(testData);

		TasksReadEvent evtData = service.readTasks(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages(),new Integer(1));
		assertEquals(evtData.getTotalItems(),new Long(evts.size()));
	}

	@Test
	public final void testReadTasksNoneAvailable()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTasks()");
		ArrayList<Task> evts=new ArrayList<Task>();
		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<Task> testData=new PageImpl<Task>(evts,pageable,evts.size());
		when(taskRepository.findAll(any(Pageable.class))).thenReturn(testData);
				
		TasksReadEvent evtData = service.readTasks(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages().intValue(),0);
		assertEquals(evtData.getTotalItems().longValue(),0);
	}

	@Test
	public final void testReadTasksNullReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTasks()");
		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		
		Page<Task> testData=null;
		when(taskRepository.findAll(any(Pageable.class))).thenReturn(testData);

		int pageLength=10;
		int pageNumber=0;
		TasksReadEvent evtData = service.readTasks(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertFalse(((AllReadEvent)evtData).isEntityFound());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskEventHandler#updateTask(com.eulersbridge.iEngage.core.events.task.UpdateTaskEvent)}.
	 */
	@Test
	public final void testUpdateTask()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingTask()");
		Task testData=DatabaseDataFixture.populateTask1();
		when(taskRepository.findOne(any(Long.class))).thenReturn(testData);
		when(taskRepository.save(any(Task.class))).thenReturn(testData);
		TaskDetails dets=testData.toTaskDetails();
		UpdateTaskEvent createElectionEvent=new UpdateTaskEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateTask(createElectionEvent);
		TaskDetails returnedDets = (TaskDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toTaskDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskEventHandler#updateTask(com.eulersbridge.iEngage.core.events.tasks.UpdateTaskEvent)}.
	 */
	@Test
	public final void testUpdateTaskNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingTask()");
		Task testData=DatabaseDataFixture.populateTask1();
		when(taskRepository.findOne(any(Long.class))).thenReturn(null);
		when(taskRepository.save(any(Task.class))).thenReturn(testData);
		TaskDetails dets=testData.toTaskDetails();
		UpdateTaskEvent createTaskEvent=new UpdateTaskEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateTask(createTaskEvent);
		assertNull(evtData.getDetails());
		assertEquals(evtData.getNodeId(),testData.getNodeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskEventHandler#deleteTask(com.eulersbridge.iEngage.core.events.task.DeleteTaskEvent)}.
	 */
	@Test
	public final void testDeleteTask()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingTask()");
		Task testData=DatabaseDataFixture.populateTask1();
		when(taskRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(taskRepository).delete((any(Long.class)));
		DeleteTaskEvent deleteTaskEvent=new DeleteTaskEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteTask(deleteTaskEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TaskEventHandler#deleteTask(com.eulersbridge.iEngage.core.events.tasks.DeleteTaskEvent)}.
	 */
	@Test
	public final void testDeleteTaskNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingTask()");
		Task testData=DatabaseDataFixture.populateTask1();
		when(taskRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(taskRepository).delete((any(Long.class)));
		DeleteTaskEvent deleteTaskEvent=new DeleteTaskEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteTask(deleteTaskEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}
}
