package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.configuration.*;
import com.eulersbridge.iEngage.database.domain.Configuration;
import com.eulersbridge.iEngage.database.repository.ConfigurationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ConfigurationEventHandler implements ConfigurationService {
    private static Logger LOG = LoggerFactory.getLogger(ConfigurationService.class);

    private ConfigurationRepository configurationRepository;

    public ConfigurationEventHandler(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public ConfigurationCreatedEvent createConfiguration(CreateConfigurationEvent createConfigurationEvent) {
        ConfigurationDetails configurationDetails = createConfigurationEvent.getConfigurationDetails();
        Configuration configuration = Configuration.fromConfigurationDetails(configurationDetails);
        Configuration result = configurationRepository.save(configuration);
        ConfigurationCreatedEvent configurationCreatedEvent = new ConfigurationCreatedEvent(result.getConfigId(), result.toConfigurationDetails());
        return configurationCreatedEvent;
    }

    @Override
    public ReadConfigurationEvent requestReadConfiguration(RequestReadConfigurationEvent requestReadConfigurationEvent) {
        Configuration configuration = configurationRepository.findOne(requestReadConfigurationEvent.getConfigId());
        ReadConfigurationEvent readConfigurationEvent;
        if(configuration != null){
            readConfigurationEvent = new ReadConfigurationEvent(configuration.getConfigId(), configuration.toConfigurationDetails());
        }
        else{
            readConfigurationEvent = ReadConfigurationEvent.notFound(requestReadConfigurationEvent.getConfigId());
        }
        return readConfigurationEvent;
    }

    @Override
    public ConfigurationUpdatedEvent updateConfiguration(UpdateConfigurationEvent updateConfigurationEvent) {
        ConfigurationDetails configurationDetails = updateConfigurationEvent.getConfigurationDetails();
        Configuration configuration = Configuration.fromConfigurationDetails(configurationDetails);
        Long configId = configurationDetails.getConfigId();
        if(LOG.isDebugEnabled()) LOG.debug("configId is " + configId);
        Configuration configurationOld = configurationRepository.findOne(configId);
        if(configurationOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("configuration entity not found " + configId);
            return ConfigurationUpdatedEvent.notFound(configId);
        }
        else{
            Configuration result = configurationRepository.save(configuration);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getConfigId());
            return new ConfigurationUpdatedEvent(result.getConfigId(), result.toConfigurationDetails());
        }
    }

    @Override
    public DeletedEvent deleteConfiguration(DeleteConfigurationEvent deleteConfigurationEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteConfigurationEvent= "+deleteConfigurationEvent);
        Long configId = deleteConfigurationEvent.getConfigId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteConfiguration("+configId+")");
        Configuration configuration = configurationRepository.findOne(configId);
        if(configuration == null){
            return ConfigurationDeletedEvent.notFound(configId);
        }
        else{
            configurationRepository.delete(configId);
            ConfigurationDeletedEvent configurationDeletedEvent = new ConfigurationDeletedEvent(configId);
            return configurationDeletedEvent;
        }
    }
}
