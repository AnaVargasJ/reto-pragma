package com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.impl;

import com.avargas.devops.pruebas.app.retopragma.application.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.application.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.IUsuarioService;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.constants.Rol;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.exceptions.NoDataFoundException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Roles;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Usuarios;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericConverter genericConverter;

    @Override
    @Transactional
    public ResponseEntity<?> crearPropietario(UsuarioDTO usuarioDTO) {
        Usuarios usuarios = new Usuarios();
        try{
            Optional<Usuarios> usuarioExistente = usuarioRepository.buscarPorCorreo(usuarioDTO.getCorreo());
            usuarioRepository.buscarPorCorreo(usuarioDTO.getCorreo())
                    .ifPresent(u -> {
                        throw new NoDataFoundException("Correo ya registrado");
                    });
            usuarioRepository.existsByUsuarioDocumento(usuarioDTO.getNumeroDocumento())
                    .ifPresent(u -> {
                        throw new NoDataFoundException("Numero de documento ya registrado");
                    });

            Roles rol = rolesRepository.findByDescripcion(Rol.PROP.getDescripcion()).orElseGet(null);
            if (rol == null) {
                log.error("Error al crear el propietario: Rol no encontrado");
                return ResponseEntity.badRequest().body("Error al crear el propietario: Rol no encontrado");
            }
            usuarioDTO.setClave(passwordEncoder.encode(usuarioDTO.getClave()));
            usuarios = genericConverter.mapEntityToDto(usuarioDTO, Usuarios.class);
            usuarios.setRol(rol);
            usuarioRepository.save(usuarios);
            return new ResponseEntity<>("Propietario creado correctamente", HttpStatus.CREATED);

        }catch (Exception e){
            log.error("Error al crear el propietario: {}", e.getMessage());
            return new ResponseEntity<>("Error al crear el propietario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
