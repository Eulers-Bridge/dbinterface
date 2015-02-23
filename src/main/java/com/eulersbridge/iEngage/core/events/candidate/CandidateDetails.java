package com.eulersbridge.iEngage.core.events.candidate;
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Yikai Gong
 */

public class CandidateDetails extends Details {
    private String information;
    private String policyStatement;
    private Set<String> pictures;
    private Long userId;
    private Long positionId;
    private TicketDetails ticketDetails;

    private static Logger LOG = LoggerFactory.getLogger(CandidateDetails.class);

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", information = ");
        buff.append(getInformation());
        buff.append(", policyStatement = ");
        buff.append(getPolicyStatement());
        buff.append(", pictures = ");
        buff.append(getPictures());
        buff.append(", userId = ");
        buff.append(getUserId());
        buff.append(", positionId = ");
        buff.append(getPositionId());
        buff.append(", ticket = ");
        if (null==getTicketDetails())
        	buff.append("null");
        else buff.append(getTicketDetails().toString());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        if (getNodeId()!=null)
        {
            result = prime * result	+ getNodeId().hashCode();
        }
        else
        {
            result = prime * result	+ ((information == null) ? 0 : information.hashCode());
            result = prime * result + ((policyStatement == null) ? 0 : policyStatement.hashCode());
            result = prime * result + ((pictures == null) ? 0 : pictures.hashCode());
            result = prime * result + ((userId == null) ? 0 : userId.hashCode());
            result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CandidateDetails other = (CandidateDetails) obj;
        if (nodeId != null)
        {
            if (nodeId.equals(other.nodeId))
                return true;
            else return false;
        }
        else
        {
            if (other.nodeId != null)
                return false;
            if (information == null) {
                if (other.information != null)
                    return false;
            } else if (!information.equals(other.information))
                return false;
            if (policyStatement == null) {
                if (other.policyStatement != null)
                    return false;
            } else if (!policyStatement.equals(other.policyStatement))
                return false;
            if (userId == null) {
                if (other.userId != null)
                    return false;
            } else if (!userId.equals(other.userId))
                return false;
            if (positionId == null) {
                if (other.positionId != null)
                    return false;
            } else if (!positionId.equals(other.positionId))
                return false;
        }
        return true;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPolicyStatement() {
        return policyStatement;
    }

    public void setPolicyStatement(String policyStatement) {
        this.policyStatement = policyStatement;
    }

    public Set<String> getPictures() {
        return pictures;
    }

    public void setPictures(Set<String> pictures) {
        this.pictures = pictures;
    }

	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the positionId
	 */
	public Long getPositionId()
	{
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(Long positionId)
	{
		this.positionId = positionId;
	}

    public TicketDetails getTicketDetails() {
        return ticketDetails;
    }

    public void setTicketDetails(TicketDetails ticketDetails) {
        this.ticketDetails = ticketDetails;
    }
}
