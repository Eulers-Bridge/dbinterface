package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.configuration.ConfigurationDetails;
import com.eulersbridge.iEngage.rest.controller.ConfigurationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Configuration extends ResourceSupport {
    private Long configId;
    //TODO add attributes

    private static Logger LOG = LoggerFactory.getLogger(Configuration.class);

    public Configuration() {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static Configuration fromConfigurationDetails(ConfigurationDetails configurationDetails){
        Configuration configuration = new Configuration();
        String simpleName = Position.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        configuration.setConfigId(configurationDetails.getConfigId());
        //TODO add attributes

        // {!begin selfRel}
        configuration.add(linkTo(ConfigurationController.class).slash(name).slash(configuration.configId).withSelfRel());
        // {!end selfRel}
        return configuration;
    }

    public ConfigurationDetails toConfigurationDetails(){
        ConfigurationDetails configurationDetails = new ConfigurationDetails();
        configurationDetails.setConfigId(getConfigId());
        //TODO add attributes

        return configurationDetails;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }
}
