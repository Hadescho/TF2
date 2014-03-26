package org.elsys.TF2Checker.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity(name="DBUser")
@NamedQueries({
	@NamedQuery(name="byBackpackId", query="SELECT u FROM DBUser u WHERE id64=:id64")
})
public class DBUser {
	@Column()
	long id64;
	
	@Column()
	float value;
	
	@Id
	@Column()
	Date fetchDate;

	public long getId64() {
		return id64;
	}

	public void setId64(long id64) {
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

	public DBUser() {
	}
	public DBUser(long id64, float value) {
		this.id64 = id64;
		this.value = value;
		this.fetchDate = new Date();
	}

}
