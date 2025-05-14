package com.avargas.devops.pruebas.app.retopragma.application.mapper;

import com.avargas.devops.pruebas.app.retopragma.application.dto.response.RolDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.UsuarioDTOResponse;
import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioResponseMapper {


    default UsuarioDTOResponse toUsuarioDTO(UsuarioModel usuarioModel) {
        return UsuarioDTOResponse.builder()
                .idUsuario(usuarioModel.getId())
                .nombre(usuarioModel.getNombre())
                .numeroDocumento(usuarioModel.getNumeroDocumento())
                .celular(usuarioModel.getCelular())
                .correo(usuarioModel.getCorreo())
                .fechaNacimiento(formatearFecha(usuarioModel.getFechaNacimiento()))
                .rol(toRolDTO(usuarioModel.getRol()))
                .build();
    }


    default RolDTO toRolDTO(RolModel rolModel) {
        return RolDTO.builder()
                .idRol(rolModel.getId())
                .descripcion(rolModel.getDescripcion())
                .nombre(rolModel.getNombre())
                .build();
    }

    ;

    public static String formatearFecha(Date fecha) {
        if (fecha == null) return null;
        return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
    }
}
