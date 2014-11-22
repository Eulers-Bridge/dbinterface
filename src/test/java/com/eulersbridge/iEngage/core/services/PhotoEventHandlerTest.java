/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotosReadEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotosEvent;
import com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.PhotoAlbumRepository;
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
    @Mock
	PhotoAlbumRepository photoAlbumRepository;

    PhotoEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new PhotoEventHandler(photoRepository,photoAlbumRepository);
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
		Photo testPhoto=new Photo();
		testPhoto.setNodeId(testData.getOwner().getNodeId());
		when(photoRepository.findOne(any(Long.class))).thenReturn(testPhoto);
		when(photoRepository.save(any(Photo.class))).thenReturn(testData);
		PhotoDetails dets=testData.toPhotoDetails();
		CreatePhotoEvent createPhotoEvent=new CreatePhotoEvent(dets);
		PhotoCreatedEvent evtData = service.createPhoto(createPhotoEvent);
		Details returnedDets = evtData.getDetails();
		assertEquals(testData.toPhotoDetails(),returnedDets);
		assertEquals(testData.getNodeId(),returnedDets.getNodeId());
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
		PhotoReadEvent evtData = (PhotoReadEvent) service.readPhoto(readPhotoEvent);
		PhotoDetails returnedDets = (PhotoDetails) evtData.getDetails();
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
		ReadEvent evtData = service.readPhoto(readPhotoEvent);
		assertNull(evtData.getDetails());
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
		DeletedEvent evtData = service.deletePhoto(deletePhotoEvent);
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
		DeletedEvent evtData = service.deletePhoto(deletePhotoEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#findPhotos(com.eulersbridge.iEngage.core.events.photo.findPhotosEvent)}.
	 */
	@Test
	public final void testFindPhotos()
	{
		if (LOG.isDebugEnabled()) LOG.debug("FindingPhotos()");
		
		ArrayList<Photo> evts=new ArrayList<Photo>();
		evts.add(DatabaseDataFixture.populatePhoto1());
		evts.add(DatabaseDataFixture.populatePhoto2());

		
		Long ownerId=1l;
		ReadPhotosEvent evt=new ReadPhotosEvent(ownerId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<Photo> testData=new PageImpl<Photo>(evts,pageable,evts.size());
		when(photoRepository.findByOwnerId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		PhotosReadEvent evtData = service.findPhotos(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages(),new Integer(1));
		assertEquals(evtData.getTotalPhotos(),new Long(evts.size()));
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isOwnerFound());
		assertTrue(evtData.isPhotosFound());
	}
	
	@Test
	public final void testFindPhotosNoneReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("FindingPhotos()");
		
		ArrayList<Photo> evts=new ArrayList<Photo>();

		
		Long ownerId=1l;
		ReadPhotosEvent evt=new ReadPhotosEvent(ownerId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<Photo> testData=new PageImpl<Photo>(evts,pageable,evts.size());
		when(photoRepository.findByOwnerId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		Photo inst=DatabaseDataFixture.populatePhoto1();
		when(photoRepository.findOne(any(Long.class))).thenReturn(inst);

		PhotosReadEvent evtData = service.findPhotos(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages().intValue(),0);
		assertEquals(evtData.getTotalPhotos().longValue(),evts.size());
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isOwnerFound());
		assertTrue(evtData.isPhotosFound());
	}

	@Test
	public final void testFindPhotosNullReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("FindingPhotos()");
		
		Long ownerId=1l;
		ReadPhotosEvent evt=new ReadPhotosEvent(ownerId);
		int pageLength=10;
		int pageNumber=0;
		
		Page<Photo> testData=null;
		when(photoRepository.findByOwnerId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		PhotosReadEvent evtData = service.findPhotos(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertNull(evtData.getTotalPages());
		assertNull(evtData.getTotalPhotos());
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isOwnerFound());
		assertFalse(evtData.isPhotosFound());
	}

	@Test
	public final void testFindPhotosInstNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("FindingPhotos()");
		
		ArrayList<Photo> evts=new ArrayList<Photo>();

		
		Long ownerId=1l;
		ReadPhotosEvent evt=new ReadPhotosEvent(ownerId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<Photo> testData=new PageImpl<Photo>(evts,pageable,evts.size());
		when(photoRepository.findByOwnerId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		when(photoRepository.findOne(any(Long.class))).thenReturn(null);

		PhotosReadEvent evtData = service.findPhotos(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertNull(evtData.getTotalPages());
		assertNull(evtData.getTotalPhotos());
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isOwnerFound());
		assertFalse(evtData.isPhotosFound());
	}


}
