package com.eulersbridge.iEngage.rest.controller.fixture;

import com.eulersbridge.iEngage.core.events.users.*;

import static com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture.*;

public class RestEventFixtures 
{
	public static UserSignUpEvent userStatusNotFound(String email)
	{
		return UserSignUpEvent.notFound(email);
	}
	public static UserSignUpEvent userSignedUp(String email)
	{
		return new UserSignUpEvent(email,customEmailUser(email));
	}
	public static ReadUserEvent requestUserEvent(String email)
	{
		return new ReadUserEvent(email,customEmailUser(email));
	}
}
