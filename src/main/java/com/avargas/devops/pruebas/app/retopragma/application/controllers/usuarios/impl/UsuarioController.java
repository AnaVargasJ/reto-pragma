package com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios.impl;

import com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios.IUsuarioController;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.IUsuarioService;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements IUsuarioController {

    private final IUsuarioService usuarioService;

    @Override
    @PostMapping("/crearPropietario")
    public ResponseEntity<?> crearPropietario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearPropietario(usuarioDTO);
    }


}
