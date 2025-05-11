package com.avargas.devops.pruebas.app.retopragma.application.dto.request;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.FieldMapping;
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
public class UsuarioDTO implements Serializable {

        private static final long serialVersionUID = 1L;

        @FieldMapping("id")
        private Long idUsuario;

        @NotBlank
        @Schema(description = "Nombre del usuario", example = "Juan", type = "string")
        private String nombre;

        @NotBlank
        @Schema(description = "Apellido del usuario", example = "Pérez", type = "string")
        private String apellido;

        @NotBlank
        @Pattern(regexp = "^\\d+$", message = "El documento debe contener solo números")
        @Schema(description = "Número de documento", example = "123456789", type = "string")
        private String numeroDocumento;

        @NotBlank
        @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Número de celular inválido")
        @Schema(description = "Número de celular", example = "+573121234566", type = "string")
        private String celular;

        @NotBlank
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Formato de fecha inválido. Use dd/MM/yyyy")
        @Schema(description = "Fecha de generación", example = "01/01/2024", type = "string", pattern = "^\\d{2}/\\d{2}/\\d{4}$")
        @FieldMapping("fechaNacimiento")
        private String fechaNacimiento;


        @NotBlank
        @Email
        @Schema(description = "Correo electrónico del usuario", example = "usuario@correo.com", type = "string", format = "email")
        private String correo;

        @NotBlank
        @Size(min = 6, max = 13)
        @Schema(description = "Clave del usuario, entre 6 y 13 caracteres", example = "clave123", type = "string", minLength = 6, maxLength = 13)
        private String clave;


}
