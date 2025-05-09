package com.avargas.devops.pruebas.app.retopragma.infraestructure;


import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.validation.ExistsByUsernameValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ExistsByUsernameValidation.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistByUsername {

    String message() default "El usuario, ya existe ne la base de datos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
