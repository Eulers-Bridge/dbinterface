/**
 * 
 */
package com.eulersbridge.iEngage.core.events.studentYear;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 *
 */
public class StudentYearCreatedEvent extends CreatedEvent 
{
	private StudentYearDetails studentYearDetails;
	private Long id;
	private boolean institutionFound=true;
	
	public StudentYearCreatedEvent(Long id, StudentYearDetails studentYearDetails) 
	{
		this.studentYearDetails=studentYearDetails;
		this.id=id;
	}

	public StudentYearCreatedEvent(Long id) 
	{
		this.id=id;
	}

	/**
	 * @return the studentYearDetails
	 */
	public StudentYearDetails getStudentYearDetails() {
		return studentYearDetails;
	}

	/**
	 * @param studentYearDetails the studentYearDetails to set
	 */
	public void setStudentYearDetails(StudentYearDetails studentYearDetails) {
		this.studentYearDetails = studentYearDetails;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound() {
		return institutionFound;
	}

	/**
	 * @param institutionFound the institutionFound to set
	 */
	public void setInstitutionFound(boolean institutionFound) {
		this.institutionFound = institutionFound;
	}

	public static StudentYearCreatedEvent institutionNotFound(Long nodeId) 
	{
		StudentYearCreatedEvent ev = new StudentYearCreatedEvent(nodeId);
	    ev.setInstitutionFound(false);
	    return ev;
	}

}
