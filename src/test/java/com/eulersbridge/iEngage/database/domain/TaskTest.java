/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.task.TaskDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class TaskTest
{

	private Task task;
	private TaskDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		task=DatabaseDataFixture.populateTask1();
		dets=task.toTaskDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#Task()}.
	 */
	@Test
	public final void testTask()
	{
		Task taskTest=new Task();
		assertEquals("taskTest not of Task class",taskTest.getClass(),Task.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#Task(java.lang.Long, java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public final void testTaskLongStringInteger()
	{
		Task taskTest=new Task(dets.getNodeId(),dets.getAction(),dets.getDescription(),dets.getXpValue());
		assertEquals("taskTest not of Task class",taskTest.getClass(),Task.class);
		assertEquals(dets.getAction(),taskTest.getAction());
		assertEquals(dets.getNodeId(),taskTest.getNodeId());
		assertEquals(dets.getXpValue(),taskTest.getXpValue());
		assertEquals(dets.getDescription(),taskTest.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#fromTaskDetails(com.eulersbridge.iEngage.core.events.task.TaskDetails)}.
	 */
	@Test
	public final void testFromTaskDetails()
	{
		Task evt2=Task.fromTaskDetails(dets);
		assertEquals(dets.getAction(),evt2.getAction());
		assertEquals(dets.getNodeId(),evt2.getNodeId());
		assertEquals(dets.getXpValue(),evt2.getXpValue());
		assertEquals(dets.getDescription(),evt2.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#toTaskDetails()}.
	 */
	@Test
	public final void testToTaskDetails()
	{
		TaskDetails dets2=task.toTaskDetails();
		assertEquals(dets2.getAction(),task.getAction());
		assertEquals(dets2.getNodeId(),task.getNodeId());
		assertEquals(dets2.getXpValue(),task.getXpValue());
		assertEquals(dets2.getDescription(),task.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",task.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(dets.getNodeId(),task.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long id=34l;
		assertNotEquals(id,task.getNodeId());
		task.setNodeId(id);
		assertEquals(id, task.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#getAction()}.
	 */
	@Test
	public final void testGetAction()
	{
		assertEquals(dets.getAction(),task.getAction());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#setAction(java.lang.String)}.
	 */
	@Test
	public final void testSetAction()
	{
		String action="Another action";
		assertNotEquals(action,task.getAction());
		task.setAction(action);
		assertEquals(action, task.getAction());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#getDescription()}.
	 */
	@Test
	public final void testGetDescription()
	{
		assertEquals(dets.getDescription(),task.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		String action="Another description";
		assertNotEquals(action,task.getDescription());
		task.setDescription(action);
		assertEquals(action, task.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#getXpValue()}.
	 */
	@Test
	public final void testGetXpValue()
	{
		assertEquals(dets.getXpValue(), task.getXpValue());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Task#setXpValue(java.lang.Integer)}.
	 */
	@Test
	public final void testSetXpValue()
	{
		Integer xps=34;
		assertNotEquals(xps,task.getXpValue());
		task.setXpValue(xps);
		assertEquals(xps, task.getXpValue());
	}

	private void checkHashCode(Task test1,Task test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(Task test1,Task test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Task taskTest=DatabaseDataFixture.populateTask1();
		assertEquals(taskTest.hashCode(),taskTest.hashCode());
		assertEquals(taskTest.hashCode(),task.hashCode());
		taskTest.setNodeId(null);
		checkHashCode(task,taskTest);
		task.setNodeId(null);
		taskTest.setAction(null);
		checkHashCode(task,taskTest);
		taskTest.setAction(task.getAction());
		taskTest.setXpValue(null);
		checkHashCode(task,taskTest);
		taskTest.setXpValue(task.getXpValue());
		taskTest.setDescription(null);
		checkHashCode(task,taskTest);
		taskTest.setDescription(task.getDescription());
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals()
	{
		Task taskTest=null;
		assertNotEquals(taskTest,task);
		assertNotEquals(task,taskTest);
		String notElection="";
		assertNotEquals(task,notElection);
		taskTest=DatabaseDataFixture.populateTask1();
		assertEquals(taskTest,taskTest);
		assertEquals(taskTest,task);
		taskTest.setNodeId(54l);
		checkNotEquals(task,taskTest);
		task.setNodeId(null);
		checkNotEquals(task,taskTest);
		taskTest.setNodeId(null);
		assertEquals(task, taskTest);
		assertEquals(taskTest, task);
		taskTest.setXpValue(4321);
		assertNotEquals(task, taskTest);
		taskTest.setXpValue(null);
		checkNotEquals(task, taskTest);
		taskTest.setXpValue(task.getXpValue());
		taskTest.setDescription("Some description");
		assertNotEquals(task, taskTest);
		taskTest.setDescription(null);
		checkNotEquals(taskTest, task);
		taskTest.setDescription(task.getDescription());
		
		taskTest.setAction("some action");
		assertNotEquals(task, taskTest);
		taskTest.setAction(null);
		checkNotEquals(task, taskTest);
		taskTest.setAction(task.getAction());
	}

}
