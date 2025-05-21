package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IPasswordPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IUsuarioPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioUseCase;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioValidationCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @Mock
    private IPasswordPersistencePort passwordPersistencePort;

    @Mock
    private UsuarioValidationCase usuarioValidationCase;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;



    private UsuarioModel usuarioBase() {
        return UsuarioModel.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .numeroDocumento("123456789")
                .celular("+573001112233")
                .correo("juan@example.com")
                .fechaNacimiento(Date.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .clave("clave123")
                .build();
    }

    @Test
    @Order(1)
    void crearUsuario_nombreNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setNombre(null);
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(2)
    void crearUsuario_apellidoNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setApellido(null);
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(3)
    void crearUsuario_documentoNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setNumeroDocumento(null);
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(4)
    void crearUsuario_documentoNoNumerico_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setNumeroDocumento("abc123");
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(5)
    void crearUsuario_celularNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setCelular(null);
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(6)
    void crearUsuario_correoNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setCorreo(null);
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(7)
    void crearUsuario_correoInvalido_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setCorreo("correo-no-valido");
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(8)
    void crearUsuario_celularInvalido_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setCelular("123-abc");
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(9)
    void crearUsuario_fechaNacimientoNula_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setFechaNacimiento(null);
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(10)
    void crearUsuario_menorDeEdad_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setFechaNacimiento(Date.from(LocalDate.now().minusYears(16)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.createUser(usuario));
    }

    @Test
    @Order(11)
    void login_correoNulo_lanzaExcepcion() {
        assertThrows(UsuariosDomainException.class,
                () -> usuarioUseCase.login(null, "clave123"));
    }



    @Test
    @Order(12)
    void login_correoInvalido_lanzaExcepcion() {
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.login("correo-no", "clave123"));
    }

    @Test
    @Order(13)
    void login_claveNula_lanzaExcepcion() {
        assertThrows(UsuariosDomainException.class, () -> usuarioUseCase.login("correo@ok.com", null));
    }

    @Test
    @Order(14)
    void login_datosValidos_noLanzaExcepcion() {
        UsuarioModel usuario = UsuarioModel.builder()
                .correo("correo@ok.com")
                .clave("clave123")
                .build();

        when(usuarioPersistencePort.getUsuarioByCorreo("correo@ok.com")).thenReturn(usuario);


        when(passwordPersistencePort.esClaveValida("correo@ok.com", "clave123")).thenReturn(true);




        assertDoesNotThrow(() -> usuarioUseCase.login("correo@ok.com", "clave123"));
    }


    @Test
    @Order(15)
    void crearUsuario_todoValido_procesaCorrectamente() {
        UsuarioModel usuario = usuarioBase();
        when(usuarioPersistencePort.getUsuarioByCorreo(usuario.getCorreo())).thenReturn(null);
        when(usuarioPersistencePort.getUsuarioByNumeroDocumento(usuario.getNumeroDocumento())).thenReturn(null);
        when(usuarioPersistencePort.findRolByRol("PROP")).thenReturn(RolModel.builder().nombre("PROP").build());
        when(passwordPersistencePort.encriptarClave(usuario.getClave())).thenReturn("clave123Encriptada");

        assertDoesNotThrow(() -> usuarioUseCase.createUser(usuario));
        verify(usuarioPersistencePort).saveUsuario(any());
    }

    @Test
    @Order(16)
    void getUsuarioByCorreo_usuarioExiste_retornaUsuario() {
        UsuarioModel mockUsuario = UsuarioModel.builder()
                .correo("correo@ok.com")
                .build();

        when(usuarioPersistencePort.getUsuarioByCorreo("correo@ok.com")).thenReturn(mockUsuario);

        UsuarioModel resultado = usuarioUseCase.getUsuarioByCorreo("correo@ok.com");

        assertEquals("correo@ok.com", resultado.getCorreo());
        verify(usuarioPersistencePort).getUsuarioByCorreo("correo@ok.com");
    }

    @Test
    @Order(17)
    void getUsuarioByCorreo_usuarioNoExiste_lanzaExcepcion() {
        when(usuarioPersistencePort.getUsuarioByCorreo("noexiste@ok.com")).thenReturn(null);

        assertThrows(UsuariosDomainException.class,
                () -> usuarioUseCase.getUsuarioByCorreo("noexiste@ok.com"));
    }

    @Test
    @Order(18)
    void buscarPorIdUsuario_usuarioExiste_retornaUsuario() {
        UsuarioModel mockUsuario = UsuarioModel.builder()
                .correo("correo@ok.com")
                .build();

        when(usuarioPersistencePort.buscarPorIdUsuario(1L)).thenReturn(mockUsuario);

        UsuarioModel resultado = usuarioUseCase.buscarPorIdUsuario(1L);

        assertEquals("correo@ok.com", resultado.getCorreo());
        verify(usuarioPersistencePort).buscarPorIdUsuario(1L);
    }

    @Test
    @Order(19)
    void buscarPorIdUsuario_usuarioNoExiste_lanzaExcepcion() {
        when(usuarioPersistencePort.buscarPorIdUsuario(99L)).thenReturn(null);

        assertThrows(UsuariosDomainException.class,
                () -> usuarioUseCase.buscarPorIdUsuario(99L));
    }


}

