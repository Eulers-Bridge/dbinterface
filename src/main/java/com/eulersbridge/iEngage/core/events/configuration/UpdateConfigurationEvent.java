package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class UpdateConfigurationEvent extends UpdatedEvent {
    private Long configId;
    private ConfigurationDetails configurationDetails;

    public UpdateConfigurationEvent(Long configId, ConfigurationDetails configurationDetails) {
        this.configId = configId;
        this.configurationDetails = configurationDetails;
        this.configurationDetails.setConfigId(configId);
    }

    public Long getConfigId() {
        return configId;
    }

    public ConfigurationDetails getConfigurationDetails() {
        return configurationDetails;
    }
}
