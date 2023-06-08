package com.cibertec.saludo.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
