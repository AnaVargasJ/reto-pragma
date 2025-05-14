package com.avargas.devops.pruebas.app.retopragma.application.mapper;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioRequestMapper {
    default UsuarioModel toUsuarioModel(UsuarioPropietarioRequestDTO dto) {
        return UsuarioModel.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .numeroDocumento(dto.getNumeroDocumento())
                .celular(dto.getCelular())
                .correo(dto.getCorreo())
                .clave(dto.getClave())
                .fechaNacimiento(parseFecha(dto.getFechaNacimiento()))
                .build();
    }
    default UsuarioModel toLoginModel(LoginDTO loginDto) {
        return UsuarioModel.builder()
                .correo(loginDto.getCorreo())
                .clave(loginDto.getClave())
                .build();
    }

    public static Date parseFecha(String fecha) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException("Formato de fecha inválido. Use dd/MM/yyyy", e);
        }
    }
}
