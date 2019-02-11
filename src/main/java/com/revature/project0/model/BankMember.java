package com.revature.project0.model;

import java.io.Serializable;

public class BankMember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8839982032215934509L;
	private int memberId;
	private String username;
	transient private String password;
	private boolean isAdmin;
	private String lastName;
	private String firstName;
	private String email;
	private String address;
	private String state;
	private String city;
	
	public BankMember() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BankMember(int memberId, String username, String password, boolean isAdmin, String lastName,
			String firstName, String email, String address, String state, String city) {
		super();
		this.memberId = memberId;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.address = address;
		this.state = state;
		this.city = city;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
