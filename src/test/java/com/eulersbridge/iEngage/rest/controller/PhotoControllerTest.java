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
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent;
import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.core.services.PhotoService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class PhotoControllerTest
{

    private static Logger LOG = LoggerFactory.getLogger(PhotoControllerTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.PHOTO_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	PhotoController controller;
	
	@Mock
	PhotoService photoService;
	@Mock
	InstitutionService instService;
	
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#PhotoController()}.
	 */
	@Test
	public final void testPhotoController()
	{
		PhotoController photController=new PhotoController();
		assertNotNull("Not yet implemented",photController);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#findPhoto(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindPhoto() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPhoto()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		PhotoReadEvent testData=new PhotoReadEvent(dets.getNodeId(), dets);
		when (photoService.readPhoto(any(ReadPhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{photoId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.url",is(dets.getUrl())))
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.date",is(dets.getDate())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(status().isOk())	;
	}


	@Test
	public final void testFindPhotoNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindPhoto()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		PhotoReadEvent testData=PhotoReadEvent.notFound(dets.getNodeId());
		when (photoService.readPhoto(any(ReadPhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{photoId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#createPhoto(com.eulersbridge.iEngage.rest.domain.Photo)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreatePhoto() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		PhotoCreatedEvent testData=new PhotoCreatedEvent(dets.getNodeId(), dets);
		String content="{\"url\":\"http://localhost:8080/\",\"title\":\"Test Photo\",\"description\":\"description\",\"date\":123456,\"ownerId\":3214}";
		String returnedContent="{\"nodeId\":"+dets.getNodeId().intValue()+",\"url\":\""+dets.getUrl()+"\",\"title\":\""+dets.getTitle()+
								"\",\"description\":\""+dets.getDescription()+"\",\"date\":"+dets.getDate()+",\"ownerId\":"+dets.getOwnerId()+
								",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/photo/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/photo/1/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/photo/1/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/photos\"}]}";
		when (photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.url",is(dets.getUrl())))
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.date",is(dets.getDate())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreatePhotoInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		PhotoCreatedEvent testData=new PhotoCreatedEvent(dets.getNodeId(), dets);
		String content="{\"url1\":\"http://localhost:8080/\",\"title\":\"Test Photo\",\"description\":\"description\",\"date\":123456,\"ownerId\":3214}";
		when (photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePhotoNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		PhotoCreatedEvent testData=new PhotoCreatedEvent(dets.getNodeId(), dets);
		when (photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreatePhotoNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		PhotoCreatedEvent testData=PhotoCreatedEvent.ownerNotFound(dets.getOwnerId());
		String content="{\"url\":\"http://localhost:8080/\",\"title\":\"Test Photo\",\"description\":\"description\",\"date\":123456,\"ownerId\":3214}";
		when (photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testCreatePhotoNullIdReturned() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		PhotoCreatedEvent testData=new PhotoCreatedEvent(null, dets);
		String content="{\"url\":\"http://localhost:8080/\",\"title\":\"Test Photo\",\"description\":\"description\",\"date\":123456}";
		when (photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(status().isBadRequest())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#deletePhoto(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDeletePhoto() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhoto()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		PhotoDeletedEvent testData=new PhotoDeletedEvent(dets.getNodeId());
		when (photoService.deletePhoto(any(DeletePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{photoId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testDeletePhotoNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhoto()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		DeletedEvent testData=PhotoDeletedEvent.notFound(dets.getNodeId());
		when (photoService.deletePhoto(any(DeletePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{photoId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeletePhotoForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhoto()");
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		DeletedEvent testData=PhotoDeletedEvent.deletionForbidden(dets.getNodeId());
		when (photoService.deletePhoto(any(DeletePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{photoId}/",dets.getNodeId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#updatePhoto(java.lang.Long, com.eulersbridge.iEngage.rest.domain.Photo)}.
	 * @throws Exception 
	 */
	@Test
	public final void testUpdatePhoto() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePhoto()");
		Long id=1L;
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		dets.setTitle("Test Photo2");
		PhotoUpdatedEvent testData=new PhotoUpdatedEvent(id, dets);
		String content="{\"nodeId\":1234,\"url\":\"http://localhost:8080/\",\"title\":\"Test Photo\",\"description\":\"description\",\"date\":123456,\"ownerId\":3214}";
		String returnedContent="{\"nodeId\":"+dets.getNodeId().intValue()+",\"url\":\""+dets.getUrl()+"\",\"title\":\""+dets.getTitle()+
								"\",\"description\":\""+dets.getDescription()+"\",\"date\":"+dets.getDate()+",\"ownerId\":"+dets.getOwnerId()+
								",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/photo/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/photo/1/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/photo/1/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/photos\"}]}";
		when (photoService.updatePhoto(any(UpdatePhotoEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.nodeId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.url",is(dets.getUrl())))
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.description",is(dets.getDescription())))
		.andExpect(jsonPath("$.date",is(dets.getDate())))
		.andExpect(jsonPath("$.ownerId",is(dets.getOwnerId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#findPhotos(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testFindPhotos()
	{
		fail("Not yet implemented"); // TODO
	}

}
