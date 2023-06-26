package com.cibertec.saludo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
