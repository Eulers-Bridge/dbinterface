package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.rest.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Iterator;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


public class UserDomain extends ResourceSupport {
  private String givenName;
  private String familyName;
  private String gender;
  private String nationality;
  private String yearOfBirth;
  private String password;
  private String contactNumber;
  private Boolean accountVerified;
  private Boolean hasPersonality;
  private Boolean hasPPSEQuestions;
  private Long institutionId;
  @Email
  private String email;
  private Iterable<PhotoDetails> photos;
  private Boolean consentGiven;
  private Boolean trackingOff;
  private Boolean optOutDataCollection;
  private String profilePhoto;

  private Integer numOfCompTasks;
  private Integer numOfCompBadges;
  private Long experience;
  private Long level;

  private String arn;
  private String deviceToken;

  private static Logger LOG = LoggerFactory.getLogger(UserDomain.class);

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(String yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getExperience() {
    return experience;
  }

  public void setExperience(Long experience) {
    this.experience = experience;
  }

  public Long getLevel() {
    return level;
  }

  public void setLevel(Long level) {
    this.level = level;
  }

  public Integer getNumOfCompTasks() {
    return numOfCompTasks;
  }

  public void setNumOfCompTasks(Integer numOfCompTasks) {
    this.numOfCompTasks = numOfCompTasks;
  }

  public Integer getNumOfCompBadges() {
    return numOfCompBadges;
  }

  public void setNumOfCompBadges(Integer numOfCompBadges) {
    this.numOfCompBadges = numOfCompBadges;
  }

  public String getArn() {
    return arn;
  }

  public void setArn(String arn) {
    this.arn = arn;
  }

  public String getDeviceToken() {
    return deviceToken;
  }

  public void setDeviceToken(String deviceToken) {
    this.deviceToken = deviceToken;
  }

  /**
   * @return the contactNumber
   */
  public String getContactNumber() {
    return contactNumber;
  }

  /**
   * @param contactNumber the contactNumber to set
   */
  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public Boolean isAccountVerified() {
    return accountVerified;
  }

  public void setAccountVerified(Boolean accountVerified) {
    this.accountVerified = accountVerified;
  }

  public Boolean getHasPPSEQuestions() {
    return hasPPSEQuestions;
  }

  public void setHasPPSEQuestions(Boolean hasPPSEQuestions) {
    this.hasPPSEQuestions = hasPPSEQuestions;
  }

  /**
   * @return the consentGiven
   */
  public Boolean isConsentGiven() {
    return consentGiven;
  }

  /**
   * @param consentGiven the consentGiven to set
   */
  public void setConsentGiven(Boolean consentGiven) {
    this.consentGiven = consentGiven;
  }

  public Long getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(Long institutionId) {
    this.institutionId = institutionId;
  }

  /**
   * @return the hasPersonality
   */
  public Boolean getHasPersonality() {
    return hasPersonality;
  }

  /**
   * @param hasPersonality the hasPersonality to set
   */
  public void setHasPersonality(Boolean hasPersonality) {
    this.hasPersonality = hasPersonality;
  }

  /**
   * @return the photos
   */
  public Iterable<PhotoDetails> getPhotos() {
    return photos;
  }

  /**
   * @param photos the photos to set
   */
  public void setPhotos(Iterable<PhotoDetails> photos) {
    this.photos = photos;
  }

  /**
   * @return the trackingOn
   */
  public Boolean isTrackingOff() {
    return trackingOff;
  }

  /**
   * @param trackingOn the trackingOn to set
   */
  public void setTrackingOff(Boolean trackingOff) {
    this.trackingOff = trackingOff;
  }

  /**
   * @return the optOutDataCollection
   */
  public Boolean isOptOutDataCollection() {
    return optOutDataCollection;
  }

  /**
   * @param optOutDataCollection the optOutDataCollection to set
   */
  public void setOptOutDataCollection(Boolean optOutDataCollection) {
    this.optOutDataCollection = optOutDataCollection;
  }

  public String getProfilePhoto() {
    return profilePhoto;
  }

  public void setProfilePhoto(String profilePhoto) {
    this.profilePhoto = profilePhoto;
  }

  public UserDetails toUserDetails() {
    UserDetails details = new UserDetails(email);

    details.setGivenName(getGivenName());
    details.setFamilyName(getFamilyName());
    details.setGender(getGender());
    details.setNationality(getNationality());
    details.setYearOfBirth(getYearOfBirth());
    details.setPassword(getPassword());
    details.setContactNumber(getContactNumber());
    details.setAccountVerified(isAccountVerified());
    details.setInstitutionId(getInstitutionId());
    details.setOptOutDataCollection(isOptOutDataCollection());
    details.setTrackingOff(isTrackingOff());
    details.setConsentGiven(isConsentGiven());
    details.setProfilePhoto(getProfilePhoto());
    details.setArn(arn);
    details.setDeviceToken(deviceToken);
    return details;
  }

  // {!begin fromUserDetails}

  public static UserDomain fromUserDetails(UserDetails readUser) {
    UserDomain user = new UserDomain();

    user.setEmail(readUser.getEmail());
    user.setGivenName(readUser.getGivenName());
    user.setFamilyName(readUser.getFamilyName());
    user.setGender(readUser.getGender());
    user.setNationality(readUser.getNationality());
    user.setYearOfBirth(readUser.getYearOfBirth());
    String password = readUser.getPassword(), hash = null;
//    if (password != null) {
//      try {
//        hash = PasswordHash.createHash(password);
//      } catch (NoSuchAlgorithmException e) {
//        e.printStackTrace();
//      } catch (InvalidKeySpecException e) {
//        e.printStackTrace();
//      }
//    }
//    user.setPassword(hash);
    user.setContactNumber(readUser.getContactNumber());
    user.setAccountVerified(readUser.isAccountVerified());
    user.setInstitutionId(readUser.getInstitutionId());
    user.setHasPersonality(readUser.hasPersonality());
    user.setHasPPSEQuestions(readUser.getHasPPSEQuestions());
    user.setTrackingOff(readUser.isTrackingOff());
    user.setOptOutDataCollection(readUser.isOptOutDataCollection());
    user.setConsentGiven(readUser.isConsentGiven());
    user.setPhotos(readUser.getPhotos());
    user.setProfilePhoto(readUser.getProfilePhoto());
    user.setExperience(readUser.getExperience());
    user.setLevel(calculateUserLevel(readUser.getExperience()));
    user.setNumOfCompBadges(readUser.getNumOfCompBadges());
    user.setNumOfCompTasks(readUser.getNumOfCompTasks());
    user.setArn(readUser.getArn());
    user.setDeviceToken(readUser.getDeviceToken());

    String simpleName = UserDomain.class.getSimpleName();
    String name = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);

    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
    //this section is all that is actually needed in our model to add hateoas support.

    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
    //We have explicitly avoided that.
    // {!begin selfRel}
    user.add(new Link(linkTo(UserController.class).slash(name).slash(user.email).toUriComponentsBuilder().build().toUriString() + "/", "self"));
    // {!end selfRel}
    return user;
  }

  public String toString() {
    StringBuffer buff = new StringBuffer("[ email = ");
    String retValue;
    buff.append(getEmail());
    buff.append(", givenName = ");
    buff.append(getGivenName());
    buff.append(", familyName = ");
    buff.append(getFamilyName());
    buff.append(", gender = ");
    buff.append(getGender());
    buff.append(", nationality = ");
    buff.append(getNationality());
    buff.append(", yearOfBirth = ");
    buff.append(getYearOfBirth());
    buff.append(", password = ");
    buff.append(getPassword());
    buff.append(", photos = ");
    buff.append(getPhotos());
    buff.append(", contactNumber = ");
    buff.append(getContactNumber());
    buff.append(", institutionId = ");
    buff.append(getInstitutionId());
    buff.append(", accountVerified = ");
    buff.append(isAccountVerified());
    buff.append(", consentGiven = ");
    buff.append(isConsentGiven());
    buff.append(", optOutDataCollection = ");
    buff.append(isOptOutDataCollection());
    buff.append(", trackingOff = ");
    buff.append(isTrackingOff());
    buff.append(" ]");
    retValue = buff.toString();
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  public static Iterator<LikeInfo> toLikesIterator(Iterator<UserDetails> iter) {
    if (null == iter) return null;
    ArrayList<LikeInfo> likes = new ArrayList<LikeInfo>();
    while (iter.hasNext()) {
      UserDetails userDetails = iter.next();
      LikeInfo likeInfo = LikeInfo.fromUserDetails(userDetails);
      likes.add(likeInfo);
    }
    return likes.iterator();
  }

  public static Long calculateUserLevel(Long experience) {
    if (experience == null || experience < 0)
      return 0l;
    else
      return experience / 1000;
  }
}
