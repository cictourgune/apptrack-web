package org.tourgune.egistour.bean;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Bean de la tabla valores de la base de datos
 */
@XmlRootElement(name = "valor")
public class Valor {
	int idvalor;
	int idvariable;
	String valorvariable;
	int idusuario;
	double valormax;
	double valormin;
	int tipo;
	int cantidad;

	

	public double getValorMax() {
		return valormax;
	}

	public void setValorMax(double valormax) {
		this.valormax = valormax;
	}

	public double getValorMin() {
		return valormin;
	}

	public void setValorMin(double valormin) {
		this.valormin = valormin;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getIdvalor() {
		return idvalor;
	}

	public void setIdvalor(int idvalor) {
		this.idvalor = idvalor;
	}

	public int getIdvariable() {
		return idvariable;
	}

	public void setIdvariable(int idvariable) {
		this.idvariable = idvariable;
	}

	public String getValorvariable() {
		return valorvariable;
	}

	public void setValorvariable(String valorvariable) {
		this.valorvariable = valorvariable;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
