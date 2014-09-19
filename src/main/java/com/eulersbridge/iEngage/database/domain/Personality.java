package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.eulersbridge.iEngage.core.events.users.PersonalityDetails;

@NodeEntity
public class Personality 
{

	@GraphId Long nodeId;
	private Float extroversion;
	private Float agreeableness;
	private Float conscientiousness;
	private Float emotionalStability;
	private Float openess;

    private static Logger LOG = LoggerFactory.getLogger(Personality.class);
    
	/**
	 * 
	 */
	public Personality() {
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

	public Long getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(Long nodeId) 
	{
		this.nodeId=nodeId;
		
	}
	
	public PersonalityDetails toPersonalityDetails()
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toPersonalityDetails()");
	    
	    PersonalityDetails details = new PersonalityDetails();
	    if (LOG.isTraceEnabled()) LOG.trace("personality "+this);

	    BeanUtils.copyProperties(this, details);
	    details.setPersonalityId(getNodeId());
	    if (LOG.isTraceEnabled()) LOG.trace("personalityDetails "+details);

	    return details;
	}
	
	public static Personality fromPersonalityDetails(PersonalityDetails dets)
	{
	    if (LOG.isTraceEnabled()) LOG.trace("fromUserDetails()");

	    if (LOG.isTraceEnabled()) LOG.trace("personalityDetails "+dets);
	    Personality personality = new Personality(dets.getExtroversion(),dets.getAgreeableness(), dets.getConscientiousness(), dets.getEmotionalStability(), dets.getOpeness());
	    personality.setNodeId(dets.getPersonalityId());
	    if (LOG.isTraceEnabled()) LOG.trace("personality "+personality);

	    return personality;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Personality [nodeId=" + nodeId + ", extroversion="
				+ extroversion + ", agreeableness=" + agreeableness
				+ ", conscientiousness=" + conscientiousness
				+ ", emotionalStability=" + emotionalStability + ", openess="
				+ openess + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		if (nodeId!=null)
			return nodeId.hashCode();
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
		Personality other = (Personality) obj;
		if (nodeId!=null)
		{
			if (!nodeId.equals(other.getNodeId()))
				return false;
		}
		else
		{
			if (other.getNodeId()!=null)
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
