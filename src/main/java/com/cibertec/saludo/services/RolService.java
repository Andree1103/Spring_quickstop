package com.cibertec.saludo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.dao.RolRepository;
import com.cibertec.saludo.entity.Rol;

@Service
public class RolService {
	@Autowired
	private RolRepository repo;
	
	public List<Rol> listarRoles(){
		return repo.findAll();
	}
}
