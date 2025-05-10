package com.avargas.devops.pruebas.app.retopragma.infraestructure.security.auth;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.exceptions.NoDataFoundException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.security.jwt.TokenJwtConfig;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Usuarios;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
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

    /*metodo para autenticar el usuario*/
    public Authentication authenticateUser(Usuarios user) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getCorreo(), user.getClave());
        return authenticationManager.authenticate(authenticationToken);
    }

    public Map<String, Object> generateTokenResponse(Authentication authResult) throws IOException {
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

            Map<String, Object> jwtoken = new HashMap<>();
            jwtoken.put("token", token);
            jwtoken.put("mensaje", "El usuario ha iniciado sesión correctamente");
            jwtoken.put("codigo", HttpStatus.OK.value());
            return jwtoken;

    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Usuarios user = new ObjectMapper().readValue(request.getInputStream(), Usuarios.class);
            return authenticateUser(user);
        } catch (IOException e) {
            throw new NoDataFoundException("Error al leer las credenciales del usuario", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        Map<String, Object> jwtoken = generateTokenResponse(authResult);
        String token = (String) jwtoken.get("token");


        response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, TokenJwtConfig.PREFIX_TOKEN + token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(jwtoken));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(200);
    }


}
