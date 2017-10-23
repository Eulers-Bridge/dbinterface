package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.users.*;
import com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.DeleteVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.ReadVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.ReadVoteReminderEvent;
import com.eulersbridge.iEngage.rest.domain.PPSEQuestions;
import com.eulersbridge.iEngage.rest.domain.VoteReminderDomain;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

//All methods are guaranteed to return something, null will never be returned.
public interface UserService extends UserDetailsService {
  public UserCreatedEvent signUpNewUser(CreateUserEvent createUserEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #requestReadUserEvent.getEmail()==authentication.name)")
  public ReadUserEvent readUser(RequestReadUserEvent requestReadUserEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadUserEvent readUserByContactNumber(RequestReadUserEvent requestReadUserEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadUserEvent readUserByContactEmail(RequestReadUserEvent requestReadUserEvent);

  // For search api
  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public SearchUserEvent searchUserProfileByName(RequestSearchUserEvent requestSearchUserEvent);

  //TODO Need to secure this better.
  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadUserEvent readUserById(RequestReadUserEvent requestReadUserEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #updateUserEvent.getEmail()==authentication.name)")
  public UpdatedEvent updateUser(UpdateUserEvent updateUserEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #deleteUserEvent.getEmail()==authentication.name)")
  public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent);

  public UserAccountVerifiedEvent validateUserAccount(VerifyUserAccountEvent verifyUserAccountEvent);

  public UserAuthenticatedEvent authenticateUser(AuthenticateUserEvent authUserEvent) throws AuthenticationException;

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #addPersonalityEvent.getEmail()==authentication.name)")
  public PersonalityAddedEvent addPersonality(AddPersonalityEvent addPersonalityEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #userEmail==authentication.name)")
  public RequestHandledEvent addPPSEQuestions(String userEmail, PPSEQuestions ppseQuestions);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #voteReminder.getUserEmail()==authentication.name)")
  public RequestHandledEvent addVoteReminder(VoteReminderDomain voteReminder);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #addVoteRecordEvent.getVoteRecordDetails().getVoterId()==authentication.name)")
  public VoteRecordAddedEvent addVoteRecord(AddVoteRecordEvent addVoteRecordEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readVoteRecord(ReadVoteRecordEvent readVoteRecordEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readVoteReminder(ReadVoteReminderEvent any);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public DeletedEvent deleteVoteRecord(DeleteVoteRecordEvent deleteVoteRecordEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or hasRole('" + SecurityConstants.USER_ROLE + "')")
  public DeletedEvent deleteVoteReminder(DeleteVoteReminderEvent deleteVoteReminderEvent);

  public Long findUserId(String emailAddress);

  public AllReadEvent readExistingContactsById(ReadAllEvent readUserProfilesEvent, Direction sortDirection, int pageNumber, int pageLength);

  public AllReadEvent readExistingContactsByEmail(RequestReadUserEvent readUserProfilesEvent, Direction sortDirection, int pageNumber, int pageLength);

  public AllReadEvent readSupportsById(ReadAllEvent userEvent, Direction sortDirection, int pageNumber, int pageLength);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #requestReadUserEvent.getEmail()==authentication.name)")
  public AllReadEvent readSupportsByEmail(RequestReadUserEvent requestReadUserEvent, Direction sortDirection, int pageNumber, int pageLength);

  public AllReadEvent readVoteRemindersById(ReadAllEvent userEvent, Direction sortDirection, int pageNumber, int pageLength);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #requestReadUserEvent.getEmail()==authentication.name)")
  public AllReadEvent readVoteRemindersByEmail(RequestReadUserEvent requestReadUserEvent, Direction sortDirection, int pageNumber, int pageLength);

  public AllReadEvent readVoteRecordsById(ReadAllEvent userEvent, Direction sortDirection, int pageNumber, int pageLength);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #requestReadUserEvent.getEmail()==authentication.name)")
  public AllReadEvent readVoteRecordsByEmail(RequestReadUserEvent requestReadUserEvent, Direction sortDirection, int pageNumber, int pageLength);

  UserCreatedEvent resendVerificationEmail(RequestReadUserEvent createUserEvent);

  public RequestHandledEvent requestResetPWD(String email);

  public RequestHandledEvent resetPwd(String email, String token, String newPwd);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "') or (hasRole('" + SecurityConstants.USER_ROLE + "') and #userEmail==authentication.name)")
  public void updateSNSTokens(String userEmail, String topicArn, String deviceToken);
}

