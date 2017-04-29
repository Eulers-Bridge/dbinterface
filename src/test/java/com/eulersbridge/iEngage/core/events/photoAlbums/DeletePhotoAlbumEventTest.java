package com.eulersbridge.iEngage.core.events.photoAlbums;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeletePhotoAlbumEventTest
{
	DeletePhotoAlbumEvent evt;
	Long nodeId;

	@Before
	public void setUp() throws Exception
	{
		nodeId=23l;
		evt=new DeletePhotoAlbumEvent(nodeId);
	}

	@Test
	public final void testDeletePhotoAlbumEvent()
	{
		assertNotNull("Not yet implemented",evt);
	}

	@Test
	public final void testGetEventId()
	{
		assertEquals(evt.getNodeId(),nodeId);
		nodeId++;
		assertNotEquals(evt.getNodeId(),nodeId);
	}

}
