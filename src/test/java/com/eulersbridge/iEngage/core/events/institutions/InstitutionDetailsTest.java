package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class InstitutionDetailsTest
{
	final Long institutionId = new Long(1);
	final Long newsFeedId = new Long(12);
	List<Long> newsFeedIds = new ArrayList<>();
	final String name = new String("University of Melbourne");
	final String campus = new String("Parkville");
	final String state = new String("Victroia");
	final String countryName = new String("Australia");
	InstitutionDetails institutionDetails = null;

	@Before
	public void setUp() throws Exception
	{
		newsFeedIds.add(institutionId);
		institutionDetails = new InstitutionDetails(institutionId);
		institutionDetails.setName(name);
		institutionDetails.setCampus(campus);
		institutionDetails.setState(state);
		institutionDetails.setCountryName(countryName);
		institutionDetails.setNewsFeedId(newsFeedId);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void testInstitutionDetails() throws Exception
	{
		InstitutionDetails institutionDetails1 = new InstitutionDetails(
				new Long(1));
		assertNotNull("InstitutionDetail is null", institutionDetails1);
	}

	@Test
	public void testGetNodeId() throws Exception
	{
		assertEquals("InstitutionId does not match", institutionId,
				institutionDetails.getNodeId());
	}

	@Test
	public void testSetNodeId() throws Exception
	{
		Long institutionId2 = new Long(2);
		institutionDetails.setNodeId(institutionId2);
		assertEquals("InstitutionId does not match", institutionId2,
				institutionDetails.getNodeId());
	}

	@Test
	public void testGetNewsFeedId() throws Exception
	{
		assertEquals("InstitutionId does not match", newsFeedId,
				institutionDetails.getNewsFeedId());
	}

	@Test
	public void testSetNewsFeedId() throws Exception
	{
		Long newsFeedId2 = new Long(2);
		List<Long> idLists = new ArrayList<>();
		idLists.add(newsFeedId2);
		institutionDetails.setNewsFeedId(newsFeedId2);
		assertEquals("newsFeedId does not match", newsFeedId2,
				institutionDetails.getNewsFeedId());
	}

	@Test
	public void testGetName() throws Exception
	{
		assertEquals("Name does not match", name, institutionDetails.getName());
	}

	@Test
	public void testSetName() throws Exception
	{
		String name2 = new String("Joe Smith");
		institutionDetails.setName(name2);
		assertEquals("name does not match", name2, institutionDetails.getName());
	}

	@Test
	public void testGetCampus() throws Exception
	{
		assertEquals("campus does not match", campus,
				institutionDetails.getCampus());
	}

	@Test
	public void testSetCampus() throws Exception
	{
		String campus2 = new String("Burnley");
		institutionDetails.setCampus(campus2);
		assertEquals("campus does not match", campus2,
				institutionDetails.getCampus());
	}

	@Test
	public void testGetState() throws Exception
	{
		assertEquals("state does not match", state,
				institutionDetails.getState());
	}

	@Test
	public void testSetState() throws Exception
	{
		String state2 = new String("Queensland");
		institutionDetails.setState(state2);
		assertEquals("state does not match", state2,
				institutionDetails.getState());
	}

	@Test
	public void testGetCountryName() throws Exception
	{
		assertEquals("CountryName does not match", countryName,
				institutionDetails.getCountryName());
	}

	@Test
	public void testSetCountryName() throws Exception
	{
		String countryName2 = new String("United Kingdom");
		institutionDetails.setCountryName(countryName2);
		assertEquals("CountryName does not match", countryName2,
				institutionDetails.getCountryName());
	}

	@Test
	public void testToString() throws Exception
	{
		assertNotNull("toString() return null", institutionDetails.toString());
		InstitutionDetails institutionDetails1 = new InstitutionDetails(
				new Long(1));
		assertNotEquals("different details toString() equals",
				institutionDetails1.toString(), institutionDetails.toString());

	}
}