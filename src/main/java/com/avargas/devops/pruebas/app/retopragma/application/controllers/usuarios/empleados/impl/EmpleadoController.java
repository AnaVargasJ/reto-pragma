package com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios.empleados.impl;

import com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios.empleados.IEmpleadoController;
import com.avargas.devops.pruebas.app.retopragma.application.controllers.usuarios.validation.ValidationService;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioEmpleadoDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioPropietarioDTO;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.empleados.IEmpleadoService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/empleado")
@RequiredArgsConstructor
@Tag(name = "Empleado", description = "Aplicación que crea empleado por medio de un propietario")
public class EmpleadoController implements IEmpleadoController {

   private final IEmpleadoService empleadoService;
   private final ValidationService validationService;

    @Override
    @PostMapping("/crearEmpleado")
    @PreAuthorize("hasRole('PROP')")
    @Operation(summary = "Crear empleado", description = "Crea un nuevo empleado en el sistema con el rol de propietario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Empleado creado correctamente",
                    content = @Content(schema = @Schema(implementation = Object.class))
            )
            , @ApiResponse(
            responseCode = "400",
            description = "Error al crear el empleado: Rol no encontrado",
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
    public ResponseEntity<?> crearEmpleado(@Valid @RequestBody
                                               @Parameter(description = "Datos de usuario empleado", required = true, content = @Content(schema = @Schema(implementation = UsuarioEmpleadoDTO.class)))
                                           UsuarioEmpleadoDTO usuarioEmpleadoDTO, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return validationService.validate(bindingResult);
        }
        return empleadoService.crearEmpleado(usuarioEmpleadoDTO);
    }

}
