package com.eulersbridge.iEngage.core.services;

import org.springframework.security.access.prepost.PreAuthorize;

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
	public UserAuthenticatedEvent authenticateUser(AuthenticateUserEvent authUserEvent);
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestReadUserEvent.getEmail()==authentication.name)")
	public PersonalityAddedEvent addPersonality(AddPersonalityEvent addPersonalityEvent);
}
