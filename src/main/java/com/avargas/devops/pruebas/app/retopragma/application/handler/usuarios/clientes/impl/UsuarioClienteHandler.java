package com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.clientes.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.clientes.IUsuarioClienteHandler;
import com.avargas.devops.pruebas.app.retopragma.application.mapper.IUsuarioRequestMapper;
import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.clientes.IUsuarioClienteServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioClienteHandler implements IUsuarioClienteHandler {

    private final IUsuarioClienteServicePort iUsuarioClienteServicePort;
    private final IUsuarioRequestMapper iUsuarioPropietarioRequestMapper;

    @Override
    public void crearCliente(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioModel usuarioModel = iUsuarioPropietarioRequestMapper.toUsuarioModel(usuarioRequestDTO);

        iUsuarioClienteServicePort.crearCliente(usuarioModel);
    }
}