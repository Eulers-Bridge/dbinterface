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
	private Float extroversion;
	private Float agreeableness;
	private Float conscientiousness;
	private Float emotionalStability;
	private Float openess;
	
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
	public PersonalityDetails(Long personalityId, Float extroversion,
			Float agreeableness, Float conscientiousness,
			Float emotionalStability, Float openess) 
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
	public Float getExtroversion() {
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
	public Float getAgreeableness() {
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
	public Float getConscientiousness() {
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
	public Float getEmotionalStability() {
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
	public Float getOpeness() {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (personalityId!=null)
			return personalityId.hashCode();
		else
		{
			result = prime * result + Float.floatToIntBits(agreeableness);
			result = prime * result + Float.floatToIntBits(conscientiousness);
			result = prime * result + Float.floatToIntBits(emotionalStability);
			result = prime * result + Float.floatToIntBits(extroversion);
			result = prime * result + Float.floatToIntBits(openess);
		}
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
		PersonalityDetails other = (PersonalityDetails) obj;
		if (personalityId!=null)
		{
			if (personalityId!=other.getPersonalityId())
				return false;
		}
		else
		{
			if (other.getPersonalityId()!=null)
				return false;
			else
			{
				if (Float.floatToIntBits(agreeableness) != Float
						.floatToIntBits(other.agreeableness))
					return false;
				if (Float.floatToIntBits(conscientiousness) != Float
						.floatToIntBits(other.conscientiousness))
					return false;
				if (Float.floatToIntBits(emotionalStability) != Float
						.floatToIntBits(other.emotionalStability))
					return false;
				if (Float.floatToIntBits(extroversion) != Float
						.floatToIntBits(other.extroversion))
					return false;
				if (Float.floatToIntBits(openess) != Float
						.floatToIntBits(other.openess))
					return false;
			}
		}
		return true;
	}
}
