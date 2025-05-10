package com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface IUsuarioController {

    ResponseEntity<?> crearPropietario(UsuarioDTO usuarioDTO);
    ResponseEntity<?> login(LoginDTO loginDTO);

    ResponseEntity<?> buscarPorCorreo(String correo);
}
