package com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.repositories;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.out.jpa.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByNombre(String nombre);
    Optional<Roles> findByDescripcion(String descripcion);
}