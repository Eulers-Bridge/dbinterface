package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.database.domain.User;

//All methods are guaranteed to return something, null will never be returned.
public interface UserService 
{
	  public User signUpNewUser(User newUser, Long InstitutionId);
	  public User findUserByEmail(String emailAddress);

}
