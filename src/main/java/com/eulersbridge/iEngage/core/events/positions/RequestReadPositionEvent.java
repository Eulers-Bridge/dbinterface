package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadPositionEvent extends RequestReadEvent
{
	public RequestReadPositionEvent(Long positionId)
	{
		super(positionId);
	}
}
