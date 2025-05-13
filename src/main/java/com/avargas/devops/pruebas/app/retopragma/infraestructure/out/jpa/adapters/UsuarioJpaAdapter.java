package com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.adapters;


import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IUsuarioPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Roles;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Usuarios;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;

    @Override
    public UsuarioModel saveUsuario(UsuarioModel userModel) {
        Usuarios usuarioEntity = usuarioEntityMapper.toEntity(userModel);
        Usuarios saved = usuarioRepository.save(usuarioEntity);
        return usuarioEntityMapper.toModel(saved);
    }

    @Override
    public UsuarioModel getUsuarioByCorreo(String correo) {
        Optional<Usuarios> usuariosEntity = usuarioRepository.buscarPorCorreo(correo);
        return usuariosEntity.map(usuarioEntityMapper::toModel).orElse(null);
    }

    @Override
    public RolModel findRolByRol(String nombre) {
        Optional<Roles> rolEntity = rolesRepository.findByNombre(nombre);
        return rolEntity.map(usuarioEntityMapper::toModel).orElse(null);

    }

    @Override
    public UsuarioModel getUsuarioByNumeroDocumento(String numeroDocumento) {
        Optional<Usuarios> usuariosEntity = usuarioRepository.existsByUsuarioDocumento(numeroDocumento);
        return usuariosEntity.map(usuarioEntityMapper::toModel).orElse(null);
    }
}
