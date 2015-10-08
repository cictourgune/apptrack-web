package org.tourgune.egistour.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.egistour.bean.Variable;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la interface RowMapper usada por JdbcTemplate para el
 * mapeo de los objetos de tipo Variable recuperados del ResulSet
 */

public class VariableRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Variable variable = new Variable();
		variable.setFechadesde(rs.getString("fechadesde"));
		variable.setFechahasta(rs.getString("fechahasta"));
		variable.setIdaplicacion(rs.getInt("idaplicacion"));
		variable.setIdtipo(rs.getInt("idtipo"));
		variable.setIdvariable(rs.getInt("idvariable"));
		variable.setMax(rs.getDouble("max"));
		variable.setMin(rs.getDouble("min"));
		variable.setNombrevariable(rs.getString("nombrevariable"));
		variable.setMultiopcion(rs.getBoolean("multiopcion"));
		variable.setObligatorio(rs.getBoolean("obligatorio"));
		return variable;

	}

}
