package com.eulersbridge.iEngage.core.events.configuration;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Yikai Gong
 */

public class CreateConfigurationEvent extends CreateEvent
{
	public CreateConfigurationEvent(ConfigurationDetails configurationDetails)
	{
		super(configurationDetails);
	}
}
