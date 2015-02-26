package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.badge.BadgeDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Badge {
    @GraphId
    private Long nodeId;
    private String name;
    private boolean awarded;
    private Long timestamp;
    private Long xpValue;

    private static Logger LOG = LoggerFactory.getLogger(Badge.class);

    public Badge() {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public static Badge fromBadgeDetails(BadgeDetails badgeDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromBadgeDetails()");
        Badge badge = new Badge();
        if (LOG.isTraceEnabled()) LOG.trace("badgeDetails "+badgeDetails);
        badge.setNodeId(badgeDetails.getNodeId());
        badge.setName(badgeDetails.getName());
        badge.setAwarded(badgeDetails.isAwarded());
        badge.setTimestamp(badgeDetails.getTimestamp());
        badge.setXpValue(badgeDetails.getXpValue());

        if (LOG.isTraceEnabled()) LOG.trace("badge "+badge);
        return badge;
    }

    public BadgeDetails toBadgeDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toBadgeDetails()");
        BadgeDetails badgeDetails = new BadgeDetails();
        if (LOG.isTraceEnabled()) LOG.trace("badge "+this);
        badgeDetails.setNodeId(getNodeId());
        badgeDetails.setName(getName());
        badgeDetails.setAwarded(isAwarded());
        badgeDetails.setTimestamp(getTimestamp());
        badgeDetails.setXpValue(getXpValue());

        if (LOG.isTraceEnabled()) LOG.trace("badgeDetails; "+ badgeDetails);
        return badgeDetails;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", awarded = ");
        buff.append(isAwarded());
        buff.append(", timestamp = ");
        buff.append(getTimestamp());
        buff.append(", xpValue = ");
        buff.append(getXpValue());;
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long badgeId) {
        this.nodeId = badgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAwarded() {
        return awarded;
    }

    public void setAwarded(boolean awarded) {
        this.awarded = awarded;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getXpValue() {
        return xpValue;
    }

    public void setXpValue(Long xpValue) {
        this.xpValue = xpValue;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (this.nodeId != null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((xpValue == null) ? 0 : xpValue.hashCode());
		}
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Badge other = (Badge) obj;
		if (nodeId != null)
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId != null) return false;
			
			if (name == null)
			{
				if (other.name != null) return false;
			}
			else if (!name.equals(other.name)) return false;
			
			if (xpValue == null)
			{
				if (other.xpValue != null) return false;
			}
			else if (!xpValue.equals(other.xpValue)) return false;
		}
		return true;
	}

}
