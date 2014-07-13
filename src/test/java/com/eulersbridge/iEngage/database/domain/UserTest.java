/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class UserTest {

	UserDetails userDetails;
	User user;
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
		userDetails=RestDataFixture.customEmailUser();
		user=User.fromUserDetails(userDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#User()}.
	 */
	@Test
	public final void testUser() 
	{
		User userTest=new User();
		assertTrue("userTest not of User class",(userTest instanceof User));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#User(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testUserStringStringStringStringStringStringStringString() 
	{
		User userTest=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "none", "password");
		assertTrue("userTest not of User class",(userTest instanceof User));
		assertEquals("Emails don't match",userTest.getEmail(),"gnewitt");
		assertEquals("First names don't match",userTest.getFirstName(),"Greg");
		assertEquals("Last names don't match",userTest.getLastName(),"Newitt");
		assertEquals("Genders don't match",userTest.getGender(),"Male");
		assertEquals("Nationalities don't match",userTest.getNationality(),"Australian");
		assertEquals("Birth years names don't match",userTest.getYearOfBirth(),"1971");
		assertEquals("Personalities don't match",userTest.getPersonality(),"none");
		assertEquals("Passwords don't match",userTest.getPassword(),"password");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getEmail()}.
	 */
	@Test
	public final void testGetEmail() 
	{
		userDetails.setEmail("Something else");
		assertNotEquals("getEmail() returns incorrect  value",user.getEmail(),userDetails.getEmail());
		userDetails.setEmail(user.getEmail());
		assertEquals("getEmail() returns expected value",user.getEmail(),userDetails.getEmail());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getFirstName()}.
	 */
	@Test
	public final void testGetFirstName() {
		userDetails.setFirstName("Something else");
		assertNotEquals("getFirstName() returns incorrect  value",user.getFirstName(),userDetails.getFirstName());
		userDetails.setFirstName(user.getFirstName());
		assertEquals("getEmail() returns expected value",user.getFirstName(),userDetails.getFirstName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getLastName()}.
	 */
	@Test
	public final void testGetLastName() {
		userDetails.setLastName("Something else");
		assertNotEquals("getLastName() returns incorrect  value",user.getLastName(),userDetails.getLastName());
		userDetails.setLastName(user.getLastName());
		assertEquals("getLastName() returns expected value",user.getLastName(),userDetails.getLastName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getGender()}.
	 */
	@Test
	public final void testGetGender() {
		userDetails.setGender("Something else");
		assertNotEquals("getGender() returns incorrect  value",user.getGender(),userDetails.getGender());
		userDetails.setGender(user.getGender());
		assertEquals("getGender() returns expected value",user.getGender(),userDetails.getGender());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getNationality()}.
	 */
	@Test
	public final void testGetNationality() {
		userDetails.setNationality("Something else");
		assertNotEquals("getNationality() returns incorrect  value",user.getNationality(),userDetails.getNationality());
		userDetails.setNationality(user.getNationality());
		assertEquals("getNationality() returns expected value",user.getNationality(),userDetails.getNationality());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getYearOfBirth()}.
	 */
	@Test
	public final void testGetYearOfBirth() {
		userDetails.setYearOfBirth("Something else");
		assertNotEquals("getYearOfBirth() returns incorrect  value",user.getYearOfBirth(),userDetails.getYearOfBirth());
		userDetails.setYearOfBirth(user.getYearOfBirth());
		assertEquals("getYearOfBirth() returns expected value",user.getYearOfBirth(),userDetails.getYearOfBirth());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getPersonality()}.
	 */
	@Test
	public final void testGetPersonality() {
		userDetails.setPersonality("Something else");
		assertNotEquals("getPersonality() returns incorrect  value",user.getPersonality(),userDetails.getPersonality());
		userDetails.setPersonality(user.getPersonality());
		assertEquals("getPersonality() returns expected value",user.getPersonality(),userDetails.getPersonality());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#comparePassword(java.lang.String)}.
	 */
	@Test
	public final void testComparePassword() 
	{
		assertFalse("comparePassword() returns false positive",user.comparePassword("Something else"));
		assertTrue("comparePassword() returns false negative",user.comparePassword(userDetails.getPassword()));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId() {
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getInstitution()}.
	 */
	@Test
	public final void testGetInstitution() {
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#setInstitution(com.eulersbridge.iEngage.database.domain.Institution)}.
	 */
	@Test
	public final void testSetInstitution() {
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getPassword()}.
	 */
	@Test
	public final void testGetPassword() {
		userDetails.setPassword("Something else");
		assertNotEquals("getPassword() returns incorrect  value",user.getPassword(),userDetails.getPassword());
		userDetails.setPassword(user.getPassword());
		assertEquals("getPassword() returns expected value",user.getPassword(),userDetails.getPassword());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#setPassword(java.lang.String)}.
	 */
	@Test
	public final void testSetPassword() {
		// Tested in above method.
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#isAccountVerified()}.
	 */
	@Test
	public final void testIsAccountVerified() {
		assertNotEquals("isAccountVerified() returns incorrect  value",user.isAccountVerified(),true);
		assertEquals("isAccountVerified() returns expected value",user.isAccountVerified(),false);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#setAccountVerified(boolean)}.
	 */
	@Test
	public final void testSetAccountVerified() {
		user.setAccountVerified(true);
		assertNotEquals("getFirstName() returns incorrect  value",user.isAccountVerified(),userDetails.isAccountVerified());
		user.setAccountVerified(false);
		assertEquals("getEmail() returns expected value",user.isAccountVerified(),userDetails.isAccountVerified());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#toUserDetails()}.
	 */
	@Test
	public final void testToUserDetails() {
		UserDetails userDetails2=user.toUserDetails();
		assertEquals("Email must be equal",userDetails.getEmail(), userDetails2.getEmail());
		assertEquals("FirstName must be equal",userDetails.getFirstName(), userDetails2.getFirstName());
		assertEquals("LastName must be equal",userDetails.getLastName(), userDetails2.getLastName());
		assertEquals("Gender must be equal",userDetails.getGender(), userDetails2.getGender());
		assertEquals("Personality must be equal",userDetails.getPersonality(), userDetails2.getPersonality());
		assertEquals("Institution Ids must be equal",userDetails.getInstitutionId(), userDetails2.getInstitutionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#fromUserDetails(com.eulersbridge.iEngage.core.events.users.UserDetails)}.
	 */
	@Test
	public final void testFromUserDetails() 
	{
		UserDetails userDetails=RestDataFixture.customEmailUser();
		User user=User.fromUserDetails(userDetails);
		assertEquals("Email must be equal",userDetails.getEmail(), user.getEmail());
		assertEquals("FirstName must be equal",userDetails.getFirstName(), user.getFirstName());
		assertEquals("LastName must be equal",userDetails.getLastName(), user.getLastName());
		assertEquals("Gender must be equal",userDetails.getGender(), user.getGender());
		assertEquals("Personality must be equal",userDetails.getPersonality(), user.getPersonality());
		assertEquals("Institutions must be equal",userDetails.getInstitutionId(), user.getInstitution().getNodeId());
		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#equals(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testEqualsUser() 
	{
		User user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "none", "password");
		User user2=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "none", "password");
		assertFalse("Different objects with same details are being declared equal.",user1.equals(user2));
	}

}
