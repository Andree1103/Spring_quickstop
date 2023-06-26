package com.cibertec.saludo.models;


import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_roles")
public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String descripcion;
	@JsonIgnore
	@OneToMany(mappedBy="rol")
	private List<Usuario> listaUsuarios;
	
	@JsonIgnore
	@OneToMany(mappedBy="rol")
	private List<RolUrl> listaRolEnlace;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<RolUrl> getListaRolEnlace() {
		return listaRolEnlace;
	}

	public void setListaRolEnlace(List<RolUrl> listaRolEnlace) {
		this.listaRolEnlace = listaRolEnlace;
	}

	
}
