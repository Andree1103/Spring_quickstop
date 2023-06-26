package com.cibertec.saludo.repos;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.saludo.models.DetalleVenta;

public interface DetallePedidoRepository extends JpaRepository<DetalleVenta, Integer>{
	@Query("SELECT d FROM DetalleVenta d WHERE d.venta.id = ?1")
	public List<DetalleVenta> listAllforIdVentas(int id_ventas);

}
