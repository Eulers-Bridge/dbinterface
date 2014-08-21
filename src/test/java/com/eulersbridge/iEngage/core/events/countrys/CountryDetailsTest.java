package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.*;

import static org.junit.Assert.*;

public class CountryDetailsTest {

    CountryDetails countryDetail1;
    final Long contryId1 = new Long(1);
    final Long contryId2 = new Long(2);
    final String germany="Germany";
    final String france="France";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        countryDetail1 = new CountryDetails(contryId1);
        countryDetail1.setCountryName(germany);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCountryDetails() throws Exception{
        CountryDetails countryDetail2 = new CountryDetails(contryId2);
        assertNotNull("CountryDetail is null", countryDetail2);
    }

    @Test
    public void testGetCountryId() throws Exception {
        assertEquals("CountryId doesn't match", countryDetail1.getCountryId(), contryId1);
    }

    @Test
    public void testSetCountryId() throws Exception {
        countryDetail1.setCountryId(contryId2);
        assertEquals("CountryId doesn't match", countryDetail1.getCountryId(), contryId2);
    }

    @Test
    public void testGetCountryName() throws Exception {
        assertEquals("Country name doesn't match", countryDetail1.getCountryName(), germany);
    }

    @Test
    public void testSetCountryName() throws Exception {
        countryDetail1.setCountryName(france);
        assertEquals("Country name doesn't match", countryDetail1.getCountryName(), france);
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(countryDetail1.toString());
    }
}