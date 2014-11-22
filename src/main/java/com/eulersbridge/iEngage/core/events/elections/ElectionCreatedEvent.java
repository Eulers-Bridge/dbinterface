package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class ElectionCreatedEvent extends CreatedEvent
{
    private Long id;
    private boolean institutionFound=true;

    public ElectionCreatedEvent (Long id, ElectionDetails electionDetails)
    {
    	super(electionDetails);
        this.id = id;
    }

    public ElectionCreatedEvent(Long id){
        this.id = id;
    }

    public Long getElectionId(){
        return this.id;
    }

    public void setKey(Long id){
        this.id = id;
    }

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound() {
		return institutionFound;
	}

	public static ElectionCreatedEvent institutionNotFound(Long institutionId) 
	{
		ElectionCreatedEvent evt=new ElectionCreatedEvent(institutionId);
		evt.institutionFound=false;
		return evt;
	}
}
