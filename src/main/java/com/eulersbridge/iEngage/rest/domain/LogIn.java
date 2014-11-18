/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Iterator;

import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.users.LoginDetails;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.rest.controller.ControllerConstants;
import com.eulersbridge.iEngage.rest.controller.ElectionController;
import com.eulersbridge.iEngage.rest.controller.NewsController;
import com.eulersbridge.iEngage.rest.controller.UserController;
import com.eulersbridge.iEngage.rest.controller.InstitutionController;

/**
 * @author Greg Newitt
 *
 */
public class LogIn extends ResourceSupport 
{
	Iterator <NewsArticle> articles;
	User user;
	Long userId;
	/**
	 * @return the articles
	 */
	public Iterator<NewsArticle> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Iterator<NewsArticle> articles) {
		this.articles = articles;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public static LogIn fromLoginDetails(LoginDetails loginDets) 
	{
		LogIn login=new LogIn();
		login.articles=NewsArticle.toArticlesIterator(loginDets.getArticles());
		String simpleName=LogIn.class.getSimpleName();
		String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
		    
		UserDetails returnedUser=loginDets.getUser();
		User user=User.fromUserDetails(returnedUser);
		user.removeLinks();
		login.user = user;
		login.userId=loginDets.getUserId();
		    
		//TODOCUMENT.  Adding the library, the above extends ResourceSupport and
		//this section is all that is actually needed in our model to add hateoas support.

		//Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
		//We have explicitly avoided that.
		// {!begin selfRel}
		login.add(linkTo(UserController.class).slash(name).withSelfRel());
		// {!end selfRel}
		// {!begin institution}
		login.add(linkTo(InstitutionController.class).slash(ControllerConstants.INSTITUTION_LABEL).slash(login.getUser().getInstitutionId()).withRel(RestDomainConstants.RELATED_LABEL));
		// {!end institution}
		
		// {!begin articles}
		login.add(linkTo(NewsController.class).slash(ControllerConstants.NEWS_ARTICLES_LABEL).withRel(RestDomainConstants.RELATED_LABEL));
		// {!end articles}
		
		// {!begin elections}
		login.add(linkTo(ElectionController.class).slash(name).slash(ControllerConstants.ELECTION_LABEL).withRel(RestDomainConstants.RELATED_LABEL));
		// {!end elections}

		
		return login;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "LogIn [articles=" + articles + ", user=" + user + ", userId="
				+ userId + "]";
	}


}
