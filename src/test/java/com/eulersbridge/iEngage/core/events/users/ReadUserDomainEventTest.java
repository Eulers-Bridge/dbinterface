package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadUserDomainEventTest {
    final String email = new String("yikaig@gmail.com");
    final String givenName = new String("Yikai");
    final String familyName = new String("Gong");
    final String gender = new String("male");
    final String nationality = new String("China");
    final String yearOfBirth = new String("1989");
    final String password = new String("password");
    final Long institutionId = new Long(1);
    UserDetails userDetails = null;

    ReadUserEvent readUserEvent = null;

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

        readUserEvent = new ReadUserEvent(email,userDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadUserEvent() throws Exception {
        assertNotNull("readUserEvent is null", readUserEvent);
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, userDetails.getEmail());
    }

    @Test
    public void testGetReadUserDetails() throws Exception {
        assertEquals("userDetails does not match", userDetails, readUserEvent.getDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        ReadUserEvent readUserEvent1 = ReadUserEvent.notFound(email);
        assertNotNull("notFound() returns null", readUserEvent1);
        assertFalse("entityFound is not false", readUserEvent1.isEntityFound());
    }
}