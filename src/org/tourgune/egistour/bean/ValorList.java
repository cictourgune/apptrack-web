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
@XmlRootElement(name = "valores")
public class ValorList {
	private Collection<Valor> valores;
	private int rowCount;

	public ValorList() {
		super();
		valores = new ArrayList<Valor>();
	}

	@XmlElementRef
	public Collection<Valor> getValores() {
		return valores;
	}

	public void setValores(Collection<Valor> valores) {
		this.valores = valores;
	}

	public boolean addValores(Valor valor) {
		return this.valores.add(valor);
	}

	@XmlTransient
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
