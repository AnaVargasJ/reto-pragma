package com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.empleados.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioEmpleadoDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioDTO;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.empleados.IEmpleadoService;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.constants.Rol;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Roles;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Usuarios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpleadoService implements IEmpleadoService {

    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericConverter genericConverter;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public ResponseEntity<?> crearEmpleado(@Valid UsuarioEmpleadoDTO usuarioEmpleadoDTO) {
        Usuarios usuarios = new Usuarios();
        try {
            Roles rol = rolesRepository.findByDescripcion(Rol.EMP.getDescripcion()).orElseGet(null);
            if (rol == null) {
                log.error("Error al crear el empleado: Rol no encontrado");
                return ResponseEntity.badRequest().body("Error al crear el empleado: Rol no encontrado");
            }
            usuarioEmpleadoDTO.setClave(passwordEncoder.encode(usuarioEmpleadoDTO.getClave()));
            usuarios = genericConverter.mapDtoToEntity(usuarioEmpleadoDTO, Usuarios.class);
            usuarios.setRol(rol);
            usuarioRepository.save(usuarios);
            return new ResponseEntity<>("Empleado creado correctamente", HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Error al crear el empleado: {}", e.getMessage());
            return new ResponseEntity<>("Error al crear el empleado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
