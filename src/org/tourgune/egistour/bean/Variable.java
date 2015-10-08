package org.tourgune.egistour.bean;


/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Bean de la tabla variables de la base de datos
 */

public class Variable {

	private int idvariable;
	private int idaplicacion;
	private int idtipo;
	private String nombrevariable;
	private double max;
	private double min;

	private String fechadesde;
	private String fechahasta;
	private Boolean multiopcion;
	private Boolean obligatorio;

	private java.util.List<Opcion> opciones;

	public java.util.List<Opcion> getOpciones() {
		return opciones;
	}

	public void setOpciones(java.util.List<Opcion> opciones) {
		this.opciones = opciones;
	}

	public int getIdvariable() {
		return idvariable;
	}

	public void setIdvariable(int idvariable) {
		this.idvariable = idvariable;
	}

	public int getIdaplicacion() {
		return idaplicacion;
	}

	public void setIdaplicacion(int idaplicacion) {
		this.idaplicacion = idaplicacion;
	}

	public int getIdtipo() {
		return idtipo;
	}

	public void setIdtipo(int idtipo) {
		this.idtipo = idtipo;
	}

	public String getNombrevariable() {
		return nombrevariable;
	}

	public void setNombrevariable(String nombrevariable) {
		this.nombrevariable = nombrevariable;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
	}

	public Boolean getMultiopcion() {
		return multiopcion;
	}

	public void setMultiopcion(Boolean multiopcion) {
		this.multiopcion = multiopcion;
	}

	public Boolean getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

}
