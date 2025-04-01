package com.library.project.web.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="estudiante")
public class Estudiante implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "apellido_paterno", nullable = false)
	private String apellidoPaterno;
	
	@Column(name = "apellido_materno", nullable = false)
	private String apellidoMaterno;
	
	@Column(name = "numero_documento", nullable = false)
	private String numeroDocumento;
	
	@Column(name = "correo", nullable = false)
	private String correo;
	
	@Column(name = "cod_estudiante", nullable = false)
	private String codigoEstudiante;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "carrera_id", referencedColumnName = "id")
	private Carrera carrera;
	
	@OneToMany(mappedBy = "estudiante")
	@JsonIgnore
	private List<Prestamo> prestamos;

}
