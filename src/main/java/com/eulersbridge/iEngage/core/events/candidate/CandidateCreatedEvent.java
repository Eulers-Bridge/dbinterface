package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CandidateCreatedEvent extends CreatedEvent {
    private Long failedId;
	boolean positionFound=true;
	boolean userFound=true;

    private static Logger LOG = LoggerFactory.getLogger(CandidateCreatedEvent.class);

    public CandidateCreatedEvent(Long failedId) {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.failedId = failedId;
    }

    public CandidateCreatedEvent(CandidateDetails candidateDetails) {
        super(candidateDetails);
    }

	/**
	 * @return the failedId
	 */
	public Long getFailedId()
	{
		return failedId;
	}

	/**
	 * @param failedId the failedId to set
	 */
	public void setFailedId(Long failedId)
	{
		this.failedId = failedId;
	}

	/**
	 * @return the positionFound
	 */
	public boolean isPositionFound()
	{
		return positionFound;
	}

	/**
	 * @param positionFound the positionFound to set
	 */
	public void setPositionFound(boolean positionFound)
	{
		this.positionFound = positionFound;
	}

	/**
	 * @return the userFound
	 */
	public boolean isUserFound()
	{
		return userFound;
	}

	/**
	 * @param userFound the userFound to set
	 */
	public void setUserFound(boolean userFound)
	{
		this.userFound = userFound;
	}

	public static CandidateCreatedEvent positionNotFound(Long positionId)
	{
		CandidateCreatedEvent failedEvent = new CandidateCreatedEvent(positionId);
		failedEvent.setFailed(true);
		failedEvent.setPositionFound(false);
		return failedEvent;
	}

	public static CandidateCreatedEvent userNotFound(Long userId)
	{
		CandidateCreatedEvent failedEvent = new CandidateCreatedEvent(userId);
		failedEvent.setFailed(true);
		failedEvent.setUserFound(false);
		return failedEvent;
	}

}
