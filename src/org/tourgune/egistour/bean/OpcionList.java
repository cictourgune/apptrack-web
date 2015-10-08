package org.tourgune.egistour.bean;

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
@XmlRootElement(name = "opciones")
public class OpcionList {

	private Collection<Opcion> opciones;
	private int rowCount;

	@XmlElementRef
	public Collection<Opcion> getVariables() {
		return opciones;
	}

	public void setVariables(Collection<Opcion> opciones) {
		this.opciones = opciones;
	}

	@XmlTransient
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
