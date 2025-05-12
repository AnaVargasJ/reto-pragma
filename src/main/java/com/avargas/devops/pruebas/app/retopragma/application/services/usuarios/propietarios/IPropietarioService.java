package com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.propietarios;


import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IPropietarioService {

    ResponseEntity<?> crearPropietario(UsuarioPropietarioDTO usuarioPropietarioDTO, BindingResult bindingResul);
    ResponseEntity<?> login(LoginDTO loginDTOO);
    ResponseEntity<?> buscarPorCorreo(String correo);


}
