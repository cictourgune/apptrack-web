package org.tourgune.egistour.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.egistour.bean.Visor;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la interface RowMapper usada por JdbcTemplate para el
 * mapeo de los objetos de tipo Visor recuperados del ResulSet
 */

public class VisorRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Visor visor = new Visor();

		visor.setIdvisor(rs.getInt("idvisor"));
		visor.setIdaplicacion(rs.getInt("idaplicacion"));
		visor.setUsuario(rs.getString("usuario"));
		visor.setPwd(rs.getString("pwd"));
		visor.setLatitud(rs.getDouble("latitud"));
		visor.setLongitud(rs.getDouble("longitud"));
		visor.setZoom(rs.getInt("zoom"));

		return visor;

	}
}
