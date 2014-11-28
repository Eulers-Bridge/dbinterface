/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import java.util.Iterator;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author Greg Newitt
 *
 */
public class Polls extends ResourceSupport
{
	Long totalPolls;
	Integer totalPages;
	Iterator<Poll> polls;

	/**
	 * @return the totalPolls
	 */
	public Long getTotalPolls()
	{
		return totalPolls;
	}

	/**
	 * @param totalPolls the totalPolls to set
	 */
	public void setTotalPolls(Long totalPolls)
	{
		this.totalPolls = totalPolls;
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
	public Iterator<Poll> getPolls()
	{
		return polls;
	}

	/**
	 * @param photoAlbums the photoAlbums to set
	 */
	public void setPolls(Iterator<Poll> polls)
	{
		this.polls = polls;
	}

	public static  Polls fromPollsIterator(Iterator<Poll> iter, Long totalPolls, Integer totalPages)
	{
	    Polls newPolls = new Polls();
//	    String simpleName=NewsArticles.class.getSimpleName();
//	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    newPolls.totalPolls= totalPolls;
	    newPolls.totalPages = totalPages;
	    newPolls.polls = iter;
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
//	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).withSelfRel());
	    // {!end selfRel}

	    return newPolls;
	}

}
