/**
 * 
 */
package com.eulersbridge.iEngage.core.events.studentYear;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class StudentYearReadEvent extends ReadEvent 
{
	private Long id;
	private StudentYearDetails readStudentYearDetails;
	
	public StudentYearReadEvent(Long id) 
	{
		this.id = id;
	}

	  public StudentYearReadEvent(Long id, StudentYearDetails readStudentYearDetails) 
	  {
		  this.id = id;
		  this.readStudentYearDetails = readStudentYearDetails;
	  }

	  public Long getNewsArticleId() 
	  {
		  return this.id;
	  }

	  public StudentYearDetails getReadStudentYearDetails() 
	  {
		  return readStudentYearDetails;
	  }

	  public static StudentYearReadEvent notFound(Long id) 
	  {
		  StudentYearReadEvent ev = new StudentYearReadEvent(id);
		  ev.entityFound=false;
		  return ev;
	  }

}
