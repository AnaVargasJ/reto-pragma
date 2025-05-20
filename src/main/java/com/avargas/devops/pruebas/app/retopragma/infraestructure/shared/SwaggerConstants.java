package com.avargas.devops.pruebas.app.retopragma.infraestructure.shared;

public final class SwaggerConstants {

    private SwaggerConstants() {}

    // === Tags ===
    public static final String TAG_EMPLEADO = "Empleado";
    public static final String TAG_EMPLEADO_DESC = "Aplicación que crea empleado por medio de un propietario.";

    public static final String TAG_USUARIO = "Usuario";
    public static final String TAG_USUARIO_DESC = "Operaciones relacionadas con la gestión de usuarios en el sistema.";

    public static final String TAG_PROPIETARIO = "Propietario";
    public static final String TAG_PROPIETARIO_DESC = "Aplicación que crea propietarios por medio de un administrador.";


    // === Operaciones - Propietario ===
    public static final String OP_CREAR_PROPIETARIO_SUMMARY = "Crear propietario";
    public static final String OP_CREAR_PROPIETARIO_DESC = "Crea un nuevo propietario en el sistema con el rol de administrador.";

    //===Operaciones empleado

    public static final String OP_CREAR_EMPLEADO_SUMMARY = "Crear empleado";
    public static final String OP_CREAR_EMPLEADO_DESC = "Crea un nuevo empleado en el sistema con el rol de propietario.";

    // === Operaciones - Login ===
    public static final String OP_LOGIN_SUMMARY = "Inicia sesión de un usuario";
    public static final String OP_LOGIN_DESC = "Este servicio permite a un usuario iniciar sesión con su información de usuario.";

    // === Respuestas específicas - Login ===
    public static final String RESPONSE_200_LOGIN_DESC = "El usuario ha iniciado sesión correctamente.";
    public static final String RESPONSE_401_LOGIN_DESC = "Credenciales inválidas.";


    // === Operaciones - Cliente ===
    public static final String OP_CREAR_CLIENTE_SUMMARY = "Crear cuenta cliente";
    public static final String OP_CREAR_CLIENTE_DESC = "Crea un nuevo cliente en el sistema.";

    // === Operaciones - Usuario ===
    public static final String OP_BUSCAR_POR_CORREO_SUMMARY = "Buscar usuario por correo";
    public static final String OP_BUSCAR_POR_ID_SUMMARY = "Buscar usuario por id";
    public static final String OP_BUSCAR_POR_CORREO_DESC = "Busca un usuario en el sistema usando su correo electrónico.";
    public static final String OP_BUSCAR_POR_ID_DESC = "Busca un usuario usando el id";
    public static final String OP_BUSCAR_POR_ID_PARAMETER = "Id del usuario a buscar";

    // === Descripciones de respuestas comunes ===
    public static final String RESPONSE_200_DESC = "Operación exitosa.";
    public static final String RESPONSE_201_DESC = "Recurso creado correctamente.";
    public static final String RESPONSE_400_DESC = "Petición inválida o error de validación.";
    public static final String RESPONSE_401_DESC = "Token inválido.";
    public static final String RESPONSE_403_DESC = "Acceso denegado: No tiene permisos para realizar esta operación.";
    public static final String RESPONSE_404_DESC = "Recurso no encontrado.";
    public static final String RESPONSE_409_DESC = "Conflicto: El recurso ya existe o está en uso.";
    public static final String RESPONSE_500_DESC = "Error interno del servidor.";
}