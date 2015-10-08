package org.tourgune.egistour.bean;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Bean de la tabla desarrolladores de la base de datos
 */

public class Developer {

	private Integer id;
	private String devname;
	private String password;
	private String token;
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDevname() {
		return devname;
	}

	public void setUsername(String devname) {
		this.devname = devname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
