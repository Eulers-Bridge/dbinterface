package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Personality 
{

	@GraphId Long nodeId;
	private float extroversion;
	private float agreeableness;
	private float conscientiousness;
	private float emotionalStability;
	private float openess;

	/**
	 * @param extroversion
	 * @param agreeableness
	 * @param conscientiousness
	 * @param emotionalStability
	 * @param openess
	 */
	public Personality(float extroversion, float agreeableness,
			float conscientiousness, float emotionalStability, float openess) 
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
	public float getExtroversion() {
		return extroversion;
	}

	/**
	 * @return the agreeableness
	 */
	public float getAgreeableness() {
		return agreeableness;
	}

	/**
	 * @return the conscientiousness
	 */
	public float getConscientiousness() {
		return conscientiousness;
	}

	/**
	 * @return the emotionalStability
	 */
	public float getEmotionalStability() {
		return emotionalStability;
	}

	/**
	 * @return the openess
	 */
	public float getOpeness() {
		return openess;
	}

	public Long getNodeId()
	{
		return nodeId;
	}
}
