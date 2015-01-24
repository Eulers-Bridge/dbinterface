package com.eulersbridge.iEngage.core.events;

public class CreatedEvent
{
	Details details;

	/**
	 * 
	 */
	public CreatedEvent()
	{
		super();
	}

	/**
	 * @param details
	 */
	public CreatedEvent(Details details)
	{
		this();
		this.details = details;
	}

	/**
	 * @return the details
	 */
	public Details getDetails()
	{
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(Details details)
	{
		this.details = details;
	}
	
    public Long getNodeId()
    {
    	Long nodeId=null;
    	if (getDetails()!=null) nodeId=this.getDetails().getNodeId();
    		
        return nodeId;
    }
    
    public void setNodeId(Long nodeId)
    {
    	if (getDetails()!=null) this.getDetails().setNodeId(nodeId);
    }

}
