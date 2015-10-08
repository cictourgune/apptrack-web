package org.tourgune.egistour.bean;

/**
 * AppTrack
 * 
 * Created by CICtourGUNE on 10/04/13. Copyright (c) 2013 CICtourGUNE. All
 * rights reserved.
 * 
 * Bean de la tabla visores de la base de datos
 */
public class Visor {

	int idvisor;
	int idaplicacion;
	String usuario;
	String pwd;
	double latitud;
	double longitud;
	int zoom;

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

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getIdvisor() {
		return idvisor;
	}

	public void setIdvisor(int idvisor) {
		this.idvisor = idvisor;
	}

	public int getIdaplicacion() {
		return idaplicacion;
	}

	public void setIdaplicacion(int idaplicacion) {
		this.idaplicacion = idaplicacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
