package org.tourgune.egistour.facade;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.bean.Aplicacion;
import org.tourgune.egistour.dao.ApplicationDao;
import org.tourgune.egistour.dao.DeveloperDao;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service("applicationFacade")
@Transactional
public class ApplicationFacade {

	
	
	@Resource
	private ApplicationDao applicationDao;

	@Resource
	private DeveloperDao developerDao;

	/**
	 * Este método es el encargado de recuperar la lista de aplicaciones de un determinado desarrollador
	 * 
	 * @param developername Nombre del desarrollador
	 * @return Lista de aplicaciones
	 */
	public List getApplications(String developername) {

		return applicationDao.getApplications(developername);
	}

	/**
	 * Metodo que se usa para crear aplicaciones
	 * 
	 * @param aplicacion Objeto de tipo aplicación que contiene la información de la aplicación que se quiere insertar
	 * @param developername Identificador del desarrollador a la que va asociado la aplicacion
	 * @return Devuelve el identificador de la aplicación en caso de éxito y -1 en caso contrario
	 */
	
	public String createApplication(Aplicacion aplicacion, String developername) {
		Integer idDev = developerDao.getDeveloperID(developername);

		String tokken = applicationDao.createApplication(aplicacion, idDev);

		return tokken;
	}

	
	/**
	 * Este método devuelve la información relativa a una aplicación
	 * 
	 * @param idApplication Identificador de la aplicación que se quiere consultar
	 * @return Devuelve un objeto de tipo Aplicacion
	 */
	public Aplicacion getApplication(Integer idApplication) {
		Aplicacion aplicacion = applicationDao.getApplication(idApplication);

		return aplicacion;
	}
	
	
	/**
	 * Este metodo es el encargado de suprimir una aplicación
	 * 
	 * @param idApplication Identificador de la aplicación que se quiere eliminar
	 * @return Devuelve un 1 en caso de éxito y un -1 en caso contrario
	 */
	public String deleteApplication(Integer idApplication) {
		return applicationDao.deleteApplication(idApplication);
	}
	
	
	/**
	 * Metodo encargado de modificar la localización inicial de un mapa de una aplicación
	 * 
	 * @param idApplication Identificador de la aplicación que se quiere modificar
	 * @param lat Nueva Latitud
	 * @param lng Nueva Longitud
	 * @param zoom Nuevo Zoom
	 * @return Devuelve un 1 en caso de éxito y un -1 en caso contrario
	 */
	public String updateLocation(Integer idApplication, double lat, double lng,
			int zoom) {
		return applicationDao.updateLocation(idApplication, lat, lng, zoom);
	}
}
