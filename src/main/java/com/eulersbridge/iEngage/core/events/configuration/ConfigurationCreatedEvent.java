package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ConfigurationCreatedEvent extends CreatedEvent{
    private Long Configid;
    private ConfigurationDetails configurationDetails;

    private static Logger LOG = LoggerFactory.getLogger(ConfigurationCreatedEvent.class);

    public ConfigurationCreatedEvent(Long configid) {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        Configid = configid;
    }

    public ConfigurationCreatedEvent(Long configid, ConfigurationDetails configurationDetails) {
        this(configid);
        this.configurationDetails = configurationDetails;
    }

    public Long getConfigid() {
        return Configid;
    }

    public void setConfigid(Long configid) {
        Configid = configid;
    }

    public ConfigurationDetails getConfigurationDetails() {
        return configurationDetails;
    }

    public void setConfigurationDetails(ConfigurationDetails configurationDetails) {
        this.configurationDetails = configurationDetails;
    }
}
