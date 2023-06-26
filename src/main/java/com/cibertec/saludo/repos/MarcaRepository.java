package com.cibertec.saludo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.models.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {

}
