package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.database.domain.Student;

//All methods are guaranteed to return something, null will never be returned.
public interface UserService 
{
	  public Student signUpNewUser(Student newUser, Long InstitutionId);
	  public Student findUserByEmail(String emailAddress);

}
