package org.tourgune.egistour.facade;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.bean.Opcion;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.dao.VariableDao;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service("variableFacade")
@Transactional
public class VariableFacade {
	@Resource
	private VariableDao variableDao;

	/**
	 * Método que se utiliza para la creación de una variable de tipo Integer
	 * 
	 * @param variable Objeto de tipo variable que contiene la información a insertar
	 * @return devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String createIntVariable(Variable variable) {

		return variableDao.createVariableInt(variable);
	}

	/**
	 * Método que se utiliza para la creación de una variable de tipo Fecha
	 * 
	 * @param variable Objeto de tipo variable que contiene la información a insertar
	 * @return devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String createFechaVariable(Variable variable) {

		return variableDao.createVariableFecha(variable);
	}

	/**
	 * Método que sirve para crear una variable de tipo Opcion
	 * 
	 * @param variable Objeto que contiene la información de la variable
	 * @param opciones Lista de opciones separados por ";"
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String createOpcionesVariable(Variable variable, String opciones) {

		return variableDao.createVariableOpciones(variable, opciones);
	}
	/**
	 * Método que se utiliza para crear una variable de tipo Decimal
	 * 
	 * @param variable Objeto que contiene toda la información de la variable que se quiere insertar
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String createDecimalVariable(Variable variable) {
		return variableDao.createVariableDecimal(variable);
	}
	/**
	 * Método que devuelve la lista de variables de una determinada aplicación 
	 * 
	 * @param idAplicacion Identificador de la aplicación 
	 * @return Devuelve la lista de variables de una aplicación
	 */
	public List<Variable> getVariables(int idaplicacion) {

		return variableDao.getVariables(idaplicacion);
	}
	/**
	 * Método que recupera toda la información de una determina variable
	 * 
	 * @param idVariable Identificador de la variable que se quiere consultar
	 * @return Devuelve un objeto de tipo variable que contiene toda la información
	 */
	public Variable getVariable(int idvariable) {
		return variableDao.getVariable(idvariable);

	}
	/**
	 * Método que dado un identificador de variable devuelve la lista de opciones en caso de que fuese de tipo Opción
	 * 
	 * @param idVariable Identificador de la variable que se quiere consultar
	 * @return Devuelve la lista de opciones 
	 */
	public List<Opcion> getOpciones(int idvariable) {

		return variableDao.getOpciones(idvariable);
	}
	/**
	 * Método que sirve para suprimir una determinada variable
	 * 
	 * @param idVariable Identificador de la variable que se queire eliminar
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String deleteVariable(int idVariable) {

		return variableDao.deleteVariable(idVariable);
	}
	/**
	 * Modifica una variable de tipo integer
	 * 
	 * @param variable Objeto de tipo Variable que contienen nuevos datos de la variable
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	
	public String updateVariableInt(Variable variable) {

		return variableDao.updateVariableInt(variable);
	}
	/**
	 * Modifica una variable de tipo fecha
	 * 
	 * @param variable Objeto de tipo Variable que contienen nuevos datos de la variable
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	public String updateVariableDate(Variable variable) {

		return variableDao.updateVariableDate(variable);
	}
	/**
	 * Modifica una variable de tipo opción
	 * 
	 * @param variable Objeto de tipo Variable que contienen nuevos datos de la variable
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	public String updateVariableOption(Variable variable) {

		return variableDao.updateVariableOption(variable);
	}
	/**
	 * Modifica una variable de tipo decimal
	 * 
	 * @param variable Objeto de tipo Variable que contienen nuevos datos de la variable
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	public String updateVariableDecimal(Variable variable) {

		return variableDao.updateVariableDecimal(variable);
	}
	/**
	 * Métodpo que sirve para duplicar las variable de una aplicación en otra
	 * 
	 * @param idApplicationVieja Identificador de la aplicación de la que se quieren copiar las variables
	 * @param idApplicationNueva Identificador de la aplicación de la que se quieren pegar las variables
	 * @return
	 */
	public String duplicarVariables(Integer idApplicationVieja,
			Integer idApplicationNueva) {
		return variableDao.duplicarVariables(idApplicationVieja,
				idApplicationNueva);
	}

}
