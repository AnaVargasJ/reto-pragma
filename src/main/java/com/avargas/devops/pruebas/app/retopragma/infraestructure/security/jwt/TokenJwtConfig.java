package com.avargas.devops.pruebas.app.retopragma.infraestructure.security.jwt;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;

public interface TokenJwtConfig {

    SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    String PREFIX_TOKEN = "Bearer ";
    String HEADER_AUTHORIZATION = "Authorization";

    String CONTENT_TYPE = "application/json";
}
