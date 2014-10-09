/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadNewsArticlesEvent extends RequestReadEvent 
{
	Long instId;
	Long nfId;
	
	private ReadNewsArticlesEvent()
	{
		super();
	}
	
	public ReadNewsArticlesEvent(Long institutionId)
	{
		this();
		this.instId=institutionId;
	}

/*	public ReadNewsArticlesEvent(Long instId,Long nfId)
	{
		this(nfId);
		this.instId=instId;
	}
*/
	/**
	 * @return the instId
	 */
	public Long getInstId() {
		return instId;
	}
	/**
	 * @param instId the instId to set
	 */
	public void setInstId(Long instId) {
		this.instId = instId;
	}
	/**
	 * @return the nfId
	 */
	public Long getNfId() {
		return nfId;
	}
	/**
	 * @param syId the syId to set
	 */
	public void setNfId(Long nfId) {
		this.nfId = nfId;
	}
}
