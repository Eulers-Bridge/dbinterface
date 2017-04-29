/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.task.TaskCompleteDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class TaskCompleteTest
{
	private TaskComplete taskComplete;
	private TaskCompleteDetails dets;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		taskComplete=DatabaseDataFixture.populateTaskComplete1();
		dets=taskComplete.toTaskCompleteDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#TaskComplete()}.
	 */
	@Test
	public final void testTaskComplete()
	{
		TaskComplete taskTest=new TaskComplete();
		assertEquals("taskTest not of Task class",taskTest.getClass(),TaskComplete.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#toTaskCompleteDetails()}.
	 */
	@Test
	public final void testToTaskCompleteDetails()
	{
		TaskCompleteDetails dets2=taskComplete.toTaskCompleteDetails();
		assertEquals(dets2.getDate(),taskComplete.getDate());
		assertEquals(dets2.getNodeId(),taskComplete.getNodeId());
		assertEquals(dets2.getUserId(),taskComplete.getUser().getNodeId());
		assertEquals(dets2.getTaskId(),taskComplete.getTask().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#fromTaskCompleteDetails(com.eulersbridge.iEngage.core.events.task.TaskCompleteDetails)}.
	 */
	@Test
	public final void testFromTaskCompleteDetails()
	{
		TaskComplete evt2=TaskComplete.fromTaskCompleteDetails(dets);
		assertEquals(dets.getDate(),evt2.getDate());
		assertEquals(dets.getNodeId(),evt2.getNodeId());
		assertEquals(dets.getUserId(),evt2.getUser().getNodeId());
		assertEquals(dets.getTaskId(),evt2.getTask().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(dets.getNodeId(),taskComplete.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long id=34l;
		assertNotEquals(id,taskComplete.getNodeId());
		taskComplete.setNodeId(id);
		assertEquals(id, taskComplete.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#getDate()}.
	 */
	@Test
	public final void testGetDate()
	{
		assertEquals(dets.getDate(),taskComplete.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#setDate(java.lang.Long)}.
	 */
	@Test
	public final void testSetDate()
	{
		Long date=taskComplete.getDate()+54;
		assertNotEquals(date,taskComplete.getDate());
		taskComplete.setDate(date);
		assertEquals(date, taskComplete.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#getUser()}.
	 */
	@Test
	public final void testGetUser()
	{
		assertEquals(dets.getUserId(),taskComplete.getUser().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#setUser(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testSetUser()
	{
		User user=DatabaseDataFixture.populateUserGnewitt2();
		assertNotEquals(user,taskComplete.getUser());
		taskComplete.setUser(user);
		assertEquals(user, taskComplete.getUser());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#getTask()}.
	 */
	@Test
	public final void testGetTask()
	{
		assertEquals(dets.getTaskId(),taskComplete.getTask().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#setTask(com.eulersbridge.iEngage.database.domain.Task)}.
	 */
	@Test
	public final void testSetTask()
	{
		Task task=DatabaseDataFixture.populateTask2();
		assertNotEquals(task,taskComplete.getTask());
		taskComplete.setTask(task);
		assertEquals(task, taskComplete.getTask());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",taskComplete.toString());
	}

	private void checkHashCode(TaskComplete test1,TaskComplete test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		TaskComplete taskTest=DatabaseDataFixture.populateTaskComplete1();
		assertEquals(taskTest.hashCode(),taskTest.hashCode());
		assertEquals(taskTest.hashCode(),taskComplete.hashCode());
		taskTest.setNodeId(null);
		checkHashCode(taskComplete,taskTest);
		taskComplete.setNodeId(null);
		taskTest.setTask(null);
		checkHashCode(taskComplete,taskTest);
		taskTest.setTask(taskComplete.getTask());
		taskTest.setUser(null);;
		checkHashCode(taskComplete,taskTest);
		taskTest.setUser(taskComplete.getUser());
		taskTest.setDate(null);;
		checkHashCode(taskComplete,taskTest);
		taskTest.setDate(taskComplete.getDate());
	}

	private void checkNotEquals(TaskComplete test1,TaskComplete test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.TaskComplete#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		TaskComplete taskTest=null;
		assertNotEquals(taskTest,taskComplete);
		assertNotEquals(taskComplete,taskTest);
		String notElection="";
		assertNotEquals(taskComplete,notElection);
		taskTest=DatabaseDataFixture.populateTaskComplete1();
		assertEquals(taskTest,taskTest);
		assertEquals(taskTest,taskComplete);
		taskTest.setNodeId(54l);
		checkNotEquals(taskComplete,taskTest);
		taskComplete.setNodeId(null);
		checkNotEquals(taskComplete,taskTest);
		taskTest.setNodeId(null);
		assertEquals(taskComplete, taskTest);
		assertEquals(taskTest, taskComplete);
		taskTest.setTask(new Task());
		assertNotEquals(taskComplete, taskTest);
		taskTest.setTask(null);
		checkNotEquals(taskComplete, taskTest);
		taskTest.setTask(taskComplete.getTask());
		taskTest.setUser(new User());
		assertNotEquals(taskComplete, taskTest);
		taskTest.setUser(null);
		checkNotEquals(taskTest, taskComplete);
		taskTest.setUser(taskComplete.getUser());
		
		taskTest.setDate(taskComplete.getDate()+54);
		assertNotEquals(taskComplete, taskTest);
		taskTest.setDate(null);
		checkNotEquals(taskComplete, taskTest);
		taskTest.setDate(taskComplete.getDate());
	}

}
