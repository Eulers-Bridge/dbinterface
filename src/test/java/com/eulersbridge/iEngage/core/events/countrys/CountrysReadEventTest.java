package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class CountrysReadEventTest {
    CountrysReadEvent countrysReadEvent = null;
    ArrayList<CountryDetails> countrys = null;
    CountryDetails countryDetails = null;
    CountryDetails countryDetails2 = null;
    final Long id1 = new Long(1);
    final Long id2 = new Long(2);
    final String france = "France";
    final String germany = "Germany";

    @Before
    public void setUp() throws Exception {
        countryDetails = new CountryDetails(id1);
        countryDetails.setCountryName(france);
        countryDetails2 = new CountryDetails(id2);
        countryDetails2.setCountryName(germany);
        countrys = new ArrayList<>();
        countrys.add(countryDetails);
        countrys.add(countryDetails2);
        countrysReadEvent = new CountrysReadEvent(countrys);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCountrysReadEvent() throws Exception {
        CountrysReadEvent countrysReadEvent = new CountrysReadEvent(countrys);
        assertNotNull("CountryReadEvent is null",countrysReadEvent);
    }

    @Test
    public void testGetCountrys() throws Exception {
        Iterable<CountryDetails> countrys2 = countrysReadEvent.getCountrys();
        assertEquals("country details do not match", countrys2, countrys);
    }

    @Test
    public void testSetCountrys() throws Exception {
        ArrayList<CountryDetails> countrys2 = new ArrayList<>();
        countrys2.add(countryDetails);
        countrysReadEvent.setCountrys(countrys2);
        assertEquals("country details do not match", countrys2, countrysReadEvent.getCountrys());


    }
}