package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CommentDetails extends Details {

    private Long targetId;
    private String userName;
    private String userEmail;
    private Long timestamp;
    private String content;
    private PhotoDetails profilePhotoDetails;
    private static Logger LOG = LoggerFactory.getLogger(CommentDetails.class);

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", targetId = ");
        buff.append(getTargetId());
        buff.append(", userName = ");
        buff.append(getUserName());
        buff.append(", userEmail = ");
        buff.append(getUserEmail());
        buff.append(", timestamp = ");
        buff.append(getTimestamp());
        buff.append(", content = ");
        buff.append(getContent());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
        return retValue;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        if (getNodeId() != null)
        {
            result = prime * result + getNodeId().hashCode();
        }
        else
        {
            result = prime * result + ((targetId == null) ? 0 : targetId.hashCode());
            result = prime * result + ((userName == null) ? 0 : userName.hashCode());
            result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
            result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
            result = prime * result + ((content == null) ? 0 : content.hashCode());
        }
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CommentDetails other = (CommentDetails) obj;
        if (nodeId != null)
        {
            if (nodeId.equals(other.nodeId))
                return true;
            else return false;
        }
        else
        {
            if (other.nodeId != null) return false;
            if (targetId == null)
            {
                if (other.targetId != null) return false;
            }
            else if (!targetId.equals(other.targetId)) return false;
            if (userName == null)
            {
                if (other.userName != null) return false;
            }
            else if (!userName.equals(other.userName)) return false;
            if (userEmail == null)
            {
                if (other.userEmail != null) return false;
            }
            else if (!userEmail.equals(other.userEmail)) return false;
            if (timestamp == null)
            {
                if (other.timestamp != null) return false;
            }
            else if (!timestamp.equals(other.timestamp)) return false;
            if (content == null)
            {
                if (other.content != null) return false;
            }
            else if (!content.equals(other.content)) return false;
        }
        return true;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	/**
	 * @return the profilePhotoDetails
	 */
	public PhotoDetails getProfilePhotoDetails()
	{
		return profilePhotoDetails;
	}

	/**
	 * @param profilePhotoDetails the profilePhotoDetails to set
	 */
	public void setProfilePhotoDetails(PhotoDetails profilePhotoDetails)
	{
		this.profilePhotoDetails = profilePhotoDetails;
	}
}
