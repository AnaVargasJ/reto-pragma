package com.avargas.devops.pruebas.app.retopragma.application.mapper.impl;


import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.mapper.IUsuarioRequestMapper;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UsuarioRequestMapper implements IUsuarioRequestMapper {
    @Override
    public UsuarioModel toUsuarioModel(UsuarioRequestDTO dto) {
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

    @Override
    public UsuarioModel toLoginModel(LoginDTO loginDto) {
        return UsuarioModel.builder()
                .correo(loginDto.getCorreo())
                .clave(loginDto.getClave())
                .build();
    }

    private Date parseFecha(String fecha) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException("Formato de fecha inválido. Use dd/MM/yyyy", e);
        }
    }
}
