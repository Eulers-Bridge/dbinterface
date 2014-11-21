package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadPositionEvent extends ReadEvent
{
	public ReadPositionEvent(Long positionId)
	{
		super(positionId);
	}

	public ReadPositionEvent(Long positionId, PositionDetails positionDetails)
	{
		super(positionId, positionDetails);
	}

	public Long getPositionId()
	{
		return getNodeId();
	}

	public PositionDetails getPositionDetails()
	{
		return (PositionDetails)getDetails();
	}
}
