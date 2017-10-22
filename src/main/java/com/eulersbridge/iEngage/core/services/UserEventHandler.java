package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.core.events.users.*;
import com.eulersbridge.iEngage.core.events.voteRecord.*;
import com.eulersbridge.iEngage.core.events.voteReminder.*;
import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.*;
import com.eulersbridge.iEngage.email.EmailConstants;
import com.eulersbridge.iEngage.email.EmailResetPWD;
import com.eulersbridge.iEngage.email.EmailVerification;
import com.eulersbridge.iEngage.rest.domain.PPSEQuestions;
import com.eulersbridge.iEngage.rest.domain.UserProfile;
import com.eulersbridge.iEngage.security.PasswordHash;
import com.eulersbridge.iEngage.security.SecurityConstants;
import com.eulersbridge.iEngage.security.UserCredentialDetails;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Service
public class UserEventHandler implements UserService {

  private static Logger LOG = LoggerFactory.getLogger(UserEventHandler.class);
  private PasswordEncoder passwordEncoder;

  private UserRepository userRepository;
  private PersonalityRepository personRepository;
  private InstitutionRepository instRepository;
  private PPSEQuestionsRepository ppseQuestionsRepository;
  private VerificationTokenRepository tokenRepository;

  private VelocityEngine velocityEngine;

  @Autowired
  public UserEventHandler(PasswordEncoder passwordEncoder,
                          UserRepository userRepository,
                          PersonalityRepository personRepository,
                          InstitutionRepository instRepo,
                          VerificationTokenRepository tokenRepo,
                          VelocityEngine velocityEngine,
                          PPSEQuestionsRepository ppseQuestionsRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.personRepository = personRepository;
    this.instRepository = instRepo;
    this.tokenRepository = tokenRepo;
    this.velocityEngine = velocityEngine;
    this.ppseQuestionsRepository = ppseQuestionsRepository;
  }

  @Override
  @Transactional
  public UserCreatedEvent signUpNewUser(CreateUserEvent createUserEvent) {
    UserDetails newUser = (UserDetails) createUserEvent.getDetails();
    UserCreatedEvent result;
    if ((null == newUser) || (null == newUser.getEmail())) {
      result = null;
    } else if ((null == newUser.getInstitutionId()) || (null == newUser.getPassword()) || (null == newUser.getGivenName()) || (null == newUser.getFamilyName())) {
      result = UserCreatedEvent.instituteNotFound(newUser.getEmail());
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Finding institution with instId = "
          + newUser.getInstitutionId());
      Institution inst = instRepository.findOne(newUser
        .getInstitutionId(), 0);
      if (LOG.isDebugEnabled()) LOG.debug("User Details :" + newUser);
      User userToInsert = User.fromUserDetails(newUser);

      User createdUser = null, existingUser = null;

      existingUser = userRepository.findByEmail(userToInsert.getEmail(), 0);
      // TODO potentially check if existing user is verified. User might
      // want another verification email
      if ((inst != null) && (null == existingUser)) {
        if (LOG.isDebugEnabled())
          LOG.debug("Found institution = " + inst);
        userToInsert.setPassword(passwordEncoder.encode(userToInsert.getPassword()));
        userToInsert.setInstitution(inst.toNode());
        userToInsert.setAccountVerified(false);
        userToInsert.setRoles(SecurityConstants.USER_ROLE);
        if (LOG.isDebugEnabled())
          LOG.debug("userToInsert :" + userToInsert);
        User test = userRepository.save(userToInsert);
        // TODO what happens if this fails?
        if (LOG.isDebugEnabled()) LOG.debug("test = " + test);
        createdUser = userRepository.findOne(test.getNodeId());

        VerificationToken token = new VerificationToken(
          VerificationToken.VerificationTokenType.emailVerification,
          test, EmailConstants.DEFAULT_EXPIRY_TIME_IN_MINS);
        if (LOG.isDebugEnabled())
          LOG.debug("Verification token = " + token.toString());
        VerificationToken t = tokenRepository.save(token);
        System.out.println("velocityEngine=" + velocityEngine);
        EmailVerification verifyEmail = new EmailVerification(
          velocityEngine, createdUser, token);
        result = new UserCreatedEvent(createdUser.getEmail(),
          createdUser.toUserDetails(), verifyEmail);

      } else if (null == inst) {
        result = UserCreatedEvent.instituteNotFound(newUser.getEmail());
      } else {
        result = UserCreatedEvent.userNotUnique(newUser.getEmail());
      }
    }
    return result;
  }

  @Override
  public UserCreatedEvent resendVerificationEmail(RequestReadUserEvent createUserEvent) {
    String userEmail = createUserEvent.getEmail();
    UserCreatedEvent result;
    User createdUser = userRepository.findByEmail(createUserEvent.getEmail());
    if (createdUser != null && createdUser.getVerificationToken() != null && createdUser.getVerificationToken() instanceof VerificationToken) {
      Iterable<VerificationToken> tokens = createdUser.getVerificationToken$();
      Iterator<VerificationToken> tokenIter = tokens.iterator();
      VerificationToken token = null;

      if (tokenIter.hasNext())
        token = tokenIter.next();

      if (LOG.isDebugEnabled())
        LOG.debug("Verification token = " + token.toString());

      EmailVerification verifyEmail = new EmailVerification(
        velocityEngine, createdUser, token);
      result = new UserCreatedEvent(userEmail,
        createdUser.toUserDetails(), verifyEmail);
    } else
      result = UserCreatedEvent.instituteNotFound(null);
    return result;
  }

  @Override
  public ReadUserEvent readUser(
    RequestReadUserEvent requestReadUserEvent) {
    ReadUserEvent response;
    if (requestReadUserEvent != null) {
      System.out.println("!!!" + requestReadUserEvent.getEmail());
      User user = userRepository.findByEmail(requestReadUserEvent.getEmail());
      if (user == null) {
        response = ReadUserEvent.notFound(requestReadUserEvent.getEmail());
      } else {
        // template.fetch(user.getInstitution());
        UserDetails result = user.toUserDetails();
        if (LOG.isDebugEnabled()) LOG.debug("Result - " + result);
        response = new ReadUserEvent(requestReadUserEvent.getEmail(),
          result);
      }
    } else {
      response = ReadUserEvent.notFound((String) null);
    }
    return response;
  }

  @Override
  public ReadUserEvent readUserById(RequestReadUserEvent requestReadUserEvent) {
    ReadUserEvent response;
    if (requestReadUserEvent != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("requestReadUser(" + requestReadUserEvent.getNodeId()
          + ")");
      User user = userRepository.findOne(requestReadUserEvent.getNodeId());
      if (user == null) {
        response = ReadUserEvent.notFound((String) null);
      } else {
        UserDetails result = user.toUserDetails();
        if (LOG.isDebugEnabled()) LOG.debug("Result - " + result);
        response = new ReadUserEvent(requestReadUserEvent.getEmail(),
          result);
      }
    } else {
      response = ReadUserEvent.notFound((String) null);
    }
    return response;
  }

  private UserDetails removeConfidentialDetails(UserDetails userDetails) {
    UserDetails publicDetails = new UserDetails();
    publicDetails.setContactNumber(userDetails.getContactNumber());
    publicDetails.setEmail(userDetails.getEmail());
    publicDetails.setFamilyName(userDetails.getFamilyName());
    publicDetails.setGivenName(userDetails.getGivenName());
    publicDetails.setGender(userDetails.getGender());
    publicDetails.setNationality(userDetails.getNationality());
    publicDetails.setInstitutionId(userDetails.getInstitutionId());
    publicDetails.setPhotos(userDetails.getPhotos());
    publicDetails.setProfilePhoto(userDetails.getProfilePhoto());
    publicDetails.setNumOfCompTasks(userDetails.getNumOfCompTasks());
//    publicDetails.setTotalTasks(userDetails.getTotalTasks());
    publicDetails.setNumOfCompBadges(userDetails.getNumOfCompBadges());
//    publicDetails.setTotalBadges(userDetails.getTotalBadges());
    publicDetails.setExperience(userDetails.getExperience());

    return publicDetails;
  }

  @Override
  public ReadUserEvent readUserByContactEmail(
    RequestReadUserEvent requestReadUserEvent) {
    if (LOG.isDebugEnabled()) LOG.debug("readUserByContactEmail()");
    ReadUserEvent readEvt = readUser(requestReadUserEvent);
    ReadUserEvent publicReadEvt = readEvt;
    if (readEvt.isEntityFound()) {
      UserDetails publicDetails = removeConfidentialDetails((UserDetails) readEvt.getDetails());
      publicDetails.setContactNumber(null);
      publicReadEvt = new ReadUserEvent(requestReadUserEvent.getEmail(), publicDetails);
    }
    return publicReadEvt;
  }

  @Override
  public ReadUserEvent readUserByContactNumber(RequestReadUserEvent requestReadUserEvent) {
    ReadUserEvent response;
    if (requestReadUserEvent != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("requestReadUser(" + requestReadUserEvent.getNodeId()
          + ")");
      User user = userRepository.findByContactNumber(requestReadUserEvent.getEmail());
      if (user == null) {
        response = ReadUserEvent.notFound(requestReadUserEvent.getEmail());
      } else {
        UserDetails result = removeConfidentialDetails(user.toUserDetails());
        if (LOG.isDebugEnabled()) LOG.debug("Result - " + result);
        response = new ReadUserEvent(requestReadUserEvent.getEmail(),
          result);
      }
    } else {
      response = ReadUserEvent.notFound((String) null);
    }
    return response;
  }

  @Override
  public SearchUserEvent searchUserProfileByName(RequestSearchUserEvent requestSearchUserEvent) {
    String[] input = requestSearchUserEvent.getqueryString().split(" ");
    String pattern_1 = input[0].toLowerCase();
    String pattern_2 = input.length == 2 ? input[1].toLowerCase() : " NULL ";
    List<User> users = userRepository.searchUserByName(pattern_1, pattern_2);
    if (users.size() < 1) {
      users = userRepository.searchUserByName2(pattern_1, pattern_1);
    }
    if (users.size() < 1) {
      users = userRepository.searchUserByName2(pattern_1, pattern_2);
    }
    List<UserProfile> userProfileList = new ArrayList<>(users.size());
    for (User user : users) {
      UserProfile userProfile = UserProfile.fromUserDetails(user.toUserDetails());
      userProfileList.add(userProfile);
    }
    SearchUserEvent searchUserEvent = new SearchUserEvent(requestSearchUserEvent.getqueryString(), userProfileList);
    return searchUserEvent;
  }

  public AllReadEvent readExistingContacts(Long userId, Pageable pageable) {
    Page<User> contacts = null;
    ArrayList<UserDetails> dets = new ArrayList<UserDetails>();
    AllReadEvent nare = null;

    if (LOG.isDebugEnabled()) LOG.debug("UserId " + userId);
    contacts = userRepository.findContacts(userId, pageable);
    if (contacts != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + contacts.getTotalElements() + " total pages =" + contacts.getTotalPages());
      Iterator<User> iter = contacts.iterator();
      while (iter.hasNext()) {
        User na = iter.next();
        if (LOG.isDebugEnabled())
          LOG.debug("Converting to details - " + na.getEmail());
        UserDetails det = na.toUserDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        User user = userRepository.findOne(userId);
        if ((null == user) ||
          ((null == user.getEmail()) || ((null == user.getGivenName()) && (null == user.getFamilyName()) && (null == user.getGender())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(UserId)");
          nare = AllReadEvent.notFound(null);
        } else {
          nare = new AllReadEvent(userId, dets, contacts.getTotalElements(), contacts.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(userId, dets, contacts.getTotalElements(), contacts.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(null);
    }
    return nare;
  }

  @Override
  public AllReadEvent readExistingContactsById(ReadAllEvent readContactsEvent, Direction sortDirection, int pageNumber, int pageLength) {
    Long userId = readContactsEvent.getParentId();
    AllReadEvent nare = null;

    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "b.familyName");
    nare = readExistingContacts(userId, pageable);


    return nare;
  }

  @Override
  public AllReadEvent readExistingContactsByEmail(
    RequestReadUserEvent readUserProfilesEvent, Direction sortDirection,
    int pageNumber, int pageLength) {
    String email = readUserProfilesEvent.getEmail();
    Long userId = findUserId(email);
    AllReadEvent nare = null;
    if (userId != null) {
      Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "b.familyName");
      nare = readExistingContacts(userId, pageable);
    } else
      nare = AllReadEvent.notFound(null);

    return nare;
  }

  @Override
  @Transactional
  public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("requestDeleteUser(" + deleteUserEvent.getEmail() + ")");
    User user = userRepository.findByEmail(deleteUserEvent.getEmail());
    if (user == null) {
      return UserDeletedEvent.notFound(deleteUserEvent.getEmail());
    }
    userRepository.delete(user.getNodeId());
    return new UserDeletedEvent(deleteUserEvent.getEmail(),
      user.toUserDetails());
  }

  @Override
  @Transactional
  public UpdatedEvent updateUser(UpdateUserEvent updateUserEvent) {
    UserDetails newUser = (UserDetails) updateUserEvent.getDetails();
    User user = null, result = null, updateUser = User
      .fromUserDetails(newUser);
    if (LOG.isDebugEnabled()) LOG.debug("User Details :" + newUser);
    user = userRepository.findByEmail(updateUserEvent.getEmail(), 0);
    if (null == user) {
      return UserUpdatedEvent.userNotFound(newUser.getEmail());
    } else {
//      userToUpdate.setId(user.getId());
//      userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
//      userToUpdate.setRoles(user.getRoles());
//      if (null != updateUserDetails.getEmail())
//        user.setEmail(updateUserDetails.getEmail());
      if (null != updateUser.getContactNumber())
        user.setContactNumber(updateUser.getContactNumber());
      if (null != updateUser.getFamilyName())
        user.setFamilyName(updateUser.getFamilyName());
      if (null != updateUser.getGender())
        user.setGender(updateUser.getGender());
      if (null != updateUser.getGivenName())
        user.setGivenName(updateUser.getGivenName());
      if (null != updateUser.getNationality())
        user.setNationality(updateUser.getNationality());
      if (null != updateUser.getYearOfBirth())
        user.setYearOfBirth(updateUser.getYearOfBirth());
      if (null != updateUser.getAccountVerified())
        user.setAccountVerified(updateUser.getAccountVerified());
      if (null != updateUser.getTrackingOff())
        user.setTrackingOff(updateUser.getTrackingOff());
      if (null != updateUser.getConsentGiven())
        user.setConsentGiven(updateUser.getConsentGiven());
      if (null != updateUser.getOptOutDataCollection())
        user.setOptOutDataCollection(updateUser.getOptOutDataCollection());
      if (null != updateUser.getArn())
        user.setArn(updateUser.getArn());
      if (null != updateUser.getDeviceToken())
        user.setDeviceToken(updateUser.getDeviceToken());

//      if (null != newUser.getInstitutionId()) {
//        if (user.getInstitution() != null)
//          newUser.setInstitutionId(user.getInstitution().getId());
//        if (user.getInstitution() instanceof Institution) {
//          user.setInstitution(user.getInstitution$());
//        }
//      }
//      User.copyUntweakablePropoties(user, updateUserDetails);

      result = userRepository.save(user, 0);
      return new UserUpdatedEvent(result.getEmail(), result.toUserDetails());
    }
  }

  @Override
  @Transactional
  public UserAccountVerifiedEvent validateUserAccount(
    VerifyUserAccountEvent verifyUserAccountEvent) {
    String emailToVerify = verifyUserAccountEvent.getEmail();
    String tokenString = verifyUserAccountEvent.getToken();
    User user = null, resultUser = null;
    VerificationToken token = null, resultToken = null;
    UserAccountVerifiedEvent verificationResult = null;
    if (LOG.isDebugEnabled())
      LOG.debug("Verification Details :" + emailToVerify + " "
        + tokenString);
    user = userRepository.findByEmail(emailToVerify);
    token = tokenRepository.findByToken(tokenString);
    if (null == user) {
      if (LOG.isDebugEnabled())
        LOG.debug("User not found, cannot be verified.");
      verificationResult = new UserAccountVerifiedEvent(emailToVerify);
      verificationResult
        .setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.userNotFound);
    } else if (null == token) {
      if (LOG.isDebugEnabled())
        LOG.debug("Token does not exist, cannot be verified.");
      verificationResult = new UserAccountVerifiedEvent(emailToVerify,
        user.toUserDetails());
      verificationResult
        .setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenDoesntExists);
    } else if (!(token.getUser().getNodeId().equals(user.getNodeId()))) {
      if (LOG.isDebugEnabled())
        LOG.debug("Token does not match, specified user.  Cannot be verified.");
      verificationResult = new UserAccountVerifiedEvent(emailToVerify,
        user.toUserDetails());
      verificationResult
        .setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenUserMismatch);
    } else if ((token.isVerified()) || (user.getAccountVerified())) {
      if (LOG.isDebugEnabled())
        LOG.debug("User is already verified, cannot be verified twice.");
      verificationResult = new UserAccountVerifiedEvent(emailToVerify,
        user.toUserDetails());
      verificationResult
        .setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed);
    } else if (token.hasExpired()) {
      if (LOG.isDebugEnabled())
        LOG.debug("Token already expired, cannot be used anymore.");
      verificationResult = new UserAccountVerifiedEvent(emailToVerify,
        user.toUserDetails());
      verificationResult
        .setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenExpired);
    } else if (!(token.getTokenType()
      .equals(VerificationToken.VerificationTokenType.emailVerification
        .toString()))) {
      if (LOG.isDebugEnabled())
        LOG.debug("Token type mismatch, " + token.getTokenType()
          + " cannot be used for email verification.");
      if (LOG.isDebugEnabled())
        LOG.debug("Token type = "
          + token.getTokenType()
          + " VerificationToken.VerificationTokenType = "
          + VerificationToken.VerificationTokenType.emailVerification
          .toString());
      verificationResult = new UserAccountVerifiedEvent(emailToVerify,
        user.toUserDetails());
      verificationResult
        .setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenTypeMismatch);
    } else {
      user.setAccountVerified(true);
      if (LOG.isDebugEnabled()) LOG.debug("userToVerify :" + user);
      resultUser = userRepository.save(user, 0);

      token.setVerified(true);
      if (LOG.isDebugEnabled()) LOG.debug("tokenToVerify :" + token);
      resultToken = tokenRepository.save(token, 0);

      verificationResult = new UserAccountVerifiedEvent(emailToVerify,
        resultUser.toUserDetails(), resultToken.isVerified());
    }

    return verificationResult;
  }

  @Override
  public UserAuthenticatedEvent authenticateUser(
    AuthenticateUserEvent authUserEvent) throws AuthenticationException {
    String userName = authUserEvent.getUserName();
    String password = authUserEvent.getPassword();
    String emailAddress = userName;
    UserAuthenticatedEvent evt;
    User user = userRepository.findByEmail(emailAddress, 0);
    if (user != null) {
      if (user.getAccountVerified()) {
        String dbHash = user.getPassword();
        String components[] = dbHash.split(":");
        if (components.length == 3) {
          if (LOG.isDebugEnabled()) LOG.debug("Hashed password");
          boolean check;
          try {
            check = PasswordHash.validatePassword(password, dbHash);
          } catch (NoSuchAlgorithmException e) {
            throw new AuthenticationServiceException("No Such Algorithm.");
          } catch (InvalidKeySpecException e) {
            throw new AuthenticationServiceException("Invalid Key Spec.");
          }
          if (check) {
            List<GrantedAuthority> grantedAuths = authsFromString(user
              .getRoles());
            evt = new UserAuthenticatedEvent(grantedAuths);
          } else {
            if (LOG.isDebugEnabled())
              LOG.debug("Password does not match.");
            evt = UserAuthenticatedEvent.badCredentials();
            throw new BadCredentialsException(
              SecurityConstants.BadPassword);
          }
        } else {
          if (LOG.isDebugEnabled()) LOG.debug("Non hashed password.");
          if (user.comparePassword(password)) {
            List<GrantedAuthority> grantedAuths = authsFromString(user
              .getRoles());
            evt = new UserAuthenticatedEvent(grantedAuths);
          } else {
            if (LOG.isDebugEnabled())
              LOG.debug("Password does not match.");
            evt = UserAuthenticatedEvent.badCredentials();
            throw new BadCredentialsException(
              SecurityConstants.BadPassword);
          }
        }
      } else {
        if (LOG.isDebugEnabled())
          LOG.debug("Account is not verified.");
        evt = UserAuthenticatedEvent.badCredentials();
        AccountStatusException e = new DisabledException(
          SecurityConstants.NotVerified);
        throw e;
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("No such account.");
      evt = UserAuthenticatedEvent.badCredentials();
      throw new BadCredentialsException(
        SecurityConstants.BadPassword);
//			throw new UsernameNotFoundException(SecurityConstants.UserNotFound);
    }
    return evt;
  }

  List<GrantedAuthority> authsFromString(String authString) {
    List<GrantedAuthority> grantedAuths = new ArrayList<>();
    if (authString != null) {
      String[] auths = authString.split(",");
      for (int x = 0; x < auths.length; x++) {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(
          auths[x]);
        grantedAuths.add(auth);
      }
    }
    return grantedAuths;
  }

  @Override
  public PersonalityAddedEvent addPersonality(
    AddPersonalityEvent addPersonalityEvent) {
    PersonalityAddedEvent evt;

    String emailAddress = addPersonalityEvent.getEmail();
    if (LOG.isDebugEnabled()) LOG.debug("Email address - " + emailAddress);

    User user = userRepository.findByEmail(emailAddress);
    if (user != null) { // Valid User
      if (LOG.isDebugEnabled())
        LOG.debug("UserId - " + user.getNodeId());
      PersonalityDetails dets = addPersonalityEvent.getDetails();
      Personality personality = Personality.fromPersonalityDetails(dets);
      if (LOG.isDebugEnabled())
        LOG.debug("Personality - " + personality);

      Personality personalityAdded;
      Boolean existingPersonality = (user.getPersonality() != null);
      if (existingPersonality) {
        personality.setNodeId(user.getPersonality().getNodeId());
      }

      personalityAdded = personRepository.save(personality, 0);
      if (personalityAdded != null) {
        if (!existingPersonality) {
          Long personalityId = personalityAdded.getNodeId();
          Personality personalityLinked = userRepository.addPersonality(
            user.getNodeId(), personalityId);
          if (personalityLinked.equals(personalityAdded))
            evt = new PersonalityAddedEvent(
              personalityAdded.toPersonalityDetails());
          else evt = PersonalityAddedEvent.userNotFound();
        } else {
          evt = new PersonalityAddedEvent(
            personalityAdded.toPersonalityDetails());
        }
      } else {
        evt = PersonalityAddedEvent.userNotFound();
      }

    } else {
      if (LOG.isDebugEnabled()) LOG.debug("No such account.");
      evt = PersonalityAddedEvent.userNotFound();
    }
    return evt;
  }

  @Override
  public RequestHandledEvent addPPSEQuestions(String userEmail,
                                              PPSEQuestions ppseQuestions) {
    User user = userRepository.findByEmail(userEmail);
    if (user == null)
      return RequestHandledEvent.userNotFound();
    PPSEQuestionsNode p = PPSEQuestionsNode.fromRestDomain(ppseQuestions);
    // Overwrite previous question node if exists
    if (user.getpPSEQuestions() != null)
      p.setNodeId(user.getpPSEQuestions().getNodeId());
    p.setUser(user.toNode());
    PPSEQuestionsNode r = ppseQuestionsRepository.save(p);
    return r == null
      ? RequestHandledEvent.badRequest()
      : new RequestHandledEvent<>(r.toRestDomain());
  }

  @Override
  public RequestHandledEvent requestResetPWD(String email) {
    User user = userRepository.findByEmail(email, 0);
    if (user == null)
      return RequestHandledEvent.userNotFound();
    String uuid = UUID.randomUUID().toString();
    String timestamp = Long.toString(new Date().getTime());
    String token = timestamp + "-" + uuid;
    user.setResetPwdToken(token);
    User savedUser = userRepository.save(user, 0);
    if (savedUser == null || savedUser.getResetPwdToken() == null)
      return RequestHandledEvent.failed();
    Institution institution = instRepository.findInstitutionByUserEMail(email);
    savedUser.setInstitution(institution);
    EmailResetPWD emailResetPWD =
      new EmailResetPWD(velocityEngine, savedUser, savedUser.getResetPwdToken());

    return new RequestHandledEvent<>(emailResetPWD);
  }

  @Override
  public RequestHandledEvent resetPwd(String email, String token, String newPwd) {
    User user = userRepository.findByEmail(email, 0);
    if (user == null)
      return RequestHandledEvent.userNotFound();
    if (!token.equals(user.getResetPwdToken()))
      return RequestHandledEvent.notAllowed();
    Long timestamp;
    try {
      timestamp = Long.valueOf(user.getResetPwdToken().split("-")[0]);
    } catch (NumberFormatException e) {
      return RequestHandledEvent.badRequest();
    }
    Long currentTimestamp = new Date().getTime();
    Long diff = currentTimestamp - timestamp;
    Long validTime = 86400000L; // 24×60×60×1000 - 24 hour in milic
    if (diff > validTime)
      return RequestHandledEvent.premissionExpired();
    String encryptedNewPwd = passwordEncoder.encode(newPwd);
    user.setPassword(encryptedNewPwd);
    user.setResetPwdToken("null");
    User savedUser = userRepository.save(user, 0);
    if (savedUser == null)
      return RequestHandledEvent.failed();
    return new RequestHandledEvent<>(savedUser);
  }

  @Override
  public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(s, 0);
    if (user == null)
      throw new UsernameNotFoundException("User: " + s + " not found");
    return new UserCredentialDetails(user);
  }

  @Override
  public CreatedEvent addVoteReminder(
    AddVoteReminderEvent addVoteReminderEvent) {
    VoteReminderAddedEvent evt;
    VoteReminderDetails details;

    if (null == addVoteReminderEvent)
      return VoteReminderAddedEvent.userNotFound();
    details = addVoteReminderEvent.getVoteReminderDetails();
    if ((null == details) || (null == details.getUserId()))
      return VoteReminderAddedEvent.userNotFound();
    if (null == addVoteReminderEvent.getVoteReminderDetails().getDate())
      return VoteReminderAddedEvent.failed(details);
    String emailAddress = addVoteReminderEvent.getVoteReminderDetails()
      .getUserId();
    if (LOG.isDebugEnabled()) LOG.debug("Email address - " + emailAddress);

    User user = userRepository.findByEmail(emailAddress);
    if (user != null) { // Valid User
      if (LOG.isDebugEnabled())
        LOG.debug("UserId - " + user.getNodeId());
      Long electionId = addVoteReminderEvent.getVoteReminderDetails()
        .getElectionId();
      Long date = addVoteReminderEvent.getVoteReminderDetails().getDate();
      String location = addVoteReminderEvent.getVoteReminderDetails()
        .getLocation();
      if (LOG.isDebugEnabled())
        LOG.debug("Election id - " + electionId + " Location - "
          + location + " Date - " + date);

      VoteReminder voteReminderAdded = userRepository.addVoteReminder(
        user.getNodeId(), electionId, date, location);
      if (voteReminderAdded != null) {
        evt = new VoteReminderAddedEvent();
        evt.setDetails(voteReminderAdded.toVoteReminderDetails());
      } else {
        evt = VoteReminderAddedEvent.electionNotFound();
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("No such account.");
      evt = VoteReminderAddedEvent.userNotFound();
    }
    return evt;
  }

  @Override
  public VoteRecordAddedEvent addVoteRecord(
    AddVoteRecordEvent addVoteRecordEvent) {
    VoteRecordAddedEvent evt;

    String emailAddress = addVoteRecordEvent.getVoteRecordDetails()
      .getVoterId();
    if (LOG.isDebugEnabled()) LOG.debug("Email address - " + emailAddress);

    User user = userRepository.findByEmail(emailAddress);
    if (user != null) { // Valid User
      if (LOG.isDebugEnabled())
        LOG.debug("UserId - " + user.getNodeId());
      Long electionId = addVoteRecordEvent.getVoteRecordDetails()
        .getElectionId();
      String location = addVoteRecordEvent.getVoteRecordDetails()
        .getLocation();
      if (LOG.isDebugEnabled())
        LOG.debug("Election id - " + electionId + " Location - "
          + location);

      VoteRecord voteRecordAdded = userRepository.addVoteRecord(
        user.getNodeId(), electionId, location);
      if (voteRecordAdded != null) {
        evt = new VoteRecordAddedEvent();
        evt.setDetails(voteRecordAdded.toVoteRecordDetails());
      } else {
        evt = VoteRecordAddedEvent.electionNotFound();
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("No such account.");
      evt = VoteRecordAddedEvent.userNotFound();
    }
    return evt;
  }

  @Override
  public ReadEvent readVoteRecord(ReadVoteRecordEvent readVoteRecordEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("readVoteRecord(" + readVoteRecordEvent.getNodeId() + ")");
    VoteRecord vr = userRepository.readVoteRecord(readVoteRecordEvent
      .getNodeId());
    ReadEvent response;
    if (vr == null) {
      response = VoteRecordReadEvent.notFound(readVoteRecordEvent
        .getNodeId());
    } else {
      VoteRecordDetails result = vr.toVoteRecordDetails();
      if (LOG.isDebugEnabled()) LOG.debug("Result - " + result);
      response = new VoteRecordReadEvent(readVoteRecordEvent.getNodeId(),
        result);
    }
    return response;
  }

  @Override
  public ReadEvent readVoteReminder(
    ReadVoteReminderEvent readVoteReminderEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("readVoteRecord(" + readVoteReminderEvent.getNodeId()
        + ")");
    VoteReminder vr = userRepository.readVoteReminder(readVoteReminderEvent
      .getNodeId());
    ReadEvent response;
    if (vr == null) {
      response = VoteReminderReadEvent.notFound(readVoteReminderEvent
        .getNodeId());
    } else {
      VoteReminderDetails result = vr.toVoteReminderDetails();
      if (LOG.isDebugEnabled()) LOG.debug("Result - " + result);
      response = new VoteReminderReadEvent(
        readVoteReminderEvent.getNodeId(), result);
    }
    return response;
  }

  @Override
  public DeletedEvent deleteVoteRecord(
    DeleteVoteRecordEvent deleteVoteRecordEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("deleteVoteRecord(" + deleteVoteRecordEvent.getNodeId()
        + ")");
    VoteRecord vr = userRepository.deleteVoteRecord(deleteVoteRecordEvent
      .getNodeId());
    DeletedEvent response;
    if (vr == null) {
      response = VoteRecordDeletedEvent.notFound(deleteVoteRecordEvent
        .getNodeId());
    } else {
      VoteRecordDetails result = vr.toVoteRecordDetails();
      if (LOG.isDebugEnabled()) LOG.debug("Result - " + result);
      // response=new
      // VoteRecordDeletedEvent(deleteVoteRecordEvent.getVoteRecordId(),
      // result);
      response = new VoteRecordDeletedEvent(
        deleteVoteRecordEvent.getNodeId());
    }
    return response;
  }

  @Override
  public DeletedEvent deleteVoteReminder(
    DeleteVoteReminderEvent deleteVoteReminderEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("deleteVoteReminder("
        + deleteVoteReminderEvent.getNodeId() + ")");
    VoteReminder vr = userRepository
      .deleteVoteReminder(deleteVoteReminderEvent.getNodeId());
    DeletedEvent response;
    if (vr == null) {
      response = VoteReminderDeletedEvent
        .notFound(deleteVoteReminderEvent.getNodeId());
    } else {
      VoteReminderDetails result = vr.toVoteReminderDetails();
      if (LOG.isDebugEnabled()) LOG.debug("Result - " + result);
      // response=new
      // VoteReminderDeletedEvent(deleteVoteReminderEvent.getVoteReminderId(),
      // result);
      response = new VoteReminderDeletedEvent(
        deleteVoteReminderEvent.getNodeId());
    }
    return response;
  }

  public Long findUserId(String emailAddress) {
    if (LOG.isDebugEnabled())
      LOG.debug("findUserId(" + emailAddress + ")");
    Long response = null;
    if (emailAddress != null) {
      User user = userRepository.findByEmail(emailAddress);
      if (user != null) {
        response = user.getNodeId();
      }
    }
    return response;

  }

  public String findUserEmail(Long userId) {
    if (LOG.isDebugEnabled())
      LOG.debug("findUserEmail(" + userId + ")");
    String response = null;
    if (userId != null) {
      User user = userRepository.findOne(userId);
      if (user != null) {
        response = user.getEmail();
      }
    }
    return response;

  }

  @Override
  public AllReadEvent readSupportsById(ReadAllEvent userEvent,
                                       Direction sortDirection, int pageNumber, int pageLength) {
    Long userId = userEvent.getParentId();
    String email = findUserEmail(userId);

    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(email);
    return readSupportsByEmail(requestReadUserEvent, sortDirection, pageNumber, pageLength);
  }

  @Override
  public AllReadEvent readSupportsByEmail(
    RequestReadUserEvent requestReadUserEvent, Direction sortDirection,
    int pageNumber, int pageLength) {
    String email = requestReadUserEvent.getEmail();
    Long userId = findUserId(email);
    AllReadEvent nare = null;

    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "b.name");

    Page<Ticket> tickets = null;
    ArrayList<TicketDetails> dets = new ArrayList<TicketDetails>();

    if (LOG.isDebugEnabled()) LOG.debug("Email " + email);

    tickets = userRepository.findSupports(userId, pageable);
    if (tickets != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + tickets.getTotalElements() + " total pages =" + tickets.getTotalPages());
      Iterator<Ticket> iter = tickets.iterator();
      while (iter.hasNext()) {
        Ticket na = iter.next();
        if (LOG.isDebugEnabled())
          LOG.debug("Converting to details - " + na.getName());
        TicketDetails det = na.toTicketDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        User user = userRepository.findByEmail(email);
        if ((null == user) ||
          ((null == user.getEmail()) || ((null == user.getGivenName()) && (null == user.getFamilyName()) && (null == user.getGender())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(UserId)");
          nare = AllReadEvent.notFound(userId);
        } else {
          nare = new AllReadEvent(userId, dets, tickets.getTotalElements(), tickets.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(userId, dets, tickets.getTotalElements(), tickets.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(userId);
    }
    return nare;
  }

  @Override
  public AllReadEvent readVoteRemindersById(ReadAllEvent userEvent,
                                            Direction sortDirection, int pageNumber, int pageLength) {
    Long userId = userEvent.getParentId();
    String email = findUserEmail(userId);

    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(email);
    return readVoteRemindersByEmail(requestReadUserEvent, sortDirection, pageNumber, pageLength);
  }

  @Override
  public AllReadEvent readVoteRemindersByEmail(
    RequestReadUserEvent requestReadUserEvent, Direction sortDirection,
    int pageNumber, int pageLength) {
    String email = requestReadUserEvent.getEmail();
    Long userId = findUserId(email);
    AllReadEvent nare = null;

    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "r.date");

    Page<VoteReminder> tickets = null;
    ArrayList<VoteReminderDetails> dets = new ArrayList<VoteReminderDetails>();

    if (LOG.isDebugEnabled()) LOG.debug("Email " + email);

    tickets = userRepository.findVoteReminders(userId, pageable);
    if (tickets != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + tickets.getTotalElements() + " total pages =" + tickets.getTotalPages());
      Iterator<VoteReminder> iter = tickets.iterator();
      while (iter.hasNext()) {
        VoteReminder na = iter.next();
        if (LOG.isDebugEnabled())
          LOG.debug("Converting to details - " + na.getDate());
        VoteReminderDetails det = na.toVoteReminderDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        User user = userRepository.findByEmail(email);
        if ((null == user) ||
          ((null == user.getEmail()) || ((null == user.getGivenName()) && (null == user.getFamilyName()) && (null == user.getGender())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(UserId)");
          nare = AllReadEvent.notFound(userId);
        } else {
          nare = new AllReadEvent(userId, dets, tickets.getTotalElements(), tickets.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(userId, dets, tickets.getTotalElements(), tickets.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(userId);
    }
    return nare;
  }

  @Override
  public AllReadEvent readVoteRecordsById(ReadAllEvent userEvent,
                                          Direction sortDirection, int pageNumber, int pageLength) {
    Long userId = userEvent.getParentId();
    String email = findUserEmail(userId);

    RequestReadUserEvent requestReadUserEvent = new RequestReadUserEvent(email);
    return readVoteRecordsByEmail(requestReadUserEvent, sortDirection, pageNumber, pageLength);
  }

  @Override
  public AllReadEvent readVoteRecordsByEmail(
    RequestReadUserEvent requestReadUserEvent, Direction sortDirection,
    int pageNumber, int pageLength) {
    String email = requestReadUserEvent.getEmail();
    Long userId = findUserId(email);
    AllReadEvent nare = null;

    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "r.date");

    Page<VoteRecord> tickets = null;
    ArrayList<VoteRecordDetails> dets = new ArrayList<VoteRecordDetails>();

    if (LOG.isDebugEnabled()) LOG.debug("Email " + email);

    tickets = userRepository.findVoteRecords(userId, pageable);
    if (tickets != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + tickets.getTotalElements() + " total pages =" + tickets.getTotalPages());
      Iterator<VoteRecord> iter = tickets.iterator();
      while (iter.hasNext()) {
        VoteRecord na = iter.next();
        if (LOG.isDebugEnabled())
          LOG.debug("Converting to details - " + na.getDate());
        VoteRecordDetails det = na.toVoteRecordDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        User user = userRepository.findByEmail(email);
        if ((null == user) ||
          ((null == user.getEmail()) || ((null == user.getGivenName()) && (null == user.getFamilyName()) && (null == user.getGender())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(UserId)");
          nare = AllReadEvent.notFound(userId);
        } else {
          nare = new AllReadEvent(userId, dets, tickets.getTotalElements(), tickets.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(userId, dets, tickets.getTotalElements(), tickets.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(userId);
    }
    return nare;
  }

  @Override
  public void updateSNSTokens(String userEmail, String topicArn, String deviceToken) {
    assert (userEmail != null && topicArn != null && deviceToken != null);
    User user = userRepository.findByEmail(userEmail, 0);
    // Abort if user not found
    if (user == null){
      LOG.error("User not found in updateSNSTokens");
      return;
    }
    // Abort if topicArn and deviceToken is already up-to-date.
    if (topicArn.equals(user.getArn()) && deviceToken.equals(user.getDeviceToken()))
      return;

    user.setArn(topicArn);
    user.setDeviceToken(deviceToken);
    userRepository.save(user, 0);
  }
}
