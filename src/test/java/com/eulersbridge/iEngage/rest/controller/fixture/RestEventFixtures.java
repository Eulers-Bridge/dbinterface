package com.eulersbridge.iEngage.rest.controller.fixture;

import com.eulersbridge.iEngage.core.events.users.*;

import static com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture.*;

public class RestEventFixtures 
{
/*	public static CreateUserEvent userStatusNotFound(String email)
	{
		return CreateUserEvent.notFound(email);
	}
*/	public static CreateUserEvent userSignedUp(String email)
	{
		return new CreateUserEvent(email,customEmailUser(email));
	}
	public static ReadUserEvent requestUserEvent(String email)
	{
		return new ReadUserEvent(email,customEmailUser(email));
	}
}
