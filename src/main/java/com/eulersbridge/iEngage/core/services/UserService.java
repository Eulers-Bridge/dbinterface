package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
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
import com.eulersbridge.iEngage.core.events.users.UserUpdatedEvent;
import com.eulersbridge.iEngage.core.events.users.VerifyUserAccountEvent;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.DeleteVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.ReadVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordAddedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDeletedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordReadEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.AddVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.ReadVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDeletedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderReadEvent;

//All methods are guaranteed to return something, null will never be returned.
public interface UserService 
{
	public UserCreatedEvent signUpNewUser(CreateUserEvent createUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public ReadUserEvent requestReadUser(RequestReadUserEvent requestReadUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public UserUpdatedEvent updateUser(UpdateUserEvent updateUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent);
	public UserAccountVerifiedEvent validateUserAccount(VerifyUserAccountEvent verifyUserAccountEvent);
	public UserAuthenticatedEvent authenticateUser(AuthenticateUserEvent authUserEvent) throws AuthenticationException;
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public PersonalityAddedEvent addPersonality(AddPersonalityEvent addPersonalityEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public VoteReminderAddedEvent addVoteReminder(AddVoteReminderEvent addVoteReminderEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public VoteRecordAddedEvent addVoteRecord(AddVoteRecordEvent addVoteRecordEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public VoteRecordReadEvent readVoteRecord(ReadVoteRecordEvent readVoteRecordEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public VoteReminderReadEvent readVoteReminder(ReadVoteReminderEvent any);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public VoteRecordDeletedEvent deleteVoteRecord(DeleteVoteRecordEvent deleteVoteRecordEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public VoteReminderDeletedEvent deleteVoteReminder(DeleteVoteReminderEvent deleteVoteReminderEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public LikedEvent like(LikeEvent likeEvent);
    @PreAuthorize("hasRole('ROLE_USER')")
    public LikedEvent unlike(LikeEvent likeEvent);
}
