package com.eulersbridge.iEngage.core.events.generalInfo;

public class GiInstitution 
{
	Long instId;
	String instName;
	
	public GiInstitution(Long instId,String name)
	{
		this.instId=instId;
		this.instName=name;
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
	 * @return the instName
	 */
	public String getInstName() {
		return instName;
	}

	/**
	 * @param instName the instName to set
	 */
	public void setInstName(String instName) {
		this.instName = instName;
	}
}