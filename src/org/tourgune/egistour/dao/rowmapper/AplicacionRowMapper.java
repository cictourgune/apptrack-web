package org.tourgune.egistour.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.egistour.bean.Aplicacion;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la interface RowMapper usada por JdbcTemplate para el
 * mapeo de los objetos de tipo Aplicación recuperados del ResulSet
 */

public class AplicacionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Aplicacion aplicacion = new Aplicacion();
		aplicacion.setDescripcion(rs.getString("descripcion"));
		aplicacion.setFechacreacion(rs.getString("fechacreacion"));
		aplicacion.setIdaplicacion(rs.getInt("idaplicacion"));
		aplicacion.setIddesarrollador(rs.getInt("iddesarrollador"));
		aplicacion.setNombreaplicacion(rs.getString("nombreaplicacion"));
		aplicacion.setTokkenaplicacion(rs.getString("tokkenaplicacion"));
		aplicacion.setLatitud(rs.getDouble("latitud"));
		aplicacion.setLongitud(rs.getDouble("longitud"));
		aplicacion.setZoom(rs.getInt("zoom"));

		return aplicacion;

	}

}
