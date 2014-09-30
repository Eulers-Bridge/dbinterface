package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.rest.controller.NewsController;

public class NewsArticle extends ResourceSupport
{
	private Long articleId;
	private Long institutionId;
	private String title;
	private String content;
	private Set<String> picture;
	private Set<String> likers;
	private Long date;
	private String creatorEmail;
	private String studentYear;

    private static Logger LOG = LoggerFactory.getLogger(NewsArticle.class);

    public static NewsArticle fromNewsArticleDetails(NewsArticleDetails readNews) 
	{
	    NewsArticle news = new NewsArticle();
	    String simpleName=NewsArticle.class.getSimpleName();
	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    news.articleId= readNews.getNewsArticleId();
	    news.content = readNews.getContent();
	    news.creatorEmail = readNews.getCreatorEmail();
	    news.date = readNews.getDate();
	    news.likers = readNews.getLikers();
	    news.picture = readNews.getPicture();
	    news.title = readNews.getTitle();
	    news.institutionId = readNews.getInstitutionId();
	    news.studentYear = readNews.getStudentYear();
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).withSelfRel());
	    // {!end selfRel}
	    // {!begin status}
	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).slash("previous").withRel("Previous"));
	    // {!end status}
	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).slash("next").withRel("Next"));

	    return news;
	}

	public NewsArticleDetails toNewsArticleDetails() 
	{
		NewsArticleDetails details = new NewsArticleDetails();

	    details.setNewsArticleId(getArticleId());
	    details.setTitle(getTitle());
	    details.setContent(getContent());
	    details.setPicture(getPicture());
	    details.setLikers(getLikers());
	    details.setDate(getDate());
	    details.setCreatorEmail(creatorEmail);
	    details.setInstitutionId(getInstitutionId());

	    return details;
	}

	/**
	 * @return the articleId
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the picture
	 */
	public Set<String> getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(Set<String> picture) {
		this.picture = picture;
	}

	/**
	 * @return the likers
	 */
	public Set<String> getLikers() {
		return likers;
	}

	/**
	 * @param likers the likers to set
	 */
	public void setLikers(Set<String> likers) {
		this.likers = likers;
	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

	/**
	 * @return the creatorEmail
	 */
	public String getCreatorEmail() {
		return creatorEmail;
	}

	/**
	 * @param creatorEmail the creatorEmail to set
	 */
	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	/**
	 * @return the studentYear
	 */
	public String getStudentYear() {
		return studentYear;
	}

	/**
	 * @param studentYear the studentYear to set
	 */
	public void setStudentYear(String studentYear) {
		this.studentYear = studentYear;
	}
	
	public static Iterator<NewsArticle> toArticlesIterator(Iterator<NewsArticleDetails> iter)
	{
		ArrayList <NewsArticle> articles=new ArrayList<NewsArticle>();
		while(iter.hasNext())
		{
			NewsArticleDetails dets=iter.next();
			NewsArticle thisArticle=NewsArticle.fromNewsArticleDetails(dets);
			articles.add(thisArticle);		
		}
		return articles.iterator();
	}

	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ articleId = ");
		String retValue;
		buff.append(getArticleId());
		buff.append(", institutionId = ");
		buff.append(getInstitutionId());
		buff.append(", title = ");
		buff.append(getTitle());
		buff.append(", content = ");
		buff.append(getContent());
		buff.append(", picture = ");
		buff.append(getPicture());
		buff.append(", likers = ");
		buff.append(getLikers());
		buff.append(", date = ");
		buff.append(getDate());
		buff.append(", creator email = ");
		buff.append(getCreatorEmail());
		buff.append(", studentYear = ");
		buff.append(getStudentYear());
		buff.append(", institutionId = ");
		buff.append(getInstitutionId());
		buff.append(", pictures = ");
		buff.append(getPicture());
		buff.append(", likers = ");
		buff.append(getLikers());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}

}
