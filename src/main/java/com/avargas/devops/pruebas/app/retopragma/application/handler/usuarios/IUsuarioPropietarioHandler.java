package com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios;


import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IUsuarioPropietarioHandler {

    ResponseEntity<?> crearPropietario(UsuarioRequestDTO usuarioRequestDTO);
    ResponseEntity<?> login(LoginDTO loginDTO);
    ResponseEntity<?> buscarPorCorreo(String correo);

}
