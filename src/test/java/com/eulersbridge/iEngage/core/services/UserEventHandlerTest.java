/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent;
import com.eulersbridge.iEngage.core.events.users.AuthenticateUserEvent;
import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityAddedEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityDetails;
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
import com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.DeleteVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.ReadVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordAddedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails;
import com.eulersbridge.iEngage.core.events.voteReminder.AddVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.ReadVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDetails;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderReadEvent;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import com.eulersbridge.iEngage.database.domain.VerificationToken.VerificationTokenType;
import com.eulersbridge.iEngage.database.domain.VoteRecord;
import com.eulersbridge.iEngage.database.domain.VoteReminder;
import com.eulersbridge.iEngage.database.repository.InstitutionMemoryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.PersonalityMemoryRepository;
import com.eulersbridge.iEngage.database.repository.PersonalityRepository;
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
	private PersonalityRepository personRepo;
	
    @Mock
    VerificationTokenMemoryRepository tRepo;
    @Mock
	InstitutionRepository iRepo;
    @Mock
    UserRepository uRepo;
    @Mock
    PersonalityRepository pRepo;

    UserEventHandler userServiceMocked;

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
		personRepo=new PersonalityMemoryRepository();
		HashMap<Long, Institution> institutions=DatabaseDataFixture.populateInstitutions();
		instRepo=new InstitutionMemoryRepository(institutions);
		
		HashMap<Long,VerificationToken> tokens= new HashMap<Long,VerificationToken>();
		tokenRepo=new VerificationTokenMemoryRepository(tokens);
		userService=new UserEventHandler(userRepo, personRepo, instRepo, tokenRepo);
		MockitoAnnotations.initMocks(this);

		userServiceMocked=new UserEventHandler(uRepo,pRepo,iRepo,tRepo);
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
		UserEventHandler userService2=new UserEventHandler(userRepo, personRepo, instRepo, tokenRepo);
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
		assertNull("Not yet implemented",nace);
		VerificationToken token=tokenRepo.findToken();
		VerifyUserAccountEvent verifyUserAccountEvent=new VerifyUserAccountEvent("gnewitt@hotmail.com", token.getEncodedTokenString());
		UserAccountVerifiedEvent test = userService.validateUserAccount(verifyUserAccountEvent);
		assertNotNull("account verified event returned.",test);
	}

	@Test
	public void testSignUpNewUserNullInstReturned() 
	{
		CreateUserEvent createUserEvent;
		UserDetails nADs;
		String email="gnewitt2@hotmail.com";
		nADs=new UserDetails(email);
		nADs.setGivenName("Greg");
		nADs.setInstitutionId((long)1);
		createUserEvent=new CreateUserEvent(nADs);
		Institution instData=null;
		User userData=null;
		when(iRepo.findOne(any(Long.class))).thenReturn(instData);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);

		UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
		assertNotNull(nace);
		assertFalse(nace.isInstituteFound());
	}
	@Test
	public void testSignUpNewUserExistingUserReturned() 
	{
		CreateUserEvent createUserEvent;
		UserDetails nADs;
		String email="gnewitt2@hotmail.com";
		nADs=new UserDetails(email);
		nADs.setGivenName("Greg");
		nADs.setInstitutionId((long)1);
		createUserEvent=new CreateUserEvent(nADs);
		Institution instData=DatabaseDataFixture.populateInstUniMelb();
		User userData=DatabaseDataFixture.populateUserGnewitt();
		when(iRepo.findOne(any(Long.class))).thenReturn(instData);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);

		UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
		assertNotNull(nace);
		assertTrue(nace.isInstituteFound());
		assertFalse(nace.isUserUnique());
	}
	
	@Test
	public void testValidateUserAccountNullToken()
	{
		VerifyUserAccountEvent verifyUserAccountEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		String token="testToken";
		verifyUserAccountEvent=new VerifyUserAccountEvent(userData.getEmail(), token);
		VerificationToken tokData=null;
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

		UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
		assertNotNull(nace);
		assertEquals(nace.getEmail(),userData.getEmail());
		assertEquals(nace.getUserDetails(),userData.toUserDetails());
		assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenDoesntExists,nace.getVerificationError());
	}

	@Test
	public void testValidateUserAccountDifferentUserToken()
	{
		VerifyUserAccountEvent verifyUserAccountEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		User userData2=DatabaseDataFixture.populateUserGnewitt2();
		String token="testToken";
		verifyUserAccountEvent=new VerifyUserAccountEvent(userData.getEmail(), token);
		VerificationToken tokData=new VerificationToken(VerificationTokenType.emailVerification, userData2, 60);
		tokData.setVerified(true);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

		UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
		assertNotNull(nace);
		assertEquals(nace.getEmail(),userData.getEmail());
		assertEquals(nace.getUserDetails(),userData.toUserDetails());
		assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenUserMismatch,nace.getVerificationError());
	}

	@Test
	public void testValidateUserAccountUserAlreadyVerified()
	{
		VerifyUserAccountEvent verifyUserAccountEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		userData.setAccountVerified(true);
		String token="testToken";
		verifyUserAccountEvent=new VerifyUserAccountEvent(userData.getEmail(), token);
		VerificationToken tokData=new VerificationToken(VerificationTokenType.emailVerification, userData, 60);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

		UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
		assertNotNull(nace);
		assertEquals(nace.getEmail(),userData.getEmail());
		assertEquals(nace.getUserDetails(),userData.toUserDetails());
		assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed,nace.getVerificationError());
	}

	@Test
	public void testValidateUserAccountTokenAlreadyUsed()
	{
		VerifyUserAccountEvent verifyUserAccountEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		String token="testToken";
		verifyUserAccountEvent=new VerifyUserAccountEvent(userData.getEmail(), token);
		VerificationToken tokData=new VerificationToken(VerificationTokenType.emailVerification, userData, 60);
		tokData.setVerified(true);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

		UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
		assertNotNull(nace);
		assertEquals(nace.getEmail(),userData.getEmail());
		assertEquals(nace.getUserDetails(),userData.toUserDetails());
		assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed,nace.getVerificationError());
	}

	@Test
	public void testValidateUserAccountTokenTypeWrong()
	{
		VerifyUserAccountEvent verifyUserAccountEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		userData.setAccountVerified(false);
		String token="testToken";
		verifyUserAccountEvent=new VerifyUserAccountEvent(userData.getEmail(), token);
		VerificationToken tokData=new VerificationToken(VerificationTokenType.emailRegistration, userData, 60);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

		UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
		assertNotNull(nace);
		assertEquals(nace.getEmail(),userData.getEmail());
		assertEquals(nace.getUserDetails(),userData.toUserDetails());
		assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenTypeMismatch,nace.getVerificationError());
	}

	@Test
	public void testValidateUserAccountTokenExpired()
	{
		VerifyUserAccountEvent verifyUserAccountEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		userData.setAccountVerified(false);
		String token="testToken";
		verifyUserAccountEvent=new VerifyUserAccountEvent(userData.getEmail(), token);
		VerificationToken tokData=new VerificationToken(VerificationTokenType.emailVerification, userData, -10);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

		UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
		assertNotNull(nace);
		assertEquals(nace.getEmail(),userData.getEmail());
		assertEquals(nace.getUserDetails(),userData.toUserDetails());
		assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenExpired,nace.getVerificationError());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#requestReadUser(com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent)}.
	 */
	@Test
	public void testRequestReadUserNullUser() {
		RequestReadUserEvent rnae=new RequestReadUserEvent("gnewitt2@hotmail.com");
		assertEquals("1 == 1",rnae.getEmail(),"gnewitt2@hotmail.com");
		ReadUserEvent rane=userService.requestReadUser(rnae);
		assertNotNull("Not yet implemented",rane);
	}

	@Test
	public void testRequestReadUser() 
	{
		
		User userData=DatabaseDataFixture.populateUserGnewitt();
		RequestReadUserEvent rnae=new RequestReadUserEvent(userData.getEmail());
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		ReadUserEvent rane=userServiceMocked.requestReadUser(rnae);

		assertNotNull(rane);
		assertEquals(rane.getEmail(),userData.getEmail());
		assertEquals(rane.getDetails(),userData.toUserDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#deleteUser(com.eulersbridge.iEngage.core.events.users.DeleteUserEvent)}.
	 */
	@Test
	public void testDeleteUser() 
	{
		DeleteUserEvent deleteUserEvent=new DeleteUserEvent("gnewitt@hotmail.com");
		UserDeletedEvent nUDe = userService.deleteUser(deleteUserEvent);
		assertNotNull("Not yet implemented",nUDe);
	}

	@Test
	public void testDeleteUserNotFound() 
	{
		
		User userData=DatabaseDataFixture.populateUserGnewitt();
		DeleteUserEvent deleteUserEvent=new DeleteUserEvent(userData.getEmail());
		when(uRepo.findByEmail(any(String.class))).thenReturn(null);
		UserDeletedEvent nUDe = userServiceMocked.deleteUser(deleteUserEvent);

		assertNotNull(nUDe);
		assertFalse(nUDe.isEntityFound());
		assertFalse(nUDe.isDeletionCompleted());
		assertEquals(nUDe.getEmail(),userData.getEmail());
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
		nADs.setGender("Female");
		nADs.setInstitutionId((long)1);
		nADs.setPassword("123");
		
		UpdateUserEvent updateUserEvent=new UpdateUserEvent(nADs.getEmail(), nADs);
		UserUpdatedEvent nude = (UserUpdatedEvent) userService.updateUser(updateUserEvent);
		assertNotNull("UserUpdatedEvent returned null",nude);
		assertNotNull("UserDetails returned null",nude.getDetails());
		assertEquals("Email address not updated.",nude.getEmail(),nADs.getEmail());
		assertEquals("Nationality not updated.",((UserDetails)nude.getDetails()).getNationality(),nADs.getNationality());
		assertEquals("First name not updated.",((UserDetails)nude.getDetails()).getGivenName(),nADs.getGivenName());
		assertEquals("Last name not updated.",((UserDetails)nude.getDetails()).getFamilyName(),nADs.getFamilyName());
		assertEquals("Year of Birth not updated.",((UserDetails)nude.getDetails()).getYearOfBirth(),nADs.getYearOfBirth());
		assertEquals("Gender not updated.",((UserDetails)nude.getDetails()).getGender(),nADs.getGender());
		assertEquals("Password not updated.",((UserDetails)nude.getDetails()).getPassword(),nADs.getPassword());
	}

	@Test
	public void shouldAuthenticateUser()
	{
		User user=DatabaseDataFixture.populateUserGnewitt();
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail(), user.getPassword());
		UserAuthenticatedEvent authEvt=userService.authenticateUser(evt);
		assertTrue("User did not authenticate.",authEvt.isAuthenticated());
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
	public void shouldAuthenticateUserAdminContentManagerReturningOfficerRole()
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
		String retOfficerRole=null;
		String contentMgrRole=null;
		while (iter.hasNext())
		{
			GrantedAuthority auth=iter.next();
			String authority=auth.getAuthority();
			if (authority.equals(SecurityConstants.USER_ROLE))
				userRole=SecurityConstants.USER_ROLE;
			if (authority.equals(SecurityConstants.ADMIN_ROLE))
				adminRole=SecurityConstants.ADMIN_ROLE;
			if (authority.equals(SecurityConstants.CONTENT_MANAGER_ROLE))
				contentMgrRole=SecurityConstants.CONTENT_MANAGER_ROLE;
			if (authority.equals(SecurityConstants.RETURNING_OFFICER_ROLE))
				retOfficerRole=SecurityConstants.RETURNING_OFFICER_ROLE;
			LOG.debug("authority - "+authority);
		}
		assertEquals("A role should be USER",userRole,SecurityConstants.USER_ROLE);
		assertEquals("A role should be ADMIN",adminRole,SecurityConstants.ADMIN_ROLE);
		assertEquals("A role should be CONTENT_MANAGER",contentMgrRole,SecurityConstants.CONTENT_MANAGER_ROLE);
		assertEquals("A role should be RETURNING_OFFICER",retOfficerRole,SecurityConstants.RETURNING_OFFICER_ROLE);
	}
	
	
	@Test
	public void shouldNotAuthenticateUserDueToPassword()
	{
		User user=DatabaseDataFixture.populateUserGnewitt();
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail(), user.getPassword()+'2');
		boolean exception=false;
		try
		{
			userService.authenticateUser(evt);
		}
		catch (BadCredentialsException e)
		{
			assertNotNull(e);
			exception=true;
			
		}
		assertTrue("Exception not thrown.",exception);
	}

	@Test
	public void shouldNotAuthenticateUserDueToUserName()
	{
		User user=DatabaseDataFixture.populateUserGnewitt();
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail()+'3', user.getPassword());
		boolean exception=false;
		try
		{
			userService.authenticateUser(evt);
		}
		catch (UsernameNotFoundException e)
		{
			assertNotNull(e);
			exception=true;
			
		}
		assertTrue("Exception not thrown.",exception);
	}

	@Test
	public void shouldNotAuthenticateUserDueToUserNotVerified()
	{
		User user=DatabaseDataFixture.populateUserGnewitt2();
		AuthenticateUserEvent evt=new AuthenticateUserEvent(user.getEmail(), user.getPassword());
		
		when(uRepo.findByEmail(any(String.class))).thenReturn(user);

		boolean exception=false;
		try
		{
			userServiceMocked.authenticateUser(evt);
		}
		catch (DisabledException e)
		{
			assertNotNull(e);
			exception=true;
			
		}
		assertTrue("Exception not thrown.",exception);
	}

	@Test
	public void shouldAddPersonalityToUser() 
	{
		User user = DatabaseDataFixture.populateUserGnewitt();
		PersonalityDetails details = new PersonalityDetails(null, 4.2F, 3.2F, 1.7F, 2.9F, 3.9F);
		AddPersonalityEvent addEvt = new AddPersonalityEvent(user.getEmail(),
				details);
		PersonalityAddedEvent evtAdd=userService.addPersonality(addEvt);
		assertNotNull("",evtAdd.getPersonalityDetails().getPersonalityId());
		assertEquals("",evtAdd.getPersonalityDetails().getAgreeableness(),details.getAgreeableness());
		assertEquals("",evtAdd.getPersonalityDetails().getConscientiousness(),details.getConscientiousness());
		assertEquals("",evtAdd.getPersonalityDetails().getEmotionalStability(),details.getEmotionalStability());
		assertEquals("",evtAdd.getPersonalityDetails().getExtroversion(),details.getExtroversion());
		assertEquals("",evtAdd.getPersonalityDetails().getOpeness(),details.getOpeness());
	}

	@Test
	public void shouldNotAddPersonalityToUserNotFound() 
	{
		User user = DatabaseDataFixture.populateUserGnewitt2();
		PersonalityDetails details = new PersonalityDetails(null, 4.2F, 3.2F, 1.7F, 2.9F, 3.9F);
		AddPersonalityEvent addEvt = new AddPersonalityEvent(user.getEmail(),
				details);
		PersonalityAddedEvent evtAdd=userService.addPersonality(addEvt);
		assertNotNull("",evtAdd);
		assertFalse("",evtAdd.isUserFound());
	}

	@Ignore
	@Test
	public void shouldNotAddPersonalityToUserAlreadyHasPersonality() 
	{
		// TODO
	}
	
	@Test
	public void shouldAddVoteReminderToUser() 
	{
		AddVoteReminderEvent addVoteReminderEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		VoteReminder vr=DatabaseDataFixture.populateVoteReminder1();
		VoteReminderDetails vrd=vr.toVoteReminderDetails();
		addVoteReminderEvent=new AddVoteReminderEvent(vrd);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(uRepo.addVoteReminder(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

		VoteReminderAddedEvent nace = userServiceMocked.addVoteReminder(addVoteReminderEvent);
		assertNotNull(nace);
		assertEquals(nace.getDetails(),vrd);
		assertTrue(nace.isElectionFound());
		assertTrue(nace.isUserFound());
	}
	
	@Test
	public void shouldAddVoteReminderToUserUserNotFound() 
	{
		AddVoteReminderEvent addVoteReminderEvent;
		VoteReminder vr=DatabaseDataFixture.populateVoteReminder1();
		VoteReminderDetails vrd=vr.toVoteReminderDetails();
		addVoteReminderEvent=new AddVoteReminderEvent(vrd);
		when(uRepo.findByEmail(any(String.class))).thenReturn(null);
		when(uRepo.addVoteReminder(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

		VoteReminderAddedEvent nace = userServiceMocked.addVoteReminder(addVoteReminderEvent);
		assertNotNull(nace);
		assertTrue(nace.isElectionFound());
		assertFalse(nace.isUserFound());
	}

	@Test
	public void shouldAddVoteReminderToUserElectionNotFound() 
	{
		AddVoteReminderEvent addVoteReminderEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		VoteReminder vr=DatabaseDataFixture.populateVoteReminder1();
		VoteReminderDetails vrd=vr.toVoteReminderDetails();
		addVoteReminderEvent=new AddVoteReminderEvent(vrd);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(uRepo.addVoteReminder(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(null);

		VoteReminderAddedEvent nace = userServiceMocked.addVoteReminder(addVoteReminderEvent);
		assertNotNull(nace);
		assertFalse(nace.isElectionFound());
		assertTrue(nace.isUserFound());
	}
	@Test
	public void shouldAddVoteRecordToUser() 
	{
		AddVoteRecordEvent addVoteRecordEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		VoteRecord vr=DatabaseDataFixture.populateVoteRecord1();
		VoteRecordDetails vrd=vr.toVoteRecordDetails();
		addVoteRecordEvent=new AddVoteRecordEvent(vrd);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(uRepo.addVoteRecord(any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

		VoteRecordAddedEvent nace = userServiceMocked.addVoteRecord(addVoteRecordEvent);
		assertNotNull(nace);
		assertEquals(nace.getDetails(),vrd);
		assertTrue(nace.isElectionFound());
		assertTrue(nace.isUserFound());
	}
	
	@Test
	public void shouldAddVoteRecordToUserUserNotFound() 
	{
		AddVoteRecordEvent addVoteRecordEvent;
		VoteRecord vr=DatabaseDataFixture.populateVoteRecord1();
		VoteRecordDetails vrd=vr.toVoteRecordDetails();
		addVoteRecordEvent=new AddVoteRecordEvent(vrd);
		when(uRepo.findByEmail(any(String.class))).thenReturn(null);
		when(uRepo.addVoteRecord(any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

		VoteRecordAddedEvent nace = userServiceMocked.addVoteRecord(addVoteRecordEvent);
		assertNotNull(nace);
		assertTrue(nace.isElectionFound());
		assertFalse(nace.isUserFound());
	}

	@Test
	public void shouldAddVoteRecordToUserElectionNotFound() 
	{
		AddVoteRecordEvent addVoteRecordEvent;
		User userData=DatabaseDataFixture.populateUserGnewitt();
		VoteRecord vr=DatabaseDataFixture.populateVoteRecord1();
		VoteRecordDetails vrd=vr.toVoteRecordDetails();
		addVoteRecordEvent=new AddVoteRecordEvent(vrd);
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
		when(uRepo.addVoteRecord(any(Long.class), any(Long.class), any(String.class))).thenReturn(null);

		VoteRecordAddedEvent nace = userServiceMocked.addVoteRecord(addVoteRecordEvent);
		assertNotNull(nace);
		assertFalse(nace.isElectionFound());
		assertTrue(nace.isUserFound());
	}
	
	@Test
	public void shouldReadVoteReminder() 
	{
		ReadVoteReminderEvent readVoteReminderEvent;
		Long id=1l;
		VoteReminder vr=DatabaseDataFixture.populateVoteReminder1();
		VoteReminderDetails vrd=vr.toVoteReminderDetails();
		readVoteReminderEvent=new ReadVoteReminderEvent(id);
		when(uRepo.readVoteReminder(any(Long.class))).thenReturn(vr);

		VoteReminderReadEvent nace = (VoteReminderReadEvent) userServiceMocked.readVoteReminder(readVoteReminderEvent);
		assertNotNull(nace);
		assertEquals(nace.getDetails(),vrd);
		assertTrue(nace.isEntityFound());
		assertEquals(nace.getNodeId(),id);
	}
	
	@Test
	public void shouldReadVoteReminderNotFound() 
	{
		ReadVoteReminderEvent readVoteReminderEvent;
		Long id=1l;
		readVoteReminderEvent=new ReadVoteReminderEvent(id);
		when(uRepo.readVoteReminder(any(Long.class))).thenReturn(null);

		ReadEvent nace = userServiceMocked.readVoteReminder(readVoteReminderEvent);
		assertNotNull(nace);
		assertNull(nace.getDetails());
		assertFalse(nace.isEntityFound());
		assertEquals(nace.getNodeId(),id);
	}
	
	@Test
	public void shouldReadVoteRecord() 
	{
		ReadVoteRecordEvent readVoteRecordEvent;
		Long id=1l;
		VoteRecord vr=DatabaseDataFixture.populateVoteRecord1();
		VoteRecordDetails vrd=vr.toVoteRecordDetails();
		readVoteRecordEvent=new ReadVoteRecordEvent(id);
		when(uRepo.readVoteRecord(any(Long.class))).thenReturn(vr);

		ReadEvent nace = userServiceMocked.readVoteRecord(readVoteRecordEvent);
		assertNotNull(nace);
		assertEquals(nace.getDetails(),vrd);
		assertTrue(nace.isEntityFound());
		assertEquals(nace.getNodeId(),id);
	}
	
	@Test
	public void shouldReadVoteRecordNotFound() 
	{
		ReadVoteRecordEvent readVoteRecordEvent;
		Long id=1l;
		readVoteRecordEvent=new ReadVoteRecordEvent(id);
		when(uRepo.readVoteReminder(any(Long.class))).thenReturn(null);

		ReadEvent nace = userServiceMocked.readVoteRecord(readVoteRecordEvent);
		assertNotNull(nace);
		assertNull(nace.getDetails());
		assertFalse(nace.isEntityFound());
		assertEquals(nace.getNodeId(),id);
	}
	
	@Test
	public void shouldDeleteVoteRecord() 
	{
		DeleteVoteRecordEvent deleteVoteRecordEvent;
		Long id=1l;
		VoteRecord vr=DatabaseDataFixture.populateVoteRecord1();
		deleteVoteRecordEvent=new DeleteVoteRecordEvent(id);
		when(uRepo.deleteVoteRecord(any(Long.class))).thenReturn(vr);

		DeletedEvent nace = userServiceMocked.deleteVoteRecord(deleteVoteRecordEvent);
		assertNotNull(nace);
		assertTrue(nace.isEntityFound());
		assertTrue(nace.isDeletionCompleted());
		assertEquals(nace.getNodeId(),id);
	}
	
	@Test
	public void shouldDeleteVoteRecordNotFound() 
	{
		DeleteVoteRecordEvent deleteVoteRecordEvent;
		Long id=1l;
		deleteVoteRecordEvent=new DeleteVoteRecordEvent(id);
		when(uRepo.deleteVoteRecord(any(Long.class))).thenReturn(null);

		DeletedEvent nace = userServiceMocked.deleteVoteRecord(deleteVoteRecordEvent);
		assertNotNull(nace);
		assertFalse(nace.isEntityFound());
		assertFalse(nace.isDeletionCompleted());
		assertEquals(nace.getNodeId(),id);
	}
	
	@Test
	public void shouldDeleteVoteReminder() 
	{
		DeleteVoteReminderEvent deleteVoteReminderEvent;
		Long id=1l;
		VoteReminder vr=DatabaseDataFixture.populateVoteReminder1();
		deleteVoteReminderEvent=new DeleteVoteReminderEvent(id);
		when(uRepo.deleteVoteReminder(any(Long.class))).thenReturn(vr);

		DeletedEvent nace = userServiceMocked.deleteVoteReminder(deleteVoteReminderEvent);
		assertNotNull(nace);
		assertTrue(nace.isEntityFound());
		assertTrue(nace.isDeletionCompleted());
		assertEquals(nace.getNodeId(),id);
	}
	
	@Test
	public void shouldDeleteVoteReminderNotFound() 
	{
		DeleteVoteReminderEvent deleteVoteReminderEvent;
		Long id=1l;
		deleteVoteReminderEvent=new DeleteVoteReminderEvent(id);
		when(uRepo.deleteVoteReminder(any(Long.class))).thenReturn(null);

		DeletedEvent nace = userServiceMocked.deleteVoteReminder(deleteVoteReminderEvent);
		assertNotNull(nace);
		assertFalse(nace.isEntityFound());
		assertFalse(nace.isDeletionCompleted());
		assertEquals(nace.getNodeId(),id);
	}
	
	@Test
	public void shouldLoadUserByUsername() 
	{
		User userData=DatabaseDataFixture.populateUserGnewitt();
		when(uRepo.findByEmail(any(String.class))).thenReturn(userData);

		org.springframework.security.core.userdetails.UserDetails  nace = userServiceMocked.loadUserByUsername(userData.getEmail());
		assertNotNull(nace);
		assertTrue(nace.isAccountNonExpired());
		assertTrue(nace.isAccountNonLocked());
		assertTrue(nace.isCredentialsNonExpired());
		assertTrue(nace.isEnabled());
		assertEquals(userData.getEmail(),nace.getUsername());
		assertEquals(userData.getPassword(),nace.getPassword());
	}
	
	@Test
	public void shouldLoadUserByUsernameUserNotFound() 
	{
		User userData=DatabaseDataFixture.populateUserGnewitt();
		when(uRepo.findByEmail(any(String.class))).thenReturn(null);
		boolean exceptionThrown=false;
		try
		{
			userServiceMocked.loadUserByUsername(userData.getEmail());
		}
		catch (UsernameNotFoundException e)
		{
			exceptionThrown=true;
		}
		assertTrue(exceptionThrown);
	}
}
