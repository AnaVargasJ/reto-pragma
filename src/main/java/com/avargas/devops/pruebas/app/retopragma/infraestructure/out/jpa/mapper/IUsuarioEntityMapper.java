package com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.mapper;

import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Roles;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Usuarios;

public interface IUsuarioEntityMapper {

     UsuarioModel toModel(Usuarios usuarioEntity);
     Usuarios toEntity(UsuarioModel usuarioModel) ;
    RolModel toModel(Roles rolEntity);

    Roles toEntity(RolModel rolModel) ;
}
