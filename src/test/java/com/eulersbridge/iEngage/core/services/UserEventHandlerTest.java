/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.users.*;
import com.eulersbridge.iEngage.core.events.voteRecord.*;
import com.eulersbridge.iEngage.core.events.voteReminder.*;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.domain.VerificationToken.VerificationTokenType;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.PersonalityRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenRepository;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Greg Newitt
 */
public class UserEventHandlerTest {
  @Mock
  VerificationTokenRepository tRepo;
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
  public static void setUpBeforeClass() throws Exception {
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
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    userServiceMocked = new UserEventHandler(new BCryptPasswordEncoder(), uRepo, pRepo, iRepo, tRepo, null, null);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#(com.eulersbridge.iEngage.database.repository.UserRepository, com.eulersbridge.iEngage.database.repository.InstitutionRepository, com.eulersbridge.iEngage.database.repository.VerificationTokenRepository)}.
   */
  @Test
  public void testUserEventHandler() {
    UserEventHandler userService2 = new UserEventHandler(null, uRepo, pRepo, iRepo, tRepo, null, null);
    assertNotNull("newsService not being created by constructor.", userService2);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#signUpNewUser(com.eulersbridge.iEngage.core.events.users.CreateUserEvent)}.
   */
  @Test
  public void testSignUpNewUser() {
    CreateUserEvent createUserEvent;
    UserDetails nADs;
    String email = "joeblogs@hotmail.com";
    nADs = new UserDetails(email);
    nADs.setGivenName("Joe");
    nADs.setFamilyName("Blogs");
    nADs.setPassword("pass");
    nADs.setInstitutionId((long) 1);
    createUserEvent = new CreateUserEvent(nADs);
    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    User user = User.fromUserDetails(nADs);
    User user2 = User.fromUserDetails(nADs);
    user.setInstitution(new Institution(nADs.getInstitutionId()));
    user2.setInstitution(new Institution(nADs.getInstitutionId()));
    user2.setNodeId(543l);

    when(iRepo.findOne(any(Long.class))).thenReturn(inst);
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);
    when(uRepo.save(any(User.class))).thenReturn(user2);
    when(uRepo.findOne(any(Long.class))).thenReturn(user2);
    when(tRepo.save(any(VerificationToken.class))).thenReturn(null);

    UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
    assertNotNull("Not yet implemented", nace);

    VerificationTokenType tokenType = VerificationTokenType.emailVerification;
    int expirationTimeInMinutes = 20;
    VerificationToken token = new VerificationToken(tokenType, user2, expirationTimeInMinutes);
    when(tRepo.findByToken(any(String.class))).thenReturn(token);
    VerifyUserAccountEvent verifyUserAccountEvent = new VerifyUserAccountEvent("gnewitt@hotmail.com", token.getEncodedTokenString());
    UserAccountVerifiedEvent test = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull("account verified event returned.", test);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#signUpNewUser(com.eulersbridge.iEngage.core.events.users.CreateUserEvent)}.
   */
  @Test
  public void testSignUpNewUserNotAnExistingUserValidInst() {
    CreateUserEvent createUserEvent;
    UserDetails nADs;
    String email = "joeblogs@hotmail.com";
    nADs = new UserDetails(email);
    nADs.setGivenName("Joe");
    nADs.setFamilyName("Blogs");
    nADs.setPassword("pass");
    nADs.setInstitutionId((long) 1);
    createUserEvent = new CreateUserEvent(nADs);
    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    User user = User.fromUserDetails(nADs);
    user.setInstitution(new Institution(nADs.getInstitutionId()));
    user.setNodeId(543l);

    when(iRepo.findOne(any(Long.class), anyInt())).thenReturn(inst);
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    when(uRepo.save(any(User.class))).thenReturn(user);
    when(uRepo.findOne(any(Long.class))).thenReturn(user);
    when(tRepo.save(any(VerificationToken.class))).thenReturn(null);

    UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
    assertNotNull("Not yet implemented", nace);
    assertEquals(nace.getNodeId(), user.getNodeId());
    assertEquals(nace.getEmail(), user.getEmail());
    assertEquals(nace.getDetails(), user.toUserDetails());

    VerificationTokenType tokenType = VerificationTokenType.emailVerification;
    int expirationTimeInMinutes = 20;
    VerificationToken token = new VerificationToken(tokenType, user, expirationTimeInMinutes);
    when(tRepo.findByToken(any(String.class))).thenReturn(token);
    VerifyUserAccountEvent verifyUserAccountEvent = new VerifyUserAccountEvent("gnewitt@hotmail.com", token.getEncodedTokenString());
    UserAccountVerifiedEvent test = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull("account verified event returned.", test);
  }

  @Test
  public void testSignUpNewUserNullProvidedReturned() {
    CreateUserEvent createUserEvent;
    UserDetails nADs = null;
    createUserEvent = new CreateUserEvent(nADs);

    UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
    assertNull(nace);
  }

  @Test
  public void testSignUpNewUserNullEmailProvidedReturned() {
    CreateUserEvent createUserEvent;
    UserDetails nADs;
    String email = null;
    nADs = new UserDetails(email);
    createUserEvent = new CreateUserEvent(nADs);
    Institution instData = null;
    User userData = null;
    when(iRepo.findOne(any(Long.class))).thenReturn(instData);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);

    UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
    assertNull(nace);
  }

  @Test
  public void testSignUpNewUserNullInstProvidedReturned() {
    CreateUserEvent createUserEvent;
    UserDetails nADs;
    String email = "joeblogs@hotmail.com";
    nADs = new UserDetails(email);
    nADs.setGivenName("Joe");
    nADs.setFamilyName("Blogs");
    nADs.setPassword("pass");
    createUserEvent = new CreateUserEvent(nADs);

    UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
    assertNotNull(nace);
    assertFalse(nace.isInstituteFound());
  }

  @Test
  public void testSignUpNewUserNullInstReturned() {
    CreateUserEvent createUserEvent;
    UserDetails nADs;
    String email = "joeblogs@hotmail.com";
    nADs = new UserDetails(email);
    nADs.setGivenName("Joe");
    nADs.setFamilyName("Blogs");
    nADs.setPassword("pass");
    nADs.setInstitutionId((long) 1);
    createUserEvent = new CreateUserEvent(nADs);
    Institution instData = null;
    User userData = null;
    when(iRepo.findOne(any(Long.class))).thenReturn(instData);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);

    UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
    assertNotNull(nace);
    assertFalse(nace.isInstituteFound());
  }

  @Test
  public void testSignUpNewUserExistingUserReturned() {
    CreateUserEvent createUserEvent;
    UserDetails nADs;
    String email = "joeblogs@hotmail.com";
    nADs = new UserDetails(email);
    nADs.setGivenName("Joe");
    nADs.setFamilyName("Blogs");
    nADs.setPassword("pass");
    nADs.setInstitutionId((long) 1);
    createUserEvent = new CreateUserEvent(nADs);
    Institution instData = DatabaseDataFixture.populateInstUniMelb();
    User userData = DatabaseDataFixture.populateUserGnewitt();
    when(iRepo.findOne(any(Long.class), anyInt())).thenReturn(instData);
    when(uRepo.findByEmail(any(String.class), anyInt())).thenReturn(userData);

    UserCreatedEvent nace = userServiceMocked.signUpNewUser(createUserEvent);
    assertNotNull(nace);
    assertTrue(nace.isInstituteFound());
    assertFalse(nace.isUserUnique());
  }

  @Test
  public void testValidateUserAccount() {
    VerifyUserAccountEvent verifyUserAccountEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    userData.setAccountVerified(false);
    String token = "testToken";
    verifyUserAccountEvent = new VerifyUserAccountEvent(userData.getEmail(), token);
    VerificationToken tokData = new VerificationToken(VerificationTokenType.emailVerification, userData, 60);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(tRepo.findByToken(any(String.class))).thenReturn(tokData);
    when(uRepo.save(any(User.class), anyInt())).thenReturn(userData);
    when(tRepo.save(any(VerificationToken.class), anyInt())).thenReturn(tokData);

    UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull(nace);
    assertEquals(nace.getEmail(), userData.getEmail());
    assertEquals(nace.getUserDetails(), userData.toUserDetails());
    assertTrue(nace.isAccountVerified());
    assertNull(nace.getVerificationError());
  }

  @Test
  public void testValidateUserAccountNullToken() {
    VerifyUserAccountEvent verifyUserAccountEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    String token = "testToken";
    verifyUserAccountEvent = new VerifyUserAccountEvent(userData.getEmail(), token);
    VerificationToken tokData = null;
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

    UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull(nace);
    assertEquals(nace.getEmail(), userData.getEmail());
    assertEquals(nace.getUserDetails(), userData.toUserDetails());
    assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenDoesntExists, nace.getVerificationError());
  }

  @Test
  public void testValidateUserAccountDifferentUserToken() {
    VerifyUserAccountEvent verifyUserAccountEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    User userData2 = DatabaseDataFixture.populateUserGnewitt2();
    String token = "testToken";
    verifyUserAccountEvent = new VerifyUserAccountEvent(userData.getEmail(), token);
    VerificationToken tokData = new VerificationToken(VerificationTokenType.emailVerification, userData2, 60);
    tokData.setVerified(true);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

    UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull(nace);
    assertEquals(nace.getEmail(), userData.getEmail());
    assertEquals(nace.getUserDetails(), userData.toUserDetails());
    assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenUserMismatch, nace.getVerificationError());
  }

  @Test
  public void testValidateUserAccountUserAlreadyVerified() {
    VerifyUserAccountEvent verifyUserAccountEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    userData.setAccountVerified(true);
    String token = "testToken";
    verifyUserAccountEvent = new VerifyUserAccountEvent(userData.getEmail(), token);
    VerificationToken tokData = new VerificationToken(VerificationTokenType.emailVerification, userData, 60);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

    UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull(nace);
    assertEquals(nace.getEmail(), userData.getEmail());
    assertEquals(nace.getUserDetails(), userData.toUserDetails());
    assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed, nace.getVerificationError());
  }

  @Test
  public void testValidateUserAccountTokenAlreadyUsed() {
    VerifyUserAccountEvent verifyUserAccountEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    String token = "testToken";
    verifyUserAccountEvent = new VerifyUserAccountEvent(userData.getEmail(), token);
    VerificationToken tokData = new VerificationToken(VerificationTokenType.emailVerification, userData, 60);
    tokData.setUser(userData);
    tokData.setVerified(true);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

    UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull(nace);
    assertEquals(nace.getEmail(), userData.getEmail());
    assertEquals(nace.getUserDetails(), userData.toUserDetails());
    assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed, nace.getVerificationError());
  }

  @Test
  public void testValidateUserAccountTokenTypeWrong() {
    VerifyUserAccountEvent verifyUserAccountEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    userData.setAccountVerified(false);
    String token = "testToken";
    verifyUserAccountEvent = new VerifyUserAccountEvent(userData.getEmail(), token);
    VerificationToken tokData = new VerificationToken(VerificationTokenType.emailRegistration, userData, 60);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

    UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull(nace);
    assertEquals(nace.getEmail(), userData.getEmail());
    assertEquals(nace.getUserDetails(), userData.toUserDetails());
    assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenTypeMismatch, nace.getVerificationError());
  }

  @Test
  public void testValidateUserAccountTokenExpired() {
    VerifyUserAccountEvent verifyUserAccountEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    userData.setAccountVerified(false);
    String token = "testToken";
    verifyUserAccountEvent = new VerifyUserAccountEvent(userData.getEmail(), token);
    VerificationToken tokData = new VerificationToken(VerificationTokenType.emailVerification, userData, -10);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(tRepo.findByToken(any(String.class))).thenReturn(tokData);

    UserAccountVerifiedEvent nace = userServiceMocked.validateUserAccount(verifyUserAccountEvent);
    assertNotNull(nace);
    assertEquals(nace.getEmail(), userData.getEmail());
    assertEquals(nace.getUserDetails(), userData.toUserDetails());
    assertEquals(UserAccountVerifiedEvent.VerificationErrorType.tokenExpired, nace.getVerificationError());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#readUser(com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent)}.
   */
  @Test
  public void testRequestReadUserNullUser() {
    RequestReadUserEvent rnae = new RequestReadUserEvent("gnewitt2@hotmail.com");
    assertEquals("1 == 1", rnae.getEmail(), "gnewitt2@hotmail.com");
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    ReadUserEvent rane = userServiceMocked.readUser(rnae);
    assertNotNull("Not yet implemented", rane);
    assertFalse(rane.isEntityFound());
    assertEquals(rane.getEmail(), rnae.getEmail());
  }

  @Test
  public void testRequestReadUser() {
    User userData = DatabaseDataFixture.populateUserGnewitt();
    RequestReadUserEvent rnae = new RequestReadUserEvent(userData.getEmail());
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    ReadUserEvent rane = userServiceMocked.readUser(rnae);

    assertNotNull(rane);
    assertEquals(rane.getEmail(), userData.getEmail());
    assertEquals(rane.getDetails(), userData.toUserDetails());
  }

  @Test
  public void testRequestReadUserNullRequest() {
    RequestReadUserEvent rnae = null;
    ReadUserEvent rane = userServiceMocked.readUser(rnae);

    assertNotNull(rane);
    assertNull(rane.getNodeId());
    assertNull(rane.getEmail());
    assertNull(rane.getDetails());
    assertFalse(rane.isEntityFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#deleteUser(com.eulersbridge.iEngage.core.events.users.DeleteUserEvent)}.
   */
  @Test
  public void testDeleteUser() {
    User userData = DatabaseDataFixture.populateUserGnewitt();
    DeleteUserEvent deleteUserEvent = new DeleteUserEvent(userData.getEmail());
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    doNothing().when(uRepo).delete(any(Long.class));
    UserDeletedEvent nUDe = userServiceMocked.deleteUser(deleteUserEvent);

    assertNotNull(nUDe);
    assertTrue(nUDe.isEntityFound());
    assertTrue(nUDe.isDeletionCompleted());
    assertEquals(nUDe.getEmail(), userData.getEmail());

  }

  @Test
  public void testDeleteUserNotFound() {

    User userData = DatabaseDataFixture.populateUserGnewitt();
    DeleteUserEvent deleteUserEvent = new DeleteUserEvent(userData.getEmail());
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    UserDeletedEvent nUDe = userServiceMocked.deleteUser(deleteUserEvent);

    assertNotNull(nUDe);
    assertFalse(nUDe.isEntityFound());
    assertFalse(nUDe.isDeletionCompleted());
    assertEquals(nUDe.getEmail(), userData.getEmail());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.UserEventHandler#updateUser(com.eulersbridge.iEngage.core.events.users.UpdateUserEvent)}.
   */
  @Test
  public void shouldUpdateUser() {
    User user = DatabaseDataFixture.populateUserGnewitt2();
    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    UserDetails nADs;
    nADs = new UserDetails("gnewitt@hotmail.com");
    nADs.setGivenName("Gregory");
    nADs.setFamilyName("Lawson");
    nADs.setNationality("British");
    nADs.setYearOfBirth("1974");
    nADs.setGender("Female");
    nADs.setInstitutionId((long) 1);
    nADs.setPassword("123");

    User user1 = User.fromUserDetails(nADs);
    user1.setInstitution(new Institution(nADs.getInstitutionId()));

    UpdateUserEvent updateUserEvent = new UpdateUserEvent(nADs.getEmail(), nADs);
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);
    when(iRepo.findOne(any(Long.class))).thenReturn(inst);
    when(uRepo.save(any(User.class), anyInt())).thenReturn(user1);

    UserUpdatedEvent nude = (UserUpdatedEvent) userServiceMocked.updateUser(updateUserEvent);
    assertNotNull("UserUpdatedEvent returned null", nude);
    assertNotNull("UserDetails returned null", nude.getDetails());
    assertEquals("Email address not updated.", nude.getEmail(), nADs.getEmail());
    assertEquals("Nationality not updated.", ((UserDetails) nude.getDetails()).getNationality(), nADs.getNationality());
    assertEquals("First name not updated.", ((UserDetails) nude.getDetails()).getGivenName(), nADs.getGivenName());
    assertEquals("Last name not updated.", ((UserDetails) nude.getDetails()).getFamilyName(), nADs.getFamilyName());
    assertEquals("Year of Birth not updated.", ((UserDetails) nude.getDetails()).getYearOfBirth(), nADs.getYearOfBirth());
    assertEquals("Gender not updated.", ((UserDetails) nude.getDetails()).getGender(), nADs.getGender());
    assertEquals("Password not updated.", ((UserDetails) nude.getDetails()).getPassword(), nADs.getPassword());
  }

  @Test
  public void shouldUpdateUserNullInstitution() {
    User user = DatabaseDataFixture.populateUserGnewitt2();
    UserDetails nADs;
    nADs = new UserDetails("gnewitt@hotmail.com");
    nADs.setGivenName("Gregory");
    nADs.setFamilyName("Lawson");
    nADs.setNationality("British");
    nADs.setYearOfBirth("1974");
    nADs.setGender("Female");
    nADs.setInstitutionId((long) 1);
    nADs.setPassword("123");

    UpdateUserEvent updateUserEvent = new UpdateUserEvent(nADs.getEmail(), nADs);
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);
    when(iRepo.findOne(any(Long.class))).thenReturn(null);

    UserUpdatedEvent nude = (UserUpdatedEvent) userServiceMocked.updateUser(updateUserEvent);
    assertNotNull("UserUpdatedEvent returned null", nude);
    assertFalse(nude.isEntityFound());
    assertNull(nude.getDetails());
    assertNull(nude.getNodeId());
    assertEquals(nude.getEmail(), nADs.getEmail());
  }

  @Test
  public void shouldUpdateUserNoExistingUser() {
    Institution inst = DatabaseDataFixture.populateInstUniMelb();
    UserDetails nADs;
    nADs = new UserDetails("gnewitt@hotmail.com");
    nADs.setGivenName("Gregory");
    nADs.setFamilyName("Lawson");
    nADs.setNationality("British");
    nADs.setYearOfBirth("1974");
    nADs.setGender("Female");
    nADs.setInstitutionId((long) 1);
    nADs.setPassword("123");
    User user = User.fromUserDetails(nADs);
    user.setInstitution(new Institution(nADs.getInstitutionId()));

    UpdateUserEvent updateUserEvent = new UpdateUserEvent(nADs.getEmail(), nADs);
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    when(iRepo.findOne(any(Long.class))).thenReturn(inst);
    when(uRepo.save(any(User.class), anyInt())).thenReturn(user);

    UserUpdatedEvent nude = (UserUpdatedEvent) userServiceMocked.updateUser(updateUserEvent);
    assertNotNull("UserUpdatedEvent returned null", nude);
    assertNotNull("UserDetails returned null", nude.getDetails());
    assertEquals("Email address not updated.", nude.getEmail(), nADs.getEmail());
    assertEquals("Nationality not updated.", ((UserDetails) nude.getDetails()).getNationality(), nADs.getNationality());
    assertEquals("First name not updated.", ((UserDetails) nude.getDetails()).getGivenName(), nADs.getGivenName());
    assertEquals("Last name not updated.", ((UserDetails) nude.getDetails()).getFamilyName(), nADs.getFamilyName());
    assertEquals("Year of Birth not updated.", ((UserDetails) nude.getDetails()).getYearOfBirth(), nADs.getYearOfBirth());
    assertEquals("Gender not updated.", ((UserDetails) nude.getDetails()).getGender(), nADs.getGender());
    assertEquals("Password not updated.", ((UserDetails) nude.getDetails()).getPassword(), nADs.getPassword());
  }

  @Test
  public void shouldAuthenticateUser() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    AuthenticateUserEvent evt = new AuthenticateUserEvent(user.getEmail(), user.getPassword());
    when(uRepo.findByEmail(any(String.class), anyInt())).thenReturn(user);
    UserAuthenticatedEvent authEvt = userServiceMocked.authenticateUser(evt);
    assertTrue("User did not authenticate.", authEvt.isAuthenticated());
  }

  @Test
  public void shouldAuthenticateUserRole() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    uRepo.save(user);
    AuthenticateUserEvent evt = new AuthenticateUserEvent(user.getEmail(), user.getPassword());
    when(uRepo.findByEmail(any(String.class), anyInt())).thenReturn(user);
    UserAuthenticatedEvent authEvt = userServiceMocked.authenticateUser(evt);

    List<GrantedAuthority> auths = authEvt.getGrantedAuths();
    Iterator<GrantedAuthority> iter = auths.iterator();
    String userRole = null;
    while (iter.hasNext()) {
      GrantedAuthority auth = iter.next();
      String authority = auth.getAuthority();
      if (authority.equals(SecurityConstants.USER_ROLE))
        userRole = SecurityConstants.USER_ROLE;
      LOG.debug("authority - " + authority);
    }
    assertEquals("Role should be USER", userRole, SecurityConstants.USER_ROLE);
  }

  @Test
  public void shouldAuthenticateUserAdminContentManagerReturningOfficerRole() {
    User user = DatabaseDataFixture.populateUserGnewitt2();
    user.setAccountVerified(true);
    uRepo.save(user);
    LOG.debug("Roles - " + user.getRoles());
    AuthenticateUserEvent evt = new AuthenticateUserEvent(user.getEmail(), user.getPassword());
    when(uRepo.findByEmail(any(String.class), anyInt())).thenReturn(user);
    UserAuthenticatedEvent authEvt = userServiceMocked.authenticateUser(evt);

    List<GrantedAuthority> auths = authEvt.getGrantedAuths();
    Iterator<GrantedAuthority> iter = auths.iterator();
    String userRole = null;
    String adminRole = null;
    String retOfficerRole = null;
    String contentMgrRole = null;
    while (iter.hasNext()) {
      GrantedAuthority auth = iter.next();
      String authority = auth.getAuthority();
      if (authority.equals(SecurityConstants.USER_ROLE))
        userRole = SecurityConstants.USER_ROLE;
      if (authority.equals(SecurityConstants.ADMIN_ROLE))
        adminRole = SecurityConstants.ADMIN_ROLE;
      if (authority.equals(SecurityConstants.CONTENT_MANAGER_ROLE))
        contentMgrRole = SecurityConstants.CONTENT_MANAGER_ROLE;
      if (authority.equals(SecurityConstants.RETURNING_OFFICER_ROLE))
        retOfficerRole = SecurityConstants.RETURNING_OFFICER_ROLE;
      LOG.debug("authority - " + authority);
    }
    assertEquals("A role should be USER", userRole, SecurityConstants.USER_ROLE);
    assertEquals("A role should be ADMIN", adminRole, SecurityConstants.ADMIN_ROLE);
    assertEquals("A role should be CONTENT_MANAGER", contentMgrRole, SecurityConstants.CONTENT_MANAGER_ROLE);
    assertEquals("A role should be RETURNING_OFFICER", retOfficerRole, SecurityConstants.RETURNING_OFFICER_ROLE);
  }


  @Test
  public void shouldNotAuthenticateUserDueToPassword() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    AuthenticateUserEvent evt = new AuthenticateUserEvent(user.getEmail(), user.getPassword() + '2');
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);

    boolean exception = false;
    try {
      userServiceMocked.authenticateUser(evt);
    } catch (BadCredentialsException e) {
      assertNotNull(e);
      exception = true;

    }
    assertTrue("Exception not thrown.", exception);
  }

  @Test
  public void shouldNotAuthenticateUserDueToUserName() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    AuthenticateUserEvent evt = new AuthenticateUserEvent(user.getEmail() + '3', user.getPassword());
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);

    boolean exception = false;
    try {
      userServiceMocked.authenticateUser(evt);
    } catch (BadCredentialsException e) {
      assertNotNull(e);
      exception = true;

    }
    assertTrue("Exception not thrown.", exception);
  }

  @Test
  public void shouldNotAuthenticateUserDueToUserNotVerified() {
    User user = DatabaseDataFixture.populateUserGnewitt2();
    AuthenticateUserEvent evt = new AuthenticateUserEvent(user.getEmail(), user.getPassword());

    when(uRepo.findByEmail(any(String.class), anyInt())).thenReturn(user);

    boolean exception = false;
    try {
      userServiceMocked.authenticateUser(evt);
    } catch (DisabledException e) {
      assertNotNull(e);
      exception = true;

    }
    assertTrue("Exception not thrown.", exception);
  }

  @Test
  public void shouldAddPersonalityToUser() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    PersonalityDetails details = new PersonalityDetails(11l, 4.2F, 3.2F, 1.7F, 2.9F, 3.9F);
    AddPersonalityEvent addEvt = new AddPersonalityEvent(user.getEmail(),
      details);
    Personality personality = Personality.fromPersonalityDetails(details);
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);
    when(pRepo.save(any(Personality.class), anyInt())).thenReturn(personality);
    when(uRepo.addPersonality(any(Long.class), any(Long.class))).thenReturn(personality);
    PersonalityAddedEvent evtAdd = userServiceMocked.addPersonality(addEvt);
    assertNotNull("", evtAdd.getPersonalityDetails().getPersonalityId());
    assertEquals("", evtAdd.getPersonalityDetails().getAgreeableness(), details.getAgreeableness());
    assertEquals("", evtAdd.getPersonalityDetails().getConscientiousness(), details.getConscientiousness());
    assertEquals("", evtAdd.getPersonalityDetails().getEmotionalStability(), details.getEmotionalStability());
    assertEquals("", evtAdd.getPersonalityDetails().getExtroversion(), details.getExtroversion());
    assertEquals("", evtAdd.getPersonalityDetails().getOpeness(), details.getOpeness());
  }

  @Test
  public void shouldAddPersonalityToUserPersonalityDidNotSave() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    PersonalityDetails details = new PersonalityDetails(11l, 4.2F, 3.2F, 1.7F, 2.9F, 3.9F);
    AddPersonalityEvent addEvt = new AddPersonalityEvent(user.getEmail(),
      details);
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);
    when(pRepo.save(any(Personality.class))).thenReturn(null);
    PersonalityAddedEvent evtAdd = userServiceMocked.addPersonality(addEvt);
    assertNotNull(evtAdd);
    assertFalse(evtAdd.isUserFound());
  }

  @Test
  public void shouldNotAddPersonalityToUserNotFound() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    PersonalityDetails details = new PersonalityDetails(11l, 4.2F, 3.2F, 1.7F, 2.9F, 3.9F);
    AddPersonalityEvent addEvt = new AddPersonalityEvent(user.getEmail(),
      details);
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    PersonalityAddedEvent evtAdd = userServiceMocked.addPersonality(addEvt);
    assertNotNull("", evtAdd);
    assertFalse("", evtAdd.isUserFound());
  }

  @Test
  public void shouldNotAddPersonalityToUserAlreadyHasPersonality() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    PersonalityDetails details = new PersonalityDetails(11l, 4.2F, 3.2F, 1.7F, 2.9F, 3.9F);
    PersonalityDetails details2 = new PersonalityDetails(15l, 4.2F, 3.2F, 1.7F, 2.9F, 3.9F);
    AddPersonalityEvent addEvt = new AddPersonalityEvent(user.getEmail(),
      details);
    Personality personality = Personality.fromPersonalityDetails(details);
    Personality personality2 = Personality.fromPersonalityDetails(details2);
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);
    when(pRepo.save(any(Personality.class))).thenReturn(personality);
    when(uRepo.addPersonality(any(Long.class), any(Long.class))).thenReturn(personality2);
    PersonalityAddedEvent evtAdd = userServiceMocked.addPersonality(addEvt);
    assertNotNull("", evtAdd);
    assertNull("", evtAdd.getPersonalityDetails());
    assertFalse(evtAdd.isUserFound());
  }

  @Test
  public void shouldAddVoteReminderToUser() {
    AddVoteReminderEvent addVoteReminderEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    VoteReminder vr = DatabaseDataFixture.populateVoteReminder1();
    VoteReminderDetails vrd = vr.toVoteReminderDetails();
    addVoteReminderEvent = new AddVoteReminderEvent(vrd);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(uRepo.addVoteReminder(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

    CreatedEvent nace = userServiceMocked.addVoteReminder(addVoteReminderEvent);
    assertNotNull(nace);
    assertEquals(nace.getDetails(), vrd);
    assertTrue(((VoteReminderAddedEvent) nace).isElectionFound());
    assertTrue(((VoteReminderAddedEvent) nace).isUserFound());
  }

  @Test
  public void shouldAddVoteReminderToUserEmptyRequest() {
    AddVoteReminderEvent addVoteReminderEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    VoteReminder vr = DatabaseDataFixture.populateVoteReminder1();
    addVoteReminderEvent = new AddVoteReminderEvent();
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(uRepo.addVoteReminder(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

    CreatedEvent nace = userServiceMocked.addVoteReminder(addVoteReminderEvent);
    assertNotNull(nace);
    assertNull(((VoteReminderAddedEvent) nace).getDetails());
    assertFalse(((VoteReminderAddedEvent) nace).isUserFound());
  }

  @Test
  public void shouldAddVoteReminderToUserEmptyDetails() {
    AddVoteReminderEvent addVoteReminderEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    VoteReminder vr = DatabaseDataFixture.populateVoteReminder1();
    VoteReminderDetails vrd = new VoteReminderDetails();
    addVoteReminderEvent = new AddVoteReminderEvent(vrd);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(uRepo.addVoteReminder(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

    CreatedEvent nace = userServiceMocked.addVoteReminder(addVoteReminderEvent);
    assertNotNull(nace);
    assertNull(((VoteReminderAddedEvent) nace).getDetails());
    assertFalse(((VoteReminderAddedEvent) nace).isUserFound());
  }

  @Test
  public void shouldAddVoteReminderToUserUserNotFound() {
    AddVoteReminderEvent addVoteReminderEvent;
    VoteReminder vr = DatabaseDataFixture.populateVoteReminder1();
    VoteReminderDetails vrd = vr.toVoteReminderDetails();
    addVoteReminderEvent = new AddVoteReminderEvent(vrd);
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    when(uRepo.addVoteReminder(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

    CreatedEvent nace = userServiceMocked.addVoteReminder(addVoteReminderEvent);
    assertNotNull(nace);
    assertTrue(((VoteReminderAddedEvent) nace).isElectionFound());
    assertFalse(((VoteReminderAddedEvent) nace).isUserFound());
  }

  @Test
  public void shouldAddVoteReminderToUserElectionNotFound() {
    AddVoteReminderEvent addVoteReminderEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    VoteReminder vr = DatabaseDataFixture.populateVoteReminder1();
    VoteReminderDetails vrd = vr.toVoteReminderDetails();
    addVoteReminderEvent = new AddVoteReminderEvent(vrd);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(uRepo.addVoteReminder(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(null);

    CreatedEvent nace = userServiceMocked.addVoteReminder(addVoteReminderEvent);
    assertNotNull(nace);
    assertFalse(((VoteReminderAddedEvent) nace).isElectionFound());
    assertTrue(((VoteReminderAddedEvent) nace).isUserFound());
  }

  @Test
  public void shouldAddVoteRecordToUser() {
    AddVoteRecordEvent addVoteRecordEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    VoteRecord vr = DatabaseDataFixture.populateVoteRecord1();
    VoteRecordDetails vrd = vr.toVoteRecordDetails();
    addVoteRecordEvent = new AddVoteRecordEvent(vrd);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(uRepo.addVoteRecord(any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

    VoteRecordAddedEvent nace = userServiceMocked.addVoteRecord(addVoteRecordEvent);
    assertNotNull(nace);
    assertEquals(nace.getDetails(), vrd);
    assertTrue(nace.isElectionFound());
    assertTrue(nace.isUserFound());
  }

  @Test
  public void shouldAddVoteRecordToUserUserNotFound() {
    AddVoteRecordEvent addVoteRecordEvent;
    VoteRecord vr = DatabaseDataFixture.populateVoteRecord1();
    VoteRecordDetails vrd = vr.toVoteRecordDetails();
    addVoteRecordEvent = new AddVoteRecordEvent(vrd);
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    when(uRepo.addVoteRecord(any(Long.class), any(Long.class), any(String.class))).thenReturn(vr);

    VoteRecordAddedEvent nace = userServiceMocked.addVoteRecord(addVoteRecordEvent);
    assertNotNull(nace);
    assertTrue(nace.isElectionFound());
    assertFalse(nace.isUserFound());
  }

  @Test
  public void shouldAddVoteRecordToUserElectionNotFound() {
    AddVoteRecordEvent addVoteRecordEvent;
    User userData = DatabaseDataFixture.populateUserGnewitt();
    VoteRecord vr = DatabaseDataFixture.populateVoteRecord1();
    VoteRecordDetails vrd = vr.toVoteRecordDetails();
    addVoteRecordEvent = new AddVoteRecordEvent(vrd);
    when(uRepo.findByEmail(any(String.class))).thenReturn(userData);
    when(uRepo.addVoteRecord(any(Long.class), any(Long.class), any(String.class))).thenReturn(null);

    VoteRecordAddedEvent nace = userServiceMocked.addVoteRecord(addVoteRecordEvent);
    assertNotNull(nace);
    assertFalse(nace.isElectionFound());
    assertTrue(nace.isUserFound());
  }

  @Test
  public void shouldReadVoteReminder() {
    ReadVoteReminderEvent readVoteReminderEvent;
    Long id = 1l;
    VoteReminder vr = DatabaseDataFixture.populateVoteReminder1();
    VoteReminderDetails vrd = vr.toVoteReminderDetails();
    readVoteReminderEvent = new ReadVoteReminderEvent(id);
    when(uRepo.readVoteReminder(any(Long.class))).thenReturn(vr);

    VoteReminderReadEvent nace = (VoteReminderReadEvent) userServiceMocked.readVoteReminder(readVoteReminderEvent);
    assertNotNull(nace);
    assertEquals(nace.getDetails(), vrd);
    assertTrue(nace.isEntityFound());
    assertEquals(nace.getNodeId(), id);
  }

  @Test
  public void shouldReadVoteReminderNotFound() {
    ReadVoteReminderEvent readVoteReminderEvent;
    Long id = 1l;
    readVoteReminderEvent = new ReadVoteReminderEvent(id);
    when(uRepo.readVoteReminder(any(Long.class))).thenReturn(null);

    ReadEvent nace = userServiceMocked.readVoteReminder(readVoteReminderEvent);
    assertNotNull(nace);
    assertNull(nace.getDetails());
    assertFalse(nace.isEntityFound());
    assertEquals(nace.getNodeId(), id);
  }

  @Test
  public void shouldReadVoteRecord() {
    ReadVoteRecordEvent readVoteRecordEvent;
    Long id = 1l;
    VoteRecord vr = DatabaseDataFixture.populateVoteRecord1();
    VoteRecordDetails vrd = vr.toVoteRecordDetails();
    readVoteRecordEvent = new ReadVoteRecordEvent(id);
    when(uRepo.readVoteRecord(any(Long.class))).thenReturn(vr);

    ReadEvent nace = userServiceMocked.readVoteRecord(readVoteRecordEvent);
    assertNotNull(nace);
    assertEquals((VoteRecordDetails) nace.getDetails(), vrd);
    assertEquals(((VoteRecordDetails) ((VoteRecordReadEvent) nace).getDetails()).getVoterId(), vrd.getVoterId());
    assertTrue(nace.isEntityFound());
    assertEquals(nace.getNodeId(), id);
  }

  @Test
  public void shouldReadVoteRecordNotFound() {
    ReadVoteRecordEvent readVoteRecordEvent;
    Long id = 1l;
    readVoteRecordEvent = new ReadVoteRecordEvent(id);
    when(uRepo.readVoteReminder(any(Long.class))).thenReturn(null);

    ReadEvent nace = userServiceMocked.readVoteRecord(readVoteRecordEvent);
    assertNotNull(nace);
    assertNull(nace.getDetails());
    assertFalse(nace.isEntityFound());
    assertEquals(nace.getNodeId(), id);
  }

  @Test
  public void shouldDeleteVoteRecord() {
    DeleteVoteRecordEvent deleteVoteRecordEvent;
    Long id = 1l;
    VoteRecord vr = DatabaseDataFixture.populateVoteRecord1();
    deleteVoteRecordEvent = new DeleteVoteRecordEvent(id);
    when(uRepo.deleteVoteRecord(any(Long.class))).thenReturn(vr);

    DeletedEvent nace = userServiceMocked.deleteVoteRecord(deleteVoteRecordEvent);
    assertNotNull(nace);
    assertTrue(nace.isEntityFound());
    assertTrue(nace.isDeletionCompleted());
    assertEquals(nace.getNodeId(), id);
  }

  @Test
  public void shouldDeleteVoteRecordNotFound() {
    DeleteVoteRecordEvent deleteVoteRecordEvent;
    Long id = 1l;
    deleteVoteRecordEvent = new DeleteVoteRecordEvent(id);
    when(uRepo.deleteVoteRecord(any(Long.class))).thenReturn(null);

    DeletedEvent nace = userServiceMocked.deleteVoteRecord(deleteVoteRecordEvent);
    assertNotNull(nace);
    assertFalse(nace.isEntityFound());
    assertFalse(nace.isDeletionCompleted());
    assertEquals(nace.getNodeId(), id);
  }

  @Test
  public void shouldDeleteVoteReminder() {
    DeleteVoteReminderEvent deleteVoteReminderEvent;
    Long id = 1l;
    VoteReminder vr = DatabaseDataFixture.populateVoteReminder1();
    deleteVoteReminderEvent = new DeleteVoteReminderEvent(id);
    when(uRepo.deleteVoteReminder(any(Long.class))).thenReturn(vr);

    DeletedEvent nace = userServiceMocked.deleteVoteReminder(deleteVoteReminderEvent);
    assertNotNull(nace);
    assertTrue(nace.isEntityFound());
    assertTrue(nace.isDeletionCompleted());
    assertEquals(nace.getNodeId(), id);
  }

  @Test
  public void shouldDeleteVoteReminderNotFound() {
    DeleteVoteReminderEvent deleteVoteReminderEvent;
    Long id = 1l;
    deleteVoteReminderEvent = new DeleteVoteReminderEvent(id);
    when(uRepo.deleteVoteReminder(any(Long.class))).thenReturn(null);

    DeletedEvent nace = userServiceMocked.deleteVoteReminder(deleteVoteReminderEvent);
    assertNotNull(nace);
    assertFalse(nace.isEntityFound());
    assertFalse(nace.isDeletionCompleted());
    assertEquals(nace.getNodeId(), id);
  }

  @Test
  public void shouldLoadUserByUsername() {
    User userData = DatabaseDataFixture.populateUserGnewitt();
    when(uRepo.findByEmail(any(String.class), anyInt())).thenReturn(userData);

    org.springframework.security.core.userdetails.UserDetails nace = userServiceMocked.loadUserByUsername(userData.getEmail());
    assertNotNull(nace);
    assertTrue(nace.isAccountNonExpired());
    assertTrue(nace.isAccountNonLocked());
    assertTrue(nace.isCredentialsNonExpired());
    assertTrue(nace.isEnabled());
    assertEquals(userData.getEmail(), nace.getUsername());
    assertEquals(userData.getPassword(), nace.getPassword());
  }

  @Test
  public void shouldLoadUserByUsernameUserNotFound() {
    User userData = DatabaseDataFixture.populateUserGnewitt();
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    boolean exceptionThrown = false;
    try {
      userServiceMocked.loadUserByUsername(userData.getEmail());
    } catch (UsernameNotFoundException e) {
      exceptionThrown = true;
    }
    assertTrue(exceptionThrown);
  }

  @Test
  public void testReadUserByContactEmail() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(user.getEmail());
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);
    ReadUserEvent result = userServiceMocked.readUserByContactEmail(requestReadUserEvent);
    UserDetails dets = (UserDetails) result.getDetails();
    assertNull(result.getNodeId());
    assertEquals(result.getEmail(), user.getEmail());
    assertEquals(dets.getEmail(), user.getEmail());
    assertEquals(dets.getFamilyName(), user.getFamilyName());
    assertEquals(dets.getGivenName(), user.getGivenName());
    assertEquals(dets.getGender(), user.getGender());
    assertEquals(dets.getNationality(), user.getNationality());
    assertEquals(dets.getInstitutionId(), user.getInstitution$().getNodeId());
    assertNull(dets.getPassword());
    assertNull(dets.getYearOfBirth());
    assertNull(dets.getContactNumber());
  }

  @Test
  public void testReadUserByContactEmailNotFound() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(user.getEmail());
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    ReadUserEvent result = userServiceMocked.readUserByContactEmail(requestReadUserEvent);
    assertNull(result.getNodeId());
    assertEquals(result.getEmail(), user.getEmail());
    assertFalse(result.isEntityFound());
    assertNull(result.getDetails());
  }

  @Test
  public void testReadUserByContactNumber() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(user.getContactNumber());
    when(uRepo.findByContactNumber(any(String.class))).thenReturn(user);
    ReadUserEvent result = userServiceMocked.readUserByContactNumber(requestReadUserEvent);
    UserDetails dets = (UserDetails) result.getDetails();
    assertNull(result.getNodeId());
    assertEquals(result.getEmail(), user.getContactNumber());
    assertEquals(dets.getContactNumber(), user.getContactNumber());
    assertEquals(dets.getFamilyName(), user.getFamilyName());
    assertEquals(dets.getGivenName(), user.getGivenName());
    assertEquals(dets.getGender(), user.getGender());
    assertEquals(dets.getNationality(), user.getNationality());
    assertEquals(dets.getInstitutionId(), user.getInstitution$().getNodeId());
    assertNull(dets.getPassword());
    assertNull(dets.getYearOfBirth());
    assertEquals(dets.getEmail(), user.getEmail());
  }

  @Test
  public void testReadUserByContactNumberNotFound() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(user.getContactNumber());
    when(uRepo.findByContactNumber(any(String.class))).thenReturn(null);
    ReadUserEvent result = userServiceMocked.readUserByContactNumber(requestReadUserEvent);
    assertNull(result.getNodeId());
    assertEquals(result.getEmail(), user.getContactNumber());
    assertFalse(result.isEntityFound());
    assertNull(result.getDetails());
  }

  @Test
  public void testReadUserByContactNumberNullRequest() {
    RequestReadUserEvent requestReadUserEvent = null;
    ReadUserEvent result = userServiceMocked.readUserByContactNumber(requestReadUserEvent);
    assertNull(result.getNodeId());
    assertNull(result.getEmail());
    assertNull(result.getDetails());
    assertFalse(result.isEntityFound());
  }

  @Test
  public void testFindUserId() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    when(uRepo.findByEmail(any(String.class))).thenReturn(user);
    Long id = userServiceMocked.findUserId(user.getEmail());
    assertEquals(id, user.getNodeId());
  }

  @Test
  public void testFindUserIdNotFound() {
    User user = DatabaseDataFixture.populateUserGnewitt();
    when(uRepo.findByEmail(any(String.class))).thenReturn(null);
    Long id = userServiceMocked.findUserId(user.getEmail());
    assertNull(id);
  }

  @Test
  public void testReadUserById() {
    User value = DatabaseDataFixture.populateUserGnewitt();
    when(uRepo.findOne(any(Long.class))).thenReturn(value);
    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(value.getNodeId());
    ReadUserEvent uEvt = userServiceMocked.readUserById(requestReadUserEvent);
    assertNull(uEvt.getNodeId());
    assertNull(uEvt.getEmail());
    UserDetails dets = (UserDetails) uEvt.getDetails();
    assertEquals(dets, value.toUserDetails());
  }

  @Test
  public void testReadUserByIdNoUser() {
    User value = DatabaseDataFixture.populateUserGnewitt();
    when(uRepo.findOne(any(Long.class))).thenReturn(null);
    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(value.getNodeId());
    ReadUserEvent uEvt = userServiceMocked.readUserById(requestReadUserEvent);
    assertNull(uEvt.getNodeId());
    assertNull(uEvt.getEmail());
    assertNull(uEvt.getDetails());
    assertFalse(uEvt.isEntityFound());
  }

  @Test
  public void testReadUserByIdNullRequest() {
    RequestReadUserEvent requestReadUserEvent = null;
    ReadUserEvent uEvt = userServiceMocked.readUserById(requestReadUserEvent);
    assertNull(uEvt.getNodeId());
    assertNull(uEvt.getEmail());
    assertNull(uEvt.getDetails());
    assertFalse(uEvt.isEntityFound());
  }

  @Test
  public void testReadExistingContacts() {
    HashMap<Long, User> userEvts = DatabaseDataFixture.populateUsers();
    ArrayList<User> users = new ArrayList<User>();
    Iterator<User> iter = userEvts.values().iterator();
    while (iter.hasNext()) {
      User na = iter.next();
      users.add(na);
    }


    Long parentId = 453l;
    ReadAllEvent readAllEvent = new ReadAllEvent(parentId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable pageable = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<User> value = new PageImpl<User>(users, pageable, users.size());
    when(uRepo.findContacts(any(Long.class), any(Pageable.class))).thenReturn(value);

    AllReadEvent uEvt = userServiceMocked.readExistingContactsById(readAllEvent, Direction.ASC, pageNumber, pageLength);
    assertNotNull(uEvt);
    assertEquals(uEvt.getTotalPages(), new Integer(1));
    assertEquals(uEvt.getTotalItems(), new Long(users.size()));
  }

  @Test
  public void testReadExistingContactsNoContacts() {
    ArrayList<User> users = new ArrayList<User>();
    User user = DatabaseDataFixture.populateUserGnewitt();


    Long parentId = 453l;
    ReadAllEvent readAllEvent = new ReadAllEvent(parentId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable pageable = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<User> value = new PageImpl<User>(users, pageable, users.size());
    when(uRepo.findContacts(any(Long.class), any(Pageable.class))).thenReturn(value);
    when(uRepo.findOne(any(Long.class))).thenReturn(user);

    AllReadEvent uEvt = userServiceMocked.readExistingContactsById(readAllEvent, Direction.ASC, pageNumber, pageLength);
    assertNotNull(uEvt);
    assertEquals(uEvt.getTotalPages().intValue(), 0);
    assertEquals(uEvt.getTotalItems().intValue(), users.size());
  }

  @Test
  public void testReadExistingContactsNoParentUser() {
    ArrayList<User> users = new ArrayList<User>();


    Long parentId = 453l;
    ReadAllEvent readAllEvent = new ReadAllEvent(parentId);
    int pageLength = 10;
    int pageNumber = 0;

    Pageable pageable = new PageRequest(pageNumber, pageLength, Direction.ASC, "a.date");
    Page<User> value = new PageImpl<User>(users, pageable, users.size());
    when(uRepo.findContacts(any(Long.class), any(Pageable.class))).thenReturn(value);
    when(uRepo.findOne(any(Long.class))).thenReturn(null);

    AllReadEvent uEvt = userServiceMocked.readExistingContactsById(readAllEvent, Direction.ASC, pageNumber, pageLength);
    assertNotNull(uEvt);
    assertFalse(uEvt.isEntityFound());
  }
}
