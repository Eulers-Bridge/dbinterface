package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeletePositionEvent extends DeleteEvent
{
	public DeletePositionEvent(Long positionId)
	{
		super(positionId);
	}
}
