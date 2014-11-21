package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import com.eulersbridge.iEngage.email.EmailVerification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UserCreatedEventTest {
    final String email = new String("yikaig@gmail.com");
    final String givenName = new String("Yikai");
    final String familyName = new String("Gong");
    final String gender = new String("male");
    final String nationality = new String("China");
    final String yearOfBirth = new String("1989");
    final String password = new String("password");
    final Long institutionId = new Long(1);
    UserDetails userDetails = null;

    UserCreatedEvent userCreatedEvent = null;
    EmailVerification verifyEmail = null;

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

        User user = new User(email, givenName, familyName, gender, nationality, yearOfBirth, password);
        VerificationToken token = new VerificationToken(VerificationToken.VerificationTokenType.emailRegistration , user, 10);
        verifyEmail = new EmailVerification(null,user, token);
        userCreatedEvent = new UserCreatedEvent(email, userDetails, verifyEmail);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUserCreatedEvent() throws Exception {
        assertNotEquals("userCreatedEvent is null", userCreatedEvent);
        UserCreatedEvent userCreatedEvent1 = new UserCreatedEvent(email);
        assertNotNull("userCreatedEvent is null", userCreatedEvent1);
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, userCreatedEvent.getEmail());
    }

    @Test
    public void testSetKey() throws Exception {
        String email1 = new String("test@gmail.com");
        userCreatedEvent.setKey(email1);
        assertEquals("email does not match", email1, userCreatedEvent.getEmail());
    }

    @Test
    public void testSetUserDetails() throws Exception {
        UserCreatedEvent userCreatedEvent1 = new UserCreatedEvent(email);
        userCreatedEvent1.setUserDetails(userDetails);
        assertEquals("userDetail does not match", userDetails, userCreatedEvent1.getDetails());
    }

    @Test
    public void testGetUserDetails() throws Exception {
        assertEquals("userDetail does not match", userDetails, userCreatedEvent.getDetails());
    }

    @Test
    public void testInstituteNotFound() throws Exception {
        UserCreatedEvent userCreatedEvent1 = UserCreatedEvent.instituteNotFound(email);
        assertNotNull("instituteNotFound() returns null", userCreatedEvent1);
        assertFalse("instituteFound is not false", userCreatedEvent1.isInstituteFound());
    }

    @Test
    public void testIsInstituteFound() throws Exception {
        assertTrue("instituteFound is not true", userCreatedEvent.isInstituteFound());
    }

    @Test
    public void testIsUserUnique() throws Exception {
        assertTrue("userUnique is not true", userCreatedEvent.isUserUnique());
    }

    @Test
    public void testGetVerificationEmail() throws Exception {
        assertEquals("verifyEmail does not match", verifyEmail, userCreatedEvent.getVerificationEmail());
    }

    @Test
    public void testSetVerificationEmail() throws Exception {
        User user1 = new User(email, givenName, familyName, gender, nationality, yearOfBirth, password);
        VerificationToken token1 = new VerificationToken(VerificationToken.VerificationTokenType.emailVerification , user1, 10);
        EmailVerification verifyEmail1 = new EmailVerification(null,user1, token1);
        UserCreatedEvent userCreatedEvent1 = new UserCreatedEvent(email, userDetails, verifyEmail1);
        assertEquals("verifyEmail does not match", verifyEmail1, userCreatedEvent1.getVerificationEmail());
    }

    @Test
    public void testUserNotUnique() throws Exception {
        UserCreatedEvent userCreatedEvent1 = UserCreatedEvent.userNotUnique(email);
        assertNotNull("instituteNotFound() returns null", userCreatedEvent1);
        assertFalse("userUnique is not false", userCreatedEvent1.isUserUnique());
    }
}