package com.eulersbridge.iEngage.rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

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
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.NewsService;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import org.junit.After;
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Yikai Gong
 */

public class NewsControllerTest
{

	private static Logger LOG = LoggerFactory
			.getLogger(NewsControllerTest.class);

	private String urlPrefix = ControllerConstants.API_PREFIX
			+ ControllerConstants.NEWS_ARTICLE_LABEL;

	MockMvc mockMvc;

	@InjectMocks
	NewsController newsController;

	@Mock
	NewsService newsService;
	@Mock
	LikesService likesService;

	@Before
	public void setUp() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);

		this.mockMvc = standaloneSetup(newsController).setMessageConverters(
				new MappingJackson2HttpMessageConverter()).build();
	}
	
	static String createPhotosString(Iterator <PhotoDetails> iter)
	{
		StringBuffer photoString=new StringBuffer("[");
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
		returnedContent.append("\",\"photos\":");
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

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public final void testNewsController()
	{
		NewsController newsController1 = new NewsController();
		assertNotNull("Not yet implemented", newsController1);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ElectionController#findElection(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	public final void testFindArticle() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		ReadEvent testData=new ReadNewsArticleEvent(dets.getNodeId(), dets);
		when (newsService.requestReadNewsArticle(any(RequestReadNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{newsArticleId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.content",is(dets.getContent())))
		.andExpect(jsonPath("$.date",is(dets.getDate())))
		.andExpect(jsonPath("$.creatorEmail",is(dets.getCreatorEmail())))
		.andExpect(jsonPath("$.articleId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.institutionId",is(dets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testFindArticleNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingFindArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		ReadEvent testData=ReadNewsArticleEvent.notFound(dets.getNodeId());
		when (newsService.requestReadNewsArticle(any(RequestReadNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(get(urlPrefix+"/{articleId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound())	;
	}

	@Test
	public void testAlterNewsArticle() throws Exception
	{

	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ArticleController#likeArticle(java.lang.Long, java.lang.String)}.
	 */
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.controller.ArticleController#unlikeArticle(java.lang.Long, java.lang.String)}.
	 */
	@Test
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
	public final void testDeleteNewsArticle() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteNewsArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		NewsArticleDeletedEvent testData=new NewsArticleDeletedEvent(dets.getNodeId());
		when (newsService.deleteNewsArticle(any(DeleteNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{articleId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("{\"success\":true,\"errorReason\":null,\"responseObject\":null}"))
		.andExpect(status().isOk())	;
	}
	@Test
	public final void testDeleteNewsArticleNotFound() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteNewsArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		DeletedEvent testData=NewsArticleDeletedEvent.notFound(dets.getNodeId());
		when (newsService.deleteNewsArticle(any(DeleteNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{articleId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("{\"success\":false,\"errorReason\":\"Not found\",\"responseObject\":null}"))
		.andExpect(status().isNotFound());
	}
	@Test
	public final void testDeleteNewsArticleForbidden() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingDeleteNewsArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		DeletedEvent testData=NewsArticleDeletedEvent.deletionForbidden(dets.getNodeId());
		when (newsService.deleteNewsArticle(any(DeleteNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(delete(urlPrefix+"/{articleId}/",dets.getNodeId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("{\"success\":false,\"errorReason\":\"Could not delete\",\"responseObject\":null}"))
		.andExpect(status().isGone());
	}

	@Test
	public final void testCreateNewsArticle() throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingCreateNewsArticle()");
		NewsArticleDetails dets=DatabaseDataFixture.populateNewsArticle1().toNewsArticleDetails();
		NewsArticleCreatedEvent testData=new NewsArticleCreatedEvent(dets.getNodeId(), dets);
		String content=setupContent(dets);
		if (LOG.isDebugEnabled()) LOG.debug("content = "+content);
		String returnedContent=setupReturnedContent(dets);
		if (LOG.isDebugEnabled()) LOG.debug("returnedContent = "+returnedContent);
		if (LOG.isDebugEnabled()) LOG.debug("picture - "+dets.getPhotos());
		when (newsService.createNewsArticle(any(CreateNewsArticleEvent.class))).thenReturn(testData);
		this.mockMvc.perform(post(urlPrefix+"/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
		.andDo(print())
		.andExpect(jsonPath("$.articleId",is(dets.getNodeId().intValue())))
		.andExpect(jsonPath("$.title",is(dets.getTitle())))
		.andExpect(jsonPath("$.content",is(dets.getContent())))
//TODO		.andExpect(jsonPath("$.photos",is(dets.getPhotos())))
		.andExpect(jsonPath("$.likes",is(dets.getLikes())))
		.andExpect(jsonPath("$.date",is(dets.getDate())))
		.andExpect(jsonPath("$.creatorEmail",is(dets.getCreatorEmail())))
		.andExpect(jsonPath("$.links[0].rel",is("self")))
		.andExpect(jsonPath("$.links[1].rel",is("Previous")))
		.andExpect(jsonPath("$.links[2].rel",is("Next")))
		.andExpect(jsonPath("$.links[3].rel",is("Liked By")))
		.andExpect(jsonPath("$.links[4].rel",is("UnLiked By")))
		.andExpect(jsonPath("$.links[5].rel",is("Likes")))
		.andExpect(jsonPath("$.links[6].rel",is("Read all")))
		.andExpect(content().string(returnedContent))
		.andExpect(status().isCreated());		
if (LOG.isDebugEnabled()) LOG.debug("dets.getPhotos = "+dets.getPhotos());
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