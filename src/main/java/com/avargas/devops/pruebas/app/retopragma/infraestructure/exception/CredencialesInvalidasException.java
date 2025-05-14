package com.avargas.devops.pruebas.app.retopragma.infraestructure.exception;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}

