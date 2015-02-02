/**
 * 
 */
package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class BadgesReadEvent extends ReadEvent
{
	Iterable<BadgeDetails> tasks;
	
	public BadgesReadEvent(Long electionId, Iterable<BadgeDetails> elections)
	{
		super(1l);
		this.tasks=elections;
	}

	public BadgesReadEvent(Iterable<BadgeDetails> elections)
	{
		super(1l);
		this.tasks=elections;
	}

	public BadgesReadEvent()
	{
		super(1l);
	}
	
	/**
	 * @return the elections
	 */
	public Iterable<BadgeDetails> getBadges() 
	{
		return tasks;
	}

	/**
	 * @param elections the elections to set
	 */
	public void setBadges(Iterable<BadgeDetails> tasks) 
	{
		this.tasks = tasks;
	}
}
