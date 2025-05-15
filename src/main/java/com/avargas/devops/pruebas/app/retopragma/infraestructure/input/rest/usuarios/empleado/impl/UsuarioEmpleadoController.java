package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.empleado.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.empleados.IUsuarioEmpleadoHandler;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.empleado.IUsuarioEmpleadoController;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.shared.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/empleado")
@RequiredArgsConstructor
@Tag(name = "Empleado", description = "Aplicación que crea empleado por medio de un propietario")
public class UsuarioEmpleadoController implements IUsuarioEmpleadoController {

    private final IUsuarioEmpleadoHandler empleadoService;
  

    @Override
    @PostMapping("/crearEmpleado")
    @PreAuthorize("hasRole('PROP')")
    @Operation(summary = "Crear empleado", description = "Crea un nuevo empleado en el sistema con el rol de propietario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Empleado creado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
            , @ApiResponse(
            responseCode = "400",
            description = "Error al crear el empleado: Rol no encontrado",
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
    public ResponseEntity<?> crearEmpleado(@RequestBody
                                           @Parameter(description = "Datos de usuario empleado", required = true, content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class)))
                                               UsuarioRequestDTO usuarioEmpleadoDTO)
    {
        empleadoService.crearEmpleado(usuarioEmpleadoDTO);
        return new ResponseEntity<>(ResponseUtil.success("Empleado creado correctamente"),HttpStatus.CREATED);
    }

}