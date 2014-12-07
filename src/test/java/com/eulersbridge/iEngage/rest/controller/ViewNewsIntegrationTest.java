package com.eulersbridge.iEngage.rest.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleLikedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUnlikedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UnlikeNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;
import com.eulersbridge.iEngage.core.services.NewsService;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

public class ViewNewsIntegrationTest {

    private static Logger LOG = LoggerFactory.getLogger(ViewNewsIntegrationTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.NEWS_ARTICLE_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	NewsController controller;
	
	@Mock
	NewsService newsService;
	
	@Before
	public void setUp() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	@Test
	public final void testAlterNewsArticle() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingAlterNewsArticle()");
		Long id=1L;
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		dets.setTitle("Test Article2");
		dets.setDate(123456l);
		NewsArticleUpdatedEvent testData=new NewsArticleUpdatedEvent(id, dets);
		String content="{\"articleId\":1,\"institutionId\":1,\"title\":\"Test Article2\",\"content\":\"Contents of the Test Article\",\"picture\":null,\"likes\":null,\"date\":"+dets.getDate()+",\"creatorEmail\":\"gnewitt@hotmail.com\"}";
		String returnedContent="{\"articleId\":1,\"institutionId\":1,\"title\":\"Test Article2\",\"content\":\"Contents of the Test Article\",\"picture\":[\"http://localhost:8080/testPictures/picture2.jpg\",\"http://localhost:8080/testPictures/picture.jpg\"],\"likes\":0,\"date\":"+dets.getDate()+",\"creatorEmail\":\"gnewitt@hotmail.com\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/newsArticle/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/newsArticle/1/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/newsArticle/1/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/newsArticle/1/likedBy/USERID\"},{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/newsArticle/1/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/newsArticle/1/likes\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/newsArticles\"}]}";
		when (newsService.updateNewsArticle(any(UpdateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.articleId",is(dets.getNewsArticleId().intValue())))
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.content",is(dets.getContent())))
		.andExpect(jsonPath("$.date",is(dets.getDate().intValue())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.creatorEmail",is(dets.getCreatorEmail())))
		.andExpect(jsonPath("$.likes",is(dets.getLikes())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testAlterNewsArticleNullEventReturned() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingAlterNewsArticle()");
		Long id=1L;
		String content="{\"articleId\":1,\"institutionId\":1,\"title\":\"Test Article\",\"content\":\"Contents of the Test Article\",\"picture\":null,\"likes\":null,\"date\":1234567,\"creatorEmail\":\"gnewitt@hotmail.com\"}";
		when (newsService.updateNewsArticle(any(UpdateNewsArticleEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testAlterNewsArticleBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingAlterNewsArticle()");
		Long id=1L;
		String content="{\"articleId\":1,\"institutionId1\":1,\"title\":\"Test Article\",\"content\":\"Contents of the Test Article\",\"picture\":null,\"likers\":null,\"date\":1234567,\"creatorEmail\":\"gnewitt@hotmail.com\"}";
		when (newsService.updateNewsArticle(any(UpdateNewsArticleEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testAlterNewsArticleEmptyContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingAlterNewsArticle()");
		Long id=1L;
		when (newsService.updateNewsArticle(any(UpdateNewsArticleEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())	;		
	}


	@Test
	public final void testLikeArticle()  throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikeNewsArticle()");
		Long id=1L;
		String email="gnewitt@hotmail.com";
		NewsArticleLikedEvent testData=new NewsArticleLikedEvent(id, email,true);
		when (newsService.likeNewsArticle(any(LikeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{email}/",id.intValue(),email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testLikeArticleItemNotFound()  throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikeNewsArticle()");
		Long id=1L;
		String email="gnewitt@hotmail.com";
		NewsArticleLikedEvent testData=NewsArticleLikedEvent.articleNotFound(id, email);
		when (newsService.likeNewsArticle(any(LikeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{email}/",id.intValue(),email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isGone())	;		
	}

	@Test
	public final void testLikeArticleUserNotFound()  throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikeNewsArticle()");
		Long id=1L;
		String email="gnewitt@hotmail.com";
		NewsArticleLikedEvent testData=NewsArticleLikedEvent.userNotFound(id, email);
		when (newsService.likeNewsArticle(any(LikeEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/likedBy/{email}/",id.intValue(),email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testUnlikeArticle()  throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUnLikeNewsArticle()");
		Long id=1L;
		String email="gnewitt@hotmail.com";
		NewsArticleUnlikedEvent testData=new NewsArticleUnlikedEvent(id, email,true);
		when (newsService.unlikeNewsArticle(any(UnlikeNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{email}/",id.intValue(),email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("true"))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testUnlikeArticleItemNotFound()  throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUnLikeNewsArticle()");
		Long id=1L;
		String email="gnewitt@hotmail.com";
		NewsArticleUnlikedEvent testData=NewsArticleUnlikedEvent.articleNotFound(id, email);
		when (newsService.unlikeNewsArticle(any(UnlikeNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{email}/",id.intValue(),email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isGone())	;		
	}

	@Test
	public final void testUnlikeArticleUserNotFound()  throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingUnLikeNewsArticle()");
		Long id=1L;
		String email="gnewitt@hotmail.com";
		NewsArticleUnlikedEvent testData=NewsArticleUnlikedEvent.userNotFound(id, email);
		when (newsService.unlikeNewsArticle(any(UnlikeNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(put(urlPrefix+"/{id}/unlikedBy/{email}/",id.intValue(),email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testFindArticle() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindArticle()");
		Long newsArticleId=1l;
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		ReadNewsArticleEvent testData=new ReadNewsArticleEvent(newsArticleId, dets);
		when (newsService.requestReadNewsArticle(any(RequestReadNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{newsArticleId}/",newsArticleId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.date",is(dets.getDate())))
		.andExpect(jsonPath("$.creatorEmail",is(dets.getCreatorEmail())))
		.andExpect(jsonPath("$.content",is(dets.getContent())))
		.andExpect(jsonPath("$.articleId",is(dets.getNewsArticleId().intValue())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
//TODO
/*		.andExpect(jsonPath("$.picture",is(dets.getPicture())))
		.andExpect(jsonPath("$.likers",is(dets.getLikers())))
*/		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindArticleNoArticle() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindArticle()");
		Long newsArticleId=5l;
		ReadEvent testData=ReadNewsArticleEvent.notFound(newsArticleId);
		when (newsService.requestReadNewsArticle(any(RequestReadNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{newsArticleId}/",newsArticleId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteNewsArticle() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		NewsArticleDeletedEvent testData=new NewsArticleDeletedEvent(dets.getNewsArticleId());
		when (newsService.deleteNewsArticle(any(DeleteNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{newsArticleId}/",dets.getNewsArticleId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("true"))
//TODO This should not be returning boolean.
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testDeleteNewsArticleNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteArticle()");
		Long newsArticleId=1l;
		DeletedEvent testData=NewsArticleDeletedEvent.notFound(newsArticleId);
		when (newsService.deleteNewsArticle(any(DeleteNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{newsArticleId}/",newsArticleId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("false"))
//TODO This should not be returning boolean.
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testDeleteNewsArticleForbidden() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		DeletedEvent testData=NewsArticleDeletedEvent.deletionForbidden(dets.getNewsArticleId());
		when (newsService.deleteNewsArticle(any(DeleteNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{newsArticleId}/",dets.getNewsArticleId().intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("false"))
//TODO This should not be returning boolean.
		.andExpect(status().isGone())	;
	}

	@Test
	public final void testCreateNewsArticle() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		Long id=1L;
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		NewsArticleCreatedEvent testData=new NewsArticleCreatedEvent(id, dets);
		String content="{\"institutionId\":1,\"title\":\"Test Article\",\"content\":\"Contents of the Test Article\",\"picture\":null,\"likes\":null,\"date\":"+dets.getDate()+",\"creatorEmail\":\"gnewitt@hotmail.com\"}";
		String returnedContent="{\"articleId\":1,\"institutionId\":1,\"title\":\"Test Article\",\"content\":\"Contents of the Test Article\",\"picture\":[\"http://localhost:8080/testPictures/picture2.jpg\",\"http://localhost:8080/testPictures/picture.jpg\"],\"likes\":0,\"date\":"+dets.getDate()+",\"creatorEmail\":\"gnewitt@hotmail.com\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/newsArticle/1\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/newsArticle/1/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/newsArticle/1/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/newsArticle/1/likedBy/USERID\"},{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/newsArticle/1/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/newsArticle/1/likes\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/newsArticles\"}]}";
		when (newsService.createNewsArticle(any(CreateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(jsonPath("$.articleId",is(dets.getNewsArticleId().intValue())))
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.date",is(dets.getDate())))
		.andExpect(jsonPath("$.creatorEmail",is(dets.getCreatorEmail())))
		.andExpect(jsonPath("$.content",is(dets.getContent())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
//TODO
//		.andExpect(jsonPath("$.picture",is(dets.getPicture())))
		.andExpect(jsonPath("$.likes",is(dets.getLikes())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated())	;		
	}

	@Test
	public final void testCreateNewsArticleInvalidContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		NewsArticleCreatedEvent testData=null;
		String content="{\"institutionId1\":1,\"title\":\"Test Article\",\"content\":\"Contents of the Test Article\",\"picture\":null,\"likes\":null,\"date\":12345678,\"creatorEmail\":\"gnewitt@hotmail.com\"}";
		when (newsService.createNewsArticle(any(CreateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateNewsArticleNoContent() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		NewsArticleCreatedEvent testData=null;
		when (newsService.createNewsArticle(any(CreateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateNewsArticleInstNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		NewsArticleCreatedEvent testData=NewsArticleCreatedEvent.institutionNotFound();
		String content="{\"institutionId\":56,\"title\":\"Test Article\",\"content\":\"Contents of the Test Article\",\"picture\":null,\"likes\":null,\"date\":"+dets.getDate()+",\"creatorEmail\":\"gnewitt@hotmail.com\"}";
		when (newsService.createNewsArticle(any(CreateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateNewsArticleCreatorNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		NewsArticleCreatedEvent testData=NewsArticleCreatedEvent.creatorNotFound();
		String content="{\"institutionId\":56,\"title\":\"Test Article\",\"content\":\"Contents of the Test Article\",\"picture\":null,\"likes\":null,\"date\":"+dets.getDate()+",\"creatorEmail\":\"gnewittt@hotmail.com\"}";
		when (newsService.createNewsArticle(any(CreateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testCreateNewsArticleNullIdReturned() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		NewsArticleCreatedEvent testData=new NewsArticleCreatedEvent(null, dets);
		String content="{\"institutionId\":56,\"title\":\"Test Article\",\"content\":\"Contents of the Test Article\",\"picture\":null,\"likes\":null,\"date\":"+dets.getDate()+",\"creatorEmail\":\"gnewittt@hotmail.com\"}";
		when (newsService.createNewsArticle(any(CreateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public final void testFindArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindArticles()");
		Long instId=1l;
		Map<Long, NewsArticle> dets=DatabaseDataFixture.populateNewsArticles();
		Collection<NewsArticle> articles=dets.values();
		Iterator<NewsArticle> iter=articles.iterator();
		ArrayList<NewsArticleDetails> artDets=new ArrayList<NewsArticleDetails>(); 
		while (iter.hasNext())
		{
			NewsArticle article=iter.next();
			artDets.add(article.toNewsArticleDetails());
		}
		NewsArticlesReadEvent testData=new NewsArticlesReadEvent(instId,artDets,15l,2);
		when (newsService.readNewsArticles(any(ReadNewsArticlesEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$totalArticles",is(15)))
		.andExpect(jsonPath("$totalPages",is(2)))
		.andExpect(jsonPath("$articles[0].title",is(artDets.get(0).getTitle())))
		.andExpect(jsonPath("$articles[0].date",is(artDets.get(0).getDate())))
		.andExpect(jsonPath("$articles[0].creatorEmail",is(artDets.get(0).getCreatorEmail())))
		.andExpect(jsonPath("$articles[0].content",is(artDets.get(0).getContent())))
		.andExpect(jsonPath("$articles[0].articleId",is(artDets.get(0).getNewsArticleId().intValue())))
		.andExpect(jsonPath("$articles[0].institutionId",is(artDets.get(0).getInstitutionId().intValue())))
		.andExpect(jsonPath("$articles[1].title",is(artDets.get(1).getTitle())))
		.andExpect(jsonPath("$articles[1].date",is(artDets.get(1).getDate())))
		.andExpect(jsonPath("$articles[1].creatorEmail",is(artDets.get(1).getCreatorEmail())))
		.andExpect(jsonPath("$articles[1].content",is(artDets.get(1).getContent())))
		.andExpect(jsonPath("$articles[1].articleId",is(artDets.get(1).getNewsArticleId().intValue())))
		.andExpect(jsonPath("$articles[1].institutionId",is(artDets.get(1).getInstitutionId().intValue())))
//TODO
/*		.andExpect(jsonPath("$.picture",is(dets.getPicture())))
		.andExpect(jsonPath("$.likers",is(dets.getLikers())))
/		.andExpect(jsonPath("$.links[0].rel",is("self")))
*/		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindArticlesZeroArticles() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindArticles()");
		Long instId=11l;
		ArrayList<NewsArticleDetails> artDets=new ArrayList<NewsArticleDetails>(); 
		NewsArticlesReadEvent testData=new NewsArticlesReadEvent(instId,artDets);
		when (newsService.readNewsArticles(any(ReadNewsArticlesEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+""
				+ "s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindArticlesNoInst() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindArticles()");
		Long instId=11l;
		NewsArticlesReadEvent testData=NewsArticlesReadEvent.institutionNotFound();
		when (newsService.readNewsArticles(any(ReadNewsArticlesEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

}
