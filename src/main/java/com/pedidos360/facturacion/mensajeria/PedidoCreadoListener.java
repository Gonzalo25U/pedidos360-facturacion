package com.pedidos360.facturacion.mensajeria;

import com.pedidos360.facturacion.service.FacturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoCreadoListener {

    private final FacturaService facturaService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_GENERAR_FACTURA)
    public void generarFactura(PedidoCreadoEvent evento) {
        try {
            var factura = facturaService.generar(evento.getPedidoId(), evento.getUsuarioId(), evento.getTotal());
            log.info("Factura {} generada para el pedido {}", factura.getNumeroFactura(), evento.getPedidoId());
        } catch (Exception e) {
            log.error("No se pudo generar la factura del pedido {}: {}", evento.getPedidoId(), e.getMessage());
        }
    }
}
