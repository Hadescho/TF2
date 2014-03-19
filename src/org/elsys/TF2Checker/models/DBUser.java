package org.elsys.TF2Checker.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity(name="backpackValues")
@NamedQueries({
	@NamedQuery(name="byId", query="SELECT * FROM backpackValues WHERE id64=:id64")
})
public class DBUser {
	@Id
	String id64;
	
	@Column()
	float value;
	
	@Column()
	Date fetchDate;

	public String getId64() {
		return id64;
	}

	public void setId64(String id64) {
		this.id64 = id64;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Date getFetchDate() {
		return fetchDate;
	}

	public void setFetchDate(Date fetchDate) {
		this.fetchDate = fetchDate;
	}

	public DBUser(String id64, float value) {
		super();
		this.id64 = id64;
		this.value = value;
		this.fetchDate = new Date();
	}
	
	
	
	
}
