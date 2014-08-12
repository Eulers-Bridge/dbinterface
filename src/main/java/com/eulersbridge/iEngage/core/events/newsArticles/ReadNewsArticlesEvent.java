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
	Long syId;
	
	public ReadNewsArticlesEvent()
	{
		
	}
	
	public ReadNewsArticlesEvent(Long instId,Long syId)
	{
		this.instId=instId;
		this.syId=syId;
	}
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
	 * @return the syId
	 */
	public Long getSyId() {
		return syId;
	}
	/**
	 * @param syId the syId to set
	 */
	public void setSyId(Long syId) {
		this.syId = syId;
	}
}
