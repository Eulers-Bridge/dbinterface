/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class CountryTest {
	Country country;
	final String germany="Germany";
	final String france="France";
	final Long node1=new Long(1);
	final Long node2=new Long(2);
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		country=new Country();
		country.setNodeId(node1);
		country.setCountryName(germany);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#Country()}.
	 */
	@Test
	public void testCountry() 
	{
		Country country2=new Country();
		assertNotNull("country is null",country2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#getNodeId()}.
	 */
	@Test
	public void testGetNodeId() 
	{
		assertEquals("Node id doesn't match.",country.getNodeId(),node1);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#getCountryName()}.
	 */
	@Test
	public void testGetCountryName() 
	{
		assertEquals("Node id doesn't match.",country.getCountryName(),germany);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#setCountryName(java.lang.String)}.
	 */
	@Test
	public void testSetCountryName() 
	{
		country.setCountryName(france);
		assertEquals("Node id doesn't match.",country.getCountryName(),france);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#getInstitutions()}.
	 */
	@Test
	public void testGetInstitutions() 
	{
		assertNull("Node id doesn't match.",country.getInstitutions());
	}
	
	public boolean compareInstitutions(Iterable<Institution> insts1,Iterable<Institution> insts2)
	{
		boolean result=true;
		Iterator<Institution> iter1=insts1.iterator();
		Iterator<Institution> iter2=insts2.iterator();
		Institution inst1=null,inst2=null;
		while ((iter1.hasNext())&&(iter2.hasNext()))
		{
			inst1=iter1.next();
			inst2=iter2.next();
			if (inst1!=inst2)
				break;
		}
		if ((iter1.hasNext())||(iter2.hasNext())||(inst1!=inst2))
			result=false;
		
		return result;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#setInstitutions(java.lang.String)}.
	 */
	@Test
	public void testSetInstitutions() 
	{
		ArrayList <Institution> institutions=new ArrayList<Institution>();
		institutions.add(DatabaseDataFixture.populateInstUniMelb());
		country.setInstitutions(institutions);
		assertTrue("Institutions doesn't match.",compareInstitutions(country.getInstitutions(),institutions));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#toCountryDetails()}.
	 */
	@Test
	public void testToCountryDetails() 
	{
		CountryDetails test = country.toCountryDetails();
		assertEquals("names don't match.", test.getCountryName(),country.getCountryName());
		assertEquals("id don't match.", test.getCountryId(),country.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#fromCountryDetails(com.eulersbridge.iEngage.core.events.countrys.CountryDetails)}.
	 */
	@Test
	public void testFromCountryDetails() 
	{
		CountryDetails test = country.toCountryDetails();
		Country country2=Country.fromCountryDetails(test);
		assertEquals("names don't match.", country2.getCountryName(),country.getCountryName());
		assertEquals("id don't match.", country2.getNodeId(),country.getNodeId());
	}

	
	private void checkHashCode(Country test1,Country test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(Country test1,Country test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Country countryTest=DatabaseDataFixture.populateCountryAust();
		assertEquals(countryTest.hashCode(),countryTest.hashCode());
		assertEquals(countryTest.hashCode(),country.hashCode());
		countryTest.setNodeId(null);
		checkHashCode(country,countryTest);
		country.setNodeId(null);
		
		countryTest.setCountryName(null);
		checkHashCode(country,countryTest);
		countryTest.setCountryName(country.getCountryName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#equals(com.eulersbridge.iEngage.database.domain.Country)}.
	 */
	@Test
	public void testEqualsCountry() 
	{
		Country country2=new Country();
		country2.setNodeId(node1);
		country2.setCountryName(germany);
		assertEquals("Countries with idential nodeId should be equal.",country,country2);
		assertEquals("Country should be equal to itself.",country,country);
		
		country=DatabaseDataFixture.populateCountryAust();

		Country countryTest=null;
		assertNotEquals(countryTest,country);
		assertNotEquals(country,countryTest);
		String notElection="";
		assertNotEquals(country,notElection);
		countryTest=DatabaseDataFixture.populateCountryAust();
		assertEquals(countryTest,countryTest);
		assertEquals(countryTest,country);
		
		countryTest.setNodeId(54l);
		checkNotEquals(country,countryTest);
		country.setNodeId(null);
		checkNotEquals(country,countryTest);
		countryTest.setNodeId(null);
		
		assertEquals(country, countryTest);
		assertEquals(countryTest, country);
		
		countryTest.setCountryName("Some description");
		assertNotEquals(country, countryTest);
		countryTest.setCountryName(null);
		checkNotEquals(countryTest, country);
		countryTest.setCountryName(country.getCountryName());
		
		countryTest.setInstitutions(null);
		assertNotEquals(country, countryTest);
		assertNotEquals(countryTest,country);
		countryTest.setInstitutions(DatabaseDataFixture.populateInstitutions().values());
		checkNotEquals(countryTest, country);
		countryTest.setCountryName(country.getCountryName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Country#setNodeId(java.lang.Long)}.
	 */
	@Test
	public void testSetNodeId() 
	{
		country.setNodeId(node2);
		assertEquals("Node id doesn't match.",country.getNodeId(),node2);
	}
}
