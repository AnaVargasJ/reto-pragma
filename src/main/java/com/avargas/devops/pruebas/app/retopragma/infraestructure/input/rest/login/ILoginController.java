package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest.login;

import com.avargas.devops.pruebas.app.retopragma.application.dto.request.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface ILoginController {

    ResponseEntity<?> login(LoginDTO loginDTO);

}
