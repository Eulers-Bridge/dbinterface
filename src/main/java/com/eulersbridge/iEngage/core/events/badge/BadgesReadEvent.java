/**
 * 
 */
package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.AllReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class BadgesReadEvent extends AllReadEvent
{
	Iterable<BadgeDetails> badges;
	
	public BadgesReadEvent(Iterable<BadgeDetails> badges, Long totalItems, Integer totalPages)
	{
		super(null,totalItems,totalPages);
		this.badges=badges;
	}

	public BadgesReadEvent(Iterable<BadgeDetails> badges)
	{
		super(null);
		this.badges=badges;
	}

	public BadgesReadEvent()
	{
		super(null);
	}
	
	/**
	 * @return the badges
	 */
	public Iterable<BadgeDetails> getBadges() 
	{
		return badges;
	}

	/**
	 * @param badges the badges to set
	 */
	public void setBadges(Iterable<BadgeDetails> tasks) 
	{
		this.badges = tasks;
	}
	
	public static BadgesReadEvent notFound(Long id)
	{
		BadgesReadEvent ev = new BadgesReadEvent();
		ev.entityFound = false;
		return ev;
	}
}
