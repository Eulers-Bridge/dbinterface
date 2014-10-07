/**
 * 
 */
package com.eulersbridge.iEngage.core.events.studentYear;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadStudentYearEvent extends RequestReadEvent 
{
	private Long id;

	public ReadStudentYearEvent(Long id) 
	{
	    this.id = id;
	}

	public Long getStudentYearId() 
	{
	    return id;
	}

}
