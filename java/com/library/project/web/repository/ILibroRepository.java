package com.library.project.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.project.web.models.Libro;

public interface ILibroRepository extends JpaRepository<Libro, Long>{

	@Query("SELECT l FROM Libro l " +
		       "JOIN l.autor a " +
		       "JOIN l.pasillo p " +
		       "WHERE CONCAT(l.id, ' ', l.fechaPublic, ' ', l.titulo, ' ', a.nombre, ' ', a.apellidoPaterno, ' ', a.apellidoMaterno, ' ', p.nombre) " +
		       "LIKE %?1%")
	public List<Libro> findAll(String palabraClave);
}
