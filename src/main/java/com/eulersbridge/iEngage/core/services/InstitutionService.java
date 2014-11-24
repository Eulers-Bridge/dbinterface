package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoReadEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.ReadGeneralInfoEvent;
import com.eulersbridge.iEngage.core.events.institutions.*;
import com.eulersbridge.iEngage.core.events.newsFeed.CreateNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.ReadNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedCreatedEvent;
import com.eulersbridge.iEngage.database.domain.NewsFeed;


//All methods are guaranteed to return something, null will never be returned.
public interface InstitutionService {
	public InstitutionCreatedEvent createInstitution(CreateInstitutionEvent createInstitutionEvent);
	public ReadEvent requestReadInstitution(RequestReadInstitutionEvent requestReadInstitutionEvent);
	public UpdatedEvent updateInstitution(UpdateInstitutionEvent updateInstitutionEvent);
	public DeletedEvent deleteInstitution(DeleteInstitutionEvent deleteInstitutionEvent);
	public InstitutionsReadEvent readInstitutions(ReadInstitutionsEvent readInstitutionsEvent);
	public NewsFeedCreatedEvent createNewsFeed(CreateNewsFeedEvent createStudentYearEvent);
	public ReadEvent readNewsFeed(ReadNewsFeedEvent readStudentYearEvent);
	public NewsFeed getNewsFeed(Long institutionId);
	public GeneralInfoReadEvent getGeneralInfo(ReadGeneralInfoEvent readGeneralInfoEvent);
}
