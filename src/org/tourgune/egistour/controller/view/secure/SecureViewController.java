package org.tourgune.egistour.controller.view.secure;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tourgune.egistour.bean.Aplicacion;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.bean.Visor;
import org.tourgune.egistour.facade.ApplicationFacade;
import org.tourgune.egistour.facade.VariableFacade;
import org.tourgune.egistour.facade.VisorFacade;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Controlador privado encargado tratar peticiones HTTP relacionadas con la navegación entre pantallas
 */

@Controller
@RequestMapping("private")
public class SecureViewController {

	@Resource
	private ApplicationFacade applicationFacade;

	@Resource
	private VariableFacade variableFacade;

	@Resource
	private VisorFacade visorFacade;
	
	/**
	 * Controlador encargado de visualizar el Dashboard
	 * 
	 * @return ModelAndView encargado de redireccionar a la página "dashboard" además de almacenar la información de aplicación y la lista de variables
	 */

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView getDashboard() {

		String developername = SecurityContextHolder.getContext()
				.getAuthentication().getName();

		Set<String> roles = AuthorityUtils
				.authorityListToSet(SecurityContextHolder.getContext()
						.getAuthentication().getAuthorities());

		List listaAplicaciones = applicationFacade
				.getApplications(developername);

		ModelAndView mv = new ModelAndView();
		mv.addObject("listaAplicaciones", listaAplicaciones);

		if (roles.contains("ROLE_REGISTERED")) {

			mv.setViewName("dashboard");
		} else {
			if (roles.contains("ROLE_VISOR")) {
				// cargo los datos de las aplicaciones
				Visor visor = visorFacade.getVisor(SecurityContextHolder
						.getContext().getAuthentication().getName());

				Aplicacion aplicacion = applicationFacade.getApplication(visor
						.getIdaplicacion());

				List<Variable> listaVariables = variableFacade
						.getVariables(visor.getIdaplicacion());

				mv.addObject("aplicacion", aplicacion);
				mv.addObject("listaVariables", listaVariables);
				mv.addObject("numUsuarios", 0);
				mv.setViewName("vista");
			}
		}

		return mv;
	}
	
	
	@RequestMapping(value = "/documentacion", method = RequestMethod.GET)
	public String documentacion() {
		return "documentacion";
	}
	

	/**
	 * Controlador encargado de redireccionar a la pantalla donde se visualiza el mapa
	 * 
	 * @param nombreAplicacion Nombre de la aplicación que se quiere visualizar el mapa
	 * @param idAplicacion Identificador de la aplicación que se quiere visualizar el mapa
	 * @return ModelAndView encargado de redireccionar al mapa, además de cargar en el modelo la lista de variables asociadas a esa aplicación
	 */
	@RequestMapping(value = "/mapa", method = RequestMethod.GET)
	public ModelAndView getMapa(
			@RequestParam(value = "nombreaplicacion", required = true) String nombreAplicacion,
			@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {
		 
		String developername = SecurityContextHolder.getContext()
				.getAuthentication().getName();

		Set<String> roles = AuthorityUtils
				.authorityListToSet(SecurityContextHolder.getContext()
						.getAuthentication().getAuthorities());

		List listaAplicaciones = applicationFacade
				.getApplications(developername);

		ModelAndView mv = new ModelAndView();
		mv.addObject("listaAplicaciones", listaAplicaciones);

		if (roles.contains("ROLE_REGISTERED")) {

			mv.setViewName("dashboard");
		} else {
			if (roles.contains("ROLE_VISOR")) {
				// cargo los datos de las aplicaciones
				Visor visor = visorFacade.getVisor(SecurityContextHolder
						.getContext().getAuthentication().getName());

				Aplicacion aplicacion = applicationFacade.getApplication(visor
						.getIdaplicacion());

				List<Variable> listaVariables = variableFacade
						.getVariables(visor.getIdaplicacion());

				mv.addObject("aplicacion", aplicacion);
				mv.addObject("listaVariables", listaVariables); 
			}
		}
			 
		List<Variable> listaVariables = variableFacade
				.getVariables(idAplicacion);
		mv.addObject("nombreAplicacion", nombreAplicacion);
		mv.addObject("listaVariables", listaVariables);
		mv.addObject("numUsuarios", 0);

		mv.setViewName("mapa");

		return mv;

	}
	
	/**
	 * Controlador encargado de redireccionar a la pantalla donde se visualiza el mapa
	 * 
	 * @param nombreAplicacion Nombre de la aplicación que se quiere visualizar el mapa
	 * @param idAplicacion Identificador de la aplicación que se quiere visualizar el mapa
	 * @return ModelAndView encargado de redireccionar al mapa, además de cargar en el modelo la lista de variables asociadas a esa aplicación
	 */
	@RequestMapping(value = "/mapaAnalysis", method = RequestMethod.GET)
	public ModelAndView getMapaAnalysis(
			@RequestParam(value = "nombreaplicacion", required = true) String nombreAplicacion,
			@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {

		ModelAndView mv = new ModelAndView();
		List<Variable> listaVariables = variableFacade
				.getVariables(idAplicacion);
		mv.addObject("nombreAplicacion", nombreAplicacion);
		mv.addObject("listaVariables", listaVariables);
		mv.addObject("numUsuarios", 0);

		mv.setViewName("mapaAnalysis");

		return mv;

	}
	/**
	 * Controlador encargado de redireccionar a la pantalla donde se visualiza reports
	 * 
	 * @param nombreAplicacion Nombre de la aplicación que se quiere visualizar los informes
	 * @param idAplicacion Identificador de la aplicación que se quiere visualizar el mapa
	 * @return ModelAndView encargado de redireccionar al mapa, además de cargar en el modelo la lista de variables asociadas a esa aplicación
	 */
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView getReport(
			@RequestParam(value = "nombreaplicacion", required = true) String nombreAplicacion,
			@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {

		ModelAndView mv = new ModelAndView();
		
		
		
		String developername = SecurityContextHolder.getContext()
				.getAuthentication().getName();

		Set<String> roles = AuthorityUtils
				.authorityListToSet(SecurityContextHolder.getContext()
						.getAuthentication().getAuthorities());

		List listaAplicaciones = applicationFacade
				.getApplications(developername);

	 
		mv.addObject("listaAplicaciones", listaAplicaciones);

		if (roles.contains("ROLE_REGISTERED")) {

			mv.setViewName("dashboard");
		} else {
			if (roles.contains("ROLE_VISOR")) {
				// cargo los datos de las aplicaciones
				Visor visor = visorFacade.getVisor(SecurityContextHolder
						.getContext().getAuthentication().getName());

				Aplicacion aplicacion = applicationFacade.getApplication(visor
						.getIdaplicacion());

				List<Variable> listaVariables = variableFacade
						.getVariables(visor.getIdaplicacion());

				mv.addObject("aplicacion", aplicacion);
				mv.addObject("listaVariables", listaVariables); 
			}
		}
		
		
		List<Variable> listaVariables = variableFacade
				.getVariables(idAplicacion);
		mv.addObject("nombreAplicacion", nombreAplicacion);
		mv.addObject("listaVariables", listaVariables);
		mv.setViewName("reports");

		return mv;

	}
	
	
	
	/**
	 * Controlador encargado de redireccionar a la pantalla donde se visualizan los informes mensuales
	 * 
	 * @param nombreAplicacion Nombre de la aplicación que se quiere visualizar los informes
	 * @param idAplicacion Identificador de la aplicación que se quiere visualizar el mapa
	 * @return ModelAndView encargado de redireccionar al mapa, además de cargar en el modelo la lista de variables asociadas a esa aplicación
	 */
	@RequestMapping(value = "/informe", method = RequestMethod.GET)
	public ModelAndView getInforme(
			@RequestParam(value = "nombreaplicacion", required = true) String nombreAplicacion,
			@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {

		ModelAndView mv = new ModelAndView();
		
		
		
		String developername = SecurityContextHolder.getContext()
				.getAuthentication().getName();

		Set<String> roles = AuthorityUtils
				.authorityListToSet(SecurityContextHolder.getContext()
						.getAuthentication().getAuthorities());

		List listaAplicaciones = applicationFacade
				.getApplications(developername);

	 
		mv.addObject("listaAplicaciones", listaAplicaciones);

		if (roles.contains("ROLE_REGISTERED")) {

			mv.setViewName("dashboard");
		} else {
			if (roles.contains("ROLE_VISOR")) {
				// cargo los datos de las aplicaciones
				Visor visor = visorFacade.getVisor(SecurityContextHolder
						.getContext().getAuthentication().getName());

				Aplicacion aplicacion = applicationFacade.getApplication(visor
						.getIdaplicacion());

				List<Variable> listaVariables = variableFacade
						.getVariables(visor.getIdaplicacion());

				mv.addObject("aplicacion", aplicacion);
				mv.addObject("listaVariables", listaVariables); 
			}
		}
		
		
		List<Variable> listaVariables = variableFacade
				.getVariables(idAplicacion);
		mv.addObject("nombreAplicacion", nombreAplicacion);
		mv.addObject("listaVariables", listaVariables);
		mv.setViewName("informes");

		return mv;

	}
	
	
	
	@RequestMapping(value = "/informes", method = RequestMethod.GET)
	public ModelAndView getInformesView() { 
		ModelAndView mv = new ModelAndView(); 
		
		String developername = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		
		List listaAplicaciones = applicationFacade
				.getApplications(developername); 
		mv.addObject("listaAplicaciones", listaAplicaciones); 
		
		mv.setViewName("informes"); 
		return mv; 
	}
	
	
	
	/**
	 * Controlador encargado de redireccionar a la página del registro de la aplicación
	 * 
	 * @return Redirección a la vista "registro-application"
	 */

	@RequestMapping(value = "/aplicacion/nueva", method = RequestMethod.GET)
	public String newApplication() {
		return "registro-application";
	}

	/**
	 * Controlador encargado de redireccionar a la página de registro de visores
	 * 
	 * @return Redirección a la vista "registro-visor"
	 */
	@RequestMapping(value = "/aplicacion/visor", method = RequestMethod.GET)
	public String newVisor() {
		return "registro-visor";
	}

	/**
	 * Controlador encargado de redireccionar a la página de duplicar aplicación
	 * 
	 * @return Redirección a la vista "duplicar-application"
	 */
	@RequestMapping(value = "/aplicacion/duplicar", method = RequestMethod.GET)
	public String doDuplicarApplication() {
		return "duplicar-application";
	}
	
	
	/**
	 * Controlador encargado de redireccionar a la página de creación de variables
	 * 
	 * @return Redirección a la vista "variable"
	 */
	@RequestMapping(value = "/variable/nueva", method = RequestMethod.GET)
	public String newVariable() {
		return "variable";
	}

	/**
	 *  Controlador encargado de redireccionar a la página de creación de variable de tipo Integer
	 * 
	 * @param idAplicacion Identificador de la aplicación a la cual se quiere añadir una variable
	 * @return ModelAndView donde se redirecciona a la pantalla de creación de variable de tipo Integer, además almacenar el indentificador de la aplicación en el modelo
	 */
	
	@RequestMapping(value = "/variable/int", method = RequestMethod.GET)
	public ModelAndView newIntVar(
			@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("idAplicacion", idAplicacion);
		mv.setViewName("integer");
		return mv;
	}
	
	/**
	 *  Controlador encargado de redireccionar a la página de creación de variable de tipo Fecha
	 * 
	 * @param idAplicacion Identificador de la aplicación a la cual se quiere añadir una variable 
	 * @return ModelAndView donde se redirecciona a la pantalla de creación de variable de tipo Fecha, además almacenar el indentificador de la aplicación en el modelo
	 */

	@RequestMapping(value = "/variable/date", method = RequestMethod.GET)
	public ModelAndView newDateVar(
			@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("idAplicacion", idAplicacion);
		mv.setViewName("date");
		return mv;
	}

	
	/**
	 *  Controlador encargado de redireccionar a la página de creación de variable de tipo Opcion
	 * 
	 * @param idAplicacion Identificador de la aplicación a la cual se quiere añadir una variable
	 * @return ModelAndView donde se redirecciona a la pantalla de creación de variable de tipo Opcion, además almacenar el indentificador de la aplicación en el modelo
	 */
	
	
	@RequestMapping(value = "/variable/option", method = RequestMethod.GET)
	public ModelAndView newOptionVar(
			@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("idAplicacion", idAplicacion);
		mv.setViewName("option");
		return mv;
	}

	/**
	 *  Controlador encargado de redireccionar a la página de creación de variable de tipo Decimal
	 * 
	 * @param idAplicacion Identificador de la aplicación a la cual se quiere añadir una variable
	 * @return ModelAndView donde se redirecciona a la pantalla de creación de variable de tipo Decimal, además almacenar el indentificador de la aplicación en el modelo
	 */
	
	
	@RequestMapping(value = "/variable/decimal", method = RequestMethod.GET)
	public ModelAndView newDecimalVar(
			@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("idAplicacion", idAplicacion);
		mv.setViewName("decimal");
		return mv;
	}


	/**
	 * Controlador encargado de redireccionar a la página de información
	 * 
	 * @return Redirección a la página de información
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String infoDeveloper() {
		return "informacion";
	}

	
	/**
	 * Controlador encargado de redireccionar a la pagina de comparar dos mapas
	 * 
	 * @return Redirecciona a la página de comparador de mapas
	 */
	@RequestMapping(value = "/mapa/comprador", method = RequestMethod.GET)
	public ModelAndView newDecimalVar() {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("comparador");
		return mv;
	}

}
