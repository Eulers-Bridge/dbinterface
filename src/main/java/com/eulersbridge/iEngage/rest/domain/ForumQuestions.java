/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import org.springframework.hateoas.ResourceSupport;

import java.util.Iterator;

/**
 * @author Greg Newitt
 *
 */
public class ForumQuestions extends ResourceSupport
{
	Long totalForumQuestions;
	Integer totalPages;
	Iterator<ForumQuestion> forumQuestions;

	/**
	 * @return the totalForumQuestions
	 */
	public Long getTotalForumQuestions()
	{
		return totalForumQuestions;
	}

	/**
	 * @param totalForumQuestions the totalForumQuestions to set
	 */
	public void setForumQuestions(Long totalForumQuestions)
	{
		this.totalForumQuestions = totalForumQuestions;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages()
	{
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages)
	{
		this.totalPages = totalPages;
	}

	/**
	 * @return the polls
	 */
	public Iterator<ForumQuestion> getForumQuestions()
	{
		return forumQuestions;
	}

	/**
	 * @param photoAlbums the photoAlbums to set
	 */
	public void setForumQuestions(Iterator<ForumQuestion> forumQuestions)
	{
		this.forumQuestions = forumQuestions;
	}

	public static  ForumQuestions fromForumQuestionsIterator(Iterator<ForumQuestion> iter, Long totalForumQuestions, Integer totalPages)
	{
		ForumQuestions newForumQuestions = new ForumQuestions();
//	    String simpleName=NewsArticles.class.getSimpleName();
//	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    newForumQuestions.totalForumQuestions= totalForumQuestions;
	    newForumQuestions.totalPages = totalPages;
	    newForumQuestions.forumQuestions = iter;
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
//	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).withSelfRel());
	    // {!end selfRel}

	    return newForumQuestions;
	}

}
