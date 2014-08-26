/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;

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
