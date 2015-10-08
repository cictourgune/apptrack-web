package org.tourgune.egistour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.tourgune.egistour.bean.Punto;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.dao.rowmapper.VariableRowMapper;
import org.tourgune.egistour.utils.TablasBD;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service
public class SDKDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Metodo que comprueba si el token del desarrollador y el de la aplicacion existen y concuerdan.
	 * 
	 * @param devToken Token del desarrollador
	 * @param appToken Token de la aplicacion
	 * @return 1 si los dos tokens son correctos, -1 en caso contrario
	 */
	public int login(String devToken, String appToken){
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT " + TablasBD.TABLE_APPLICATIONS_ID + " ");
			sql.append("FROM " + TablasBD.TABLE_APPLICATIONS + " as a JOIN " + TablasBD.TABLE_DEVELOPERS +" as d on a.idDesarrollador = d.idDesarrollador ");
			sql.append("WHERE a." + TablasBD.TABLE_APPLICATIONS_TOKEN + "= ? and d." + TablasBD.TABLE_DEVELOPERS_TOKEN + " = ? and a." + TablasBD.TABLE_APPLICATIONS_ACTIVA + "= true");
	
			Object[] parametros;
			parametros = new Object[] {appToken, devToken};
			
			Integer id = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
			
			System.out.println("login: " + sql.toString());
			System.out.println("login id: " + id);
			
			return id; 
			
        } catch (EmptyResultDataAccessException e) { 
            return -1;
        }
	}
	
	/**
	 * Metodo que almacena un objeto de tipo punto en la base de datos
	 * 
	 * @param point Objeto de tipo punto a insertar en la base de datos
	 * @param idUser Identificador del usuario
	 * @return 1 si el punto es insertado correctamente, -1 en caso contrario
	 */
	public int createPoint(Punto point, int idUser) { 
		
StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO " + TablasBD.TABLE_POINTS + " ");
		sql.append("(" + TablasBD.TABLE_POINTS_USER + "," + TablasBD.TABLE_POINTS_LAT + "," + TablasBD.TABLE_POINTS_LONG +"," + TablasBD.TABLE_POINTS_DATE + "," + TablasBD.TABLE_POINTS_PROVIDER + ") ");
		sql.append("VALUES ");
		sql.append("(?, ?, ?, ?, ?)");
		
		Object[] parametros;
		int[] types;

		parametros = new Object[] { idUser, point.getLatitud(), point.getLongitud(), point.getFecha(), point.getProvider() };
		
		types = new int[] { Types.INTEGER, Types.DOUBLE, Types.DOUBLE, Types.TIMESTAMP, Types.VARCHAR };
		System.out.println("create point: " + sql.toString());
		
		if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			System.out.println("create point: 1");
			return 1;
		}else{
			System.out.println("create point: -1");
			return -1;
		} 	
	}
	
	/**
	 * Metodo que comprueba si existe un usuario con ese IMEI
	 * 
	 * @param imei Identificador unico del dispositivo
	 * @param idApp Identificador de la aplicacion
	 * @return El identificador del usuario con el IMEI enviado, -1 en caso contrario
	 */
	public int existImei(String imei, int idApp){
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT " + TablasBD.TABLE_USERS_ID + " ");
			sql.append("FROM " + TablasBD.TABLE_USERS + " ");
			sql.append("WHERE " + TablasBD.TABLE_USERS_IMEI + "= ? and " + TablasBD.TABLE_USERS_APP + " = ?");
	
			Object[] parametros;
			parametros = new Object[] {imei, idApp};
	
			Integer id = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
			
			System.out.println("exist imei: " + sql.toString());
			System.out.println("exist imei id: " + id);
			
			return id; 
			
        } catch (EmptyResultDataAccessException e) { 
            return -1;
        }
	}
	
	/**
	 * Metodo que crea un nuevo usuario
	 * 
	 * @param imei Identificador unico del dispositivo
	 * @param idApp Identificador de la aplicacion
	 * @return El identificador del usuario asignado por la base de datos si se inserta correctamente, 0 en caso contrario
	 */
	public int createUser(final String imei, final int idApp) { 
		
		final StringBuffer sql = new StringBuffer();
		int lastId = 0;
       		
        try {
        	sql.append("INSERT INTO " + TablasBD.TABLE_USERS + " ");
    		sql.append("(" + TablasBD.TABLE_USERS_APP + "," + TablasBD.TABLE_USERS_IMEI + ") ");
    		sql.append("VALUES ");
    		sql.append("(?, ?)");
               
    		KeyHolder keyHolder = new GeneratedKeyHolder();
    		jdbcTemplate.update(new PreparedStatementCreator() {
    			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
    				PreparedStatement ps = connection.prepareStatement(sql.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
    				ps.setInt(1, idApp);
    				ps.setString(2, imei);

    				return ps;
    			}
    		}, keyHolder);
    		
    		Map<String, Object> claves = keyHolder.getKeys();	
    		lastId = (Integer)claves.get("idusuario");
    		
    		System.out.println("create user: " + sql.toString());
			System.out.println("create user id: " + lastId);
    		
               
        } catch (Exception e) {
               e.printStackTrace();
        }
        
        return lastId;
	}
	
	/**
	 * Metodo que almacena un nuevo valor en la base de datos
	 * 
	 * @param value Objeto de tipo value a insertar en la base de datos
	 * @param idUser Identificador del usuario
	 * @return 1 si el valor es insertado correctamente, -1 en caso contrario
	 */
	public int createValue(Valor value, int idUser) { 
		
		StringBuffer sql = new StringBuffer();
				
		sql.append("INSERT INTO " + TablasBD.TABLE_VALUES + " ");
		sql.append("(" + TablasBD.TABLE_VALUES_PARAM + "," + TablasBD.TABLE_VALUES_VALUE + "," + TablasBD.TABLE_VALUES_USER +  ") ");
		sql.append("VALUES ");
		sql.append("(?, ?, ?)");
		 
		Object[] parametros;
		int[] types;
		parametros = new Object[] { value.getIdvariable(), value.getValorvariable(), idUser };
		types = new int[] { Types.INTEGER, Types.VARCHAR, Types.INTEGER };
		
		System.out.println("create value: " + sql.toString());
		
		if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			System.out.println("create value: 1"); 
			return 1;
		}else{
			System.out.println("create value: -1"); 
			return -1;
		} 	
	}
	
	/**
	 * Metodo que consulta una variable
	 * 
	 * @param idvariable Identificador de la variable a consultar
	 * @return Objeto de tipo variable almacenado en la base de datos, si no se encuentra, el objeto tendra el campo idTipo = -1 
	 */
	public Variable getVariable(int idVariable) {
		try {
			StringBuffer sql = new StringBuffer();
			String rowList = "idvariable, idaplicacion, idtipo, nombrevariable, max,min, fechadesde,fechahasta,multiopcion,obligatorio ";
			String tableList = TablasBD.TABLE_VARIABLES + "  ";
			String condition = "WHERE idvariable= ?";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { idVariable };
			int[] types = new int[] { Types.INTEGER };

			Variable variable;
		
			System.out.println("get variable: " + sql.toString());

			try {
				variable = jdbcTemplate.queryForObject(sql.toString(), parametros, types, new VariableRowMapper());
			} catch (final EmptyResultDataAccessException e) {
				variable = new Variable();
				variable.setIdtipo(-1);
				return variable;
			}

			return variable;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}		
	}
	
	
	
	/**
	 * Metodo que consulta una variable
	 * 
	 * @param idvariable Identificador de la variable a consultar
	 * @return Objeto de tipo variable almacenado en la base de datos, si no se encuentra, el objeto tendra el campo idTipo = -1 
	 */
	
	public int getPlataforma(String appToken) {
		try {
			StringBuffer sql = new StringBuffer();
			String rowList = "idvariable";
			String tableList = TablasBD.TABLE_VARIABLES + " v, " + TablasBD.TABLE_APPLICATIONS + " a ";
			String condition = "WHERE a.idaplicacion = v.idaplicacion and a.tokkenaplicacion = ? and nombrevariable = 'plataforma'";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;
			parametros = new Object[] { appToken };
	
			System.out.println("get plataforma: " + sql.toString());

			int result;
			
			try {
				result = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
			} catch (final EmptyResultDataAccessException e) {
				result = -1;
			}
			System.out.println("result get plataforma: " + result);
			return result;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return -1;
		}

		
	}
	
	
	
	
	
	/**
	 * Metodo que consulta si una variable tiene la opcion option
	 * 
	 * @param idvariable Identificador de la variable a consultar
	 * @param option Cadena de caracteres a consultar si existe como opcion de la variable
	 * @return Identificador de la opcion. Si no existe la opcion -1
	 */
	public Integer existOption(int idVariable, String option) {
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT " + TablasBD.TABLE_OPTIONS_ID + " ");
			sql.append("FROM " + TablasBD.TABLE_OPTIONS + " ");
			sql.append("WHERE " + TablasBD.TABLE_OPTIONS_VARIABLE_ID + " = ? and " + TablasBD.TABLE_OPTIONS_OPTION + " =?");
	
			Object[] parametros;
			parametros = new Object[] { idVariable, option };
				
			Integer id = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
			
			System.out.println("exist option: " + sql.toString());
			System.out.println("exist option id: " + id);
			
			return id; 
			
        } catch (EmptyResultDataAccessException e) { 
        	System.out.println(e);
            return -1;
        }
	}

	/**
	 * Metodo que comprueba si un usuario ya ha almacenado un valor para esa variable
	 * 
	 * @param idvariable Identificador de la variable a consultar
	 * @param idUsuario Identificador del usuario
	 * @return Identificador del valor en caso de haberse almacenado con anterioridad. En caso contrario, -1
	 */
	public Integer existValor(int idVariable, int idUsuario) {
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT " + TablasBD.TABLE_VALUES_ID + " ");
			sql.append("FROM " + TablasBD.TABLE_VALUES + " ");
			sql.append("WHERE " + TablasBD.TABLE_VALUES_PARAM + " = ? and " + TablasBD.TABLE_VALUES_USER + " =?");
	
			Object[] parametros;	
			parametros = new Object[] { idVariable, idUsuario };
						
			Integer id = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
			
			System.out.println("exist valor: " + sql.toString());
			System.out.println("exist valor id: " + id);
			
			return id; 
			
        } catch (EmptyResultDataAccessException e) { 
        	System.out.println("no exist valor: " + e);
            return -1;
        }
	}	
	
	/**
	 * Metodo que modifica un valor de una variable (Se ejecuta tras comprobar que el valor existe mediante la funcion existValor)
	 * 
	 * @param val Objeto de tipo valor que contiene la variable a modificar y el valor a asignar
	 * @param idUsuario Identificador del usuario
	 * @return 1 si el valor es modificado correctamente, -1 en caso contrario
	 */
	public int updateValor(Valor val, int idUsuario) {
		
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE " + TablasBD.TABLE_VALUES);
			sql.append(" SET " + TablasBD.TABLE_VALUES_VALUE + " = ? ");
			sql.append("WHERE " + TablasBD.TABLE_VALUES_PARAM + " = ? and " + TablasBD.TABLE_VALUES_USER + " = ?");

			Object[] parametros;
			int[] types;
			
			parametros = new Object[] { val.getValorvariable(), val.getIdvariable(), idUsuario };		
			types = new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER };
			
			System.out.println("update valor: " + sql.toString());
			
			if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
				System.out.println("update valor: 1");
				return 1;
			}else{
				System.out.println("update valor: -1");
				return -1;
			}
	}
	
	/**
	 * Metodo que almacena el modo de captura de la posicion del dispositivo (GPS / WiFi)
	 * 
	 * @param idApp Identificador de la aplicacion
	 * @param conexion Cadena de caracteres que especifica el modo de captura de la posicion del dispositivo (GPS / WiFi)
	 * @return 1 si el valor es almacenado correctamente, -1 en caso contrario
	 */
	public Integer updateAppConectivity(int idApp, String conexion) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE " + TablasBD.TABLE_APPLICATIONS);
		sql.append(" SET " + TablasBD.TABLE_APPLICATIONS_CONEXION + " = ? ");
		sql.append("WHERE " + TablasBD.TABLE_APPLICATIONS_ID + " = ? ");

		Object[] parametros;
		int[] types;
		
		parametros = new Object[] { conexion, idApp };		
		types = new int[] { Types.VARCHAR, Types.INTEGER };
		
		System.out.println("update valor: " + sql.toString());
		
		if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			System.out.println("update conexion: 1");
			return 1;
		}else{
			System.out.println("update conexion: -1");
			return -1;
		}
	}
	
	/**
	 * Metodo que comprueba si se ha almacenado algun valor para la aplicacion idApp
	 * 
	 * @param idApp Identificador de la aplicacion
	 * @return 1 si si se ha almacenado algun valor, -1 en caso contrario
	 */
	public int existAppConectivity(int idApp) {
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT " + TablasBD.TABLE_APPLICATIONS_CONEXION + " ");
			sql.append("FROM " + TablasBD.TABLE_APPLICATIONS + " ");
			sql.append("WHERE " + TablasBD.TABLE_APPLICATIONS_ID + " = ? and conexion <> NULL");
	
			Object[] parametros;	
			parametros = new Object[] { idApp };
						
			String conexion = jdbcTemplate.queryForObject(sql.toString(), parametros, String.class);
			
			System.out.println("exist app conexion: " + sql.toString());
			System.out.println("exist app conexion: " + conexion);
			
			if (conexion == null){
				return -1;
			}
			else{
				return 1; 
			}
			
			
        } catch (EmptyResultDataAccessException e) { 
        	System.out.println("no exist conexion: " + e);
            return -1;
        }
	}

	//-------------------------------------------------
		public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}


		

}
