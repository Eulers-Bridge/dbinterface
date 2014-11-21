package com.eulersbridge.iEngage.core.events;

public class RequestReadEvent 
{
	private Long nodeId;

	/**
	 * @param nodeId
	 */
	public RequestReadEvent(Long nodeId)
	{
		super();
		this.nodeId = nodeId;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

}
