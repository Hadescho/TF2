package org.elsys.TF2Checker.models;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import services.UserService;

@Entity(name="User1")
public class User {
	@Id
	private long id;
	@Column(unique=true)
	public String email;
	@Column
	private String password;
	@Column
	public long id64;
	@OneToMany
	private List<DBUser > backpackValues;
	
	
	public String getEmail() {
		return email;
	}
	
//	public void setEmail(String email) {
//		this.email = email;
//	}
	
	public String getPassword() {
		return password;
	}
	
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	public long getId64() {
		return id64;
	}
	
//	public void setId64(long id64) {
//		this.id64 = id64;
//	}
	
	public List<DBUser> getBackpackValues() {
		return backpackValues;
	}
	
	public User(String email, String password , long id64){ //Contructor for registration
		this.id64 = id64;
		this.password = password;
		this.email = email;
		this.id = Math.abs(new Random((new Date()).getTime()).nextLong());
	}
	
	public User(){  //Default constructor
		this.id64 = 1;
		this.password = "default constructor";
		this.email = "default constructor";
		this.id = Math.abs(new Random((new Date()).getTime()).nextLong());
	}
	
	public User(String email,String password){
		User u = UserService.getInstance().getUsers(email, password);
		System.out.println("----------------- Is the user null?" + u.equals(null) + "---------------");
	}
	
}
