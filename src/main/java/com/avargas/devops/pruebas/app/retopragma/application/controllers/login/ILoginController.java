package com.avargas.devops.pruebas.app.retopragma.application.controllers.login;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface ILoginController {

    ResponseEntity<?> login(LoginDTO loginDTO);

}
