package com.cibertec.saludo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
