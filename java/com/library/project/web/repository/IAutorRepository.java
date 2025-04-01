package com.library.project.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.project.web.models.Autor;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNombre(String nombre);
    
    boolean existsByApellidoPaterno(String apellidoPaterno);
    
    boolean existsByApellidoMaterno(String apellidoMaterno);
    
    @Query("SELECT a FROM Autor a " +
		       "WHERE CONCAT(a.id, ' ', a.nombre, ' ', a.apellidoPaterno, ' ', a.apellidoMaterno) " +
		       "LIKE %?1%")
	public List<Autor> findAll(String palabraClave);
}
