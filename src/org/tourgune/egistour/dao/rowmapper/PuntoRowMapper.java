package org.tourgune.egistour.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.egistour.bean.Punto;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la interface RowMapper usada por JdbcTemplate para el
 * mapeo de los objetos de tipo Punto recuperados del ResulSet
 */

public class PuntoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Punto punto = new Punto();

		punto.setIdpunto(rs.getInt("idpunto"));
		punto.setIdusuario(rs.getInt("idusuario"));
		punto.setLatitud(rs.getDouble("latitud"));
		punto.setLongitud(rs.getDouble("longitud"));
		punto.setFecha(rs.getString("fecha"));
		return punto;

	}

}
