package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.empleado;


import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IUsuarioEmpleadoController {

    ResponseEntity<?> crearEmpleado(UsuarioRequestDTO usuarioRequestDTO);
}
