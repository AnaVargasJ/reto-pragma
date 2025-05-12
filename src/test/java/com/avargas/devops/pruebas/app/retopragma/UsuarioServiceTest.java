package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.propietarios.impl.PropietarioService;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioDTO;
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

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

    @Autowired
    private PropietarioService usuarioService;

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

        UsuarioPropietarioDTO usuarioPropietarioDTO = UsuarioPropietarioDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("test@example.com")
                .numeroDocumento("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioPropietarioDTO, "usuarioDTO");

        ResponseEntity<?> response = usuarioService.crearPropietario(usuarioPropietarioDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Propietario creado correctamente", response.getBody());


        Optional<Usuarios> usuario = usuarioRepository.buscarPorCorreo(usuarioPropietarioDTO.getCorreo());
        assertTrue(usuario.isPresent());

    }

    @Test
    @Order(2)
    void crearPropietarioCorreoDuplicado() {
        crearPropietarioExitoso();
        UsuarioPropietarioDTO usuarioPropietarioDTO = UsuarioPropietarioDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("test@example.com")
                .numeroDocumento("2345689")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioPropietarioDTO, "usuarioDTO");

        ResponseEntity<?> response = usuarioService.crearPropietario(usuarioPropietarioDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errores = (Map<String, String>) response.getBody();
        assertTrue(errores.containsKey("correo"));
    }

    @Test
    @Order(3)
    void crearPropietarioDocumentoDuplicado() {
        crearPropietarioExitoso();
        UsuarioPropietarioDTO usuarioPropietarioDTO = UsuarioPropietarioDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("testo1@example.com")
                .numeroDocumento("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioPropietarioDTO, "usuarioDTO");

        ResponseEntity<?> response = usuarioService.crearPropietario(usuarioPropietarioDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errores = (Map<String, String>) response.getBody();
        assertTrue(errores.containsKey("numeroDocumento"));
    }

    @Test
    @Order(4)
    void crearPropietarioMenorEdad() {
        crearPropietarioExitoso();
        UsuarioPropietarioDTO usuarioPropietarioDTO = UsuarioPropietarioDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("testo1@example.com")
                .numeroDocumento("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2020")
                .clave("password")
                .build();

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioPropietarioDTO, "usuarioDTO");

        ResponseEntity<?> response = usuarioService.crearPropietario(usuarioPropietarioDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());


        Map<String, String> errores = (Map<String, String>) response.getBody();
        assertTrue(errores.containsKey("fechaNacimiento"));
    }

    @Test
    @Order(5)
    void crearPropietarioNull() {
        UsuarioPropietarioDTO usuarioPropietarioDTO = new UsuarioPropietarioDTO();

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioPropietarioDTO, "usuarioDTO");

        ResponseEntity<?> response = usuarioService.crearPropietario(usuarioPropietarioDTO, bindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al crear el propietario", response.getBody());


    }
}