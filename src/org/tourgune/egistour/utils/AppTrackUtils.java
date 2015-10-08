package org.tourgune.egistour.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service
public class AppTrackUtils {

	public static String DOMAIN = Configuration.getInstance().getProperty(
			Configuration.IP);
	public static String PORT = Configuration.getInstance().getProperty(
			Configuration.PORT);
	public static String ROOT = Configuration.getInstance().getProperty(
			Configuration.ROOT);
	public static String BASEURL = "http://" + DOMAIN + ":" + PORT + "/" + ROOT;

	public static String APIURL = BASEURL + "/api";
	public static String API_USER = APIURL + "/user";
	public static String API_QR = "/open";

	/**
	 * Este método convierte un String separado por ";" en una lista de valores
	 * 
	 * @param values Valores separados por ";"
	 * @return Lista de valores resultante
	 */
	public List<String> stringToArray(String values) {
		String[] arrayValues = values.split(";");
		List<String> array = new ArrayList<String>();
		// En este momento tenemos un array en el que cada elemento es una
		// opcion.
		for (int i = 0; i < arrayValues.length; i++) {
			String y = arrayValues[i].trim();// para quitar los primeros y
												// �ltimos espacios
			if (y.length() > 0) {
				array.add(y);
			}

		}
		return array;

	}

	/**
	 * Este método convierte un texto en formato fecha, en un objeto de tipo Date
	 * 
	 * @param fecha Valor en formato texto de la fecha
	 * @return Objeto Date resultante
	 */
	
	public Date convertStringToDate(String fecha) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaData = null;

		try {
			fechaData = df.parse(fecha);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fechaData;

	}

}
