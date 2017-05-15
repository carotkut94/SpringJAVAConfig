package com.death.dbChnageWriter;

import java.sql.Timestamp;

public class Users {
	
	private UserDetails details;
	
	private int id;
	private String username;
	private String password;
	private String alias;
	private Timestamp timestamp;
	
	public UserDetails getDetails() {
		return details;
	}
	public void setDetails(UserDetails details) {
		this.details = details;
	}
	public int getId() {
		return id;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
