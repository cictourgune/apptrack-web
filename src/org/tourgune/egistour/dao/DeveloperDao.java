package org.tourgune.egistour.dao;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tourgune.egistour.bean.Developer;
import org.tourgune.egistour.utils.TablasBD;

/**
 * AppTrack
 * 
 * Created by CICtourGUNE on 10/04/13. Copyright (c) 2013 CICtourGUNE. All
 * rights reserved.
 * 
 * Clase que implementa la logica de base de datos sobre los desarrolladores
 */
@Service
public class DeveloperDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * Método encargado de crear un desarrollador
	 * 
	 * @param developer
	 *            Objeto de tipo Developer que contiene la información del
	 *            desarrollador a crear
	 * @return Devuelve el identificador del desarrollador en caso de éxito y -1
	 *         en caso contrario
	 */
	public String createDeveloper(Developer developer) {

		StringBuffer sql = new StringBuffer();

		String token = UUID.randomUUID().toString();

		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String fecha = sdf.format(cal.getTime());

		Boolean activate = false;

		sql.append("INSERT INTO " + TablasBD.TABLE_DEVELOPERS + " ");
		sql.append("(" + TablasBD.TABLE_DEVELOPERS_NAME + ","
				+ TablasBD.TABLE_DEVELOPERS_EMAIL + ","
				+ TablasBD.TABLE_DEVELOPERS_TOKEN + ","
				+ TablasBD.TABLE_DEVELOPERS_DATE + ","
				+ TablasBD.TABLE_DEVELOPERS_PASSWORD + ","
				+ TablasBD.TABLE_DEVELOPERS_ACTIVE + ") ");
		sql.append("VALUES ");
		sql.append("(?, ?, ?, ?, ?, ?)");

		Object[] parametros;
		int[] types;

		// encriptar password
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword(developer.getPassword(),
				null);

		parametros = new Object[] { developer.getDevname(),
				developer.getEmail(), token, fecha, hashedPass, activate };

		types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.DATE, Types.VARCHAR, Types.BOOLEAN };

		if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
			return token;
		} else {
			return "-1";
		}
	}

	/**
	 * Método creado para conseguir la contraseá de un desarrollador
	 * 
	 * @param devname
	 *            Nombre del desarrollador
	 * @return Devuelve la contraseña del desarrollador
	 */
	public String getDeveloperPassword(String devname) {

		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT " + TablasBD.TABLE_DEVELOPERS_PASSWORD + " ");
			sql.append("FROM " + TablasBD.TABLE_DEVELOPERS + " ");
			sql.append("WHERE " + TablasBD.TABLE_DEVELOPERS_NAME + " = ? AND "
					+ TablasBD.TABLE_DEVELOPERS_ACTIVE + "='true'");

			Object[] parametros;
			parametros = new Object[] { devname };

			String pass = jdbcTemplate.queryForObject(sql.toString(),
					parametros, String.class);

			return pass;

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Método creado para verificar la existencia del Token
	 * 
	 * @param token
	 *            Token asocialo al desarrollador
	 * @return True en caso de que exista y false en caso contrario
	 */
	public boolean existsToken(String token) {

		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT count(*) FROM ");
			sql.append(TablasBD.TABLE_DEVELOPERS + " ");
			sql.append("WHERE " + TablasBD.TABLE_DEVELOPERS_TOKEN + " = ? ");

			Object[] parametros;
			parametros = new Object[] { token.trim() };

			Integer tokennum = jdbcTemplate.queryForObject(sql.toString(),
					parametros, Integer.class);

			if (tokennum == 0) {
				return false;
			}

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Método que activa al desarrollado mediante la modificación de un campo en
	 * la base de datos
	 * 
	 * @param token
	 *            Token del desarrollador a activar
	 * @return Devuelve un 1 en caso de éxito y un -1 en caso contrario
	 */
	public Integer activateDeveloper(String token) {

		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE " + TablasBD.TABLE_DEVELOPERS + " ");
		sql.append("SET ");
		sql.append(TablasBD.TABLE_DEVELOPERS_ACTIVE + " = 'true' ");
		sql.append("WHERE " + TablasBD.TABLE_DEVELOPERS_TOKEN + " = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { token };

		types = new int[] { Types.VARCHAR };

		if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Metodo que devuelve el identificador del desarrollador
	 * 
	 * @param developername
	 *            Nombre del desarrollador
	 * @return Devuelve el identificador del desarrollador en caso de éxito y -1
	 *         en caso contrario
	 */
	public Integer getDeveloperID(String developername) {

		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "iddesarrollador";
			String tableList = TablasBD.TABLE_DEVELOPERS;
			String condition = " WHERE nombre= ? AND active=TRUE";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { developername };
			Integer idDeveloper;

			idDeveloper = jdbcTemplate.queryForObject(sql.toString(),
					parametros, Integer.class);

			return idDeveloper;

		} catch (EmptyResultDataAccessException e) {
			return -1;
		}

	}

	/**
	 * Método que comprueba si el nombre del desarrollador éxiste
	 * 
	 * @param developername
	 *            Nombre del desarrollador
	 * @return Devuelve el identificador del desarrollador en caso de que exista
	 *         y un -1 en caso contrario
	 */
	public Integer existDeveloper(String developername) {

		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String tableList = TablasBD.TABLE_DEVELOPERS;
			String condition = " WHERE nombre= ? ";

			sql.append("SELECT count(*) FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { developername };
			Integer existe;

			existe = jdbcTemplate.queryForObject(sql.toString(), parametros,
					Integer.class);
			return existe;

		} catch (EmptyResultDataAccessException e) {
			return -1;
		}
	}

	/**
	 * Método que devuelve el token del desarrollador
	 * 
	 * @param developername
	 *            Nombre del desarrollador
	 * @return Devuelve el token del desarrollador en caso de exitir y - 1 en
	 *         caso contrario
	 */
	public String getDeveloperToken(String developername) {

		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "tokkendesarrollador";
			String tableList = TablasBD.TABLE_DEVELOPERS;
			String condition = " WHERE nombre= ? AND active=TRUE";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { developername };
			String token;

			token = jdbcTemplate.queryForObject(sql.toString(), parametros,
					String.class);
			return token;

		} catch (EmptyResultDataAccessException e) {
			return "-1";
		}

	}

	public Integer restorePassword(String token, String email, String pass) {

		try {
			StringBuffer sql = new StringBuffer();
			// encriptar password
			PasswordEncoder encoder = new Md5PasswordEncoder();
			String hashedPass = encoder.encodePassword(pass, null);

			sql.append(" UPDATE " + TablasBD.TABLE_DEVELOPERS)
					.append(" SET " + TablasBD.TABLE_DEVELOPERS_PASSWORD
							+ " = ? ")
					.append(" WHERE " + TablasBD.TABLE_DEVELOPERS_TOKEN
							+ " = ? ")
					.append(" AND " + TablasBD.TABLE_DEVELOPERS_EMAIL + " = ? ");

			Object[] parametros;
			int[] types;

			parametros = new Object[] { hashedPass, token, email };

			types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };

			int num = jdbcTemplate.update(sql.toString(), parametros, types);
			if (num == 0) {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	public String getDeveloperTokenByEmail(String email) {

		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT " + TablasBD.TABLE_DEVELOPERS_TOKEN + " ")
					.append("FROM " + TablasBD.TABLE_DEVELOPERS + " ")
					.append("WHERE " + TablasBD.TABLE_DEVELOPERS_EMAIL
							+ " = ? ");

			Object[] parametros;
			parametros = new Object[] { email };

			String token = jdbcTemplate.queryForObject(sql.toString(),
					parametros, String.class);

			return token;

		} catch (EmptyResultDataAccessException e) {
			return "-1";
		}
	}

	public Integer validateTokenEmail(String token, String email) {

		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT 1 ")
					.append(" FROM " + TablasBD.TABLE_DEVELOPERS)
					.append(" WHERE " + TablasBD.TABLE_DEVELOPERS_TOKEN
							+ " = ? ")
					.append(" AND " + TablasBD.TABLE_DEVELOPERS_EMAIL + " = ? ");

			Object[] parametros;
			parametros = new Object[] { token, email };

			Integer num = jdbcTemplate.queryForObject(sql.toString(),
					parametros, Integer.class);

			if (num == 0) {
				return -1;
			}

			return 1;

		} catch (EmptyResultDataAccessException e) {
			return -1;
		}
	}

	public String getUserTokenByEmail(String email) {
		
		try {
			StringBuffer sql = new StringBuffer();

			sql.append("SELECT " + TablasBD.TABLE_DEVELOPERS_TOKEN + " ")
					.append("FROM " + TablasBD.TABLE_DEVELOPERS + " ")
					.append("WHERE " + TablasBD.TABLE_DEVELOPERS_EMAIL
							+ " = ? ");
		
			Object[] parametros;
			parametros = new Object[] { email };
			
			String token = jdbcTemplate.queryForObject(sql.toString(),
					parametros, String.class);
			
			return token;

		} catch (EmptyResultDataAccessException e) {

			return "-1";

		}

	}

	public Integer existsEmail(String email) {

		try {

			StringBuffer sql = new StringBuffer();

			sql.append("SELECT count(*) FROM ");
			sql.append(TablasBD.TABLE_DEVELOPERS + " ");
			sql.append("WHERE " + TablasBD.TABLE_DEVELOPERS_EMAIL + " = ?");

			Object[] parametros;

			parametros = new Object[] { email };
			Integer num = jdbcTemplate.queryForObject(sql.toString(),
					parametros, Integer.class);
			if (num == 0) {
				return -1;
			}
			return 1;

		} catch (Exception e) {

			return -1;

		}

	}

	// -------------------------------------------------
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
