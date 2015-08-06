/**
 * 
 */
package com.eulersbridge.iEngage.core.events;

import com.eulersbridge.iEngage.database.domain.User;

/**
 * @author Greg Newitt
 *
 */
public class LikeEvent 
{
	protected Long nodeId;
	protected String emailAddress;
    protected Class targetType;

	public LikeEvent(Long nodeId, User user) 
	{
		this(nodeId,user.getEmail());
	}
	
	public LikeEvent(Long nodeId, String email)
	{
		this.nodeId=nodeId;
		this.emailAddress=email;
	}

    public LikeEvent(Long nodeId, String email, Class targetType)
    {
        this.nodeId=nodeId;
        this.emailAddress=email;
        this.targetType = targetType;
    }

	/**
	 * @return the userId
	 */
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

    public Class getTargetType() {
        return targetType;
    }

    public void setTargetType(Class targetType) {
        this.targetType = targetType;
    }
}
