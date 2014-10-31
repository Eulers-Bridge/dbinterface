package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.services.NewsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Yikai Gong
 */

public class NewsControllerTest {

    private static Logger LOG = LoggerFactory.getLogger(NewsControllerTest.class);

    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.NEWS_ARTICLE_LABEL;

    MockMvc mockMvc;

    @InjectMocks
    NewsController newsController;

    @Mock
    NewsService newsService;

    @Before
    public void setUp() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("setup()");
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(newsController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public final void testNewsController()
    {
        NewsController newsController1 = new NewsController();
        assertNotNull("Not yet implemented", newsController1);
    }

    @Test
    public void testAlterNewsArticle() throws Exception {

    }

    @Test
    public void testLikeArticle() throws Exception {

    }

    @Test
    public void testUnlikeArticle() throws Exception {

    }

    @Test
    public void testDeleteNewsArticle() throws Exception {

    }

    @Test
    public void testCreateNewsArticle() throws Exception {

    }

    @Test
    public void testFindArticles() throws Exception {

    }
}