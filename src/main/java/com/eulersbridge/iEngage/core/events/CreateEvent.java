package com.eulersbridge.iEngage.core.events;

public class CreateEvent
{
    protected Details details;

	/**
	 * @param details
	 */
	public CreateEvent(Details details)
	{
		super();
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

}
