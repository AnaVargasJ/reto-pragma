package com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios.impl;

import com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios.IUsuarioController;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.IUsuarioService;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.LoginDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Propietario", description = "Aplicación que crea propietarios por medio de un administrador")
public class UsuarioController implements IUsuarioController {

    private final IUsuarioService usuarioService;

    @Override
    @PostMapping("/crearPropietario")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear propietario", description = "Crea un nuevo propietario en el sistema con el rol de administrador")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Propietario creado correctamente",
                    content = @Content(schema = @Schema(implementation = Object.class))
            )
            , @ApiResponse(
            responseCode = "400",
            description = "Error al crear el propietario: Rol no encontrado",
            content = @Content(schema = @Schema(implementation = Object.class))
    ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token invalido",
                    content = @Content(schema = @Schema(implementation = Object.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado: No tiene permisos para realizar esta operación",
                    content = @Content(schema = @Schema(implementation = Object.class))
            )
    })
    public ResponseEntity<?> crearPropietario(@Valid @RequestBody
                                              @Parameter(description = "Datos de usuario", required = true, content = @Content(schema = @Schema(implementation = UsuarioDTO.class)))
                                              UsuarioDTO usuarioDTO, BindingResult bindingResult) {
        return usuarioService.crearPropietario(usuarioDTO, bindingResult);
    }

    @Override
    @PostMapping("/login")
    @Operation(
            summary = "Inicia sesión de un usuario",
            description = "Este servicio permite a un usuario iniciar sesión con su información de usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El usuario ha iniciado sesión correctamente",
                    content = @Content(schema = @Schema(implementation = Object.class))
            )
            ,
            @ApiResponse(
                    responseCode = "401",
                    description = "credenciales invalidas",
                    content = @Content(schema = @Schema(implementation = Object.class))
            )
    })
    public ResponseEntity<?> login(@RequestBody
                                   @Parameter(description = "Datos de usuario", required = true, content = @Content(schema = @Schema(implementation = LoginDTO.class)))
                                   LoginDTO loginDTO) {
        return usuarioService.login(loginDTO);
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
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de conversión de entidad a DTO",
                    content = @Content(schema = @Schema(implementation = Map.class)) // Map<String, Object>
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class)) // Map<String, Object>
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> buscarPorCorreo(@PathVariable
                                             @Parameter(description = "Correo electrónico del usuario a buscar", required = true)
                                             String correo) {
        return usuarioService.buscarPorCorreo(correo);
    }


}
