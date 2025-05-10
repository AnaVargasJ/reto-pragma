package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.application.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.application.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.impl.UsuarioService;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Roles;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Usuarios;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GenericConverter genericConverter;



    @Test
    @Order(1)
    void crearPropietarioExitoso() {

        Roles rolProp = Roles.builder()
                .nombre("PROP")
                .descripcion("Propietario")
                .build();

        rolesRepository.save(rolProp);

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("test@example.com")
                .numeroDocumento("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioDTO, "usuarioDTO");

        ResponseEntity<?> response = usuarioService.crearPropietario(usuarioDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Propietario creado correctamente", response.getBody());


        Optional<Usuarios> usuario = usuarioRepository.buscarPorCorreo(usuarioDTO.getCorreo());
        assertTrue(usuario.isPresent());

    }
}
