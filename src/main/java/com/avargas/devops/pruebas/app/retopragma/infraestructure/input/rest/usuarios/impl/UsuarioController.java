package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.impl;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.UsuarioRequestDTO;
import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.IUsuarioPropietarioHandler;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.clientes.IUsuarioClienteHandler;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.usuarios.IUsuarioController;
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
@RequestMapping(EndpointApi.BASE_PATH_USUARIOS)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.TAG_PROPIETARIO, description = SwaggerConstants.TAG_PROPIETARIO_DESC)
public class UsuarioController implements IUsuarioController {

    private final IUsuarioPropietarioHandler usuarioService;
    private final IUsuarioClienteHandler iUsuarioClienteHandler;


    @Override
    @PostMapping(EndpointApi.CREATE_PROPIETARIO )
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = SwaggerConstants.OP_CREAR_PROPIETARIO_SUMMARY,
            description = SwaggerConstants.OP_CREAR_PROPIETARIO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.CREATED, description = SwaggerConstants.RESPONSE_201_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.UNAUTHORIZED, description = SwaggerConstants.RESPONSE_401_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.FORBIDDEN, description = SwaggerConstants.RESPONSE_403_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> crearPropietario(@Valid @RequestBody
                                              @Parameter(description = "Datos de usuario", required = true, content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class)))
                                              UsuarioRequestDTO usuarioRequestDTO) {

        return usuarioService.crearPropietario(usuarioRequestDTO);

    }

    @Override
    @GetMapping(EndpointApi.FIND_BY_CORREO)
    @Operation(
            summary = SwaggerConstants.OP_BUSCAR_POR_CORREO_SUMMARY,
            description = SwaggerConstants.OP_BUSCAR_POR_CORREO_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.OK, description = SwaggerConstants.RESPONSE_200_DESC,
                    content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = "Error de conversión de entidad a DTO",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.NOT_FOUND, description = SwaggerConstants.RESPONSE_404_DESC,
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<?> buscarPorCorreo(@PathVariable("correo")
                                                 @Parameter(description = "Correo electrónico del usuario a buscar", required = true)
                                             String correo) {
        return usuarioService.buscarPorCorreo(correo);
    }

    @Override
    @PostMapping(EndpointApi.CREATE_CLIENTE)
    @Operation(
            summary = SwaggerConstants.OP_CREAR_CLIENTE_SUMMARY,
            description = SwaggerConstants.OP_CREAR_CLIENTE_DESC,
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponseCode.CREATED, description = "Cliente creado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = SwaggerResponseCode.BAD_REQUEST, description = SwaggerConstants.RESPONSE_400_DESC,
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> crearCliente(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
      iUsuarioClienteHandler.crearCliente(usuarioRequestDTO);
        return new ResponseEntity<>(ResponseUtil.success("Cliente creado correctamente"), HttpStatus.CREATED);
    }

}
