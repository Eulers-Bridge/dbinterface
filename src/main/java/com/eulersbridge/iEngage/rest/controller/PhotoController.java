/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Greg Newitt
 *
 */
@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class PhotoController
{
    private static Logger LOG = LoggerFactory.getLogger(PhotoController.class);
    
	public PhotoController()
	{
		if (LOG.isDebugEnabled()) LOG.debug("PhotoController()");
	}



}
