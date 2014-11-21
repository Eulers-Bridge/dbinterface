package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.*;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.rest.domain.LikeInfo;
import com.eulersbridge.iEngage.rest.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.core.services.NewsService;
import com.eulersbridge.iEngage.rest.domain.NewsArticle;
import com.eulersbridge.iEngage.rest.domain.NewsArticles;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class NewsController 
{
    @Autowired NewsService newsService;
    @Autowired
    LikesService likesService;

	public NewsController() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("NewsController()");
	}

    private static Logger LOG = LoggerFactory.getLogger(NewsController.class);

   /**
     * Is passed all the necessary data to update a news article.
     * Or potentially create a new one.
     * The request must be a PUT with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting news article object. 
     * There will also be a relationship set up with the 
     * user who created the article.
     * 
     * @param articleId the id of the news article to be updated.
     * @param newsArticle the newsArticle object passed across as JSON.
     * @return the newsArticle object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{articleId}")
    public @ResponseBody ResponseEntity<NewsArticle> alterNewsArticle(@PathVariable Long articleId,
    		@RequestBody NewsArticle newsArticle) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to edit newsArticle. "+articleId);
    	UpdateNewsArticleEvent unae=new UpdateNewsArticleEvent(articleId,newsArticle.toNewsArticleDetails());
    	if (LOG.isDebugEnabled()) LOG.debug("Update na event - "+unae.getUNewsArticleDetails());
    	NewsArticleUpdatedEvent newsEvent=newsService.updateNewsArticle(unae);
    	if (null!=newsEvent)
    	{	
    		if (LOG.isDebugEnabled()) LOG.debug("newsEvent - "+newsEvent);
			NewsArticle restNews=NewsArticle.fromNewsArticleDetails(newsEvent.getNewsArticleDetails());
			if (LOG.isDebugEnabled()) LOG.debug("restNews = "+restNews);
		  	return new ResponseEntity<NewsArticle>(restNews,HttpStatus.OK);
    	}
    	else
    		return new ResponseEntity<NewsArticle>(HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Is passed all the necessary data to read a news article from the database.
     * The request must be a GET with the news article id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the user object read from the database.
     * 
     * @param email the email address of the user object to be read.
     * @return the user object.
     * 

	*/
	@RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{articleId}/likedBy/{email}/")
	public @ResponseBody ResponseEntity<Boolean> likeArticle(@PathVariable Long articleId,@PathVariable String email) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to have "+email+" like news article. "+articleId);
		NewsArticleLikedEvent articleEvent=newsService.likeNewsArticle(new LikeEvent(articleId,email));
		
		ResponseEntity<Boolean> response;
		
		if (!articleEvent.isEntityFound())
		{
			response = new ResponseEntity<Boolean>(HttpStatus.GONE);
		}
		else if (!articleEvent.isUserFound())
		{
			response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Boolean restNews=articleEvent.isResultSuccess();
			response = new ResponseEntity<Boolean>(restNews,HttpStatus.OK);
		}
		return response;
	}
    
    /**
     * Is passed all the necessary data to read a news article from the database.
     * The request must be a GET with the news article id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the user object read from the database.
     * 
     * @param email the email address of the user object to be read.
     * @return the user object.
     * 

	*/
	@RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{articleId}/unlikedBy/{email}/")
	public @ResponseBody ResponseEntity<Boolean> unlikeArticle(@PathVariable Long articleId,@PathVariable String email) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to have "+email+" unlike news article. "+articleId);
		NewsArticleUnlikedEvent articleEvent=newsService.unlikeNewsArticle(new LikeEvent(articleId,email));
  	
		ResponseEntity<Boolean> response;
		
		if (!articleEvent.isEntityFound())
		{
			response = new ResponseEntity<Boolean>(HttpStatus.GONE);
		}
		else if (!articleEvent.isUserFound())
		{
			response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Boolean restNews=articleEvent.isResultSuccess();
			response = new ResponseEntity<Boolean>(restNews,HttpStatus.OK);
		}
		return response;
	}
    
    /**
     * Is passed all the necessary data to read a news article from the database.
     * The request must be a GET with the news article id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the user object read from the database.
     * 
     * @param email the email address of the user object to be read.
     * @return the user object.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{articleId}")
	public @ResponseBody ResponseEntity<NewsArticle> findArticle(@PathVariable Long articleId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve news article. "+articleId);
		
		ReadEvent articleEvent=newsService.requestReadNewsArticle(new RequestReadNewsArticleEvent(articleId));
  	
		if (!articleEvent.isEntityFound())
		{
			return new ResponseEntity<NewsArticle>(HttpStatus.NOT_FOUND);
		}
		NewsArticle restNews=NewsArticle.fromNewsArticleDetails((NewsArticleDetails)articleEvent.getDetails());
        return new ResponseEntity<NewsArticle>(restNews,HttpStatus.OK);
	}
    
    /**
     * Is passed all the necessary data to delete a news article.
     * The request must be a DELETE with the news article id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the deleted news article object.
     * 
     * @param id the id of the news article object to be deleted.
     * @return the news article object deleted.
     * 

	*/
	@RequestMapping(method=RequestMethod.DELETE,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{articleId}")
	public @ResponseBody ResponseEntity<Boolean> deleteNewsArticle(@PathVariable Long articleId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to delete news article. "+articleId);
		ResponseEntity<Boolean> response;
		DeletedEvent newsEvent=newsService.deleteNewsArticle(new DeleteNewsArticleEvent(articleId));
		if (newsEvent.isDeletionCompleted())
			response=new ResponseEntity<Boolean>(newsEvent.isDeletionCompleted(),HttpStatus.OK);
		else if (newsEvent.isEntityFound())
			response=new ResponseEntity<Boolean>(newsEvent.isDeletionCompleted(),HttpStatus.GONE);
		else
			response=new ResponseEntity<Boolean>(newsEvent.isDeletionCompleted(),HttpStatus.NOT_FOUND);
		return response;
	}
    
    
    /**
     * Is passed all the necessary data to create a news article.
     * The request must be a POST with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting news article object.
     * There will also be a relationship set up with the 
     * user who created the article.
     * 
     * @param news article the news article object passed across as JSON.
     * @return the news article object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.POST,value=ControllerConstants.NEWS_ARTICLE_LABEL)
    public @ResponseBody ResponseEntity<NewsArticle> createNewsArticle(@RequestBody NewsArticle newsArticle) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to create news article "+newsArticle);
    	NewsArticleCreatedEvent newsEvent=newsService.createNewsArticle(new CreateNewsArticleEvent(newsArticle.toNewsArticleDetails()));
    	ResponseEntity<NewsArticle> resp;
    	if (!newsEvent.isCreatorFound())
    	{
    		resp = new ResponseEntity<NewsArticle>(HttpStatus.BAD_REQUEST);
    	}
    	else if(!newsEvent.isInstitutionFound())
    	{
    		resp = new ResponseEntity<NewsArticle>(HttpStatus.BAD_REQUEST);
    	}
    	else if (newsEvent.getNewsArticleId()==null)
    	{
    		resp = new ResponseEntity<NewsArticle>(HttpStatus.BAD_REQUEST);
    	}
    	else
    	{
    		NewsArticle restNews=NewsArticle.fromNewsArticleDetails(newsEvent.getNewsArticleDetails());
	    	if (LOG.isDebugEnabled()) LOG.debug("News event"+newsEvent.toString());
	    	resp = new ResponseEntity<NewsArticle>(restNews,HttpStatus.CREATED);
    	}
    	return resp;
    }

    /**
     * Is passed all the necessary data to read news articles from the database.
     * The request must be a GET with the institutionId/student year presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the news articles read from the database.
     * 
     * @param email the email address of the user object to be read.
     * @return the user object.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.NEWS_ARTICLES_LABEL+"/{institutionId}")
	public @ResponseBody ResponseEntity<NewsArticles> findArticles(@PathVariable(value="") Long institutionId,
			@RequestParam(value="direction",required=false,defaultValue=ControllerConstants.DIRECTION) String direction,
			@RequestParam(value="page",required=false,defaultValue=ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value="pageSize",required=false,defaultValue=ControllerConstants.PAGE_LENGTH) String pageSize) 
	{
		int pageNumber=0;
		int pageLength=10;
		pageNumber=Integer.parseInt(page);
		pageLength=Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve news articles from institution "+institutionId+'.');
				
		ResponseEntity<NewsArticles> response;
		
		Direction sortDirection=Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection=Direction.ASC;
		NewsArticlesReadEvent articleEvent=newsService.readNewsArticles(new ReadNewsArticlesEvent(institutionId),sortDirection, pageNumber,pageLength);
  	
		if (!articleEvent.isEntityFound())
		{
			response = new ResponseEntity<NewsArticles>(HttpStatus.NOT_FOUND);
		}
		
		else
		{
			Iterator<NewsArticle> articles = NewsArticle.toArticlesIterator(articleEvent.getArticles().iterator());
			NewsArticles newsArticles = NewsArticles.fromArticlesIterator(articles, articleEvent.getTotalArticles(), articleEvent.getTotalPages());
			response = new ResponseEntity<NewsArticles>(newsArticles,HttpStatus.OK);
		}

		return response;
	}

    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{articleId}" + ControllerConstants.LIKES_LABEL)
    public @ResponseBody ResponseEntity<Iterator<LikeInfo>> findLikes(
            @PathVariable Long articleId,
            @RequestParam(value="direction",required=false,defaultValue=ControllerConstants.DIRECTION) String direction,
            @RequestParam(value="page",required=false,defaultValue=ControllerConstants.PAGE_NUMBER) String page,
            @RequestParam(value="pageSize",required=false,defaultValue=ControllerConstants.PAGE_LENGTH) String pageSize)
    {
        int pageNumber = 0;
        int pageLength = 10;
        pageNumber = Integer.parseInt(page);
        pageLength = Integer.parseInt(pageSize);
        if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve liked users from article "+articleId+'.');
        Direction sortDirection = Direction.DESC;
        if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;

//        NewsArticleLikesEvent newsArticleLikesEvent = newsService.likesNewsArticle(new LikesNewsArticleEvent(articleId), sortDirection, pageNumber, pageLength);
//        if (!newsArticleLikesEvent.isArticlesFound())
//        {
//            return new ResponseEntity<Iterator<LikeInfo>>(HttpStatus.NOT_FOUND);
//        }

        LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(new LikesLikeableObjectEvent(articleId), sortDirection, pageNumber, pageLength);
        Iterator<LikeInfo> likes = User.toLikesIterator(likeableObjectLikesEvent.getUserDetails().iterator());
        if (likes.hasNext() == false){
            ReadEvent articleEvent=newsService.requestReadNewsArticle(new RequestReadNewsArticleEvent(articleId));
            if (!articleEvent.isEntityFound())
                return new ResponseEntity<Iterator<LikeInfo>>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
    }
}