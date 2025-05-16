package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.clientes.IUsuarioClienteHandler;
import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Roles;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Usuarios;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class UsuarioClienteServiceTest {

    @Autowired
    private IUsuarioClienteHandler iUsuarioClienteHandler;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @Test
    @Order(1)
    void crearClienteExitoso() {
        Roles rolProp = Roles.builder()
                .nombre("CLI")
                .descripcion("Cliente")
                .build();

        rolesRepository.save(rolProp);

        UsuarioRequestDTO usuarioRequestDTO = UsuarioRequestDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("test@example.com")
                .numeroDocumento("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

        iUsuarioClienteHandler.crearCliente(usuarioRequestDTO);
        ResponseDTO responseDTO = ResponseDTO.builder()
                .mensaje("Cliente creado correctamente")
                .codigo(HttpStatus.CREATED.value()).build();
        Optional<Usuarios> usuario = usuarioRepository.buscarPorCorreo(usuarioRequestDTO.getCorreo());
        assertTrue(usuario.isPresent());

    }

    @Test
    @Order(2)
    void crearClienteCorreoDuplicado() {
        crearClienteExitoso();
        UsuarioRequestDTO dtoDuplicado = UsuarioRequestDTO.builder()
                .nombre("Otro")
                .apellido("Usuario")
                .correo("test@example.com")
                .numeroDocumento("999999999")
                .celular("+573000000000")
                .fechaNacimiento("12/05/2000")
                .clave("password123")
                .build();

        UsuariosDomainException exception = assertThrows(
                UsuariosDomainException.class,
                () -> iUsuarioClienteHandler.crearCliente(dtoDuplicado)
        );
        assertEquals("El correo ya está registrado", exception.getMessage());
    }

    @Test
    @Order(3)
    void crearClienteDocumentoDuplicado() {
        crearClienteExitoso();
        UsuarioRequestDTO usuarioPropietarioDTO = UsuarioRequestDTO.builder()
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
                () -> iUsuarioClienteHandler.crearCliente(usuarioPropietarioDTO)
        );
        assertEquals("El documento ya está registrado", exception.getMessage());
    }
}
