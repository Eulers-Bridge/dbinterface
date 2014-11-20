package com.eulersbridge.iEngage.core.events;

public class Details
{
    protected Long nodeId;

	/**
	 * 
	 */
	public Details()
	{
		super();
	}

	/**
	 * @param nodeId
	 */
	public Details(Long nodeId)
	{
		this();
		this.nodeId = nodeId;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Details [nodeId=" + nodeId + "]";
	}

}
