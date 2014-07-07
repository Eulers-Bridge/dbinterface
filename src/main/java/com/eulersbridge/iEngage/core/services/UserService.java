package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.database.domain.User;

//All methods are guaranteed to return something, null will never be returned.
public interface UserService 
{
	  public User signUpNewUser(UserDetails newUser);
	  public User findUser(String email);
	  public ReadUserEvent requestReadUser(RequestReadUserEvent requestReadUserEvent);

}
