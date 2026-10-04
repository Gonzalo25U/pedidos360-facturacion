package com.pedidos360.facturacion.controller;

import com.pedidos360.facturacion.dto.FacturaDTO;
import com.pedidos360.facturacion.exception.AccesoNoPermitidoException;
import com.pedidos360.facturacion.security.UsuarioActualProvider;
import com.pedidos360.facturacion.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService service;
    private final UsuarioActualProvider usuarioActualProvider;

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> misFacturas() {
        return ResponseEntity.ok(service.listarPropias(usuarioActualProvider.obtenerUsuarioId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPropia(usuarioActualProvider.obtenerUsuarioId(), id));
    }

    @GetMapping("/admin/todas")
    public ResponseEntity<List<FacturaDTO>> todas() {
        if (!usuarioActualProvider.esAdmin()) {
            throw new AccesoNoPermitidoException("Solo un Admin puede ver todas las facturas");
        }
        return ResponseEntity.ok(service.listarTodas());
    }
}
