package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.Details;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ConfigurationDetails extends Details
{
    //TODO add attributes

    private static Logger LOG = LoggerFactory.getLogger(ConfigurationDetails.class);

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getConfigId());
        //TODO add attributes
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        if (getConfigId()!=null)
        {
            result = prime * result	+ getConfigId().hashCode();
        }
        else
        {
            //TODO add attributes
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
        ConfigurationDetails other = (ConfigurationDetails) obj;
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
            //TODO add attributes
        }
        return true;
    }

    public Long getConfigId() {
        return nodeId;
    }

    public void setConfigId(Long configId) {
        this.nodeId = configId;
    }
}
