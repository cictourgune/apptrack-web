package org.tourgune.egistour.facade;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.bean.Developer;
import org.tourgune.egistour.bean.Visor;
import org.tourgune.egistour.dao.DeveloperDao;
import org.tourgune.egistour.dao.VisorDao;
import org.tourgune.egistour.utils.AppTrackUtils;
import org.tourgune.egistour.utils.MailUtils;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service("developerFacade")
@Transactional
public class DeveloperFacade {

	@Resource
	private DeveloperDao developerDao;

	@Resource
	private VisorDao visorDao;

	@Resource
	private MailUtils mailUtils;

	
	/**
	 * Método encargado de crear un desarrollador
	 * 
	 * @param developer Objeto de tipo Developer que contiene la información del desarrollador a crear
	 * @return Devuelve el identificador del desarrollador en caso de éxito y -1 en caso contrario
	 */
	public Integer createDeveloper(Developer developer) {
		String token = developerDao.createDeveloper(developer);
		if (token != "-1") {
			mailUtils
					.sendMail(
							"apptrack.it3lab@gmail.com",
							developer.getEmail().trim(),
							"Welcome to apptrack!!!",
							"Hello "
									+ developer.getDevname()
									+ ", \n\nClick this link and start using apptrack \n\n "
									+ AppTrackUtils.BASEURL
									+ "/validationToken?token=" + token);
		}
		return 1;
	}

	
	public Integer validateDeveloper(String token) {
		// ver si existe
		if (developerDao.existsToken(token)) {
			// activar cuenta
			return developerDao.activateDeveloper(token);//
		}
		return -1;
	}
	/**
	 * Metodo que devuelve el identificador del desarrollador
	 * 
	 * @param developername Nombre del desarrollador
	 * @return Devuelve el identificador del desarrollador en caso de éxito y -1 en caso contrario
	 */
	public Integer getDepeloperID(String developername) {

		return developerDao.getDeveloperID(developername);

	}

	/**
	 * Método que comprueba si el nombre del desarrollador éxiste
	 * 
	 * @param developername Nombre del desarrollador
	 * @return Devuelve el identificador del desarrollador en caso de que exista y un -1 en caso contrario
	 */
	public Integer existDeveloper(String developername) {

		Visor visor = visorDao.getVisor(developername);

		if (visor != null && visor.getIdvisor() > 0)
			return visor.getIdvisor();
		else
			return developerDao.existDeveloper(developername);
	}

	/**
	 * Este método, dado un desarrollador en concreto devuelve su token
	 * 
	 * @param developername Nombre del desarrollador
	 * @return Devuelve el token del desarrollador
	 */
	public String getDeveloperToken(String developername) {
		return developerDao.getDeveloperToken(developername);
	}


	
	
	
	public String getUserTokenByEmail(String email){ 

		return developerDao.getUserTokenByEmail(email);

	}
		
	public Integer restorePassword(String token, String email, String pass){  
		return developerDao.restorePassword(token, email, pass);
	}
	
	public Integer validateTokenEmail (String token, String email) {
		return developerDao.validateTokenEmail(token, email);
	}
	
	
	public Integer existsEmail(String email){ 

		return developerDao.existsEmail(email); 

	} 
	
	
	
	
	
}
