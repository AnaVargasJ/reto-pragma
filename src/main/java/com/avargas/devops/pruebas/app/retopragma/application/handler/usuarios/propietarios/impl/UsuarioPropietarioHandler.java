package com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.propietarios.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.UsuarioDTOResponse;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.IUsuarioPropietarioHandler;
import com.avargas.devops.pruebas.app.retopragma.application.mapper.propietarios.IUsuarioPropietarioRequestMapper;
import com.avargas.devops.pruebas.app.retopragma.domain.api.IUsuarioServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.security.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioPropietarioHandler implements IUsuarioPropietarioHandler {

    private final IUsuarioServicePort iUsuarioServicePort;
    private final IUsuarioPropietarioRequestMapper iUsuarioPropietarioRequestMapper;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public ResponseEntity<?> crearPropietario(UsuarioPropietarioRequestDTO usuarioPropietarioRequestDTO) {

        UsuarioModel usuarioModel = iUsuarioPropietarioRequestMapper.toUsuarioModel(usuarioPropietarioRequestDTO);

        iUsuarioServicePort.createUser(usuarioModel);
        return new ResponseEntity<>(ResponseDTO.builder()
                .mensaje("Propietario creado correctamente")
                .codigo(HttpStatus.CREATED.value())
                .build(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        Authentication authentication = null;
        Map<String, Object> respuesta = new HashMap<>();
        try {

            UsuarioModel usuarioModel = iUsuarioPropietarioRequestMapper.toLoginModel(loginDTO);
            authentication = jwtAuthenticationFilter.authenticateUser(usuarioModel);
            respuesta = jwtAuthenticationFilter.generateTokenResponse(authentication);
            return ResponseEntity.ok(respuesta);
        } catch (AuthenticationException e ){
            respuesta.put("error", e.getMessage());
            respuesta.put("mensaje", "credenciales invalidas");
            respuesta.put("codigo", HttpStatus.UNAUTHORIZED.value());

            return new ResponseEntity<>(respuesta, HttpStatus.UNAUTHORIZED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> buscarPorCorreo(String correo) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            return usuarioRepository.buscarPorCorreo(correo)
                    .map(usuarios -> {
                        try {
                            UsuarioDTOResponse usuarioDTO = null;
                            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
                        } catch (Exception e) {
                            log.error("Error al convertir el json");
                            respuesta.put("error", e.getMessage());
                            respuesta.put("mensaje", "Errores de validación en la solicitud");
                            respuesta.put("codigo", HttpStatus.BAD_REQUEST.value());
                            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
                        }
                    })
                    .orElseGet(() -> {
                        ;
                        respuesta.put("error", "No se encontró el usuario");
                        respuesta.put("mensaje", "No se encontró el usuario");
                        respuesta.put("codigo", HttpStatus.NOT_FOUND.value());
                        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            log.error("Error al convertir el json");
            respuesta.put("error", e.getMessage());
            respuesta.put("mensaje", "Errores de validación en la solicitud");
            respuesta.put("codigo", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }

    private Boolean esMayorDeEdad(String fechaNacimiento) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaNacimiento, formatter);
            return Period.between(fecha, LocalDate.now()).getYears() >= 18;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}