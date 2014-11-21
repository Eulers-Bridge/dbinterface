package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Yikai Gong
 */

public class CreatePositionEvent extends CreateEvent
{
	public CreatePositionEvent(PositionDetails positionDetails)
	{
		super(positionDetails);
	}
}
