package org.tourgune.egistour.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.tourgune.egistour.bean.Latlong;
import org.tourgune.egistour.bean.Punto;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.dao.rowmapper.LatlongRowMapper;
import org.tourgune.egistour.dao.rowmapper.PuntoRowMapper;
import org.tourgune.egistour.utils.TablasBD;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la lógica de base de datos sobre los puntos de cada aplicación
 */

@Service
public class PuntoDao {

	
	/**
	 * Metodo que recupera las consultas a realizar para la recuperación de puntos
	 * 
	 * @param listaValores Lista de valores que contiene el filtro de busqueda
	 * @param idaplicacion Identificador de la aplicación que se quiere consultar
	 * @return Devuelve la consulta en formato SQL
	 */
	private String getConsultas(java.util.List<Valor> listaValores,
			int idaplicacion) {
		
		java.util.List<String> consultas = new ArrayList();

		Iterator iter = listaValores.iterator();
	
		while (iter.hasNext()) {
			Valor valor = (Valor) iter.next();
			
			
			if (valor.getTipo() == 1) {
				int vmin = (int) valor.getValorMin();
				int vmax = (int) valor.getValorMax();
				String sql = " (select idusuario from valores where idvariable="
						+ valor.getIdvariable()
						+ " and valorvariable::Integer>="
						+ vmin
						+ " and valorvariable::Integer<=" + vmax + ") ";
				consultas.add(sql);

			}

			if (valor.getTipo() == 2) {

				String valoresArray = valor.getValorvariable().replace(",", "','");
				
				
				String sql = " (select idusuario from valores where idvariable="
						+ valor.getIdvariable()
						+ " and regexp_split_to_array (valorvariable,';')::text[] @> ARRAY['" + valoresArray +"']::text[] ='t')";
					
				
				System.out.println("SQL:" + sql);

				consultas.add(sql);

			}

			if (valor.getTipo() == 3) {
				String sql = " (select idusuario from valores where idvariable="
						+ valor.getIdvariable()
						+ " and valorvariable='"
						+ valor.getValorvariable() + "') ";
				consultas.add(sql);
			}
			if (valor.getTipo() == 4) {
				String sql = " (select idusuario from valores where idvariable="
						+ valor.getIdvariable()
						+ " and valorvariable::Decimal>="
						+ valor.getValorMin()
						+ " and valorvariable::Decimal<="
						+ valor.getValorMax()
						+ ") ";
				consultas.add(sql);

			}

		}

		Iterator iter2 = consultas.iterator();
		String consultaCompleta = "";
		while (iter2.hasNext()) {
			String consulta = (String) iter2.next();
			consultaCompleta = consultaCompleta + consulta + " INTERSECT ";
		}

		
		if (consultaCompleta.length() < 2)
			return "select idusuario from usuarios where idaplicacion="
					+ idaplicacion;
		else
			return consultaCompleta
					.substring(0, consultaCompleta.length() - 10);

	}

	/**
	 * Método de devuelve la lista de puntos que cumplen con el filtro de búsqueda
	 * 
	 * @param listaValores Lista que contiene los valores de la busqueda
	 * @param idaplicacion Identificador de la aplicación de la cual se quieren consultar los puntos
	 * @param fecha_desde Filtro de tiempo de la búsqueda
	 * @param fecha_hasta Filtro de tiempo de la búsqueda
	 * @return
	 */
	public List<Punto> getPuntos(java.util.List<Valor> listaValores,
			int idaplicacion, String fecha_desde, String fecha_hasta, int horaMin, int horaMax) {
		
		
		
		String consulta = getConsultas(listaValores, idaplicacion);
		String consultaCompleta = "";

		if (fecha_desde.equals(fecha_hasta)) {
			consultaCompleta = "select idpunto, idusuario, latitud, longitud, fecha from puntos where idusuario in ("
					+ consulta + ") and fecha::date =" + fecha_desde + "::date and date_part('hour', fecha)>="+horaMin +" and date_part('hour', fecha)<="+horaMax;
		} else {
			consultaCompleta = "select idpunto, idusuario, latitud, longitud, fecha from puntos where idusuario in ("
					+ consulta + ") and fecha::date between " + fecha_desde
					+ "::date and " + fecha_hasta + "::date and date_part('hour', fecha)>="+horaMin +" and date_part('hour', fecha)<="+horaMax;
		}

		
		//cl1
		//consultaCompleta = "select idpunto, idusuario, latitud, longitud, fecha from puntos where idusuario in (3353,3402,3423,3430,3479,3532,3541,3592,3621,3631,3672,3673,3679,3680,3769,3777,3793,3809,3817,3829,3835,3876,3912,3927,3959,3979,3981,3992,4006,4019,4029,4062,4109,4168,4182,4240,4274,4354,4361,4444,4512,4564,4621,4644,4668,4796,4805,4814,4868,4882,4901,4909,4917)";
		
		//cl2
		//consultaCompleta = "select idpunto, idusuario, latitud, longitud, fecha from puntos where idusuario in (3355, 3395, 3413, 3455, 3458, 3485, 3500, 3512, 3520, 3537, 3542, 3593, 3595, 3609, 3637, 3641, 3659, 3671, 3686, 3696, 3714, 3718, 3733, 3744, 3755, 3765, 3779, 3785, 3800, 3827, 3843, 3868, 3877, 3907, 3924, 3951, 3961, 3970, 3973, 3996, 4034, 4108, 4140, 4157, 4166, 4179, 4185, 4221, 4364, 4387, 4551, 4652, 4659, 4664, 4716, 4757, 4759, 4764, 4774, 4777, 4877, 4879, 4892, 4930, 4945, 5151)";
		
		
		//cl3
		//consultaCompleta = "select idpunto, idusuario, latitud, longitud, fecha from puntos where idusuario in (3361, 3387, 3396, 3408, 3421, 3425, 3426, 3472, 3475, 3506, 3514, 3533, 3538, 3545, 3550, 3562, 3569, 3581, 3584, 3604, 3615, 3619, 3624, 3632, 3661, 3676, 3678, 3690, 3695, 3697, 3712, 3713, 3724, 3731, 3746, 3756, 3781, 3792, 3798, 3804, 3813, 3832, 3885, 3888, 3903, 3956, 3971, 3991, 3999, 4007, 4009, 4021, 4030, 4032, 4039, 4042, 4054, 4060, 4101, 4103, 4105, 4158, 4160, 4162, 4192, 4216, 4217, 4257, 4272, 4277, 4286, 4315, 4347, 4348, 4360, 4368, 4426, 4430, 4484, 4488, 4492, 4536, 4542, 4560, 4639, 4641, 4797, 4905, 4906, 4925, 4938, 4949, 4954, 4955, 4958, 4963)";
		
		
		//cl4
		//consultaCompleta = "select idpunto, idusuario, latitud, longitud, fecha from puntos where idusuario in (3367, 3400, 3454, 3517, 3549, 3594, 3630, 3704, 3802, 3830, 3865, 3900, 3908, 3913, 4014, 4131, 4219, 4242, 4316, 4437, 4442, 4517, 4698, 4932, 4950)";
		
		//consultaCompleta =  "select idpunto, idusuario, latitud, longitud, fecha from puntos where idpunto in (select puntos.idpunto from puntos, usuarios where puntos.idusuario=usuarios.idusuario and usuarios.idaplicacion = 40)";
		//System.out.println("consulta completa: " + consultaCompleta);
		List<Punto> listaPuntos = null;
		try {

			
//			System.out.println("Antes de la consulta: " + System.currentTimeMillis());
			
			listaPuntos = (List<Punto>) jdbcTemplate.query(consultaCompleta,
					new PuntoRowMapper());
			
//			System.out.println("Despues de la consulta: " + System.currentTimeMillis());
			
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}

		System.out.println("tamano lista: " + listaPuntos.size());
		
		return listaPuntos;
	}
	
	
	
	
	
	
	public List<Latlong> getLatlongs(java.util.List<Valor> listaValores,
			int idaplicacion, String fecha_desde, String fecha_hasta, int horaMin, int horaMax) {
		
		
		String consulta = getConsultas(listaValores, idaplicacion);
		String consultaCompleta = "";

		if (fecha_desde.equals(fecha_hasta)) {
			consultaCompleta = "select latitud, longitud from puntos where idusuario in ("
					+ consulta + ") and fecha::date =" + fecha_desde + "::date and date_part('hour', fecha)>="+horaMin +" and date_part('hour', fecha)<="+horaMax;
		} else {
			consultaCompleta = "select latitud, longitud from puntos where idusuario in ("
					+ consulta + ") and fecha::date between " + fecha_desde
					+ "::date and " + fecha_hasta + "::date and date_part('hour', fecha)>="+horaMin +" and date_part('hour', fecha)<="+horaMax;
		}

		
		//cl1
		//consultaCompleta = "select latitud, longitud from puntos where idusuario in (3353,3402,3423,3430,3479,3532,3541,3592,3621,3631,3672,3673,3679,3680,3769,3777,3793,3809,3817,3829,3835,3876,3912,3927,3959,3979,3981,3992,4006,4019,4029,4062,4109,4168,4182,4240,4274,4354,4361,4444,4512,4564,4621,4644,4668,4796,4805,4814,4868,4882,4901,4909,4917)";
		
		//cl2
		//consultaCompleta = "select latitud, longitud from puntos where idusuario in (3355, 3395, 3413, 3455, 3458, 3485, 3500, 3512, 3520, 3537, 3542, 3593, 3595, 3609, 3637, 3641, 3659, 3671, 3686, 3696, 3714, 3718, 3733, 3744, 3755, 3765, 3779, 3785, 3800, 3827, 3843, 3868, 3877, 3907, 3924, 3951, 3961, 3970, 3973, 3996, 4034, 4108, 4140, 4157, 4166, 4179, 4185, 4221, 4364, 4387, 4551, 4652, 4659, 4664, 4716, 4757, 4759, 4764, 4774, 4777, 4877, 4879, 4892, 4930, 4945, 5151)";

		
		//cl3
		//consultaCompleta = "select  latitud, longitud from puntos where idusuario in (3361, 3387, 3396, 3408, 3421, 3425, 3426, 3472, 3475, 3506, 3514, 3533, 3538, 3545, 3550, 3562, 3569, 3581, 3584, 3604, 3615, 3619, 3624, 3632, 3661, 3676, 3678, 3690, 3695, 3697, 3712, 3713, 3724, 3731, 3746, 3756, 3781, 3792, 3798, 3804, 3813, 3832, 3885, 3888, 3903, 3956, 3971, 3991, 3999, 4007, 4009, 4021, 4030, 4032, 4039, 4042, 4054, 4060, 4101, 4103, 4105, 4158, 4160, 4162, 4192, 4216, 4217, 4257, 4272, 4277, 4286, 4315, 4347, 4348, 4360, 4368, 4426, 4430, 4484, 4488, 4492, 4536, 4542, 4560, 4639, 4641, 4797, 4905, 4906, 4925, 4938, 4949, 4954, 4955, 4958, 4963)";

		//cl4
		//consultaCompleta = "select latitud, longitud from puntos where idusuario in (3367, 3400, 3454, 3517, 3549, 3594, 3630, 3704, 3802, 3830, 3865, 3900, 3908, 3913, 4014, 4131, 4219, 4242, 4316, 4437, 4442, 4517, 4698, 4932, 4950)";

		//consultaCompleta =  "select latitud, longitud from puntos where idpunto in (select puntos.idpunto from puntos, usuarios where puntos.idusuario=usuarios.idusuario and usuarios.idaplicacion = 53)";
		
		//System.out.println("consulta completa: " + consultaCompleta);
		List<Latlong> listaLatlongs = null;
		try {

			
			System.out.println("Antes de la consulta: " + System.currentTimeMillis());
			
			listaLatlongs = (List<Latlong>) jdbcTemplate.query(consultaCompleta,
					new LatlongRowMapper());
			
			System.out.println("Despues de la consulta: " + System.currentTimeMillis());
			
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}

		System.out.println("tamano lista: " + listaLatlongs.size());
		
		return listaLatlongs;
	}
	
	
	
	
	
	
	
	
	public int getUsuariosPuntos(java.util.List<Valor> listaValores,
			int idaplicacion, String fecha_desde, String fecha_hasta, int horaMin, int horaMax) {
		
		
		
		String consulta = getConsultas(listaValores, idaplicacion);
		String consultaCompleta = "";

		if (fecha_desde.equals(fecha_hasta)) {
			consultaCompleta = "select count(distinct(idusuario)) from puntos where idusuario in ("
					+ consulta + ") and fecha::date =" + fecha_desde + "::date and date_part('hour', fecha)>="+horaMin +" and date_part('hour', fecha)<="+horaMax;
		} else {
			consultaCompleta = "select count(distinct(idusuario)) from puntos where idusuario in ("
					+ consulta + ") and fecha::date between " + fecha_desde
					+ "::date and " + fecha_hasta + "::date and date_part('hour', fecha)>="+horaMin +" and date_part('hour', fecha)<="+horaMax;
		}

		
		
		Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
		System.out.println("numero usuarios: " + numUsuarios);
		return numUsuarios;
	}

	
	/**
	 * Método que se utiliza para eliminar los puntos de la aplicación
	 * 
	 * @param idaplicacion Identificador de la aplicación que se quieren eliminar los puntos
	 * @return
	 */
	public String deletePuntos(int idaplicacion) {
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM " + TablasBD.TABLE_POINTS + " ");
		sql.append("WHERE " + TablasBD.TABLE_POINTS_USER + " IN (SELECT "
				+ TablasBD.TABLE_USERS_ID + " FROM " + TablasBD.TABLE_USERS
				+ " WHERE " + TablasBD.TABLE_USERS_APP + "=?)");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { idaplicacion };

		types = new int[] { Types.INTEGER };

		if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
			return "1";
		} else {
			return "-1";
		}
	}

	@Resource
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Latlong> getUsersLocation(int idaplicacion, String fecha_desde, String fecha_hasta,
			int horaMin, int horaMax, String usersID) {
		
		String consultaCompleta = "";
		
		if (fecha_desde.equals(fecha_hasta)) {
			consultaCompleta = "select latitud, longitud from puntos where idusuario in ("
					+ usersID + ") and fecha::date =" + fecha_desde + "::date and date_part('hour', fecha)>="+horaMin +" and date_part('hour', fecha)<="+horaMax;
		} else {
			consultaCompleta = "select latitud, longitud from puntos where idusuario in ("
					+ usersID + ") and fecha::date between " + fecha_desde
					+ "::date and " + fecha_hasta + "::date and date_part('hour', fecha)>="+horaMin +" and date_part('hour', fecha)<="+horaMax;
		}
		

		System.out.println("consultaCompleta"+consultaCompleta);		
		List<Latlong> listaLatlongs = null;
		try {

			listaLatlongs = (List<Latlong>) jdbcTemplate.query(consultaCompleta,
					new LatlongRowMapper());

		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
		
		return listaLatlongs;
	}

}
