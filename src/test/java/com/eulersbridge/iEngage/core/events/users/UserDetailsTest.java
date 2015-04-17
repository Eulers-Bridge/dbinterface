package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UserDetailsTest {
    final String email = new String("yikaig@gmail.com");
    final Long userId=1l;
    final String givenName = new String("Yikai");
    final String familyName = new String("Gong");
    final String gender = new String("male");
    final String nationality = new String("China");
    final String yearOfBirth = new String("1989");
    final String password = new String("password");
    final Boolean accountVerified = true;
    final Long institutionId = new Long(1);
    UserDetails userDetails = null;

    final String email1 = new String("test@gmail.com");
    UserDetails userDetails1 = null;


    @Before
    public void setUp() throws Exception {
        userDetails = new UserDetails(email);
        userDetails.setGivenName(givenName);
        userDetails.setFamilyName(familyName);
        userDetails.setGender(gender);
        userDetails.setNationality(nationality);
        userDetails.setYearOfBirth(yearOfBirth);
        userDetails.setPassword(password);
        userDetails.setInstitutionId(institutionId);
        userDetails.setNodeId(userId);
        userDetails.setAccountVerified(accountVerified);

        userDetails1 = new UserDetails(email1);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUserDetails() throws Exception {
        assertNotNull("userDetails is null", userDetails);
        assertNotNull("userDetails is null", userDetails1);
    }

    @Test
    public void testGetFamilyName() throws Exception {
        assertEquals("familyName does not match", familyName, userDetails.getFamilyName());
    }

    @Test
    public void testSetFamilyName() throws Exception {
        String familyName1 = new String("Smith");
        userDetails1.setFamilyName(familyName1);
        assertEquals("familyName does not match", familyName1, userDetails1.getFamilyName());
    }

    @Test
    public void testGetGender() throws Exception {
        assertEquals("gender does not match", gender, userDetails.getGender());
    }

    @Test
    public void testSetGender() throws Exception {
        String gender1 = new String("female");
        userDetails1.setGender(gender1);
        assertEquals("gender does not match", gender1, userDetails1.getGender());
    }

    @Test
    public void testGetNationality() throws Exception {
        assertEquals("Nationality does not match", nationality, userDetails.getNationality());
    }

    @Test
    public void testSetNationality() throws Exception {
        String nationality1 = new String("France");
        userDetails1.setNationality(nationality1);
        assertEquals("Nationality does not match", nationality1, userDetails1.getNationality());
    }

    @Test
    public void testGetYearOfBirth() throws Exception {
        assertEquals("yearOfBirth does not match", yearOfBirth, userDetails.getYearOfBirth());
    }

    @Test
    public void testSetYearOfBirth() throws Exception {
        String yearOfBirth1 = new String("1990");
        userDetails1.setYearOfBirth(yearOfBirth1);
        assertEquals("yearOfBirth does not match", yearOfBirth1, userDetails1.getYearOfBirth());
    }

    @Test
    public void testGetPassword() throws Exception {
        assertEquals("password does not match", password, userDetails.getPassword());
    }

    @Test
    public void testSetPassword() throws Exception {
        String password1 = new String("111111");
        userDetails1.setPassword(password1);
        assertEquals("password does not match", password1, userDetails1.getPassword());
    }

    @Test
    public void testGetInstitutionId() throws Exception {
        assertEquals("InstitutionId does not match", institutionId, userDetails.getInstitutionId());
    }

    @Test
    public void testSetInstitutionId() throws Exception {
        Long institutionId1 = new Long(2);
        userDetails1.setInstitutionId(institutionId1);
        assertEquals("InstitutionId does not match", institutionId1, userDetails1.getInstitutionId());
    }

    @Test
    public void testGetNodeId() throws Exception
    {
        assertEquals("NodeId does not match", userId, userDetails.getNodeId());
    }

    @Test
    public void testSetNodeId() throws Exception
    {
        Long userId1 = new Long(2);
        userDetails1.setNodeId(userId1);
        assertEquals("UserId does not match", userId1, userDetails1.getNodeId());
    }

    @Test
    public void testSetEmail() throws Exception {
        String email2 = new String("test2@gmail.com");
        userDetails1.setEmail(email2);
        assertEquals("email does not match", email2, userDetails1.getEmail());
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, userDetails.getEmail());
    }

    @Test
    public void testGetGivenName() throws Exception {
        assertEquals("givenName does not match", givenName, userDetails.getGivenName());
    }

    @Test
    public void testSetGivenName() throws Exception {
        String givenName1 = new String("Joe");
        userDetails1.setGivenName(givenName1);
        assertEquals("givenName does not match", givenName1, userDetails1.getGivenName());
    }

    @Test
    public void testIsAccountVerified() throws Exception {
        assertTrue("accountVerified is not false", userDetails.isAccountVerified());
    }

    @Test
    public void testSetAccountVerified() throws Exception {
        userDetails.setAccountVerified(accountVerified);
        assertEquals("accountVerified does not match", accountVerified, userDetails.isAccountVerified());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("toString() returns null", userDetails.toString());
    }
    
    @Test
    public void testEqualsAndHashcode() throws Exception
    {
    	assertEquals("",userDetails,userDetails);
    	assertNotEquals("",userDetails,null);
    	String string1="";
    	assertNotEquals("",userDetails,string1);
    	assertNotEquals("",userDetails,userDetails1);
    	userDetails1.setEmail(email);
    	assertNotEquals("",userDetails,userDetails1);
    	assertNotEquals("",userDetails.hashCode(),userDetails1.hashCode());
    	userDetails1.setEmail(null);
    	assertNotEquals("",userDetails,userDetails1);
    	assertNotEquals("",userDetails.hashCode(),userDetails1.hashCode());
    	UserDetails userDetails2=new UserDetails();
    	assertEquals("",userDetails2,userDetails1);
    	assertEquals("",userDetails2.hashCode(),userDetails1.hashCode());
    	userDetails2=new UserDetails(null);
        userDetails2.setGivenName(givenName);
        userDetails2.setFamilyName(familyName);
        userDetails2.setGender(gender);
        userDetails2.setNationality(nationality);
        userDetails2.setYearOfBirth(yearOfBirth);
        userDetails2.setPassword(password);
        userDetails2.setInstitutionId(institutionId);
        assertNotEquals("",userDetails2,userDetails);
        assertNotEquals("",userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setEmail(email);
        
        userDetails2.setAccountVerified(null);
        assertNotEquals(userDetails2, userDetails1);
        assertNotEquals(userDetails, userDetails2);
        assertNotEquals(""+userDetails2.isAccountVerified()+" "+userDetails.isAccountVerified(),userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setAccountVerified(userDetails.isAccountVerified());
        assertEquals(userDetails2, userDetails);
        assertEquals(userDetails, userDetails2);
        
        userDetails2.setFamilyName(null);
        assertNotEquals(userDetails2, userDetails);
        assertNotEquals(userDetails, userDetails2);
        assertNotEquals(""+userDetails2.isAccountVerified()+" "+userDetails.isAccountVerified(),userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setFamilyName(userDetails.getFamilyName());
        userDetails2.setGivenName(null);
        assertNotEquals(userDetails2, userDetails);
        assertNotEquals(userDetails, userDetails2);
        assertNotEquals(""+userDetails2.isAccountVerified()+" "+userDetails.isAccountVerified(),userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setGivenName(userDetails.getGivenName());
        userDetails2.setGender(null);
        assertNotEquals(userDetails2, userDetails);
        assertNotEquals(userDetails, userDetails2);
        assertNotEquals(""+userDetails2.isAccountVerified()+" "+userDetails.isAccountVerified(),userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setGender(userDetails.getGender());
        userDetails2.setNationality(null);
        assertNotEquals(userDetails2, userDetails);
        assertNotEquals(userDetails, userDetails2);
        assertNotEquals(""+userDetails2.isAccountVerified()+" "+userDetails.isAccountVerified(),userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setNationality(userDetails.getNationality());
        userDetails2.setPassword(null);
        assertNotEquals(userDetails2, userDetails);
        assertNotEquals(userDetails, userDetails2);
        assertNotEquals(""+userDetails2.isAccountVerified()+" "+userDetails.isAccountVerified(),userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setPassword(userDetails.getPassword());
        userDetails2.setInstitutionId(null);
        assertNotEquals(userDetails2, userDetails);
        assertNotEquals(userDetails, userDetails2);
        assertNotEquals(""+userDetails2.isAccountVerified()+" "+userDetails.isAccountVerified(),userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setInstitutionId(userDetails.getInstitutionId());
        userDetails2.setYearOfBirth(null);
        assertNotEquals(userDetails2, userDetails);
        assertNotEquals(userDetails, userDetails2);
        assertNotEquals(""+userDetails2.isAccountVerified()+" "+userDetails.isAccountVerified(),userDetails2.hashCode(),userDetails.hashCode());
        userDetails2.setYearOfBirth(userDetails.getYearOfBirth());
        assertEquals(userDetails2, userDetails);
    	
    }
}