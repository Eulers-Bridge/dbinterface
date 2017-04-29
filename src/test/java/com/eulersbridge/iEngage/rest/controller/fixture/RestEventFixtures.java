package com.eulersbridge.iEngage.rest.controller.fixture;

import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import com.eulersbridge.iEngage.database.domain.VerificationToken.VerificationTokenType;
import com.eulersbridge.iEngage.email.EmailVerification;

import static com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture.customEmailUser;

public class RestEventFixtures 
{
/*	public static CreateUserEvent userStatusNotFound(String email)
	{
		return CreateUserEvent.notFound(email);
	}
*/	public static CreateUserEvent userSignedUp(String email)
	{
		return new CreateUserEvent(customEmailUser(email));
	}
	public static ReadUserEvent requestUserEvent(String email)
	{
		return new ReadUserEvent(email,customEmailUser(email));
	}
	
	public static UserCreatedEvent populateUserCreatedEvent()
	{
		User user=DatabaseDataFixture.populateUserGnewitt2();
		ReadUserEvent readData=RestDataFixture.customEmailUser2(user.getEmail());
		UserDetails dets=(UserDetails) readData.getDetails();
		int expirationTimeInMinutes=60;
		VerificationTokenType tokenType=VerificationTokenType.emailVerification;
		VerificationToken token=new VerificationToken(tokenType, user, expirationTimeInMinutes);
		EmailVerification verifyEmail=new EmailVerification(null, user, token);
		UserCreatedEvent testData=new UserCreatedEvent(readData.getEmail(), dets,verifyEmail);
		return testData;
	}
	

}
