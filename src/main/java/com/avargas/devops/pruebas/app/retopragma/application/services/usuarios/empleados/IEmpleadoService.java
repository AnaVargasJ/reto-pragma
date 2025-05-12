package com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.empleados;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioEmpleadoDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IEmpleadoService {
    ResponseEntity<?> crearEmpleado(UsuarioEmpleadoDTO usuarioEmpleadoDTO);
}
