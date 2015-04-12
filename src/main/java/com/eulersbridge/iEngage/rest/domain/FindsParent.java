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
public class FindsParent extends ResourceSupport
{
	Long totalElements;
	Integer totalPages;
	Iterator<?> foundObjects;

	/**
	 * @return the totalElements
	 */
	public Long getTotalElements() {
		return totalElements;
	}

	/**
	 * @param totalElements the totalElements to set
	 */
	public void setTotalElements(Long totalArticles) {
		this.totalElements = totalArticles;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the articles
	 */
	public Iterator<?> getFoundObjects() {
		return foundObjects;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setFoundObjects(Iterator<?> foundObjects) {
		this.foundObjects = foundObjects;
	}

	public static  FindsParent fromArticlesIterator(Iterator<?> iter, Long totalArticles, Integer totalPages)
	{
		FindsParent news = new FindsParent();
//	    String simpleName=NewsArticles.class.getSimpleName();
//	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    news.totalElements= totalArticles;
	    news.totalPages = totalPages;
	    news.foundObjects = iter;
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
//	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).withSelfRel());
	    // {!end selfRel}

	    return news;
	}

}
