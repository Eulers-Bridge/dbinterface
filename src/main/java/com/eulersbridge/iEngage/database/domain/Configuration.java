package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.configuration.ConfigurationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Configuration {
    @GraphId
    private Long configId;
    //TODO add attributes

    private static Logger LOG = LoggerFactory.getLogger(Configuration.class);

    public Configuration() {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public static Configuration fromConfigurationDetails(ConfigurationDetails configurationDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromConfigurationDetails()");
        Configuration configuration = new Configuration();
        if (LOG.isTraceEnabled()) LOG.trace("configurationDetails "+configurationDetails);
        configuration.setConfigId(configurationDetails.getConfigId());
        //TODO add attributes

        if (LOG.isTraceEnabled()) LOG.trace("configuration "+configuration);
        return configuration;
    }

    public ConfigurationDetails toConfigurationDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toConfigurationDetails()");
        ConfigurationDetails configurationDetails = new ConfigurationDetails();
        if (LOG.isTraceEnabled()) LOG.trace("position "+this);
        configurationDetails.setConfigId(getConfigId());
        //TODO add attributes

        if (LOG.isTraceEnabled()) LOG.trace("configurationDetails; "+ configurationDetails);
        return configurationDetails;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getConfigId());
        //TODO add attributes
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }
}
