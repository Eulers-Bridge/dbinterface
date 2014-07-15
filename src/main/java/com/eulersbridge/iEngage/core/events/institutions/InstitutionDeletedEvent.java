package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class InstitutionDeletedEvent extends DeletedEvent 
{
	  private Long id;
	  private InstitutionDetails details;
	  private boolean deletionCompleted;

	  private InstitutionDeletedEvent(Long id) 
	  {
		    this.id = id;
	  }

	  public InstitutionDeletedEvent(Long id, InstitutionDetails details) {
		this.id = id;
	    this.details = details;
	    this.deletionCompleted = true;
	  }

	  public Long getId() {
	    return id;
	  }

	  public InstitutionDetails getDetails() {
	    return details;
	  }

	  public boolean isDeletionCompleted() {
	    return deletionCompleted;
	  }

	  public static InstitutionDeletedEvent deletionForbidden(Long id, InstitutionDetails details) {
		InstitutionDeletedEvent ev = new InstitutionDeletedEvent(id, details);
	    ev.entityFound=true;
	    ev.deletionCompleted=false;
	    return ev;
	  }

	  public static InstitutionDeletedEvent notFound(Long id) 
	  {
		InstitutionDeletedEvent ev = new InstitutionDeletedEvent(id);
	    ev.entityFound=false;
	    return ev;
	  }

}
