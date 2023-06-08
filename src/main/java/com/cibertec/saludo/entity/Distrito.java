package com.cibertec.saludo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_distritos")
public class Distrito {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre_dis;
	
	@JsonIgnore
	@OneToMany(mappedBy="distrito")
	private List<Usuario> listaUsuarios;
	@JsonIgnore
	@OneToMany(mappedBy="distrito")
	private List<Cliente> listaClientes;
	


	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre_dis() {
		return nombre_dis;
	}

	public void setNombre_dis(String nombre_dis) {
		this.nombre_dis = nombre_dis;
	}

}
