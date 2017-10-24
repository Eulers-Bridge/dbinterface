/**
 *
 */
package com.eulersbridge.iEngage.rest.domain;

import org.springframework.hateoas.ResourceSupport;

import java.util.Iterator;
import java.util.List;

/**
 * @author Greg Newitt
 */
public class WrappedDomainList<T> extends ResourceSupport {
  Long totalElements;
  Integer totalPages;
  Iterator<T> foundObjects;

  public WrappedDomainList() {
  }

  public WrappedDomainList(List<T> list, Long totleElements, Integer totalPages) {
    super();
    this.foundObjects = list.iterator();
    this.totalElements = totleElements;
    this.totalPages = totalPages;
  }

  public WrappedDomainList(List<T> list, Integer totleElements, Integer totalPages) {
    super();
    this.foundObjects = list.iterator();
    this.totalElements = totleElements.longValue();
    this.totalPages = totalPages;
  }

  public Long getTotalElements() {
    return totalElements;
  }


  public void setTotalElements(Long totalArticles) {
    this.totalElements = totalArticles;
  }

  public Integer getTotalPages() {
    return totalPages;
  }


  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public Iterator<T> getFoundObjects() {
    return foundObjects;
  }

  public void setFoundObjects(Iterator<T> foundObjects) {
    this.foundObjects = foundObjects;
  }

  public static WrappedDomainList fromIterator(Iterator<?> iter, Long totalArticles, Integer totalPages) {
    WrappedDomainList news = new WrappedDomainList();
    news.totalElements = totalArticles;
    news.totalPages = totalPages;
    news.foundObjects = iter;
    return news;
  }

}
