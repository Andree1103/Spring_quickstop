package com.cibertec.saludo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.saludo.dao.DetallePedidoRepository;
import com.cibertec.saludo.dao.VentaRepository;
import com.cibertec.saludo.entity.DetalleVenta;
import com.cibertec.saludo.entity.Venta;

@Service
public class VentaService {
	@Autowired
	private VentaRepository repo;
	@Autowired
	private DetallePedidoRepository repoDeta;
	
	public List<Venta> listarPedidos(){
		return repo.findAll();
	}
	public void grabar(Venta bean) {
		repo.save(bean);
	}
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}
	public Venta buscar(Integer id) {
		return repo.findById(id).orElse(null);
	}
	
	public void registrarPedido(Venta bean) {
		try {
			repo.save(bean);
			for(DetalleVenta dp:bean.getListaDetallePedido()) {
				repoDeta.save(dp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
