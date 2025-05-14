package com.avargas.devops.pruebas.app.retopragma.infraestructure.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String mensaje) {
        super(mensaje);
    }
}
