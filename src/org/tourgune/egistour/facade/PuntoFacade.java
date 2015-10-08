package org.tourgune.egistour.facade;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.bean.Latlong;
import org.tourgune.egistour.bean.Punto;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.dao.PuntoDao;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service("puntoFacade")
@Transactional
public class PuntoFacade {

	@Resource
	private PuntoDao puntoDao;

	
	/**
	 * Método de devuelve la lista de puntos que cumplen con el filtro de búsqueda
	 * 
	 * @param listaValores Lista que contiene los valores de la busqueda
	 * @param idaplicacion Identificador de la aplicación de la cual se quieren consultar los puntos
	 * @param fecha_desde Filtro de tiempo de la búsqueda
	 * @param fecha_hasta Filtro de tiempo de la búsqueda
	 * @return
	 */
	
	public List<Punto> getPuntos(java.util.List<Valor> listaValores,
			int idaplicacion, String fecha_desde, String fecha_hasta, int horaMin, int horaMax) {
		return puntoDao.getPuntos(listaValores, idaplicacion, fecha_desde,
				fecha_hasta,  horaMin,  horaMax);
	}
	
	
	
	public List<Latlong> getLatlongs(java.util.List<Valor> listaValores,
			int idaplicacion, String fecha_desde, String fecha_hasta, int horaMin, int horaMax) {
		return puntoDao.getLatlongs(listaValores, idaplicacion, fecha_desde,
				fecha_hasta,  horaMin,  horaMax);
	}
	
	
	
	
	public int getUsuariosPuntos(java.util.List<Valor> listaValores,
			int idaplicacion, String fecha_desde, String fecha_hasta, int horaMin, int horaMax) {
		return puntoDao.getUsuariosPuntos(listaValores, idaplicacion, fecha_desde,
				fecha_hasta,  horaMin,  horaMax);
	}

	/**
	 * Método que se utiliza para eliminar los puntos de la aplicación
	 * 
	 * @param idaplicacion Identificador de la aplicación que se quieren eliminar los puntos
	 * @return
	 */
	
	
	
	public String deletePuntos(int idaplicacion) {
		return puntoDao.deletePuntos(idaplicacion);
	}



	public List<Latlong> getUsersLocation(int idaplicacion, String fecha_desde, String fecha_hasta,
			int horaMin, int horaMax, String usersID) {
		System.out.println("Dentro del facade");
		return puntoDao.getUsersLocation(idaplicacion, fecha_desde,
				fecha_hasta,  horaMin,  horaMax, usersID);
	
	}
}
