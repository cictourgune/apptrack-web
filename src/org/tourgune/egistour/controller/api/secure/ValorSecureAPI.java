package org.tourgune.egistour.controller.api.secure;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.egistour.bean.Muestra;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.facade.ValorFacade;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Controlador privado encargado tratar peticiones HTTP relacionadas con la gestión de estadísticas. 
 */

@Controller
@RequestMapping("api/valores")
public class ValorSecureAPI {

	@Resource
	private ValorFacade valorFacade;

	/**
	 * Controlador encargado de consultar la lista de puntos entre fecha_desde y fecha_hasta, de una aplicación en concreto
	 * 
	 * @param jsonString JSON de la lista de valores. Esta variable contiene los parametros de búsqueda
	 * @param idaplicacion Identificador de la aplicación que se quiere consultar
	 * @param fecha_desde Fecha desde la cual se quiere realizar la consulta
	 * @param fecha_hasta Desde hasta la cual se quiere realizar la consulta
	 * @return Devuelve una lista de Punto que cumplen las condiciones de la busqueda
	 */
	@RequestMapping(value = "/valores", method = RequestMethod.POST)
	public @ResponseBody
	java.util.List<Valor> getListaDatosEstadisticos(
			@RequestParam(value = "idvariable", required = false) int idvariable,
			@RequestParam(value = "idaplicacion", required = false) int idaplicacion,
			@RequestParam(value = "fecha_desde", required = false) String fecha_desde,
			@RequestParam(value = "fecha_hasta", required = false) String fecha_hasta,
			@RequestParam(value = "tipo", required = false) int tipo) {

		java.util.List<Valor> listaDatosEstadisticos = null;

		try {
			listaDatosEstadisticos = valorFacade.getDatosEstadisticos(idvariable, idaplicacion, fecha_desde, fecha_hasta,tipo);
			return listaDatosEstadisticos;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return listaDatosEstadisticos;

	}
	
	@RequestMapping(value = "/muestra", method = RequestMethod.POST)
	public @ResponseBody
	java.util.List<Muestra> getMuestraEstadisticos(
			@RequestParam(value = "idaplicacion", required = false) int idaplicacion,
			@RequestParam(value = "fecha_desde", required = false) String fecha_desde,
			@RequestParam(value = "fecha_hasta", required = false) String fecha_hasta) {

		java.util.List<Muestra> muestraEstadisticos = null;

		try {
			muestraEstadisticos = valorFacade.getMuestraEstadisticos(idaplicacion, fecha_desde, fecha_hasta);
			return muestraEstadisticos;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return muestraEstadisticos;

	}
	
	

}
