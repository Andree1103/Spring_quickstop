package com.cibertec.saludo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.models.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer>{

}
