package org.tourgune.egistour.controller.api.secure;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.egistour.facade.DeveloperFacade;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Controlador privado encargado tratar peticiones HTTP relacionadas con la gestion de desarrolladores. 
 */

@Controller
@RequestMapping("api/developer")
public class DeveloperSecureAPI {
	
	@Resource
	private DeveloperFacade developerFacade;
	
	@RequestMapping(value = "/token", method = RequestMethod.GET)
    public @ResponseBody
    String tokenDeveloper() {
		String developername = SecurityContextHolder.getContext()
                      .getAuthentication().getName();
          return developerFacade.getDeveloperToken(developername);
    }
	
	


}
