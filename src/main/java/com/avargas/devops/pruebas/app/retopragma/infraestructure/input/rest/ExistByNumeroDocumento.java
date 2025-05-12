package com.avargas.devops.pruebas.app.retopragma.infraestructure.input.rest;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ExistsByNumeroDocumentoValidation.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistByNumeroDocumento {

    String message() default "El numero de documento ya existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
