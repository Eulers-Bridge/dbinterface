/**
 * 
 */
package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.AllReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class CandidatesReadEvent extends AllReadEvent
{
	Iterable<CandidateDetails> candidates;
	private boolean electionFound=true;
	
	public CandidatesReadEvent(Long electionId, Iterable<CandidateDetails> candidates, Long totalItems, Integer totalPages)
	{
		super(electionId,totalItems,totalPages);
		this.candidates=candidates;
	}

	public CandidatesReadEvent(Long electionId, Iterable<CandidateDetails> candidates)
	{
		super(electionId);
		this.candidates=candidates;
	}

	public CandidatesReadEvent(Iterable<CandidateDetails> candidates)
	{
		super(null);
		this.candidates=candidates;
	}

	public CandidatesReadEvent()
	{
		super(null);
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
