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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.saludo.entity.Categoria;
import com.cibertec.saludo.entity.Marca;
import com.cibertec.saludo.entity.Producto;
import com.cibertec.saludo.services.CategoriaService;
import com.cibertec.saludo.services.MarcaService;
import com.cibertec.saludo.services.ProductoService;
import com.cibertec.saludo.utils.Libreria;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/Productos")
public class MantenimientoProductoController {
	@Autowired
	private CategoriaService servCategoria;
	@Autowired
	private MarcaService servMarca;
	@Autowired
	private ProductoService servProducto;
	@RequestMapping("/Crud_Productos")
	public String lista(Model model) {
		model.addAttribute("categorias", servCategoria.listarCategorias());
		model.addAttribute("marcas", servMarca.listarMarcas());
		model.addAttribute("productos",servProducto.listarProductos());
		return "Crud_Productos";
	}
	@RequestMapping("/buscar")
	@ResponseBody
	public Producto buscar(@RequestParam("id") int id) {
		Producto bean=servProducto.buscar(id);
		return bean;
	}
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("id") int id,
						@RequestParam("nombre") String nombre,
						@RequestParam("descripcion") String descripcion,
						@RequestParam("precio") double precio,
						@RequestParam("stock") int stock,
						@RequestParam("estado") int estado,
						@RequestParam("categoria") int idCat,
						@RequestParam("marca") int idMar,
						RedirectAttributes redirect) {
		try {
			Producto bean = new Producto();
			bean.setId(id);
			bean.setNombre(nombre);
			bean.setDescripcion(descripcion);
			bean.setPrecio(precio);
			bean.setEstado(estado);
			bean.setStock(stock);
			Categoria cat = new Categoria();
			cat.setId(idCat);
			bean.setCategoria(cat);
			Marca mar = new Marca();
			mar.setId(idMar);
			bean.setMarca(mar);
			servProducto.grabar(bean);
			if(id==0)
				redirect.addFlashAttribute("MENSAJE","Producto registrada");
			else
				redirect.addFlashAttribute("MENSAJE","Producto actualizada");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en la grabación");
			e.printStackTrace();
		}
		return "redirect:/Productos/Crud_Productos";
	}
	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam("id") int id, RedirectAttributes redirect) {
		try {
			servProducto.eliminar(id);
			redirect.addFlashAttribute("MENSAJE","Producto eliminado");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en la eliminación");
			e.printStackTrace();
		}
		return "redirect:/Productos/Crud_Productos";
	}
	@RequestMapping("/reporte")
	public void reporte(HttpServletResponse response) {
		try {
			List<Producto> data = servProducto.listarProductos();
			File file=ResourceUtils.getFile("classpath:reporte_productos.jrxml");
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
