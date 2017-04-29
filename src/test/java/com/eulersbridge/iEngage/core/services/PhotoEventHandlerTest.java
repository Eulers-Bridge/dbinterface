/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.photo.*;
import com.eulersbridge.iEngage.core.events.photoAlbums.*;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.PhotoAlbumRepository;
import com.eulersbridge.iEngage.database.repository.PhotoRepository;
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

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
    @Mock
    OwnerRepository ownerRepository;

    PhotoEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new PhotoEventHandler(photoRepository,photoAlbumRepository,ownerRepository);
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
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#createPhoto(com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent)}.
	 */
	@Test
	public final void testCreatePhoto()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		Owner testOwner=new Owner();
		testOwner.setNodeId(testData.getOwner().getNodeId());
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
		when(photoRepository.save(any(Photo.class))).thenReturn(testData);
		PhotoDetails dets=testData.toPhotoDetails();
		CreatePhotoEvent createPhotoEvent=new CreatePhotoEvent(dets);
		PhotoCreatedEvent evtData = service.createPhoto(createPhotoEvent);
		Details returnedDets = evtData.getDetails();
		assertEquals(testData.toPhotoDetails(),returnedDets);
		assertEquals(testData.getNodeId(),returnedDets.getNodeId());
		assertNotNull(evtData.getNodeId());
	}
	@Test
	public final void testCreatePhotoNullOwnerId()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		Owner testOwner=new Owner(null);
		testData.setOwner(testOwner);
		PhotoDetails dets=testData.toPhotoDetails();
		CreatePhotoEvent createPhotoEvent=new CreatePhotoEvent(dets);
		PhotoCreatedEvent evtData = service.createPhoto(createPhotoEvent);
		Details returnedDets = evtData.getDetails();
		assertNull(returnedDets);
		assertFalse(evtData.isOwnerFound());
		assertEquals(evtData.getNodeId(),testData.getOwner().getNodeId());
	}

	@Test
	public final void testCreatePhotoOwnerNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingPhoto()");
		Photo testData=DatabaseDataFixture.populatePhoto1();
		Owner testOwner=null;
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
		PhotoDetails dets=testData.toPhotoDetails();
		CreatePhotoEvent createPhotoEvent=new CreatePhotoEvent(dets);
		PhotoCreatedEvent evtData = service.createPhoto(createPhotoEvent);
		Details returnedDets = evtData.getDetails();
		assertNull(returnedDets);
		assertFalse(evtData.isOwnerFound());
		assertEquals(evtData.getNodeId(),testData.getOwner().getNodeId());
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#createPhotoAlbum(com.eulersbridge.iEngage.core.events.photoAlbums.CreatePhotoAlbumEvent)}.
	 */
	@Test
	public final void testCreatePhotoAlbum()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingPhotoAlbum()");
		PhotoAlbum testData=DatabaseDataFixture.populatePhotoAlbum1();
		Owner testOwner=new Owner();
		testOwner.setNodeId(testData.getOwner().getNodeId());
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
		when(photoAlbumRepository.save(any(PhotoAlbum.class))).thenReturn(testData);
		PhotoAlbumDetails dets=testData.toPhotoAlbumDetails();
		CreatePhotoAlbumEvent createPhotoAlbumEvent=new CreatePhotoAlbumEvent(dets);
		PhotoAlbumCreatedEvent evtData = service.createPhotoAlbum(createPhotoAlbumEvent);
		Details returnedDets = evtData.getDetails();
		assertEquals(testData.toPhotoAlbumDetails(),returnedDets);
		assertEquals(testData.getNodeId(),returnedDets.getNodeId());
		assertNotNull(returnedDets.getNodeId());
	}
	@Test
	public final void testCreatePhotoAlbumOwnerNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingPhotoAlbum()");
		PhotoAlbum testData=DatabaseDataFixture.populatePhotoAlbum1();
		Owner testOwner=null;
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
		PhotoAlbumDetails dets=testData.toPhotoAlbumDetails();
		CreatePhotoAlbumEvent createPhotoAlbumEvent=new CreatePhotoAlbumEvent(dets);
		PhotoAlbumCreatedEvent evtData = service.createPhotoAlbum(createPhotoAlbumEvent);
		Details returnedDets = evtData.getDetails();
		assertNull(returnedDets);
		assertFalse(evtData.isOwnerFound());
		assertEquals(evtData.getOwnerId(),testData.getOwner().getNodeId());
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
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#readPhotoAlbum(com.eulersbridge.iEngage.core.events.photoAlbums.ReadPhotoAlbumEvent)}.
	 */
	@Test
	public final void testReadPhotoAlbum()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPhotoAlbum()");
		PhotoAlbum testData=DatabaseDataFixture.populatePhotoAlbum1();
		when(photoAlbumRepository.findOne(any(Long.class))).thenReturn(testData);
		ReadPhotoAlbumEvent readPhotoAlbumEvent=new ReadPhotoAlbumEvent(testData.getNodeId());
		PhotoAlbumReadEvent evtData = (PhotoAlbumReadEvent) service.readPhotoAlbum(readPhotoAlbumEvent);
		PhotoAlbumDetails returnedDets = (PhotoAlbumDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toPhotoAlbumDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#readPhotoAlbum(com.eulersbridge.iEngage.core.events.photoAlbums.ReadPhotoAlbumEvent)}.
	 */
	@Test
	public final void testReadPhotoAlbumNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPhotoAlbum()");
		PhotoAlbum testData=null;
		Long nodeId=1l;
		when(photoAlbumRepository.findOne(any(Long.class))).thenReturn(testData);
		ReadPhotoAlbumEvent readPhotoAlbumEvent=new ReadPhotoAlbumEvent(nodeId);
		ReadEvent evtData = service.readPhotoAlbum(readPhotoAlbumEvent);
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
		UpdatedEvent evtData = service.updatePhoto(createPhotoEvent);
		PhotoDetails returnedDets = (PhotoDetails) evtData.getDetails();
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
		UpdatedEvent evtData = service.updatePhoto(updatePhotoEvent);
		assertNull(evtData.getDetails());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#updatePhotoAlbum(com.eulersbridge.iEngage.core.events.photoAlbums.UpdatePhotoAlbumEvent)}.
	 */
	@Test
	public final void testUpdatePhotoAlbum()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPhotoAlbum()");
		PhotoAlbum testData=DatabaseDataFixture.populatePhotoAlbum1();
		when(photoAlbumRepository.findOne(any(Long.class))).thenReturn(testData);
		when(photoAlbumRepository.save(any(PhotoAlbum.class))).thenReturn(testData);
		PhotoAlbumDetails dets=testData.toPhotoAlbumDetails();
		UpdatePhotoAlbumEvent createPhotoAlbumEvent=new UpdatePhotoAlbumEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePhotoAlbum(createPhotoAlbumEvent);
		PhotoAlbumDetails returnedDets = (PhotoAlbumDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toPhotoAlbumDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#updatePhotoAlbum(com.eulersbridge.iEngage.core.events.photoAlbums.UpdatePhotoAlbumEvent)}.
	 */
	@Test
	public final void testUpdatePhotoAlbumNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPhotoAlbum()");
		PhotoAlbum testData=DatabaseDataFixture.populatePhotoAlbum1();
		when(photoAlbumRepository.findOne(any(Long.class))).thenReturn(null);
		when(photoAlbumRepository.save(any(PhotoAlbum.class))).thenReturn(testData);
		PhotoAlbumDetails dets=testData.toPhotoAlbumDetails();
		UpdatePhotoAlbumEvent updatePhotoAlbumEvent=new UpdatePhotoAlbumEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePhotoAlbum(updatePhotoAlbumEvent);
		assertNull(evtData.getDetails());
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
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#deletePhotoAlbum(com.eulersbridge.iEngage.core.events.photoAlbums.DeletePhotoAlbumEvent)}.
	 */
	@Test
	public final void testDeleteAlbumPhoto()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingPhotoAlbum()");
		PhotoAlbum testData=DatabaseDataFixture.populatePhotoAlbum1();
		when(photoAlbumRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(photoAlbumRepository).delete((any(Long.class)));
		DeletePhotoAlbumEvent deletePhotoAlbumEvent=new DeletePhotoAlbumEvent(testData.getNodeId());
		DeletedEvent evtData = service.deletePhotoAlbum(deletePhotoAlbumEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#deletePhotoAlbum(com.eulersbridge.iEngage.core.events.photoAlbums.DeletePhotoAlbumEvent)}.
	 */
	@Test
	public final void testDeletePhotoAlbumNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingPhotoAlbum()");
		PhotoAlbum testData=DatabaseDataFixture.populatePhotoAlbum1();
		when(photoAlbumRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(photoAlbumRepository).delete((any(Long.class)));
		DeletePhotoAlbumEvent deletePhotoAlbumEvent=new DeletePhotoAlbumEvent(testData.getNodeId());
		DeletedEvent evtData = service.deletePhotoAlbum(deletePhotoAlbumEvent);
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#deletePhotos(com.eulersbridge.iEngage.core.events.photo.deletePhotosEvent)}.
	 */
	@Test
	public final void testDeletePhotos()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingPhotos()");
		
		ArrayList<Photo> evts=new ArrayList<Photo>();
		evts.add(DatabaseDataFixture.populatePhoto1());
		evts.add(DatabaseDataFixture.populatePhoto2());

		
		Long ownerId=1l;
		ReadPhotosEvent evt=new ReadPhotosEvent(ownerId);
		
		when(photoRepository.deletePhotosByOwnerId(any(Long.class))).thenReturn(ownerId);

		PhotosReadEvent evtData = service.deletePhotos(evt);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages().intValue(),ownerId.intValue());
		assertEquals(evtData.getTotalPhotos(),ownerId);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isOwnerFound());
		assertTrue(evtData.isPhotosFound());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PhotoEventHandler#findPhotoAlbums(com.eulersbridge.iEngage.core.events.photo.findPhotoAlbumsEvent)}.
	 */
	@Test
	public final void testFindPhotoAlbums()
	{
		if (LOG.isDebugEnabled()) LOG.debug("FindingPhotoAlbums()");
		
		ArrayList<PhotoAlbum> evts=new ArrayList<PhotoAlbum>();
		evts.add(DatabaseDataFixture.populatePhotoAlbum1());
		evts.add(DatabaseDataFixture.populatePhotoAlbum2());

		
		Long ownerId=1l;
		ReadAllEvent evt=new ReadAllEvent(ownerId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.created");
		Page<PhotoAlbum> testData=new PageImpl<PhotoAlbum>(evts,pageable,evts.size());
		when(photoAlbumRepository.findByOwnerId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		AllReadEvent evtData = service.findPhotoAlbums(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages(),new Integer(1));
		assertEquals(evtData.getTotalItems(),new Long(evts.size()));
		assertTrue(evtData.isEntityFound());
	}

	@Test
	public final void testFindPhotoAlbumsNoneReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("FindingPhotoAlbums()");
		
		ArrayList<PhotoAlbum> evts=new ArrayList<PhotoAlbum>();

		
		Long ownerId=1l;
		ReadAllEvent evt=new ReadAllEvent(ownerId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<PhotoAlbum> testData=new PageImpl<PhotoAlbum>(evts,pageable,evts.size());
		when(photoAlbumRepository.findByOwnerId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		Owner inst=new Owner(DatabaseDataFixture.populatePhotoAlbum1().getNodeId());
		when(ownerRepository.findOne(any(Long.class))).thenReturn(inst);

		AllReadEvent evtData = service.findPhotoAlbums(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages().intValue(),0);
		assertEquals(evtData.getTotalItems().longValue(),evts.size());
		assertTrue(evtData.isEntityFound());
	}

	@Test
	public final void testFindPhotoAlbumsNullReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("FindingPhotoAlbums()");
		
		Long ownerId=1l;
		ReadAllEvent evt=new ReadAllEvent(ownerId);
		int pageLength=10;
		int pageNumber=0;
		
		Page<PhotoAlbum> testData=null;
		when(photoAlbumRepository.findByOwnerId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		AllReadEvent evtData = service.findPhotoAlbums(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertNull(evtData.getTotalPages());
		assertNull(evtData.getTotalItems());
		assertFalse(evtData.isEntityFound());
	}

	@Test
	public final void testFindPhotoAlbumsInstNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("FindingPhotoAlbums()");
		
		ArrayList<PhotoAlbum> evts=new ArrayList<PhotoAlbum>();

		
		Long ownerId=1l;
		ReadAllEvent evt=new ReadAllEvent(ownerId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<PhotoAlbum> testData=new PageImpl<PhotoAlbum>(evts,pageable,evts.size());
		when(photoAlbumRepository.findByOwnerId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		when(ownerRepository.findOne(any(Long.class))).thenReturn(null);

		AllReadEvent evtData = service.findPhotoAlbums(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertNull(evtData.getTotalPages());
		assertNull(evtData.getTotalItems());
		assertFalse(evtData.isEntityFound());
	}


}
