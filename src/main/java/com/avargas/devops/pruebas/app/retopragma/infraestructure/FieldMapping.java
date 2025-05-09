package com.avargas.devops.pruebas.app.retopragma.infraestructure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // La anotación debe ser accesible en tiempo de ejecución
@Target(ElementType.FIELD) // Solo se aplica a campos
public @interface FieldMapping {
    String value(); // Nombre del campo en la entidad

}
