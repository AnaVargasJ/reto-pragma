package com.avargas.devops.pruebas.app.retopragma.infraestructure.security.auth;


import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.domain.model.UsuarioModel;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.exception.NoDataFoundException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.exception.TokenInvalidoException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.security.jwt.TokenJwtConfig;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.shared.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private static String defaultUsername = "";
    private static String defaultPassword = "";


    public static void actualizarValoresPredeterminados(String username, String password) {
        defaultUsername = username;
        defaultPassword = password;
    }

    public Authentication authenticateUser(UsuarioModel user) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getCorreo(), user.getClave());
        return authenticationManager.authenticate(authenticationToken);
    }

    public ResponseDTO generateTokenResponse(Authentication authResult) throws IOException {
        try {
            User user = (User) authResult.getPrincipal();
            String username = user.getUsername();
            Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

            Claims claims = Jwts.claims()
                    .add("authorities", new ObjectMapper().writeValueAsString(roles))
                    .add("username", username)
                    .build();

            String token = Jwts.builder()
                    .subject(username)
                    .claims(claims)
                    .expiration(new Date(System.currentTimeMillis() + 3600000)) // token válido por 1 hora
                    .issuedAt(new Date())
                    .signWith(TokenJwtConfig.SECRET_KEY)
                    .compact();

            return ResponseUtil.error(
                    "El usuario ha iniciado sesión correctamente",
                    Map.of("token", token),
                    HttpStatus.OK.value());
        } catch (JwtException e) {
        throw new TokenInvalidoException("Token inválido: " + e.getMessage());
    }


}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UsuarioModel user = new ObjectMapper().readValue(request.getInputStream(), UsuarioModel.class);
            return authenticateUser(user);
        } catch (IOException e) {
            throw new NoDataFoundException("Error al leer las credenciales del usuario", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        ResponseDTO respuesta = generateTokenResponse(authResult);
        Map<String, Object> jwtoken = (Map<String, Object>) respuesta.getRespuesta();
        String token = (String) jwtoken.get("token");

        response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, TokenJwtConfig.PREFIX_TOKEN + token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(respuesta));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {


        ResponseDTO respuesta = ResponseUtil.error(
                "Credenciales inválidas",
                Map.of("error", failed.getMessage()),
                HttpStatus.UNAUTHORIZED.value());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(respuesta));
    }

}