package org.tourgune.egistour.dao;


import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.tourgune.egistour.utils.AppTrackUtils;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 29/01/15.
 * Copyright (c) 2015 CICtourGUNE. All rights reserved.
 * 
 * Clase que implenta la lógica de base de datos relacionada con los usuarios
 */

@Service
public class UsuarioDao {

	/**
	 * Este método devuelve la cantidad total de usuariosque hay en la plataforma para la aplicación seleccionada
	 */
	public int getTotalUsers(Integer idApplication) {

		String consultaCompleta = "Select count(distinct(idusuario)) from usuarios where idaplicacion="+idApplication;
		System.out.println("consultaCompleta"+consultaCompleta);
		try {
			Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
			System.out.println("número usuarios totales: " + numUsuarios);
			return numUsuarios;
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}

	}
	/**
	 * Este método devuelve la cantidad de usuarios recurrentes del mes seleccionado 
	 */
	public int getReturningUsers(Integer idApplication, Integer anio, Integer mes) {
		String mesnuevo;
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		String consultaCompleta="";
		
		consultaCompleta = "Select count(distinct(usuarios.idusuario)) from usuarios, puntos where usuarios.idusuario=puntos.idusuario "
				+ "and usuarios.idusuario in (select usuarios.idusuario from usuarios,puntos where usuarios.idusuario=puntos.idusuario "
				+ "and TO_CHAR(puntos.fecha,'YYYYMM')<'"+anio+""+mesnuevo+"' and usuarios.idaplicacion="+idApplication+") "
				+ "and date_part('month',puntos.fecha) = "+mes+" and date_part('year',puntos.fecha) ="+anio+" "
				+ "and usuarios.idaplicacion="+idApplication;
		
		
	
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {
			Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
			System.out.println("número usuarios recurrentes: " + numUsuarios);
			if (numUsuarios!=null){
				return numUsuarios;
			}else{
				return 0;
			}		
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}

	}
	/**
	 * Este método devuelve la cantidad de nuevos usuarios del mes seleccionado 
	 */
	public int getNewUsers(Integer idApplication, Integer anio, Integer mes) {

		String consultaCompleta="";
		consultaCompleta = "select sum(b.cantidad) "
							+ "from(select a.fecha1,count(distinct(a.usu)) as cantidad "
									+ "from( Select usuarios.idusuario as usu,date_part('day',min(puntos.fecha))as fecha1 "
											+ "from usuarios, puntos "
											+ "where usuarios.idusuario=puntos.idusuario "
											+ "and usuarios.idaplicacion="+idApplication+" "
											+ "group by usuarios.idusuario "
											+ "having date_part('month',min(puntos.fecha))="+mes+" "
											+ "and date_part('year',min(puntos.fecha))="+anio+" "
											+ ")a "
									+ "group by a.fecha1)b";
	
		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {
			Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
			System.out.println("número usuarios nuevos: " + numUsuarios);
			if (numUsuarios!=null){
				return numUsuarios;
			}else{
				return 0;
			}
			
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}

	}
	/**
	 * Este método devuelve la cantidad de usuarios recurrentes y nuevos del mes seleccionado 
	 */
	public List<Map<String, Object>> getNewAndReturningUsersPerMonth(Integer idApplication, Integer anio, Integer mes) {
	
		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		String consultaCompleta="";
		
		
		consultaCompleta="SELECT CASE WHEN COALESCE( newusers,0)=0 THEN fecha2  WHEN COALESCE( retusers,0)=0 THEN fecha1 ELSE fecha1 END as filtro, COALESCE( newusers,0) AS newusers, COALESCE( retusers,0) AS retusers "
				+ "from "
				+ "(select a.fecha1 as fecha1,count(distinct(a.usu)) as newusers "
				+ "from("
				+ "Select usuarios.idusuario as usu,date_part('day',min(puntos.fecha))as fecha1 "
				+ "from usuarios, puntos "
				+ "where usuarios.idusuario=puntos.idusuario "
				+ "and usuarios.idaplicacion="+idApplication+" "
				+ "group by usuarios.idusuario "
				+ "having date_part('month',min(puntos.fecha))="+mes+" "
				+ "and date_part('year',min(puntos.fecha))="+anio+" "
				+ ")a "
				+ "group by a.fecha1 ) as a "
				+ "FULL OUTER JOIN "
				+ "(Select date_part('day',puntos.fecha) as fecha2,count(distinct(usuarios.idusuario)) as retusers "
				+ "from usuarios, puntos "
				+ "where usuarios.idusuario=puntos.idusuario "
				+ "and usuarios.idusuario in (select usuarios.idusuario "
				+ "from usuarios,puntos "
				+ "where usuarios.idusuario=puntos.idusuario "
				+ "and TO_CHAR(puntos.fecha,'YYYYMM')<'"+anio+""+mesnuevo+"'"
				+ "and usuarios.idaplicacion="+idApplication+") "
				+ "and date_part('month',puntos.fecha) = "+(mes)+" "
				+ "and date_part('year',puntos.fecha) ="+(anio)+" "
				+ "and usuarios.idaplicacion="+idApplication+" "
				+ "group by date_part('day',puntos.fecha)) as b "
				+ "ON a.fecha1=b.fecha2";
		
		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			List<Map<String,Object>> numUsuariosPuntos = jdbcTemplate.queryForList(consultaCompleta.toString());
			System.out.println("número de puntos y usuarios por días del mes: " + numUsuariosPuntos.size());
			return numUsuariosPuntos;
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * Este método devuelve la cantidad de usuarios y puntos del mes, agrupados por días del mes seleccionado 
	 */
	public List<Map<String, Object>> getPointsUsersPerMonth(Integer idApplication, Integer anio, Integer mes) {

		String consultaCompleta = "select date_part('day',puntos.fecha) as filtro, count(*) as puntos, count(distinct(usuarios.idusuario)) as usuarios, (count(*)::float/(count(distinct(usuarios.idusuario))::float)) as ratio "
				+ "from puntos, usuarios "
				+ "where puntos.idusuario=usuarios.idusuario "
				+ "and usuarios.idaplicacion="+idApplication+ " "
				+ "and date_part('month',puntos.fecha)="+mes+" "
				+ "and date_part('year',puntos.fecha)="+anio+" "
				+ "group by date_part('day',puntos.fecha) "
				+ "order by date_part('day',puntos.fecha)";
		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			List<Map<String,Object>> numUsuariosPuntos = jdbcTemplate.queryForList(consultaCompleta.toString());
			System.out.println("número de puntos y usuarios por días del mes: " + numUsuariosPuntos.size());
			return numUsuariosPuntos;
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}
	/**
	 * Este método devuelve la cantidad de usuarios y puntos de la semana, del mes seleccionado
	 */
	public List<Map<String, Object>> getPointsUsersPerWeek(Integer idApplication, Integer anio, Integer mes) {

		String consultaCompleta = "select date_part('dow',puntos.fecha) as filtro,count(*) as puntos, count(distinct(usuarios.idusuario)) as usuarios, (count(*)::float/(count(distinct(usuarios.idusuario))::float)) as ratio "
				+ "from puntos, usuarios "
				+ "where puntos.idusuario=usuarios.idusuario "
				+ "and usuarios.idaplicacion="+idApplication+ " "
				+ "and date_part('month',puntos.fecha)="+mes+" "
				+ "and date_part('year',puntos.fecha)="+anio+" "
				+ "group by date_part('dow',puntos.fecha) "
				+ "order by date_part('dow',puntos.fecha)";
		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			List<Map<String,Object>> numUsuariosPuntos = jdbcTemplate.queryForList(consultaCompleta.toString());
			System.out.println("número de puntos y usuarios por día de la semana: " + numUsuariosPuntos.size());
			return numUsuariosPuntos;
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}
	/**
	 * Este método devuelve la cantidad de usuarios y puntos por horas, del mes seleccionado 
	 */
	public List<Map<String, Object>> getPointsUsersPerHour(Integer idApplication, Integer anio, Integer mes) {

		String consultaCompleta = "select date_part('hour',puntos.fecha) as filtro,count(*) as puntos, count(distinct(usuarios.idusuario)) as usuarios, (count(*)::float/(count(distinct(usuarios.idusuario))::float)) as ratio "
				+ "from puntos, usuarios "
				+ "where puntos.idusuario=usuarios.idusuario "
				+ "and usuarios.idaplicacion="+idApplication+ " "
				+ "and date_part('month',puntos.fecha)="+mes+" "
				+ "and date_part('year',puntos.fecha)="+anio+" "
				+ "group by date_part('hour',puntos.fecha) "
				+ "order by date_part('hour',puntos.fecha)";
		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			List<Map<String,Object>> numUsuariosPuntos = jdbcTemplate.queryForList(consultaCompleta.toString());
			System.out.println("número de puntos y usuarios por día de la semana: " + numUsuariosPuntos.size());
			return numUsuariosPuntos;
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}
	/**
	 * Este método devuelve el ranking dependiendo de la comunidad de descarga
	 */
	public List<Map<String, Object>> getDownloadsRanking(Integer idApplication, Integer anio, Integer mes) {

		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		
		String consultaCompleta = "select comunidades.nombre as comunidades, count(*) as cantidad "
				+ "from"
				+ "(select p.comunidad, p.idusuario, max(p.fecha) "
				+ "from puntos p, (select usuarios.idusuario idusuario, min(fecha) as fecha_inicio, max(fecha) as fecha_fin "
				+ "from puntos, aplicaciones,usuarios,  comunidades "
				+ "where puntos.idusuario= usuarios.idusuario "
				+ "and usuarios.idaplicacion=aplicaciones.idaplicacion "
				+ "and comunidades.idarea=puntos.comunidad "
				+ "and aplicaciones.idaplicacion="+idApplication+" "
				+ "and TO_CHAR(puntos.fecha,'YYYYMM')='"+anio+""+mesnuevo+"' "
				+ "group by usuarios.idusuario) a "
				+ "where a.idusuario = p.idusuario and a.fecha_inicio = p.fecha "
				+ "group by p.idusuario, p.comunidad "
				+ "order by p.idusuario) b, comunidades comunidades "
				+ "where comunidades.idarea= b.comunidad "
				+ "group by comunidades.nombre order by cantidad desc";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			List<Map<String,Object>> numUsuariosPuntos = jdbcTemplate.queryForList(consultaCompleta.toString());
			System.out.println("Ranking de descargas: " + numUsuariosPuntos.size());
			return numUsuariosPuntos;
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}
	/**
	 * Este método devuelve la cantidad de usuarios que no entran en el destino
	 */
	public int getUsersNotInDestination(Integer idApplication, Integer anio, Integer mes) {

		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		
		String consultaCompleta = "Select count(distinct(puntos.idusuario)) "
								+ "from puntos, usuarios "
								+ "where puntos.idusuario=usuarios.idusuario "
								+ "and usuarios.idaplicacion="+idApplication+" "
								+ "and TO_CHAR(puntos.fecha,'YYYYMM')='"+anio+""+mesnuevo+"' "
								+ "and usuarios.idusuario not in ( select distinct(puntos.idusuario) "
								+ "from puntos, usuarios "
								+ "where puntos.idusuario=usuarios.idusuario "
								+ "and usuarios.idaplicacion="+idApplication+" "
								+ "and TO_CHAR(puntos.fecha,'YYYYMM')='"+anio+""+mesnuevo+"' "
								+ "and comunidad=(select origen "
								+ "from aplicaciones "
								+ "where idaplicacion="+idApplication+"))";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
			System.out.println("número de puntos y usuarios por día de la semana: " + numUsuarios);
			if (numUsuarios!=null){
				return numUsuarios;
			}else{
				return 0;
			}	
			
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	/**
	 * Este método devuelve el ranking de municipios
	 */
	public List<Map<String, Object>> getTownsRanking(Integer idApplication, Integer anio, Integer mes) {

		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		
		String consultaCompleta = "select m.gid as idmunicipio, m.texto as municipio, count(distinct(u.idusuario)) as cantidad "
				+ "from puntos p, usuarios u, aplicaciones a, municipios m "
				+ "where p.idusuario=u.idusuario "
				+ "and u.idaplicacion=a.idaplicacion "
				+ "and m.gid=p.municipio "
				+ "and a.idaplicacion="+idApplication+" "
				+ "and TO_CHAR(p.fecha,'YYYYMM')='"+anio+""+mesnuevo+"' "
				+ "group by m.gid "
				+ "order by cantidad desc "
				+ "FETCH FIRST 10 ROWS ONLY";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			List<Map<String,Object>> numUsuariosMunicipios = jdbcTemplate.queryForList(consultaCompleta.toString());
			System.out.println("número de usuarios por municipios: " + numUsuariosMunicipios.size());
			return numUsuariosMunicipios;
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * Este método devuelve la cantidad de usuarios de un municipio y mes seleccionados
	 */
	public int getUsersPerOneTownAndMonth(Integer idApplication, Integer anio, Integer mes, Integer idmunicipio) {

		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		
		String consultaCompleta = "select  count(distinct(u.idusuario)) as cantidad "
				+ "from puntos p, usuarios u, aplicaciones a, municipios m "
				+ "where p.idusuario=u.idusuario "
				+ "and u.idaplicacion=a.idaplicacion "
				+ "and m.gid=p.municipio "
				+ "and m.gid ="+ idmunicipio +" "
				+ "and a.idaplicacion="+idApplication +" "
				+ "and TO_CHAR(p.fecha,'YYYYMM')='"+anio+""+mesnuevo+"'";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
			System.out.println("número de usuarios en un municipio y en un mes específico: " + numUsuarios);
			if (numUsuarios!=null){
				return numUsuarios;
			}else{
				return 0;
			}	
			
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}
	/**
	 * Este método devuelve el total de usuarios con municipio de un mes seleccionado
	 */
	public int getTotalUsersOfTowns(Integer idApplication, Integer anio, Integer mes) {

		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		
		//Aqui no se contemplan los usuarios sin municipio
		String consultaCompleta = "select  count(distinct(u.idusuario)) as cantidad "
				+ "from puntos p, usuarios u, aplicaciones a, municipios m "
				+ "where p.idusuario=u.idusuario "
				+ "and u.idaplicacion=a.idaplicacion "
				+ "and m.gid=p.municipio "
				+ "and a.idaplicacion="+idApplication+" "
				+ "and TO_CHAR(p.fecha,'YYYYMM')='"+anio+""+mesnuevo+"'";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
			System.out.println("número de usuarios en un mes específico: " + numUsuarios);
			if (numUsuarios!=null){
				return numUsuarios;
			}else{
				return 0;
			}	
			
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}
	/**
	 * Este método devuelve el ranking por comunidades
	 */
	public List<Map<String, Object>> getRegionsRanking(Integer idApplication, Integer anio, Integer mes) {

		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		
		String consultaCompleta="select c.idarea as idcomunidad, c.nombre as comunidad , count(distinct(p.idusuario)) as cantidad "
				+ "from puntos p, usuarios u, aplicaciones a, comunidades c "
				+ "where p.idusuario=u.idusuario "
				+ "and u.idaplicacion=a.idaplicacion "
				+ "and c.idarea=p.comunidad "
				+ "and a.idaplicacion= "+idApplication+" "
				+ "and TO_CHAR(p.fecha,'YYYYMM')='"+anio+""+mesnuevo+"' "
				+ "group by c.idarea "
				+ "order by cantidad desc "
				+ "FETCH FIRST 10 ROWS ONLY";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			List<Map<String,Object>> numUsuariosMunicipios = jdbcTemplate.queryForList(consultaCompleta.toString());
			System.out.println("número de usuarios por comunidades: " + numUsuariosMunicipios.size());
			return numUsuariosMunicipios;
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * Este método devuelve la cantidad de usuarios de una comunidad y mes seleccionados
	 */
	public int getUsersPerOneRegionAndMonth(Integer idApplication, Integer anio, Integer mes, Integer idcomunidad) {

		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		
		String consultaCompleta = "select  count(distinct(u.idusuario)) as cantidad "
				+ "from puntos p, usuarios u, aplicaciones a, comunidades c "
				+ "where p.idusuario=u.idusuario "
				+ "and u.idaplicacion=a.idaplicacion "
				+ "and c.idarea=p.comunidad "
				+ "and c.idarea ="+ idcomunidad +" "
				+ "and a.idaplicacion="+idApplication +" "
				+ "and TO_CHAR(p.fecha,'YYYYMM')='"+anio+""+mesnuevo+"'";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
			System.out.println("número de usuarios en una comunidad y en un mes específico: " + numUsuarios);
			if (numUsuarios!=null){
				return numUsuarios;
			}else{
				return 0;
			}	
			
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}
	/**
	 * Este método devuelve el total de usuarios con comunidad de un mes seleccionado
	 */
	public int getTotalUsersOfRegions(Integer idApplication, Integer anio, Integer mes) {

		String mesnuevo="";
		if (mes<10){
			mesnuevo="0"+mes;
		}else{
			mesnuevo=mes+"";
		}
		
		//Aqui no se contemplan los usuarios sin comunidad
		String consultaCompleta = "select  count(distinct(u.idusuario)) as cantidad "
				+ "from puntos p, usuarios u, aplicaciones a, comunidades c "
				+ "where p.idusuario=u.idusuario "
				+ "and u.idaplicacion=a.idaplicacion "
				+ "and c.idarea=p.comunidad "
				+ "and a.idaplicacion="+idApplication+" "
				+ "and TO_CHAR(p.fecha,'YYYYMM')='"+anio+""+mesnuevo+"'";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			Integer numUsuarios = jdbcTemplate.queryForObject(consultaCompleta.toString(),null, Integer.class);
			System.out.println("número de usuarios en un mes específico: " + numUsuarios);
			if (numUsuarios!=null){
				return numUsuarios;
			}else{
				return 0;
			}	
			
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	
	public List<Map<String, Object>> getYears(Integer idApplication) {


		
		String consultaCompleta="select distinct(extract(year from fecha)) as years "
								+ "from puntos, usuarios "
								+ "where puntos.idusuario=usuarios.idusuario "
								+ "and usuarios.idaplicacion="+idApplication+" "
								+ "and puntos.fecha::date <= current_date "
								+ "order by years";

		
		System.out.println("consultaCompleta"+consultaCompleta);
		
		try {

			List<Map<String,Object>> cantidadAños = jdbcTemplate.queryForList(consultaCompleta.toString());
			System.out.println("años: " + cantidadAños.size());
			return cantidadAños;
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	
	@Resource
	private AppTrackUtils appTrackUtils;

	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
