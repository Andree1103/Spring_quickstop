package com.cibertec.saludo.models;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_rol_url")
public class RolUrl {
	@EmbeddedId
	private RolUrlPK pk;
	@ManyToOne
	@JoinColumn(name="id", insertable=false,updatable = false)
	private Rol rol;	
	@ManyToOne
	@JoinColumn(name="idenlace_url", insertable=false,updatable = false)
	private Url url;
	public RolUrlPK getPk() {
		return pk;
	}
	public void setPk(RolUrlPK pk) {
		this.pk = pk;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public Url getUrl() {
		return url;
	}
	public void setUrl(Url url) {
		this.url = url;
	}
	
}
