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
@XmlRootElement(name = "latlongs")
public class LatlongList {

	private Collection<Latlong> latlongs;
	private int rowCount;

	public LatlongList() {
		super();
		latlongs = new ArrayList<Latlong>();
	}

	@XmlElementRef
	public Collection<Latlong> getLatlongs() {
		return latlongs;
	}

	public void setLatlongs(Collection<Latlong> latlongs) {
		this.latlongs = latlongs;
	}

	public boolean addLatlongs(Latlong latlong) {
		return this.latlongs.add(latlong);
	}

	@XmlTransient
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

}
