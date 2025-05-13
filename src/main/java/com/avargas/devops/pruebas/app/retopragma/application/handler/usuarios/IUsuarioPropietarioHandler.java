package com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios;


import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IUsuarioPropietarioHandler {

    ResponseEntity<?> crearPropietario(UsuarioPropietarioRequestDTO usuarioPropietarioRequestDTO);
    ResponseEntity<?> login(LoginDTO loginDTO);
    ResponseEntity<?> buscarPorCorreo(String correo);

}
