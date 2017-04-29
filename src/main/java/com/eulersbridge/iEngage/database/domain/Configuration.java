package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.configuration.ConfigurationDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Configuration extends Node{
  //TODO add attributes

  private static Logger LOG = LoggerFactory.getLogger(Configuration.class);

  public Configuration() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public static Configuration fromConfigurationDetails(ConfigurationDetails configurationDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromConfigurationDetails()");
    Configuration configuration = new Configuration();
    if (LOG.isTraceEnabled())
      LOG.trace("configurationDetails " + configurationDetails);
    configuration.setNodeId(configurationDetails.getConfigId());
    //TODO add attributes

    if (LOG.isTraceEnabled()) LOG.trace("configuration " + configuration);
    return configuration;
  }

  public ConfigurationDetails toConfigurationDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toConfigurationDetails()");
    ConfigurationDetails configurationDetails = new ConfigurationDetails();
    if (LOG.isTraceEnabled()) LOG.trace("position " + this);
    configurationDetails.setConfigId(getNodeId());
    //TODO add attributes

    if (LOG.isTraceEnabled())
      LOG.trace("configurationDetails; " + configurationDetails);
    return configurationDetails;
  }

  @Override
  public String toString() {
    String buff = "[ id = " + getNodeId() +
      " ]";
    String retValue;
    //TODO add attributes
    retValue = buff;
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

}
