/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.users.PersonalityDetails;
import com.eulersbridge.iEngage.rest.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Greg Newitt
 *
 */
public class Personality extends ResourceSupport 
{
	Long personalityId;
	private Float extroversion;
	private Float agreeableness;
	private Float conscientiousness;
	private Float emotionalStability;
	private Float openess;

    private static Logger LOG = LoggerFactory.getLogger(Personality.class);

	/**
	 * 
	 */
	public Personality() 
	{
		super();
	}

	/**
	 * @param extroversion
	 * @param agreeableness
	 * @param conscientiousness
	 * @param emotionalStability
	 * @param openess
	 */
	public Personality(Float extroversion, Float agreeableness,
			Float conscientiousness, Float emotionalStability, Float openess) 
	{
		super();
		this.extroversion = extroversion;
		this.agreeableness = agreeableness;
		this.conscientiousness = conscientiousness;
		this.emotionalStability = emotionalStability;
		this.openess = openess;
	}
	
	/**
	 * @return the extroversion
	 */
	public Float getExtroversion() {
		return extroversion;
	}

	/**
	 * @return the agreeableness
	 */
	public Float getAgreeableness() {
		return agreeableness;
	}

	/**
	 * @return the conscientiousness
	 */
	public Float getConscientiousness() {
		return conscientiousness;
	}

	/**
	 * @return the emotionalStability
	 */
	public Float getEmotionalStability() {
		return emotionalStability;
	}

	/**
	 * @return the openess
	 */
	public Float getOpeness() {
		return openess;
	}

	public Long getPersonalityId()
	{
		return personalityId;
	}

	public void setPersonalityId(Long personalityId) 
	{
		this.personalityId=personalityId;
		
	}
	
	public PersonalityDetails toPersonalityDetails()
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toPersonalityDetails()");
	    
	    PersonalityDetails details = new PersonalityDetails();
	    if (LOG.isTraceEnabled()) LOG.trace("personality "+this);

	    BeanUtils.copyProperties(this, details);
	    details.setPersonalityId(getPersonalityId());
	    if (LOG.isTraceEnabled()) LOG.trace("personalityDetails "+details);

	    return details;
	}
	
	public static Personality fromPersonalityDetails(PersonalityDetails dets)
	{
	    if (LOG.isTraceEnabled()) LOG.trace("fromUserDetails()");

	    if (LOG.isTraceEnabled()) LOG.trace("personalityDetails "+dets);
	    
	    Personality personality = new Personality(dets.getExtroversion(),dets.getAgreeableness(), dets.getConscientiousness(), dets.getEmotionalStability(), dets.getOpeness());
	    personality.setPersonalityId(dets.getPersonalityId());
	    if (LOG.isTraceEnabled()) LOG.trace("personality "+personality);

	    String simpleName= UserDomain.class.getSimpleName();
	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
	    personality.add(linkTo(UserController.class).slash(name).slash(personality.personalityId).withSelfRel());
	    // {!end selfRel}
	    return personality;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Personality [nodeId=" + personalityId + ", extroversion="
				+ extroversion + ", agreeableness=" + agreeableness
				+ ", conscientiousness=" + conscientiousness
				+ ", emotionalStability=" + emotionalStability + ", openess="
				+ openess + "]";
	}
}
