package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Election 
{
	@GraphId Long nodeId;
    private String title;
	private Long start;
	private Long end;
	private Long votingStart;
	private Long votingEnd;
	@RelatedTo(type = DatabaseDomainConstants.HAS_ELECTION_LABEL, direction=Direction.OUTGOING)
	private Institution institution;

    private static Logger LOG = LoggerFactory.getLogger(Election.class);

    public Election() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public Election(Long year, String title, Long start, Long end, Long votingStart,
                    Long votingEnd)
	{
		if (LOG.isDebugEnabled()) LOG.debug("Constructor("+year+','+start+','+end+','+votingStart+','+votingEnd+')');
        this.title = title;
		this.start = start;
		this.end = end;
		this.votingStart = votingStart;
		this.votingEnd = votingEnd;
	}

	public Long getStart()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getStart() = "+start);
		return start;
	}
	
	public Long getEnd()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getEnd() = "+end);
		return end;
	}
	
	public Long getVotingStart()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getVotingStart() = "+votingStart);
		return votingStart;
	}

    public String getTitle(){
        if (LOG.isDebugEnabled()) LOG.debug("getTitle() = "+title);
        return this.title;
    }
	
	public Long getVotingEnd()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getVotingEnd() = "+votingEnd);
		return votingEnd;
	}
	
	public Long getNodeId()
	{
		return nodeId;
	}

    public void setNodeId(Long nodeId){
        this.nodeId = nodeId;
    }

    public void setStart(Long start){
        this.start = start;
    }

    public void setEnd(Long end){
        this.end = end;
    }

    public void setVotingStart(Long votingStart){
        this.votingStart = votingStart;
    }

    public void setVotingEnd(Long votingEnd){
        this.votingEnd = votingEnd;
    }

    public void setTitle(String title){
        this.title = title;
    }
	
	/**
	 * @return the institution
	 */
	public Institution getInstitution() {
		return institution;
	}

	/**
	 * @param institution the institution to set
	 */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", title = ");
		buff.append(getTitle());
		buff.append(", start = ");
		buff.append(getStart());
		buff.append(", end = ");
		buff.append(getEnd());
		buff.append(" , voting start = ");
		buff.append(getVotingStart());
		buff.append(", voting end = ");
		buff.append(getVotingEnd());
		buff.append(", institution = ");
		buff.append(getInstitution());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	

    public ElectionDetails toElectionDetails()
    {
        if (LOG.isTraceEnabled()) LOG.trace("toElectionDetails()");

        ElectionDetails electionDetails = new ElectionDetails();
        if (LOG.isTraceEnabled()) LOG.trace("election "+this);
        electionDetails.setElectionId(this.getNodeId());
        electionDetails.setTitle(this.getTitle());
        electionDetails.setStart(this.getStart());
        electionDetails.setEnd(this.getEnd());
        electionDetails.setStartVoting(this.getVotingStart());
        electionDetails.setEndVoting(this.getVotingEnd());
        electionDetails.setInstitutionId(this.institution.getNodeId());
        if (LOG.isTraceEnabled()) LOG.trace("electionDetail; "+ electionDetails);
        return electionDetails;
    }

    public static Election fromElectionDetails(ElectionDetails electionDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromElectionDetails()");
        Election election = new Election();
        if (LOG.isTraceEnabled()) LOG.trace("electionDetails "+electionDetails);
        election.setNodeId(electionDetails.getElectionId());
        election.setTitle(electionDetails.getTitle());
        election.setStart(electionDetails.getStart());
        election.setEnd(electionDetails.getEnd());
        election.setVotingStart(electionDetails.getStartVoting());
        election.setVotingEnd(electionDetails.getEndVoting());
        Institution inst=new Institution();
        inst.setNodeId(electionDetails.getInstitutionId());
        election.setInstitution(inst);
        if (LOG.isTraceEnabled()) LOG.trace("election "+election);
        return election;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		if (this.nodeId!=null)
		{
			result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		}
		else
		{
			result = prime * result + ((end == null) ? 0 : end.hashCode());
			result = prime * result
					+ ((institution == null) ? 0 : institution.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			result = prime * result
					+ ((votingEnd == null) ? 0 : votingEnd.hashCode());
			result = prime * result
					+ ((votingStart == null) ? 0 : votingStart.hashCode());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Election other = (Election) obj;

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
			if (end == null) {
				if (other.end != null)
					return false;
			} else if (!end.equals(other.end))
				return false;
			if (institution == null) {
				if (other.institution != null)
					return false;
			} else if (!institution.equals(other.institution))
				return false;
			if (start == null) {
				if (other.start != null)
					return false;
			} else if (!start.equals(other.start))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			if (votingEnd == null) {
				if (other.votingEnd != null)
					return false;
			} else if (!votingEnd.equals(other.votingEnd))
				return false;
			if (votingStart == null) {
				if (other.votingStart != null)
					return false;
			} else if (!votingStart.equals(other.votingStart))
				return false;
		}
		return true;
	}
}
