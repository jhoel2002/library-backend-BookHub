package com.library.project.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.project.web.models.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByCorreo(String correo);
	
	@Query("SELECT u FROM Usuario u " +
			"JOIN u.rol r " +
		       "WHERE CONCAT(u.id, ' ', u.nombre, ' ', u.username, ' ', u.apellidoPaterno, ' ', u.apellidoMaterno, ' ', u.correo, ' ', u.numeroDocumento, ' ', r.nombre) " +
		       "LIKE %?1%")
	public List<Usuario> findAll(String palabraClave);
}
