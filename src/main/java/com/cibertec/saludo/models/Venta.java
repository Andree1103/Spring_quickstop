package com.cibertec.saludo.models;


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
@Table(name="tb_ventas")
public class Venta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pedido")
	private Date fecha;
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_entrega")
	private Date fechaEntrega;
	@Column(name="precio_envio")
	private double precioEnvio;
	private int estado;
	@Column(name = "suma_total")
	private double sumaTotal;
	@JsonIgnore
	@OneToMany(mappedBy="venta")
	private List<DetalleVenta> listaDetallePedido;
	@ManyToOne
	@JoinColumn(name = "id_usu")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "id_cli")
	private Cliente cliente;
	
	public double getSumaTotal() {
	    return sumaTotal;
	}

	public void setSumaTotal(double sumaTotal) {
	    this.sumaTotal = sumaTotal;
	}
	public List<DetalleVenta> getListaDetallePedido() {
		return listaDetallePedido;
	}
	public void setListaDetallePedido(List<DetalleVenta> listaDetallePedido) {
		this.listaDetallePedido = listaDetallePedido;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public double getPrecioEnvio() {
		return precioEnvio;
	}
	public void setPrecioEnvio(double precioEnvio) {
		this.precioEnvio = precioEnvio;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
