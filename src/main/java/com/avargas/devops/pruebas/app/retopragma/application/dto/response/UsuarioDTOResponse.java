package com.avargas.devops.pruebas.app.retopragma.application.dto.response;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.FieldMapping;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTOResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @FieldMapping("id")
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private String celular;
    private String fechaNacimiento;
    private String correo;
    private String clave;
    private RolDTO rol;
}
