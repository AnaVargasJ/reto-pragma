package com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories;

import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios,Long> {

    /*@Query("SELECT u FROM Usuarios u WHERE u.correo = ?1")
    Boolean existsByCorreo(String correo);

    @Query("SELECT u FROM Usuarios u WHERE u.numeroDocumento = ?1")
    Boolean existsByNumeroDocumento(String numeroDocumento);*/

    Boolean existsByCorreo(String correo);
    Boolean existsByNumeroDocumento(String numeroDocumento);

    @Query("SELECT u FROM Usuarios u WHERE u.numeroDocumento = ?1")
    Optional<Usuarios> existsByUsuarioDocumento(String numeroDocumento);

    @Query("SELECT u FROM Usuarios u JOIN FETCH u.rol WHERE u.correo = :correo")
    Optional<Usuarios> buscarPorCorreo(@Param("correo") String correo);

}
