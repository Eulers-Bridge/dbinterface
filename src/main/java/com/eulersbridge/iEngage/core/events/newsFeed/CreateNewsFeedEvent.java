/**
 * 
 */
package com.eulersbridge.iEngage.core.events.studentYear;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Greg Newitt
 *
 */
public class CreateStudentYearEvent extends CreateEvent 
{
	StudentYearDetails studentYearDetails;
	Long studentYearId;
	
    private static Logger LOG = LoggerFactory.getLogger(CreateStudentYearEvent.class);
    
    public CreateStudentYearEvent(Long id, StudentYearDetails studentYearDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateStudentYearEvent("+id+","+studentYearDetails+") = ");
		studentYearDetails.setNodeId(id);
		this.studentYearDetails=studentYearDetails;
	}

	public CreateStudentYearEvent(StudentYearDetails studentYearDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateStudentYearEvent("+studentYearDetails+") = ");
		this.studentYearDetails=studentYearDetails;
	}

	public StudentYearDetails getStudentYearDetails() {
		return this.studentYearDetails;
	}

	public void setStudentYearDetails(StudentYearDetails studentYearDetails) {
		this.studentYearDetails = studentYearDetails;
	}

}
