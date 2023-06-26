package com.cibertec.saludo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.saludo.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {

}
