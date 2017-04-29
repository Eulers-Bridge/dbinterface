package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class VerifyUserAccountEventTest {
    final String email = new String("yikaig@gmail.com");
    final String token = new String("token00");
    VerifyUserAccountEvent verifyUserAccountEvent = null;

    @Before
    public void setUp() throws Exception {
        verifyUserAccountEvent = new VerifyUserAccountEvent(email, token);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testVerifyUserAccountEvent() throws Exception {
        assertNotNull("verifyUserAccountEvent is null", verifyUserAccountEvent);
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, verifyUserAccountEvent.getEmail());
    }

    @Test
    public void testGetToken() throws Exception {
        assertEquals("token does not match", token, verifyUserAccountEvent.getToken());
    }
}