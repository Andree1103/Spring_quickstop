package com.cibertec.saludo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.entity.DetalleVenta;

public interface DetallePedidoRepository extends JpaRepository<DetalleVenta, Integer>{

}
