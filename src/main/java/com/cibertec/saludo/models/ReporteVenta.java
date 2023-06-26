package com.cibertec.saludo.models;

public class ReporteVenta {
	private int cod_venta ;
	private String nombre_cli;
	private String nomproductos;
	private int cantidad;
	private double precio_unidad;
	private double total;
	private double suma_total;
	public int getCod_venta() {
		return cod_venta;
	}
	public void setCod_venta(int cod_venta) {
		this.cod_venta = cod_venta;
	}
	public String getNombre_cli() {
		return nombre_cli;
	}
	public void setNombre_cli(String nombre_cli) {
		this.nombre_cli = nombre_cli;
	}
	public String getNomproductos() {
		return nomproductos;
	}
	public void setNomproductos(String nomproductos) {
		this.nomproductos = nomproductos;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecio_unidad() {
		return precio_unidad;
	}
	public void setPrecio_unidad(double precio_unidad) {
		this.precio_unidad = precio_unidad;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getSuma_total() {
		return suma_total;
	}
	public void setSuma_total(double suma_total) {
		this.suma_total = suma_total;
	}
	
	
}
