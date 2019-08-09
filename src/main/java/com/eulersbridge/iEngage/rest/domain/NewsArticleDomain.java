package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.rest.controller.NewsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class NewsArticleDomain extends ResourceSupport {
  private Long articleId;
  private Long institutionId;
  private String title;
  private String content;
  private List<PhotoDomain> photos;
  private Integer likes;
  private Long date;
  private String creatorEmail;
  private UserProfile creatorProfile;
  private boolean inappropriateContent = false;

  private static Logger LOG = LoggerFactory.getLogger(NewsArticleDomain.class);

  public static NewsArticleDomain fromNewsArticleDetails(NewsArticleDetails readNews) {
    NewsArticleDomain news = new NewsArticleDomain();
    String simpleName = NewsArticleDomain.class.getSimpleName();
    String name = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);

    news.articleId = readNews.getNewsArticleId();
    news.content = readNews.getContent();
    news.creatorEmail = readNews.getCreatorEmail();
    news.date = readNews.getDate();
    news.likes = readNews.getLikes();
    if (readNews.getPhotos() != null)
      news.photos = StreamSupport.stream(readNews.getPhotos().spliterator(), false).map(PhotoDomain::fromPhotoDetails).collect(Collectors.toList());
    news.title = readNews.getTitle();
    news.institutionId = readNews.getInstitutionId();
    news.setInappropriateContent(readNews.isInappropriateContent());
    news.setCreatorProfile(UserProfile.fromUserDetails(readNews.getCreatorDetails()));

    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
    //this section is all that is actually needed in our model to add hateoas support.

    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
    //We have explicitly avoided that.
    // {!begin selfRel}
    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).withSelfRel());
    // {!end selfRel}
    // {!begin previous}
    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
    // {!end previous}
    // {!begin next}
    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
    // {!end next}
    // {!begin likedBy}
    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).slash(RestDomainConstants.LIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.LIKEDBY_LABEL));
    // {!end likedBy}
    // {!begin unlikedBy}
    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).slash(RestDomainConstants.UNLIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.UNLIKEDBY_LABEL));
    // {!end unlikedBy}
    // {!begin likes}
    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).slash(RestDomainConstants.LIKES).withRel(RestDomainConstants.LIKES_LABEL));
    // {!end likes}
    // {!begin readAll}
    news.add(linkTo(NewsController.class).slash(name + 's').withRel(RestDomainConstants.READALL_LABEL));
    // {!end readAll}

    return news;
  }

  public NewsArticleDetails toNewsArticleDetails() {
    NewsArticleDetails details = new NewsArticleDetails();

    details.setNewsArticleId(getArticleId());
    details.setTitle(getTitle());
    details.setContent(getContent());
    details.setLikes(getLikes());
    details.setDate(getDate());
    details.setCreatorEmail(creatorEmail);
    details.setInstitutionId(getInstitutionId());
    details.setInappropriateContent(isInappropriateContent());

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
   * @return the photos
   */
  public List<PhotoDomain> getPhotos() {
    return photos;
  }

  /**
   * @param photos the photos to set
   */
  public void setPhotos(List<PhotoDomain> photos) {
    this.photos = photos;
  }

  /**
   * @return the likers
   */
  public Integer getLikes() {
    return likes;
  }

  /**
   * @param likes the likers to set
   */
  public void setLikes(Integer likes) {
    this.likes = likes;
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
   * @return the inappropriateContent
   */
  public boolean isInappropriateContent() {
    return inappropriateContent;
  }

  /**
   * @param inappropriateContent the inappropriateContent to set
   */
  public void setInappropriateContent(boolean inappropriateContent) {
    this.inappropriateContent = inappropriateContent;
  }

  public UserProfile getCreatorProfile() {
    return creatorProfile;
  }

  public void setCreatorProfile(UserProfile creatorProfile) {
    this.creatorProfile = creatorProfile;
  }

  public static Iterator<NewsArticleDomain> toArticlesIterator(Iterator<NewsArticleDetails> iter) {
    if (null == iter) return null;
    ArrayList<NewsArticleDomain> articles = new ArrayList<NewsArticleDomain>();
    while (iter.hasNext()) {
      NewsArticleDetails dets = iter.next();
      NewsArticleDomain thisArticle = NewsArticleDomain.fromNewsArticleDetails(dets);
      Link self = thisArticle.getLink("self");
      thisArticle.removeLinks();
      thisArticle.add(self);
      articles.add(thisArticle);
    }
    return articles.iterator();
  }

  public String toString() {
    StringBuffer buff = new StringBuffer("[ articleId = ");
    String retValue;
    buff.append(getArticleId());
    buff.append(", institutionId = ");
    buff.append(getInstitutionId());
    buff.append(", title = ");
    buff.append(getTitle());
    buff.append(", content = ");
    buff.append(getContent());
    buff.append(", photos = ");
    buff.append(getPhotos());
    buff.append(", likers = ");
    buff.append(getLikes());
    buff.append(", date = ");
    buff.append(getDate());
    buff.append(", creator email = ");
    buff.append(getCreatorEmail());
    buff.append(", institutionId = ");
    buff.append(getInstitutionId());
    buff.append(", pictures = ");
    buff.append(getPhotos());
    buff.append(", likes = ");
    buff.append(getLikes());
    buff.append(" ]");
    retValue = buff.toString();
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

}
