package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.login.ipml;


import com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.login.ILoginController;
import com.avargas.devops.pruebas.app.retopragma.application.handler.usuarios.IUsuarioPropietarioHandler;
import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LoginController implements ILoginController {


    private final IUsuarioPropietarioHandler usuarioService;

    @Override
    @PostMapping("/login")
    @Operation(
            summary = "Inicia sesión de un usuario",
            security = @SecurityRequirement(name = ""),
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
}