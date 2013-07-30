package com.viepak.halalfood.shared;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
public class User implements IsSerializable  {
	
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	private String password;
	private String name;
	private String role;
	private String email;
	private String phoneNumber;
	private Boolean IsActive;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean getIsActive() {
		return IsActive;
	}
	public void setIsActive(Boolean isActive) {
		IsActive = isActive;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
