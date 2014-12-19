package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.Details;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class TaskDetails extends Details{
    private String action;
    private boolean completed;
    private Long timestamp;
    private Long xpValue;

    private static Logger LOG = LoggerFactory.getLogger(TaskDetails.class);

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", action = ");
        buff.append(getAction());
        buff.append(", completed = ");
        buff.append(isCompleted());
        buff.append(", timestamp = ");
        buff.append(getTimestamp());
        buff.append(", xpValue = ");
        buff.append(getXpValue());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        if (getNodeId()!=null)
        {
            result = prime * result	+ getNodeId().hashCode();
        }
        else
        {
            result = prime * result	+ ((action == null) ? 0 : action.hashCode());
            result = prime * result	+ (new Boolean(completed).hashCode());
            result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
            result = prime * result + ((xpValue == null) ? 0 : xpValue.hashCode());
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaskDetails other = (TaskDetails) obj;
        if (nodeId != null)
        {
            if (nodeId.equals(other.nodeId))
                return true;
            else return false;
        }
        else
        {
            if (other.nodeId != null)
                return false;
            if (action == null) {
                if (other.action != null)
                    return false;
            } else if (!action.equals(other.action))
                return false;
            if (completed != other.completed)
                return false;
            if (timestamp == null) {
                if (other.timestamp != null)
                    return false;
            } else if (!timestamp.equals(other.timestamp))
                return false;
            if (xpValue == null) {
                if (other.xpValue != null)
                    return false;
            } else if (!xpValue.equals(other.xpValue))
                return false;
        }
        return true;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getXpValue() {
        return xpValue;
    }

    public void setXpValue(Long xpValue) {
        this.xpValue = xpValue;
    }
}
