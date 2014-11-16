/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * @author Greg Newitt
 *
 */
@NodeEntity
public class Owner
{
	@GraphId Long nodeId;

    private static Logger LOG = LoggerFactory.getLogger(Election.class);

    public Owner() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

    public Owner(Long nodeId) 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
		this.nodeId=nodeId;
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
