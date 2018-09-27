///**
// *
// */
//package com.eulersbridge.iEngage.rest.controller;
//
//import com.eulersbridge.iEngage.core.events.*;
//import com.eulersbridge.iEngage.core.events.photo.*;
//import com.eulersbridge.iEngage.core.events.photoAlbums.*;
//import com.eulersbridge.iEngage.core.services.interfacePack.LikesService;
//import com.eulersbridge.iEngage.core.services.interfacePack.PhotoService;
//import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
//import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
//import com.eulersbridge.iEngage.database.domain.Photo;
//import com.eulersbridge.iEngage.database.domain.User;
//import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;
//import com.eulersbridge.iEngage.rest.domain.PhotoDomain;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
//
///**
// * @author Greg Newitt
// */
//public class PhotoDomainControllerTest {
//
//  private static Logger LOG = LoggerFactory.getLogger(PhotoDomainControllerTest.class);
//
//  private String urlPrefix = ControllerConstants.API_PREFIX + ControllerConstants.PHOTO_LABEL;
//  private String urlPrefix2 = ControllerConstants.API_PREFIX + ControllerConstants.PHOTO_ALBUM_LABEL;
//  private String urlPrefix1 = ControllerConstants.API_PREFIX + ControllerConstants.USER_LABEL + ControllerConstants.PHOTO_LABEL;
//
//  MockMvc mockMvc;
//
//  @InjectMocks
//  PhotoController controller;
//
//  @Mock
//  PhotoService photoService;
//  @Mock
//  UserService userService;
//  @Mock
//  LikesService likesService;
//
//  /**
//   * @throws java.lang.Exception
//   */
//  @Before
//  public void setUp() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("setup()");
//    MockitoAnnotations.initMocks(this);
//
//    MappingJackson2HttpMessageConverter converter = RestDataFixture.setUpConverter();
//    this.mockMvc = standaloneSetup(controller).setMessageConverters(converter).build();
//  }
//
//  static String setupContent(PhotoDetails dets) {
//    String urlPair = dets.getUrl() == null ? "\"url\":null" : "\"url\":\"" + dets.getUrl() + "\"";
//    String thumbNailUrlPair = dets.getThumbNailUrl() == null ? "\"thumbNailUrl\":null" : "\"thumbNailUrl\":\"" + dets.getThumbNailUrl() + "\"";
//    String titlePair = dets.getTitle() == null ? "\"title\":null" : "\"title\":\"" + dets.getTitle() + "\"";
//    String descriptionPair = dets.getDescription() == null ? "\"description\":null" : "\"description\":\"" + dets.getDescription() + "\"";
//
//
//    String content = "{\"numOfLikes\":" + dets.getNumOfLikes() + "," + urlPair + "," + thumbNailUrlPair + "," + titlePair + "," + descriptionPair + "," +
//      "\"sequence\":" + dets.getSequence() + ",\"date\":" + dets.getDate() + ",\"ownerId\":" + dets.getOwnerId() + ",\"inappropriateContent\":" + dets.isInappropriateContent() + "}";
//    if (LOG.isDebugEnabled()) LOG.debug(content);
//    return content;
//  }
//
//  String setupReturnedContent(PhotoDetails dets) {
//    String returnedContent = "{\"nodeId\":" + dets.getNodeId().intValue() + ",\"url\":\"" + dets.getUrl() + "\",\"thumbNailUrl\":\"" + dets.getThumbNailUrl() + "\",\"title\":\"" + dets.getTitle() +
//      "\",\"description\":\"" + dets.getDescription() + "\",\"date\":" + dets.getDate() + ",\"ownerId\":" + dets.getOwnerId() + ",\"sequence\":" + dets.getSequence() +
//      ",\"inappropriateContent\":" + dets.isInappropriateContent() + ",\"numOfLikes\":" + dets.getNumOfLikes() +
//      ",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/photo/" + dets.getNodeId().intValue() + "\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/photo/" + dets.getNodeId().intValue() + "/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/photo/" + dets.getNodeId().intValue() + "/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/photos\"}]}";
//    return returnedContent;
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#PhotoController()}.
//   */
//  @Test
//  public final void testPhotoController() {
//    PhotoController photController = new PhotoController();
//    assertNotNull("Not yet implemented", photController);
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#findPhoto(java.lang.Long)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testFindPhoto() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    PhotoReadEvent testData = new PhotoReadEvent(dets.getNodeId(), dets);
//    when(photoService.readPhoto(any(ReadPhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "/{photoId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("$.nodeId", is(dets.getNodeId().intValue())))
//      .andExpect(jsonPath("$.url", is(dets.getUrl())))
//      .andExpect(jsonPath("$.title", is(dets.getTitle())))
//      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//      .andExpect(jsonPath("$.date", is(dets.getDate())))
//      .andExpect(jsonPath("$.inappropriateContent", is(dets.isInappropriateContent())))
//      .andExpect(jsonPath("$.numOfLikes", is(dets.getNumOfLikes().intValue())))
//      .andExpect(jsonPath("$.links[0].rel", is("self")))
//      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
//      .andExpect(jsonPath("$.links[2].rel", is("Next")))
//      .andExpect(jsonPath("$.links[3].rel", is("Read all")))
//      .andExpect(status().isOk());
//  }
//
//
//  @Test
//  public final void testFindPhotoNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    ReadEvent testData = PhotoReadEvent.notFound(dets.getNodeId());
//    when(photoService.readPhoto(any(ReadPhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "/{photoId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#findPhotoAlbum(java.lang.Long)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testFindPhotoAlbum() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    PhotoAlbumReadEvent testData = new PhotoAlbumReadEvent(dets.getNodeId(), dets);
//    when(photoService.readPhotoAlbum(any(ReadPhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix2 + "/{photoAlbumId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(jsonPath("$.nodeId", is(dets.getNodeId().intValue())))
//      .andExpect(jsonPath("$.name", is(dets.getName())))
//      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//      .andExpect(jsonPath("$.location", is(dets.getLocation())))
//      .andExpect(jsonPath("$.created", is(dets.getCreated())))
//      .andExpect(jsonPath("$.ownerId", is(dets.getOwnerId().intValue())))
//      .andExpect(jsonPath("$.modified", is(dets.getModified())))
//      .andExpect(jsonPath("$.links[0].rel", is("self")))
//      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
//      .andExpect(jsonPath("$.links[2].rel", is("Next")))
//      .andExpect(jsonPath("$.links[3].rel", is("Read all")))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindPhotoAlbumNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    ReadEvent testData = PhotoAlbumReadEvent.notFound(dets.getNodeId());
//    when(photoService.readPhotoAlbum(any(ReadPhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix2 + "/{photoAlbumId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
//
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#createPhoto(PhotoDomain)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testCreatePhoto() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
//    Photo photo = DatabaseDataFixture.populatePhoto1();
//    PhotoDetails dets = photo.toPhotoDetails();
//    PhotoCreatedEvent testData = new PhotoCreatedEvent(dets.getNodeId(), dets);
//    String content = setupContent(dets);
//    String returnedContent = setupReturnedContent(dets);
//    when(photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andDo(print())
//      .andExpect(jsonPath("$.nodeId", is(dets.getNodeId().intValue())))
//      .andExpect(jsonPath("$.url", is(dets.getUrl())))
//      .andExpect(jsonPath("$.thumbNailUrl", is(dets.getThumbNailUrl())))
//      .andExpect(jsonPath("$.title", is(dets.getTitle())))
//      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//      .andExpect(jsonPath("$.date", is(dets.getDate())))
//      .andExpect(jsonPath("$.ownerId", is(dets.getOwnerId().intValue())))
//      .andExpect(jsonPath("$.sequence", is(dets.getSequence())))
//      .andExpect(jsonPath("$.inappropriateContent", is(dets.isInappropriateContent())))
//      .andExpect(jsonPath("$.numOfLikes", is(dets.getNumOfLikes().intValue())))
//      .andExpect(jsonPath("$.links[0].rel", is("self")))
//      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
//      .andExpect(jsonPath("$.links[2].rel", is("Next")))
//      .andExpect(jsonPath("$.links[3].rel", is("Read all")))
//      .andExpect(content().string(returnedContent))
//      .andExpect(status().isCreated());
//  }
//
//  @Test
//  public final void testCreatePhotoInvalidContent() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    PhotoCreatedEvent testData = new PhotoCreatedEvent(dets.getNodeId(), dets);
//    String content = setupContent(dets).replace("\"url\"", "\"url1\"");
//    when(photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public final void testCreatePhotoEmptyContent() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    PhotoCreatedEvent testData = PhotoCreatedEvent.ownerNotFound(dets.getOwnerId());
//    when(photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
//    String content = "{}";
//    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isNotFound());
//  }
//
//  @Test
//  public final void testCreatePhotoNoContent() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    PhotoCreatedEvent testData = new PhotoCreatedEvent(dets.getNodeId(), dets);
//    when(photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public final void testCreatePhotoNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    PhotoCreatedEvent testData = PhotoCreatedEvent.ownerNotFound(dets.getOwnerId());
//    String content = setupContent(dets);
//    when(photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isNotFound());
//  }
//
//  @Test
//  public final void testCreatePhotoNullIdReturned() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    PhotoCreatedEvent testData = new PhotoCreatedEvent(null, dets);
//    String content = setupContent(dets);
//    when(photoService.createPhoto(any(CreatePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isBadRequest());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#createPhotoAlbum(com.eulersbridge.iEngage.rest.domain.PhotoAlbum)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testCreatePhotoAlbum() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    PhotoAlbumCreatedEvent testData = new PhotoAlbumCreatedEvent(dets);
//    String content = "{\"name\":\"testName\",\"description\":\"description\",\"location\":\"location\",\"thumbNailUrl\":\"thumbNailUrl\",\"created\":123456,\"ownerId\":3214,\"modified\":null}";
//    if (LOG.isDebugEnabled()) LOG.debug("content = " + content);
//    String returnedContent = "{\"nodeId\":" + dets.getNodeId().intValue() + ",\"name\":\"" + dets.getName() + "\",\"description\":\"" + dets.getDescription() +
//      "\",\"location\":\"" + dets.getLocation() + "\",\"thumbNailUrl\":\"" + dets.getThumbNailUrl() + "\",\"created\":" + dets.getCreated() + ",\"creatorId\":" + dets.getCreatorId() + ",\"ownerId\":" + dets.getOwnerId() + ",\"modified\":" + dets.getModified() +
//      ",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/photoAlbum/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/photoAlbum/1/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/photoAlbum/1/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/photoAlbums\"}]}";
//    if (LOG.isDebugEnabled()) LOG.debug("returnedContent = " + returnedContent);
//    when(photoService.createPhotoAlbum(any(CreatePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix2 + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(jsonPath("$.nodeId", is(dets.getNodeId().intValue())))
//      .andExpect(jsonPath("$.name", is(dets.getName())))
//      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//      .andExpect(jsonPath("$.location", is(dets.getLocation())))
//      .andExpect(jsonPath("$.thumbNailUrl", is(dets.getThumbNailUrl())))
//      .andExpect(jsonPath("$.created", is(dets.getCreated())))
//      .andExpect(jsonPath("$.ownerId", is(dets.getOwnerId().intValue())))
//      .andExpect(jsonPath("$.modified", is(dets.getModified())))
//      .andExpect(jsonPath("$.links[0].rel", is("self")))
//      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
//      .andExpect(jsonPath("$.links[2].rel", is("Next")))
//      .andExpect(jsonPath("$.links[3].rel", is("Read all")))
//      .andExpect(content().string(returnedContent))
//      .andExpect(status().isCreated());
//  }
//
//  @Test
//  public final void testCreatePhotoAlbumInvalidContent() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    PhotoAlbumCreatedEvent testData = new PhotoAlbumCreatedEvent(dets);
//    String content = "{\"name1\":\"testName\",\"description\":\"description\",\"location\":\"location\",\"created\":123456,\"ownerId\":3214,\"modified\":null}";
//    when(photoService.createPhotoAlbum(any(CreatePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix2 + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public final void testCreatePhotoAlbumNoContent() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    PhotoAlbumCreatedEvent testData = new PhotoAlbumCreatedEvent(dets);
//    when(photoService.createPhotoAlbum(any(CreatePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix2 + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public final void testCreatePhotoAlbumEmptyContent() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhotoAlbum()");
//    String content = "{}";
//    this.mockMvc.perform(post(urlPrefix2 + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public final void testCreatePhotoAlbumNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    PhotoAlbumCreatedEvent testData = PhotoAlbumCreatedEvent.ownerNotFound(dets.getOwnerId());
//    String content = "{\"name\":\"testName\",\"description\":\"description\",\"location\":\"location\",\"created\":123456,\"ownerId\":3214,\"modified\":null}";
//    when(photoService.createPhotoAlbum(any(CreatePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix2 + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isNotFound());
//  }
//
//  @Test
//  public final void testCreatePhotoAlbumNullIdReturned() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    dets.setNodeId(null);
//    PhotoAlbumCreatedEvent testData = new PhotoAlbumCreatedEvent(dets);
//    String content = "{\"name\":\"testName\",\"description\":\"description\",\"location\":\"location\",\"created\":123456,\"ownerId\":3214,\"modified\":null}";
//    when(photoService.createPhotoAlbum(any(CreatePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix2 + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public final void testCreatePhotoAlbumNullDetsReturned() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhotoAlbum()");
//    PhotoAlbumDetails dets = null;
//    PhotoAlbumCreatedEvent testData = new PhotoAlbumCreatedEvent(dets);
//    String content = "{\"name\":\"testName\",\"description\":\"description\",\"location\":\"location\",\"created\":123456,\"ownerId\":3214,\"modified\":null}";
//    when(photoService.createPhotoAlbum(any(CreatePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix2 + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public final void testCreatePhotoAlbumNullEvtReturned() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingCreatePhotoAlbum()");
//    PhotoAlbumCreatedEvent testData = null;
//    String content = "{\"name\":\"testName\",\"description\":\"description\",\"location\":\"location\",\"created\":123456,\"ownerId\":3214,\"modified\":null}";
//    when(photoService.createPhotoAlbum(any(CreatePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(post(urlPrefix2 + "/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andExpect(status().isBadRequest());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#deletePhoto(java.lang.Long)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testDeletePhoto() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    PhotoDeletedEvent testData = new PhotoDeletedEvent(dets.getNodeId());
//    when(photoService.deletePhoto(any(DeletePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "/{photoId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testDeletePhotoNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    DeletedEvent testData = PhotoDeletedEvent.notFound(dets.getNodeId());
//    when(photoService.deletePhoto(any(DeletePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "/{photoId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
//
//  @Test
//  public final void testDeletePhotoForbidden() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhoto()");
//    PhotoDetails dets = DatabaseDataFixture.populatePhoto1().toPhotoDetails();
//    DeletedEvent testData = PhotoDeletedEvent.deletionForbidden(dets.getNodeId());
//    when(photoService.deletePhoto(any(DeletePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "/{photoId}/", dets.getNodeId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isGone());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#deletePhotoAlbum(java.lang.Long)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testDeletePhotoAlbum() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    PhotoAlbumDeletedEvent testData = new PhotoAlbumDeletedEvent(dets.getNodeId());
//    when(photoService.deletePhotoAlbum(any(DeletePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix2 + "/{photoAlbumId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testDeletePhotoAlbumNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    DeletedEvent testData = PhotoAlbumDeletedEvent.notFound(dets.getNodeId());
//    when(photoService.deletePhotoAlbum(any(DeletePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix2 + "/{photoAlbumId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
//
//  @Test
//  public final void testDeletePhotoAlbumForbidden() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhotoAlbum()");
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    DeletedEvent testData = PhotoAlbumDeletedEvent.deletionForbidden(dets.getNodeId());
//    when(photoService.deletePhotoAlbum(any(DeletePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix2 + "/{photoAlbumId}/", dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isGone());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#updatePhoto(java.lang.Long, PhotoDomain)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testUpdatePhoto() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePhoto()");
//    Long id = 1L;
//    Photo photo = DatabaseDataFixture.populatePhoto1();
//    PhotoDetails dets = photo.toPhotoDetails();
//    dets.setTitle("Test Photo2");
//    PhotoUpdatedEvent testData = new PhotoUpdatedEvent(id, dets);
//    String content = setupContent(dets);
//    String returnedContent = setupReturnedContent(dets);
//    when(photoService.updatePhoto(any(UpdatePhotoEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andDo(print())
//      .andExpect(jsonPath("$.nodeId", is(dets.getNodeId().intValue())))
//      .andExpect(jsonPath("$.url", is(dets.getUrl())))
//      .andExpect(jsonPath("$.thumbNailUrl", is(dets.getThumbNailUrl())))
//      .andExpect(jsonPath("$.title", is(dets.getTitle())))
//      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//      .andExpect(jsonPath("$.date", is(dets.getDate())))
//      .andExpect(jsonPath("$.ownerId", is(dets.getOwnerId().intValue())))
//      .andExpect(jsonPath("$.sequence", is(dets.getSequence())))
//      .andExpect(jsonPath("$.inappropriateContent", is(dets.isInappropriateContent())))
//      .andExpect(jsonPath("$.numOfLikes", is(dets.getNumOfLikes().intValue())))
//      .andExpect(jsonPath("$.links[0].rel", is("self")))
//      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
//      .andExpect(jsonPath("$.links[2].rel", is("Next")))
//      .andExpect(jsonPath("$.links[3].rel", is("Read all")))
//      .andExpect(content().string(returnedContent))
//      .andExpect(status().isOk());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#updatePhotoAlbum(java.lang.Long, com.eulersbridge.iEngage.rest.domain.PhotoAlbum)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testUpdatePhotoAlbum() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingUpdatePhotoAlbum()");
//    Long id = 1L;
//    PhotoAlbumDetails dets = DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
//    dets.setName("Test PhotoAlbum 2");
//    PhotoAlbumUpdatedEvent testData = new PhotoAlbumUpdatedEvent(id, dets);
//    String content = "{\"nodeId\":1234,\"name\":\"testName\",\"description\":\"description\",\"location\":\"location\",\"thumbNailUrl\":\"thumbNailUrl\",\"created\":123456,\"ownerId\":3214,\"modified\":null}";
//    if (LOG.isDebugEnabled()) LOG.debug("content = " + content);
//    String returnedContent = "{\"nodeId\":" + dets.getNodeId().intValue() + ",\"name\":\"" + dets.getName() + "\",\"description\":\"" + dets.getDescription() +
//      "\",\"location\":\"" + dets.getLocation() + "\",\"thumbNailUrl\":\"" + dets.getThumbNailUrl() + "\",\"created\":" + dets.getCreated() + ",\"creatorId\":" + dets.getCreatorId() + ",\"ownerId\":" + dets.getOwnerId() + ",\"modified\":" + dets.getModified() +
//      ",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/photoAlbum/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/photoAlbum/1/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/photoAlbum/1/next\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/photoAlbums\"}]}";
//    when(photoService.updatePhotoAlbum(any(UpdatePhotoAlbumEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(put(urlPrefix2 + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andDo(print())
//      .andExpect(jsonPath("$.nodeId", is(dets.getNodeId().intValue())))
//      .andExpect(jsonPath("$.name", is(dets.getName())))
//      .andExpect(jsonPath("$.description", is(dets.getDescription())))
//      .andExpect(jsonPath("$.location", is(dets.getLocation())))
//      .andExpect(jsonPath("$.thumbNailUrl", is(dets.getThumbNailUrl())))
//      .andExpect(jsonPath("$.created", is(dets.getCreated())))
//      .andExpect(jsonPath("$.ownerId", is(dets.getOwnerId().intValue())))
//      .andExpect(jsonPath("$.modified", is(dets.getModified())))
//      .andExpect(jsonPath("$.links[0].rel", is("self")))
//      .andExpect(jsonPath("$.links[1].rel", is("Previous")))
//      .andExpect(jsonPath("$.links[2].rel", is("Next")))
//      .andExpect(jsonPath("$.links[3].rel", is("Read all")))
//      .andExpect(content().string(returnedContent))
//      .andExpect(status().isOk());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#findPhotos(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testFindProfilePhotos() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindProfilePhotos()");
//    Long instId = 1l;
//    String email = "gnewitt@hotmail.com";
//    HashMap<Long, com.eulersbridge.iEngage.database.domain.Photo> dets = DatabaseDataFixture.populatePhotos();
//    Iterable<com.eulersbridge.iEngage.database.domain.Photo> photos = dets.values();
//    Iterator<com.eulersbridge.iEngage.database.domain.Photo> iter = photos.iterator();
//    ArrayList<PhotoDetails> photoDets = new ArrayList<PhotoDetails>();
//    while (iter.hasNext()) {
//      com.eulersbridge.iEngage.database.domain.Photo article = iter.next();
//      photoDets.add(article.toPhotoDetails());
//    }
//    PhotosReadEvent testData = new PhotosReadEvent(instId, photoDets);
//    testData.setTotalPages(1);
//    testData.setTotalPhotos(new Long(photoDets.size()));
//    when(userService.findUserId(any(String.class))).thenReturn(instId);
//    when(photoService.findPhotos(any(ReadPhotosEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix1 + "s/{ownerEmail}/", email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("totalPhotos", is(testData.getTotalPhotos().intValue())))
//      .andExpect(jsonPath("totalPages", is(testData.getTotalPages())))
//      .andExpect(jsonPath("photos[0].nodeId", is(photoDets.get(0).getNodeId().intValue())))
//      .andExpect(jsonPath("photos[0].url", is(photoDets.get(0).getUrl())))
//      .andExpect(jsonPath("photos[0].title", is(photoDets.get(0).getTitle())))
//      .andExpect(jsonPath("photos[0].description", is(photoDets.get(0).getDescription())))
//      .andExpect(jsonPath("photos[0].date", is(photoDets.get(0).getDate())))
//      .andExpect(jsonPath("photos[0].ownerId", is(photoDets.get(0).getOwnerId().intValue())))
//      .andExpect(jsonPath("photos[1].nodeId", is(photoDets.get(1).getNodeId().intValue())))
//      .andExpect(jsonPath("photos[1].url", is(photoDets.get(1).getUrl())))
//      .andExpect(jsonPath("photos[1].title", is(photoDets.get(1).getTitle())))
//      .andExpect(jsonPath("photos[1].description", is(photoDets.get(1).getDescription())))
//      .andExpect(jsonPath("photos[1].date", is(photoDets.get(1).getDate())))
//      .andExpect(jsonPath("photos[1].ownerId", is(photoDets.get(1).getOwnerId().intValue())))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindProfilePhotosZeroPhotos() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotos()");
//    Long instId = 11l;
//    String email = "gnewitt@hotmail.com";
//    ArrayList<PhotoDetails> eleDets = new ArrayList<PhotoDetails>();
//    PhotosReadEvent testData = new PhotosReadEvent(instId, eleDets);
//    when(userService.findUserId(any(String.class))).thenReturn(instId);
//    when(photoService.findPhotos(any(ReadPhotosEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix1 + "s/{ownerEmail}/", email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindProfilePhotosNoOwner() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotos()");
//    Long instId = 11l;
//    String email = "gnewitt@hotmail.com";
//    PhotosReadEvent testData = PhotosReadEvent.ownerNotFound();
//    when(userService.findUserId(any(String.class))).thenReturn(instId);
//    when(photoService.findPhotos(any(ReadPhotosEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix1 + "s/{ownerEmail}/", email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#findPhotos(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testFindPhotos() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotos()");
//    Long instId = 1l;
//    HashMap<Long, com.eulersbridge.iEngage.database.domain.Photo> dets = DatabaseDataFixture.populatePhotos();
//    Iterable<com.eulersbridge.iEngage.database.domain.Photo> photos = dets.values();
//    Iterator<com.eulersbridge.iEngage.database.domain.Photo> iter = photos.iterator();
//    ArrayList<PhotoDetails> photoDets = new ArrayList<PhotoDetails>();
//    while (iter.hasNext()) {
//      com.eulersbridge.iEngage.database.domain.Photo article = iter.next();
//      photoDets.add(article.toPhotoDetails());
//    }
//    PhotosReadEvent testData = new PhotosReadEvent(instId, photoDets);
//    testData.setTotalPages(1);
//    testData.setTotalPhotos(new Long(photoDets.size()));
//    when(photoService.findPhotos(any(ReadPhotosEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("totalPhotos", is(testData.getTotalPhotos().intValue())))
//      .andExpect(jsonPath("totalPages", is(testData.getTotalPages())))
//      .andExpect(jsonPath("photos[0].nodeId", is(photoDets.get(0).getNodeId().intValue())))
//      .andExpect(jsonPath("photos[0].url", is(photoDets.get(0).getUrl())))
//      .andExpect(jsonPath("photos[0].title", is(photoDets.get(0).getTitle())))
//      .andExpect(jsonPath("photos[0].description", is(photoDets.get(0).getDescription())))
//      .andExpect(jsonPath("photos[0].date", is(photoDets.get(0).getDate())))
//      .andExpect(jsonPath("photos[0].ownerId", is(photoDets.get(0).getOwnerId().intValue())))
//      .andExpect(jsonPath("photos[0].inappropriateContent", is(photoDets.get(0).isInappropriateContent())))
//      .andExpect(jsonPath("photos[0].numOfLikes", is(photoDets.get(0).getNumOfLikes().intValue())))
//      .andExpect(jsonPath("photos[1].nodeId", is(photoDets.get(1).getNodeId().intValue())))
//      .andExpect(jsonPath("photos[1].url", is(photoDets.get(1).getUrl())))
//      .andExpect(jsonPath("photos[1].title", is(photoDets.get(1).getTitle())))
//      .andExpect(jsonPath("photos[1].description", is(photoDets.get(1).getDescription())))
//      .andExpect(jsonPath("photos[1].date", is(photoDets.get(1).getDate())))
//      .andExpect(jsonPath("photos[1].ownerId", is(photoDets.get(1).getOwnerId().intValue())))
//      .andExpect(jsonPath("photos[1].inappropriateContent", is(photoDets.get(1).isInappropriateContent())))
//      .andExpect(jsonPath("photos[1].numOfLikes", is(photoDets.get(1).getNumOfLikes().intValue())))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindPhotosZeroPhotos() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotos()");
//    Long instId = 11l;
//    ArrayList<PhotoDetails> eleDets = new ArrayList<PhotoDetails>();
//    PhotosReadEvent testData = new PhotosReadEvent(instId, eleDets);
//    when(photoService.findPhotos(any(ReadPhotosEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindPhotosNoOwner() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotos()");
//    Long instId = 11l;
//    PhotosReadEvent testData = PhotosReadEvent.ownerNotFound();
//    when(photoService.findPhotos(any(ReadPhotosEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#deletePhotos(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
//   *
//   * @throws Exception
//   */
//
//  @Test
//  public final void testDeletePhotos() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhotos()");
//    Long instId = 1l;
//    HashMap<Long, com.eulersbridge.iEngage.database.domain.Photo> dets = DatabaseDataFixture.populatePhotos();
//    Iterable<com.eulersbridge.iEngage.database.domain.Photo> photos = dets.values();
//    Iterator<com.eulersbridge.iEngage.database.domain.Photo> iter = photos.iterator();
//    ArrayList<PhotoDetails> photoDets = new ArrayList<PhotoDetails>();
//    while (iter.hasNext()) {
//      com.eulersbridge.iEngage.database.domain.Photo article = iter.next();
//      photoDets.add(article.toPhotoDetails());
//    }
//    PhotosReadEvent testData = new PhotosReadEvent(instId, photoDets);
//    testData.setTotalPages(1);
//    testData.setTotalPhotos(new Long(photoDets.size()));
//    when(photoService.deletePhotos(any(ReadPhotosEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
///*		.andExpect(jsonPath("$totalPhotos",is(testData.getTotalPhotos().intValue())))
//    .andExpect(jsonPath("$totalPages",is(testData.getTotalPages())))
//		.andExpect(jsonPath("$photos[0].nodeId",is(photoDets.get(0).getId().intValue())))
//		.andExpect(jsonPath("$photos[0].url",is(photoDets.get(0).getUrl())))
//		.andExpect(jsonPath("$photos[0].title",is(photoDets.get(0).getTitle())))
//		.andExpect(jsonPath("$photos[0].description",is(photoDets.get(0).getDescription())))
//		.andExpect(jsonPath("$photos[0].date",is(photoDets.get(0).getDate())))
//		.andExpect(jsonPath("$photos[0].ownerId",is(photoDets.get(0).getOwnerId().intValue())))
//		.andExpect(jsonPath("$photos[1].nodeId",is(photoDets.get(1).getId().intValue())))
//		.andExpect(jsonPath("$photos[1].url",is(photoDets.get(1).getUrl())))
//		.andExpect(jsonPath("$photos[1].title",is(photoDets.get(1).getTitle())))
//		.andExpect(jsonPath("$photos[1].description",is(photoDets.get(1).getDescription())))
//		.andExpect(jsonPath("$photos[1].date",is(photoDets.get(1).getDate())))
//		.andExpect(jsonPath("$photos[1].ownerId",is(photoDets.get(1).getOwnerId().intValue())))
//*/.andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testDeletePhotosZeroPhotos() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhotos()");
//    Long instId = 11l;
//    ArrayList<PhotoDetails> eleDets = new ArrayList<PhotoDetails>();
//    PhotosReadEvent testData = new PhotosReadEvent(instId, eleDets);
//    when(photoService.deletePhotos(any(ReadPhotosEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testDeletePhotosNoOwner() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeletePhotos()");
//    Long instId = 11l;
//    PhotosReadEvent testData = PhotosReadEvent.ownerNotFound();
//    when(photoService.deletePhotos(any(ReadPhotosEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.PhotoController#findPhotos(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testFindPhotoAlbums() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotoAlbums()");
//    Long instId = 1l;
//    HashMap<Long, com.eulersbridge.iEngage.database.domain.PhotoAlbum> dets = DatabaseDataFixture.populatePhotoAlbums();
//    Iterable<com.eulersbridge.iEngage.database.domain.PhotoAlbum> photos = dets.values();
//    Iterator<com.eulersbridge.iEngage.database.domain.PhotoAlbum> iter = photos.iterator();
//    ArrayList<PhotoAlbumDetails> photoDets = new ArrayList<PhotoAlbumDetails>();
//    while (iter.hasNext()) {
//      com.eulersbridge.iEngage.database.domain.PhotoAlbum article = iter.next();
//      photoDets.add(article.toPhotoAlbumDetails());
//    }
//    AllReadEvent testData = new AllReadEvent(instId, photoDets);
//    testData.setTotalPages(1);
//    testData.setTotalItems(new Long(photoDets.size()));
//    when(photoService.findPhotoAlbums(any(ReadPhotoAlbumsEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix2 + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("totalElements", is(testData.getTotalItems().intValue())))
//      .andExpect(jsonPath("totalPages", is(testData.getTotalPages())))
//      .andExpect(jsonPath("foundObjects[0].nodeId", is(photoDets.get(0).getNodeId().intValue())))
//      .andExpect(jsonPath("foundObjects[0].name", is(photoDets.get(0).getName())))
//      .andExpect(jsonPath("foundObjects[0].description", is(photoDets.get(0).getDescription())))
//      .andExpect(jsonPath("foundObjects[0].location", is(photoDets.get(0).getLocation())))
//      .andExpect(jsonPath("foundObjects[0].created", is(photoDets.get(0).getCreated())))
//      .andExpect(jsonPath("foundObjects[0].ownerId", is(photoDets.get(0).getOwnerId().intValue())))
//      .andExpect(jsonPath("foundObjects[0].modified", is(photoDets.get(0).getModified())))
//      .andExpect(jsonPath("foundObjects[1].nodeId", is(photoDets.get(1).getNodeId().intValue())))
//      .andExpect(jsonPath("foundObjects[1].name", is(photoDets.get(1).getName())))
//      .andExpect(jsonPath("foundObjects[1].description", is(photoDets.get(1).getDescription())))
//      .andExpect(jsonPath("foundObjects[1].location", is(photoDets.get(1).getLocation())))
//      .andExpect(jsonPath("foundObjects[1].created", is(photoDets.get(1).getCreated())))
//      .andExpect(jsonPath("foundObjects[1].ownerId", is(photoDets.get(1).getOwnerId().intValue())))
//      .andExpect(jsonPath("foundObjects[1].modified", is(photoDets.get(1).getModified())))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindPhotoAlbumsZeroAlbums() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotoAlbums()");
//    Long instId = 11l;
//    ArrayList<PhotoAlbumDetails> photoAlbumDets = new ArrayList<PhotoAlbumDetails>();
//    AllReadEvent testData = new AllReadEvent(instId, photoAlbumDets);
//    when(photoService.findPhotoAlbums(any(ReadPhotoAlbumsEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix2 + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindPhotoAlbumsNoInstitution() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotoAlbums()");
//    Long instId = 11l;
//    AllReadEvent testData = AllReadEvent.notFound(null);
//    when(photoService.findPhotoAlbums(any(ReadPhotoAlbumsEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix2 + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
//
////  @Test
////  public final void testFindPhotoAlbumsNoNewsFeed() throws Exception {
////    if (LOG.isDebugEnabled()) LOG.debug("performingFindPhotoAlbums()");
////    Long instId = 11l;
////    AllReadEvent testData = AllReadEvent.notFound(null);
////    when(photoService.findPhotoAlbums(any(ReadPhotoAlbumsEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(testData);
////    this.mockMvc.perform(get(urlPrefix2 + "s/{instId}/", instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
////      .andExpect(status().isNotFound());
////  }
//
//  @Test
//  public final void testLikePhoto() throws Exception {
//    Photo photo = DatabaseDataFixture.populatePhoto1();
//    User user = DatabaseDataFixture.populateUserGnewitt();
//    String email = user.getEmail();
//    Long photoId = photo.getNodeId();
//    LikedEvent event = new LikedEvent(photoId, email, true);
//    when(likesService.like(any(LikeEvent.class))).thenReturn(event);
//
//    this.mockMvc.perform(put(urlPrefix + "/{photoId}/" + ControllerConstants.LIKED_BY_LABEL + "/{email}/", photoId, email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("success", is(event.isResultSuccess())))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testLikePhotoFailed() throws Exception {
//    Photo photo = DatabaseDataFixture.populatePhoto1();
//    User user = DatabaseDataFixture.populateUserGnewitt();
//    String email = user.getEmail();
//    Long photoId = photo.getNodeId();
//    LikedEvent event = new LikedEvent(photoId, email, false);
//    when(likesService.like(any(LikeEvent.class))).thenReturn(event);
//
//    this.mockMvc.perform(put(urlPrefix + "/{photoId}/" + ControllerConstants.LIKED_BY_LABEL + "/{email}/", photoId, email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("success", is(event.isResultSuccess())))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testLikePhotoEntityNotFound() throws Exception {
//    Photo photo = DatabaseDataFixture.populatePhoto1();
//    User user = DatabaseDataFixture.populateUserGnewitt();
//    String email = user.getEmail();
//    Long photoId = photo.getNodeId();
//    LikedEvent event = LikedEvent.entityNotFound(photoId, email);
//    when(likesService.like(any(LikeEvent.class))).thenReturn(event);
//
//    this.mockMvc.perform(put(urlPrefix + "/{photoId}/" + ControllerConstants.LIKED_BY_LABEL + "/{email}/", photoId, email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(status().isGone());
//  }
//
//  @Test
//  public final void testLikePhotoUserNotFound() throws Exception {
//    Photo photo = DatabaseDataFixture.populatePhoto1();
//    User user = DatabaseDataFixture.populateUserGnewitt();
//    String email = user.getEmail();
//    Long photoId = photo.getNodeId();
//    LikedEvent event = LikedEvent.userNotFound(photoId, email);
//    when(likesService.like(any(LikeEvent.class))).thenReturn(event);
//
//    this.mockMvc.perform(put(urlPrefix + "/{photoId}/" + ControllerConstants.LIKED_BY_LABEL + "/{email}/", photoId, email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(status().isNotFound());
//  }
//
//}
