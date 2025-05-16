package com.avargas.devops.pruebas.app.retopragma.domain.usecase.clientes;

import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.clientes.IUsuarioClienteServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IPasswordPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IUsuarioPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioValidationCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsuarioClienteUseCase implements IUsuarioClienteServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;

    private final UsuarioValidationCase usuarioValidationCase;

    private final IPasswordPersistencePort iPasswordPersistencePort;

    @Override
    public void crearCliente(UsuarioModel usuarioModel) {
        usuarioValidationCase.validateUserFields(usuarioModel);

        if (usuarioPersistencePort.getUsuarioByCorreo(usuarioModel.getCorreo()) != null) {
            throw new UsuariosDomainException("El correo ya está registrado");
        }
        if (usuarioPersistencePort.getUsuarioByNumeroDocumento(usuarioModel.getNumeroDocumento()) != null) {
            throw new UsuariosDomainException("El documento ya está registrado");
        }
        RolModel rolModel = usuarioPersistencePort.findRolByRol("CLI");
        if (rolModel == null) {
            throw new UsuariosDomainException("El rol no existe");
        }
        usuarioModel.setClave(iPasswordPersistencePort.encriptarClave(usuarioModel.getClave()));
        usuarioModel.setRol(rolModel);
        usuarioPersistencePort.saveUsuario(usuarioModel);
    }
}
