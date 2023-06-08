package com.cibertec.saludo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.entity.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer>{

}
