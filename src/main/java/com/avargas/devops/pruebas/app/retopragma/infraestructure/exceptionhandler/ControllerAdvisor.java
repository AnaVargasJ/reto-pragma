package com.avargas.devops.pruebas.app.retopragma.infraestructure.exceptionhandler;


import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.exception.CredencialesInvalidasException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.exception.NoDataFoundException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.exception.TokenInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

import static com.avargas.devops.pruebas.app.retopragma.infraestructure.exceptionhandler.ExceptionResponse.*;


@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ResponseDTO> handleNoDataFoundException(NoDataFoundException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ResponseDTO.builder()
                        .mensaje(NO_DATA_FOUND.getMessage())
                        .respuesta(null)
                        .codigo(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }

    @ExceptionHandler(UsuariosDomainException.class)
    public ResponseEntity<ResponseDTO> handleUsuariosDomainException(UsuariosDomainException ex) {

        String message = ex.getMessage();

        String tipoMensaje = switch (message) {
            case "El usuario debe ser mayor de edad" -> USUARIO_MENOR_EDAD.getMessage();
            case "El correo ya está registrado" -> CORREO_DUPLICADO.getMessage();
            case "El número de documento ya está registrado." -> DOCUMENTO_DUPLICADO.getMessage();
            case "El rol 'PROP' no existe." -> ROL_NO_ENCONTRADO.getMessage();
            default -> CAMPOS_INVALIDOS.getMessage();
        };

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDTO.builder()
                        .mensaje(tipoMensaje)
                        .respuesta(Map.of("error", message))
                        .codigo(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGeneralError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseDTO.builder()
                        .mensaje(ERROR_INTERNO.getMessage())
                        .respuesta(Map.of("error", ex.getMessage()))
                        .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build()
        );
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ResponseDTO> handleCredencialesInvalidas(CredencialesInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseDTO.builder()
                        .mensaje("Credenciales inválidas")
                        .respuesta(Map.of("error", ex.getMessage()))
                        .codigo(HttpStatus.UNAUTHORIZED.value())
                        .build()
        );
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ResponseDTO> handleTokenInvalido(TokenInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseDTO.builder()
                        .mensaje("Token inválido")
                        .respuesta(Map.of("error", ex.getMessage()))
                        .codigo(HttpStatus.UNAUTHORIZED.value())
                        .build()
        );
    }


}
