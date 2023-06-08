package com.cibertec.saludo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer>{

}
