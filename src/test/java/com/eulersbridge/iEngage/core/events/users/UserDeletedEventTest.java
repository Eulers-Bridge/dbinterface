package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UserDeletedEventTest {
    final String email = new String("yikaig@gmail.com");
    final String givenName = new String("Yikai");
    final String familyName = new String("Gong");
    final String gender = new String("male");
    final String nationality = new String("China");
    final String yearOfBirth = new String("1989");
    final String personality = new String("Idealist");
    final String password = new String("password");
    final boolean accountVerified = true;
    final Long institutionId = new Long(1);
    UserDetails userDetails = null;

    UserDeletedEvent userDeletedEvent = null;

    @Before
    public void setUp() throws Exception {
        userDetails = new UserDetails(email);
        userDetails.setGivenName(givenName);
        userDetails.setFamilyName(familyName);
        userDetails.setGender(gender);
        userDetails.setNationality(nationality);
        userDetails.setYearOfBirth(yearOfBirth);
        userDetails.setPersonality(personality);
        userDetails.setPassword(password);
        userDetails.setInstitutionId(institutionId);

        userDeletedEvent = new UserDeletedEvent(email, userDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUserDeletedEvent() throws Exception {
        assertNotNull("userDeletedEvent is null", userDeletedEvent);
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, userDeletedEvent.getEmail());
    }

    @Test
    public void testGetDetails() throws Exception {
        assertEquals("userDetails does not match", userDetails, userDeletedEvent.getDetails());
    }

    @Test
    public void testIsDeletionCompleted() throws Exception {
        assertTrue("deletionCompleted is not true", userDeletedEvent.isDeletionCompleted());
    }

    @Test
    public void testDeletionForbidden() throws Exception {
        UserDeletedEvent userDeletedEvent1 = UserDeletedEvent.deletionForbidden(email, userDetails);
        assertNotNull("userDeletedEvent is null", userDeletedEvent1);
        assertFalse("deletionCompleted is not false", userDeletedEvent1.isDeletionCompleted());
    }

    @Test
    public void testNotFound() throws Exception {
        UserDeletedEvent userDeletedEvent1 = UserDeletedEvent.notFound(email);
        assertNotNull("userDeletedEvent is null", userDeletedEvent1);
        assertFalse("entityFound is not false", userDeletedEvent1.isEntityFound());
    }
}