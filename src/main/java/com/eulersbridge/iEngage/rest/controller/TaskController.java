package com.eulersbridge.iEngage.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class TaskController {

    private static Logger LOG = LoggerFactory.getLogger(TaskController.class);

    public TaskController() {
    }


}
