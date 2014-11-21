package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ConfigurationCreatedEvent extends CreatedEvent{
    private Long configid;

    private static Logger LOG = LoggerFactory.getLogger(ConfigurationCreatedEvent.class);

    public ConfigurationCreatedEvent(Long configid)
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.configid = configid;
    }

    public ConfigurationCreatedEvent(Long configid, ConfigurationDetails configurationDetails)
    {
    	super(configurationDetails);
        this.configid = configid;
    }

    public Long getConfigid() {
        return configid;
    }

    public void setConfigid(Long configid)
    {
        this.configid = configid;
    }

    public ConfigurationDetails getConfigurationDetails() {
        return (ConfigurationDetails) getDetails();
    }

    public void setConfigurationDetails(ConfigurationDetails configurationDetails) {
        setDetails(configurationDetails);
    }
}
