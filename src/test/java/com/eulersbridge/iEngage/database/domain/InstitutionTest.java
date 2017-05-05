/**
 *
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */
public class InstitutionTest {
  private Institution institution;
  private static Logger LOG = LoggerFactory.getLogger(InstitutionTest.class);

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    institution = DatabaseDataFixture.populateInstUniMelb();
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#Institution()}.
   */
  @Test
  public final void testInstitution() {
    if (LOG.isDebugEnabled()) LOG.debug("Test Instituion Constructor");
    Institution testObj = new Institution();
    assertEquals(testObj.getClass(), Institution.class);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#Institution(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
   */
  @Test
  public final void testInstitutionStringStringStringString() {
    if (LOG.isDebugEnabled()) LOG.debug("Test Instituion Constructor");
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution("University of Melbourne", "Parkville", "VIC", country);
    assertEquals("University constructor does not return a class of type institution.", testObj.getClass(), Institution.class);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getName()}.
   */
  @Test
  public final void testGetName() {
    if (LOG.isDebugEnabled()) LOG.debug("Test getName()");
    String name = "University of Melbourne";
    String campus = "Parkville";
    String state = "VIC";
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution(name, campus, state, country);
    assertEquals("getName() does not return the value class was constructed with.", testObj.getName(), name);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setName(java.lang.String)}.
   */
  @Test
  public final void testSetName() {
    if (LOG.isDebugEnabled()) LOG.debug("Test setName()");
    String name = "University of Melbourne";
    String campus = "Parkville";
    String state = "VIC";
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution(name, campus, state, country);
    String name2 = "Monash University";
    testObj.setName(name2);
    assertEquals("getName() does not return the value that setter should have set it to.", testObj.getName(), name2);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getCampus()}.
   */
  @Test
  public final void testGetCampus() {
    if (LOG.isDebugEnabled()) LOG.debug("Test getCampus()");
    String name = "University of Melbourne";
    String campus = "Parkville";
    String state = "VIC";
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution(name, campus, state, country);
    assertEquals("getCampus() does not return the value class was constructed with.", testObj.getCampus(), campus);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setCampus(java.lang.String)}.
   */
  @Test
  public final void testSetCampus() {
    if (LOG.isDebugEnabled()) LOG.debug("Test setCampus()");
    String name = "University of Melbourne";
    String campus = "Parkville";
    String state = "VIC";
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution(name, campus, state, country);
    String campus2 = "Clayton";
    testObj.setCampus(campus2);
    assertEquals("getCampus() does not return the value that setter should have set it to.", testObj.getCampus(), campus2);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getState()}.
   */
  @Test
  public final void testGetState() {
    if (LOG.isDebugEnabled()) LOG.debug("Test getState()");
    String name = "University of Melbourne";
    String campus = "Parkville";
    String state = "VIC";
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution(name, campus, state, country);
    assertEquals("getState() does not return the value class was constructed with.", testObj.getState(), state);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setState(java.lang.String)}.
   */
  @Test
  public final void testSetState() {
    if (LOG.isDebugEnabled()) LOG.debug("Test setState()");
    String name = "University of Melbourne";
    String campus = "Parkville";
    String state = "VIC";
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution(name, campus, state, country);
    String state2 = "NSW";
    testObj.setState(state2);
    assertEquals("getState() does not return the value that setter should have set it to.", testObj.getState(), state2);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getCountry()}.
   */
  @Test
  public final void testGetCountry() {
    if (LOG.isDebugEnabled()) LOG.debug("Test getCountry()");
    String name = "University of Melbourne";
    String campus = "Parkville";
    String state = "VIC";
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution(name, campus, state, country);
    assertEquals("getCountry() does not return the value class was constructed with.", testObj.getCountry().getCountryName(), country.getCountryName());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setCountry(java.lang.String)}.
   */
  @Test
  public final void testSetCountry() {
    if (LOG.isDebugEnabled()) LOG.debug("Test setCountry()");
    String name = "University of Melbourne";
    String campus = "Parkville";
    String state = "VIC";
    Country country = new Country();
    country.setCountryName("Australia");
    Institution testObj = new Institution(name, campus, state, country);
    Country country2 = new Country();
    country2.setCountryName("Australia");
    testObj.setCountry(country2);
    assertEquals("getCountry() does not return the value that setter should have set it to.", testObj.getCountry().getCountryName(), country2.getCountryName());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#toString()}.
   */
  @Test
  public final void testToString() {
    assertNotNull(institution.toString());
  }

  @Test
  public final void testToDetails() {
    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    InstitutionDetails dets = inst.toInstDetails();
    assertEquals("Names don't match.", dets.getName(), inst.getName());
    assertEquals("Campuses don't match.", dets.getCampus(), inst.getCampus());
    assertEquals("States don't match.", dets.getState(), inst.getState());
    assertEquals("Countrys don't match.", dets.getCountryName(), inst.getCountry().getCountryName());
  }

  @Test
  public final void testFromDetails() {
    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    Institution inst2 = Institution.fromInstDetails(inst.toInstDetails());
    Country country = new Country();
    country.setCountryName(inst.getCountry().getCountryName());
    inst2.setCountry(country);
    assertEquals("Names don't match.", inst2.getName(), inst.getName());
    assertEquals("Campuses don't match.", inst2.getCampus(), inst.getCampus());
    assertEquals("States don't match.", inst2.getState(), inst.getState());
    assertEquals("Countrys don't match.", inst2.getCountry().getCountryName(), inst.getCountry().getCountryName());
  }

  @Test
  public final void testFromDetailsNullDetails() {
    Institution inst2 = Institution.fromInstDetails(null);
    assertNull(inst2);
  }

  private void checkHashCode(Institution test1, Institution test2) {
    assertNotEquals(test1.hashCode(), test2.hashCode());
    assertNotEquals(test2.hashCode(), test1.hashCode());
  }

  private void checkNotEquals(Institution test1, Institution test2) {
    assertNotEquals(test1, test2);
    assertNotEquals(test2, test1);
  }

  /**
   * Test method for {@link java.lang.Object#hashCode()}.
   */
  @Test
  public final void testHashCode() {
    Institution institutionTest = DatabaseDataFixture.populateInstUniMelb();
    assertEquals(institutionTest.hashCode(), institutionTest.hashCode());
    assertEquals(institutionTest.hashCode(), institution.hashCode());
    institutionTest.setNodeId(null);
    checkHashCode(institution, institutionTest);
    institution.setNodeId(null);

    institutionTest.setName(null);
    checkHashCode(institution, institutionTest);
    institutionTest.setName(institution.getName());

    institutionTest.setCampus(null);
    checkHashCode(institution, institutionTest);
    institutionTest.setCampus(institution.getCampus());

    institutionTest.setState(null);
    checkHashCode(institution, institutionTest);
    institutionTest.setState(institution.getState());

    institutionTest.setCountry(null);
    checkHashCode(institution, institutionTest);
    institutionTest.setCountry(institution.getCountry());

  }

  /**
   * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
   */
  @Test
  public final void testEquals() {
    Institution institutionTest = null;
    assertNotEquals(institutionTest, institution);
    assertNotEquals(institution, institutionTest);
    String notElection = "";
    assertNotEquals(institution, notElection);
    institutionTest = DatabaseDataFixture.populateInstUniMelb();
    assertEquals(institutionTest, institutionTest);
    assertEquals(institutionTest, institution);

    institutionTest.setNodeId(54l);
    checkNotEquals(institution, institutionTest);
    institution.setNodeId(null);
    checkNotEquals(institution, institutionTest);
    institutionTest.setNodeId(null);

    assertEquals(institution, institutionTest);
    assertEquals(institutionTest, institution);

    institutionTest.setName("Some description");
    assertNotEquals(institution, institutionTest);
    institutionTest.setName(null);
    checkNotEquals(institutionTest, institution);
    institutionTest.setName(institution.getName());

    institutionTest.setCampus("title");
    assertNotEquals(institution, institutionTest);
    institutionTest.setCampus(null);
    checkNotEquals(institution, institutionTest);
    institutionTest.setCampus(institution.getCampus());

    institutionTest.setState(null);
    ;
    checkNotEquals(institution, institutionTest);
    institutionTest.setState(institution.getState());

    institutionTest.setCountry(null);
    checkNotEquals(institution, institutionTest);
    institutionTest.setCountry(institution.getCountry());
  }
}
