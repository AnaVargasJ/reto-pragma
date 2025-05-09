package com.avargas.devops.pruebas.app.retopragma;

import com.avargas.devops.pruebas.app.retopragma.application.repositories.RolesRepository;
import com.avargas.devops.pruebas.app.retopragma.application.repositories.UsuarioRepository;
import com.avargas.devops.pruebas.app.retopragma.application.services.usuarios.IUsuarioService;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.constants.Rol;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.domains.generic.UsuarioDTO;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.commons.exceptions.NoDataFoundException;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.converter.GenericConverter;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Roles;
import com.avargas.devops.pruebas.app.retopragma.model.entities.usuarios.Usuarios;
import org.junit.jupiter.api.*;
		import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RetoPragmaApplicationTests {

	@Autowired
	private  IUsuarioService usuarioService;
	@Autowired
	private  UsuarioRepository usuarioRepository;
	@Autowired
	private  RolesRepository rolesRepository;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private  GenericConverter genericConverter;


	/*@BeforeEach
	void setUp() {
		usuarioRepository.deleteAll();
		rolesRepository.deleteAll();

		Roles rol = new Roles();
		rol.setDescripcion("Propietario");
		rolesRepository.save(rol);
	}*/

	@Test
	@Order(1)
	void crearPropietario_exitosamente() {
		Roles rol = Roles.builder()
				.descripcion("Propietario")
				.nombre("PROP")
				.build();
		rolesRepository.save(rol);
		UsuarioDTO dto = UsuarioDTO.builder()
				.nombre("Juan")
				.apellido("Pérez")
				.celular("+573001112233")
				.numeroDocumento("8328239")
				.fechaNacimiento("01/01/1990")
				.clave("clave123")
				.correo("nuevo@test.com")
				.build();


		ResponseEntity<?> response = usuarioService.crearPropietario(dto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Propietario creado correctamente", response.getBody());
	}


	@Test
	@Order(2)
	void crearPropietario_fallaExistente() {
		Roles rol = Roles.builder()
				.descripcion("Propietario")
				.nombre("PROP")
				.build();
		rolesRepository.save(rol);
		Usuarios  user =
				Usuarios.builder()
						.nombre("Juan")
						.apellido("Pérez")
						.celular("+573001112233")
						.numeroDocumento("8328239")
						.fechaNacimiento(new Date())
						.clave("clave123")
						.correo("nuevo@test.com")
						.rol(rol)
						.build();
		usuarioRepository.save(user);
		UsuarioDTO dto = UsuarioDTO.builder()
				.nombre("Juan")
				.apellido("Pérez")
				.celular("+573001112233")
				.numeroDocumento("8328239")
				.fechaNacimiento("01/01/1990")
				.clave("clave123")
				.correo("nuevo@test.com")
				.build();


		ResponseEntity<?> response = usuarioService.crearPropietario(dto);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Error al crear el propietario", response.getBody());
	}

	@Test
	@Order(3)
	void crearPropietario_fallaNumeroDocumentoExistente() {
		Roles rol = Roles.builder()
				.descripcion("Propietario")
				.nombre("PROP")
				.build();
		rolesRepository.save(rol);
		Usuarios  user =
				Usuarios.builder()
						.nombre("Juan")
						.apellido("Pérez")
						.celular("+573001112233")
						.numeroDocumento("8328239")
						.fechaNacimiento(new Date())
						.clave("clave123")
						.correo("nuevo@test.com")
						.rol(rol)
						.build();
		usuarioRepository.save(user);
		UsuarioDTO dto = UsuarioDTO.builder()
				.nombre("Juan")
				.apellido("Pérez")
				.celular("+573001112233")
				.numeroDocumento("8328239")
				.fechaNacimiento("01/01/1990")
				.clave("clave123")
				.correo("nuevo@test.com")
				.build();


		ResponseEntity<?> response = usuarioService.crearPropietario(dto);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Error al crear el propietario", response.getBody());
	}

}
