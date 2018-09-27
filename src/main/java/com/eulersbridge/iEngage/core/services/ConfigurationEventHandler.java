package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.configuration.*;
import com.eulersbridge.iEngage.core.services.interfacePack.ConfigurationService;
import com.eulersbridge.iEngage.database.domain.Configuration;
import com.eulersbridge.iEngage.database.repository.ConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yikai Gong
 */

@Service
public class ConfigurationEventHandler implements ConfigurationService {
  private static Logger LOG = LoggerFactory
    .getLogger(ConfigurationService.class);

  private ConfigurationRepository configurationRepository;

  @Autowired
  public ConfigurationEventHandler(
    ConfigurationRepository configurationRepository) {
    this.configurationRepository = configurationRepository;
  }

  @Override
  public ConfigurationCreatedEvent createConfiguration(
    CreateConfigurationEvent createConfigurationEvent) {
    ConfigurationDetails configurationDetails =
      (ConfigurationDetails) createConfigurationEvent.getDetails();
    Configuration configuration = Configuration
      .fromConfigurationDetails(configurationDetails);
    Configuration result = configurationRepository.save(configuration);
    return new ConfigurationCreatedEvent(
      result.getNodeId(), result.toConfigurationDetails());
  }

  @Override
  public ReadEvent requestReadConfiguration(
    RequestReadConfigurationEvent requestReadConfigurationEvent) {
    Configuration configuration = configurationRepository
      .findById(requestReadConfigurationEvent.getNodeId()).get();
    ReadEvent readConfigurationEvent;
    if (configuration != null) {
      readConfigurationEvent = new ReadConfigurationEvent(
        configuration.getNodeId(),
        configuration.toConfigurationDetails());
    } else {
      readConfigurationEvent = ReadConfigurationEvent
        .notFound(requestReadConfigurationEvent.getNodeId());
    }
    return readConfigurationEvent;
  }

  @Override
  public UpdatedEvent updateConfiguration(
    UpdateConfigurationEvent updateConfigurationEvent) {
    ConfigurationDetails configurationDetails = (ConfigurationDetails) updateConfigurationEvent
      .getDetails();
    Configuration configuration = Configuration
      .fromConfigurationDetails(configurationDetails);
    Long configId = configurationDetails.getConfigId();
    if (LOG.isDebugEnabled()) LOG.debug("configId is " + configId);
    Configuration configurationOld = configurationRepository
      .findById(configId).get();
    if (configurationOld == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("configuration entity not found " + configId);
      return ConfigurationUpdatedEvent.notFound(configId);
    } else {
      Configuration result = configurationRepository.save(configuration, 0);
      if (LOG.isDebugEnabled())
        LOG.debug("updated successfully" + result.getNodeId());
      return new ConfigurationUpdatedEvent(result.getNodeId()
        , result.toConfigurationDetails());
    }
  }

  @Override
  public DeletedEvent deleteConfiguration(
    DeleteConfigurationEvent deleteConfigurationEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deleteConfigurationEvent= "
        + deleteConfigurationEvent);
    Long configId = deleteConfigurationEvent.getNodeId();
    if (LOG.isDebugEnabled())
      LOG.debug("deleteConfiguration(" + configId + ")");
    Configuration configuration = configurationRepository.findById(configId).get();
    if (configuration == null) {
      return ConfigurationDeletedEvent.notFound(configId);
    } else {
      configurationRepository.deleteById(configId);
      return new ConfigurationDeletedEvent(configId);
    }
  }
}
