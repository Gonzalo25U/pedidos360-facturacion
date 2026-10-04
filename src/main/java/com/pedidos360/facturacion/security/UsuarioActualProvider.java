package com.pedidos360.facturacion.security;

public interface UsuarioActualProvider {
    String obtenerUsuarioId();
    boolean esAdmin();
}

