package com.eulersbridge.iEngage.core.events;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CreatedEventTest
{
    CreatedEvent createdEvent;
    Details dets;
	Long nodeId;

    @Before
    public void setUp() throws Exception
    {
    	nodeId=23l;
		dets=new Details(nodeId);
        createdEvent = new CreatedEvent(dets);
    }

    @Test
    public void testCreatedEvent() throws Exception
    {
    	createdEvent=new CreatedEvent();
        assertNotNull("createdEvent is null", createdEvent);
    }
    
    @Test
    public void testCreatedEventDetails() throws Exception
    {
        assertNotNull("createdEvent is null", createdEvent);
        assertEquals(createdEvent.getDetails(),dets);
    }
    
    @Test
    public void testGetDetails() throws Exception
    {
        assertEquals(createdEvent.getDetails(),dets);
    }
    @Test
    public void testSetDetails() throws Exception
    {
        assertEquals(createdEvent.getDetails(),dets);
        Details dets2=new Details(nodeId);
        createdEvent.setDetails(dets2);
        assertEquals(createdEvent.getDetails(),dets);
        assertEquals(createdEvent.getDetails(),dets2);
        dets2=new Details(nodeId+1);
        createdEvent.setDetails(dets2);
        assertNotEquals(createdEvent.getDetails(),dets);
        assertEquals(createdEvent.getDetails(),dets2);
    }
}