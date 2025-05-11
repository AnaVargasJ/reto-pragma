package com.avargas.devops.pruebas.app.retopragma.application.dto.response;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.FieldMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @FieldMapping("id")
    private Long idRol;
    private String nombre;
    private String descripcion;

    // Getters and Setters

}
