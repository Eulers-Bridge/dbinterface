package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class DeleteUserDomainEventTest {
    final String email = new String("yikaig@gmail.com");
    DeleteUserEvent deleteUserEvent = null;

    @Before
    public void setUp() throws Exception {
        deleteUserEvent = new DeleteUserEvent(email);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void tearDeleteUserEvent() throws Exception {
        assertNotNull("deleteUserEvent is null", deleteUserEvent);
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("email does not match", email, deleteUserEvent.getEmail());
    }
}