package org.tourgune.egistour.facade;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.bean.Visor;
import org.tourgune.egistour.dao.DeveloperDao;
import org.tourgune.egistour.dao.VisorDao;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service("visorFacade")
@Transactional
public class VisorFacade {

	@Resource
	private VisorDao visorDao;

	@Resource
	private DeveloperDao developerDao;

	/**
	 * Método que sirve para crear un visor
	 * 
	 * @param visor Objeto de tipo visor
	 * @return Devuelce el identificador del visor en caso de éxito y -1 en caso constario
	 */
	public String createVisor(final Visor visor) {
		// miro tambien que no existan usuarios de tipo developer
		Integer idDeveloper = developerDao.existDeveloper(visor.getUsuario());

		if (idDeveloper > 0)
			return "-1";
		else {
			Visor visorAux = visorDao.getVisor(visor.getUsuario());

			if (visorAux == null)
				return visorDao.createVisor(visor);
			else
				return "-1";
		}

	}
	/**
	 * Método qeu recupera la lista de visores de una determinada aplicación
	 * 
	 * @param idaplicacion Identificador de la aplicación de la que se quieren recuperar los visores
	 * @return
	 */
	public List getVisores(Integer idaplicacion) {
		return visorDao.getVisores(idaplicacion);
	}
	/**
	 * Método que sirve para eliminar un determinado visor
	 * 
	 * @param idVisor Identificador del visor que se quiere eliminar
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String deleteVisor(Integer idvisor) {
		return visorDao.deleteVisor(idvisor);
	}

	/**
	 * Método que devuelve mediante un objeto de tipo Visor la información de un visor
	 * 
	 * @param usuario Nombre del visor
	 * @return Devuelve el objeto de tipo Visor
	 */
	public Visor getVisor(String usuario) {
		return visorDao.getVisor(usuario);
	}
	
	public String updateLocation(Integer idVisor, double lat, double lng,
			int zoom) {
		return visorDao.updateLocation(idVisor, lat, lng, zoom);
	}

}
