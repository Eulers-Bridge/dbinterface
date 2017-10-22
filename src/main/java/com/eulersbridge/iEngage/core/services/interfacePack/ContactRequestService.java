/**
 *
 */
package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Greg Newitt
 */
public interface ContactRequestService {
  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent createContactRequest(String userEmail, String targetEmail);

//  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
//  public ReadEvent readContactRequest(ReadContactRequestEvent readContactRequestEvent);
//
//  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
//  public ReadEvent readContactRequestByUserIdContactNumber(ReadContactRequestEvent readContactRequestEvent);
//
//  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
//  public UpdatedEvent acceptContactRequest(AcceptContactRequestEvent acceptContactRequestEvent);
//
//  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
//  UpdatedEvent rejectContactRequest(UpdateEvent acceptContactRequestEvent);
//
//  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
//  public AllReadEvent readContactRequestsReceived(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);
//
//  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
//  public AllReadEvent readContactRequestsMade(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);

}
