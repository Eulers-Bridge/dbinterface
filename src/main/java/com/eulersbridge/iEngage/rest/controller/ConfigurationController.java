package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.configuration.*;
import com.eulersbridge.iEngage.core.services.ConfigurationService;
import com.eulersbridge.iEngage.rest.domain.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class ConfigurationController {

    @Autowired
    ConfigurationService configurationService;

    private static Logger LOG = LoggerFactory.getLogger(ConfigurationController.class);

    public ConfigurationController() {
        if(LOG.isDebugEnabled()) LOG.debug("ConfigurationController");
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.CONFIGURATION_LABEL)
    public @ResponseBody ResponseEntity<Configuration>
    createConfiguration(@RequestBody Configuration configuration)
    {
        if (LOG.isInfoEnabled()) LOG.info("attempting to create configuration "+configuration);
        CreateConfigurationEvent createConfigurationEvent = new CreateConfigurationEvent(configuration.toConfigurationDetails());
        ConfigurationCreatedEvent configurationCreatedEvent = configurationService.createConfiguration(createConfigurationEvent);
        if(configurationCreatedEvent.getConfigid() == null)
        {
            return new ResponseEntity<Configuration>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            Configuration result = Configuration.fromConfigurationDetails(configurationCreatedEvent.getConfigurationDetails());
            if (LOG.isDebugEnabled()) LOG.debug("configuration: "+result.toString());
            return new ResponseEntity<Configuration>(result, HttpStatus.OK);
        }
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CONFIGURATION_LABEL+"/{configId}")
    public @ResponseBody ResponseEntity<Configuration>
    findConfiguration(@PathVariable Long configId)
    {
        if (LOG.isInfoEnabled()) LOG.info(configId+" attempting to get configuration. ");
        RequestReadConfigurationEvent requestReadConfigurationEvent= new RequestReadConfigurationEvent(configId);
        ReadConfigurationEvent readConfigurationEvent= configurationService.requestReadConfiguration(requestReadConfigurationEvent);
        if (readConfigurationEvent.isEntityFound())
        {
            Configuration configuration = Configuration.fromConfigurationDetails(readConfigurationEvent.getConfigurationDetails());
            return new ResponseEntity<Configuration>(configuration, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Configuration>(HttpStatus.NOT_FOUND);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.CONFIGURATION_LABEL+"/{configId}")
    public @ResponseBody ResponseEntity<Configuration>
    updateConfiguration(@PathVariable Long configId, @RequestBody Configuration configuration)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update configuration. " + configId);
        ConfigurationUpdatedEvent configurationUpdatedEvent = configurationService.updateConfiguration(new UpdateConfigurationEvent(configId, configuration.toConfigurationDetails()));
        if(null != configurationUpdatedEvent)
        {
            if (LOG.isDebugEnabled()) LOG.debug("configurationUpdatedEvent - "+configurationUpdatedEvent);
            if(configurationUpdatedEvent.isEntityFound())
            {
                Configuration result = Configuration.fromConfigurationDetails(configurationUpdatedEvent.getConfigurationDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<Configuration>(result, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<Configuration>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<Configuration>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.CONFIGURATION_LABEL+"/{configId}")
    public @ResponseBody ResponseEntity<Boolean>
    deleteConfiguration(@PathVariable Long configId)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete configuration. " + configId);
        ResponseEntity<Boolean> response;
        ConfigurationDeletedEvent configurationDeletedEvent = configurationService.deleteConfiguration(new DeleteConfigurationEvent(configId));
        if (configurationDeletedEvent.isDeletionCompleted())
            response = new ResponseEntity<Boolean>(configurationDeletedEvent.isDeletionCompleted(), HttpStatus.OK);
        else if (configurationDeletedEvent.isEntityFound())
            response = new ResponseEntity<Boolean>(configurationDeletedEvent.isDeletionCompleted(), HttpStatus.GONE);
        else
            response = new ResponseEntity<Boolean>(configurationDeletedEvent.isDeletionCompleted(), HttpStatus.NOT_FOUND);
        return response;
    }
}
