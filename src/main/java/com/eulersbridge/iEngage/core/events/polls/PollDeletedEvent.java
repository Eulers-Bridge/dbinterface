package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class PollDeletedEvent extends DeletedEvent
{

	public PollDeletedEvent(Long pollid)
	{
		super(pollid);
	}

	/**
	 * @return the pollid
	 */
	public Long getPollid()
	{
		return getNodeId();
	}

	/**
	 * @param pollid
	 *            the pollid to set
	 */
	public void setPollid(Long pollid)
	{
		setNodeId(pollid);
	}
}
