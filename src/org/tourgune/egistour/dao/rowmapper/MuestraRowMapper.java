package org.tourgune.egistour.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.egistour.bean.Muestra;

public class MuestraRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Muestra muestra = new Muestra();
		muestra.setFecha(rs.getString("fecha"));	
		muestra.setCantidad(rs.getInt("cantidad"));
		return muestra;
	
	}

}
