package com.avargas.devops.pruebas.app.retopragma.application.mapper;

import com.avargas.devops.pruebas.app.retopragma.application.dto.response.RolDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.UsuarioDTOResponse;
import com.avargas.devops.pruebas.app.retopragma.domain.model.RolModel;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public interface IUsuarioResponseMapper {


     UsuarioDTOResponse toUsuarioDTO(UsuarioModel usuarioModel) ;


    RolDTO toRolDTO(RolModel rolModel);


}
