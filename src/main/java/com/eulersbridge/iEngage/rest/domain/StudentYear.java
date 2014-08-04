package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.studentYear.StudentYearDetails;
import com.eulersbridge.iEngage.rest.controller.InstitutionController;

public class StudentYear extends ResourceSupport
{
	Long nodeId;
	String year;
	Long start;
	Long end;
	Long institutionId;
	
	public StudentYear()
	{
		
	}
	
	public StudentYear(String year,Long start, Long end, Long institutionId)
	{
		this.year=year;
		this.start=start;
		this.end=end;
		this.institutionId=institutionId;
	}
	
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the start
	 */
	public Long getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Long start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public Long getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(Long end) {
		this.end = end;
	}
	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return institutionId;
	}
	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
	public StudentYearDetails toStudentYearDetails() 
	  {
		StudentYearDetails details = new StudentYearDetails(year,start,end,institutionId);
		details.setNodeId(nodeId);

	    return details;
	  }

	  // {!begin fromOrderDetails}
	  public static StudentYear fromStudentYearDetails(StudentYearDetails readStudentYear) 
	  {
		  StudentYear studentYear = new StudentYear();

		  studentYear.nodeId = readStudentYear.getNodeId();
		  studentYear.year = readStudentYear.getYear();
		  studentYear.start = readStudentYear.getStart();
		  studentYear.end = readStudentYear.getEnd();
		  studentYear.institutionId = readStudentYear.getInstitutionId();
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
		  studentYear.add(linkTo(InstitutionController.class).slash(studentYear.nodeId).withSelfRel());
	    // {!end selfRel}

	    return studentYear;
	  }


}
