package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.application.dto.response.ResponseDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.security.auth.JwtAuthenticationFilter;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.security.jwt.TokenJwtConfig;
import com.avargas.devops.pruebas.app.retopragma.util.TestUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import java.io.*;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private AuthenticationManager authenticationManager;


    @Mock
    private FilterChain chain;

    @Mock
    private Authentication authentication;


    @Mock
    private HttpServletRequest request;


    @Mock
    private HttpServletResponse response;

    private static final String USERNAME = "validUsername";
    private static final String PASSWORD = "validPassword";

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
    }

    @Test
    void testGenerateTokenResponse() throws IOException {
        User mockUser = new User(USERNAME, PASSWORD, Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseDTO responseDTO = jwtAuthenticationFilter.generateTokenResponse(authentication);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getRespuesta());

        String token = ((Map<String, String>) responseDTO.getRespuesta()).get("token");

        assertNotNull(token);
        assertEquals("El usuario ha iniciado sesión correctamente", responseDTO.getMensaje());
        assertEquals(HttpStatus.OK.value(), responseDTO.getCodigo());
    }

    @Test
    void testAttemptAuthentication_ValidRequest() throws IOException {

        String userJson = "{\"correo\":\"validUsername\",\"clave\":\"validPassword\"}";

        InputStream inputStream = new ByteArrayInputStream(userJson.getBytes());

        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };

        when(request.getInputStream()).thenAnswer(invocation -> servletInputStream);
        JwtAuthenticationFilter.actualizarValoresPredeterminados("validUsername", "validPassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        Authentication result = jwtAuthenticationFilter.attemptAuthentication(request, response);

        assertNotNull(result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testAttemptAuthentication_InvalidRequest() throws IOException {
        when(request.getInputStream()).thenThrow(new IOException("Simulated IO Exception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jwtAuthenticationFilter.attemptAuthentication(request, response);
        });

        assertEquals("Error al leer las credenciales del usuario", exception.getMessage());
    }



    @Test
    void testSuccessfulAuthentication() throws Exception {
        User mockUser = new User(USERNAME, PASSWORD, Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(mockUser);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        TestUtil.invokePrivateMethod(
                jwtAuthenticationFilter,
                "successfulAuthentication",
                void.class,
                new Class[]{HttpServletRequest.class, HttpServletResponse.class, FilterChain.class, Authentication.class},
                request, response, chain, authentication
        );

        ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
        verify(response).addHeader(eq(TokenJwtConfig.HEADER_AUTHORIZATION), headerCaptor.capture());
        String authHeader = headerCaptor.getValue();
        assertTrue(authHeader.startsWith(TokenJwtConfig.PREFIX_TOKEN), "El encabezado Authorization debe comenzar con 'Bearer '");

        String responseBody = stringWriter.toString();
        assertNotNull(responseBody);

    }

    @Test
    void testUnsuccessfulAuthentication() throws Exception {
        StringWriter responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        AuthenticationException exception = new BadCredentialsException("Credenciales inválidas");
        when(response.getWriter()).thenReturn(printWriter);
        TestUtil.invokePrivateMethod(
                jwtAuthenticationFilter,
                "unsuccessfulAuthentication",
                void.class,
                new Class[]{HttpServletRequest.class, HttpServletResponse.class, AuthenticationException.class},
                request, response, exception
        );


        printWriter.flush();

        verify(response).setStatus(HttpStatus.UNAUTHORIZED.value());
        verify(response).setContentType(TokenJwtConfig.CONTENT_TYPE);

        String body = responseWriter.toString();
        assertTrue(body.contains("Credenciales inválidas"));
        assertTrue(body.contains("error"));
        assertTrue(body.contains("Credenciales inválidas"));
    }








}