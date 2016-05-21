package com.rna.login;

//import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;



public class UserLogin {
	@NotEmpty(message = "Please enter your username.")
	private String username;
	
	@NotEmpty(message = "Please enter your password.")
	private String password;
	private String userType;

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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}