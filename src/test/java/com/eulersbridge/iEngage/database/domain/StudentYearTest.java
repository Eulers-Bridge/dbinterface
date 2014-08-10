/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.studentYear.StudentYearDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.email.EmailVerificationTest;

/**
 * @author Emma
 *
 */
public class StudentYearTest 
{
	StudentYear sy;
	
    private static Logger LOG = LoggerFactory.getLogger(StudentYearTest.class);
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
		sy=DatabaseDataFixture.populateStudentYear2014();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#toDetails()}.
	 */
	@Test
	public void testToDetails() 
	{
		StudentYearDetails dets = sy.toDetails();
		if (LOG.isDebugEnabled()) LOG.debug("dets node id "+dets.getNodeId()+" sy inst id "+sy.getNodeId());
		assertEquals("NodeIds don't match",dets.getNodeId(),sy.getNodeId());
		assertEquals("Years don't match",dets.getYear(),sy.getYear());
		assertEquals("starts don't match",dets.getStart(),sy.getStart());
		assertEquals("ends don't match",dets.getEnd(),sy.getEnd());
		if (LOG.isDebugEnabled()) LOG.debug("dets inst id "+dets.getInstitutionId()+" sy inst id "+sy.getInstitution().getNodeId());
		assertEquals("InstitutionIds don't match",dets.getInstitutionId(),sy.getInstitution().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#fromDetails(com.eulersbridge.iEngage.core.events.studentYear.StudentYearDetails)}.
	 */
	@Test
	public void testFromDetails() 
	{
		StudentYearDetails dets = sy.toDetails();
		StudentYear sy2=StudentYear.fromDetails(dets);
		assertEquals("NodeIds don't match",dets.getNodeId(),sy2.getNodeId());
		assertEquals("Years don't match",dets.getYear(),sy2.getYear());
		assertEquals("starts don't match",dets.getStart(),sy2.getStart());
		assertEquals("ends don't match",dets.getEnd(),sy2.getEnd());
		if (LOG.isDebugEnabled()) LOG.debug("dets inst id "+dets.getInstitutionId()+" sy inst id "+sy.getInstitution().getNodeId());
		assertEquals("InstitutionIds don't match",dets.getInstitutionId(),sy2.getInstitution().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#getNodeId()}.
	 */
	@Test
	public void testGetNodeId() 
	{
		assertEquals("",sy.getNodeId(),new Long(1));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#setNodeId(java.lang.Long)}.
	 */
	@Test
	public void testSetNodeId() 
	{
		sy.setNodeId(new Long(3));
		assertEquals("",sy.getNodeId(),new Long(3));
		sy.setNodeId(new Long(1));
		assertEquals("",sy.getNodeId(),new Long(1));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#getYear()}.
	 */
	@Test
	public void testGetYear() 
	{
		assertEquals("",sy.getYear(),"2014");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#setYear(java.lang.String)}.
	 */
	@Test
	public void testSetYear() 
	{
		sy.setYear("2013");
		assertEquals("",sy.getYear(),"2013");
		sy.setYear("2014");
		assertEquals("",sy.getYear(),"2014");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#getStart()}.
	 */
	@Test
	public void testGetStart() 
	{
		Long start=sy.getStart();
		assertEquals("",sy.getStart(),start);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#setStart(java.lang.Long)}.
	 */
	@Test
	public void testSetStart() {
		Long start=sy.getStart();
		sy.setStart(new Long(12347));
		assertEquals("",sy.getStart(),new Long(12347));
		sy.setStart(new Long(12345));
		assertEquals("",sy.getStart(),new Long(12345));
		sy.setStart(start);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#getEnd()}.
	 */
	@Test
	public void testGetEnd() {
		Long end=sy.getEnd();
		assertEquals("",sy.getEnd(),end);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#setEnd(java.lang.Long)}.
	 */
	@Test
	public void testSetEnd() {
		Long end=sy.getEnd();
		sy.setEnd(new Long(12347));
		assertEquals("",sy.getEnd(),new Long(12347));
		sy.setEnd(new Long(12345));
		assertEquals("",sy.getEnd(),new Long(12345));
		sy.setEnd(end);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#getInstitution()}.
	 */
	@Test
	public void testGetInstitution() 
	{
		Institution inst=sy.getInstitution(),ddfinst=DatabaseDataFixture.populateStudentYear2014().getInstitution();
		assertEquals(inst.getName(),ddfinst.getName());
		assertEquals(inst.getCampus(),ddfinst.getCampus());
		assertEquals(inst.getState(),ddfinst.getState());
		assertEquals(inst.getCountry().getCountryName(),ddfinst.getCountry().getCountryName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#setInstitution(com.eulersbridge.iEngage.database.domain.Institution)}.
	 */
	@Test
	public void testSetInstitution() 
	{
		Institution inst=sy.getInstitution();
		String name="UCLA",campus="LA",state="California";
		Institution inst2=DatabaseDataFixture.populateInst(name,campus,state , DatabaseDataFixture.populateCountryAust());
		sy.setInstitution(inst2);
		assertEquals("",inst2.getName(),name);
		assertEquals("",inst2.getCampus(),campus);
		assertEquals("",inst2.getState(),state);
		assertEquals("",inst2.getCountry().getCountryName(),"Australia");
		sy.setInstitution(inst);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#getNews()}.
	 */
	@Test
	public void testGetNews() 
	{
		Set<NewsArticle> news;
		news=sy.getNews();
		assertNull(news);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#setNews(java.util.Set)}.
	 */
	@Test
	public void testSetNews() 
	{
		Set<NewsArticle> news=sy.getNews();
		sy.setNews(null);
		assertNull(sy.getNews());
		sy.setNews(news);
		assertEquals(sy.getNews(),news);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.StudentYear#toString()}.
	 */
	@Test
	public void testToString() 
	{
		assertNotNull(sy.toString());
	}

}
