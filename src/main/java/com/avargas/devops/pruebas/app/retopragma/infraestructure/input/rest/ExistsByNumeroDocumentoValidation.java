package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByNumeroDocumentoValidation implements ConstraintValidator<ExistByNumeroDocumento,String> {

    private final UsuarioRepository usuarioRepository;
    @Override
    public boolean isValid(String numeroDocumento, ConstraintValidatorContext constraintValidatorContext) {
            return !usuarioRepository.existsByNumeroDocumento(numeroDocumento);
    }

}
