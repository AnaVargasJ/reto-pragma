package com.avargas.devops.pruebas.app.retopragma.application.services.usuarios;


import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IUsuarioService {

    ResponseEntity<?> crearPropietario(UsuarioDTO usuarioDTO, BindingResult bindingResul);
    ResponseEntity<?> login(LoginDTO loginDTOO);
    ResponseEntity<?> buscarPorCorreo(String correo);


}
