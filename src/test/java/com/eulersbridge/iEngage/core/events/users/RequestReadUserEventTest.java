package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class RequestReadUserEventTest {
    final String email = new String("yikaig@gmail.com");
    RequestReadUserEvent requestReadUserEvent = null;

    @Before
    public void setUp() throws Exception {
        requestReadUserEvent = new RequestReadUserEvent(email);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestReadUserEvent() throws Exception {
        assertNotNull("requestReadUserEvent is null", requestReadUserEvent);
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, requestReadUserEvent.getEmail());
    }
}