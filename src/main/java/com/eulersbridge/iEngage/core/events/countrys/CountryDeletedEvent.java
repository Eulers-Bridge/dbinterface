package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class CountryDeletedEvent extends DeletedEvent
{
	private CountryDetails details;

	private CountryDeletedEvent(Long id)
	{
		super(id);
	}

	public CountryDeletedEvent(Long id, CountryDetails details)
	{
		this(id);
		this.details = details;
		this.deletionCompleted = true;
	}

	public Long getId()
	{
		return getNodeId();
	}

	public CountryDetails getDetails()
	{
		return details;
	}

	public static CountryDeletedEvent deletionForbidden(Long id,
			CountryDetails details)
	{
		CountryDeletedEvent ev = new CountryDeletedEvent(id, details);
		ev.entityFound = true;
		ev.deletionCompleted = false;
		return ev;
	}

	public static CountryDeletedEvent notFound(Long id)
	{
		CountryDeletedEvent ev = new CountryDeletedEvent(id);
		ev.entityFound = false;
		ev.deletionCompleted = false;
		return ev;
	}

}
