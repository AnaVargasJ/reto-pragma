package com.avargas.devops.pruebas.app.retopragma.application.services.usuarios;


import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface IUsuarioService {

    ResponseEntity<?> crearPropietario(UsuarioDTO usuarioDTO);
    ResponseEntity<?> login(LoginDTO loginDTOO);
    ResponseEntity<?> buscarPorCorreo(String correo);


}
