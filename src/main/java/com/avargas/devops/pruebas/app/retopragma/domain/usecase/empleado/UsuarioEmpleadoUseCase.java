package com.avargas.devops.pruebas.app.retopragma.domain.usecase.empleado;

import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.empleados.IUsuarioEmpleadoServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.exception.UsuariosDomainException;
import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IPasswordPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IUsuarioPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioValidationCase;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UsuarioEmpleadoUseCase implements IUsuarioEmpleadoServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final UsuarioValidationCase usuarioValidationCase;
    private final IPasswordPersistencePort iPasswordPersistencePort;

    @Override
    public void createUserEmpleado(UsuarioModel usuarioModel) {
        usuarioValidationCase.validateUserFields(usuarioModel);

        if (usuarioPersistencePort.getUsuarioByCorreo(usuarioModel.getCorreo()) != null) {
            throw new UsuariosDomainException("El correo ya está registrado");
        }
        if (usuarioPersistencePort.getUsuarioByNumeroDocumento(usuarioModel.getNumeroDocumento()) != null) {
            throw new UsuariosDomainException("El documento ya está registrado");
        }
        RolModel rolModel = usuarioPersistencePort.findRolByRol("EMP");
        if (rolModel == null) {
            throw new UsuariosDomainException("El rol no existe");
        }
        usuarioModel.setRol(rolModel);
        usuarioModel.setClave(iPasswordPersistencePort.encriptarClave(usuarioModel.getClave()));
        usuarioPersistencePort.saveUsuario(usuarioModel);
    }

}
