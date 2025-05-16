package com.avargas.devops.pruebas.app.retopragma.domain.usecase;

import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.propietarios.IUsuarioServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IPasswordPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IUsuarioPersistencePort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UsuarioUseCase implements IUsuarioServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;

    private final UsuarioValidationCase usuarioValidationCase;

    private final IPasswordPersistencePort iPasswordPersistencePort;

    @Override
    public void createUser(UsuarioModel usuarioModel) {
        usuarioValidationCase.validateUserFields(usuarioModel);

        if (usuarioPersistencePort.getUsuarioByCorreo(usuarioModel.getCorreo()) != null) {
            throw new UsuariosDomainException("El correo ya está registrado");
        }
        if (usuarioPersistencePort.getUsuarioByNumeroDocumento(usuarioModel.getNumeroDocumento()) != null) {
            throw new UsuariosDomainException("El documento ya está registrado");
        }
        RolModel rolModel = usuarioPersistencePort.findRolByRol("PROP");
        if (rolModel == null) {
            throw new UsuariosDomainException("El rol no existe");
        }
        usuarioModel.setClave(iPasswordPersistencePort.encriptarClave(usuarioModel.getClave()));
        usuarioModel.setRol(rolModel);
        usuarioPersistencePort.saveUsuario(usuarioModel);
    }

    @Override
    public UsuarioModel login(String correo, String clave) {
        UsuarioModel usuario = usuarioPersistencePort.getUsuarioByCorreo(correo);
        if (usuario == null) {
            throw new UsuariosDomainException("Usuario no encontrado");
        }
        iPasswordPersistencePort.esClaveValida(correo, usuario.getClave());
        usuarioValidationCase.validaLoginFiels(correo, clave);
        return usuario;
    }

    @Override
    public UsuarioModel getUsuarioByCorreo(String correo) {
        UsuarioModel usuario = usuarioPersistencePort.getUsuarioByCorreo(correo);
        if (usuario == null) {
            throw new UsuariosDomainException("Usuario no encontrado");
        }
        return usuario;
    }


}
