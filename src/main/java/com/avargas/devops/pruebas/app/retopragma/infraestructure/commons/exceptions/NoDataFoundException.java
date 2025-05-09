package com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.exceptions;

public class NoDataFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public NoDataFoundException(String message) {
        super(message);
    }
    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }



}
