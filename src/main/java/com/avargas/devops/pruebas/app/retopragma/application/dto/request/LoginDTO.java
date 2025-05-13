package com.avargas.devops.pruebas.app.retopragma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Correo electrónico del usuario", example = "usuario@correo.com", type = "string", format = "email")
    private String correo;

    @Schema(description = "Clave del usuario, entre 6 y 13 caracteres", example = "clave123", type = "string", minLength = 6, maxLength = 13)
    private String clave;
}
