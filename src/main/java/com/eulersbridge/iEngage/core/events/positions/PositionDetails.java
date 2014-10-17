package com.eulersbridge.iEngage.core.events.positions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PositionDetails {
    private Long positionId;
    private String name;
    private String description;

    private static Logger LOG = LoggerFactory.getLogger(PositionDetails.class);

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getPositionId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", description = ");
        buff.append(getDescription());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        if (getPositionId()!=null)
        {
            result = prime * result	+ getPositionId().hashCode();
        }
        else
        {
            result = prime * result	+ ((name == null) ? 0 : name.hashCode());
            result = prime * result	+ ((description == null) ? 0 : description.hashCode());
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
        PositionDetails other = (PositionDetails) obj;
        if (positionId != null)
        {
            if (positionId.equals(other.positionId))
                return true;
            else return false;
        }
        else
        {
            if (other.positionId != null)
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (description == null) {
                if (other.description != null)
                    return false;
            } else if (!description.equals(other.description))
                return false;
        }
        return true;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}