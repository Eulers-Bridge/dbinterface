package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class ConfigurationUpdatedEvent extends UpdatedEvent
{
	public ConfigurationUpdatedEvent(Long configId,
			ConfigurationDetails configurationDetails)
	{
		super(configId,configurationDetails);
	}

	public ConfigurationUpdatedEvent(Long configId)
	{
		super(configId);
	}
}
