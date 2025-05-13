package com.avargas.devops.pruebas.app.retopragma.infraestructure.security;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.security.jwt.TokenJwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String path = request.getRequestURI();

        if (path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars") ||
                path.startsWith("/configuration")) {
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write("");
            return;
        }

        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", "No autorizado: token inválido o no enviado");
        error.put("codigo", HttpStatus.FORBIDDEN.value());
        error.put("error", authException.getMessage());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(TokenJwtConfig.CONTENT_TYPE); // application/json
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}
