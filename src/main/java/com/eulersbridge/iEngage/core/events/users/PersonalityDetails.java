/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

/**
 * @author Greg Newitt
 *
 */
public class PersonalityDetails 
{
	private Long personalityId;
	private float extroversion;
	private float agreeableness;
	private float conscientiousness;
	private float emotionalStability;
	private float openess;
	
	/**
	 * 
	 */
	public PersonalityDetails() 
	{
		super();
	}

	/**
	 * @param personalityId
	 * @param extroversion
	 * @param agreeableness
	 * @param conscientiousness
	 * @param emotionalStability
	 * @param openess
	 */
	public PersonalityDetails(Long personalityId, float extroversion,
			float agreeableness, float conscientiousness,
			float emotionalStability, float openess) 
	{
		super();
		this.personalityId = personalityId;
		this.extroversion = extroversion;
		this.agreeableness = agreeableness;
		this.conscientiousness = conscientiousness;
		this.emotionalStability = emotionalStability;
		this.openess = openess;
	}
	
	/**
	 * @return the personalityId
	 */
	public Long getPersonalityId() {
		return personalityId;
	}

	/**
	 * @param personalityId the personalityId to set
	 */
	public void setPersonalityId(Long personalityId) {
		this.personalityId = personalityId;
	}

	/**
	 * @return the extroversion
	 */
	public float getExtroversion() {
		return extroversion;
	}
	/**
	 * @param extroversion the extroversion to set
	 */
	public void setExtroversion(float extroversion) {
		this.extroversion = extroversion;
	}
	/**
	 * @return the agreeableness
	 */
	public float getAgreeableness() {
		return agreeableness;
	}
	/**
	 * @param agreeableness the agreeableness to set
	 */
	public void setAgreeableness(float agreeableness) {
		this.agreeableness = agreeableness;
	}
	/**
	 * @return the conscientiousness
	 */
	public float getConscientiousness() {
		return conscientiousness;
	}
	/**
	 * @param conscientiousness the conscientiousness to set
	 */
	public void setConscientiousness(float conscientiousness) {
		this.conscientiousness = conscientiousness;
	}
	/**
	 * @return the emotionalStability
	 */
	public float getEmotionalStability() {
		return emotionalStability;
	}
	/**
	 * @param emotionalStability the emotionalStability to set
	 */
	public void setEmotionalStability(float emotionalStability) {
		this.emotionalStability = emotionalStability;
	}
	/**
	 * @return the openess
	 */
	public float getOpeness() {
		return openess;
	}
	/**
	 * @param openess the openess to set
	 */
	public void setOpeness(float openess) {
		this.openess = openess;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PersonalityDetails [personalityId=" + personalityId
				+ ", extroversion=" + extroversion + ", agreeableness="
				+ agreeableness + ", conscientiousness=" + conscientiousness
				+ ", emotionalStability=" + emotionalStability + ", openess="
				+ openess + "]";
	}
}
