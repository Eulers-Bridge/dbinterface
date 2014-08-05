package com.eulersbridge.iEngage.core.services;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.institutions.CreateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionCreatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDeletedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.studentYear.CreateStudentYearEvent;
import com.eulersbridge.iEngage.core.events.studentYear.StudentYearCreatedEvent;
import com.eulersbridge.iEngage.rest.domain.Institution;


//All methods are guaranteed to return something, null will never be returned.
public interface InstitutionService {
	public InstitutionCreatedEvent createInstitution(CreateInstitutionEvent createInstitutionEvent);
	public ReadInstitutionEvent requestReadInstitution(RequestReadInstitutionEvent requestReadInstitutionEvent);
	public InstitutionUpdatedEvent updateInstitution(UpdateInstitutionEvent updateInstitutionEvent);
	public InstitutionDeletedEvent deleteInstitution(DeleteInstitutionEvent deleteInstitutionEvent);
	public Iterator<Institution> getInstitutions();
	public StudentYearCreatedEvent createStudentYear(CreateStudentYearEvent createStudentYearEvent);

}
