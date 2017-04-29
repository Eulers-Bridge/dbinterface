package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.Details;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ElectionDetails extends Details
{
    private String title;
    private Long start;
    private Long end;
    private Long startVoting;
    private Long endVoting;
    private Long institutionId;
    private String introduction;
    private String process;

    private static Logger LOG = LoggerFactory.getLogger(ElectionDetails.class);

    public ElectionDetails()
    {
		super();
    }

    /**
	 * @param electionId
	 * @param title
	 * @param start
	 * @param end
	 * @param startVoting
	 * @param endVoting
	 * @param institutionId
	 * @param introduction
	 * @param process
	 */
	public ElectionDetails(Long electionId, String title, Long start, Long end,
			Long startVoting, Long endVoting, Long institutionId,String introduction,String process) 
	{
		super(electionId);
		this.title = title;
		this.start = start;
		this.end = end;
		this.startVoting = startVoting;
		this.endVoting = endVoting;
		this.institutionId = institutionId;
		this.introduction = introduction;
		this.process = process;
	}

	public void setElectionId(Long electionId){
        this.nodeId = electionId;
    }
    public Long getElectionId(){
        return getNodeId();
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setStart(Long start){
        this.start = start;
    }
    public Long getStart(){
        return this.start;
    }

    public void setEnd(Long end){
        this.end = end;
    }
    public Long getEnd(){
        return this.end;
    }

    public void setStartVoting(Long startVoting){
        this.startVoting = startVoting;
    }
    public Long getStartVoting(){
        return this.startVoting;
    }

    public void setEndVoting(Long endVoting){
        this.endVoting = endVoting;
    }
    public Long getEndVoting(){
        return this.endVoting;
    }

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getElectionId());
        buff.append(", title = ");
        buff.append(getTitle());
        buff.append(", start = ");
        buff.append(getStart());
        buff.append(", end = ");
        buff.append(getEnd());
        buff.append(", startVoting = ");
        buff.append(getStartVoting());
        buff.append(", endVoting = ");
        buff.append(getEndVoting());
        buff.append(", introduction = ");
        buff.append(getIntroduction());
        buff.append(", process = ");
        buff.append(getProcess());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction()
	{
		return introduction;
	}

	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction)
	{
		this.introduction = introduction;
	}

	/**
	 * @return the process
	 */
	public String getProcess()
	{
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(String process)
	{
		this.process = process;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (nodeId != null) 
		{
			result = prime * result
					+ nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((end == null) ? 0 : end.hashCode());
			result = prime * result
					+ ((endVoting == null) ? 0 : endVoting.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
			result = prime * result
					+ ((startVoting == null) ? 0 : startVoting.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			result = prime * result + ((introduction == null) ? 0 : introduction.hashCode());
			result = prime * result + ((process == null) ? 0 : process.hashCode());
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
		ElectionDetails other = (ElectionDetails) obj;
		if (nodeId != null) 
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		} else //electionId null, other fields must match.
		
		{
			if (end == null) {
				if (other.end != null)
					return false;
			} else if (!end.equals(other.end))
				return false;
			if (endVoting == null) {
				if (other.endVoting != null)
					return false;
			} else if (!endVoting.equals(other.endVoting))
				return false;
			if (start == null) {
				if (other.start != null)
					return false;
			} else if (!start.equals(other.start))
				return false;
			if (startVoting == null) {
				if (other.startVoting != null)
					return false;
			} else if (!startVoting.equals(other.startVoting))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			if (introduction == null) {
				if (other.introduction != null)
					return false;
			} else if (!introduction.equals(other.introduction))
				return false;
			if (process == null) {
				if (other.process != null)
					return false;
			} else if (!process.equals(other.process))
				return false;
			if (institutionId == null) {
				if (other.institutionId != null)
					return false;
			} else if (!institutionId.equals(other.institutionId))
				return false;
		}
		return true;
	}
}
