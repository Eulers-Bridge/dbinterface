/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UpdateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.users.UserUpdatedEvent;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.InstitutionMemoryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserMemoryRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenMemoryRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenRepository;

/**
 * @author Greg Newitt
 *
 */
public class UserEventHandlerTest 
{
	UserService userService;
	private VerificationTokenRepository tokenRepo;
	private UserRepository userRepo;
	private InstitutionRepository instRepo;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		HashMap<Long, User> users=new HashMap<Long, User>();
		User user=new User("gnewitt@hotmail.com", "Greg", "Newitt", "Male", "Australian", "1971", "None", "test123");
		users.put(new Long(1), user);
		userRepo=new UserMemoryRepository(users);
		instRepo=new InstitutionMemoryRepository();
		tokenRepo=new VerificationTokenMemoryRepository();
		userService=new UserEventHandler(userRepo, instRepo, tokenRepo);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#UserEventHandler(com.eulersbridge.iEngage.database.repository.UserRepository, com.eulersbridge.iEngage.database.repository.InstitutionRepository, com.eulersbridge.iEngage.database.repository.VerificationTokenRepository)}.
	 */
	@Test
	public void testUserEventHandler() 
	{
		UserEventHandler userService2=new UserEventHandler(userRepo, instRepo, tokenRepo);
		assertNotNull("newsService not being created by constructor.",userService2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#signUpNewUser(com.eulersbridge.iEngage.core.events.users.CreateUserEvent)}.
	 */
	@Test
	public void testSignUpNewUser() 
	{
		CreateUserEvent createUserEvent;
		UserDetails nADs;
		String email="gnewitt@hotmail.com";
		nADs=new UserDetails(email);
		nADs.setFirstName("Greg");
		createUserEvent=new CreateUserEvent(nADs);
		UserCreatedEvent nace = userService.signUpNewUser(createUserEvent);
		if (null==nace) fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#requestReadUser(com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent)}.
	 */
	@Test
	public void testRequestReadUser() {
		RequestReadUserEvent rnae=new RequestReadUserEvent("gnewitt@hotmail.com");
		assertEquals("1 == 1",rnae.getEmail(),"gnewitt@hotmail.com");
		ReadUserEvent rane=userService.requestReadUser(rnae);
		if (null==rane)
			fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#deleteUser(com.eulersbridge.iEngage.core.events.users.DeleteUserEvent)}.
	 */
	@Test
	public void testDeleteUser() 
	{
		DeleteUserEvent deleteUserEvent=new DeleteUserEvent("gnewitt@hotmail.com");
		UserDeletedEvent nUDe = userService.deleteUser(deleteUserEvent);
		if (null==nUDe)	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#updateUser(com.eulersbridge.iEngage.core.events.users.UpdateUserEvent)}.
	 */
	@Test
	public void testUpdateUser() 
	{
		UserDetails nADs;
		nADs=new UserDetails("gnewitt@bigfoot.com");
		nADs.setFirstName("Greg");
		nADs.setLastName("Newitt");
		nADs.setNationality("British");;
		nADs.setYearOfBirth("1971");
		
		UpdateUserEvent updateUserEvent=new UpdateUserEvent(nADs.getEmail(), nADs);
		UserUpdatedEvent nude = userService.updateUser(updateUserEvent);
		if (null==nude) fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#validateUserAccount(com.eulersbridge.iEngage.core.events.users.VerifyUserAccountEvent)}.
	 */
	@Test
	public void testValidateUserAccount() {
		fail("Not yet implemented");
	}

}
