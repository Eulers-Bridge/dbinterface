package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteConfigurationEvent extends DeleteEvent
{

    public DeleteConfigurationEvent(Long configId)
    {
        super(configId);
    }

    public Long getConfigId() {
        return getNodeId();
    }
}
