package org.elsys.TF2Checker.models;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="User")
public class User {
	@Id
	private long id;
	@Column(unique=true)
	public String email;
	@Column
	private String password;
	@Column
	public long id64;
	
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
	public long getId64() {
		return id64;
	}
	public void setId64(long id64) {
		this.id64 = id64;
	}
	
	
	public User(String email, String password , long id64){
		this.id64 = id64;
		this.password = password;
		this.email = email;
		this.id = Math.abs(new Random((new Date()).getTime()).nextLong());
	}
}
