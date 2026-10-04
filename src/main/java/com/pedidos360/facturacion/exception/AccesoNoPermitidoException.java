package com.pedidos360.facturacion.exception;

public class AccesoNoPermitidoException extends RuntimeException {
    public AccesoNoPermitidoException(String mensaje) {
        super(mensaje);
    }
}
