package com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.constants;

public enum Rol {
    ADMIN("Administrador"),
    PROP("Propietario"),
    EMP("Empleado"),
    CLI("Cliente");

    private final String descripcion;


    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static boolean isValid(String code) {

        for (Rol type : Rol.values()) {
            if (type.name().equals(code)) {
                return true;
            }
        }
        return false;

    }


}
