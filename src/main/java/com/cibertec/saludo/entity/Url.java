package com.cibertec.saludo.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_url")
public class Url {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	private String descripcion;
	private String ruta;
	@JsonIgnore
	@OneToMany(mappedBy="url")
	private List<RolUrl> listaRolEnlace;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public List<RolUrl> getListaRolEnlace() {
		return listaRolEnlace;
	}

	public void setListaRolEnlace(List<RolUrl> listaRolEnlace) {
		this.listaRolEnlace = listaRolEnlace;
	}
	
}
