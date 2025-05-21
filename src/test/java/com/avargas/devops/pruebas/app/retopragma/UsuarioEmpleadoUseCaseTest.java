package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IPasswordPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IUsuarioPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioValidationCase;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.empleado.UsuarioEmpleadoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(OrderAnnotation.class)
class UsuarioEmpleadoUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @Mock
    private IPasswordPersistencePort passwordPersistencePort;

    private UsuarioValidationCase usuarioValidationCase;

    @InjectMocks
    private UsuarioEmpleadoUseCase usuarioEmpleadoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioValidationCase = new UsuarioValidationCase();
        usuarioEmpleadoUseCase = new UsuarioEmpleadoUseCase(
                usuarioPersistencePort,
                usuarioValidationCase,
                passwordPersistencePort
        );
    }

    private UsuarioModel usuarioBase() {
        return UsuarioModel.builder()
                .nombre("Empleado")
                .apellido("Ejemplo")
                .numeroDocumento("987654321")
                .celular("+573002223344")
                .correo("empleado@empresa.com")
                .fechaNacimiento(Date.from(LocalDate.of(1990, 1, 1)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .clave("claveEmpleado")
                .build();
    }

    @Test
    @Order(1)
    void createUserEmpleado_datosValidos_creaUsuario() {
        UsuarioModel usuario = usuarioBase();

        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(null);
        when(usuarioPersistencePort.getUsuarioByNumeroDocumento(usuario.getNumeroDocumento())).thenReturn(null);
        when(usuarioPersistencePort.findRolByRol("EMP")).thenReturn(RolModel.builder().nombre("EMP").build());
        when(passwordPersistencePort.encriptarClave("claveEmpleado")).thenReturn("claveEncriptada");

        assertDoesNotThrow(() -> usuarioEmpleadoUseCase.createUserEmpleado(usuario));
        verify(usuarioPersistencePort).saveUsuario(any());
    }

    @Test
    @Order(2)
    void createUserEmpleado_correoYaRegistrado_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();

        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(new UsuarioModel());

        assertThrows(UsuariosDomainException.class, () -> usuarioEmpleadoUseCase.createUserEmpleado(usuario));
    }

    @Test
    @Order(3)
    void createUserEmpleado_documentoYaRegistrado_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();

        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(null);
        when(usuarioPersistencePort.getUsuarioByNumeroDocumento(usuario.getNumeroDocumento())).thenReturn(new UsuarioModel());

        assertThrows(UsuariosDomainException.class, () -> usuarioEmpleadoUseCase.createUserEmpleado(usuario));
    }

    @Test
    @Order(4)
    void createUserEmpleado_rolNoExiste_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();

        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(null);
        when(usuarioPersistencePort.getUsuarioByNumeroDocumento(usuario.getNumeroDocumento())).thenReturn(null);
        when(usuarioPersistencePort.findRolByRol("EMP")).thenReturn(null);

        assertThrows(UsuariosDomainException.class, () -> usuarioEmpleadoUseCase.createUserEmpleado(usuario));
    }
}
