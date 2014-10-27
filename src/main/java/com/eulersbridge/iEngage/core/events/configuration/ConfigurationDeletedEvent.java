package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class ConfigurationDeletedEvent extends DeletedEvent {
    private final Long configId;
    private boolean deletionCompleted = true;

    public ConfigurationDeletedEvent(Long configId) {
        this.configId = configId;
    }

    public static ConfigurationDeletedEvent deletionForbidden(Long configId){
        ConfigurationDeletedEvent configurationDeletedEvent = new ConfigurationDeletedEvent(configId);
        configurationDeletedEvent.entityFound = true;
        configurationDeletedEvent.deletionCompleted = false;
        return configurationDeletedEvent;
    }

    public static ConfigurationDeletedEvent notFound(Long configId){
        ConfigurationDeletedEvent configurationDeletedEvent = new ConfigurationDeletedEvent(configId);
        configurationDeletedEvent.entityFound = false;
        configurationDeletedEvent.deletionCompleted = false;
        return configurationDeletedEvent;
    }

    public boolean isDeletionCompleted(){
        return deletionCompleted;
    }
}
