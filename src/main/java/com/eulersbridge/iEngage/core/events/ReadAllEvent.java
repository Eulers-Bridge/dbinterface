/**
 * 
 */
package com.eulersbridge.iEngage.core.events;


/**
 * @author Greg Newitt
 *
 */
public class ReadAllEvent
{
	Long instId;
	
	private ReadAllEvent()
	{
		super();
	}
	
	public ReadAllEvent(Long institutionId)
	{
		this();
		this.instId=institutionId;
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

}
