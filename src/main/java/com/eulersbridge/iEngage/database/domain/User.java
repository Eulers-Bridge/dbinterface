package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.activation.DataContentHandler;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@NodeEntity
public class User extends Node {
  private static final Logger LOG = LoggerFactory.getLogger(User.class);
  @NotNull
  @NotBlank
  @Email
  @Index(unique = true, primary = false)
  private String email;
  private String givenName;
  private String familyName;
  private String gender;
  private String nationality;
  private String yearOfBirth;
  private String password;
  private String contactNumber;
  private String roles;
  private Boolean accountVerified;
  private Boolean optOutDataCollection;
  private Boolean trackingOff;
  private Boolean consentGiven;
  private String profilePhoto;
  private Long experience;

  @Relationship(type = DatabaseDomainConstants.HAS_PERSONALITY_LABEL, direction = Relationship.OUTGOING)
  private Node personality;
  @Relationship(type = DatabaseDomainConstants.USERS_LABEL, direction = Relationship.OUTGOING)
  private Node institution;
  @Relationship(type = DatabaseDomainConstants.VERIFIED_BY_LABEL, direction = Relationship.UNDIRECTED)
  private List<Node> verificationToken;
  @Relationship(type = DatabaseDomainConstants.HAS_PHOTO_LABEL, direction = Relationship.UNDIRECTED)
  private List<Node> photos;

  @Relationship(type = DatabaseDomainConstants.CONTACT_LABEL, direction = Relationship.UNDIRECTED)
  private List<Contact> contacts;
  @Relationship(type = DatabaseDomainConstants.HAS_COMPLETED_TASK_LABEL, direction = Relationship.UNDIRECTED)
  private List<TaskComplete> completedTasks;
  @Relationship(type = DatabaseDomainConstants.HAS_COMPLETED_BADGE_LABEL, direction = Relationship.UNDIRECTED)
  private List<BadgeComplete> completedBadges;


  //  @Query("match (n:" + DatabaseDomainConstants.TASK + ") return count(n)")
//  private Long totalTasks;
//

//  @Query("match (n:" + DatabaseDomainConstants.BADGE + ") return count(n)")
//  private Long totalBadges;
//

  public User() {
    if (LOG.isDebugEnabled()) LOG.debug("Constructor");
  }

  public User(Long nodeId) {
    super(nodeId);
  }

  public User(String email, String givenName, String familyName, String gender, String nationality, String yearOfBirth, String password, String contactNumber) {
    if (LOG.isTraceEnabled())
      LOG.trace("Constructor(" + email + ',' + givenName + ',' + familyName + ',' + gender + ',' + nationality + ',' + yearOfBirth + ')');
    this.email = email;
    this.givenName = givenName;
    this.familyName = familyName;
    this.gender = gender;
    this.nationality = nationality;
    this.yearOfBirth = yearOfBirth;
    this.password = password;
    this.contactNumber = contactNumber;
  }

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

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public Boolean getAccountVerified() {
    return accountVerified;
  }

  public void setAccountVerified(Boolean accountVerified) {
    this.accountVerified = accountVerified;
  }

  public Boolean getOptOutDataCollection() {
    return optOutDataCollection;
  }

  public void setOptOutDataCollection(Boolean optOutDataCollection) {
    this.optOutDataCollection = optOutDataCollection;
  }

  public Boolean getTrackingOff() {
    return trackingOff;
  }

  public void setTrackingOff(Boolean trackingOff) {
    this.trackingOff = trackingOff;
  }

  public Boolean getConsentGiven() {
    return consentGiven;
  }

  public void setConsentGiven(Boolean consentGiven) {
    this.consentGiven = consentGiven;
  }

  public String getProfilePhoto() {
    return profilePhoto;
  }

  public void setProfilePhoto(String profilePhoto) {
    this.profilePhoto = profilePhoto;
  }

  public Long getExperience() {
    return experience;
  }

  public void setExperience(Long experience) {
    this.experience = experience;
  }


  //=============================================

  public List<TaskComplete> getCompletedTasks() {
    return completedTasks;
  }

  public List<BadgeComplete> getCompletedBadges() {
    return completedBadges;
  }

  public void setCompletedTasks(List<TaskComplete> completedTasks) {
    this.completedTasks = completedTasks;
  }

  public void setCompletedBadges(List<BadgeComplete> completedBadges) {
    this.completedBadges = completedBadges;
  }

  public Integer getNumOfCompTasks() {
    if (completedTasks == null)
      return 0;
    return completedTasks.size();
  }

  public Integer getNumOfCompBadges() {
    if (completedBadges == null)
      return 0;
    return completedBadges.size();
  }

  public Integer getNumOfContacts() {
    if (contacts == null)
      return 0;
    else
      return contacts.size();
  }

  public Institution getInstitution() {
    return (Institution) institution;
  }

  public void setInstitution(Node institution) {
    this.institution = institution;
  }

  public List<VerificationToken> getVerificationToken() {
    return castList(verificationToken, VerificationToken.class);
  }

  public void setVerificationToken(List<Node> verificationToken) {
    this.verificationToken = verificationToken;
  }

  public Personality getPersonality() {
    return (Personality) personality;
  }

  public void setPersonality(Node personality) {
    this.personality = personality;
  }

  public List<Photo> getPhotos() {
    return castList(photos, Photo.class);
  }

  public void setPhotos(List<Node> photos) {
    this.photos = photos;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }

  public UserDetails toUserDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toUserDetails()");

    UserDetails details = new UserDetails();
    if (LOG.isTraceEnabled()) LOG.trace("user " + this);

    BeanUtils.copyProperties(this, details);

    details.setUserId(nodeId);

    details.setInstitutionId(this.institution.getNodeId());
    Boolean personality = false;
    Personality thisPersonality = getPersonality();
    if (thisPersonality != null) {
      if (LOG.isDebugEnabled()) LOG.debug("personality = " + getPersonality());
      personality = true;
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("personality = " + getPersonality());
    }
    details.setHasPersonality(personality);
    if (null == getAccountVerified())
      details.setAccountVerified(DatabaseDomainConstants.AccountVerifiedDefault);
    else
      details.setAccountVerified(getAccountVerified());
    if (null == getConsentGiven())
      details.setConsentGiven(DatabaseDomainConstants.ConsentGivenDefault);
    else
      details.setConsentGiven(getConsentGiven());
    if (null == getTrackingOff())
      details.setTrackingOff(DatabaseDomainConstants.TrackingOffDefault);
    else
      details.setTrackingOff(getTrackingOff());
    if (null == getOptOutDataCollection())
      details.setOptOutDataCollection(DatabaseDomainConstants.OptOutDataCollectionDefault);
    else
      details.setOptOutDataCollection(getOptOutDataCollection());
    details.setPhotos(Photo.photosToPhotoDetails(getPhotos()));

    if (LOG.isTraceEnabled()) LOG.trace("userDetails " + details);

    return details;
  }

  public static User fromUserDetails(UserDetails userDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromUserDetails()");
    User user = new User();
    if (LOG.isTraceEnabled()) LOG.trace("userDetails " + userDetails);
    user.email = userDetails.getEmail();
    user.givenName = userDetails.getGivenName();
    user.gender = userDetails.getGender();
    user.familyName = userDetails.getFamilyName();
    user.nationality = userDetails.getNationality();
    user.password = userDetails.getPassword();
    user.setContactNumber(userDetails.getContactNumber());
    user.setYearOfBirth(userDetails.getYearOfBirth());
    user.setAccountVerified(userDetails.isAccountVerified());
    user.setConsentGiven(userDetails.isConsentGiven());
    user.setTrackingOff(userDetails.isTrackingOff());
    user.setOptOutDataCollection(userDetails.isOptOutDataCollection());
    user.setProfilePhoto(userDetails.getProfilePhoto());
    Institution inst = new Institution();
    inst.setNodeId(userDetails.getInstitutionId());
    user.institution = inst.toNode();
    if (LOG.isTraceEnabled()) LOG.trace("user " + user);
    user.setExperience(userDetails.getExperience());

    return user;
  }

  public String toString() {
    StringBuffer buff = new StringBuffer("[ nodeId = ");
    String retValue;
    buff.append(getNodeId());
    buff.append(", email = ");
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
    buff.append(", personality = ");
    buff.append(getPersonality());
    buff.append(", photos = ");
    buff.append(getPhotos());
    buff.append(", accountVerified = ");
    buff.append(getAccountVerified());
    buff.append(", consentGiven = ");
    buff.append(getConsentGiven());
    buff.append(", optOutDataCollection = ");
    buff.append(getOptOutDataCollection());
    buff.append(", trackingOff = ");
    buff.append(getTrackingOff());
    buff.append(" ]");
    retValue = buff.toString();
    if (LOG.isTraceEnabled()) LOG.trace("toString() = " + retValue);
    return retValue;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (nodeId != null) {
      result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
    } else {
      result = prime * result + ((accountVerified == null) ? 0 : accountVerified.hashCode());
      result = prime * result + ((consentGiven == null) ? 0 : consentGiven.hashCode());
      result = prime * result + ((trackingOff == null) ? 0 : trackingOff.hashCode());
      result = prime * result + ((optOutDataCollection == null) ? 0 : optOutDataCollection.hashCode());
      result = prime * result + ((email == null) ? 0 : email.hashCode());
      result = prime * result
        + ((familyName == null) ? 0 : familyName.hashCode());
      result = prime * result + ((gender == null) ? 0 : gender.hashCode());
      result = prime * result
        + ((givenName == null) ? 0 : givenName.hashCode());
      result = prime * result
        + ((institution == null) ? 0 : institution.hashCode());
      result = prime * result
        + ((nationality == null) ? 0 : nationality.hashCode());
      result = prime * result
        + ((password == null) ? 0 : password.hashCode());
      result = prime * result
        + ((contactNumber == null) ? 0 : contactNumber.hashCode());
      result = prime * result
        + ((personality == null) ? 0 : personality.hashCode());
      result = prime * result + ((roles == null) ? 0 : roles.hashCode());
      result = prime * result + ((photos == null) ? 0 : photos.hashCode());
      result = prime
        * result
        + ((verificationToken == null) ? 0 : verificationToken
        .hashCode());
      result = prime * result
        + ((yearOfBirth == null) ? 0 : yearOfBirth.hashCode());
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (nodeId != null) {
      if (nodeId.equals(other.nodeId))
        return true;
      else return false;
    } else {
      if (other.nodeId != null)
        return false;
      if (accountVerified != other.accountVerified)
        return false;
      if (consentGiven != other.consentGiven)
        return false;
      if (trackingOff != other.trackingOff)
        return false;
      if (optOutDataCollection != other.optOutDataCollection)
        return false;
      if (email == null) {
        if (other.email != null)
          return false;
      } else if (!email.equals(other.email))
        return false;
      if (familyName == null) {
        if (other.familyName != null)
          return false;
      } else if (!familyName.equals(other.familyName))
        return false;
      if (gender == null) {
        if (other.gender != null)
          return false;
      } else if (!gender.equals(other.gender))
        return false;
      if (givenName == null) {
        if (other.givenName != null)
          return false;
      } else if (!givenName.equals(other.givenName))
        return false;
      if (institution == null) {
        if (other.institution != null)
          return false;
      } else if (!institution.equals(other.institution))
        return false;
      if (nationality == null) {
        if (other.nationality != null)
          return false;
      } else if (!nationality.equals(other.nationality))
        return false;
      if (password == null) {
        if (other.password != null)
          return false;
      } else if (!password.equals(other.password))
        return false;
      if (contactNumber == null) {
        if (other.contactNumber != null)
          return false;
      } else if (!contactNumber.equals(other.contactNumber))
        return false;
      if (personality == null) {
        if (other.personality != null)
          return false;
      } else if (!personality.equals(other.personality))
        return false;
      if (roles == null) {
        if (other.roles != null)
          return false;
      } else if (!roles.equals(other.roles))
        return false;
      if (photos == null) {
        if (other.photos != null)
          return false;
      } else if (!photos.equals(other.photos))
        return false;
      if (verificationToken == null) {
        if (other.verificationToken != null)
          return false;
      } else if (!verificationToken.equals(other.verificationToken))
        return false;
      if (yearOfBirth == null) {
        if (other.yearOfBirth != null)
          return false;
      } else if (!yearOfBirth.equals(other.yearOfBirth))
        return false;
    }
    return true;
  }

  public boolean comparePassword(String password) {
    return password.equals(this.password);
  }

  public static void copyUntweakablePropoties(User origin, User target) {
    target.setExperience(origin.getExperience());
  }
}
