package com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.propietarios;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;

public interface IUsuarioServicePort {
    void createUser(UsuarioModel usuarioModel);
    UsuarioModel login(String correo, String clave);

    UsuarioModel getUsuarioByCorreo(String correo);
    UsuarioModel buscarPorIdUsuario(Long idUsuario);


}
