package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios;


import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IUsuarioController {

    ResponseEntity<?> crearPropietario(UsuarioPropietarioRequestDTO usuarioPropietarioRequestDTO);

    ResponseEntity<?> buscarPorCorreo(String correo);
}
