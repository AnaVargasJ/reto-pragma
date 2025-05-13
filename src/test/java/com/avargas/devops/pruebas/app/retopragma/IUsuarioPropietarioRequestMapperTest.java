package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.application.mapper.propietarios.IUsuarioPropietarioRequestMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IUsuarioPropietarioRequestMapperTest {

    @Test
    void deberiaLanzarExcepcionCuandoFormatoFechaEsInvalido() {
        String fechaInvalida = "31-12-2000";
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> IUsuarioPropietarioRequestMapper.parseFecha(fechaInvalida)
        );

        assertTrue(exception.getMessage().contains("Formato de fecha inválido"));
    }
}
