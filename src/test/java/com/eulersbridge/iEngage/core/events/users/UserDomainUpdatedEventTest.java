package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UserDomainUpdatedEventTest {
    final String email = new String("yikaig@gmail.com");
    final String givenName = new String("Yikai");
    final String familyName = new String("Gong");
    final String gender = new String("male");
    final String nationality = new String("China");
    final String yearOfBirth = new String("1989");
    final String password = new String("password");
    final Long institutionId = new Long(1);
    UserDetails userDetails = null;

    UserUpdatedEvent userUpdatedEvent = null;

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

        userUpdatedEvent = new UserUpdatedEvent(email, userDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUserUpdatedEvent() throws Exception {
        assertNotNull("userUpdatedEvent is null", userUpdatedEvent);
        UserUpdatedEvent userUpdatedEvent1 = new UserUpdatedEvent(email);
        assertNotNull("userUpdatedEvent is null", userUpdatedEvent1);
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, userUpdatedEvent.getEmail());
    }

    @Test
    public void testGetUserDetails() throws Exception {
        assertEquals("userDetails does not match", userDetails, userUpdatedEvent.getDetails());
    }

    @Test
    public void testEntityNotFound() throws Exception {
        UserUpdatedEvent userUpdatedEvent1 = UserUpdatedEvent.instituteNotFound(email);
        assertNotNull("userUpdatedEvent is null", userUpdatedEvent1);
        assertFalse("instituteFound is not false", userUpdatedEvent1.isEntityFound());
    }

    @Test
    public void testIsEntityFound() throws Exception {
        assertTrue("instituteFound is not true", userUpdatedEvent.isEntityFound());
    }
}