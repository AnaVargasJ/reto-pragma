package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios;


import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IUsuarioController {

    ResponseEntity<?> crearPropietario(UsuarioRequestDTO usuarioRequestDTO);

    ResponseEntity<?> buscarPorCorreo(String correo);

    ResponseEntity<?> crearCliente(UsuarioRequestDTO usuarioRequestDTO);
}
