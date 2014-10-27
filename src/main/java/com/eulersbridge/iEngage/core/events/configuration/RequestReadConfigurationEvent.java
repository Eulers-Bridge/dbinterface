package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadConfigurationEvent extends RequestReadEvent {
    private Long configId;

    public RequestReadConfigurationEvent(Long configId) {
        this.configId = configId;
    }

    public Long getConfigId() {
        return configId;
    }
}
