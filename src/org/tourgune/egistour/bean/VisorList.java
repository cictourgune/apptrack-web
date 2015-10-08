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
@XmlRootElement(name = "visores")
public class VisorList {

	private Collection<Visor> visores;
	private int rowCount;

	@XmlElementRef
	public Collection<Visor> getVisores() {
		return visores;
	}

	public void setqrs(Collection<Visor> visores) {
		this.visores = visores;
	}

	@XmlTransient
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

}
