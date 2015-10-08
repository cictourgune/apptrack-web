package org.tourgune.egistour.controller.api.secure;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.tourgune.egistour.facade.UsuarioFacade;



/**
 * AppTrack
 *
 * Created by CICtourGUNE on 29/01/2015.
 * Copyright (c) 2015 CICtourGUNE. All rights reserved.
 * 
 * Controlador privado encargado tratar peticiones HTTP relacionadas con la gestion de aplicaciones. 
 */

@Controller
@RequestMapping("api/usuario")
public class UsuarioSecureAPI {

	@Resource
	private UsuarioFacade usuarioFacade;

	
	@RequestMapping(value = "/totales/{idaplicacion}", method = RequestMethod.GET)
	public @ResponseBody int getTotalUsers(	@PathVariable(value = "idaplicacion") Integer idAplicacion) {
		return usuarioFacade.getTotalUsers(idAplicacion);
	}
	
	@RequestMapping(value = "/recurrentes", method = RequestMethod.GET)
	public @ResponseBody int getReturningUsers(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getReturningUsers(idAplicacion, anio, mes);
	}
	
	@RequestMapping(value = "/nuevos", method = RequestMethod.GET)
	public @ResponseBody int getNewUsers(	
										@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
										@RequestParam(value = "anio", required = true) int anio,
										@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getNewUsers(idAplicacion, anio, mes);
	}
	
	@RequestMapping(value = "/nuevosyrec", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getNewReturningUsers(	
										@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
										@RequestParam(value = "anio", required = true) int anio,
										@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getNewReturningUsers(idAplicacion, anio, mes);
	}
	
	@RequestMapping(value = "/puntosUsuariosPorMes", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getPointsUsersPerMonth(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getPointsUsersPerMonth(idAplicacion, anio, mes);
	}
	
	@RequestMapping(value = "/puntosUsuariosPorSemana", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getPointsUsersPerWeek(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getPointsUsersPerWeek(idAplicacion, anio, mes);
	}
	
	@RequestMapping(value = "/puntosUsuariosPorHora", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getPointsUsersPerHour(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getPointsUsersPerHour(idAplicacion, anio, mes);
	}
	
	@RequestMapping(value = "/rankingDescargas", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getDownloadsRanking(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getDownloadsRanking(idAplicacion, anio, mes);
	}
	
	@RequestMapping(value = "/usuariosNoEnDestino", method = RequestMethod.GET)
	public @ResponseBody int getUsersNotInDestination(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getUsersNotInDestination(idAplicacion, anio, mes);
	}
	@RequestMapping(value = "/rankingMunicipios", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getTownsRanking(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getTownsRanking(idAplicacion, anio, mes);
	}
	@RequestMapping(value = "/unMunicipio", method = RequestMethod.GET)
	public @ResponseBody int getUsersPerOneTownAndMonth(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes,
												@RequestParam(value = "idmunicipio", required = true) int idmunicipio) {
		return usuarioFacade.getUsersPerOneTownAndMonth(idAplicacion, anio, mes, idmunicipio);
	}
	@RequestMapping(value = "/usuariosTotMunicipios", method = RequestMethod.GET)
	public @ResponseBody int getTotalUsersOfTowns(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getTotalUsersOfTowns(idAplicacion, anio, mes);
	}
	@RequestMapping(value = "/rankingComunidades", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getRegionsRanking(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getRegionsRanking(idAplicacion, anio, mes);
	}
	@RequestMapping(value = "/unaComunidad", method = RequestMethod.GET)
	public @ResponseBody int getUsersPerOneRegionAndMonth(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes,
												@RequestParam(value = "idcomunidad", required = true) int idcomunidad) {
		return usuarioFacade.getUsersPerOneRegionAndMonth(idAplicacion, anio, mes, idcomunidad);
	}
	@RequestMapping(value = "/usuariosTotComunidades", method = RequestMethod.GET)
	public @ResponseBody int getTotalUsersOfRegions(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion,
												@RequestParam(value = "anio", required = true) int anio,
												@RequestParam(value = "mes", required = true) int mes) {
		return usuarioFacade.getTotalUsersOfRegions(idAplicacion, anio, mes);
	}
	@RequestMapping(value = "/anios", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getYears(	
												@RequestParam(value = "idaplicacion", required = true) int idAplicacion) {
		System.out.println("");
		return usuarioFacade.getYears(idAplicacion);
	}
}
