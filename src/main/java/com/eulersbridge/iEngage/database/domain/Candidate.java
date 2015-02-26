package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Candidate extends Likeable
{
    @GraphId
    private Long nodeId;
    private String information;
    private String policyStatement;
    @Fetch
    @RelatedTo(type = DatabaseDomainConstants.IS_CANDIDATE_LABEL, direction= Direction.BOTH)
    private User user;

    @RelatedTo(type = DatabaseDomainConstants.HAS_CANDIDATE_LABEL, direction= Direction.BOTH)
    private Position position;

//    @RelatedTo(type = DatabaseDomainConstants.WAS_ASKED_LABEL, direction= Direction.BOTH)
//    private ForumQuestion forumQuestion;

    @Fetch
    @RelatedTo(type = DatabaseDomainConstants.IS_ON_TICKET_LABEL, direction= Direction.BOTH)
    private Ticket ticket;

    private static Logger LOG = LoggerFactory.getLogger(Candidate.class);

    public Candidate()
    {
		super();
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    /**
	 * @param nodeId
	 * @param information
	 * @param policyStatement
	 * @param user
	 * @param position
	 */
	public Candidate(Long candidateId, String information,
			String policyStatement, User user, Position position, Ticket ticket)
	{
		super();
		this.nodeId = candidateId;
		this.information = information;
		this.policyStatement = policyStatement;
		this.user = user;
		this.position = position;
        this.ticket = ticket;
	}

	public static Candidate fromCandidateDetails(CandidateDetails candidateDetails)
    {
        if (LOG.isTraceEnabled()) LOG.trace("fromCandidateDetails()");
        Candidate candidate = new Candidate();
        if (LOG.isTraceEnabled()) LOG.trace("candidateDetails "+candidateDetails);
        candidate.setNodeId(candidateDetails.getNodeId());
        candidate.setInformation(candidateDetails.getInformation());
        candidate.setPolicyStatement(candidateDetails.getPolicyStatement());
        User user=new User();
        user.setNodeId(candidateDetails.getUserId());
        candidate.setUser(user);
        Position position=new Position();
        position.setNodeId(candidateDetails.getPositionId());
        candidate.setPosition(position);

//        candidate.setTicket(Ticket.fromTicketDetails(candidateDetails.getTicketDetails()));

        if (LOG.isTraceEnabled()) LOG.trace("candidate "+candidate);
        return candidate;
    }

    public CandidateDetails toCandidateDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toCandidateDetails()");
        CandidateDetails candidateDetails = new CandidateDetails();
        if (LOG.isTraceEnabled()) LOG.trace("candidate "+this);
        candidateDetails.setNodeId(getNodeId());
        if (null==getUser())
        	candidateDetails.setUserId(null);
        else candidateDetails.setUserId(getUser().getNodeId());
        candidateDetails.setInformation(getInformation());
        candidateDetails.setPolicyStatement(getPolicyStatement());
        if (null==getPosition())
        	candidateDetails.setPositionId(null);
        else candidateDetails.setPositionId(getPosition().getNodeId());
        
        if (null==getTicket())
        	candidateDetails.setTicketDetails(null);
        else candidateDetails.setTicketDetails(getTicket().toTicketDetails());

        if (LOG.isTraceEnabled()) LOG.trace("candidateDetails; "+ candidateDetails);
        return candidateDetails;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", information = ");
        buff.append(getInformation());
        buff.append(", policyStatement = ");
        buff.append(getPolicyStatement());
        buff.append(", ticket = ");
        buff.append(getTicket());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    /**
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position)
	{
		this.position = position;
	}

	public String getPolicyStatement() {
        return policyStatement;
    }

    public void setPolicyStatement(String policyStatement) {
        this.policyStatement = policyStatement;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
			result = prime * result + ((policyStatement == null) ? 0 : policyStatement.hashCode());
			result = prime * result
					+ ((information == null) ? 0 : information.hashCode());
			result = prime * result + ((user == null) ? 0 : user.hashCode());
			result = prime * result + ((position == null) ? 0 : position.hashCode());
			result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
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
		Candidate other = (Candidate) obj;
		if (nodeId != null)
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId != null) return false;
			
			if (policyStatement == null)
			{
				if (other.policyStatement != null) return false;
			}
			else if (!policyStatement.equals(other.policyStatement)) return false;
			
			if (information == null)
			{
				if (other.information != null) return false;
			}
			else if (!information.equals(other.information)) return false;
			
			if (user == null)
			{
				if (other.user != null) return false;
			}
			else if (!user.equals(other.user)) return false;

			if (position == null)
			{
				if (other.position != null) return false;
			}
			else if (!position.equals(other.position)) return false;
			
			if (ticket == null)
			{
				if (other.ticket != null) return false;
			}
			else if (!ticket.equals(other.ticket)) return false;
		}
		return true;
	}
}
