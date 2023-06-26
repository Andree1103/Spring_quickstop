package com.cibertec.saludo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.models.TipoUsuario;
import com.cibertec.saludo.repos.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {
	@Autowired
	private TipoUsuarioRepository repo;
	public List<TipoUsuario> listarTipoUsuario(){
		return repo.findAll();
	}
}	
