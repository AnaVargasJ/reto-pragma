package com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.empleados.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.empleados.IUsuarioEmpleadoHandler;
import com.avargas.devops.pruebas.app.retopragma.application.mapper.IUsuarioRequestMapper;
import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.empleados.IUsuarioEmpleadoServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioEmpleadoHandler implements IUsuarioEmpleadoHandler {

    private final IUsuarioEmpleadoServicePort iUsuarioEmpleadoServicePort;
    private final IUsuarioRequestMapper iUsuarioPropietarioRequestMapper;


    @Override
    public void crearEmpleado(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioModel usuarioModel = iUsuarioPropietarioRequestMapper.toUsuarioModel(usuarioRequestDTO);
        iUsuarioEmpleadoServicePort.createUserEmpleado(usuarioModel);
    }
}