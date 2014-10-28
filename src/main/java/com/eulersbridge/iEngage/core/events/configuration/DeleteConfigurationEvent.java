package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteConfigurationEvent extends DeleteEvent {
    private final Long configId;

    public DeleteConfigurationEvent(Long configId) {
        this.configId = configId;
    }

    public Long getConfigId() {
        return configId;
    }
}
