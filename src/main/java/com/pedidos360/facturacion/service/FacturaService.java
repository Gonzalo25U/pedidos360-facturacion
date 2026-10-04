package com.pedidos360.facturacion.service;

import com.pedidos360.facturacion.dto.FacturaDTO;
import com.pedidos360.facturacion.exception.RecursoNoEncontradoException;
import com.pedidos360.facturacion.model.Factura;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository repository;

    @Transactional
    public FacturaDTO generar(Long pedidoId, String usuarioId, BigDecimal total) {
        // Si por algun motivo el evento llegara duplicado (RabbitMQ puede
        // reentregar mensajes tras una caida), no se genera una factura repetida
        // para el mismo pedido.
        var existente = repository.findByPedidoId(pedidoId);
        if (existente.isPresent()) {
            return toDTO(existente.get());
        }

        String numeroFactura = "FAC-" + pedidoId + "-" + System.currentTimeMillis();
        Factura factura = new Factura(numeroFactura, pedidoId, usuarioId, total);
        return toDTO(repository.save(factura));
    }

    public List<FacturaDTO> listarPropias(String usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream().map(this::toDTO).toList();
    }

    public FacturaDTO obtenerPropia(String usuarioId, Long id) {
        Factura factura = repository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Factura no encontrada: " + id));
        return toDTO(factura);
    }

    public List<FacturaDTO> listarTodas() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    private FacturaDTO toDTO(Factura f) {
        return new FacturaDTO(f.getId(), f.getNumeroFactura(), f.getPedidoId(), f.getUsuarioId(),
                f.getTotal(), f.getEstado(), f.getFechaEmision());
    }
}