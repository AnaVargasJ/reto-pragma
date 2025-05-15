package com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.propietarios.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.UsuarioDTOResponse;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.IUsuarioPropietarioHandler;
import com.avargas.devops.pruebas.app.retopragma.application.mapper.IUsuarioRequestMapper;
import com.avargas.devops.pruebas.app.retopragma.application.mapper.IUsuarioResponseMapper;
import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.propietarios.IUsuarioServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.exception.TokenInvalidoException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.security.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioPropietarioHandler implements IUsuarioPropietarioHandler {

    private final IUsuarioServicePort iUsuarioServicePort;
    private final IUsuarioRequestMapper iUsuarioPropietarioRequestMapper;
    private final IUsuarioResponseMapper usuarioResponseMapper;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public ResponseEntity<?> crearPropietario(UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioModel usuarioModel = iUsuarioPropietarioRequestMapper.toUsuarioModel(usuarioRequestDTO);

        iUsuarioServicePort.createUser(usuarioModel);
        return new ResponseEntity<>(ResponseDTO.builder()
                .mensaje("Propietario creado correctamente")
                .codigo(HttpStatus.CREATED.value())
                .build(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {
        try {
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
            UsuarioModel usuarioModel = iUsuarioPropietarioRequestMapper.toLoginModel(loginDTO);
            usuarioModel = iUsuarioServicePort.login(usuarioModel.getCorreo(), usuarioModel.getClave());
            Authentication authentication = jwtAuthenticationFilter.authenticateUser(usuarioModel);
            ResponseDTO respuesta = jwtAuthenticationFilter.generateTokenResponse(authentication);

            return ResponseEntity.ok(respuesta);
        } catch (IOException e) {
            throw new TokenInvalidoException("Error al generar el token JWT");
        }
    }


    @Override
    public ResponseEntity<?> buscarPorCorreo(String correo) {
        UsuarioModel usuarioModel = iUsuarioServicePort.getUsuarioByCorreo(correo);
        UsuarioDTOResponse response = usuarioResponseMapper.toUsuarioDTO(usuarioModel);
        return new ResponseEntity<>(ResponseDTO.builder()
                .mensaje("Usuario encontrado exitosamente")
                .respuesta(response)
                .codigo(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

}