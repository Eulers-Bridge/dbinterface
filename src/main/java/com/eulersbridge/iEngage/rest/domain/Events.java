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
public class Events extends ResourceSupport
{
	Long totalEvents;
	Integer totalPages;
	Iterator<Event> events;

	/**
	 * @return the totalEvents
	 */
	public Long getTotalEvents()
	{
		return totalEvents;
	}

	/**
	 * @param totalEvents the totalEvents to set
	 */
	public void setTotalEvents(Long totalEvents)
	{
		this.totalEvents = totalEvents;
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
	 * @return the events
	 */
	public Iterator<Event> getEvents() {
		return events;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setEvents(Iterator<Event> events) {
		this.events = events;
	}

	public static  Events fromEventsIterator(Iterator<Event> iter, Long totalEvents, Integer totalPages)
	{
		Events events = new Events();
//	    String simpleName=NewsArticles.class.getSimpleName();
//	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

		events.totalEvents= totalEvents;
		events.totalPages = totalPages;
		events.events = iter;
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
//	    news.add(linkTo(NewsController.class).slash(name).slash(news.articleId).withSelfRel());
	    // {!end selfRel}

	    return events;
	}

}
