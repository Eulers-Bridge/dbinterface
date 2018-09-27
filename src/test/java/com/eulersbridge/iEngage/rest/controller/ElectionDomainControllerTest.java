///**
// *
// */
//package com.eulersbridge.iEngage.rest.controller;
//
//import com.eulersbridge.iEngage.core.events.*;
//import com.eulersbridge.iEngage.core.events.elections.*;
//import com.eulersbridge.iEngage.core.events.votingLocation.AddVotingLocationEvent;
//import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
//import com.eulersbridge.iEngage.core.services.interfacePack.ElectionService;
//import com.eulersbridge.iEngage.core.services.interfacePack.InstitutionService;
//import com.eulersbridge.iEngage.core.services.interfacePack.VotingLocationService;
//import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
//import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;
//import com.eulersbridge.iEngage.rest.domain.ElectionDomain;
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
//public class ElectionDomainControllerTest {
//  private static Logger LOG = LoggerFactory.getLogger(ElectionDomainControllerTest.class);
//
//  private String urlPrefix = ControllerConstants.API_PREFIX + ControllerConstants.ELECTION_LABEL;
//
//  MockMvc mockMvc;
//
//  @InjectMocks
//  ElectionController controller;
//
//  @Mock
//  ElectionService electionService;
//  @Mock
//  InstitutionService instService;
//  @Mock
//  VotingLocationService votingLocationService;
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
////		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#ElectionController()}.
//   */
//  @Test
//  public final void testElectionController() {
//    ElectionController elecController = new ElectionController();
//    assertNotNull("Not yet implemented", elecController);
//  }
//
//
//  @Test
//  public final void testFindPreviousElection() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPreviousElection()");
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    ReadElectionEvent testData = new ReadElectionEvent(dets.getElectionId(), dets);
//    when(electionService.readPreviousElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "/{electionId}" + ControllerConstants.PREVIOUS_LABEL, dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("$.title", is(dets.getTitle())))
//      .andExpect(jsonPath("$.start", is(dets.getStart().intValue())))
//      .andExpect(jsonPath("$.end", is(dets.getEnd().intValue())))
//      .andExpect(jsonPath("$.startVoting", is(dets.getStartVoting().intValue())))
//      .andExpect(jsonPath("$.endVoting", is(dets.getEndVoting().intValue())))
//      .andExpect(jsonPath("$.electionId", is(dets.getElectionId().intValue())))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindPreviousElectionNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindPreviousElection()");
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    ReadEvent testData = ReadElectionEvent.notFound(dets.getElectionId());
//    when(electionService.readPreviousElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "/{electionId}" + ControllerConstants.PREVIOUS_LABEL, dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(status().isNotFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#findNextElection(java.lang.Long)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testFindNextElection() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindNextElection()");
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    ReadElectionEvent testData = new ReadElectionEvent(dets.getElectionId(), dets);
//    when(electionService.readNextElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "/{electionId}" + ControllerConstants.NEXT_LABEL, dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("$.title", is(dets.getTitle())))
//      .andExpect(jsonPath("$.start", is(dets.getStart().intValue())))
//      .andExpect(jsonPath("$.end", is(dets.getEnd().intValue())))
//      .andExpect(jsonPath("$.startVoting", is(dets.getStartVoting().intValue())))
//      .andExpect(jsonPath("$.endVoting", is(dets.getEndVoting().intValue())))
//      .andExpect(jsonPath("$.electionId", is(dets.getElectionId().intValue())))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testFindNextElectionNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingFindNextElection()");
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    ReadEvent testData = ReadElectionEvent.notFound(dets.getElectionId());
//    when(electionService.readNextElection(any(RequestReadElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(get(urlPrefix + "/{electionId}" + ControllerConstants.NEXT_LABEL, dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(status().isNotFound());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#deleteElection(java.lang.Long)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testDeleteElection() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeleteElection()");
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    ElectionDeletedEvent testData = new ElectionDeletedEvent(dets.getElectionId());
//    when(electionService.deleteElection(any(DeleteElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "/{electionId}/", dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
//      .andExpect(status().isOk());
//  }
//
//  @Test
//  public final void testDeleteElectionNotFound() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeleteElection()");
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    DeletedEvent testData = ElectionDeletedEvent.notFound(dets.getElectionId());
//    when(electionService.deleteElection(any(DeleteElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "/{electionId}/", dets.getElectionId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(status().isNotFound());
//  }
//
//  @Test
//  public final void testDeleteElectionForbidden() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingDeleteElection()");
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    DeletedEvent testData = ElectionDeletedEvent.deletionForbidden(dets.getElectionId());
//    when(electionService.deleteElection(any(DeleteElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(delete(urlPrefix + "/{electionId}/", dets.getElectionId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(status().isGone());
//  }
//
//
//
//  @Test
//  public void testUpdateElectionNullEventReturned() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingUpdateElection()");
//    Long id = 1L;
//    String content = "{\"electionId\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
//    when(electionService.updateElection(any(UpdateElectionEvent.class))).thenReturn(null);
//    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testUpdateElectionBadContent() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingUpdateElection()");
//    Long id = 1L;
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    ElectionUpdatedEvent testData = new ElectionUpdatedEvent(id, dets);
//    String content = "{\"electionId1\":1,\"title\":\"Test Election\",\"start\":123456,\"end\":123756,\"startVoting\":123456,\"endVoting\":123756,\"institutionId\":1}";
//    when(electionService.updateElection(any(UpdateElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testUpdateElectionEmptyContent() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingUpdateElection()");
//    Long id = 1L;
//    ElectionDetails dets = DatabaseDataFixture.populateElection1().toElectionDetails();
//    ElectionUpdatedEvent testData = new ElectionUpdatedEvent(id, dets);
//    when(electionService.updateElection(any(UpdateElectionEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(put(urlPrefix + "/{id}/", id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  /**
//   * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#updateElection(java.lang.Long, ElectionDomain)}.
//   *
//   * @throws Exception
//   */
//  @Test
//  public final void testAddLocationToElection() throws Exception {
//    if (LOG.isDebugEnabled()) LOG.debug("performingAddLocationToElection()");
//    VotingLocationDetails dets = DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
//    Long electionId = 3l, votingLocationId = dets.getNodeId();
//
//    UpdatedEvent testData = new UpdatedEvent(votingLocationId, dets);
//    String returnedContent = "{\"votingLocationId\":" + dets.getNodeId().intValue() + ",\"name\":\"" + dets.getName() + "\",\"information\":\"" + dets.getInformation() + "\",\"ownerId\":" + dets.getOwnerId().intValue() +
//      ",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/votingLocation/237\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/votingLocations\"}]}";
//    when(votingLocationService.addVotingLocationToElection(any(AddVotingLocationEvent.class))).thenReturn(testData);
//    this.mockMvc.perform(put(urlPrefix + "/{electionId}/votingLocation/{votingLocationId}", electionId.intValue(), votingLocationId.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//      .andDo(print())
//      .andExpect(jsonPath("$.name", is(dets.getName())))
//      .andExpect(jsonPath("$.information", is(dets.getInformation())))
//      .andExpect(jsonPath("$.ownerId", is(dets.getOwnerId().intValue())))
//      .andExpect(jsonPath("$.votingLocationId", is(dets.getNodeId().intValue())))
//      .andExpect(jsonPath("$.links[0].rel", is("self")))
//      .andExpect(jsonPath("$.links[1].rel", is("Read all")))
//      .andExpect(content().string(returnedContent))
//      .andExpect(status().isOk());
//  }
//}
