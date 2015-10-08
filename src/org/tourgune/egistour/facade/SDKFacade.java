package org.tourgune.egistour.facade;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.bean.Punto;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.dao.SDKDao;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service("sdkFacade")
@Transactional
public class SDKFacade {

	@Resource
	private SDKDao sdkDao;

	/**
	 * Metodo que comprueba si el token del desarrollador y el de la aplicacion existen y concuerdan.
	 * 
	 * @param devToken Token del desarrollador
	 * @param appToken Token de la aplicacion
	 * @return 1 si los dos tokens son correctos, -1 en caso contrario
	 */
	public Integer login(String devToken, String appToken) {
		return sdkDao.login(devToken, appToken);
	}

	/**
	 * Metodo que almacena un objeto de tipo punto en la base de datos
	 * 
	 * @param point Objeto de tipo punto a insertar en la base de datos
	 * @param idUser Identificador del usuario
	 * @return 1 si el punto es insertado correctamente, -1 en caso contrario
	 */
	public Integer createPoint(Punto point, int idUser) {
		return sdkDao.createPoint(point, idUser);
	}

	/**
	 * Metodo que crea un nuevo usuario
	 * 
	 * @param imei Identificador unico del dispositivo
	 * @param idApp Identificador de la aplicacion
	 * @return El identificador del usuario asignado por la base de datos si se inserta correctamente, 0 en caso contrario
	 */
	public Integer createUser(String imei, int idApp) {
		return sdkDao.createUser(imei, idApp);
	}

	/**
	 * Metodo que comprueba si existe un usuario con ese IMEI
	 * 
	 * @param imei Identificador unico del dispositivo
	 * @param idApp Identificador de la aplicacion
	 * @return El identificador del usuario con el IMEI enviado, -1 en caso contrario
	 */
	public Integer existImei(String imei, int idApp) {
		return sdkDao.existImei(imei, idApp);
	}

	/**
	 * Metodo que almacena un nuevo valor en la base de datos
	 * 
	 * @param value Objeto de tipo value a insertar en la base de datos
	 * @param idUser Identificador del usuario
	 * @return 1 si el valor es insertado correctamente, -1 en caso contrario
	 */
	public Integer createValue(Valor value, int idUser) {
		return sdkDao.createValue(value, idUser);
	}

	/**
	 * Metodo que consulta una variable
	 * 
	 * @param idvariable Identificador de la variable a consultar
	 * @return Objeto de tipo variable almacenado en la base de datos, si no se encuentra, el objeto tendra el campo idTipo = -1 
	 */
	public Variable getVariable(int idvariable) {
		return sdkDao.getVariable(idvariable);

	}
	
	
	
	/**
	 * Metodo que consulta una variable
	 * 
	 * @param idvariable Identificador de la variable a consultar
	 * @return Objeto de tipo variable almacenado en la base de datos, si no se encuentra, el objeto tendra el campo idTipo = -1 
	 */
	public Integer getPlataforma(String appToken) {
		return sdkDao.getPlataforma(appToken);

	}
	
	

	/**
	 * Metodo que consulta si una variable tiene la opcion option
	 * 
	 * @param idvariable Identificador de la variable a consultar
	 * @param option Cadena de caracteres a consultar si existe como opcion de la variable
	 * @return Identificador de la opcion. Si no existe la opcion -1
	 */
	public Integer existOption(int idvariable, String option) {
		return sdkDao.existOption(idvariable, option);
	}

	/**
	 * Metodo que comprueba si un usuario ya ha almacenado un valor para esa variable
	 * 
	 * @param idvariable Identificador de la variable a consultar
	 * @param idUsuario Identificador del usuario
	 * @return Identificador del valor en caso de haberse almacenado con anterioridad. En caso contrario, -1
	 */
	public Integer existValor(int idvariable, int idUsuario) {
		return sdkDao.existValor(idvariable, idUsuario);
	}

	/**
	 * Metodo que modifica un valor de una variable (Se ejecuta tras comprobar que el valor existe mediante la funcion existValor)
	 * 
	 * @param val Objeto de tipo valor que contiene la variable a modificar y el valor a asignar
	 * @param idUsuario Identificador del usuario
	 * @return 1 si el valor es modificado correctamente, -1 en caso contrario
	 */
	public Integer updateValor(Valor val, int idUsuario) {
		return sdkDao.updateValor(val, idUsuario);
	}

	/**
	 * Metodo que almacena el modo de captura de la posicion del dispositivo (GPS / WiFi)
	 * 
	 * @param idApp Identificador de la aplicacion
	 * @param conexion Cadena de caracteres que especifica el modo de captura de la posicion del dispositivo (GPS / WiFi)
	 * @return 1 si el valor es almacenado correctamente, -1 en caso contrario
	 */
	public Integer updateAppConectivity(int idApp, String conexion) {
		return sdkDao.updateAppConectivity(idApp, conexion);
	}

	/**
	 * Metodo que comprueba si se ha almacenado algun valor para la aplicacion idApp
	 * 
	 * @param idApp Identificador de la aplicacion
	 * @return 1 si si se ha almacenado algun valor, -1 en caso contrario
	 */
	public int existAppConectivity(int idApp) {
		return sdkDao.existAppConectivity(idApp);
	}
}
