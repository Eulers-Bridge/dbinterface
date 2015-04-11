/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;

/**
 * @author Greg Newitt
 *
 */
public class UserDetails extends Details
{

	private String email;
	private String givenName;
	private String familyName;
	private String gender;
	private Iterable<PhotoDetails> photos;
	private String nationality;
	private String yearOfBirth;
	private String password;
	private String contactNumber;
	private boolean accountVerified=false;
	private boolean hasPersonality=false;
	private boolean optOutDataCollection=false;
	private boolean trackingOff=false;
	private	Long institutionId;

    private Long numOfCompTasks;
    private Long numOfCompBadges;

    private static Logger LOG = LoggerFactory.getLogger(UserDetails.class);
    
	public UserDetails() 
	{
	}

	public UserDetails(String email) 
	{
		this.email=email;
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

    /**
	 * @return the contactNumber
	 */
	public String getContactNumber()
	{
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber)
	{
		this.contactNumber = contactNumber;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public void setEmail(String email) 
	{
		this.email=email;
		
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
	 * @param firstName the firstName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the accountVerified
	 */
	public boolean isAccountVerified() {
		return accountVerified;
	}

	/**
	 * @param accountVerified the accountVerified to set
	 */
	public void setAccountVerified(boolean accountVerified) {
		this.accountVerified = accountVerified;
	}
	
	/**
	 * @return the hasPersonality
	 */
	public Boolean hasPersonality()
	{
		return this.hasPersonality;
	}
	
	/**
	 * @param hasPersonality the hasPersonality to set
	 */
	public void setHasPersonality(Boolean personality)
	{
		this.hasPersonality=personality;
	}

	/**
	 * @return the photos
	 */
	public Iterable<PhotoDetails> getPhotos()
	{
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(Iterable<PhotoDetails> photos)
	{
		this.photos=photos;
		
	}
	
	/**
	 * @return the optOutDataCollection
	 */
	public Boolean isOptOutDataCollection()
	{
		return optOutDataCollection;
	}

	/**
	 * @param optOutDataCollection the optOutDataCollection to set
	 */
	public void setOptOutDataCollection(Boolean optOutDataCollection)
	{
		this.optOutDataCollection = optOutDataCollection;
	}

	/**
	 * @return the trackingOff
	 */
	public Boolean isTrackingOff()
	{
		return trackingOff;
	}

	/**
	 * @param trackingOff the trackingOff to set
	 */
	public void setTrackingOff(Boolean trackingOff)
	{
		this.trackingOff = trackingOff;
	}

	@Override
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ email = ");
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
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountVerified ? 1231 : 1237);
		result = prime * result + (hasPersonality ? 1231 : 1237);
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
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		if (accountVerified != other.accountVerified)
			return false;
		if (hasPersonality != other.hasPersonality)
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
