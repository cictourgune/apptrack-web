package org.tourgune.egistour.bean;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Bean de la tabla opciones de la base de datos
 */

public class Opcion {

	private int idvariable;
	private String nombreopcion;

	public int getIdvariable() {
		return idvariable;
	}

	public void setIdvariable(int idvariable) {
		this.idvariable = idvariable;
	}

	public String getNombreopcion() {
		return nombreopcion;
	}

	public void setNombreopcion(String nombreopcion) {
		this.nombreopcion = nombreopcion;
	}

}
