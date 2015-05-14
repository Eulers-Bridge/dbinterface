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
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleLikedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.NewsService;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

public class ViewNewsIntegrationTest {

    private static Logger LOG = LoggerFactory.getLogger(ViewNewsIntegrationTest.class);
    
    private String urlPrefix=ControllerConstants.API_PREFIX+ControllerConstants.NEWS_ARTICLE_LABEL;
    
    MockMvc mockMvc;
	
	@InjectMocks
	NewsController controller;
	
	@Mock
	NewsService newsService;
	@Mock
	LikesService likesService;
	
	@Before
	public void setUp() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	String createPhotosString(Iterator <PhotoDetails> iter)
	{
		StringBuffer photoString=new StringBuffer("\"photos\":[");
		while (iter.hasNext())
		{
			PhotoDetails picDets=iter.next();
			String photoDets=PhotoControllerTest.setupContent(picDets).replace("{","{\"nodeId\":"+picDets.getNodeId()+',');
			photoString.append(photoDets+',');
		}
		int length=photoString.length();
		if (photoString.charAt(length-1)==',')
			photoString.setLength(length-1);
		photoString.append(']');
		return photoString.toString();
	}

	String setupContent(NewsArticleDetails dets)
	{
		String content= "{\"institutionId\":"+dets.getInstitutionId()+",\"title\":\""+dets.getTitle()+"\",\"content\":\""+dets.getContent()+
						"\",\"photos\":null,\"likes\":null,\"date\":"+dets.getDate()+",\"creatorEmail\":\""+dets.getCreatorEmail()+"\"}";
		return content;
	}
	
	String setupReturnedContent(NewsArticleDetails dets)
	{
		if (LOG.isDebugEnabled()) LOG.debug("Pictures -"+dets.getPhotos());
		StringBuffer returnedContent= new StringBuffer("{\"articleId\":");
		returnedContent.append(dets.getNodeId());
		returnedContent.append(",\"institutionId\":");
		returnedContent.append(dets.getInstitutionId());
		returnedContent.append(",\"title\":\"");
		returnedContent.append(dets.getTitle());
		returnedContent.append("\",\"content\":\"");
		returnedContent.append(dets.getContent());
		returnedContent.append("\",");
		returnedContent.append(createPhotosString(dets.getPhotos().iterator()));

		returnedContent.append(",\"likes\":0,\"date\":");
		returnedContent.append(dets.getDate());
		returnedContent.append(",\"creatorEmail\":\"");
		returnedContent.append(dets.getCreatorEmail());
		returnedContent.append("\",\"inappropriateContent\":");
		returnedContent.append(dets.isInappropriateContent());
		returnedContent.append(",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/newsArticle/");
		returnedContent.append(dets.getNodeId());
		returnedContent.append("\"},{\"rel\":\"Previous\",\"href\":\"http://localhost/api/newsArticle/");
		returnedContent.append(dets.getNodeId());
		returnedContent.append("/previous\"},{\"rel\":\"Next\",\"href\":\"http://localhost/api/newsArticle/");
		returnedContent.append(dets.getNodeId());
		returnedContent.append("/next\"},{\"rel\":\"Liked By\",\"href\":\"http://localhost/api/newsArticle/");
		returnedContent.append(dets.getNodeId());
		returnedContent.append("/likedBy/USERID\"},{\"rel\":\"UnLiked By\",\"href\":\"http://localhost/api/newsArticle/");
		returnedContent.append(dets.getNodeId());
		returnedContent.append("/unlikedBy/USERID\"},{\"rel\":\"Likes\",\"href\":\"http://localhost/api/newsArticle/");
		returnedContent.append(dets.getNodeId());
		returnedContent.append("/likes\"},{\"rel\":\"Read all\",\"href\":\"http://localhost/api/newsArticles\"}]}");
		return returnedContent.toString();
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
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
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
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		String content=setupContent(dets);
		when (newsService.updateNewsArticle(any(UpdateNewsArticleEvent.class))).thenReturn(null);
		this.mockMvc.perform(put(urlPrefix+"/{id}/",id.intValue()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andExpect(status().isBadRequest())	;		
	}

	@Test
	public void testAlterNewsArticleBadContent() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingAlterNewsArticle()");
		Long id=1L;
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		String content=setupContent(dets).replace("institutionId", "institutionId1");
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
	public void testLikeArticle() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=new LikedEvent(id, user.getEmail(), true);
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);

		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
        .andExpect(jsonPath("$success",is(evt.isResultSuccess())))
		.andExpect(status().isOk())	;		
	}

	@Test
	public void testLikeArticleFailed() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=new LikedEvent(id, user.getEmail(), false);
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);

		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
        .andExpect(jsonPath("$success",is(evt.isResultSuccess())))
		.andExpect(status().isOk())	;		
	}

	@Test
	public final void testLikedByArticleNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());
		
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;		
	}

	@Test
	public final void testLikedByArticleGone() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLikedByEvent()");
		Long id=1L;
		User user=DatabaseDataFixture.populateUserGnewitt();
		LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());
		
		when(likesService.like(any(LikeEvent.class))).thenReturn(evt);
		this.mockMvc.perform(put(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isGone())	;		
	}

	public final void testUnlikeArticle() throws Exception
	{
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt= new LikedEvent(id, user.getEmail(), true);

		when(likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$success",is(evt.isResultSuccess())))
                .andExpect(status().isOk())	;
	}

	@Test
	public final void testUnlikeArticleFailed() throws Exception
	{
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt= new LikedEvent(id, user.getEmail(), false);

		when(likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$success",is(evt.isResultSuccess())))
                .andExpect(status().isOk())	;
	}

    @Test
    public final void testUnLikedByArticleNotFound() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.userNotFound(id,  user.getEmail());

        when (likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())	;
    }

    @Test
    public final void testUnLikedByArticleGone() throws Exception
    {
        if (LOG.isDebugEnabled()) LOG.debug("performingUnLikedByEvent()");
        Long id=1L;
        User user=DatabaseDataFixture.populateUserGnewitt();
        LikedEvent evt=LikedEvent.entityNotFound(id, user.getEmail());

        when (likesService.unlike(any(LikeEvent.class))).thenReturn(evt);
        this.mockMvc.perform(delete(urlPrefix+"/{id}"+ControllerConstants.LIKED_BY_LABEL+"/{userId}/",id.intValue(),user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isGone())	;
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
/*		.andExpect(jsonPath("$.photos",is(dets.getPhotos())))
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
		.andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
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
		.andExpect(content().string("{\"success\":false,\"errorReason\":\"Not found\",\"responseObject\":null}"))
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
		.andExpect(content().string("{\"success\":false,\"errorReason\":\"Could not delete\",\"responseObject\":null}"))
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
		String content=setupContent(dets);
		String returnedContent=setupReturnedContent(dets);
		when (newsService.createNewsArticle(any(CreateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.articleId",is(dets.getNewsArticleId().intValue())))
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.date",is(dets.getDate())))
		.andExpect(jsonPath("$.creatorEmail",is(dets.getCreatorEmail())))
		.andExpect(jsonPath("$.content",is(dets.getContent())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
//TODO
//		.andExpect(jsonPath("$.photos",is(dets.getPhotos())))
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
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		String content=setupContent(dets).replace("institutionId", "institutionId1");
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
		String content=setupContent(dets);
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
		String content=setupContent(dets);
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
		String content=setupContent(dets);
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
		Long numElements=(long) artDets.size();
		Integer numPages= (int) ((numElements/10)+1);
		NewsArticlesReadEvent testData=new NewsArticlesReadEvent(instId,artDets,numElements,numPages);
		when (newsService.readNewsArticles(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$totalElements",is(numElements.intValue())))
		.andExpect(jsonPath("$totalPages",is(numPages)))
		.andExpect(jsonPath("$foundObjects[0].title",is(artDets.get(0).getTitle())))
		.andExpect(jsonPath("$foundObjects[0].date",is(artDets.get(0).getDate())))
		.andExpect(jsonPath("$foundObjects[0].creatorEmail",is(artDets.get(0).getCreatorEmail())))
		.andExpect(jsonPath("$foundObjects[0].content",is(artDets.get(0).getContent())))
		.andExpect(jsonPath("$foundObjects[0].articleId",is(artDets.get(0).getNewsArticleId().intValue())))
		.andExpect(jsonPath("$foundObjects[0].institutionId",is(artDets.get(0).getInstitutionId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].title",is(artDets.get(1).getTitle())))
		.andExpect(jsonPath("$foundObjects[1].date",is(artDets.get(1).getDate())))
		.andExpect(jsonPath("$foundObjects[1].creatorEmail",is(artDets.get(1).getCreatorEmail())))
		.andExpect(jsonPath("$foundObjects[1].content",is(artDets.get(1).getContent())))
		.andExpect(jsonPath("$foundObjects[1].articleId",is(artDets.get(1).getNewsArticleId().intValue())))
		.andExpect(jsonPath("$foundObjects[1].institutionId",is(artDets.get(1).getInstitutionId().intValue())))
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
		when (newsService.readNewsArticles(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
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
		when (newsService.readNewsArticles(any(ReadAllEvent.class),any(Direction.class),any(int.class),any(int.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"s/{instId}/",instId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

}
