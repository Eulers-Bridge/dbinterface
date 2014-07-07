package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.hateoas.Link;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.rest.controller.UserController;
import org.springframework.hateoas.ResourceSupport;


public class User extends ResourceSupport
{
	private String firstName;
	private String lastName;
	private String gender;
	private String nationality;
	private String yearOfBirth;
	private String personality;
	private String password;
	private boolean accountVerified=false;
	private	Institution institution; 
	private String email;

	public String getEmail() 
	{
	    return email;
	}

	  public void setEmail(String email) {
	    this.email = email;
	  }

	  public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAccountVerified() {
		return accountVerified;
	}

	public void setAccountVerified(boolean accountVerified) {
		this.accountVerified = accountVerified;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public UserDetails toReadUser() 
	  {
		  UserDetails details = new UserDetails(email);

	    details.setFirstName(getFirstName());
	    details.setLastName(getLastName());
	    details.setGender(getGender());
	    details.setNationality(getNationality());
	    details.setYearOfBirth(getYearOfBirth());
	    details.setPersonality(getPersonality());
	    details.setPassword(getPassword());
	    details.setAccountVerified(isAccountVerified());
	    details.setInstitution(getInstitution());

	    return details;
	  }

	  // {!begin fromOrderDetails}
	  public static User fromRequestUser(UserDetails readUser) {
	    User user = new User();

	    user.email = readUser.getEmail();
	    user.firstName = readUser.getFirstName();
	    user.lastName = readUser.getLastName();
	    user.gender = readUser.getGender();
	    user.nationality = readUser.getNationality();
	    user.yearOfBirth = readUser.getYearOfBirth();
	    user.personality = readUser.getPersonality();
	    user.password = readUser.getPassword();
	    user.accountVerified = readUser.isAccountVerified();
	    user.institution = readUser.getInstitution();
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
	    user.add(linkTo(UserController.class).slash(user.email).withSelfRel());
	    // {!end selfRel}
	    // {!begin status}
	    user.add(linkTo(UserController.class).slash(user.email).slash("status").withRel("User Status"));
	    // {!end status}
	    user.add(linkTo(UserController.class).slash(user.email).slash("details").withRel("User Details"));

	    return user;
	  }

}
