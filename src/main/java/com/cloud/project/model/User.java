package com.cloud.project.model;

public class User {
	
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	
	// TABLE CONSTANTS
	public final static String USER_TABLE = "USERS";
	public final static String FLD_ID = "ID";
	public final static String FLD_USERNAME = "USERNAME";
	public final static String FLD_PASSWORD = "PASSWORD";
	public final static String FLD_FIRST_NAME = "FIRSTNAME";
	public final static String FLD_LAST_NAME = "LASTNAME";
	public final static String FLD_EMAIL = "EMAIL";

	public User(Long id, String username, String password, String email, String firstName, String lastName) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + "]";
	}

}
