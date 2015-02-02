/**
 * 
 */
package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class PositionsReadEvent extends ReadEvent
{
	Iterable<PositionDetails> positions;
	Long electionId=null;
	private boolean electionFound=true;
	private Long totalItems;
	private Integer totalPages;
	
	public PositionsReadEvent(Long electionId, Iterable<PositionDetails> elections, long totalElements, int totalPages)
	{
		super(1l);
		this.positions=elections;
		this.electionId=electionId;
		this.totalItems=totalElements;
		this.totalPages=totalPages;
	}

	public PositionsReadEvent(Long electionId, Iterable<PositionDetails> elections)
	{
		super(1l);
		this.positions=elections;
		this.electionId=electionId;
	}

	public PositionsReadEvent(Iterable<PositionDetails> elections)
	{
		super(1l);
		this.positions=elections;
	}

	public PositionsReadEvent()
	{
		super(1l);
	}
	
	/**
	 * @return the elections
	 */
	public Iterable<PositionDetails> getPositions() 
	{
		return positions;
	}

	/**
	 * @param elections the elections to set
	 */
	public void setPositions(Iterable<PositionDetails> positions) 
	{
		this.positions = positions;
	}

	/**
	 * @return the totalItems
	 */
	public Long getTotalItems()
	{
		return totalItems;
	}

	/**
	 * @param totalItems the totalItems to set
	 */
	public void setTotalItems(Long totalEvents)
	{
		this.totalItems = totalEvents;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages()
	{
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages)
	{
		this.totalPages = totalPages;
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

	public static PositionsReadEvent electionNotFound() 
	{
		PositionsReadEvent nare=new PositionsReadEvent();
		nare.electionFound=false;
		nare.entityFound=false;
		return nare;
	}


}
