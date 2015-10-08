package org.tourgune.egistour.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.dao.UsuarioDao;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 29/01/2015.
 * Copyright (c) 2015 CICtourGUNE. All rights reserved.
 * 
 */
@Service("usuarioFacade")
@Transactional
public class UsuarioFacade {

	
	
	@Resource
	private UsuarioDao usuarioDao;


	public int getTotalUsers(Integer idApplication) {

		return usuarioDao.getTotalUsers(idApplication);
	}

	public int getReturningUsers(Integer idAplicacion, int anio, int mes) {
		return usuarioDao.getReturningUsers(idAplicacion, anio, mes);
	}

	public int getNewUsers(int idAplicacion, int anio, int mes) {
		return usuarioDao.getNewUsers(idAplicacion, anio, mes);
	}

	public List<Map<String, Object>> getPointsUsersPerMonth(int idAplicacion, int anio, int mes) {
		return usuarioDao.getPointsUsersPerMonth(idAplicacion, anio, mes);
	}
	
	public List<Map<String, Object>> getPointsUsersPerWeek(int idAplicacion, int anio, int mes) {
		return usuarioDao.getPointsUsersPerWeek(idAplicacion, anio, mes);
	}
	
	public List<Map<String, Object>> getPointsUsersPerHour(int idAplicacion, int anio, int mes) {
		return usuarioDao.getPointsUsersPerHour(idAplicacion, anio, mes);
	}

	public List<Map<String, Object>> getNewReturningUsers(int idAplicacion, int anio, int mes) {
		return usuarioDao.getNewAndReturningUsersPerMonth(idAplicacion, anio, mes);
	}

	public List<Map<String, Object>> getDownloadsRanking(int idAplicacion,
			int anio, int mes) {
		return usuarioDao.getDownloadsRanking(idAplicacion, anio, mes);
	}

	public int getUsersNotInDestination(int idAplicacion,
			int anio, int mes) {
		return usuarioDao.getUsersNotInDestination(idAplicacion, anio, mes);
	}

	public List<Map<String, Object>> getTownsRanking(int idAplicacion,
			int anio, int mes) {
		return usuarioDao.getTownsRanking(idAplicacion, anio, mes);
	}

	public int getUsersPerOneTownAndMonth(
			int idAplicacion, int anio, int mes, int idmunicipio) {
		return usuarioDao.getUsersPerOneTownAndMonth(idAplicacion, anio, mes,idmunicipio);
	}
	public int getTotalUsersOfTowns(
			int idAplicacion, int anio, int mes) {
		return usuarioDao.getTotalUsersOfTowns(idAplicacion, anio, mes);
	}

	public List<Map<String, Object>> getRegionsRanking(int idAplicacion,
			int anio, int mes) {
		return usuarioDao.getRegionsRanking(idAplicacion, anio, mes);
	}

	public int getUsersPerOneRegionAndMonth(int idAplicacion, int anio,
			int mes, int idcomunidad) {
		return usuarioDao.getUsersPerOneRegionAndMonth(idAplicacion, anio, mes, idcomunidad);
	}
	public int getTotalUsersOfRegions(
			int idAplicacion, int anio, int mes) {
		return usuarioDao.getTotalUsersOfRegions(idAplicacion, anio, mes);
	}

	public List<Map<String, Object>> getYears(int idAplicacion) {
		return usuarioDao.getYears(idAplicacion);
	}
	
}
