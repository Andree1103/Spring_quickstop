package com.cibertec.saludo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre_cli;
	private String apellido_cli;
	private String  celular_cli;
	private String direccion_cli;
	private int estado;
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Venta> listaPedido;
	
	public List<Venta> getListaPedido() {
		return listaPedido;
	}
	public void setListaPedido(List<Venta> listaPedido) {
		this.listaPedido = listaPedido;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nac_cli")
	private Date fechaNac;	
	@ManyToOne
	//(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_dis")
	private Distrito distrito;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public Date getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	public Distrito getDistrito() {
		return distrito;
	}
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	public String getNombre_cli() {
		return nombre_cli;
	}
	public void setNombre_cli(String nombre_cli) {
		this.nombre_cli = nombre_cli;
	}
	public String getApellido_cli() {
		return apellido_cli;
	}
	public void setApellido_cli(String apellido_cli) {
		this.apellido_cli = apellido_cli;
	}
	public String getCelular_cli() {
		return celular_cli;
	}
	public void setCelular_cli(String celular_cli) {
		this.celular_cli = celular_cli;
	}
	public String getDireccion_cli() {
		return direccion_cli;
	}
	public void setDireccion_cli(String direccion_cli) {
		this.direccion_cli = direccion_cli;
	}
	
}
