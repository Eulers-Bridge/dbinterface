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
    final String firstName = new String("Yikai");
    final String lastName = new String("Gong");
    final String gender = new String("male");
    final String nationality = new String("China");
    final String yearOfBirth = new String("1989");
    final String personality = new String("Idealist");
    final String password = new String("password");
    final boolean accountVerified = true;
    final Long institutionId = new Long(1);
    UserDetails userDetails = null;

    final String email1 = new String("test@gmail.com");
    UserDetails userDetails1 = null;


    @Before
    public void setUp() throws Exception {
        userDetails = new UserDetails(email);
        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setGender(gender);
        userDetails.setNationality(nationality);
        userDetails.setYearOfBirth(yearOfBirth);
        userDetails.setPersonality(personality);
        userDetails.setPassword(password);
        userDetails.setInstitutionId(institutionId);

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
    public void testGetLastName() throws Exception {
        assertEquals("lastName does not match", lastName, userDetails.getLastName());
    }

    @Test
    public void testSetLastName() throws Exception {
        String lastName1 = new String("Smith");
        userDetails1.setLastName(lastName1);
        assertEquals("lastName does not match", lastName1, userDetails1.getLastName());
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
    public void testGetPersonality() throws Exception {
        assertEquals("personality does not match", personality, userDetails.getPersonality());
    }

    @Test
    public void testSetPersonality() throws Exception {
        String personality1 = new String ("Thinker");
        userDetails1.setPersonality(personality1);
        assertEquals("personality does not match", personality1, userDetails1.getPersonality());
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
    public void testGetFirstName() throws Exception {
        assertEquals("firstName does not match", firstName, userDetails.getFirstName());
    }

    @Test
    public void testSetFirstName() throws Exception {
        String firstName1 = new String("Joe");
        userDetails1.setFirstName(firstName1);
        assertEquals("firstName does not match", firstName1, userDetails1.getFirstName());
    }

    @Test
    public void testIsAccountVerified() throws Exception {
        assertFalse("accountVerified is not false", userDetails.isAccountVerified());
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
}