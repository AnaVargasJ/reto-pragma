package com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.mapper;

import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Roles;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Usuarios;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUsuarioEntityMapper {

    default UsuarioModel toModel(Usuarios usuarioEntity) {
        if (usuarioEntity == null) return null;

        return UsuarioModel.builder()
                .id(usuarioEntity.getId())
                .nombre(usuarioEntity.getNombre())
                .apellido(usuarioEntity.getApellido())
                .numeroDocumento(usuarioEntity.getNumeroDocumento())
                .celular(usuarioEntity.getCelular())
                .fechaNacimiento(usuarioEntity.getFechaNacimiento())
                .correo(usuarioEntity.getCorreo())
                .clave(usuarioEntity.getClave())
                .rol(toModel(usuarioEntity.getRol()))
                .build();
    }

    default Usuarios toEntity(UsuarioModel usuarioModel) {
        if (usuarioModel == null) return null;

        Usuarios entity = new Usuarios();
        entity.setId(usuarioModel.getId());
        entity.setNombre(usuarioModel.getNombre());
        entity.setApellido(usuarioModel.getApellido());
        entity.setNumeroDocumento(usuarioModel.getNumeroDocumento());
        entity.setCelular(usuarioModel.getCelular());
        entity.setFechaNacimiento(usuarioModel.getFechaNacimiento());
        entity.setCorreo(usuarioModel.getCorreo());
        entity.setClave(usuarioModel.getClave());
        entity.setRol(toEntity(usuarioModel.getRol()));
        return entity;
    }

    default RolModel toModel(Roles rolEntity) {
        if (rolEntity == null) return null;

        return RolModel.builder()
                .id(rolEntity.getId())
                .nombre(rolEntity.getNombre())
                .descripcion(rolEntity.getDescripcion())
                .build();
    }

    default Roles toEntity(RolModel rolModel) {
        if (rolModel == null) return null;

        Roles entity = new Roles();
        entity.setId(rolModel.getId());
        entity.setNombre(rolModel.getNombre());
        entity.setDescripcion(rolModel.getDescripcion());
        return entity;
    }
}
