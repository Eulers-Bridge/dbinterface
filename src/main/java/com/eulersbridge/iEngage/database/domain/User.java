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
	private String givenName;
	private String familyName;
	private String gender;
	private String nationality;
	private String yearOfBirth;
	@RelatedTo(type = DatabaseDomainConstants.HAS_PERSONALITY_LABEL, direction=Direction.OUTGOING)
	private Personality personality;
	private String password;
	private String roles;
	private boolean accountVerified=false;
	@RelatedTo(type = DatabaseDomainConstants.USERS_LABEL, direction=Direction.OUTGOING)
	@Fetch private Institution institution;
	@RelatedTo(type = DatabaseDomainConstants.VERIFIED_BY_LABEL, direction=Direction.BOTH)
	private Iterable<VerificationToken> verificationToken;
	@RelatedToVia(direction=Direction.BOTH, type=DatabaseDomainConstants.LIKES_LABEL)
	private Set<Like> likes;
	
    private static Logger LOG = LoggerFactory.getLogger(User.class);
    
	public User()
	{
		if (LOG.isDebugEnabled()) LOG.debug("Constructor");
	}
	
	public User(String email,String givenName,String familyName,String gender, String nationality, String yearOfBirth, String password)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+email+','+givenName+','+familyName+','+gender+','+
														  nationality+','+yearOfBirth+')');
		this.email=email;
		this.givenName=givenName;
		this.familyName=familyName;
		this.gender=gender;
		this.nationality=nationality;
		this.yearOfBirth=yearOfBirth;
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

	public String getGivenName()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getGivenName() = "+givenName);
		return givenName;
	}
	
	public String getFamilyName()
	{
		if (LOG.isTraceEnabled()) LOG.trace("getFamilyName() = "+familyName);
		return familyName;
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
	 * @return the roles
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
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
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
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
		    user.givenName=userDetails.getGivenName();
		    user.gender=userDetails.getGender();
		    userDetails.getInstitutionId();
		    user.familyName=userDetails.getFamilyName();
		    user.nationality=userDetails.getNationality();
		    user.password=userDetails.getPassword();
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
