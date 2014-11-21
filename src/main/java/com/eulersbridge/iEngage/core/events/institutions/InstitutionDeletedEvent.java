package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class InstitutionDeletedEvent extends DeletedEvent
{
	private InstitutionDetails details;

	private InstitutionDeletedEvent(Long id)
	{
		super(id);
	}

	public InstitutionDeletedEvent(Long id, InstitutionDetails details)
	{
		this(id);
		this.details = details;
		this.deletionCompleted = true;
	}

	public Long getId()
	{
		return getNodeId();
	}

	public InstitutionDetails getDetails()
	{
		return details;
	}

	public static InstitutionDeletedEvent deletionForbidden(Long id,
			InstitutionDetails details)
	{
		InstitutionDeletedEvent ev = new InstitutionDeletedEvent(id, details);
		ev.entityFound = true;
		ev.deletionCompleted = false;
		return ev;
	}

	public static InstitutionDeletedEvent notFound(Long id)
	{
		InstitutionDeletedEvent ev = new InstitutionDeletedEvent(id);
		ev.entityFound = false;
		ev.deletionCompleted = false;
		return ev;
	}

}
