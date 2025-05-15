package com.avargas.devops.pruebas.app.retopragma.domain.spi;

public interface IPasswordPersistencePort {

   String encriptarClave(String clave);

   Boolean esClaveValida(String claveSinEncriptar, String claveEncriptada);

}
