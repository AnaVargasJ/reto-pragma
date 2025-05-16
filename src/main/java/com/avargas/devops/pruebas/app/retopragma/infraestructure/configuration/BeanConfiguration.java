package com.avargas.devops.pruebas.app.retopragma.infraestructure.configuration;

import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.clientes.IUsuarioClienteServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IPasswordPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.empleados.IUsuarioEmpleadoServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.api.usuarios.propietarios.IUsuarioServicePort;
import com.avargas.devops.pruebas.app.retopragma.domain.spi.IUsuarioPersistencePort;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioUseCase;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.UsuarioValidationCase;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.clientes.UsuarioClienteUseCase;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.adapters.PasswordAdapter;
import com.avargas.devops.pruebas.app.retopragma.domain.usecase.empleado.UsuarioEmpleadoUseCase;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.adapters.UsuarioJpaAdapter;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public IUsuarioPersistencePort usuarioPersistencePort() {
        return new UsuarioJpaAdapter(usuarioRepository, rolesRepository, usuarioEntityMapper);
    }


    @Bean
    public IUsuarioServicePort usuarioServicePort(){
        return new UsuarioUseCase(usuarioPersistencePort(),usuarioValidationCase(),iPasswordPersistencePort());
    }
    @Bean
    public UsuarioValidationCase usuarioValidationCase() {
        return new UsuarioValidationCase();
    }

    @Bean
    public IPasswordPersistencePort iPasswordPersistencePort() {
        return new PasswordAdapter(passwordEncoder);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization");
            }
        };
    }


    @Bean
    public IUsuarioEmpleadoServicePort iUsuarioEmpleadoServicePort(){
        return new UsuarioEmpleadoUseCase(usuarioPersistencePort(),usuarioValidationCase(),iPasswordPersistencePort());
    }

    @Bean
    public IUsuarioClienteServicePort iUsuarioClienteServicePort(){
        return new UsuarioClienteUseCase(usuarioPersistencePort(),usuarioValidationCase(),iPasswordPersistencePort());
    }
}
