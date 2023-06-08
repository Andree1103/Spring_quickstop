package com.cibertec.saludo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.dao.CategoriaRepository;
import com.cibertec.saludo.entity.Categoria;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> listarCategorias(){
		return repo.findAll();
	}
	public void grabar(Categoria bean) {
		repo.save(bean);
	}
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}
	public Categoria buscar(Integer id) {
		return repo.findById(id).orElse(null);
	}
	
}
