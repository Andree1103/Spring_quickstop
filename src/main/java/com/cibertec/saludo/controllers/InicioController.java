package com.cibertec.saludo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cibertec.saludo.entity.Categoria;
import com.cibertec.saludo.entity.Cliente;
import com.cibertec.saludo.entity.Url;
import com.cibertec.saludo.entity.Marca;
import com.cibertec.saludo.entity.Producto;
import com.cibertec.saludo.entity.Usuario;
import com.cibertec.saludo.entity.Venta;
import com.cibertec.saludo.services.CategoriaService;
import com.cibertec.saludo.services.ClienteService;
import com.cibertec.saludo.services.MarcaService;
import com.cibertec.saludo.services.ProductoService;
import com.cibertec.saludo.services.UsuarioService;
import com.cibertec.saludo.services.VentaService;

@SessionAttributes({"ENLACES","USUARIO"})
@Controller
public class InicioController {
	@Autowired
	private UsuarioService servUsuario;
	@Autowired
	private ProductoService servProducto;
	@Autowired
	private CategoriaService servCategoria;
	@Autowired
	private MarcaService servMarca;
	@Autowired
	private ClienteService servCliente;
	@Autowired
	private VentaService serVentas;
	/*@Autowired
	private PedidoService servPedido;*/
	@RequestMapping("/Dashboard")
	public String lista(Model model, Authentication auth) {
		String vLogin = auth.getName();
		Usuario u = servUsuario.loginUsuario(vLogin);
		List<Url> lista = servUsuario.enlacesDelUsuario(u.getRol().getId());
		model.addAttribute("USUARIO",u);
		model.addAttribute("ENLACES",lista);
		List<Cliente> clientes = servCliente.listarClientes();
		int cantCli = clientes.size();
		List<Usuario> usuarios = servUsuario.listarUsuarios();
		int cantUsu = usuarios.size();
		List<Producto> productos = servProducto.listarProductos();
		int cantPro = productos.size();
		List<Categoria> categorias = servCategoria.listarCategorias();
		int cantCat = categorias.size();
		List<Marca> marcas = servMarca.listarMarcas();
		int cantMar = categorias.size();
		List<Venta> ventas = serVentas.listarPedidos();
		int cantVent= ventas.size();

		model.addAttribute("cantCli", cantCli);
		model.addAttribute("cantUsu", cantUsu);
		model.addAttribute("cantPro", cantPro);
		model.addAttribute("cantCat", cantCat);
		model.addAttribute("cantMar", cantMar);
		model.addAttribute("cantVent",cantVent);
		return "Inicio";
	}
	@RequestMapping("/Inicio")
	public String MenuMantenimiento(Model model, Authentication auth) {
		String vLogin = auth.getName();
		Usuario u = servUsuario.loginUsuario(vLogin);
		List<Url> lista = servUsuario.enlacesDelUsuario(u.getRol().getId());
		model.addAttribute("USUARIO",u);
		model.addAttribute("ENLACES",lista);
		List<Cliente> clientes = servCliente.listarClientes();
		int cantCli = clientes.size();
		List<Usuario> usuarios = servUsuario.listarUsuarios();
		int cantUsu = usuarios.size();
		List<Producto> productos = servProducto.listarProductos();
		int cantPro = productos.size();
		List<Categoria> categorias = servCategoria.listarCategorias();
		int cantCat = categorias.size();
		List<Marca> marcas = servMarca.listarMarcas();
		int cantMar = categorias.size();
		List<Venta> ventas = serVentas.listarPedidos();
		int cantVent= ventas.size();

		model.addAttribute("cantCli", cantCli);
		model.addAttribute("cantUsu", cantUsu);
		model.addAttribute("cantPro", cantPro);
		model.addAttribute("cantCat", cantCat);
		model.addAttribute("cantMar", cantMar);
		model.addAttribute("cantVent",cantVent);
		return "menu_principal";
	}
}
