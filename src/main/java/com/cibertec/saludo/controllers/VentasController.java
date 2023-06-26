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
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.ConverterProperties;


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
	private ResourceLoader resourceLoader;
	
	@Autowired
	private DataSource dataSource;
	
	
	List<DetalleVenta> detalles = new ArrayList<DetalleVenta>();
	
	Venta venta  = new Venta();
	
	@Autowired
    private TemplateEngine templateEngine;
	
	
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
	
	@GetMapping("/reporte/pdf")
	public void generarPDF(HttpServletResponse response) throws IOException {
        // Agregar el contenido HTML al documento
        String htmlContent = "<div class=\"container\">\n" +
                "    <h1 class=\"mt-4 mb-3\">\n" +
                "        QuickStore <small>Resumen de la orden</small>\n" +
                "    </h1>\n" +
                "    <div class=\"card mb-4\">\n" +
                "        <div class=\"card-body\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-lg-9\">\n" +
                "                    <div class=\"card-body\">\n" +
                "                        <h5 class=\"card-title\">DATOS ORDEN</h5>\n" +
                "                        <h6>\n" +
                "                            Nombre: <span th:text=\"${venta.usuario.nombre} + ' '+ ${venta.usuario.apellido}\"></span>\n" +
                "                        </h6>\n" +
                "                        <h6>\n" +
                "                            Telefono: <span th:text=\"${venta.usuario.celular}\"></span>\n" +
                "                        </h6>\n" +
                "                        <h6>\n" +
                "                            Direccion: <span th:text=\"${venta.usuario.direccion}\"></span>\n" +
                "                        </h6>\n" +
                "                        <h5 class=\"card-title\">PRODUCTOS</h5>\n" +
                "                        <div class=\"alert alert-light\" role=\"alert\">\n" +
                "                            <table class=\"mt-3 mx-auto table align-middle table-hover\">\n" +
                "                                <thead class=\"table-dark\">\n" +
                "                                    <tr>\n" +
                "                                        <th scope=\"col\">Producto</th>\n" +
                "                                        <th scope=\"col\">Precio</th>\n" +
                "                                        <th scope=\"col\">Cantidad</th>\n" +
                "                                        <th scope=\"col\">Total</th>\n" +
                "                                    </tr>\n" +
                "                                </thead>\n" +
                "                                <tbody>\n" +
                "                                    <tr th:each=\"dtpedido:${detalles}\">\n" +
                "                                        <td th:text=\"${dtpedido.producto.nombre}\"></td>\n" +
                "                                        <td th:text=\"${dtpedido.precio}\"></td>\n" +
                "                                        <td th:text=\"${dtpedido.cantidad}\"></td>\n" +
                "                                        <td th:text=\"${dtpedido.total}\"></td>\n" +
                "                                    </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"col-lg-3\">\n" +
                "                    <h2 class=\"card-title\">Resumen Orden</h2>\n" +
                "                    <ul class=\"list-group\">\n" +
                "                        <li class=\"list-group-item\"><h5>\n" +
                "                            Total: S/ <span th:text=\"${venta.sumaTotal}\"></span>\n" +
                "                        </h5></li>\n" +
                "                    </ul>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Configurar las propiedades del convertidor
        ConverterProperties converterProperties = new ConverterProperties();

        // Convertir el contenido HTML a PDF
        HtmlConverter.convertToPdf(htmlContent, new PdfWriter(outputStream), converterProperties);

        // Establecer las cabeceras de la respuesta HTTP
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte.pdf");
        response.setContentLength(outputStream.size());

        // Enviar el archivo PDF como respuesta
        outputStream.writeTo(response.getOutputStream());
        outputStream.close();
    }

    


	private List<ReporteVenta> obtenerDatosVenta(int codigoVenta) throws SQLException {
        List<ReporteVenta> datos = new ArrayList<>();

        // Establecer la conexión a la base de datos
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quickstop", "root", "mysql");

        // Crear la consulta SQL
        String sql = "SELECT v.id AS cod_venta, c.nombre_cli, p.nombre AS nomproductos, dv.cantidad, dv.precio_unidad, dv.total, v.suma_total " +
                "FROM tb_ventas v " +
                "INNER JOIN tb_clientes c ON v.id_cli = c.id " +
                "INNER JOIN tb_detalle_ventas dv ON v.id = dv.id_ventas " +
                "INNER JOIN tb_productos p ON dv.id_pro = p.id " +
                "WHERE v.id = ?";

        // Crear la sentencia preparada
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, codigoVenta);

        // Ejecutar la consulta SQL
        ResultSet resultSet = statement.executeQuery();

        // Recorrer los resultados y agregarlos a la lista de datos
        while (resultSet.next()) {
            ReporteVenta venta = new ReporteVenta();
            venta.setCod_venta(resultSet.getInt("cod_venta"));
            venta.setNombre_cli(resultSet.getString("nombre_cli"));
            venta.setNomproductos(resultSet.getString("nomproductos"));
            venta.setCantidad(resultSet.getInt("cantidad"));
            venta.setPrecio_unidad(resultSet.getDouble("precio_unidad"));
            venta.setTotal(resultSet.getDouble("total"));
            venta.setSuma_total(resultSet.getDouble("suma_total"));

            datos.add(venta);
        }

        // Cerrar la conexión y liberar los recursos
        resultSet.close();
        statement.close();
        connection.close();

        return datos;
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
