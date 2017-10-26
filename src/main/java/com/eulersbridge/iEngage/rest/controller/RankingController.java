package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.events.RequestHandledEvent;
import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
import com.eulersbridge.iEngage.rest.domain.ContactRequestDomain;
import com.eulersbridge.iEngage.rest.domain.WrappedDomainList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.SwitchPoint;

/**
 * @author Yikai Gong
 */
@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class RankingController {
  @Autowired
  UserService userService;

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + ControllerConstants.RANKING_LABEL + "/{ranKey}" )
  public @ResponseBody
  ResponseEntity findContactRequestsMade(@PathVariable String ranKey) {
    RequestHandledEvent res = userService.getRankedUserProfiles(ranKey);
    return res.toResponseEntity();
  }
}
