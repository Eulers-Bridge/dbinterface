package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadConfigurationEvent extends ReadEvent {
    private Long configId;
    private ConfigurationDetails configurationDetails;

    public ReadConfigurationEvent(Long configId) {
        this.configId = configId;
    }

    public ReadConfigurationEvent(Long configId, ConfigurationDetails configurationDetails) {
        this.configId = configId;
        this.configurationDetails = configurationDetails;
    }

    public Long getConfigId() {
        return configId;
    }

    public ConfigurationDetails getConfigurationDetails() {
        return configurationDetails;
    }

    public static ReadConfigurationEvent notFound(Long configId){
        ReadConfigurationEvent readConfigurationEvent = new ReadConfigurationEvent(configId);
        readConfigurationEvent.entityFound = false;
        return readConfigurationEvent;
    }
}
