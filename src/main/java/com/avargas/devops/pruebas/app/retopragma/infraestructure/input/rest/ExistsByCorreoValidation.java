package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByCorreoValidation implements ConstraintValidator<ExistByCorreo,String> {

    private final UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(String correo, ConstraintValidatorContext constraintValidatorContext) {
        return !usuarioRepository.existsByCorreo(correo);
    }

}
