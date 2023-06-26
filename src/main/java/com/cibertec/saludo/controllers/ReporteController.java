package com.cibertec.saludo.controllers;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.saludo.models.Categoria;
import com.cibertec.saludo.models.Cliente;
import com.cibertec.saludo.models.Marca;
import com.cibertec.saludo.models.Producto;
import com.cibertec.saludo.models.Usuario;
import com.cibertec.saludo.models.Venta;
import com.cibertec.saludo.services.CategoriaService;
import com.cibertec.saludo.services.ClienteService;
import com.cibertec.saludo.services.MarcaService;
import com.cibertec.saludo.services.VentaService;
import com.cibertec.saludo.services.ProductoService;
import com.cibertec.saludo.services.UsuarioService;
import com.cibertec.saludo.utils.Libreria;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/reporte")
public class ReporteController {
	@Autowired
	private ClienteService servCliente;
	@Autowired
	private ProductoService servProd;
	@Autowired
	private UsuarioService servUsuario;
	@Autowired
	private CategoriaService servCategoria;
	@Autowired
	private MarcaService servMarca;
	@Autowired
	private VentaService servPedido;
	
	@RequestMapping("/cliente")
	public void inicio (HttpServletResponse response) {
		try {
			List<Cliente> data = servCliente.listarClientes();
			//
			File file= ResourceUtils.getFile("classpath:reporte_clientes.jrxml");
			//
			JRBeanCollectionDataSource info= new JRBeanCollectionDataSource(data);
			//
			JasperPrint print= Libreria.generarReporte(file, info);
			//
			response.setContentType("application/pdf");	
			//
			OutputStream salida= response.getOutputStream();
			//
			JasperExportManager.exportReportToPdfStream(print, salida);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/producto")
	public void inicio2 (HttpServletResponse response) {
		try {
			List<Producto> data = servProd.listarProductos();
			//
			File file= ResourceUtils.getFile("classpath:reporte_productos.jrxml");
			//
			JRBeanCollectionDataSource info= new JRBeanCollectionDataSource(data);
			//
			JasperPrint print= Libreria.generarReporte(file, info);
			//
			response.setContentType("application/pdf");	
			//
			OutputStream salida= response.getOutputStream();
			//
			JasperExportManager.exportReportToPdfStream(print, salida);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/usuarios")
	public void inicio3 (HttpServletResponse response) {
		try {
			List<Usuario> data = servUsuario.listarUsuarios() ;
			//
			File file= ResourceUtils.getFile("classpath:reporte_usuarios.jrxml");
			//
			JRBeanCollectionDataSource info= new JRBeanCollectionDataSource(data);
			//
			JasperPrint print= Libreria.generarReporte(file, info);
			//
			response.setContentType("application/pdf");	
			//
			OutputStream salida= response.getOutputStream();
			//
			JasperExportManager.exportReportToPdfStream(print, salida);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/categorias")
	public void inicio4 (HttpServletResponse response) {
		try {
			List<Categoria> data = servCategoria.listarCategorias() ;
			//
			File file= ResourceUtils.getFile("classpath:reporte_categorias.jrxml");
			//
			JRBeanCollectionDataSource info= new JRBeanCollectionDataSource(data);
			//
			JasperPrint print= Libreria.generarReporte(file, info);
			//
			response.setContentType("application/pdf");	
			//
			OutputStream salida= response.getOutputStream();
			//
			JasperExportManager.exportReportToPdfStream(print, salida);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/marcas")
	public void inicio5 (HttpServletResponse response) {
		try {
			List<Marca> data = servMarca.listarMarcas() ;
			//
			File file= ResourceUtils.getFile("classpath:reporte_marcas.jrxml");
			//
			JRBeanCollectionDataSource info= new JRBeanCollectionDataSource(data);
			//
			JasperPrint print= Libreria.generarReporte(file, info);
			//
			response.setContentType("application/pdf");	
			//
			OutputStream salida= response.getOutputStream();
			//
			JasperExportManager.exportReportToPdfStream(print, salida);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/pedidos")
	public void inicio8 (HttpServletResponse response) {
		try {
			List<Venta> data = servPedido.listarPedidos() ;
			//
			File file= ResourceUtils.getFile("classpath:reporte_pedidos.jrxml");
			//
			JRBeanCollectionDataSource info= new JRBeanCollectionDataSource(data);
			//
			JasperPrint print= Libreria.generarReporte(file, info);
			//
			response.setContentType("application/pdf");	
			//
			OutputStream salida= response.getOutputStream();
			//
			JasperExportManager.exportReportToPdfStream(print, salida);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
