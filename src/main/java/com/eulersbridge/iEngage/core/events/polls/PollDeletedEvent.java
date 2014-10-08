package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class PollDeletedEvent extends DeletedEvent
{
    private Long pollid;
    private boolean deletionCompleted = true;

    public PollDeletedEvent(Long pollid) {
        this.pollid = pollid;
    }

    public static PollDeletedEvent deletionForbidden(Long pollid){
        PollDeletedEvent pollDeletedEvent = new PollDeletedEvent(pollid);
        pollDeletedEvent.entityFound = true;
        pollDeletedEvent.deletionCompleted = false;
        return pollDeletedEvent;
    }

    public static PollDeletedEvent notFound(Long pollid){
        PollDeletedEvent pollDeletedEvent = new PollDeletedEvent(pollid);
        pollDeletedEvent.entityFound = false;
        pollDeletedEvent.deletionCompleted = false;
        return pollDeletedEvent;
    }

    public boolean isDeletionCompleted(){
        return this.deletionCompleted;
    }

	/**
	 * @return the pollid
	 */
	public Long getPollid() {
		return pollid;
	}

	/**
	 * @param pollid the pollid to set
	 */
	public void setPollid(Long pollid) {
		this.pollid = pollid;
	}
}
