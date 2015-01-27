package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Candidate
{
    @GraphId
    private Long nodeId;
    private String information;
    private String policyStatement;
    @RelatedTo(type = DatabaseDomainConstants.IS_CANDIDATE_LABEL, direction= Direction.BOTH)
    private User user;

    @RelatedTo(type = DatabaseDomainConstants.HAS_CANDIDATE_LABE, direction= Direction.BOTH)
    private Position position;

//    @RelatedTo(type = DatabaseDomainConstants.WAS_ASKED_LABEL, direction= Direction.BOTH)
//    private ForumQuestion forumQuestion;

//    @RelatedTo(type = DatabaseDomainConstants.IS_ON_TICKET_LABEL, direction= Direction.BOTH)
    

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
			String policyStatement, User user, Position position)
	{
		super();
		this.nodeId = candidateId;
		this.information = information;
		this.policyStatement = policyStatement;
		this.user = user;
		this.position = position;
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

        if (LOG.isTraceEnabled()) LOG.trace("candidate "+candidate);
        return candidate;
    }

    public CandidateDetails toCandidateDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toCandidateDetails()");
        CandidateDetails candidateDetails = new CandidateDetails();
        if (LOG.isTraceEnabled()) LOG.trace("candidate "+this);
        candidateDetails.setNodeId(getNodeId());
        candidateDetails.setUserId(getUser().getNodeId());
        candidateDetails.setInformation(getInformation());
        candidateDetails.setPolicyStatement(getPolicyStatement());
        candidateDetails.setPositionId(getPosition().getNodeId());

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

}
