package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 21/09/14.
 */
public interface PollService {

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent requestReadPoll(RequestReadPollEvent requestReadPollEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public PollCreatedEvent createPoll(CreatePollEvent createPollEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deletePoll(DeletePollEvent deletePollEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updatePoll(UpdatePollEvent updatePollEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public PollAnswerCreatedEvent answerPoll(CreatePollAnswerEvent pollAnswerEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public AllReadEvent findPolls(ReadAllEvent readPollsEvent,
                                Direction sortDirection, int pageNumber, int pageLength);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readPollResult(ReadPollResultEvent ReadPollResultEvent);
}
