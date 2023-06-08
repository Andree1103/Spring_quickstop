package com.cibertec.saludo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.saludo.entity.Url;
import com.cibertec.saludo.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	@Query("select u from Usuario u where u.nombre =?1")
	public Usuario iniciarSesion(String vLogin);
	
	@Query("select e from RolUrl ru join ru.url e where ru.rol.id=?1")
	public List<Url> traerEnlacesDelUsuario(int idRol);
}