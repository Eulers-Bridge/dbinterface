/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.*;
import com.eulersbridge.iEngage.core.events.newsFeed.CreateNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.InstitutionService;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import com.eulersbridge.iEngage.database.repository.CountryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NewsFeedRepository;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Greg Newitt
 */
public class InstitutionDomainEventHandlerTest {
  private static Logger LOG = LoggerFactory.getLogger(InstitutionDomainEventHandlerTest.class);

  @Mock
  CountryRepository countryRepository;
  @Mock
  InstitutionRepository institutionRepository;
  @Mock
  NewsFeedRepository newsFeedRepository;

  InstitutionService service;
//	InstitutionMemoryRepository testInstRepo;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    service = new InstitutionEventHandler(institutionRepository, countryRepository, newsFeedRepository);
  }


  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#(com.eulersbridge.iEngage.database.repository.InstitutionRepository, com.eulersbridge.iEngage.database.repository.CountryRepository)}.
   */
  @Test
  public void testInstitutionEventHandler() {
    InstitutionService instService2 = new InstitutionEventHandler(institutionRepository, countryRepository, newsFeedRepository);
    assertNotNull("Constructor did not create service.", instService2);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#createInstitution(com.eulersbridge.iEngage.core.events.institutions.CreateInstitutionEvent)}.
   */
  @Test
  public void testCreateInstitution() {
    CreateInstitutionEvent createInstitutionEvent;
    InstitutionDetails nADs;
    Long id = new Long(2);
    nADs = new InstitutionDetails(id);
    nADs.setName("RMIT University");
    nADs.setCampus("Melbourne");
    nADs.setState("Victoria");
    nADs.setCountryName("Australia");
    createInstitutionEvent = new CreateInstitutionEvent(nADs);
    InstitutionCreatedEvent nace = service.createInstitution(createInstitutionEvent);
    assertNotNull("Not yet implemented", nace);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#requestReadInstitution(com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent)}.
   */
  @Test
  public void testRequestReadInstitution() {
    RequestReadInstitutionEvent rnae = new RequestReadInstitutionEvent(new Long(1));
    Institution value = DatabaseDataFixture.populateInstUniMelb();
    when(institutionRepository.findOne(any(Long.class))).thenReturn(value);
    assertEquals("1 == 1", rnae.getNodeId(), new Long(1));
    ReadInstitutionEvent rane = (ReadInstitutionEvent) service.requestReadInstitution(rnae);
    assertNotNull("Not yet implemented", rane);
  }

  @Test
  public void testRequestReadNonExistentInstitutionShouldReturnNotFound() {
    Long nodeId = new Long(19);
    RequestReadInstitutionEvent rnae = new RequestReadInstitutionEvent(nodeId);
    assertEquals("19 == 19", rnae.getNodeId(), new Long(19));
    ReadEvent rane = service.requestReadInstitution(rnae);
    assertNotNull("Not yet implemented", rane);
    assertNull(rane.getDetails());
    assertEquals(nodeId, rane.getNodeId());
    assertFalse("Non-existent entity should not be found.", rane.isEntityFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#updateInstitution(com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent)}.
   */
  @Test
  public void testUpdateInstitution() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingInstitution()");

    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    when(countryRepository.findByCountryName(any(String.class))).thenReturn(inst.getCountry$());
    when(institutionRepository.save(any(Institution.class))).thenReturn(inst);
    InstitutionDetails dets = inst.toInstDetails();
    UpdateInstitutionEvent updateInstitutionEvent = new UpdateInstitutionEvent(dets.getNodeId(), dets);

    UpdatedEvent nude = service.updateInstitution(updateInstitutionEvent);
    assertNotNull("Not yet implemented", nude);

    InstitutionDetails returnedDets = (InstitutionDetails) nude.getDetails();
    assertEquals(returnedDets, dets);
    assertNotNull(nude.getNodeId());
    assertEquals(nude.getNodeId(), returnedDets.getNodeId());
    assertTrue(nude.isEntityFound());
  }

  @Test
  public void testUpdateInstitutionCountryNotFound() {
    if (LOG.isDebugEnabled()) LOG.debug("UpdatingInstitution()");

    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    when(countryRepository.findByCountryName(any(String.class))).thenReturn(null);
    InstitutionDetails dets = inst.toInstDetails();
    UpdateInstitutionEvent updateInstitutionEvent = new UpdateInstitutionEvent(dets.getNodeId(), dets);

    UpdatedEvent nude = service.updateInstitution(updateInstitutionEvent);
    assertNotNull("Not yet implemented", nude);

    InstitutionDetails returnedDets = (InstitutionDetails) nude.getDetails();
    assertNull(returnedDets);
    assertEquals(dets.getNodeId(), nude.getNodeId());
    assertFalse(nude.isEntityFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#deleteInstitution(com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent)}.
   */
  @Test
  public void testDeleteInstitution() {
    DeleteInstitutionEvent deleteInstitutionEvent = new DeleteInstitutionEvent(new Long(1));
    DeletedEvent nUDe = service.deleteInstitution(deleteInstitutionEvent);
    assertNotNull("Institution Deleted Event returned null.", nUDe);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#deleteInstitution(com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent)}.
   */
  @Test
  public void testDeleteNonExistentInstitutionShouldReturnNotFound() {
    DeleteInstitutionEvent deleteInstitutionEvent = new DeleteInstitutionEvent(new Long(19));
    DeletedEvent nUDe = service.deleteInstitution(deleteInstitutionEvent);
    assertNotNull("Institution Deleted Event returned null.", nUDe);
    assertFalse("Entity allegedly found!", nUDe.isEntityFound());
    assertFalse("No entity, so deletetion could not have been completed.", nUDe.isDeletionCompleted());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler}.
   */
  @Test
  public void testReadInstitutions() {
    ArrayList<Institution> evts = new ArrayList<Institution>();
    evts.add(DatabaseDataFixture.populateInstUniMelb());
    evts.add(DatabaseDataFixture.populateInstMonashUni());


    ReadAllEvent rie = new ReadAllEvent(null);
    when(institutionRepository.findAll()).thenReturn(evts);

    InstitutionsReadEvent institutions = service.readInstitutions(rie);
    assertNotNull("Not yet implemented", institutions);
    rie = new ReadAllEvent((long) 1);
    when(institutionRepository.findByCountryId(any(Long.class))).thenReturn(evts);
    institutions = service.readInstitutions(rie);
    assertNotNull("Not yet implemented", institutions);
  }

  @Test
  public void testCreateStudentYear() {
    NewsFeedDetails newsFeedDetails = new NewsFeedDetails((long) 1);
    CreateNewsFeedEvent csye = new CreateNewsFeedEvent(newsFeedDetails);
    NewsFeed value = NewsFeed.fromDetails(newsFeedDetails);
    when(newsFeedRepository.save(any(NewsFeed.class))).thenReturn(value);

    NewsFeedCreatedEvent syce = service.createNewsFeed(csye);
    assertNotNull("Student year created event was null.", syce);
  }

}
