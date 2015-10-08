package org.tourgune.egistour.controller.api.secure;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.egistour.bean.Latlong;
import org.tourgune.egistour.bean.Punto;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.facade.PuntoFacade;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Controlador privado encargado tratar peticiones HTTP relacionadas con la gestion de puntos. 
 */

@Controller
@RequestMapping("api/puntos")
public class PuntosSecureAPI {

	@Resource
	private PuntoFacade puntoFacade;

	/**
	 * Controlador encargado de consultar la lista de puntos entre fecha_desde y fecha_hasta, de una aplicación en concreto
	 * 
	 * @param jsonString JSON de la lista de valores. Esta variable contiene los parametros de búsqueda
	 * @param idaplicacion Identificador de la aplicación que se quiere consultar
	 * @param fecha_desde Fecha desde la cual se quiere realizar la consulta
	 * @param fecha_hasta Desde hasta la cual se quiere realizar la consulta
	 * @return Devuelve una lista de Punto que cumplen las condiciones de la busqueda
	 */
	@RequestMapping(value = "/puntos", method = RequestMethod.POST)
	public @ResponseBody
	java.util.List<Punto> getListaPuntos(
			@RequestBody String jsonString,
			@RequestParam(value = "idaplicacion", required = false) int idaplicacion,
			@RequestParam(value = "horaMin", required = false) int horaMin,
			@RequestParam(value = "horaMax", required = false) int horaMax,
			@RequestParam(value = "fecha_desde", required = false) String fecha_desde,
			@RequestParam(value = "fecha_hasta", required = false) String fecha_hasta) {

		java.util.List<Punto> listaPuntos = null;


		
		try {
			final Gson gson = new Gson();
			final Type tipoListaValor = new TypeToken<java.util.List<Valor>>() {
			}.getType();
			final java.util.List<Valor> valores = gson.fromJson(jsonString,
					tipoListaValor);

			System.out.println("Llegada al server: " + System.currentTimeMillis());
			
			listaPuntos = puntoFacade.getPuntos(valores, idaplicacion,
					fecha_desde, fecha_hasta, horaMin,  horaMax);
			
//			System.out.println("Salida del server: " + System.currentTimeMillis());
		
			
			return listaPuntos;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
				
		return listaPuntos;

	}

	
	
	
	
	@RequestMapping(value = "/latlongs", method = RequestMethod.POST)
	public @ResponseBody
	List<Latlong> getListaLatlongs(
			@RequestBody String jsonString,
			@RequestParam(value = "idaplicacion", required = false) int idaplicacion,
			@RequestParam(value = "horaMin", required = false) int horaMin,
			@RequestParam(value = "horaMax", required = false) int horaMax,
			@RequestParam(value = "fecha_desde", required = false) String fecha_desde,
			@RequestParam(value = "fecha_hasta", required = false) String fecha_hasta) {

		java.util.List<Latlong> listaLatlongs = null;


		
		try {
			final Gson gson = new Gson();
			final Type tipoListaValor = new TypeToken<java.util.List<Valor>>() {
			}.getType();
			final java.util.List<Valor> valores = gson.fromJson(jsonString,
					tipoListaValor);

//			System.out.println("Llegada al server: " + System.currentTimeMillis());
			
			listaLatlongs = puntoFacade.getLatlongs(valores, idaplicacion,
					fecha_desde, fecha_hasta, horaMin,  horaMax);
			
//			System.out.println("Salida del server: " + System.currentTimeMillis());
		
			
			return listaLatlongs;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
				
		return listaLatlongs;

	}
	
	@RequestMapping(value = "/usersLocation", method = RequestMethod.POST)
	public @ResponseBody
	List<Latlong> getLocationUsers(
			@RequestParam(value = "idaplicacion", required = false) int idaplicacion,
			@RequestParam(value = "horaMin", required = false) int horaMin,
			@RequestParam(value = "horaMax", required = false) int horaMax,
			@RequestParam(value = "fecha_desde", required = false) String fecha_desde,
			@RequestParam(value = "fecha_hasta", required = false) String fecha_hasta,
			@RequestParam(value = "usersID", required = false) String usersID) {

		java.util.List<Latlong> listaUsersLocation = null;
		System.out.println("dentro de secure api");

		
		try {
			
			
			listaUsersLocation = puntoFacade.getUsersLocation(idaplicacion,
					fecha_desde, fecha_hasta, horaMin,  horaMax, usersID);

			return listaUsersLocation;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
				
		return listaUsersLocation;

	}

	
	@RequestMapping(value = "/usuarios", method = RequestMethod.POST)
	public @ResponseBody
	int getUsuariosPuntos(
			@RequestBody String jsonString,
			@RequestParam(value = "idaplicacion", required = false) int idaplicacion,
			@RequestParam(value = "horaMin", required = false) int horaMin,
			@RequestParam(value = "horaMax", required = false) int horaMax,
			@RequestParam(value = "fecha_desde", required = false) String fecha_desde,
			@RequestParam(value = "fecha_hasta", required = false) String fecha_hasta) {

		int numUsuarios = 99;

		
		try {
			final Gson gson = new Gson();
			final Type tipoListaValor = new TypeToken<java.util.List<Valor>>() {
			}.getType();
			final java.util.List<Valor> valores = gson.fromJson(jsonString,
					tipoListaValor);
			
			
			
			 numUsuarios = puntoFacade.getUsuariosPuntos(valores, idaplicacion,
					fecha_desde, fecha_hasta, horaMin,  horaMax);
			
			
			
			return numUsuarios;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return numUsuarios;

	}
}
