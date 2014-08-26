package com.eulersbridge.iEngage.database.domain;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import com.eulersbridge.iEngage.core.events.users.UserDetails;

@NodeEntity
public class User 
{
	@GraphId Long nodeId;
	@NotNull @NotBlank @Email@Indexed(unique=true) private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String nationality;
	private String yearOfBirth;
	private String personality;
	private String password;
	private boolean accountVerified=false;
	@RelatedTo(type = "USER_OF", direction=Direction.OUTGOING)
	@Fetch private Institution institution;
	@RelatedTo(type = "verifiedBy", direction=Direction.BOTH)
	private Iterable<VerificationToken> verificationToken;
	@RelatedToVia(direction=Direction.BOTH, type="LIKES")
	private Set<Like> likes;
	
    private static Logger LOG = LoggerFactory.getLogger(User.class);
    
	public User()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public User(String email,String firstName,String lastName,String gender, String nationality, String yearOfBirth, String personality, String password)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+email+','+firstName+','+lastName+','+gender+','+
														  nationality+','+yearOfBirth+','+personality+')');
		this.email=email;
		this.firstName=firstName;
		this.lastName=lastName;
		this.gender=gender;
		this.nationality=nationality;
		this.yearOfBirth=yearOfBirth;
		this.personality=personality;
		this.password=password;
	}
	
	public String getEmail()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getEmail() = "+email);
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getFirstName() = "+firstName);
		return firstName;
	}
	
	public String getLastName()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getLastName() = "+lastName);
		return lastName;
	}
	
	public String getGender()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getGender() = "+gender);
		return gender;
	}
	
	public String getNationality()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getNationality() = "+nationality);
		return nationality;
	}
	
	public String getYearOfBirth()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getYearOfBirth() = "+yearOfBirth);
		return yearOfBirth;
	}
	
	public String getPersonality()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getPersonality() = "+personality);
		return personality;
	}
	
	public boolean comparePassword(String password)
	{
		return password.equals(this.password);
	}
	
	public Long getNodeId()
	{
		return nodeId;
	}
	
	public void setNodeId(Long nodeId)
	{
		this.nodeId=nodeId;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", email = ");
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
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isTraceEnabled()) LOG.trace("toString() = "+retValue);
		return retValue;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	
//	public Password getPassword()
	public String getPassword()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getPassword() = "+password);
		return password;
	}
	
//	public void setPassword(Password password) 
	public void setPassword(String password) 
	{
		this.password = password;
	}

	/**
	 * @return the verified
	 */
	public boolean isAccountVerified() {
		return accountVerified;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setAccountVerified(boolean accountVerified) {
		this.accountVerified = accountVerified;
	}
	
	/**
	 * @return the verificationToken
	 */
	public Iterable<VerificationToken> getVerificationToken() {
		return verificationToken;
	}

	/**
	 * @param verificationToken the verificationToken to set
	 */
	public void setVerificationToken(Iterable<VerificationToken> verificationToken) {
		this.verificationToken = verificationToken;
	}

	/**
	 * @return the likes
	 */
	public Set<Like> getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

	/**
	 * @param adds the like to set
	 */
	public boolean addLike(Like like) 
	{
		boolean result=this.likes.add(like);
		return result;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @param yearOfBirth the yearOfBirth to set
	 */
	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	/**
	 * @param personality the personality to set
	 */
	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public UserDetails toUserDetails() 
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toUserDetails()");
	    
	    UserDetails details = new UserDetails();
	    if (LOG.isTraceEnabled()) LOG.trace("user "+this);

	    BeanUtils.copyProperties(this, details);
	    details.setInstitutionId(this.getInstitution().getNodeId());
	    if (LOG.isTraceEnabled()) LOG.trace("userDetails "+details);

	    return details;
	}

	  public static User fromUserDetails(UserDetails userDetails) 
	  {
		    if (LOG.isTraceEnabled()) LOG.trace("fromUserDetails()");

		    User user = new User();
		    if (LOG.isTraceEnabled()) LOG.trace("userDetails "+userDetails);
		    user.email=userDetails.getEmail();
		    user.firstName=userDetails.getFirstName();
		    user.gender=userDetails.getGender();
		    userDetails.getInstitutionId();
		    user.lastName=userDetails.getLastName();
		    user.nationality=userDetails.getNationality();
		    user.password=userDetails.getPassword();
		    user.personality=userDetails.getPersonality();
		    user.yearOfBirth=userDetails.getYearOfBirth();
		    user.accountVerified=userDetails.isAccountVerified();
		    Institution inst=new Institution();
		    inst.setNodeId(userDetails.getInstitutionId());
		    user.institution=inst;
//		    BeanUtils.copyProperties( userDetails,user);
		    if (LOG.isTraceEnabled()) LOG.trace("user "+user);

		    return user;
		  }
	  
	@Override
	public boolean equals(Object other)
	{
		if (null == other) return false;
		if (other == this) return true;
		if (!(other instanceof User)) return false;
		User user2=(User) other;
		if ((nodeId!=null)&&(nodeId.equals(user2.nodeId))) return true;
		return false;
	}

}
