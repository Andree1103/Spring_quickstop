package com.cibertec.saludo.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.models.DetalleVenta;
import com.cibertec.saludo.repos.DetallePedidoRepository;

@Service
public class PedidoDetalleService {
	
	@Autowired
	private DetallePedidoRepository repo;
	
	public List<DetalleVenta> buscar(int id_ventas) {
	    return repo.listAllforIdVentas(id_ventas);
	}

}
