package com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IUsuarioController {

    ResponseEntity<?> crearPropietario(UsuarioDTO usuarioDTO, BindingResult bindingResul);

    ResponseEntity<?> buscarPorCorreo(String correo);
}
