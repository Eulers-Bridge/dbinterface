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
	Long parentId;
	
	private ReadAllEvent()
	{
		super();
	}
	
	public ReadAllEvent(Long parentId)
	{
		this();
		this.parentId=parentId;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
