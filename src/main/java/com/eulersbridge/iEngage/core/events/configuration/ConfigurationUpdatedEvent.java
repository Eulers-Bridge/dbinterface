package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class ConfigurationUpdatedEvent extends UpdatedEvent {
    private Long configId;
    private ConfigurationDetails configurationDetails;

    public ConfigurationUpdatedEvent(Long configId, ConfigurationDetails configurationDetails) {
        this.configId = configId;
        this.configurationDetails = configurationDetails;
    }

    public ConfigurationUpdatedEvent(Long configId) {
        this.configId = configId;
    }

    public Long getConfigId() {
        return configId;
    }

    public ConfigurationDetails getConfigurationDetails() {
        return configurationDetails;
    }

    public static ConfigurationUpdatedEvent notFound(Long id){
        ConfigurationUpdatedEvent configurationUpdatedEvent = new ConfigurationUpdatedEvent(id);
        configurationUpdatedEvent.entityFound = false;
        return configurationUpdatedEvent;
    }
}
