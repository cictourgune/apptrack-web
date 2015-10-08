package org.tourgune.egistour.controller.api.secure;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.egistour.bean.Aplicacion;
import org.tourgune.egistour.bean.Visor;
import org.tourgune.egistour.facade.ApplicationFacade;
import org.tourgune.egistour.facade.PuntoFacade;
import org.tourgune.egistour.facade.VariableFacade;
import org.tourgune.egistour.facade.VisorFacade;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Controlador privado encargado tratar peticiones HTTP relacionadas con la gestion de aplicaciones. 
 */

@Controller
@RequestMapping("api/application")
public class ApplicationSecureAPI {

	@Resource
	private ApplicationFacade applicationFacade;

	@Resource
	private PuntoFacade puntoFacade;

	@Resource
	private VariableFacade variableFacade;

	@Resource
	private VisorFacade visorFacade;

	@RequestMapping(value = "/{idaplicacion}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Aplicacion getAplicacion(
			@PathVariable(value = "idaplicacion") Integer idAplicacion) {

		return applicationFacade.getApplication(idAplicacion);
	}

	/**
	 * Controlador que modifica en la base de datos la latitud y longitud
	 * inicial en la que se mostrará el mapa
	 * 
	 * @param idapplication
	 *            identificador de la aplicación que se quiere modificar la
	 *            posición inicial
	 * @param lat
	 *            nueva latitud
	 * @param lng
	 *            nueva longitud
	 * @param zoom
	 *            nuevo zoom del mapa
	 * @return devuelve 1 si se ha realizado con exito, y -1 en caso contrario
	 */
	@RequestMapping(value = "/updateloc", method = RequestMethod.GET)
	public @ResponseBody
	String updateApplication(
			@RequestParam(value = "idapplication", required = false) Integer idapplication,
			@RequestParam(value = "lat", required = false) String lat,
			@RequestParam(value = "lng", required = false) String lng,
			@RequestParam(value = "zoom", required = false) Integer zoom) {

		double latitud = Double.parseDouble(lat.substring(1, lat.length() - 1));
		double longitud = Double
				.parseDouble(lng.substring(1, lng.length() - 1));

		return applicationFacade.updateLocation(idapplication, latitud,
				longitud, zoom);
	}

	/**
	 * Controlador que elimina todos los puntos de una aplicación
	 * 
	 * @param idapplication
	 *            Identificador de la aplicación de la que se van a eliminar los
	 *            puntos
	 * @return devuelve 1 en caso de exito y -1 en caso contrario
	 */
	@RequestMapping(value = "/puntos", method = RequestMethod.DELETE)
	public @ResponseBody
	String deletePuntosApp(
			@RequestParam(value = "idapplication", required = false) Integer idapplication) {

		return puntoFacade.deletePuntos(idapplication);

	}

	/**
	 * Controlador que se usa para crear una aplicación
	 * 
	 * @param name
	 *            Nombre de la aplicación
	 * @param description
	 *            Descripción de la aplicación
	 * @return Devuelve el identificador en caso de éxito, y -1 en caso
	 *         contrario
	 */
	@RequestMapping(value = "/application/", method = RequestMethod.POST)
	public @ResponseBody
	String doAddApplication(
			@RequestParam(value = "application_name", required = false) String name,
			@RequestParam(value = "application_description", required = false) String description,
			@RequestParam(value = "application_origen", required = false) int origen) {

		Aplicacion aplicacion = new Aplicacion();
		aplicacion.setNombreaplicacion(name);
		aplicacion.setDescripcion(description);
		aplicacion.setOrigen(origen);
		String developername = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		return applicationFacade.createApplication(aplicacion, developername);
	}

	/**
	 * Controlador encargado de duplicar la aplicación y sus parametros, excepto
	 * los visores y los puntos del mapa
	 * 
	 * @param name
	 *            Nombre de la nueva aplicación
	 * @param description
	 *            Descripcion de la nueva aplicación
	 * @param idDuplicar
	 *            Identificador de la aplicación que se quiere duplicar
	 * @return Devuelve el identificador de la nueva aplicación creada en caso
	 *         de exito, y -1 en caso contrario
	 */
	@RequestMapping(value = "/duplicar", method = RequestMethod.POST)
	public @ResponseBody
	String doDuplicarAddApplication(
			@RequestParam(value = "du_application_name", required = false) String name,
			@RequestParam(value = "dup_application_description", required = false) String description,
			@RequestParam(value = "idDuplicar", required = false) Integer idDuplicar) {

		Aplicacion aplicacion = new Aplicacion();
		aplicacion.setNombreaplicacion(name);
		aplicacion.setDescripcion(description);
		String developername = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		String idAplicacionNueva = applicationFacade.createApplication(
				aplicacion, developername);
		variableFacade.duplicarVariables(idDuplicar,
				Integer.parseInt(idAplicacionNueva));
		return idAplicacionNueva;
	}

	/**
	 * Controlador encargado de elimicar la aplicación y todas sus variables
	 * 
	 * @param idAplicacion
	 *            Indentificador de la aplicación que se quiere eliminar
	 * @return Devuelve un 1 en caso de éxito y -1 en caso contrario
	 */

	@RequestMapping(value = "/{idAplicacion}", method = RequestMethod.DELETE)
	public @ResponseBody
	String deleteAplicacion(
			@PathVariable(value = "idAplicacion") String idAplicacion) {

		return applicationFacade.deleteApplication(Integer
				.parseInt(idAplicacion));
	}

	/**
	 * Controlador encargado de crear un usuario de tipo visor asignado a una
	 * aplicación
	 * 
	 * @param idaplicacion
	 *            Identificador de la aplicación sobre la cual se quiere añadir
	 *            un visor
	 * @param visor_name
	 *            Nombre del visor
	 * @param visor_pwd
	 *            Contraseña del visor
	 * @param visor_pwd_rep
	 *            Contraseña repetida
	 * @return Devuelve el identificador del visor en caso de éxito y -1 en caso
	 *         contrario
	 */
	@RequestMapping(value = "/visor/{idaplicacion}", method = RequestMethod.POST)
	public @ResponseBody
	String doAddVisor(
			@PathVariable(value = "idaplicacion") Integer idaplicacion,
			@RequestParam(value = "visor_name", required = false) String visor_name,
			@RequestParam(value = "visor_pwd", required = false) String visor_pwd,
			@RequestParam(value = "visor_pwd_rep", required = false) String visor_pwd_rep) {

		Visor visor = new Visor();
		visor.setIdaplicacion(idaplicacion);
		visor.setUsuario(visor_name);
		visor.setPwd(visor_pwd);

		return visorFacade.createVisor(visor);
	}

	/**
	 * Controlador que devuelve la lista de visores de una aplicación en
	 * contrato
	 * 
	 * @param idaplicacion
	 *            Identificador de la aplicación sobre la que se quiere realizar
	 *            la consulta de visores
	 * @return Devuleve la lista de objetos de tipo Visor
	 */
	@RequestMapping(value = "/visor/{idaplicacion}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	java.util.List<Visor> getListaVisores(
			@PathVariable(value = "idaplicacion") Integer idaplicacion) {
		return visorFacade.getVisores(idaplicacion);

	}

	/**
	 * Controlador encargado de eliminar un visor en concreto
	 * 
	 * @param idvisor Indentificador del visor que se quiere eliminar
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	@RequestMapping(value = "/visor/{idvisor}", method = RequestMethod.DELETE)
	public @ResponseBody
	String deleteVisorisores(
			@PathVariable(value = "idvisor") Integer idvisor) {
	 
		return visorFacade.deleteVisor(idvisor);

	}

}
