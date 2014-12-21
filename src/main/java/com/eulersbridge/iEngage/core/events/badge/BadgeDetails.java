package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.Details;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class BadgeDetails extends Details{
    private String name;
    private boolean awarded;
    private Long timestamp;
    private Long xpValue;

    private static Logger LOG = LoggerFactory.getLogger(BadgeDetails.class);

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", awarded = ");
        buff.append(isAwarded());
        buff.append(", timestamp = ");
        buff.append(getTimestamp());
        buff.append(", xpValue = ");
        buff.append(getXpValue());;
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
            result = prime * result	+ ((name == null) ? 0 : name.hashCode());
            result = prime * result	+ (new Boolean(awarded).hashCode());
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
        BadgeDetails other = (BadgeDetails) obj;
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
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (awarded != other.awarded)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAwarded() {
        return awarded;
    }

    public void setAwarded(boolean awarded) {
        this.awarded = awarded;
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
