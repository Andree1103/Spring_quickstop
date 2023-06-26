package com.cibertec.saludo.models;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class RolUrlPK implements Serializable{
	private int id;
	private int idenlace_url;
	@Override
	public int hashCode() {
		return Objects.hash(id, idenlace_url);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolUrlPK other = (RolUrlPK) obj;
		return idenlace_url == other.idenlace_url && id == other.id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdenlace_url() {
		return idenlace_url;
	}
	public void setIdenlace_url(int idenlace_url) {
		this.idenlace_url = idenlace_url;
	}
}
