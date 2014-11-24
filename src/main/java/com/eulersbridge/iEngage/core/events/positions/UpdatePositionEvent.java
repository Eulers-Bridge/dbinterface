package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdatePositionEvent extends UpdateEvent
{
	public UpdatePositionEvent(Long positionId, PositionDetails positionDetails)
	{
		super(positionId, positionDetails);
	}
}
