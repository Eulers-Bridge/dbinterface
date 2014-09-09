/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.eulersbridge.iEngage.core.events.users.AuthenticateUserEvent;
import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UpdateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent;
import com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.users.UserUpdatedEvent;
import com.eulersbridge.iEngage.core.events.users.VerifyUserAccountEvent;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import com.eulersbridge.iEngage.database.repository.InstitutionMemoryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserMemoryRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenMemoryRepository;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.security.SecurityConstants;

/**
 * @author Greg Newitt
 *
 */
public class UserEventHandlerTest 
{
	UserService userService;
	private VerificationTokenMemoryRepository tokenRepo;
	private UserRepository userRepo;
	private InstitutionRepository instRepo;
	
	private static Logger LOG = LoggerFactory.getLogger(UserEventHandlerTest.class);
	
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
		HashMap<Long, User> users=DatabaseDataFixture.populateUsers();
		userRepo=new UserMemoryRepository(users);
		HashMap<Long, Institution> institutions=DatabaseDataFixture.populateInstitutions();
		instRepo=new InstitutionMemoryRepository(institutions);
		
		HashMap<Long,VerificationToken> tokens= new HashMap<Long,VerificationToken>();
		tokenRepo=new VerificationTokenMemoryRepository(tokens);
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
		String email="gnewitt2@hotmail.com";
		nADs=new UserDetails(email);
		nADs.setGivenName("Greg");
		nADs.setInstitutionId((long)1);
		createUserEvent=new CreateUserEvent(nADs);
		UserCreatedEvent nace = userService.signUpNewUser(createUserEvent);
		if (null==nace) fail("Not yet implemented");
		VerificationToken token=tokenRepo.findToken();
		VerifyUserAccountEvent verifyUserAccountEvent=new VerifyUserAccountEvent("gnewitt@hotmail.com", token.getToken());
		UserAccountVerifiedEvent test = userService.validateUserAccount(verifyUserAccountEvent);
		assertNotNull("account verified event returned.",test);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#requestReadUser(com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent)}.
	 */
	@Test
	public void testRequestReadUser() {
		RequestReadUserEvent rnae=new RequestReadUserEvent("gnewitt2@hotmail.com");
		assertEquals("1 == 1",rnae.getEmail(),"gnewitt2@hotmail.com");
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
	public void shouldUpdateUser() 
	{
		UserDetails nADs;
		nADs=new UserDetails("gnewitt@hotmail.com");
		nADs.setGivenName("Gregory");
		nADs.setFamilyName("Lawson");
		nADs.setNationality("British");
		nADs.setYearOfBirth("1974");
		nADs.setPersonality("Some");
		nADs.setGender("Female");
		nADs.setInstitutionId((long)1);
		nADs.setPassword("123");
		
		UpdateUserEvent updateUserEvent=new UpdateUserEvent(nADs.getEmail(), nADs);
		UserUpdatedEvent nude = userService.updateUser(updateUserEvent);
		assertNotNull("UserUpdatedEvent returned null",nude);
		assertNotNull("UserDetails returned null",nude.getUserDetails());
		assertEquals("Email address not updated.",nude.getEmail(),nADs.getEmail());
		assertEquals("Nationality not updated.",nude.getUserDetails().getNationality(),nADs.getNationality());
		assertEquals("First name not updated.",nude.getUserDetails().getGivenName(),nADs.getGivenName());
		assertEquals("Last name not updated.",nude.getUserDetails().getFamilyName(),nADs.getFamilyName());
		assertEquals("Year of Birth not updated.",nude.getUserDetails().getYearOfBirth(),nADs.getYearOfBirth());
		assertEquals("Gender not updated.",nude.getUserDetails().getGender(),nADs.getGender());
		assertEquals("Personality of Birth not updated.",nude.getUserDetails().getPersonality(),nADs.getPersonality());
		assertEquals("Password not updated.",nude.getUserDetails().getPassword(),nADs.getPassword());
	}

	@Test
	public void shouldAuthenticateUser()
	{
		User user=DatabaseDataFixture.populateUserGnewitt();
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail(), user.getPassword());
		UserAuthenticatedEvent authEvt=userService.authenticateUser(evt);
		assertTrue("User did not authenticate.",authEvt.isAuthenticated());
		List<GrantedAuthority> auths=authEvt.getGrantedAuths();
		Iterator<GrantedAuthority> iter=auths.iterator();
		String userRole=null;
		while (iter.hasNext())
		{
			GrantedAuthority auth=iter.next();
			String authority=auth.getAuthority();
			if (authority.equals(SecurityConstants.USER_ROLE))
				userRole=SecurityConstants.USER_ROLE;
			LOG.debug("authority - "+authority);
		}
	}
	
	@Test
	public void shouldAuthenticateUserRole()
	{
		User user=DatabaseDataFixture.populateUserGnewitt();
		userRepo.save(user);
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail(), user.getPassword());
		UserAuthenticatedEvent authEvt=userService.authenticateUser(evt);

		List<GrantedAuthority> auths=authEvt.getGrantedAuths();
		Iterator<GrantedAuthority> iter=auths.iterator();
		String userRole=null;
		while (iter.hasNext())
		{
			GrantedAuthority auth=iter.next();
			String authority=auth.getAuthority();
			if (authority.equals(SecurityConstants.USER_ROLE))
				userRole=SecurityConstants.USER_ROLE;
			LOG.debug("authority - "+authority);
		}
		assertEquals("Role should be USER",userRole,SecurityConstants.USER_ROLE);
	}
	
	@Test
	public void shouldAuthenticateUserAndAdminRole()
	{
		User user=DatabaseDataFixture.populateUserGnewitt2();
		user.setAccountVerified(true);
		userRepo.save(user);
		LOG.debug("Roles - "+user.getRoles());
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail(), user.getPassword());
		UserAuthenticatedEvent authEvt=userService.authenticateUser(evt);

		List<GrantedAuthority> auths=authEvt.getGrantedAuths();
		Iterator<GrantedAuthority> iter=auths.iterator();
		String userRole=null;
		String adminRole=null;
		while (iter.hasNext())
		{
			GrantedAuthority auth=iter.next();
			String authority=auth.getAuthority();
			if (authority.equals(SecurityConstants.USER_ROLE))
				userRole=SecurityConstants.USER_ROLE;
			if (authority.equals(SecurityConstants.ADMIN_ROLE))
				adminRole=SecurityConstants.ADMIN_ROLE;
			LOG.debug("authority - "+authority);
		}
		assertEquals("A role should be USER",userRole,SecurityConstants.USER_ROLE);
		assertEquals("A role should be ADMIN",adminRole,SecurityConstants.ADMIN_ROLE);
	}
	
	
	@Test
	public void shouldNotAuthenticateUserDueToPassword()
	{
		User user=DatabaseDataFixture.populateUserGnewitt();
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail(), user.getPassword()+'2');
		UserAuthenticatedEvent auth=userService.authenticateUser(evt);
		assertFalse("User did authenticate.",auth.isAuthenticated());
	}
	@Test
	public void shouldNotAuthenticateUserDueToUserName()
	{
		User user=DatabaseDataFixture.populateUserGnewitt();
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail()+'3', user.getPassword());
		UserAuthenticatedEvent auth=userService.authenticateUser(evt);
		assertFalse("User did authenticate.",auth.isAuthenticated());
	}
	@Test
	public void shouldNotAuthenticateUserDueToUserNotVerified()
	{
		User user=DatabaseDataFixture.populateUserGnewitt2();
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail(), user.getPassword());
		UserAuthenticatedEvent auth=userService.authenticateUser(evt);
		assertFalse("User did authenticate.",auth.isAuthenticated());
	}
}
