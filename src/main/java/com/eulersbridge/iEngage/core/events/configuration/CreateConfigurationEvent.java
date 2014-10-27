package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CreateConfigurationEvent extends CreateEvent {
    private ConfigurationDetails configurationDetails;

    private static Logger LOG = LoggerFactory.getLogger(CreateConfigurationEvent.class);

    public CreateConfigurationEvent(ConfigurationDetails configurationDetails) {
        this.configurationDetails = configurationDetails;
    }

    public CreateConfigurationEvent(Long id, ConfigurationDetails configurationDetails) {
        configurationDetails.setConfigId(id);
        this.configurationDetails = configurationDetails;
    }

    public ConfigurationDetails getConfigurationDetails() {
        return configurationDetails;
    }

    public void setConfigurationDetails(ConfigurationDetails configurationDetails) {
        this.configurationDetails = configurationDetails;
    }
}
