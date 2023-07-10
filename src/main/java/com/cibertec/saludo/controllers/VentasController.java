package com.cibertec.saludo.controllers;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;

import java.io.InputStream;


import com.lowagie.text.pdf.BaseFont;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.saludo.models.Cliente;
import com.cibertec.saludo.models.DetalleVenta;
import com.cibertec.saludo.models.ListaProductos;
import com.cibertec.saludo.models.Producto;
import com.cibertec.saludo.models.ReporteVenta;
import com.cibertec.saludo.models.Usuario;
import com.cibertec.saludo.models.Venta;
import com.cibertec.saludo.services.ClienteService;
import com.cibertec.saludo.services.PedidoDetalleService;
import com.cibertec.saludo.services.VentaService;
import com.cibertec.saludo.services.ProductoService;
import com.cibertec.saludo.services.UsuarioService;
import com.cibertec.saludo.utils.Libreria;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.xhtmlrenderer.pdf.ITextRenderer;


@Controller
@RequestMapping("/Pedidos")
public class VentasController {
	@Autowired
	private UsuarioService servUsuario;
	@Autowired
	private ProductoService servProducto;
	@Autowired
	private PedidoDetalleService servDetalle;
	@Autowired
	private ClienteService servCliente;
	@Autowired
	private VentaService servVenta;
	@Autowired
    private TemplateEngine templateEngine;
	
	
	List<DetalleVenta> detalles = new ArrayList<DetalleVenta>();
	
	Venta venta  = new Venta();
	
	
	@RequestMapping("/Venta_Pedido")
	public String lista(Model model) {
		model.addAttribute("ventas",servVenta.listarPedidos());
		return "mantenimiento-pedidos";
	}
	@RequestMapping("/Registrar")
	public String registrarPedido(Model model,Authentication auth) {
		String vLogin = auth.getName();
		Usuario u = servUsuario.loginUsuario(vLogin);
		model.addAttribute("USUARIO",u);
		model.addAttribute("productos",servProducto.listarProductos());
		return "registro-pedido";
	}
	@RequestMapping("/Editar")
	public String editarPedido(Model model,Authentication auth) {
		String vLogin = auth.getName();
		Usuario u = servUsuario.loginUsuario(vLogin);
		model.addAttribute("USUARIO",u);
		model.addAttribute("productos",servProducto.listarProductos());
		return "editar-pedido";
	}
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("id") int id,
	                     @RequestParam("fechaActual") String fechaActual,
	                     @RequestParam("fechaEntrega") String fechaEntrega,
	                     @RequestParam("precioEnvio") double precioEnvio,
	                     @RequestParam("idCliente") int idCli,
	                     @RequestParam("estado") int estado,
	                     HttpSession session,
	                     Authentication auth) {

	    // Obtener información del usuario autenticado
	    String vLogin = auth.getName();
	    Usuario u = servUsuario.loginUsuario(vLogin);

	    // Obtener el ID máximo de los pedidos existentes
	    int idPed = 0;
	    List<Venta> pedidosLista = servVenta.listarPedidos();
	    for (Venta p : pedidosLista) {
	        if (p.getId() > idPed) {
	            idPed = p.getId();
	        }
	    }

	    try {
	        // Crear objeto Venta y establecer sus propiedades
	        Venta bean = new Venta();
	        bean.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(fechaActual));
	        bean.setFechaEntrega(new SimpleDateFormat("yyyy-MM-dd").parse(fechaEntrega));
	        bean.setPrecioEnvio(precioEnvio);

	        // Crear objeto Cliente y establecer su ID
	        Cliente cli = new Cliente();
	        cli.setId(idCli);
	        bean.setCliente(cli);

	        bean.setEstado(estado);
	        bean.setUsuario(u);

	        // Obtener detalles de pedido del carrito de la sesión
	        List<ListaProductos> data = (List<ListaProductos>) session.getAttribute("carrito");

	        // Crear lista de detalles de venta
	        List<DetalleVenta> lista = new ArrayList<DetalleVenta>();
	        for (ListaProductos lp : data) {
	            DetalleVenta d = new DetalleVenta();
	            Producto pro = new Producto();
	            pro.setId(lp.getId());
	            d.setCantidad(lp.getCantidad());
	            d.setProducto(pro);
	            d.setPrecio(lp.getPrecio());
	            double total = lp.getCantidad() * lp.getPrecio();
	            d.setTotal(total);
	            d.setVenta(bean);
	            lista.add(d);
	        }
	        
	        double sumaTotal = 0.0;
	        for (DetalleVenta detalle : lista) {
	            sumaTotal += detalle.getTotal();
	        }
	        bean.setSumaTotal(sumaTotal);
	        // Establecer la lista de detalles de venta en el objeto Venta
	        bean.setListaDetallePedido(lista);

	        // Registrar el pedido en el servicio
	        servVenta.registrarPedido(bean);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return "redirect:/Pedidos/Venta_Pedido";
	}

	
	@RequestMapping("/adicionar")
	@ResponseBody
	public List<ListaProductos> adicionar(@RequestParam("id") int id, 
							@RequestParam("nombre")String nom,
							@RequestParam("precio")double pre,
							@RequestParam("cantidad")int cantidad,
							HttpSession session) {
		List<ListaProductos> data =null;
		if(session.getAttribute("carrito")==null)
			data = new ArrayList<ListaProductos>();
		else
			data = (List<ListaProductos>) session.getAttribute("carrito");
		ListaProductos bean = new ListaProductos();
		bean.setId(id);
		bean.setNombre(nom);
		bean.setPrecio(pre);
		bean.setCantidad(cantidad);		
		data.add(bean);
		session.setAttribute("carrito",data);		
		return data;
	}
	@RequestMapping("/eliminar")
	@ResponseBody
	public List<ListaProductos> eliminar(@RequestParam("id") int id, HttpSession session){
		List<ListaProductos> data = (List<ListaProductos>) session.getAttribute("carrito");
		for (ListaProductos d:data) {
			if(d.getId()==id) {
				data.remove(d);
				break;
			}
		}
		session.setAttribute("carrito", data);
		return data;
	}
	@RequestMapping("/listarClientes")
	@ResponseBody
	public List<Cliente> listarClientes(@RequestParam("apellido") String ape){
		List<Cliente> data = servCliente.listarClientesPorApellido(ape + "%");
		return data;
	}
	@RequestMapping("/reporte")
	public void reporte(HttpServletResponse response) {
		try {
			List<Venta> data = servVenta.listarPedidos();
			File file=ResourceUtils.getFile("classpath:reporte_pedidos.jrxml");
			JRBeanCollectionDataSource info=new JRBeanCollectionDataSource(data);
			JasperPrint print=Libreria.generarReporte(file, info);
			response.setContentType("application/pdf");
			OutputStream salida=response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(print, salida);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@GetMapping("/generar-pdf/{idVenta}")
    public ResponseEntity<ByteArrayResource> generarPDF(@PathVariable("idVenta") Integer idVenta) throws Exception {
        Context context = new Context();
        context.setVariable("venta",servVenta.buscar(idVenta));
        context.setVariable("detalles",servDetalle.buscar(idVenta));

        String html = templateEngine.process("reportePDF", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream, true);

        byte[] content = outputStream.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(content);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(content.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
	
	@GetMapping("/generar-reporte/{idVenta}")
    public ModelAndView generarReportePDF(@PathVariable("idVenta") Integer idVenta) {
        ModelAndView modelAndView = new ModelAndView("reportePDF");
        Venta venta = servVenta.buscar(idVenta);
        List<DetalleVenta> detalles = null;
        detalles = servDetalle.buscar(idVenta);
        modelAndView.addObject("venta", venta);
        modelAndView.addObject("detalles", detalles);
        return modelAndView;
    }
	
	@RequestMapping("/pedido/{id_ventas}")
	public String order (@PathVariable("id_ventas") int id_ventas,Model model,RedirectAttributes attributes) {
		List<DetalleVenta> detalles = null;
		if (id_ventas > 0) {
			detalles = servDetalle.buscar(id_ventas);
			if (detalles == null) {
				attributes.addFlashAttribute("error", "Atencion: el Id del producto no existe!");
				return "redirect:/Pedidos/Venta_Pedido";
			}
		} else {
			attributes.addFlashAttribute("error", "Atencion: error Id del producto");
			return "redirect:/Pedidos/Venta_Pedido";
		}
		Venta venta = null;
		if (id_ventas > 0) {
			venta = servVenta.buscar(id_ventas);
			if (venta == null) {
				attributes.addFlashAttribute("error", "Atencion: el Id del producto no existe!");
				return "redirect:/Pedidos/Venta_Pedido";
			}
		} else {
			attributes.addFlashAttribute("error", "Atencion: error Id del producto");
			return "redirect:/Pedidos/Venta_Pedido";
		}
		model.addAttribute("venta",venta);
		model.addAttribute("detalles",detalles);
		return "showOrder";
	}
}
