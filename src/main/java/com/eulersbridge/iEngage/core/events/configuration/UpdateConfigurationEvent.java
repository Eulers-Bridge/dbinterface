package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class UpdateConfigurationEvent extends UpdatedEvent
{
	public UpdateConfigurationEvent(Long configId,
			ConfigurationDetails configurationDetails)
	{
		super(configId,configurationDetails);
	}
}
