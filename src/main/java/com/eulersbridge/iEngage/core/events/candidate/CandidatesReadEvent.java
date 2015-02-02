/**
 * 
 */
package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class CandidatesReadEvent extends ReadEvent
{
	Iterable<CandidateDetails> candidates;
	Long electionId=null;
	private boolean electionFound=true;
	
	public CandidatesReadEvent(Long electionId, Iterable<CandidateDetails> elections)
	{
		super(1l);
		this.candidates=elections;
		this.electionId=electionId;
	}

	public CandidatesReadEvent(Iterable<CandidateDetails> elections)
	{
		super(1l);
		this.candidates=elections;
	}

	public CandidatesReadEvent()
	{
		super(1l);
	}
	
	/**
	 * @return the elections
	 */
	public Iterable<CandidateDetails> getCandidates() 
	{
		return candidates;
	}

	/**
	 * @param elections the elections to set
	 */
	public void setCandidates(Iterable<CandidateDetails> candidates) 
	{
		this.candidates = candidates;
	}

	/**
	 * @return the electionId
	 */
	public Long getElectionId() {
		return electionId;
	}

	/**
	 * @param electionId the electionId to set
	 */
	public void setElectionId(Long electionId) {
		this.electionId = electionId;
	}

	/**
	 * @return the electionFound
	 */
	public boolean isElectionFound() {
		return electionFound;
	}

	public static CandidatesReadEvent electionNotFound() 
	{
		CandidatesReadEvent nare=new CandidatesReadEvent();
		nare.electionFound=false;
		nare.entityFound=false;
		return nare;
	}



}
