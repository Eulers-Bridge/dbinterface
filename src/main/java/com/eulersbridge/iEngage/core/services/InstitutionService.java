package com.eulersbridge.iEngage.core.services;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoReadEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.ReadGeneralInfoEvent;
import com.eulersbridge.iEngage.core.events.institutions.*;
import com.eulersbridge.iEngage.core.events.studentYear.CreateStudentYearEvent;
import com.eulersbridge.iEngage.core.events.studentYear.ReadStudentYearEvent;
import com.eulersbridge.iEngage.core.events.studentYear.StudentYearCreatedEvent;
import com.eulersbridge.iEngage.core.events.studentYear.StudentYearReadEvent;
import com.eulersbridge.iEngage.database.domain.StudentYear;


//All methods are guaranteed to return something, null will never be returned.
public interface InstitutionService {
	public InstitutionCreatedEvent createInstitution(CreateInstitutionEvent createInstitutionEvent);
	public ReadInstitutionEvent requestReadInstitution(RequestReadInstitutionEvent requestReadInstitutionEvent);
	public InstitutionUpdatedEvent updateInstitution(UpdateInstitutionEvent updateInstitutionEvent);
	public InstitutionDeletedEvent deleteInstitution(DeleteInstitutionEvent deleteInstitutionEvent);
	public InstitutionsReadEvent readInstitutions(ReadInstitutionsEvent readInstitutionsEvent);
	public StudentYearCreatedEvent createStudentYear(CreateStudentYearEvent createStudentYearEvent);
	public StudentYearReadEvent readStudentYear(ReadStudentYearEvent readStudentYearEvent);
	public Iterator<StudentYear> getStudentYears(Long institutionId);
	public GeneralInfoReadEvent getGeneralInfo(ReadGeneralInfoEvent readGeneralInfoEvent);
}
