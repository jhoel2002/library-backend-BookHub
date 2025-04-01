package com.library.project.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.project.web.models.Estudiante;

public interface IEstudianteRepository  extends JpaRepository<Estudiante, Long>{

	public boolean existsByNombre(String nombre);

    public boolean existsByApellidoPaterno(String apellidoPaterno);

    public boolean existsByApellidoMaterno(String apellidoMaterno);

    public boolean existsByNumeroDocumento(String numeroDocumento);
    
    public Estudiante findFirstByOrderByIdDesc();
    
    public Optional<Estudiante> findByCodigoEstudiante(String codigoEstudiante);
    
    @Query("SELECT e FROM Estudiante e " +
		       "JOIN e.carrera c " +
		       "WHERE CONCAT(e.id, ' ', e.nombre, ' ', e.apellidoPaterno, ' ', e.apellidoMaterno, ' ', e.numeroDocumento, ' ', e.correo, ' ', e.codigoEstudiante, ' ', c.nombre) " +
		       "LIKE %?1%")
	public List<Estudiante> findAll(String palabraClave);
}
