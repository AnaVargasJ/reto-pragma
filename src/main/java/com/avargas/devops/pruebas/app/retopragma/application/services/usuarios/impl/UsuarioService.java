package com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.impl;

import com.avargas.devops.pruebas.app.retopragma.application.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.application.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.IUsuarioService;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.constants.Rol;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.exceptions.NoDataFoundException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.security.auth.JwtAuthenticationFilter;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Roles;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Usuarios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericConverter genericConverter;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public ResponseEntity<?> crearPropietario(@Valid UsuarioDTO usuarioDTO, BindingResult bindingResult) {
        Usuarios usuarios = new Usuarios();
        try {

            if (usuarioRepository.buscarPorCorreo(usuarioDTO.getCorreo()).isPresent()) {
                bindingResult.rejectValue("correo", "correo.duplicado", "El correo ya está registrado");
            }

            if (usuarioRepository.existsByUsuarioDocumento(usuarioDTO.getNumeroDocumento()).isPresent()) {
                bindingResult.rejectValue("numeroDocumento", "documento.duplicado", "El número de documento ya está registrado");
            }

            if (!esMayorDeEdad(usuarioDTO.getFechaNacimiento())) {
                bindingResult.rejectValue("fechaNacimiento", "edad.invalida", "El usuario debe ser mayor de edad");
            }

            if (bindingResult.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                bindingResult.getFieldErrors().forEach(error ->
                        errores.put(error.getField(), error.getDefaultMessage())
                );
                return ResponseEntity.badRequest().body(errores);
            }

            Roles rol = rolesRepository.findByDescripcion(Rol.PROP.getDescripcion()).orElseGet(null);
            if (rol == null) {
                log.error("Error al crear el propietario: Rol no encontrado");
                return ResponseEntity.badRequest().body("Error al crear el propietario: Rol no encontrado");
            }
            usuarioDTO.setClave(passwordEncoder.encode(usuarioDTO.getClave()));
            usuarios = genericConverter.mapDtoToEntity(usuarioDTO, Usuarios.class);
            usuarios.setRol(rol);
            usuarioRepository.save(usuarios);
            return new ResponseEntity<>("Propietario creado correctamente", HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Error al crear el propietario: {}", e.getMessage());
            return new ResponseEntity<>("Error al crear el propietario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        Usuarios usuarios = new Usuarios();
        Authentication authentication = null;
        Map<String, Object> respuesta = new HashMap<>();
        try {
            usuarios = genericConverter.mapEntityToDto(loginDTO, Usuarios.class);
            authentication = jwtAuthenticationFilter.authenticateUser(usuarios);
            respuesta = jwtAuthenticationFilter.generateTokenResponse(authentication);
            return ResponseEntity.ok(respuesta);
        } catch (AuthenticationException | IOException e) {
            respuesta.put("error", e.getMessage());
            respuesta.put("mensaje", "credenciales invalidas");
            respuesta.put("codigo", HttpStatus.UNAUTHORIZED.value());

            return new ResponseEntity<>(respuesta, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<?> buscarPorCorreo(String correo) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            return usuarioRepository.buscarPorCorreo(correo)
                    .map(usuarios -> {
                        try{
                            UsuarioDTO usuarioDTO = genericConverter.mapEntityToDto(usuarios, UsuarioDTO.class);

                            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
                        } catch (Exception e) {
                            log.error("Error al convertir el json");
                            respuesta.put("error", e.getMessage());
                            respuesta.put("mensaje","Errores de validación en la solicitud");
                            respuesta.put("codigo",HttpStatus.BAD_REQUEST.value());
                            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
                        }})
                    .orElseGet(() -> {;
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
            return false; // Considera inválido si no puede parsear la fecha
        }
    }

}