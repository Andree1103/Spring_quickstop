package com.cibertec.saludo.controllers;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.cibertec.saludo.entity.Distrito;
import com.cibertec.saludo.entity.Producto;
import com.cibertec.saludo.entity.Rol;
import com.cibertec.saludo.entity.TipoUsuario;
import com.cibertec.saludo.entity.Usuario;
import com.cibertec.saludo.services.DistritoService;
import com.cibertec.saludo.services.RolService;
import com.cibertec.saludo.services.TipoUsuarioService;
import com.cibertec.saludo.services.UsuarioService;
import com.cibertec.saludo.utils.Libreria;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/Usuarios")
public class MantenimientoUsuarioController {;
	
	@Autowired
	private DistritoService disServ;
	@Autowired
	private TipoUsuarioService servTipoUsuario;
	@Autowired
	private RolService servRol;
	@Autowired
	private UsuarioService servUsuario;
	@RequestMapping("/Crud_Usuarios")
	public String lista(Model model) {
		model.addAttribute("distritos", disServ.listarDistritos());
		model.addAttribute("tiposUsuarios", servTipoUsuario.listarTipoUsuario());
		model.addAttribute("roles", servRol.listarRoles());
		model.addAttribute("usuarios", servUsuario.listarUsuarios());
		return "Crud_Usuarios";
	}
	@RequestMapping("/buscar")
	@ResponseBody
	public Usuario buscar(@RequestParam("id") int id) {
		Usuario bean=servUsuario.buscar(id);
		return bean;
	}
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("id") int id,
						@RequestParam("nombre") String nombre,
						@RequestParam("apellido") String apellido,
						@RequestParam("dni") String dni,
						@RequestParam("usser") String usser,
						@RequestParam("password") String contrasenia,
						@RequestParam("phone") String celular,
						@RequestParam("fechaNacimiento") String fechaNac,
						@RequestParam("estado") int estado,
						@RequestParam("tipoUsuario") int idTip,
						@RequestParam("rol") int idRol,
						@RequestParam("distrito") int idDis,
						@RequestParam("direccion") String direccion,
						RedirectAttributes redirect) {
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			Usuario bean = new Usuario();
			bean.setId(id);
			bean.setNombre(nombre);
			bean.setApellido(apellido);
			bean.setDni(dni);
			bean.setUsser(usser);
			bean.setContrasenia(encoder.encode(contrasenia));
			bean.setCelular(celular);
			if(!(fechaNac =="")) bean.setFechaNac(new SimpleDateFormat("yyyy-MM-dd").parse(fechaNac));
			bean.setEstado(estado);
			TipoUsuario tip = new TipoUsuario();
			tip.setId(idTip);
			bean.setTipoUsuario(tip);
			Rol rol = new Rol();
			rol.setId(idRol);
			bean.setRol(rol);
			if(idDis != 0) {
				Distrito dis = new Distrito();				
				dis.setId(idDis);
				bean.setDistrito(dis);
			}else bean.setDistrito(null);			
			bean.setDireccion(direccion);
			servUsuario.grabar(bean);
			if(id==0)
				redirect.addFlashAttribute("MENSAJE","Usuario registrado");
			else
				redirect.addFlashAttribute("MENSAJE","Usuario actualizado");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en la grabación");
			e.printStackTrace();
		}
		return "redirect:/Usuarios/Crud_Usuarios";
	}

	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam("id") int id,RedirectAttributes redirect) {
		try {
			servUsuario.eliminar(id);
			redirect.addFlashAttribute("MENSAJE","Usuario eliminado");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en la eliminación");
			e.printStackTrace();
		}
		return "redirect:/Usuarios/Crud_Usuarios";
	}
	@RequestMapping("/reporte")
	public void reporte(HttpServletResponse response) {
		try {
			List<Usuario> data = servUsuario.listarUsuarios();
			File file=ResourceUtils.getFile("classpath:ReporteUsuario.jrxml");
			JRBeanCollectionDataSource info=new JRBeanCollectionDataSource(data);
			JasperPrint print=Libreria.generarReporte(file, info);
			response.setContentType("application/pdf");
			OutputStream salida=response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(print, salida);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
