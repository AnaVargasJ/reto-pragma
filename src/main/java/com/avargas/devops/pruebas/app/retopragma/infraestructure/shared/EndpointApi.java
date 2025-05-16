package com.avargas.devops.pruebas.app.retopragma.infraestructure.shared;

public interface EndpointApi {

    String BASE_PATH_EMPLEADO = "/api/v1/empleado";
    String BASE_PATH_USUARIOS = "/api/v1/usuarios";

    String BASE_PATH_LOGIN = "/login";

    String CREATE_PROPIETARIO = "/crearPropietario";

    String FIND_BY_CORREO = "/buscarPorCorreo/{correo}";
    String CREATE_EMPLEADO = "/crearEmpleado";

    String CREATE_CLIENTE = "/crearCliente";
}
