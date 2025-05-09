package com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.validation;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.ExistByUsername;
import com.avargas.devops.pruebas.app.retopragma.application.repositories.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByUsernameValidation implements ConstraintValidator<ExistByUsername,String> {

    private final UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(String correo, ConstraintValidatorContext constraintValidatorContext) {
        return !usuarioRepository.existsByUsername(correo);
    }


}
