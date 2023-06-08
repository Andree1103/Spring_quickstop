package com.cibertec.saludo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {

}
