//package com.eulersbridge.iEngage.core.services;
//
//import com.eulersbridge.iEngage.core.events.DeletedEvent;
//import com.eulersbridge.iEngage.core.events.ReadAllEvent;
//import com.eulersbridge.iEngage.core.events.ReadEvent;
//import com.eulersbridge.iEngage.core.events.UpdatedEvent;
//import com.eulersbridge.iEngage.core.events.countrys.*;
//import com.eulersbridge.iEngage.core.services.interfacePack.CountryService;
//import com.eulersbridge.iEngage.database.domain.Country;
//import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
//import com.eulersbridge.iEngage.database.repository.CountryRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.when;
//
///**
// * @author Greg Newitt
// */
//public class CountryEventHandlerTest {
//  CountryEventHandler countryService;
//
//  @Mock
//  CountryRepository countryRepository;
//
//  CountryService service;
//
//  @Before
//  public void setUp() throws Exception {
//    MockitoAnnotations.initMocks(this);
//
//    service = new CountryEventHandler(countryRepository);
//  }
//
//  @Test
//  public void testCountryEventHandler() {
//    CountryService countryService2 = new CountryEventHandler(countryRepository);
//    assertNotNull("countryService not being created by constructor.", countryService2);
//  }
//
//  @Test
//  public void testCreateCountry() {
//    CreateCountryEvent createCountryEvent;
//    Country value = DatabaseDataFixture.populateCountryAust();
//    CountryDetails cDs = value.toCountryDetails();
//    createCountryEvent = new CreateCountryEvent(cDs);
//    when(countryRepository.save(any(Country.class))).thenReturn(value);
//    CountryCreatedEvent nace = service.createCountry(createCountryEvent);
//    assertNotNull("Not yet implemented", nace);
//    assertEquals(nace.getDetails(), value.toCountryDetails());
//    assertEquals(nace.getId(), value.getNodeId());
//    assertEquals(nace.getNodeId(), value.getNodeId());
//  }
//
//  @Test
//  public void testReadCountry() {
//    Country value = DatabaseDataFixture.populateCountryAust();
//    ReadCountryEvent rnae = new ReadCountryEvent(value.getNodeId());
//    when(countryRepository.findOne(any(Long.class))).thenReturn(value);
//    CountryReadEvent rane = (CountryReadEvent) service.readCountry(rnae);
//    assertNotNull("Not yet implemented", rane);
//    assertEquals(rane.getDetails(), value.toCountryDetails());
//    assertEquals(rane.getNodeId(), value.getNodeId());
//    assertTrue(rane.isEntityFound());
//  }
//
//  @Test
//  public void testReadNonExistentCountryShouldReturnNotFound() {
//    ReadCountryEvent rnae = new ReadCountryEvent(new Long(19));
//    when(countryRepository.findOne(any(Long.class))).thenReturn(null);
//    ReadEvent rane = service.readCountry(rnae);
//    assertNotNull("Not yet implemented", rane);
//    assertFalse("Entity should not be found.", rane.isEntityFound());
//  }
//
//  @Test
//  public void testUpdateCountry() {
//    Country value = DatabaseDataFixture.populateCountryAust();
//    CountryDetails cDs = value.toCountryDetails();
//    when(countryRepository.save(any(Country.class))).thenReturn(value);
//
//    cDs = new CountryDetails(new Long(1));
//    cDs.setCountryName("New Zealand");
//
//    UpdateCountryEvent updateCountryEvent = new UpdateCountryEvent(cDs.getCountryId(), cDs);
//    UpdatedEvent nude = service.updateCountry(updateCountryEvent);
//    assertNotNull("Not yet implemented", nude);
//    assertEquals(nude.getNodeId(), value.getNodeId());
//    assertEquals(nude.getDetails(), value.toCountryDetails());
//    assertTrue(nude.isEntityFound());
//    assertFalse(nude.isFailed());
//  }
//
//  @Test
//  public void testDeleteCountry() {
//    DeleteCountryEvent deleteCountryEvent = new DeleteCountryEvent(new Long(1));
//    DeletedEvent nUDe = service.deleteCountry(deleteCountryEvent);
//    assertNotNull("Not yet implemented", nUDe);
//  }
//
//  @Test
//  public void testDeleteNonExistentCountryShouldReturnNotFound() {
//    DeleteCountryEvent deleteCountryEvent = new DeleteCountryEvent(new Long(19));
//    DeletedEvent nUDe = service.deleteCountry(deleteCountryEvent);
//    assertNotNull("Not yet implemented", nUDe);
//    assertFalse("Entity can't be found...", nUDe.isEntityFound());
//    assertFalse("Deletion has not completed!", nUDe.isDeletionCompleted());
//  }
//
//  @Test
//  public void testReadCountrys() {
//    ArrayList<Country> evts = new ArrayList<Country>();
//    evts.add(DatabaseDataFixture.populateCountryAust());
//    evts.add(DatabaseDataFixture.populateCountryAust());
//
//    ReadAllEvent rce = new ReadAllEvent(null);
//    when(countryRepository.findAll()).thenReturn(evts);
//    CountrysReadEvent countrys = service.readCountrys(rce);
//    assertNotNull("Not yet implemented", countrys);
//  }
//
//}
