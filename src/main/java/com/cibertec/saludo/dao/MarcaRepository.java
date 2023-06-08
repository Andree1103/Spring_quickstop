package com.cibertec.saludo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.entity.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {

}
