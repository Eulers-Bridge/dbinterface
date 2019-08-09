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
public class NewsArticles extends ResourceSupport
{
	Long totalArticles;
	Integer totalPages;
	Iterator<NewsArticleDomain> articles;

	/**
	 * @return the totalArticles
	 */
	public Long getTotalArticles() {
		return totalArticles;
	}

	/**
	 * @param totalArticles the totalArticles to set
	 */
	public void setTotalArticles(Long totalArticles) {
		this.totalArticles = totalArticles;
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
	public Iterator<NewsArticleDomain> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Iterator<NewsArticleDomain> articles) {
		this.articles = articles;
	}

	public static  NewsArticles fromArticlesIterator(Iterator<NewsArticleDomain> iter, Long totalArticles, Integer totalPages)
	{
	    NewsArticles news = new NewsArticles();
//	    String simpleName=NewsArticles.class.getSimpleName();
//	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    news.totalArticles= totalArticles;
	    news.totalPages = totalPages;
	    news.articles = iter;
	    
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
