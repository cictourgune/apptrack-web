package org.tourgune.egistour.bean;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@XmlRootElement(name = "puntos")
public class PuntoList {

	private Collection<Punto> puntos;
	private int rowCount;

	public PuntoList() {
		super();
		puntos = new ArrayList<Punto>();
	}

	@XmlElementRef
	public Collection<Punto> getPuntos() {
		return puntos;
	}

	public void setPuntos(Collection<Punto> puntos) {
		this.puntos = puntos;
	}

	public boolean addPuntos(Punto punto) {
		return this.puntos.add(punto);
	}

	@XmlTransient
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

}
