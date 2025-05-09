package com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.FieldMapping;
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
public class UsuarioDTO implements Serializable {

        private static final long serialVersionUID = 1L;

      /*  @FieldMapping("id")
        private Long idUsuario;*/

        @NotBlank
        private String nombre;

        @NotBlank
        private String apellido;

        @NotBlank
        @Pattern(regexp = "^\\d+$", message = "El documento debe contener solo números")
        private String numeroDocumento;

        @NotBlank
        @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Número de celular inválido")
        private String celular;

        @NotBlank
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Formato de fecha inválido. Use dd/MM/yyyy")
        @FieldMapping("fechaNacimiento")
        private String fechaNacimiento;

        @NotBlank
        @Email
        private String correo;

        @NotBlank
        @Size(min = 6, max = 13)
        private String clave;


}
