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

import java.util.List;

/**
 * @author Greg Newitt
 */
public interface ContactRequestService {
  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent createContactRequest(String userEmail, String targetEmail);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent readContactRequestsMade(String userEmail);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent readContactRequestsReceived(String userEmail);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent acceptContactRequest(String userEmail, Long requestId);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent rejectContactRequest(String userEmail, Long requestId);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent readFriendsList(String userEmail);

//  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
//  public ReadEvent readContactRequestByUserIdContactNumber(ReadContactRequestEvent readContactRequestEvent);
//

//

//

//


}
