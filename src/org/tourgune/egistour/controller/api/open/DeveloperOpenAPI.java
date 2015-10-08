package org.tourgune.egistour.controller.api.open;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.egistour.bean.Developer;
import org.tourgune.egistour.facade.DeveloperFacade;
import org.tourgune.egistour.utils.MailUtils;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Controlador público encargado tratar peticiones HTTP relacionadas con la gestion de desarrolladores. 
 */

@Controller
@RequestMapping("/open/developer")
public class DeveloperOpenAPI {

	@Resource
	private DeveloperFacade developerFacade;
	
	@Resource
	private MailUtils mailUtils;

	/**
	 * Controlador que realiza el registro de un desarrollador
	 * 
	 * @param name Nombre del desarrollador
	 * @param password Clave de acceso del desarrollador
	 * @param email Correo electrónico del desarrollador, donde recibirá el link de activación de la cuenta
	 * @return devuelve el identificador de desarrollador creado, sino -1
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody
	Integer doRegistration(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "email", required = false) String email) {
		Developer developer = new Developer();
		developer.setUsername(name);
		developer.setPassword(password);
		developer.setEmail(email);

		return developerFacade.createDeveloper(developer);
	}

	/**Comprueba si el nombre de desarrollador existe en la base de datos
	 * 
	 * @param developername Nombre de usuario del desarrollador
	 * @return devuelve el identificador del desarrollador en caso de existir, sino -1
	 */
	
	@RequestMapping(value = "/exists", method = RequestMethod.GET)
	public @ResponseBody
	Integer existDeveloper(
			@RequestParam(value = "developername", required = false) String developername) {
		return developerFacade.existDeveloper(developername);
	}
	
	
	
	
	// forgot password
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
   	public @ResponseBody Integer forgotPassword(@RequestParam (value = "email", required = true) String email){ 
    
    	String token = developerFacade.getUserTokenByEmail(email);
    
    	if (token != "-1") {
    		mailUtils.sendMail("qrrrify.it3lab@gmail.com",
		  		   		email,
		  		   		"Cambio contrase√±a/Passowrd change/Pasahitza aldaketa", 
		  		   		"Has solicitado restablecer tu contrase√±a. Por favor accede al siguiente enlace para continuar \n\n" +
		  		   		"You have asked for restore your password. Please press this link in order to continue \n\n " +
		  		   		"Zure pasahitza berrezartzea eskatu duzu. Mesedez klikatu esteka jarraitzeko \n\n" +
		    		    "http://http://it3labprojects.tourgune.org/apptrack/restorePassword?token="+token+ "&email=" + email + "\n\n\n\n"+
		    		   		"S√≠guenos en/Follow us on/Jarraitu http://it3lab.tourgune.org"); //TODO dominio, idioma
		}
		return 1; 
   	}
    
    // restore password
    @RequestMapping(value = "/restorePassword", method = RequestMethod.POST)
   	public @ResponseBody Integer restorePassword(@RequestParam (value = "token", required = true) String token,
   												@RequestParam (value = "email", required = true) String email,
   												@RequestParam (value = "pass", required = true) String pass){ 
    	return developerFacade.restorePassword(token, email, pass);
    }
	
    @RequestMapping(value = "/existsEmail", method = RequestMethod.GET)

	public @ResponseBody Integer existsEmail(@RequestParam(value = "email", required = true) String email){ 

		return developerFacade.existsEmail(email); 

	}
    
	
	
	
	
	
	
	
	

}
