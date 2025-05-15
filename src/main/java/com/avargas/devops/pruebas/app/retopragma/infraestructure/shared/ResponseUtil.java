package com.avargas.devops.pruebas.app.retopragma.infraestructure.shared;

import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;

public class ResponseUtil {

    public static ResponseDTO success(String mensaje, Object respuesta) {
        return ResponseDTO.builder()
                .mensaje(mensaje)
                .respuesta(respuesta)
                .codigo(HttpStatus.OK.value())
                .build();
    }

    public static ResponseDTO error(String mensaje, Object respuesta, int codigo) {
        return ResponseDTO.builder()
                .mensaje(mensaje)
                .respuesta(respuesta)
                .codigo(codigo)
                .build();
    }

    public static ResponseDTO success(String mensaje) {
        return ResponseDTO.builder()
                .mensaje(mensaje)
                .codigo(HttpStatus.CREATED.value())
                .build();
    }
}
