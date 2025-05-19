package com.avargas.devops.pruebas.app.retopragma.application.mapper;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;


public interface IUsuarioRequestMapper {
    UsuarioModel toUsuarioModel(UsuarioRequestDTO dto);
    UsuarioModel toLoginModel(LoginDTO loginDto);
}
