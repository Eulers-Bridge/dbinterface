package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.rest.controller.UserController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;


public class User extends ResourceSupport
{
	private String givenName;
	private String familyName;
	private String gender;
	private String nationality;
	private String yearOfBirth;
	private String password;
	private String contactNumber;
	private boolean accountVerified=false;
	private boolean hasPersonality=false;
	private	Long institutionId; 
	private String email;
	private Iterable<PhotoDetails> photos;
	private boolean trackingOff=false;
	private boolean optOutDataCollection=false;

    private static Logger LOG = LoggerFactory.getLogger(User.class);
	public String getEmail() 
	{
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

	public boolean isAccountVerified() {
		return accountVerified;
	}

	public void setAccountVerified(boolean accountVerified) {
		this.accountVerified = accountVerified;
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
	public boolean getHasPersonality()
	{
		return hasPersonality;
	}

	/**
	 * @param hasPersonality the hasPersonality to set
	 */
	public void setHasPersonality(boolean hasPersonality)
	{
		this.hasPersonality = hasPersonality;
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
		this.photos = photos;
	}

	/**
	 * @return the trackingOn
	 */
	public boolean isTrackingOff()
	{
		return trackingOff;
	}

	/**
	 * @param trackingOn the trackingOn to set
	 */
	public void setTrackingOff(boolean trackingOff)
	{
		this.trackingOff = trackingOff;
	}

	/**
	 * @return the optOutDataCollection
	 */
	public boolean isOptOutDataCollection()
	{
		return optOutDataCollection;
	}

	/**
	 * @param optOutDataCollection the optOutDataCollection to set
	 */
	public void setOptOutDataCollection(boolean optOutDataCollection)
	{
		this.optOutDataCollection = optOutDataCollection;
	}

	public UserDetails toUserDetails() 
	  {
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

	    return details;
	  }

	  // {!begin fromUserDetails}
	  public static User fromUserDetails(UserDetails readUser) {
	    User user = new User();

	    user.setEmail(readUser.getEmail());
	    user.setGivenName(readUser.getGivenName());
	    user.setFamilyName(readUser.getFamilyName());
	    user.setGender(readUser.getGender());
	    user.setNationality(readUser.getNationality());
	    user.setYearOfBirth(readUser.getYearOfBirth());
	    user.setPassword(readUser.getPassword());
	    user.setContactNumber(readUser.getContactNumber());
	    user.setAccountVerified(readUser.isAccountVerified());
	    user.setInstitutionId(readUser.getInstitutionId());
	    user.setHasPersonality(readUser.hasPersonality());
	    user.setTrackingOff(readUser.isTrackingOff());
	    user.setOptOutDataCollection(readUser.isOptOutDataCollection());
	    user.setPhotos(readUser.getPhotos());
	    
	    String simpleName=User.class.getSimpleName();
	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
		user.add(new Link(linkTo(UserController.class).slash(name).slash(user.email).toUriComponentsBuilder().build().toUriString() + "/","self"));
	    // {!end selfRel}
	    return user;
	  }

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
			buff.append(", photos = ");
			buff.append(getPhotos());
			buff.append(", contactNumber = ");
			buff.append(getContactNumber());
			buff.append(", institutionId = ");
			buff.append(getInstitutionId());
			buff.append(", accountVerified = ");
			buff.append(isAccountVerified());
			buff.append(" ]");
			retValue=buff.toString();
			if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
			return retValue;
		}

    public static Iterator<LikeInfo> toLikesIterator(Iterator<UserDetails> iter)
    {
        if (null==iter) return null;
        ArrayList<LikeInfo> likes = new ArrayList<LikeInfo>();
        while(iter.hasNext())
        {
            UserDetails userDetails = iter.next();
            LikeInfo likeInfo = LikeInfo.fromUserDetails(userDetails);
            likes.add(likeInfo);
        }
        return likes.iterator();
    }
}
