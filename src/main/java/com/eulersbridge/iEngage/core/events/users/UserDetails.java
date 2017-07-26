/**
 *
 */
package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Newitt
 */
public class UserDetails extends Details {

  private Long userId;
  private String email;
  private String givenName;
  private String familyName;
  private String gender;
  private String profilePhoto;
  private Iterable<PhotoDetails> photos;
  private String nationality;
  private String yearOfBirth;
  private String password;
  private String contactNumber;
  private Boolean accountVerified;
  private Boolean hasPersonality;
  private Boolean hasPPSEQuestions;
  private Boolean consentGiven;
  private Boolean optOutDataCollection;
  private Boolean trackingOff;
  private Long institutionId;

  private Long numOfCompTasks;
  private Long totalTasks;
  private Long numOfCompBadges;
  private Long totalBadges;
  private Long numOfContacts;

  private Long experience = 0l;

  private static Logger LOG = LoggerFactory.getLogger(UserDetails.class);

  public UserDetails() {
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public UserDetails(String email) {
    this.email = email;
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

  public Long getNumOfCompTasks() {
    return numOfCompTasks;
  }

  public void setNumOfCompTasks(Long numOfCompTasks) {
    this.numOfCompTasks = numOfCompTasks;
  }

  public Long getNumOfCompBadges() {
    return numOfCompBadges;
  }

  public void setNumOfCompBadges(Long numOfCompBadges) {
    this.numOfCompBadges = numOfCompBadges;
  }

  public Long getTotalTasks() {
    return totalTasks;
  }

  public void setTotalTasks(Long totalTasks) {
    this.totalTasks = totalTasks;
  }

  public Long getTotalBadges() {
    return totalBadges;
  }

  public void setTotalBadges(Long totalBadges) {
    this.totalBadges = totalBadges;
  }

  public Long getNumOfContacts() {
    return numOfContacts;
  }

  public void setNumOfContacts(Long numOfContacts) {
    this.numOfContacts = numOfContacts;
  }

  public Long getExperience() {
    return experience;
  }

  public void setExperience(Long experience) {
    this.experience = experience;
  }

  public String getProfilePhoto() {
    return profilePhoto;
  }

  public void setProfilePhoto(String profilePhoto) {
    this.profilePhoto = profilePhoto;
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

  public Long getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(Long institutionId) {
    this.institutionId = institutionId;
  }

  public void setEmail(String email) {
    this.email = email;

  }

  public String getEmail() {
    return email;
  }

  /**
   * @return the firstName
   */
  public String getGivenName() {
    return givenName;
  }

  /**
   * @param givenName the firstName to set
   */
  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  /**
   * @return the accountVerified
   */
  public Boolean isAccountVerified() {
    return accountVerified;
  }

  /**
   * @param accountVerified the accountVerified to set
   */
  public void setAccountVerified(Boolean accountVerified) {
    this.accountVerified = accountVerified;
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

  /**
   * @return the hasPersonality
   */
  public Boolean hasPersonality() {
    return this.hasPersonality;
  }

  /**
   * @param personality the hasPersonality to set
   */
  public void setHasPersonality(Boolean personality) {
    this.hasPersonality = personality;
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

  /**
   * @return the trackingOff
   */
  public Boolean isTrackingOff() {
    return trackingOff;
  }

  /**
   * @param trackingOff the trackingOff to set
   */
  public void setTrackingOff(Boolean trackingOff) {
    this.trackingOff = trackingOff;
  }

  public Boolean getHasPPSEQuestions() {
    return hasPPSEQuestions;
  }

  public void setHasPPSEQuestions(Boolean hasPPSEQuestions) {
    this.hasPPSEQuestions = hasPPSEQuestions;
  }



  @Override
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
    buff.append(", contactNumber = ");
    buff.append(getContactNumber());
    buff.append(", accountVerified = ");
    buff.append(isAccountVerified());
    buff.append(", hasPersonality = ");
    buff.append(hasPersonality());
    buff.append(", consentGiven = ");
    buff.append(isConsentGiven());
    buff.append(", optOutDataCollection = ");
    buff.append(isOptOutDataCollection());
    buff.append(", trackingOff = ");
    buff.append(isTrackingOff());
    buff.append(", numOfCompTasks = ");
    buff.append(getNumOfCompTasks());
    buff.append(" ]");
    retValue = buff.toString();
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((accountVerified == null) ? 0 : accountVerified.hashCode());
    result = prime * result + ((hasPersonality == null) ? 0 : hasPersonality.hashCode());
    result = prime * result + ((consentGiven == null) ? 0 : consentGiven.hashCode());
    result = prime * result + ((optOutDataCollection == null) ? 0 : optOutDataCollection.hashCode());
    result = prime * result + ((trackingOff == null) ? 0 : trackingOff.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result
      + ((familyName == null) ? 0 : familyName.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result
      + ((givenName == null) ? 0 : givenName.hashCode());
    result = prime * result
      + ((institutionId == null) ? 0 : institutionId.hashCode());
    result = prime * result
      + ((nationality == null) ? 0 : nationality.hashCode());
    result = prime * result
      + ((password == null) ? 0 : password.hashCode());
    result = prime * result
      + ((contactNumber == null) ? 0 : contactNumber.hashCode());
    result = prime * result
      + ((yearOfBirth == null) ? 0 : yearOfBirth.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserDetails other = (UserDetails) obj;

    if (accountVerified == null) {
      if (other.accountVerified != null)
        return false;
    } else if (!accountVerified.equals(other.accountVerified))
      return false;
    if (hasPersonality == null) {
      if (other.hasPersonality != null)
        return false;
    } else if (!hasPersonality.equals(other.hasPersonality))
      return false;
    if (consentGiven == null) {
      if (other.consentGiven != null)
        return false;
    } else if (!consentGiven.equals(other.consentGiven))
      return false;
    if (trackingOff == null) {
      if (other.trackingOff != null)
        return false;
    } else if (!trackingOff.equals(other.trackingOff))
      return false;
    if (optOutDataCollection == null) {
      if (other.optOutDataCollection != null)
        return false;
    } else if (!optOutDataCollection.equals(other.optOutDataCollection))
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
    if (institutionId == null) {
      if (other.institutionId != null)
        return false;
    } else if (!institutionId.equals(other.institutionId))
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
    if (yearOfBirth == null) {
      if (other.yearOfBirth != null)
        return false;
    } else if (!yearOfBirth.equals(other.yearOfBirth))
      return false;
    return true;
  }

}
