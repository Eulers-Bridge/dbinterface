/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.PhotoRepository;

/**
 * @author Greg Newitt
 *
 */
public class PhotoEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(PhotoEventHandlerTest.class);

    @Mock
	PhotoRepository photoRepository;

    PhotoEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new PhotoEventHandler(photoRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#PhotoEventHandler(com.eulersbridge.iEngage.database.repository.PhotoRepository)}.
	 */
	@Test
	public final void testPhotoEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#createNewsArticle(com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent)}.
	 */
	@Test
	public final void testCreatePhoto()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		when(photoRepository.save(any(Photo.class))).thenReturn(testData);
		PhotoDetails dets=testData.toPhotoDetails();
		CreatePhotoEvent createPhotoEvent=new CreatePhotoEvent(dets);
		PhotoCreatedEvent evtData = service.createPhoto(createPhotoEvent);
		PhotoDetails returnedDets = evtData.getPhotoDetails();
		assertEquals(returnedDets,testData.toPhotoDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#readPhoto(com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent)}.
	 */
	@Test
	public final void testReadPhoto()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		when(photoRepository.findOne(any(Long.class))).thenReturn(testData);
		ReadPhotoEvent readPhotoEvent=new ReadPhotoEvent(testData.getNodeId());
		PhotoReadEvent evtData = service.readPhoto(readPhotoEvent);
		PhotoDetails returnedDets = evtData.getPhotoDetails();
		assertEquals(returnedDets,testData.toPhotoDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#readPhoto(com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent)}.
	 */
	@Test
	public final void testReadPhotoNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPhoto()");
		Photo testData=null;
		Long nodeId=1l;
		when(photoRepository.findOne(any(Long.class))).thenReturn(testData);
		ReadPhotoEvent readPhotoEvent=new ReadPhotoEvent(nodeId);
		PhotoReadEvent evtData = service.readPhoto(readPhotoEvent);
		PhotoDetails returnedDets = evtData.getPhotoDetails();
		assertNull(returnedDets);
		assertEquals(nodeId,evtData.getNodeId());
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#updatePhoto(com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent)}.
	 */
	@Test
	public final void testUpdatePhoto()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		when(photoRepository.findOne(any(Long.class))).thenReturn(testData);
		when(photoRepository.save(any(Photo.class))).thenReturn(testData);
		PhotoDetails dets=testData.toPhotoDetails();
		UpdatePhotoEvent createPhotoEvent=new UpdatePhotoEvent(dets.getNodeId(), dets);
		PhotoUpdatedEvent evtData = service.updatePhoto(createPhotoEvent);
		PhotoDetails returnedDets = evtData.getPhotoDetails();
		assertEquals(returnedDets,testData.toPhotoDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#updatePhoto(com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent)}.
	 */
	@Test
	public final void testUpdatePhotoNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		when(photoRepository.findOne(any(Long.class))).thenReturn(null);
		when(photoRepository.save(any(Photo.class))).thenReturn(testData);
		PhotoDetails dets=testData.toPhotoDetails();
		UpdatePhotoEvent updatePhotoEvent=new UpdatePhotoEvent(dets.getNodeId(), dets);
		PhotoUpdatedEvent evtData = service.updatePhoto(updatePhotoEvent);
		assertNull(evtData.getPhotoDetails());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#deletePhoto(com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent)}.
	 */
	@Test
	public final void testDeletePhoto()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		when(photoRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(photoRepository).delete((any(Long.class)));
		DeletePhotoEvent deletePhotoEvent=new DeletePhotoEvent(testData.getNodeId());
		PhotoDeletedEvent evtData = service.deletePhoto(deletePhotoEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#deletePhoto(com.eulersbridge.iEngage.core.events.photos.DeletePhotoEvent)}.
	 */
	@Test
	public final void testDeletePhotoNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		when(photoRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(photoRepository).delete((any(Long.class)));
		DeletePhotoEvent deletePhotoEvent=new DeletePhotoEvent(testData.getNodeId());
		PhotoDeletedEvent evtData = service.deletePhoto(deletePhotoEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}
}
