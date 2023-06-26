package com.cibertec.saludo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.saludo.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	@Query("select c from Cliente c where c.apellido_cli like ?1")
	public List<Cliente> listAllAtCliente(String ape);

}
