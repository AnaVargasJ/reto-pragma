package com.avargas.devops.pruebas.app.retopragma.infraestructure.security.jpa;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Usuarios;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuarios> userOptional = usuarioRepository.buscarPorCorreo(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }

        Usuarios usuario = userOptional.get();

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre())
        );


        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getClave(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
