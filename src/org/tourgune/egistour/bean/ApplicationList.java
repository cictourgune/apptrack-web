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
@XmlRootElement(name = "aplicaciones")
public class ApplicationList {
	private Collection<Aplicacion> aplicaciones;
	private int rowCount;

	@XmlElementRef
	public Collection<Aplicacion> getAplicaciones() {
		return aplicaciones;
	}

	public void setqrs(Collection<Aplicacion> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}

	@XmlTransient
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
