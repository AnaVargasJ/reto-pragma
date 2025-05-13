package com.avargas.devops.pruebas.app.retopragma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private static final long serialVersionUID = 1L;
    public String mensaje;
    public Object respuesta;
    public int codigo;
}
