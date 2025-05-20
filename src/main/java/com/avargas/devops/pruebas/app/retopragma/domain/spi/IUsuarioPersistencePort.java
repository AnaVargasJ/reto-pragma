package com.avargas.devops.pruebas.app.retopragma.domain.spi;


import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;

public interface IUsuarioPersistencePort {
    UsuarioModel saveUsuario(UsuarioModel userModel);
    UsuarioModel getUsuarioByCorreo(String correo);
    UsuarioModel buscarPorIdUsuario(Long idUsuario);
    RolModel findRolByRol(String nombre);

    UsuarioModel getUsuarioByNumeroDocumento(String numeroDocumento);
}
