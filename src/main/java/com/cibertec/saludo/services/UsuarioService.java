package com.cibertec.saludo.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.dao.UsuarioRepository;
import com.cibertec.saludo.entity.Url;
import com.cibertec.saludo.entity.Usuario;

@Service
public class UsuarioService implements UserDetailsService{	
	@Autowired
	private UsuarioRepository repo;	
	public List<Usuario> listarUsuarios(){
		return repo.findAll();
	}
	public void grabar(Usuario bean) {
		repo.save(bean);
	}
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}
	public Usuario buscar(Integer id) {
		return repo.findById(id).orElse(null);
	}
	public Usuario loginUsuario(String vLogin) {
		return repo.iniciarSesion(vLogin);
	}
	public List<Url> enlacesDelUsuario(int rol){
		return repo.traerEnlacesDelUsuario(rol);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails obj=null;
		Usuario bean = repo.iniciarSesion(username);
		Set<GrantedAuthority> rol = new HashSet<GrantedAuthority>();
		rol.add(new SimpleGrantedAuthority(bean.getRol().getNombre()));
		obj = new User(username, bean.getContrasenia(),rol);
		return obj;
	}	
	
}
