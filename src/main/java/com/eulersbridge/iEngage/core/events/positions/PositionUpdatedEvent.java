package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class PositionUpdatedEvent extends UpdatedEvent
{
	public PositionUpdatedEvent(Long positionId, PositionDetails positionDetails)
	{
		super(positionId, positionDetails);
	}

	public PositionUpdatedEvent(Long positionId)
	{
		super(positionId);
	}
}
