package com.eulersbridge.iEngage.core.events;

public class ReadEvent
{
	Long nodeId;
	Details details;

	protected boolean entityFound = true;

	/**
	 * @param nodeId
	 */
	public ReadEvent(Long nodeId)
	{
		super();
		this.nodeId = nodeId;
	}

	/**
	 * @param nodeId
	 * @param details
	 */
	public ReadEvent(Long nodeId, Details details)
	{
		super();
		this.nodeId = nodeId;
		this.details = details;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @return the details
	 */
	public Details getDetails()
	{
		return details;
	}

	public boolean isEntityFound()
	{
		return entityFound;
	}

	public static ReadEvent notFound(Long id)
	{
		ReadEvent ev = new ReadEvent(id);
		ev.entityFound = false;
		return ev;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ReadEvent [nodeId=" + nodeId + ", details=" + details
				+ ", entityFound=" + entityFound + "]";
	}
}
