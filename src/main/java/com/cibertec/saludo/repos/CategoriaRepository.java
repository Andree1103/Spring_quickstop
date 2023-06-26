package com.cibertec.saludo.repos;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
