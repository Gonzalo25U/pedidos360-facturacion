package com.pedidos360.facturacion.repository;

import com.pedidos360.facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByUsuarioId(String usuarioId);

    Optional<Factura> findByIdAndUsuarioId(Long id, String usuarioId);

    Optional<Factura> findByPedidoId(Long pedidoId);
}
