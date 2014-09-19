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
}
