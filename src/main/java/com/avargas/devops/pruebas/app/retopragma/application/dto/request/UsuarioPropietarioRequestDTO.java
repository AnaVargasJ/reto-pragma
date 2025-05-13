package com.avargas.devops.pruebas.app.retopragma.application.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioPropietarioRequestDTO implements Serializable {


    private static final long serialVersionUID = 1L;


    @Schema(description = "Correo electrónico",
            example = "juan@example.com",
            type = "string",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "Apellido del usuario",
            example = "Pérez",
            type = "string",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellido;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "236456",
            type = "string",
            requiredMode = Schema.RequiredMode.REQUIRED
    )

    private String numeroDocumento;

    @Schema(description = "Número de celular",
            example = "+573121234566",
            type = "string",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String celular;

    @Schema(description = "Fecha de generación",
            example = "01/01/2024", type = "string",
            pattern = "^\\d{2}/\\d{2}/\\d{4}$",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String fechaNacimiento;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "usuario@example.com",
            type = "string",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String correo;

    @Schema(description = "Clave del usuario, entre 6 y 13 caracteres",
            example = "clave123",
            type = "string",
            minLength = 6, maxLength = 13,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String clave;

}
