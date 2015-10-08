package org.tourgune.egistour.controller.api.secure;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.egistour.bean.Opcion;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.facade.VariableFacade;



/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Controlador privado encargado tratar peticiones HTTP relacionadas con la gestion de variables. 
 */


@Controller
@RequestMapping("api/variable")
public class VariableSecureAPI {


	@Resource
	private VariableFacade variableFacade;


	/**
	 * Controlador encargado de añadir una variable de tipo Integer
	 * 
	 * @param tipo Tipo de variable, en este caso 1
	 * @param idaplicacion Identificador de la aplicación que se quiere añadir la variable
	 * @param nombre Nombre de la variable
	 * @param chk_obli Parametro que indica si la variable es obligatoria o no
	 * @param slider_fill_min Al ser una variable de tipo numérico, en este parametro se indicará el valor mínimo de la variable
	 * @param slider_fill_max Al ser una variable de tipo numérico, en este parametro se indicará el valor máximo de la variable
	 * @return Devuelve el identificador de la variable creada en caso de éxito y -1 en caso contrario
	 */
	@RequestMapping(value = "/nuevaint", method = RequestMethod.POST)
	public @ResponseBody
	String doVariableInt(
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "idaplicacion", required = false) Integer idaplicacion,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "chk_obligatorio", required = false) boolean chk_obli,
			@RequestParam(value = "slider_fill_min", required = false) Integer slider_fill_min,
			@RequestParam(value = "slider_fill_max", required = false) Integer slider_fill_max

	) {
		String resultado = "-1";
		Variable variable = new Variable();
		variable.setNombrevariable(nombre);
		variable.setIdaplicacion(idaplicacion);
		variable.setIdtipo(1);
		variable.setMin((double) (slider_fill_min));
		variable.setMax((double) (slider_fill_max));
		variable.setObligatorio(chk_obli);
		resultado = variableFacade.createIntVariable(variable);

		return resultado;
	}

	/**
	 * Controlador encargado de añadir una variable de tipo Fecha
	 * 
	 * @param tipo Tipo de variable, en este caso 1
	 * @param idaplicacion Identificador de la aplicación que se quiere añadir la variable
	 * @param nombre Nombre de la variable
	 * @param fecha_desde Al ser una variable de tipo fecha, en este parametro se indicará el valor mínimo de la variable de tipo fecha
	 * @param fecha_hasta Al ser una variable de tipo numérico, en este parametro se indicará el valor mñaximo de la variable de tipo fecha
	 * @param chk_obli Parametro que indica si la variable es obligatoria o no
	 * @return Devuelve el identificador de la variable creada en caso de éxito y -1 en caso contrario
	 */
	@RequestMapping(value = "/nuevadate", method = RequestMethod.POST)
	public @ResponseBody
	String doVariableDate(
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "idaplicacion", required = false) Integer idaplicacion,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "fecha_desde", required = false) String fecha_desde,
			@RequestParam(value = "fecha_hasta", required = false) String fecha_hasta,
			@RequestParam(value = "chk_obligatorio", required = false) boolean chk_obli

	) {
		String resultado = "-1";
		Variable variable = new Variable();
		variable.setNombrevariable(nombre);
		variable.setIdaplicacion(idaplicacion);
		variable.setIdtipo(3);
		variable.setFechadesde(fecha_desde);
		variable.setFechahasta(fecha_hasta);
		variable.setObligatorio(chk_obli);
		resultado = variableFacade.createFechaVariable(variable);

		return resultado;
	}
	
	/** Controlador encargado de añadir una variable de tipo Opción
	 * 
	 * @param tipo  Tipo de variable, en este caso 2
	 * @param idaplicacion Identificador de la aplicación que se quiere añadir la variable
	 * @param nombre Nombre de la variable
	 * @param opciones Variable de tipo texto donde las diferentes opciones están divididas por ";"
	 * @param chk_obli  Parametro que indica si la variable es obligatoria o no
	 * @param chk_mul_option  Parametro que indica si la variable permite la opción multiple o no
	 * @return Devuelve el identificador de la variable creada en caso de éxito y -1 en caso contrario
	 */

	@RequestMapping(value = "/nuevaoption", method = RequestMethod.POST)
	public @ResponseBody
	String doVariableOption(
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "idaplicacion", required = false) Integer idaplicacion,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "opciones", required = false) String opciones,
			@RequestParam(value = "chk_obligatorio", required = false) boolean chk_obli,
			@RequestParam(value = "chk_mul_option", required = false) boolean chk_mul_option

	) {
		String resultado = "-1";
		Variable variable = new Variable();
		variable.setNombrevariable(nombre);
		variable.setIdaplicacion(idaplicacion);
		variable.setIdtipo(2);
		variable.setMultiopcion(chk_mul_option);
		variable.setObligatorio(chk_obli);

		resultado = variableFacade.createOpcionesVariable(variable, opciones);

		return resultado;
	}
	
	/** Controlador encargado de añadir una variable de tipo Opción
	 * 
	 * @param tipo  Tipo de variable, en este caso 4
	 * @param idaplicacion Identificador de la aplicación que se quiere añadir la variable
	 * @param nombre Nombre de la variable
	 * @param chk_obli Parametro que indica si la variable es obligatoria o no
	 * @param slider_fill_min Al ser una variable de tipo decimal, en este parametro se indicará el valor mínimo de la variable
	 * @param slider_fill_max Al ser una variable de tipo decimal, en este parametro se indicará el valor máximo de la variable
	 * @return Devuelve el identificador de la variable creada en caso de éxito y -1 en caso contrario
	 */

	@RequestMapping(value = "/nuevadecimal", method = RequestMethod.POST)
	public @ResponseBody
	String doVariableDecimal(
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "idaplicacion", required = false) Integer idaplicacion,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "chk_obligatorio", required = false) boolean chk_obli,
			@RequestParam(value = "slider_fill_min", required = false) Double slider_fill_min,
			@RequestParam(value = "slider_fill_max", required = false) Double slider_fill_max

	) {
		String resultado = "-1";
		Variable variable = new Variable();
		variable.setNombrevariable(nombre);
		variable.setIdaplicacion(idaplicacion);
		variable.setIdtipo(4);
		variable.setMin(slider_fill_min);
		variable.setMax(slider_fill_max);
		variable.setObligatorio(chk_obli);
		resultado = variableFacade.createDecimalVariable(variable);

		return resultado;
	}

	/**
	 * Controlador encargado de eliminar una variable
	 * 
	 * @param idVariable Identificador de la variable que se quiere eliminar
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	@RequestMapping(value = "/{idVariable}", method = RequestMethod.DELETE)
	public @ResponseBody
	String deleteVariable(@PathVariable(value = "idVariable") String idVariable) {

		return variableFacade.deleteVariable(Integer.parseInt(idVariable));
	}

	
	/**
	 * Controlador encargado de consultar la información de una variable
	 * 
	 * @param idVariable Identificador de la variable que se quiere consultar
	 * @return Devuelve un objeto de tipo Variable
	 */
	@RequestMapping(value = "/{idVariable}", method = RequestMethod.GET)
	public @ResponseBody
	Variable getVariable(@PathVariable(value = "idVariable") String idVariable) {

		return variableFacade.getVariable(Integer.parseInt(idVariable));
	}

	/**
	 * Controlador que devuelve la lista de variables de una aplicación en concreto
	 * 
	 * @param idAplicacion Identificador de la aplicación que se quiere consultar
	 * @return Devuelve la lista de Variables de la aplicación seleccionada
	 */
	@RequestMapping(value = "/variables", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	java.util.List<Variable> getListaVariables(
			@RequestParam(value = "idAplicacion", required = false) int idAplicacion

	) {
		return variableFacade.getVariables(idAplicacion);

	}

	/**
	 * Controlador encargado de devolver la lista de opciones de una variable de tipo opción
	 * 
	 * @param idVariable Identificador de la variable que se quiere consultar las opciones
	 * @return Devuelve la lista de opciones de la variable seleccionada
	 */
	@RequestMapping(value = "/opciones", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	java.util.List<Opcion> getListaOpciones(
			@RequestParam(value = "idVariable", required = false) int idVariable

	) {
		return variableFacade.getOpciones(idVariable);

	}

	/**
	 *  Controlador encargado de modificar una variable de tipo Itenger
	 *  
	 * @param idVariable Identificador de la variable que se quiere modificar
	 * @param tipo Tipo de la variable que se quiere modificar
	 * @param idaplicacion Aplicación de la variable que se quiere modificar
	 * @param nombre Nombre de la variable
	 * @param chk_int Parámetro que indica si la variable es obligatoria o no
	 * @param slider_fill_min Valor mínimo de la variable
	 * @param slider_fill_max Valor máximo de la variable
	 * @return Devuelve un 1 en caso de éxito y -1 en caso contrario
	 * 
	 */
	
	@RequestMapping(value = "/updateint/{idVariable}", method = RequestMethod.POST)
	public @ResponseBody
	String updateVariableInt(
			@PathVariable(value = "idVariable") String idVariable,
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "idaplicacion", required = false) Integer idaplicacion,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "chk_obligatorio_int", required = false) boolean chk_int,
			@RequestParam(value = "slider_fill_min", required = false) Integer slider_fill_min,
			@RequestParam(value = "slider_fill_max", required = false) Integer slider_fill_max

	) {
		String resultado = "-1";
		Variable variable = new Variable();
		variable.setNombrevariable(nombre);
		variable.setIdaplicacion(idaplicacion);
		variable.setMin((double) ((slider_fill_min)));
		variable.setMax((double) (slider_fill_max));
		variable.setIdvariable(Integer.parseInt(idVariable));
		resultado = variableFacade.updateVariableInt(variable);

		return resultado;
	}

	/** 
	 * Controlador encargado de modificar una variable de tipo fecha
	 * 
	 * @param idVariable Identificador de la variable que se quiere modificar
	 * @param tipo Tipo de la variable que se quiere modificar
	 * @param idaplicacion  Aplicación de la variable que se quiere modificar
	 * @param nombre Nombre de la variable
	 * @param fecha_desde Fecha mínima de la variable
	 * @param fecha_hasta Fecha máxima de la variable
	 * @param chk_date  Parámetro que indica si la variable es obligatoria o no 
	 * @return Devuelve un 1 en caso de éxito y -1 en caso contrario
	 */
	@RequestMapping(value = "/updatedate/{idVariable}", method = RequestMethod.POST)
	public @ResponseBody
	String updateVariableDate(
			@PathVariable(value = "idVariable") String idVariable,
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "idaplicacion", required = false) Integer idaplicacion,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "fecha_desde", required = false) String fecha_desde,
			@RequestParam(value = "fecha_hasta", required = false) String fecha_hasta,
			@RequestParam(value = "chk_obligatorio_date", required = false) boolean chk_date

	) {
		String resultado = "-1";
		Variable variable = new Variable();
		variable.setNombrevariable(nombre);
		variable.setIdaplicacion(idaplicacion);
		variable.setFechadesde(fecha_desde);
		variable.setFechahasta(fecha_hasta);
		variable.setIdvariable(Integer.parseInt(idVariable));
		resultado = variableFacade.updateVariableDate(variable);

		return resultado;
	}

	/**
	 * Controlador encargado de modificar una variable de tipo opcione
	 * 
	 * @param idVariable Identificador de la variable que se quiere modificar
	 * @param tipo Tipo de la variable que se quiere modificar
	 * @param idaplicacion Aplicación de la variable que se quiere modificar
	 * @param nombre Nombre de la variable
	 * @param opciones Nueva lista de opciones de la variable. Separadas por ";"
	 * @param chk_obli  Parámetro que indica si la variable es obligatoria o no 
	 * @param chk_mul_option Parámetro que indica si la variable es multiopción o no 
	 * @return
	 */
	@RequestMapping(value = "/updateoption/{idVariable}", method = RequestMethod.POST)
	public @ResponseBody
	String updateVariableOption(
			@PathVariable(value = "idVariable") String idVariable,
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "idaplicacion", required = false) Integer idaplicacion,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "opciones", required = false) String opciones,
			@RequestParam(value = "chk_obligatorio", required = false) boolean chk_obli,
			@RequestParam(value = "chk_mul_option", required = false) boolean chk_mul_option

	) {
		String resultado = "-1";
		Variable variable = new Variable();
		variable.setNombrevariable(nombre);
		variable.setIdaplicacion(idaplicacion);
		variable.setIdtipo(2);

		variable.setMultiopcion(chk_mul_option);

		variable.setIdvariable(Integer.parseInt(idVariable));

		String[] listaopciones = opciones.split(";");
		java.util.List<Opcion> opc = new ArrayList<Opcion>();
		for (int i = 0; i < listaopciones.length; i++) {
			Opcion opt = new Opcion();
			opt.setIdvariable(Integer.parseInt(idVariable));
			opt.setNombreopcion(listaopciones[i]);
			opc.add(opt);
		}
		variable.setOpciones(opc);
		resultado = variableFacade.updateVariableOption(variable);

		return resultado;
	}

	/**
	 * Controlador encargado de modificar una variable de tipo decimal
	 * 
	 * @param idVariable Identificador de la variable que se quiere modificar
	 * @param tipo Tipo de la variable que se quiere modificar
	 * @param idaplicacion Aplicación de la variable que se quiere modificar
	 * @param nombre Nombre de la variable
	 * @param chk_int  Parámetro que indica si la variable es obligatoria o no 
	 * @param slider_fill_min Valor mínimo de la variable
	 * @param slider_fill_max Valor máximo de la variable
	 * @return
	 */
	@RequestMapping(value = "/updatedecimal/{idVariable}", method = RequestMethod.POST)
	public @ResponseBody
	String updateVariableDecimal(
			@PathVariable(value = "idVariable") String idVariable,
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "idaplicacion", required = false) Integer idaplicacion,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "chk_obligatorio_int", required = false) boolean chk_int,
			@RequestParam(value = "slider_fill_min", required = false) Double slider_fill_min,
			@RequestParam(value = "slider_fill_max", required = false) Double slider_fill_max

	) {
		String resultado = "-1";
		Variable variable = new Variable();
		variable.setNombrevariable(nombre);
		variable.setIdaplicacion(idaplicacion);
		variable.setMin(slider_fill_min);
		variable.setMax(slider_fill_max);
		variable.setIdvariable(Integer.parseInt(idVariable));
		resultado = variableFacade.updateVariableDecimal(variable);

		return resultado;
	}
}
