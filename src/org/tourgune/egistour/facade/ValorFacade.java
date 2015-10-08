package org.tourgune.egistour.facade;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.egistour.bean.Muestra;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.dao.ValorDao;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service("valorFacade")
@Transactional
public class ValorFacade {

	@Resource
	private ValorDao valorDao;

	
	/**
	 * Método de devuelve la lista de puntos que cumplen con el filtro de búsqueda
	 * 
	 * @param idvariable Identificador de la variable de la cual se quieren consultar datos estadísticos
	 * @param idaplicacion Identificador de la aplicación de la cual se quieren consultar datos estadísticos
	 * @param fecha_desde Filtro de tiempo de la búsqueda
	 * @param fecha_hasta Filtro de tiempo de la búsqueda
	 * @param tipo 
	 * @return
	 */
	
	public List<Valor> getDatosEstadisticos(int idvariable,
			int idaplicacion, String fecha_desde, String fecha_hasta, int tipo) {
		return valorDao.getEstadisticas(idvariable, idaplicacion, fecha_desde,
				fecha_hasta,tipo);
	}
	
	public List<Muestra> getMuestraEstadisticos(int idaplicacion, String fecha_desde, String fecha_hasta)
	{
		return valorDao.getMuestra(idaplicacion, fecha_desde, fecha_hasta);
	}

	
}
