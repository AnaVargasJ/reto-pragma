package com.avargas.devops.pruebas.app.retopragma.infraestructure.exceptionhandler;

public enum ExceptionResponse {

    CORREO_NO_ENCONTRADO("El usuario no fue encontrado."),
    CORREO_DUPLICADO("El correo ya esta registrado"),
    DOCUMENTO_DUPLICADO("El Numero de documento ya esta registrado"),
    ROL_NO_ENCONTRADO("El rol solicitado no existe."),
    USUARIO_MENOR_EDAD("El usuario debe ser mayor de edad."),
    CAMPOS_INVALIDOS("Hay campos inválidos en la solicitud."),
    ERROR_INTERNO("Ocurrió un error interno en el servidor."),
    NO_DATA_FOUND("No se encontraron datos para la solicitud."),
    CLAVE_INCORRECTA("La clave es incorrecta."),
    CORREO_INVALIDO("El correo no tiene una estructura válida."),
    CELULAR_INVALIDO("El número de celular no tiene un formato válido."),
    DOCUMENTO_INVALIDO("El documento de identidad debe ser numérico.");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
