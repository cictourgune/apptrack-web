package org.tourgune.egistour.controller.view.open;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tourgune.egistour.facade.DeveloperFacade;

/**
 * AppTrack
 * 
 * Created by CICtourGUNE on 10/04/13. Copyright (c) 2013 CICtourGUNE. All
 * rights reserved.
 * 
 * Controlador público encargado tratar peticiones HTTP relacionadas con la
 * navegación entre pantallas
 */

@Controller
public class OpenViewController {

	@Resource
	private DeveloperFacade developerFacade;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index_2() {
		return "index";
	}

	@RequestMapping(value = "/registro", method = RequestMethod.GET)
	public String registro() {
		return "registro";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "logout";
	}

	@RequestMapping(value = "/validation", method = RequestMethod.GET)
	public String validation() {
		return "validation";
	}

	/**
	 * Controlador encargado de validar un desarrollador
	 * 
	 * @param token
	 *            Token asignado a los usuarios para realizar la validación
	 * @return
	 */
	@RequestMapping(value = "/validationToken", method = RequestMethod.GET)
	public ModelAndView validateUser(
			@RequestParam(value = "token", required = true) String token) {

		ModelAndView mv = new ModelAndView();
		// si existe el token, acceder a dashboard
		if (developerFacade.validateDeveloper(token) == 1) {
			mv.setViewName("login");
		} else {
			mv.setViewName("validation");
		}
		return mv;

	}

	/**
	 * Controlador encargado de redireccionar a la página de privacidad
	 * 
	 * @return
	 */
	@RequestMapping(value = "/privacidad", method = RequestMethod.GET)
	public String privacidad() {
		return "privacidad";
	}
	
	
	

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String forgotPassword() {
		return "forgotPassword";
	}

	@RequestMapping(value = "/confirmForgotPassword", method = RequestMethod.GET)
	public String confirmForgotPassword() {
		return "confirmForgotPassword";
	}
	
	

	@RequestMapping(value = "/restorePassword", method = RequestMethod.GET)
	public ModelAndView restorePassword(
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "email", required = true) String email) {

		ModelAndView mv = new ModelAndView();
		// si existe el token, acceder a dashboard
		if (developerFacade.validateTokenEmail(token, email) == 1) {
			mv.setViewName("restorePassword");
		} else {
			mv.setViewName("restorePasswordError");
		}
		return mv;

	}

}
