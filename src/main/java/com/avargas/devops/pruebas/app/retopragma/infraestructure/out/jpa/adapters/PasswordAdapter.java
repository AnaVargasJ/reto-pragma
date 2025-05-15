package com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.adapters;

import com.avargas.devops.pruebas.app.retopragma.domain.spi.IPasswordPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.exception.CredencialesInvalidasException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PasswordAdapter implements IPasswordPersistencePort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encriptarClave(String clave) {
        return passwordEncoder.encode(clave);
    }
     @Override 
    public Boolean esClaveValida(String claveSinEncriptar, String claveEncriptada) {
         if (!BCrypt.checkpw(claveSinEncriptar, claveEncriptada)) {
             throw new CredencialesInvalidasException("Clave incorrecta");
         }
        return true;
    }
}
