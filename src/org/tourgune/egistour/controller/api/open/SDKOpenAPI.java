package org.tourgune.egistour.controller.api.open;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.egistour.bean.Punto;
import org.tourgune.egistour.bean.PuntoList;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.bean.ValorList;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.facade.SDKFacade;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Controlador publico encargado tratar peticiones HTTP relacionadas con la gestion de la aplicacion movil
 */
@Controller
@RequestMapping("/open/sdk/")

public class SDKOpenAPI {

		@Resource
		private SDKFacade sdkFacade;
		
		/**
		 * Metodo que consulta en la base de datos una variable dado su identificador
		 * 
		 * @param idVariable Identificador de la variable
		 * @param devToken Token del desarrollador
		 * @param appToken Token de la aplicacion
		 * @param imei Identificador unico del dispositivo
		 * @return Si existe una variable con el identificador idVariable, devuelve la variable, sino un objeto nulo
		 */
		@RequestMapping(value = "variable/{idVariable}", method = RequestMethod.GET)
		public @ResponseBody Variable getVariable(@PathVariable(value = "idVariable") String idVariable, 
					@RequestParam(value = "devToken", required = true)String devToken, 
					@RequestParam(value = "appToken", required = true)String appToken, 
					@RequestParam(value = "imei", required = true)String imei){

			Variable var = null;
			
	    	int idApp = sdkFacade.login(devToken, appToken);
	    	
	    	if (idApp != -1){
	    		
	    		int idUser = sdkFacade.existImei(imei, idApp);
	    		if ( idUser == -1){
	    			idUser = sdkFacade.createUser(imei, idApp);
	    		}
	    		
	    		return sdkFacade.getVariable(Integer.parseInt(idVariable));    	}
	    	else
	    		return var;

			
		}	
		
		
		
		
		
		
		
		
		/**
		 * Metodo que consulta en la base de datos el identificador de la variable plataforma
		 * 
		 * @param devToken Token del desarrollador
		 * @param appToken Token de la aplicacion
		 * @param imei Identificador unico del dispositivo
		 * @return El identificador de la variable plataforma de la aplicación
		 */
		@RequestMapping(value = "variable/plataforma", method = RequestMethod.GET)
		public @ResponseBody Integer getPlataforma( 
					@RequestParam(value = "devToken", required = true)String devToken, 
					@RequestParam(value = "appToken", required = true)String appToken, 
					@RequestParam(value = "imei", required = true)String imei){

			
	    	int idApp = sdkFacade.login(devToken, appToken);
	    	
	    	if (idApp != -1){
	    		
	    		int idUser = sdkFacade.existImei(imei, idApp);
	    		if ( idUser == -1){
	    			idUser = sdkFacade.createUser(imei, idApp);
	    		}
	    		
	    		return sdkFacade.getPlataforma(appToken);    	}
	    	else
	    		return 0;

			
		}	
		
		
		
		
		
		
		
		
		/**
		 * Metodo que recoje una lista de valores, la recorre y si los valores son correctos los va insertando en la base de datos.
		 * 
		 * @param valores Una lista de objetos de tipo 
		 * @param devToken Token del desarrollador
		 * @param appToken Token de la aplicacion
		 * @param imei Identificador unico del dispositivo
		 * @return El metodo devuelve una cadena de caracteres en la que se especifican los identificadores de los valores insertados 
		 * en la base de datos y los no validos.
		 */
		@RequestMapping(value = "value/add", method = RequestMethod.POST, consumes = "application/json")
		public @ResponseBody String createValue(@RequestBody ValorList valores, 
				@RequestParam(value = "devToken", required = true)String devToken, 
				@RequestParam(value = "appToken", required = true)String appToken, 
				@RequestParam(value = "imei", required = true)String imei){
			int validez = 1;
			int idUser;
		int idApp = sdkFacade.login(devToken, appToken);
		String result = "";
		int insercion;

		if (idApp != -1){
			idUser = sdkFacade.existImei(imei, idApp);
			if ( idUser == -1){
				idUser = sdkFacade.createUser(imei, idApp);
			}
			String insertados ="";
			String novalidos ="";
			Iterator<Valor> itr = valores.getValores().iterator();
			while(itr.hasNext()) {
				
				Valor valor = itr.next();
				Variable var = sdkFacade.getVariable(valor.getIdvariable());
				if (var.getIdtipo() == 1  || var.getIdtipo() == 4){
					if(Double.parseDouble(valor.getValorvariable()) >= var.getMin() && Double.parseDouble(valor.getValorvariable()) <= var.getMax()){	
						if(sdkFacade.existValor(valor.getIdvariable(), idUser) != -1)
							insercion = sdkFacade.updateValor(valor, idUser);
						else
							insercion = sdkFacade.createValue(valor, idUser);
						if (insercion != 1)
							validez = 0;
						else
							insertados = insertados.concat(Integer.toString(valor.getIdvariable()) + ", ");
		
					}
					else {
						validez = 0;
						novalidos = novalidos.concat(Integer.toString(valor.getIdvariable()) + ", ");
					}
				}
				else if (var.getIdtipo() == 3){

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd"); 
					
				  // El m�todo parse devuelve null si no se ha podido parsear el string  seg�n el formato indicado. Este m�todo lanza una excepci�n NullPointer  exception si alguno de sus par�metros es null 
					  Date fecha1 = new Date();
					  Date fecha2 = new Date();
					  Date fecha3 = new Date();
					try {
						fecha1 = sdf.parse(valor.getValorvariable());
						 fecha2 = sdf.parse(var.getFechadesde());
						 fecha3 = sdf.parse(var.getFechahasta());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}	  

					  if (fecha2.before(fecha1) && fecha1.before(fecha3)){
						 
						  if(sdkFacade.existValor(valor.getIdvariable(), idUser) != -1)
							  insercion = sdkFacade.updateValor(valor, idUser);
						  else
							  insercion = sdkFacade.createValue(valor, idUser);
						  
						  if (insercion != 1)
								validez = 0;
						  else
								insertados = insertados.concat(Integer.toString(valor.getIdvariable()) + ", ");
					  }
					  else {
							validez = 0;
							novalidos = novalidos.concat(Integer.toString(valor.getIdvariable()) + ", ");
						}						
					}
				else if (var.getIdtipo() == 2){
					boolean existe = true;
					if(var.getMultiopcion()){
						int indice;
						
						String cadenaValores = valor.getValorvariable();
						indice = cadenaValores.indexOf(";");

						while (indice != -1 && existe){
							String primerValor  = cadenaValores.substring(0, indice);
							if(sdkFacade.existOption(valor.getIdvariable(), primerValor) == -1){
								existe = false;
							}
							
							cadenaValores = cadenaValores.substring(indice + 1);
							indice = cadenaValores.indexOf(";");
						}
						if (existe && indice == -1){
							String primerValor  = cadenaValores;
							if(sdkFacade.existOption(valor.getIdvariable(), primerValor) == -1)
								existe = false;
						}
					}
					else{
						if(sdkFacade.existOption(valor.getIdvariable(), valor.getValorvariable()) == -1)
							existe = false;
					}
					
					
					if(existe){
						if(sdkFacade.existValor(valor.getIdvariable(), idUser) != -1)
							insercion = sdkFacade.updateValor(valor, idUser);
						else
							insercion = sdkFacade.createValue(valor, idUser);
						  if (insercion != 1)
								validez = 0;
						  else
								insertados = insertados.concat(Integer.toString(valor.getIdvariable()) + ", ");
					  }
					  else {
							validez = 0;
							novalidos = novalidos.concat(Integer.toString(valor.getIdvariable()) + ", ");
						}						
					}
				
				else if (var.getIdtipo() == -1){
					validez = 0;
					novalidos = novalidos.concat(Integer.toString(valor.getIdvariable()) + ", ");
				}
					
				}
			if (insertados != "")
				insertados = insertados.substring(0, insertados.length() - 2);
			if (novalidos != "")
				novalidos = novalidos.substring(0, novalidos.length() - 2);
			
			result = "insertados: " + insertados + "; novalidos: " + novalidos;
			
		}
		else{
			result = "error: devToken o AppToken incorrecto";
			validez = 0;
		}
		if (validez == 1)
			return Integer.toString(validez);
		else
			return result;
						
		}
	
		/**
		 * Metodo que inserta un objeto de tipo punto en la base de datos
		 * 
		 * @param point Objeto de tipo punto a almacenar en la base de datos
		 * @param devToken Token del desarrollador
		 * @param appToken Token de la aplicacion
		 * @param imei Identificador unico del dispositivo
		 * @return 1 si el punto es insertado correctamente, -1 en caso contrario
		 */
		@RequestMapping(value = "point/add", method = RequestMethod.POST, consumes = "application/json")
		public @ResponseBody Integer createPoint(@RequestBody Punto point,
				@RequestParam(value = "devToken", required = true)String devToken, 
				@RequestParam(value = "appToken", required = true)String appToken, 
				@RequestParam(value = "imei", required = true)String imei){
		
			
		int idApp = sdkFacade.login(devToken, appToken);
	    	
	    	if (idApp != -1){
	    		
	    		int idUser = sdkFacade.existImei(imei, idApp);
	    		if ( idUser == -1){
	    			idUser = sdkFacade.createUser(imei, idApp);
	    		}
	    		
	    		return sdkFacade.createPoint(point, idUser);    	
	    		}
	    	else
	    		return -1;
		}
		
		/**
		 * Metodo que almacena el modo en el que se captura la posicion en el dispositivo (GPS / WiFi)
		 * 
		 * @param conexion Cadena de caracteres que especifica el modo de captura de la posicion del dispositivo (GPS / WiFi)
		 * @param devToken Token del desarrollador
		 * @param appToken Token de la aplicacion
		 * @param imei Identificador unico del dispositivo
		 * @return 1 si la conexion es insertada correctamente, -1 en caso contrario
		 */
		@RequestMapping(value = "application/conectivity", method = RequestMethod.POST, consumes = "application/json")
		public @ResponseBody Integer updateConectivity(@RequestBody String conexion,
				@RequestParam(value = "devToken", required = true)String devToken, 
				@RequestParam(value = "appToken", required = true)String appToken, 
				@RequestParam(value = "imei", required = true)String imei){
		
			
			int idApp = sdkFacade.login(devToken, appToken);
	    	if (idApp != -1){
	    		
	    		int idUser = sdkFacade.existImei(imei, idApp);
	    		if ( idUser == -1){
	    			idUser = sdkFacade.createUser(imei, idApp);
	    		}
	    		if (sdkFacade.existAppConectivity(idApp) == 1){
	    			return 1;
	    		}
	    		else{
	    			return (sdkFacade.updateAppConectivity(idApp, conexion));
	    		}
	    	}
	    	else{
	    		return -1;	
	    	}
		}
		
		
		/**
		 * Metodo que inserta un lista de puntos en la base de datos
		 * 
		 * @param points Lista de puntos a almacenar en la base de datos
		 * @param devToken Token del desarrollador
		 * @param appToken Token de la aplicacion
		 * @param imei Identificador unico del dispositivo
		 * @return 1 si todos los puntos se han insertado correctamente
		 * -1 si hay aun error y no se inserta ningun punto
		 * En caso contrario, se inserta el numero de puntos insertados
		 */
		@RequestMapping(value = "point/addmulti", method = RequestMethod.POST, consumes = "application/json")
		public @ResponseBody Integer createPoints(@RequestBody PuntoList points,
				@RequestParam(value = "devToken", required = true)String devToken, 
				@RequestParam(value = "appToken", required = true)String appToken, 
				@RequestParam(value = "imei", required = true)String imei){
		
			
			int idApp = sdkFacade.login(devToken, appToken);
	    	int insertados = 0;
	    	if (idApp != -1){
	    		
	    		int idUser = sdkFacade.existImei(imei, idApp);
	    		if ( idUser == -1){
	    			idUser = sdkFacade.createUser(imei, idApp);
//	    			idUser = sdkFacade.existImei(imei, idApp);
	    		}
	    		
	    		Iterator<Punto> itr = points.getPuntos().iterator();
	    		while(itr.hasNext()) {
	    			Punto point = itr.next();
	    			int x = sdkFacade.createPoint(point, idUser);
	    			if (x == 1){
	    				insertados++;
	    			}
	    			else{
	    				break;
	    			}
	    		}
	    		return insertados;
	    	}
	    	else
	    		return -1;
		}
		
}
