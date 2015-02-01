/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
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
		User userTest=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971",  "password","0447304209");
		assertTrue("userTest not of User class",(userTest instanceof User));
		assertEquals("Emails don't match",userTest.getEmail(),"gnewitt");
		assertEquals("First names don't match",userTest.getGivenName(),"Greg");
		assertEquals("Last names don't match",userTest.getFamilyName(),"Newitt");
		assertEquals("Genders don't match",userTest.getGender(),"Male");
		assertEquals("Nationalities don't match",userTest.getNationality(),"Australian");
		assertEquals("Birth years names don't match",userTest.getYearOfBirth(),"1971");
		assertEquals("Passwords don't match",userTest.getPassword(),"password");
		assertEquals("contact numbers don't match",userTest.getContactNumber(),"0447304209");
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
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getGivenName()}.
	 */
	@Test
	public final void testGetGivenName() {
		userDetails.setGivenName("Something else");
		assertNotEquals("getGivenName() returns incorrect  value",user.getGivenName(),userDetails.getGivenName());
		userDetails.setGivenName(user.getGivenName());
		assertEquals("getEmail() returns expected value",user.getGivenName(),userDetails.getGivenName());
	}
	
	@Test
	public final void testSetGivenName()
	{
		String givenName="GenerousGeorge";
		user.setGivenName(givenName);
		assertEquals("SetGivenName not working.",user.getGivenName(),givenName);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getFamilyName()}.
	 */
	@Test
	public final void testGetFamilyName() {
		userDetails.setFamilyName("Something else");
		assertNotEquals("getFamilyName() returns incorrect  value",user.getFamilyName(),userDetails.getFamilyName());
		userDetails.setFamilyName(user.getFamilyName());
		assertEquals("getFamilyName() returns expected value",user.getFamilyName(),userDetails.getFamilyName());
	}

	@Test
	public final void testSetFamilyName()
	{
		String familyName="GenerousGeorge";
		user.setFamilyName(familyName);
		assertEquals("SetFamilyName not working.",user.getFamilyName(),familyName);
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

	@Test
	public final void testSetGender()
	{
		String gender="Female";
		user.setGender(gender);
		assertEquals("SetGivenName not working.",user.getGender(),gender);
		gender="Male";
		user.setGender(gender);
		assertEquals("SetGivenName not working.",user.getGender(),gender);
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

	@Test
	public final void testSetNationality()
	{
		String nationality="Australian";
		user.setNationality(nationality);
		assertEquals("setNationality not working.",user.getNationality(),nationality);
		nationality="British";
		user.setNationality(nationality);
		assertEquals("setNationality not working.",user.getNationality(),nationality);
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

	@Test
	public final void testSetYearOfBirth()
	{
		String yearOfBirth="1970";
		user.setYearOfBirth(yearOfBirth);
		assertEquals("setYearOfBirth not working.",user.getYearOfBirth(),yearOfBirth);
		yearOfBirth="1971";
		user.setYearOfBirth(yearOfBirth);
		assertEquals("setYearOfBirth not working.",user.getYearOfBirth(),yearOfBirth);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getYearOfBirth()}.
	 */
	@Test
	public final void testGetVerificationToken() 
	{
		Iterable<VerificationToken> token=user.getVerificationToken();
		assertNull("token not null",token);
	}

	@Test
	public final void testSetVerificationToken()
	{
		ArrayList<VerificationToken> tokens=(new ArrayList<VerificationToken>());
		user.setVerificationToken(tokens);
		assertEquals("setVerificationToken not working.",user.getVerificationToken(),tokens);
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
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getLikes()}.
	 */
	@Test
	public final void testGetLikes() 
	{
		assertNull("GetNodeId not working",user.getLikes());
		Set<Like> likes=new HashSet<Like>();
		user.setLikes(likes);
		assertEquals("GetNodeId and equals not working.",user.getLikes(),likes);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId() 
	{
		Long nodeId=948l;
		user.setNodeId(nodeId);
		assertEquals("GetNodeId not working",user.getNodeId(),nodeId);
		assertTrue("GetNodeId and equals not working.",user.getNodeId().equals(nodeId));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#getInstitution()}.
	 */
	@Test
	public final void testGetInstitution() 
	{
		assertEquals("GetInstitution not working",user.getInstitution().getNodeId(),userDetails.getInstitutionId());
		assertNull(user.getInstitution().getCampus());
		assertNull(user.getInstitution().getCountry());
		assertNull(user.getInstitution().getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#setInstitution(com.eulersbridge.iEngage.database.domain.Institution)}.
	 */
	@Test
	public final void testSetInstitution() 
	{
		Institution inst=DatabaseDataFixture.populateInstUniMelb();
		user=new User();
		user.setInstitution(inst);
		assertEquals("Institution does not match.",user.getInstitution(),inst);
		assertEquals("Campus does not match.",user.getInstitution().getCampus(),inst.getCampus());
		assertEquals("Name does not match.",user.getInstitution().getName(),inst.getName());
		assertEquals("State does not match.",user.getInstitution().getState(),inst.getState());
		assertEquals("Country does not match.",user.getInstitution().getCountry(),inst.getCountry());
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
	public final void testSetPassword() 
	{
		String password="newPassword";
		assertNotEquals("getPassword() returns non expected value",user.getPassword(),password);
		user.setPassword(password);
		assertEquals("getPassword() returns expected value",user.getPassword(),password);
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
		assertNotEquals("getGivenName() returns incorrect  value",user.isAccountVerified(),userDetails.isAccountVerified());
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
		assertEquals("FirstName must be equal",userDetails.getGivenName(), userDetails2.getGivenName());
		assertEquals("LastName must be equal",userDetails.getFamilyName(), userDetails2.getFamilyName());
		assertEquals("Gender must be equal",userDetails.getGender(), userDetails2.getGender());
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
		assertEquals("FirstName must be equal",userDetails.getGivenName(), user.getGivenName());
		assertEquals("LastName must be equal",userDetails.getFamilyName(), user.getFamilyName());
		assertEquals("Gender must be equal",userDetails.getGender(), user.getGender());
		assertEquals("Institutions must be equal",userDetails.getInstitutionId(), user.getInstitution().getNodeId());
		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.User#equals(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testUser1EqualsUser1() 
	{
		User user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		assertTrue("Different objects with same details are being declared unequal.",user1.equals(user1));
	}
	
	@Test
	public final void testUser1EqualsUser2() 
	{
		User user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		User user2=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		assertTrue("Different objects with same details are being declared unequal.",user1.equals(user2));
	}
	
	@Test
	public final void testDifferentObjectsInEqual()
	{
		User user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		Object user2=new String("gnewitt Greg Newitt Male Australian 1971 password");
		assertFalse("Different objects are being declared equal.",user1.equals(user2));
	}

	@Test
	public final void testNullObjectUnequal()
	{
		User user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		User user2=null;
		assertFalse("Null object being declared equal.",user1.equals(user2));
	}

	@Test
	public final void testDifferentObjectsUnEqual()
	{
		User user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1972", "password","0447304209");
		User user2=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared unequal.",user1.equals(user2));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password",null);
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertFalse("Different objects with different details are being declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", null,"0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertFalse("Different objects with different details are being declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password1","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt","Male", "Australian", null, "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertFalse("Different objects with different details are being declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt","Male", "British", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt","Female", "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Lawson","Male", "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Gregory","Newitt","Male", "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt2","Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt","Male", null, "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertFalse("Different objects with different details are being declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt",null, "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertFalse("Different objects with different details are being declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg",null,"Male", "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertFalse("Different objects with different details are being declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt",null,"Newitt","Male", "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertFalse("Different objects with different details are being declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User(null,"Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		assertFalse("Different objects with different details are being declared equal.",user1.equals(user2));
		assertFalse("Different objects with different details are being declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1=new User("gnewitt","Greg","Newitt","Male", "Australian", "1971", "password","0447304209");
		user1.setNodeId(1l);
		assertFalse("Different objects with different ids declared equal.",user1.equals(user2));
		assertFalse("Different objects with different ids declared equal.",user2.equals(user1));
		assertNotEquals("",user1.hashCode(),user2.hashCode());
		user1.setGivenName("Gregory");
		user2.setNodeId(1l);
		assertTrue("objects with same ids being declared unequal.",user1.equals(user2));
		assertEquals("",user1.hashCode(),user2.hashCode());
	}

}
