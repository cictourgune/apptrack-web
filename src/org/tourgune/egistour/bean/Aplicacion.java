package org.tourgune.egistour.bean;


import javax.xml.bind.annotation.XmlRootElement;
/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Bean de la tabla aplicaciones de la base de datos
 */

@XmlRootElement(name = "aplicaciones")
public class Aplicacion {
	private int idaplicacion;
	private int iddesarrollador;
	private String nombreaplicacion;
	private String tokkenaplicacion;
	private String fechacreacion;
	private String descripcion;
	private double latitud;
	private double longitud;
	private int zoom;
	
	private int origen;

	
	public int getZoom() {
		return zoom;
	}


	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public int getIdaplicacion() {
		return idaplicacion;
	}

	public void setIdaplicacion(int idaplicacion) {
		this.idaplicacion = idaplicacion;
	}

	public int getIddesarrollador() {
		return iddesarrollador;
	}

	public void setIddesarrollador(int iddesarrollador) {
		this.iddesarrollador = iddesarrollador;
	}

	public String getNombreaplicacion() {
		return nombreaplicacion;
	}

	public void setNombreaplicacion(String nombreaplicacion) {
		this.nombreaplicacion = nombreaplicacion;
	}

	public String getTokkenaplicacion() {
		return tokkenaplicacion;
	}

	public void setTokkenaplicacion(String tokkenaplicacion) {
		this.tokkenaplicacion = tokkenaplicacion;
	}

	public String getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(String fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public void setOrigen(int origen) {
		this.origen =origen;
	}

	public int getOrigen() {
		return origen;
	}


}
