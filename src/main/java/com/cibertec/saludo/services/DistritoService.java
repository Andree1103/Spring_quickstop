package com.cibertec.saludo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.models.Distrito;
import com.cibertec.saludo.repos.DistritoRepository;

@Service
public class DistritoService {
	@Autowired
	private DistritoRepository repo;
	public List<Distrito> listarDistritos(){
		return repo.findAll();
	}
}
