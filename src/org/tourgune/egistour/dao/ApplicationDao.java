package org.tourgune.egistour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.tourgune.egistour.bean.Aplicacion;
import org.tourgune.egistour.bean.ApplicationList;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.dao.rowmapper.AplicacionRowMapper;
import org.tourgune.egistour.utils.AppTrackUtils;
import org.tourgune.egistour.utils.TablasBD;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implenta la lógica de base de datos relacionada con las aplicaciones asignadas a los desarrolladores
 */

@Service
public class ApplicationDao {

	/**
	 * Este método es el encargado de recuperar la lista de aplicaciones de un determinado desarrollador
	 * 
	 * @param developername Nombre del desarrollador
	 * @return Lista de aplicaciones
	 */
	public List getApplications(String developername) {

		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "a.idaplicacion idaplicacion, a.iddesarrollador iddesarrollador, a.nombreaplicacion nombreaplicacion, a.tokkenaplicacion tokkenaplicacion, a.fechacreacion fechacreacion, a.descripcion descripcion,  a.latitud latitud, a.longitud longitud, a.zoom zoom  ";
			String tableList = TablasBD.TABLE_DEVELOPERS + " d, "
					+ TablasBD.TABLE_APPLICATIONS + " a ";
			String innerjoin = "WHERE a.activa=TRUE and d.idDesarrollador=a.idDesarrollador ";
			String condition = "AND d.nombre= ? AND d.active=TRUE ORDER BY a.nombreaplicacion";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(innerjoin);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { developername.trim() };
			int[] types = new int[] { Types.VARCHAR };

			List<Aplicacion> listaAplicaciones = null;
			ApplicationList appList = new ApplicationList();

			try {
				listaAplicaciones = (List<Aplicacion>) jdbcTemplate.query(
						sql.toString(), parametros, types,
						new AplicacionRowMapper());
			} catch (final EmptyResultDataAccessException e) {
				return null;
			}

			return listaAplicaciones;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Este método devuelve la información relativa a una aplicación
	 * 
	 * @param idApplication Identificador de la aplicación que se quiere consultar
	 * @return Devuelve un objeto de tipo Aplicacion
	 */
	public Aplicacion getApplication(Integer idApplication) {
		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "a.idaplicacion idaplicacion, a.iddesarrollador iddesarrollador, a.nombreaplicacion nombreaplicacion, a.tokkenaplicacion tokkenaplicacion, a.fechacreacion fechacreacion, a.descripcion descripcion, a.latitud latitud, a.longitud longitud, a.zoom zoom ";
			String tableList = TablasBD.TABLE_APPLICATIONS + " a ";
			String condition = "WHERE a.idaplicacion= ?";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { idApplication };
			int[] types = new int[] { Types.INTEGER };

			Aplicacion aplicacion;

			try {
				aplicacion = jdbcTemplate.queryForObject(sql.toString(),
						parametros, types, new AplicacionRowMapper());
			} catch (final EmptyResultDataAccessException e) {
				return null;
			}

			return aplicacion;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Este metodo es el encargado de suprimir una aplicación
	 * 
	 * @param idApplication Identificador de la aplicación que se quiere eliminar
	 * @return Devuelve un 1 en caso de éxito y un -1 en caso contrario
	 */
	public String deleteApplication(Integer idApplication) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE " + TablasBD.TABLE_APPLICATIONS + " ");
		sql.append("SET ");
		sql.append(TablasBD.TABLE_APPLICATIONS_ACTIVA + " = 'false' ");
		sql.append("WHERE " + TablasBD.TABLE_APPLICATIONS_ID + " = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { idApplication };

		types = new int[] { Types.INTEGER };

		if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
			return "1";
		} else {
			return "-1";
		}
	}

	/**
	 * Metodo que se usa para crear aplicaciones
	 * 
	 * @param aplicacion Objeto de tipo aplicación que contiene la información de la aplicación que se quiere insertar
	 * @param iddeveloper Identificador del desarrollador a la que va asociado la aplicacion
	 * @return Devuelve el identificador de la aplicación en caso de éxito y -1 en caso contrario
	 */
	public String createApplication(final Aplicacion aplicacion,
			final Integer iddeveloper) {

		try {
			final StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = " (iddesarrollador, nombreaplicacion, tokkenaplicacion, descripcion, origen) ";
			String tableList = TablasBD.TABLE_APPLICATIONS;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?,?,?,?)");

			final String token = UUID.randomUUID().toString();

			Object[] parametros;
			int[] types;

			parametros = new Object[] { iddeveloper,
					aplicacion.getNombreaplicacion(), token,
					aplicacion.getDescripcion(), aplicacion.getOrigen() };

			types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
					Types.VARCHAR, Types.INTEGER };

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int idAplicacion = -1;
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							sql.toString(),
							java.sql.Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, iddeveloper);
					ps.setString(2, aplicacion.getNombreaplicacion());
					ps.setString(3, token);
					ps.setString(4, aplicacion.getDescripcion());
					ps.setInt(5, aplicacion.getOrigen());
					

					return ps;
				}
			}, keyHolder);

			Map<String, Object> claves = keyHolder.getKeys();
			idAplicacion = (Integer) claves.get("idaplicacion");

			
			//todas las aplicaciones tienen un variable asociada
			
			Variable variable = new Variable();
			variable.setIdaplicacion(idAplicacion);
			variable.setIdtipo(2);
			variable.setNombrevariable("plataforma");
			variable.setMultiopcion(false);
			variable.setObligatorio(false);
			createVariableOpciones(variable, "android;ios;javascript");
			
			
			return idAplicacion + "";

			/*
			 * if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			 * return idAplicacion+""; }else{ return "-1"; }
			 */

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "-1";
		}
	}

	
	
	
	
	
	// -------------------------------------------------

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
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE " + TablasBD.TABLE_APPLICATIONS + " ");
		sql.append("SET ");
		sql.append(TablasBD.TABLE_APPLICATIONS_LAT + " = " + lat + " , "
				+ TablasBD.TABLE_APPLICATIONS_LNG + "=" + lng + ", "
				+ TablasBD.TABLE_APPLICATIONS_ZOOM + "=" + zoom);
		sql.append(" WHERE " + TablasBD.TABLE_APPLICATIONS_ID + " = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { idApplication };

		types = new int[] { Types.INTEGER };

		if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
			return "1";
		} else {
			return "-1";
		}

	}
	
	
	
	/**
	 * Método que se utiliza para insertar una opcion en una variable de tipo Opcion
	 * 
	 * @param idVariable Identificador de la variable a la cual se le quiere añadir una opción
	 * @param opcion Nombde de la opción
	 * @return Devuelve un 1 en caso de éxito y -1 en caso contrario
	 */
	private String insertOpcion(int idVariable, String opcion) {

		try {

			StringBuffer sql = new StringBuffer();
			String rowList = " (" + TablasBD.TABLE_OPTIONS_VARIABLE_ID + ", "
					+ TablasBD.TABLE_OPTIONS_OPTION + ") ";

			String tableList = TablasBD.TABLE_OPTIONS;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?)");

			Object[] parametros;
			int[] types;

			parametros = new Object[] { idVariable, opcion };

			types = new int[] { Types.INTEGER, Types.VARCHAR };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Método que sirve para crear una variable de tipo Opcion
	 * 
	 * @param variable Objeto que contiene la información de la variable
	 * @param opciones Lista de opciones separados por ";"
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	private String createVariableOpciones(Variable variable, String opciones) {

		try {
			final StringBuffer sql = new StringBuffer();
			String rowList = " (" + TablasBD.TABLE_VARIABLES_APPLICATION_ID
					+ ", " + TablasBD.TABLE_VARIABLES_TYPE_ID + ", "
					+ TablasBD.TABLE_VARIABLES_NAME + ", "
					+ TablasBD.TABLE_VARIABLES_MULOPT + ", "
					+ TablasBD.TABLE_VARIABLES_OBLIGATORIO + " ) ";
			String tableList = TablasBD.TABLE_VARIABLES;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?,?,?,?)");

			final int idAplicacion = variable.getIdaplicacion();
			final String nombreVariable = variable.getNombrevariable();
			final Boolean multiopcion = variable.getMultiopcion();
			final Boolean obligatorio = variable.getObligatorio();

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int idVariable = -1;
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							sql.toString(),
							java.sql.Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, idAplicacion);
					ps.setInt(2, 2);
					ps.setString(3, nombreVariable);
					ps.setBoolean(4, multiopcion);
					ps.setBoolean(5, obligatorio);

					return ps;
				}
			}, keyHolder);

			Map<String, Object> claves = keyHolder.getKeys();
			idVariable = (Integer) claves.get("idvariable");

			List<String> listaOpciones = appTrackUtils.stringToArray(opciones);
			for (int i = 0; i < listaOpciones.size(); i++) {
				insertOpcion(idVariable, listaOpciones.get(i));
			}

			return "1";
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	
	@Resource
	private AppTrackUtils appTrackUtils;

	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
