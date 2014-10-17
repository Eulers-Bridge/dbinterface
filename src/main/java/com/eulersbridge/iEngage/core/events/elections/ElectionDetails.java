package com.eulersbridge.iEngage.core.events.elections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ElectionDetails {
    private Long electionId;
    private String title;
    private Long start;
    private Long end;
    private Long startVoting;
    private Long endVoting;
    private Long InstitutionId;

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
	 */
	public ElectionDetails(Long electionId, String title, Long start, Long end,
			Long startVoting, Long endVoting, Long institutionId) 
	{
		super();
		this.electionId = electionId;
		this.title = title;
		this.start = start;
		this.end = end;
		this.startVoting = startVoting;
		this.endVoting = endVoting;
		InstitutionId = institutionId;
	}

	public void setElectionId(Long electionId){
        this.electionId = electionId;
    }
    public Long getElectionId(){
        return this.electionId;
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
    public String toString(){
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
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return InstitutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		InstitutionId = institutionId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (electionId != null) 
		{
			result = prime * result
					+ electionId.hashCode();
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
		if (electionId != null) 
		{
			if (electionId.equals(other.electionId))
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
		}
		return true;
	}
}
