package com.cibertec.saludo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.saludo.models.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer>{


	@Query("SELECT v FROM Venta v " +
	           "JOIN FETCH v.cliente c " +
	           "JOIN FETCH v.listaDetallePedido d " +
	           "JOIN FETCH d.producto p " +
	           "WHERE v.id = :codVenta")
	    List<Venta> listarPedidosPorCodigoVenta(@Param("codVenta") int codVenta);
}
