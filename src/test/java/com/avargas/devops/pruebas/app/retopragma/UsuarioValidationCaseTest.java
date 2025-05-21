package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioValidationCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(OrderAnnotation.class)
class UsuarioValidationCaseTest {

    private UsuarioValidationCase validation;

    @BeforeEach
    void setUp() {
        validation = new UsuarioValidationCase();
    }

    @Test
    @Order(1)
    void validar_usuarioValido_noLanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        assertDoesNotThrow(() -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(2)
    void validar_nombreNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setNombre(null);
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(3)
    void validar_apellidoNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setApellido(null);
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(4)
    void validar_documentoNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setNumeroDocumento(null);
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(5)
    void validar_documentoNoNumerico_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setNumeroDocumento("abc123");
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(6)
    void validar_celularNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setCelular(null);
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(7)
    void validar_correoNulo_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setCorreo(null);
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(8)
    void validar_correoInvalido_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setCorreo("correo-invalido");
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(9)
    void validar_celularInvalido_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setCelular("123-456");
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(10)
    void validar_fechaNacimientoNula_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setFechaNacimiento(null);
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(11)
    void validar_menorDeEdad_lanzaExcepcion() {
        UsuarioModel usuario = usuarioBase();
        usuario.setFechaNacimiento(Date.from(LocalDate.now().minusYears(17)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        assertThrows(UsuariosDomainException.class, () -> validation.validateUserFields(usuario));
    }

    @Test
    @Order(12)
    void login_correoNulo_lanzaExcepcion() {
        assertThrows(UsuariosDomainException.class,
                () -> validation.validaLoginFiels(null, "clave123"));
    }

    @Test
    @Order(13)
    void login_correoInvalido_lanzaExcepcion() {
        assertThrows(UsuariosDomainException.class,
                () -> validation.validaLoginFiels("correo-no", "clave123"));
    }

    @Test
    @Order(14)
    void login_claveNula_lanzaExcepcion() {
        assertThrows(UsuariosDomainException.class,
                () -> validation.validaLoginFiels("correo@ok.com", null));
    }

    @Test
    @Order(15)
    void login_datosValidos_noLanzaExcepcion() {
        assertDoesNotThrow(() -> validation.validaLoginFiels("correo@ok.com", "clave123"));
    }




    private UsuarioModel usuarioBase() {
        return UsuarioModel.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .numeroDocumento("123456")
                .celular("+573001112233")
                .correo("juan@example.com")
                .fechaNacimiento(Date.from(LocalDate.of(2000, 1, 1)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .clave("clave123")
                .build();
    }
}
