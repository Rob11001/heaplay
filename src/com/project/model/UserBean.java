package com.project.model;

import java.io.Serializable;

public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String username;
	private String email;
	private String password;
	private String auth;
	private boolean active;
	
	public UserBean() {
		id = -1;
		username = email = password = auth = "";
		active = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", auth=" + auth + ", active=" + active + "]";
	}

}
