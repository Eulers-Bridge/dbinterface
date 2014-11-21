package com.eulersbridge.iEngage.core.events;

public class DeleteEvent 
{
	private final Long nodeId;

	/**
	 * @param nodeId
	 */
	public DeleteEvent(final Long nodeId)
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
