package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class PositionReadEvent extends ReadEvent
{
	public PositionReadEvent(Long positionId)
	{
		super(positionId);
	}

	public PositionReadEvent(Long positionId, PositionDetails positionDetails)
	{
		super(positionId, positionDetails);
	}
}
