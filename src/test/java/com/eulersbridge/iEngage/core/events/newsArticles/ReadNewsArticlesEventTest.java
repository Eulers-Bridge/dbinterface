package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadNewsArticlesEventTest {
    final Long instId = new Long(1);
    final Long nfId = new Long(2);
    ReadNewsArticlesEvent readNewsArticlesEvent = null;

    @Before
    public void setUp() throws Exception {
        readNewsArticlesEvent = new ReadNewsArticlesEvent(instId);
        readNewsArticlesEvent.setNfId(nfId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadNewsArticlesEvent() throws Exception {
        assertNotNull("readNewsArticlesEvent is null", readNewsArticlesEvent);
    }

    @Test
    public void testGetInstId() throws Exception 
    {
    	Long instId2=readNewsArticlesEvent.getInstId();
        assertEquals("instId does not match", instId, instId2);
    }

    @Test
    public void testSetInstId() throws Exception {
        Long instId1 = new Long(3);
        readNewsArticlesEvent.setInstId(instId1);
        assertEquals("instId does not match", instId1, readNewsArticlesEvent.getInstId());
    }

    @Test
    public void testGetSyId() throws Exception 
    {
    	Long nfId2=readNewsArticlesEvent.getNfId();
        assertEquals("nfId does not match", nfId, nfId2);
    }

    @Test
    public void testSetNfId() throws Exception {
        Long nfId1 = new Long(4);
        readNewsArticlesEvent.setNfId(nfId1);
        assertEquals("nfId does not match", nfId1, readNewsArticlesEvent.getNfId());
    }
}