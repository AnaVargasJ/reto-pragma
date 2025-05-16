package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.empleado.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.empleados.IUsuarioEmpleadoHandler;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.empleado.IUsuarioEmpleadoController;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.shared.EndpointApi;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.shared.ResponseUtil;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.shared.SwaggerConstants;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.shared.SwaggerResponseCode;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointApi.BASE_PATH_EMPLEADO)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_EMPLEADO, description = SwaggerConstants.TAG_EMPLEADO_DESC)
public class UsuarioEmpleadoController implements IUsuarioEmpleadoController {

    private final IUsuarioEmpleadoHandler empleadoService;

    @Override
    @PostMapping(EndpointApi.CREATE_EMPLEADO)
    @Operation(summary = SwaggerConstants.OP_CREAR_EMPLEADO_SUMMARY,
            description = SwaggerConstants.OP_CREAR_EMPLEADO_DESC)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = SwaggerResponseCode.CREATED,
                    description = SwaggerConstants.RESPONSE_201_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = SwaggerResponseCode.BAD_REQUEST,
                    description = SwaggerConstants.RESPONSE_400_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = SwaggerResponseCode.UNAUTHORIZED,
                    description = SwaggerConstants.RESPONSE_401_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = SwaggerResponseCode.FORBIDDEN,
                    description = SwaggerConstants.RESPONSE_403_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
    public ResponseEntity<?> crearEmpleado(@RequestBody
                                           @Parameter(description = "Datos de usuario empleado", required = true,
                                                   content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class)))
                                           UsuarioRequestDTO usuarioEmpleadoDTO) {

        empleadoService.crearEmpleado(usuarioEmpleadoDTO);
        return new ResponseEntity<>(ResponseUtil.success("Empleado creado correctamente"), HttpStatus.CREATED);
    }
}