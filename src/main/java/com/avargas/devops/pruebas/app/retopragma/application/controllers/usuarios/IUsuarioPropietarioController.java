package com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IUsuarioPropietarioController {

    ResponseEntity<?> crearPropietario(UsuarioPropietarioDTO usuarioPropietarioDTO, BindingResult bindingResul);

    ResponseEntity<?> buscarPorCorreo(String correo);
}
