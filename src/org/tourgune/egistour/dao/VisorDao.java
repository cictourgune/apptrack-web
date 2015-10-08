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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tourgune.egistour.bean.Visor;
import org.tourgune.egistour.bean.VisorList;
import org.tourgune.egistour.dao.rowmapper.VisorRowMapper;
import org.tourgune.egistour.utils.TablasBD;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la lógica de base de datos sobre los visores asignados a cada aplicación
 */

@Service
public class VisorDao {

	
	/**
	 * Método que sirve para crear un visor
	 * 
	 * @param visor Objeto de tipo visor
	 * @return Devuelce el identificador del visor en caso de éxito y -1 en caso constario
	 */
	public String createVisor(final Visor visor) {

		Visor aux = getVisor(visor.getUsuario());

		if (aux != null) {
			return "-1";
		}

		try {
			final StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = " (idaplicacion, usuario, pwd, latitud, longitud, zoom) ";
			String tableList = TablasBD.TABLE_VISORES;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?,?,?,?,?)");

			final String token = UUID.randomUUID().toString();

			Object[] parametros;
			int[] types;

			parametros = new Object[] { visor.getIdaplicacion(),
					visor.getUsuario(), visor.getPwd(), visor.getLatitud(), visor.getLongitud(), visor.getZoom() };

			types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE, Types.INTEGER };

			// encriptar password
			PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashedPass = encoder.encodePassword(visor.getPwd(),
					null);

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int idVisor = -1;
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							sql.toString(),
							java.sql.Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, visor.getIdaplicacion());
					ps.setString(2, visor.getUsuario());
					ps.setString(3, hashedPass);
					ps.setDouble(4,0);
					ps.setDouble(5,0);
					ps.setInt(6, 1);

					return ps;
				}
			}, keyHolder);

			Map<String, Object> claves = keyHolder.getKeys();
			idVisor = (Integer) claves.get("idvisor");

			return idVisor + "";

		} catch (Exception e) {
			System.err.println(e.getMessage());
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

		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "a.idvisor idvisor, a.idaplicacion idaplicacion, a.usuario usuario, a.pwd pwd, a.latitud latitud, a.longitud longitud, a.zoom zoom  ";
			String tableList = TablasBD.TABLE_VISORES + " a ";
			String innerjoin = " WHERE a.idaplicacion=?";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(innerjoin);

			Object[] parametros;

			parametros = new Object[] { idaplicacion };
			int[] types = new int[] { Types.INTEGER };

			List<Visor> listaVisores = null;
			VisorList appList = new VisorList();

			try {
				listaVisores = (List<Visor>) jdbcTemplate.query(sql.toString(),
						parametros, types, new VisorRowMapper());
			} catch (final EmptyResultDataAccessException e) {
				return null;
			}

			return listaVisores;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Método que sirve para eliminar un determinado visor
	 * 
	 * @param idVisor Identificador del visor que se quiere eliminar
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String deleteVisor(Integer idVisor) {
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM " + TablasBD.TABLE_VISORES + " ");
		sql.append("WHERE " + TablasBD.TABLE_VISORES_IDVISOR + " = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { idVisor };

		types = new int[] { Types.INTEGER };

		if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
			return "1";
		} else {
			return "-1";
		}
	}

	/**
	 * Método que devuelve mediante un objeto de tipo Visor la información de un visor
	 * 
	 * @param usuario Nombre del visor
	 * @return Devuelve el objeto de tipo Visor
	 */
	public Visor getVisor(String usuario) {

		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "idvisor , idaplicacion,	  usuario , pwd, latitud, longitud, zoom  ";
			String tableList = TablasBD.TABLE_VISORES + "  ";
			String condition = "WHERE usuario= ?";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { usuario };
			int[] types = new int[] { Types.VARCHAR };

			Visor visor;

			try {
				visor = jdbcTemplate.queryForObject(sql.toString(), parametros,
						types, new VisorRowMapper());
			} catch (final EmptyResultDataAccessException e) {
				return null;
			}

			return visor;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Método que sirve para recuperar la contraseña de un visor
	 * 
	 * @param usuario Nombre del visor
	 * @return Devuleve la contraseña en caso de que exista y null en caso contrario
	 */
	public String getVisorPassword(String usuario) {

		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT " + TablasBD.TABLE_VISORES_PWD + " ");
			sql.append("FROM " + TablasBD.TABLE_VISORES + " ");
			sql.append("WHERE " + TablasBD.TABLE_VISORES_USUARIO + " = ? ");

			Object[] parametros;
			parametros = new Object[] { usuario };

			String pass = jdbcTemplate.queryForObject(sql.toString(),
					parametros, String.class);

			return pass;

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Resource
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public String updateLocation(Integer idVisor, double lat, double lng,
			int zoom) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE " + TablasBD.TABLE_VISORES + " ");
		sql.append("SET ");
		sql.append(TablasBD.TABLE_VISORES_LAT + " = " + lat + " , "
				+ TablasBD.TABLE_VISORES_LNG + "=" + lng + ", "
				+ TablasBD.TABLE_VISORES_ZOOM + "=" + zoom);
		sql.append(" WHERE " + TablasBD.TABLE_VISORES_IDVISOR + " = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { idVisor };

		types = new int[] { Types.INTEGER };

		if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
			return "1";
		} else {
			return "-1";
		}

	}

}
