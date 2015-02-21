/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author Greg Newitt
 *
 */
@NodeEntity
public class Location extends Likeable
{
    @GraphId
    private Long nodeId;

	/**
	 * 
	 */
	public Location()
	{
		super();
	}

	/**
	 * @param nodeId
	 */
	public Location(Long nodeId)
	{
		super();
		this.nodeId = nodeId;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

}
