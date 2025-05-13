package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.propietarios.impl.UsuarioPropietarioHandler;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Roles;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Usuarios;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

    @Autowired
    private UsuarioPropietarioHandler usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;




    @Test
    @Order(1)
    void crearPropietarioExitoso() {
        Roles rolProp = Roles.builder()
                .nombre("PROP")
                .descripcion("Propietario")
                .build();

        rolesRepository.save(rolProp);

        UsuarioPropietarioRequestDTO   usuarioPropietarioRequestDTO = UsuarioPropietarioRequestDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("test@example.com")
                .numeroDocumento("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

        ResponseEntity<?> response = usuarioService.crearPropietario(usuarioPropietarioRequestDTO);
        ResponseDTO responseDTO = ResponseDTO.builder()
                .mensaje("Propietario creado correctamente")
                .codigo(HttpStatus.CREATED.value()).build();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        Optional<Usuarios> usuario = usuarioRepository.buscarPorCorreo(usuarioPropietarioRequestDTO.getCorreo());
        assertTrue(usuario.isPresent());

    }

    @Test
    @Order(2)
    void crearPropietarioCorreoDuplicado() {

        crearPropietarioExitoso();

        UsuarioPropietarioRequestDTO dtoDuplicado = UsuarioPropietarioRequestDTO.builder()
                .nombre("Otro")
                .apellido("Usuario")
                .correo("test@example.com") // mismo correo
                .numeroDocumento("999999999")
                .celular("+573000000000")
                .fechaNacimiento("12/05/2000")
                .clave("password123")
                .build();

        UsuariosDomainException exception = assertThrows(
                UsuariosDomainException.class,
                () -> usuarioService.crearPropietario(dtoDuplicado)
        );

        assertEquals("El correo ya está registrado", exception.getMessage());
    }


  @Test
    @Order(3)
    void crearPropietarioDocumentoDuplicado() {
        crearPropietarioExitoso();
        UsuarioPropietarioRequestDTO usuarioPropietarioDTO = UsuarioPropietarioRequestDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("testo1@example.com")
                .numeroDocumento("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

      UsuariosDomainException exception = assertThrows(
              UsuariosDomainException.class,
              () -> usuarioService.crearPropietario(usuarioPropietarioDTO)
      );

      assertEquals("El documento ya está registrado", exception.getMessage());
    }

   @Test
    @Order(4)
    void crearPropietarioMenorEdad() {
        crearPropietarioExitoso();
        UsuarioPropietarioRequestDTO usuarioPropietarioDTO =  UsuarioPropietarioRequestDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("testo1@example.com")
                .numeroDocumento("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2020")
                .clave("password")
                .build();

       UsuariosDomainException exception = assertThrows(
               UsuariosDomainException.class,
               () -> usuarioService.crearPropietario(usuarioPropietarioDTO)
       );

       assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
    }

}