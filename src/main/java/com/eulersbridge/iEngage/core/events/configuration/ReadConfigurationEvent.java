package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadConfigurationEvent extends ReadEvent
{
	public ReadConfigurationEvent(Long configId)
	{
		super(configId);
	}

	public ReadConfigurationEvent(Long configId,
			ConfigurationDetails configurationDetails)
	{
		super(configId,configurationDetails);
	}
}
