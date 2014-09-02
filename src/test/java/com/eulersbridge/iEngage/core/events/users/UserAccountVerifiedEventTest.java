package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UserAccountVerifiedEventTest {
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

    UserAccountVerifiedEvent userAccountVerifiedEvent = null;
    UserAccountVerifiedEvent.VerificationErrorType verificationErrorType =
            UserAccountVerifiedEvent.VerificationErrorType.tokenExpired;

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
        userAccountVerifiedEvent = new UserAccountVerifiedEvent(email, userDetails, accountVerified);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUserAccountVerifiedEvent() throws Exception {
        assertNotNull("userAccountVerifiedEvent is null", userAccountVerifiedEvent);
        UserAccountVerifiedEvent userAccountVerifiedEvent1 = new UserAccountVerifiedEvent(email);
        UserAccountVerifiedEvent userAccountVerifiedEvent2 =new UserAccountVerifiedEvent(email, userDetails);
        assertNotNull("userAccountVerifiedEvent is null", userAccountVerifiedEvent1);
        assertNotNull("userAccountVerifiedEvent is null", userAccountVerifiedEvent2);
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, userAccountVerifiedEvent.getEmail());
    }

    @Test
    public void testGetUserDetails() throws Exception {
        assertEquals("userDetail does not match", userDetails, userAccountVerifiedEvent.getUserDetails());
    }

    @Test
    public void testIsAccountVerified() throws Exception {

        UserAccountVerifiedEvent userAccountVerifiedEvent1 = new UserAccountVerifiedEvent(email);
        UserAccountVerifiedEvent userAccountVerifiedEvent2 =new UserAccountVerifiedEvent(email, userDetails);
        assertTrue("accountVerified is not true", userAccountVerifiedEvent.isAccountVerified());
        assertFalse("accountVerified is not false", userAccountVerifiedEvent1.isAccountVerified());
        assertFalse("accountVerified is not false", userAccountVerifiedEvent2.isAccountVerified());
    }

    @Test
    public void testGetVerificationError() throws Exception {
        UserAccountVerifiedEvent userAccountVerifiedEvent1 = new UserAccountVerifiedEvent(email, userDetails);
        userAccountVerifiedEvent1.setVerificationError(verificationErrorType);
        assertEquals("VerificationError does not match", verificationErrorType.toString(), userAccountVerifiedEvent1.getVerificationError());
    }

    @Test
    public void testSetVerificationError() throws Exception {
        UserAccountVerifiedEvent userAccountVerifiedEvent1 = new UserAccountVerifiedEvent(email, userDetails);
        UserAccountVerifiedEvent userAccountVerifiedEvent2 = new UserAccountVerifiedEvent(email, userDetails);
        UserAccountVerifiedEvent userAccountVerifiedEvent3 = new UserAccountVerifiedEvent(email, userDetails);
        userAccountVerifiedEvent1.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed);
        userAccountVerifiedEvent2.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed);
        userAccountVerifiedEvent3.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenDoesntExists);
        assertEquals("VerificationError does not match", userAccountVerifiedEvent1.getVerificationError(), userAccountVerifiedEvent2.getVerificationError());
        assertNotEquals("Different VerificationError matches", userAccountVerifiedEvent1.getVerificationError(), userAccountVerifiedEvent3.getVerificationError());
    }
}