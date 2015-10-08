package org.tourgune.egistour.utils;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Esta clase de configuración contiene los literales de todas las tablas de la base de datos, incluso sus campos
 * 
 */
public class TablasBD {
	
	public static String TABLE_DEVELOPERS 			= "desarrolladores";
	public static String TABLE_DEVELOPERS_ID		= "idDesarrollador";
	public static String TABLE_DEVELOPERS_NAME 		= "nombre";
	public static String TABLE_DEVELOPERS_EMAIL 	= "email";
	public static String TABLE_DEVELOPERS_PASSWORD 	= "password";
	public static String TABLE_DEVELOPERS_TOKEN 	= "tokkenDesarrollador";
	public static String TABLE_DEVELOPERS_DATE	 	= "fechaCreacion";
	public static String TABLE_DEVELOPERS_ACTIVE 	= "active";
	
	public static String TABLE_APPLICATIONS 				= "aplicaciones";
	public static String TABLE_APPLICATIONS_ID				= "idAplicacion";
	public static String TABLE_APPLICATIONS_DEVELOPER_ID 	= "idDesarrollador";
	public static String TABLE_APPLICATIONS_NAME			= "nombreAplicacion";
	public static String TABLE_APPLICATIONS_DESCRIPTION 	= "descripcion";
	public static String TABLE_APPLICATIONS_TOKEN 			= "tokkenAplicacion";
	public static String TABLE_APPLICATIONS_DATE	 		= "fechaCreacion";
	public static String TABLE_APPLICATIONS_LAT      	 	= "latitud";
	public static String TABLE_APPLICATIONS_LNG      	 	= "longitud";
	public static String TABLE_APPLICATIONS_ACTIVA	 		= "activa";
	public static String TABLE_APPLICATIONS_ZOOM	 		= "zoom";
	public static String TABLE_APPLICATIONS_CONEXION         = "conexion";

	public static String TABLE_VARIABLES 					= "variables";
	public static String TABLE_VARIABLES_ID					= "idvariables";
	public static String TABLE_VARIABLES_APPLICATION_ID 	= "idaplicacion";
	public static String TABLE_VARIABLES_TYPE_ID			= "idtipo";
	public static String TABLE_VARIABLES_NAME 				= "nombrevariable";
	public static String TABLE_VARIABLES_MAX 				= "max";
	public static String TABLE_VARIABLES_MIN	 			= "min";
	public static String TABLE_VARIABLES_DATEINI	 		= "fechadesde";
	public static String TABLE_VARIABLES_DATEFIN	 		= "fechahasta";
	public static String TABLE_VARIABLES_MULOPT	 			= "multiopcion";
	public static String TABLE_VARIABLES_OBLIGATORIO	 	= "obligatorio";

	public static String TABLE_TYPES 						= "tipos";
	public static String TABLE_TYPES_ID						= "idtipo";
	public static String TABLE_TYPES_NAME 					= "nombretipo";
	
	public static String TABLE_OPTIONS 						= "opciones";
	public static String TABLE_OPTIONS_ID					= "idopcion";
	public static String TABLE_OPTIONS_VARIABLE_ID 			= "idvariable";
	public static String TABLE_OPTIONS_OPTION				= "opcion";
	public static String TABLE_OPTIONS_CODIFICACTION		= "codificacion";


	//-------- ionzu-------
	
	public static String TABLE_USERS 						= "usuarios";	
	public static String TABLE_USERS_ID						= "idusuario";
	public static String TABLE_USERS_APP	 				= "idaplicacion";
	public static String TABLE_USERS_IMEI	 				= "imei";
	
	public static String TABLE_VALUES 						= "valores";
	public static String TABLE_VALUES_ID	 				= "idvalor";
	public static String TABLE_VALUES_PARAM					= "idvariable";
	public static String TABLE_VALUES_VALUE	 				= "valorvariable";
	public static String TABLE_VALUES_USER	 				= "idusuario";
	
	public static String TABLE_POINTS 						= "puntos";
	public static String TABLE_POINTS_ID 					= "idpunto";
	public static String TABLE_POINTS_USER 					= "idusuario";
	public static String TABLE_POINTS_LAT 					= "latitud";
	public static String TABLE_POINTS_LONG 					= "longitud";
	public static String TABLE_POINTS_LATLONG				= "latlong";
	public static String TABLE_POINTS_DATE 					= "fecha";
	public static String TABLE_POINTS_PROVIDER 				= "provider";
	
	public static String TABLE_VISORES						= "visores";
	public static String TABLE_VISORES_IDVISOR				= "idvisor";
	public static String TABLE_VISORES_IDAPLICACION			= "idaplicacion";
	public static String TABLE_VISORES_USUARIO				= "usuario";
	public static String TABLE_VISORES_PWD					= "pwd";
	public static String TABLE_VISORES_LAT					= "latitud";
	public static String TABLE_VISORES_LNG					= "longitud";
	public static String TABLE_VISORES_ZOOM					= "zoom";

	
}
