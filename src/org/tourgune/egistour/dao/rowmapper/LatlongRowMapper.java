package org.tourgune.egistour.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.egistour.bean.Latlong;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la interface RowMapper usada por JdbcTemplate para el
 * mapeo de los objetos de tipo Punto recuperados del ResulSet
 */

public class LatlongRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Latlong latlong = new Latlong();

		latlong.setLatitud(rs.getDouble("latitud"));
		latlong.setLongitud(rs.getDouble("longitud"));
		
		return latlong;

	}

}
