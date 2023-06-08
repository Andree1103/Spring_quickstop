package com.cibertec.saludo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.dao.TipoUsuarioRepository;
import com.cibertec.saludo.entity.TipoUsuario;

@Service
public class TipoUsuarioService {
	@Autowired
	private TipoUsuarioRepository repo;
	public List<TipoUsuario> listarTipoUsuario(){
		return repo.findAll();
	}
}	
