package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios.validation.ValidationService;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioEmpleadoDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioDTO;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.empleados.impl.EmpleadoService;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.propietarios.impl.PropietarioService;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Roles;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Usuarios;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class UsuarioEmpleadoServiceTest {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @Test
    @Order(1)
    void crearEmpleadoExitoso() {
        Roles rolEmp = Roles.builder()
                .nombre("EMP")
                .descripcion("Empleado")
                .build();

        rolesRepository.save(rolEmp);

        UsuarioEmpleadoDTO usuarioEmpleadoDTO = UsuarioEmpleadoDTO.builder()
                .nombre("Test")
                .apellido("User")
                .correo("test@example.com")
                .documentoIdentidad("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

        ResponseEntity<?> response = empleadoService.crearEmpleado(usuarioEmpleadoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Empleado creado correctamente", response.getBody());

        Optional<Usuarios> usuario = usuarioRepository.buscarPorCorreo(usuarioEmpleadoDTO.getCorreo());
        assertTrue(usuario.isPresent());

    }

    @Test
    @Order(2)
    void ValidarNombreObligatorio400() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        UsuarioEmpleadoDTO usuarioEmpleadoDTO = UsuarioEmpleadoDTO.builder()
                .apellido("User")
                .correo("test@example.com")
                .documentoIdentidad("123456789")
                .celular("123456789")
                .fechaNacimiento("10/05/2000")
                .clave("password")
                .build();

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioEmpleadoDTO, "usuarioEmpleadoDTO");
        ResponseEntity<?> response =  validationService.validate(bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    void crearEmpleadoError() {
        Roles rolEmp = Roles.builder()
                .nombre("EMP")
                .descripcion("Empleado")
                .build();

        rolesRepository.save(rolEmp);

        UsuarioEmpleadoDTO usuarioEmpleadoDTO = new UsuarioEmpleadoDTO();

        ResponseEntity<?> response = empleadoService.crearEmpleado(usuarioEmpleadoDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al crear el empleado", response.getBody());

    }

}
