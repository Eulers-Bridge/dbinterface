/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadNewsArticlesEvent extends ReadAllEvent 
{
	Long nfId;
	
	private ReadNewsArticlesEvent()
	{
		super(null);
	}
	
	public ReadNewsArticlesEvent(Long institutionId)
	{
		super(institutionId);
	}

/*	public ReadNewsArticlesEvent(Long instId,Long nfId)
	{
		super(nfId);
		this.instId=instId;
	}
*/
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
