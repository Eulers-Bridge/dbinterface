package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PositionCreatedEvent extends CreatedEvent
{
    private static Logger LOG = LoggerFactory.getLogger(PositionCreatedEvent.class);
    private boolean electionFound=true;
    private Long failedNodeId;

    public PositionCreatedEvent()
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public PositionCreatedEvent(PositionDetails positionDetails)
    {
    	super(positionDetails);
    }

    public PositionCreatedEvent(Long failedNodeId)
    {
    	super(null);
    	this.failedNodeId=failedNodeId;
    }

	/**
	 * @return the failedNodeId
	 */
	public Long getFailedNodeId()
	{
		return failedNodeId;
	}

	/**
	 * @param failedNodeId the failedNodeId to set
	 */
	public void setFailedNodeId(Long failedNodeId)
	{
		this.failedNodeId = failedNodeId;
	}

	/**
	 * @return the electionFound
	 */
	public boolean isElectionFound()
	{
		return electionFound;
	}
	
	public static PositionCreatedEvent electionNotFound(Long electionId) 
	{
		PositionCreatedEvent evt=new PositionCreatedEvent(electionId);
		evt.electionFound=false;
		return evt;
	}
}
