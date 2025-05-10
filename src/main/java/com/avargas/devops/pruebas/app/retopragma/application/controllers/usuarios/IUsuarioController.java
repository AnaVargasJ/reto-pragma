package com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IUsuarioController {

    ResponseEntity<?> crearPropietario(UsuarioDTO usuarioDTO, BindingResult bindingResul);
    ResponseEntity<?> login(LoginDTO loginDTO);

    ResponseEntity<?> buscarPorCorreo(String correo);
}
