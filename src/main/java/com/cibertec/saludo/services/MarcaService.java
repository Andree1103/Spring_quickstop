package com.cibertec.saludo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.dao.MarcaRepository;
import com.cibertec.saludo.entity.Marca;


@Service
public class MarcaService {
	@Autowired
	private MarcaRepository repo;
	
	public List<Marca> listarMarcas(){
		return repo.findAll();
	}
	public void grabar(Marca bean) {
		repo.save(bean);
	}
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}
	public Marca buscar(Integer id) {
		return repo.findById(id).orElse(null);
	}
	
}
