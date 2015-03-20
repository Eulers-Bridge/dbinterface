package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;

import com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent;
import com.eulersbridge.iEngage.core.events.users.AuthenticateUserEvent;
import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityAddedEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UpdateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.VerifyUserAccountEvent;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.DeleteVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.ReadVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.AddVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.ReadVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderAddedEvent;

//All methods are guaranteed to return something, null will never be returned.
public interface UserService 
{
	public UserCreatedEvent signUpNewUser(CreateUserEvent createUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public ReadUserEvent readUser(RequestReadUserEvent requestReadUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ReadUserEvent readUserByContactNumber(RequestReadUserEvent requestReadUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ReadUserEvent readUserByContactEmail(RequestReadUserEvent requestReadUserEvent);
//TODO Need to secure this better.
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ReadUserEvent readUserById(RequestReadUserEvent requestReadUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #updateUserEvent.getEmail()==authentication.name)")
	public UpdatedEvent updateUser(UpdateUserEvent updateUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #deleteUserEvent.getEmail()==authentication.name)")
	public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent);
	public UserAccountVerifiedEvent validateUserAccount(VerifyUserAccountEvent verifyUserAccountEvent);
	public UserAuthenticatedEvent authenticateUser(AuthenticateUserEvent authUserEvent) throws AuthenticationException;
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public PersonalityAddedEvent addPersonality(AddPersonalityEvent addPersonalityEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #addVoteReminderEvent.getVoteReminderDetails().getUserId()==authentication.name)")
	public VoteReminderAddedEvent addVoteReminder(AddVoteReminderEvent addVoteReminderEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #addVoteRecordEvent.getVoteRecordDetails().getVoterId()==authentication.name)")
	public VoteRecordAddedEvent addVoteRecord(AddVoteRecordEvent addVoteRecordEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ReadEvent readVoteRecord(ReadVoteRecordEvent readVoteRecordEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ReadEvent readVoteReminder(ReadVoteReminderEvent any);
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public DeletedEvent deleteVoteRecord(DeleteVoteRecordEvent deleteVoteRecordEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public DeletedEvent deleteVoteReminder(DeleteVoteReminderEvent deleteVoteReminderEvent);
	
	public Long findUserId(String emailAddress);
	AllReadEvent readExistingContacts(ReadAllEvent readUserProfilesEvent,Direction sortDirection, int pageNumber, int pageLength);
}
