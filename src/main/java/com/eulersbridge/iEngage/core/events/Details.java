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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Details other = (Details) obj;
		if (nodeId == null)
		{
			if (other.nodeId != null) return false;
		}
		else if (!nodeId.equals(other.nodeId)) return false;
		return true;
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
