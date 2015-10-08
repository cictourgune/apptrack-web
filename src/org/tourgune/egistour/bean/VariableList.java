package org.tourgune.egistour.bean;

import java.util.Collection;
//import java.util.List;

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
@XmlRootElement(name = "variables")
public class VariableList {
	private Collection<Variable> variables;
	private int rowCount;

	@XmlElementRef
	public Collection<Variable> getVariables() {
		return variables;
	}

	public void setVariables(Collection<Variable> variables) {
		this.variables = variables;
	}

	@XmlTransient
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
