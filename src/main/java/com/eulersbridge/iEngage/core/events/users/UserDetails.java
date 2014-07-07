/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.database.domain.Institution;

/**
 * @author Greg Newitt
 *
 */
public class UserDetails 
{

	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String nationality;
	private String yearOfBirth;
	private String personality;
	private String password;
	private boolean accountVerified=false;
	private	Institution institution;
	
    private static Logger LOG = LoggerFactory.getLogger(UserDetails.class);
    
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

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public UserDetails() 
	{
	}

	public UserDetails(String email) 
	{
		this.email=email;
	}

	public void setEmail(String email) 
	{
		this.email=email;
		
	}

	public String getEmail() {
		return email;
	}

	public Map<String, Integer> getUserItems() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ email = ");
		String retValue;
		buff.append(getEmail());
		buff.append(", firstName = ");
		buff.append(getFirstName());
		buff.append(", lastName = ");
		buff.append(getLastName());
		buff.append(", gender = ");
		buff.append(getGender());
		buff.append(", nationality = ");
		buff.append(getNationality());
		buff.append(", yearOfBirth = ");
		buff.append(getYearOfBirth());
		buff.append(", personality = ");
		buff.append(getPersonality());
		buff.append(", password = ");
		buff.append(getPassword());
		buff.append(", accountVerified = ");
		buff.append(isAccountVerified());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
}
