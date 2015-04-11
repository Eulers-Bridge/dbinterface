/**
 * @author Greg Newitt
 *
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.rest.controller.ControllerConstants;
import com.eulersbridge.iEngage.rest.controller.UserController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;

public class UserProfile extends ResourceSupport
{
	private String givenName;
	private String familyName;
	private String gender;
	private String nationality;
	private String contactNumber;
	private Long institutionId;
	private String email;
	private PhotoDetails profilePhoto;

    private Long numOfCompTasks;
    private Long numOfCompBadges;

	private static Logger LOG = LoggerFactory.getLogger(User.class);

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getGivenName()
	{
		return givenName;
	}

	public void setGivenName(String givenName)
	{
		this.givenName = givenName;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber()
	{
		return contactNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(String contactNumber)
	{
		this.contactNumber = contactNumber;
	}

	public Long getInstitutionId()
	{
		return institutionId;
	}

	public void setInstitutionId(Long institutionId)
	{
		this.institutionId = institutionId;
	}

	/**
	 * @return the profilePhoto
	 */
	public PhotoDetails getProfilePhoto()
	{
		return profilePhoto;
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
	 * @param profilePhoto the profilePhoto to set
	 */
	public void setProfilePhoto(PhotoDetails profilePhoto)
	{
		this.profilePhoto = profilePhoto;
	}

	public UserDetails toUserDetails()
	{
		UserDetails details = new UserDetails(email);

		details.setGivenName(getGivenName());
		details.setFamilyName(getFamilyName());
		details.setGender(getGender());
		details.setNationality(getNationality());
		details.setContactNumber(getContactNumber());
		details.setInstitutionId(getInstitutionId());

		return details;
	}

	// {!begin fromUserDetails}
	public static UserProfile fromUserDetails(UserDetails readUser)
	{
		UserProfile user = new UserProfile();

		user.email = readUser.getEmail();
		user.givenName = readUser.getGivenName();
		user.familyName = readUser.getFamilyName();
		user.gender = readUser.getGender();
		user.nationality = readUser.getNationality();
		user.contactNumber = readUser.getContactNumber();
		user.institutionId = readUser.getInstitutionId();
        user.numOfCompTasks = readUser.getNumOfCompTasks();
        user.numOfCompBadges = readUser.getNumOfCompBadges();
		Iterable<PhotoDetails> photos=readUser.getPhotos();
		user.profilePhoto=null;
		if (photos!=null)
		{
			Iterator<PhotoDetails> iterator=photos.iterator();
			if (iterator.hasNext())
			{
				user.profilePhoto=iterator.next();
			}
		}

		// TODOCUMENT. Adding the library, the above extends ResourceSupport and
		// this section is all that is actually needed in our model to add
		// hateoas support.

		// Much of the rest of the framework is helping deal with the blending
		// of domains that happens in many spring apps
		// We have explicitly avoided that.
		// {!begin selfRel}
		String details=null;
		if (user.email!=null)
			details=user.email;
		else details=user.contactNumber;
		user.add(new Link(linkTo(UserController.class).slash(ControllerConstants.CONTACT_LABEL).slash(details).toUriComponentsBuilder().build().toUriString() + "/","self"));
		// {!end selfRel}
		return user;
	}

	public String toString()
	{
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
		buff.append(", contactNumber = ");
		buff.append(getContactNumber());
		buff.append(", institutionId = ");
		buff.append(getInstitutionId());
		buff.append(" ]");
		retValue = buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
		return retValue;
	}

	public static Iterator<LikeInfo> toLikesIterator(Iterator<UserDetails> iter)
	{
		if (null == iter) return null;
		ArrayList<LikeInfo> likes = new ArrayList<LikeInfo>();
		while (iter.hasNext())
		{
			UserDetails userDetails = iter.next();
			LikeInfo likeInfo = LikeInfo.fromUserDetails(userDetails);
			likes.add(likeInfo);
		}
		return likes.iterator();
	}
	
	public static Iterator<UserProfile> toUserProfilesIterator(
			Iterator<UserDetails> iter)
	{
		if (null==iter) return null;
		ArrayList <UserProfile> userProfiles=new ArrayList<UserProfile>();
		while(iter.hasNext())
		{
			UserDetails dets=iter.next();
			UserProfile thisUserProfile=UserProfile.fromUserDetails(dets);
			Link self = thisUserProfile.getLink("self");
			thisUserProfile.removeLinks();
			thisUserProfile.add(self);
			userProfiles.add(thisUserProfile);		
		}
		return userProfiles.iterator();
	}

}
