package com.rna.register;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

//import javax.validation.constraints.Max;
//import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;


public class UserSignUp {
	@NotEmpty
	@Pattern(regexp="[^0-9]*")
	private String name;
	
	
	@NotNull
	private Long mobile;
	
	@Size(max=10)
	@NotEmpty
	private String username;
	
	@Size(min=6,max=15)
	@NotEmpty
	private String password;
	
	@Email
	private String email;

	private Address userAddress;
	
	private String userType;

	public Address getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}

	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
