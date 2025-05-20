package com.avargas.devops.pruebas.app.retopragma.domain.exception;

import lombok.Getter;

@Getter
public enum UsuariosException {

    NO_DATA_FOUND("Usuario no encontrado");

    private final String message;

    UsuariosException(String message) {
        this.message = message;
    }
}
