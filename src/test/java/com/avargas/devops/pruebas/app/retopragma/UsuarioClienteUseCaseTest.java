package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IPasswordPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IUsuarioPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioValidationCase;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.clientes.UsuarioClienteUseCase;
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
class UsuarioClienteUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @Mock
    private IPasswordPersistencePort passwordPersistencePort;

    private UsuarioValidationCase usuarioValidationCase;

    @InjectMocks
    private UsuarioClienteUseCase usuarioClienteUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioValidationCase = new UsuarioValidationCase();
        usuarioClienteUseCase = new UsuarioClienteUseCase(
                usuarioPersistencePort,
                usuarioValidationCase,
                passwordPersistencePort
        );
    }

    private UsuarioModel usuarioBase() {
        return UsuarioModel.builder()
                .nombre("Cliente")
                .apellido("Final")
                .numeroDocumento("123456789")
                .celular("+573001234567")
                .correo("cliente@test.com")
                .fechaNacimiento(Date.from(LocalDate.of(2000, 1, 1)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .clave("clave123")
                .build();
    }

    @Test
    @Order(1)
    void crearCliente_datosValidos_creaCliente() {
        UsuarioModel usuario = usuarioBase();

        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(null);
        when(usuarioPersistencePort.getUsuarioByNumeroDocumento(usuario.getNumeroDocumento())).thenReturn(null);
        when(usuarioPersistencePort.findRolByRol("CLI")).thenReturn(RolModel.builder().nombre("CLI").build());
        when(passwordPersistencePort.encriptarClave("clave123")).thenReturn("clave123Encriptada");

        assertDoesNotThrow(() -> usuarioClienteUseCase.crearCliente(usuario));
        verify(usuarioPersistencePort).saveUsuario(any());
    }

    @Test
    @Order(2)
    void crearCliente_correoYaRegistrado_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();

        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(new UsuarioModel());

        assertThrows(UsuariosDomainException.class, () -> usuarioClienteUseCase.crearCliente(usuario));
    }

    @Test
    @Order(3)
    void crearCliente_documentoYaRegistrado_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();

        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(null);
        when(usuarioPersistencePort.getUsuarioByNumeroDocumento(usuario.getNumeroDocumento())).thenReturn(new UsuarioModel());

        assertThrows(UsuariosDomainException.class, () -> usuarioClienteUseCase.crearCliente(usuario));
    }

    @Test
    @Order(4)
    void crearCliente_rolNoExiste_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();

        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(null);
        when(usuarioPersistencePort.getUsuarioByNumeroDocumento(usuario.getNumeroDocumento())).thenReturn(null);
        when(usuarioPersistencePort.findRolByRol("CLI")).thenReturn(null);

        assertThrows(UsuariosDomainException.class, () -> usuarioClienteUseCase.crearCliente(usuario));
    }
}