package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UpdateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.UserUpdatedEvent;
import com.eulersbridge.iEngage.core.events.users.VerifyUserAccountEvent;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent;

//All methods are guaranteed to return something, null will never be returned.
public interface UserService 
{
	public UserCreatedEvent signUpNewUser(CreateUserEvent createUserEvent);
	public ReadUserEvent requestReadUser(RequestReadUserEvent requestReadUserEvent);
	public UserUpdatedEvent updateUser(UpdateUserEvent updateUserEvent);
	public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent);
	public UserAccountVerifiedEvent validateUserAccount(VerifyUserAccountEvent verifyUserAccountEvent);

}
