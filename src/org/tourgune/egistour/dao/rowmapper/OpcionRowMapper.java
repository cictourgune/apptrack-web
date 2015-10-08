package org.tourgune.egistour.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.egistour.bean.Opcion;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la interface RowMapper usada por JdbcTemplate para el
 * mapeo de los objetos de tipo Opción recuperados del ResulSet
 */
public class OpcionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Opcion opcion = new Opcion();
		opcion.setIdvariable(rs.getInt("idvariable"));
		opcion.setNombreopcion(rs.getString("opcion"));

		return opcion;

	}
}
