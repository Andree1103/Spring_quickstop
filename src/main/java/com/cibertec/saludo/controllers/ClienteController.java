package com.cibertec.saludo.controllers;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.saludo.models.Cliente;
import com.cibertec.saludo.models.Distrito;
import com.cibertec.saludo.services.ClienteService;
import com.cibertec.saludo.services.DistritoService;
import com.cibertec.saludo.utils.Libreria;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



@Controller
@RequestMapping("/Clientes")
public class ClienteController {
	@Autowired
	private DistritoService disServ;
	@Autowired
	private ClienteService servCliente;
	@RequestMapping("/Crud_Clientes")
	public String lista(Model model) {
		model.addAttribute("distritos", disServ.listarDistritos());
		model.addAttribute("clientes",servCliente.listarClientes());
		return "mantenimiento-clientes";
	}
	@RequestMapping("/buscar")
	@ResponseBody
	public Cliente buscar(@RequestParam("id") int id) {
		Cliente bean=servCliente.buscar(id);
		return bean;
	}
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("id") int id,
						@RequestParam("nombre") String nombre,
						@RequestParam("apellido") String apellido,
						@RequestParam("phone") String celular,
						@RequestParam("fechaNacimiento") String fechaNac,
						@RequestParam("estado") int estado,
						@RequestParam("distrito") int idDis,
						@RequestParam("direccion") String direccion,
						RedirectAttributes redirect) {
		try {
			Cliente bean = new Cliente();
			bean.setId(id);
			bean.setNombre_cli(nombre);
			bean.setApellido_cli(apellido);
			bean.setCelular_cli(celular);
			if(!(fechaNac =="")) bean.setFechaNac(new SimpleDateFormat("yyyy-MM-dd").parse(fechaNac));
			bean.setEstado(estado);
			if(idDis != 0) {
				Distrito dis = new Distrito();				
				dis.setId(idDis);
				bean.setDistrito(dis);
			}else bean.setDistrito(null);			
			bean.setDireccion_cli(direccion);
			servCliente.grabar(bean);
			if(id==0)
				redirect.addFlashAttribute("MENSAJE","Cliente registrado");
			else
				redirect.addFlashAttribute("MENSAJE","Cliente actualizado");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en la grabación");
			e.printStackTrace();
		}
		return "redirect:/Clientes/Crud_Clientes";
	}

	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam("id") int id,RedirectAttributes redirect) {
		try {
			servCliente.eliminar(id);
			redirect.addFlashAttribute("MENSAJE","Cliente eliminado");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en la eliminación");
			e.printStackTrace();
		}
		return "redirect:/Clientes/Crud_Clientes";
	}
	@RequestMapping("/reporte")
	public void reporteMarca(HttpServletResponse response) {
		try {
			List<Cliente> data = servCliente.listarClientes();
			File file=ResourceUtils.getFile("classpath:reporte_clientes.jrxml");
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
