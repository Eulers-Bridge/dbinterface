package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class CountryDeletedEvent extends DeletedEvent 
{
	  private Long id;
	  private CountryDetails details;
	  private boolean deletionCompleted;

	  private CountryDeletedEvent(Long id) 
	  {
		    this.id = id;
	  }

	  public CountryDeletedEvent(Long id, CountryDetails details) {
		this.id = id;
	    this.details = details;
	    this.deletionCompleted = true;
	  }

	  public Long getId() {
	    return id;
	  }

	  public CountryDetails getDetails() {
	    return details;
	  }

	  public boolean isDeletionCompleted() {
	    return deletionCompleted;
	  }

	  public static CountryDeletedEvent deletionForbidden(Long id, CountryDetails details) {
		CountryDeletedEvent ev = new CountryDeletedEvent(id, details);
	    ev.entityFound=true;
	    ev.deletionCompleted=false;
	    return ev;
	  }

	  public static CountryDeletedEvent notFound(Long id) 
	  {
		CountryDeletedEvent ev = new CountryDeletedEvent(id);
	    ev.entityFound=false;
	    return ev;
	  }

}
