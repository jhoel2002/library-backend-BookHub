package com.library.project.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.project.web.models.Prestamo;

public interface IPrestamoRepository extends JpaRepository<Prestamo, Long>{

	public Prestamo findFirstByOrderByIdDesc();
	
	public Optional<Prestamo> findByCodigoPrestamo(String codigoPrestamo);
	
	@Query("SELECT p FROM Prestamo p " +
		       "JOIN p.usuario u " +
		       "JOIN p.estudiante e " +
		       "WHERE CONCAT(p.id, ' ', p.fechaPrestamo, ' ', p.fechaDevolucion, ' ', p.codigoPrestamo, ' ', u.username, ' ', e.codigoEstudiante) " +
		       "LIKE %?1%")
	public List<Prestamo> findAll(String palabraClave);
}
