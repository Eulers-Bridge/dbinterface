package com.eulersbridge.iEngage.rest.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.NewsService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

public class ViewLoginIntegrationTest {

    private static Logger LOG = LoggerFactory.getLogger(ViewLoginIntegrationTest.class);

    MockMvc mockMvc;
	
	@InjectMocks
	LoginController controller;
	
	@Mock
	UserService userService;
	
	@Mock
	NewsService newsService;
	
	@Mock
	SecurityContextHolder contextHolder;
	
	UserDetails userDets;
	
	@Before
	
	public void setUp() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("setup()");
		MockitoAnnotations.initMocks(this);

		this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
		
		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getName()).thenReturn("test@unimelb.edu.au");
		SimpleGrantedAuthority auth=new SimpleGrantedAuthority("ROLE_USER");
		userDets= DatabaseDataFixture.populateUserGnewitt().toUserDetails(); 
		Collection clist =new ArrayList<SimpleGrantedAuthority>();
		clist.add(auth);
		Mockito.when(authentication.getAuthorities()).thenReturn(clist);
		Mockito.when(authentication.getName()).thenReturn(userDets.getEmail());
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

	public ArrayList<NewsArticleDetails> initNewsArticles(Long instId,Map<Long, NewsArticle> dets) 
	{
		Collection<NewsArticle> articles=dets.values();
		Iterator<NewsArticle> iter=articles.iterator();
		ArrayList<NewsArticleDetails> artDets=new ArrayList<NewsArticleDetails>(); 
		while (iter.hasNext())
		{
			NewsArticle article=iter.next();
			artDets.add(article.toNewsArticleDetails());
		}
		return artDets;
	}
	
	@Test
	public final void testLoginUser() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLogin()");
		
		ReadUserEvent testData=new ReadUserEvent(userDets.getEmail(), userDets);
		Long instId = 1l;
		ArrayList<NewsArticleDetails> coll = initNewsArticles(instId, DatabaseDataFixture.populateNewsArticles());
		NewsArticlesReadEvent value=new NewsArticlesReadEvent(instId,coll);
		when (userService.readUser(any(RequestReadUserEvent.class))).thenReturn(testData);
		when (newsService.readNewsArticles(any(ReadNewsArticlesEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(value);
		this.mockMvc.perform(get("/api/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$articles.[0].title",is(coll.get(0).getTitle())))
		.andExpect(jsonPath("$articles.[0].date",is(coll.get(0).getDate())))
		.andExpect(jsonPath("$articles.[0].creatorEmail",is(coll.get(0).getCreatorEmail())))
		.andExpect(jsonPath("$articles.[0].content",is(coll.get(0).getContent())))
		.andExpect(jsonPath("$articles.[0].articleId",is(coll.get(0).getNewsArticleId().intValue())))
		.andExpect(jsonPath("$articles.[0].institutionId",is(coll.get(0).getInstitutionId().intValue())))
		.andExpect(jsonPath("$articles.[1].title",is(coll.get(1).getTitle())))
		.andExpect(jsonPath("$articles.[1].date",is(coll.get(1).getDate())))
		.andExpect(jsonPath("$articles.[1].creatorEmail",is(coll.get(1).getCreatorEmail())))
		.andExpect(jsonPath("$articles.[1].content",is(coll.get(1).getContent())))
		.andExpect(jsonPath("$articles.[1].articleId",is(coll.get(1).getNewsArticleId().intValue())))
		.andExpect(jsonPath("$articles.[1].institutionId",is(coll.get(1).getInstitutionId().intValue())))
		.andExpect(jsonPath("$user.givenName",is(userDets.getGivenName())))
		.andExpect(jsonPath("$user.familyName",is(userDets.getFamilyName())))
		.andExpect(jsonPath("$user.gender",is(userDets.getGender())))
		.andExpect(jsonPath("$user.nationality",is(userDets.getNationality())))
		.andExpect(jsonPath("$user.yearOfBirth",is(userDets.getYearOfBirth())))
		.andExpect(jsonPath("$user.password",is(userDets.getPassword())))
		.andExpect(jsonPath("$user.accountVerified",is(userDets.isAccountVerified())))
		.andExpect(jsonPath("$user.institutionId",is(userDets.getInstitutionId().intValue())))
		.andExpect(jsonPath("$user.email",is(userDets.getEmail())))
		.andExpect(status().isOk())	;
	}

	@Test
	public final void testLoginUserNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLogin()");
		String email="gnewitt@hotmail.com";
		ReadUserEvent testData=ReadUserEvent.notFound(email);
		Long instId = 1l;
		Collection<NewsArticleDetails> coll = initNewsArticles(instId, DatabaseDataFixture.populateNewsArticles());

		NewsArticlesReadEvent value=new NewsArticlesReadEvent(instId,coll);
		when (userService.readUser(any(RequestReadUserEvent.class))).thenReturn(testData);
		when (newsService.readNewsArticles(any(ReadNewsArticlesEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(value);
		this.mockMvc.perform(get("/api/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testLoginNewsNotFound() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLogin()");
		ReadUserEvent testData=new ReadUserEvent(userDets.getEmail(), userDets);
		NewsArticlesReadEvent value=NewsArticlesReadEvent.newsFeedNotFound();
		when (userService.readUser(any(RequestReadUserEvent.class))).thenReturn(testData);
		when (newsService.readNewsArticles(any(ReadNewsArticlesEvent.class), any(Direction.class), any(int.class), any(int.class))).thenReturn(value);
		this.mockMvc.perform(get("/api/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())	;
	}

	@Test
	public final void testLogout() throws Exception 
	{
		if (LOG.isDebugEnabled()) LOG.debug("performingLogout()");
		this.mockMvc.perform(get("/api/logout").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())	;
	}

}
