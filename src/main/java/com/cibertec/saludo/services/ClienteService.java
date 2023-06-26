package com.cibertec.saludo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.models.Cliente;
import com.cibertec.saludo.repos.ClienteRepository;


@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;
	public List<Cliente> listarClientes(){
		return repo.findAll();
	}
	public void grabar(Cliente bean) {
		repo.save(bean);
	}
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}
	public Cliente buscar(Integer id) {
		return repo.findById(id).orElse(null);
	}
	public List<Cliente> listarClientesPorApellido(String ape){
		return repo.listAllAtCliente(ape);
	}
}
