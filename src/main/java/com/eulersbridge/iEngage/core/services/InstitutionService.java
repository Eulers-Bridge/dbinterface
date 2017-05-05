package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoReadEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.ReadGeneralInfoEvent;
import com.eulersbridge.iEngage.core.events.institutions.*;
import com.eulersbridge.iEngage.core.events.newsFeed.CreateNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.ReadNewsFeedEvent;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.security.access.prepost.PreAuthorize;


//All methods are guaranteed to return something, null will never be returned.
public interface InstitutionService {
  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "')")
  public InstitutionCreatedEvent createInstitution(CreateInstitutionEvent createInstitutionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent requestReadInstitution(RequestReadInstitutionEvent requestReadInstitutionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updateInstitution(UpdateInstitutionEvent updateInstitutionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deleteInstitution(DeleteInstitutionEvent deleteInstitutionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public InstitutionsReadEvent readInstitutions(ReadAllEvent readInstitutionsEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "')")
  public NewsFeedCreatedEvent createNewsFeed(CreateNewsFeedEvent createStudentYearEvent);

  public ReadEvent readNewsFeed(ReadNewsFeedEvent readStudentYearEvent);

  public NewsFeed getNewsFeed(Long institutionId);

  public GeneralInfoReadEvent getGeneralInfo(ReadGeneralInfoEvent readGeneralInfoEvent);
}
