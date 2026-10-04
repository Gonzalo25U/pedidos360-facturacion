package com.pedidos360.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    private Long id;
    private String numeroFactura;
    private Long pedidoId;
    private String usuarioId;
    private BigDecimal total;
    private String estado;
    private LocalDateTime fechaEmision;
}
