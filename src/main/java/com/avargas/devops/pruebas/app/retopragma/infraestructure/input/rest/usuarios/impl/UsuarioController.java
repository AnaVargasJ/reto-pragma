package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.IUsuarioPropietarioHandler;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.clientes.IUsuarioClienteHandler;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.IUsuarioController;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.shared.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Propietario", description = "Aplicación que crea propietarios por medio de un administrador")
public class UsuarioController implements IUsuarioController {

    private final IUsuarioPropietarioHandler usuarioService;
    private final IUsuarioClienteHandler iUsuarioClienteHandler;


    @Override
    @PostMapping("/crearPropietario")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear propietario", description = "Crea un nuevo propietario en el sistema con el rol de administrador")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Propietario creado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
            , @ApiResponse(
            responseCode = "400",
            description = "Error al crear el propietario: Rol no encontrado",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))
    ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token invalido",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado: No tiene permisos para realizar esta operación",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
    public ResponseEntity<?> crearPropietario(@Valid @RequestBody
                                              @Parameter(description = "Datos de usuario", required = true, content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class)))
                                              UsuarioRequestDTO usuarioRequestDTO) {

        return usuarioService.crearPropietario(usuarioRequestDTO);

    }

    @Override
    @GetMapping("/buscarPorCorreo/{correo}")
    @Operation(
            summary = "Buscar usuario por correo",
            description = "Busca un usuario en el sistema usando su correo electrónico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado correctamente",
                    content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de conversión de entidad a DTO",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class)) 
            )
    })
    public ResponseEntity<?> buscarPorCorreo(@PathVariable("correo")
                                                 @Parameter(description = "Correo electrónico del usuario a buscar", required = true)
                                             String correo) {
        return usuarioService.buscarPorCorreo(correo);
    }

    @Override
    @PostMapping("/crearCliente")
    @Operation(summary = "Crear cuenta cliente", security = @SecurityRequirement(name = ""), description = "Crea un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
            , @ApiResponse(
            responseCode = "400",
            description = "Error al crear el cliente: Rol no encontrado",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))
    ),
    })
    public ResponseEntity<?> crearCliente(UsuarioRequestDTO usuarioRequestDTO) {
      iUsuarioClienteHandler.crearCliente(usuarioRequestDTO);
        return new ResponseEntity<>(ResponseUtil.success("Cliente creado correctamente"), HttpStatus.CREATED);
    }

}
