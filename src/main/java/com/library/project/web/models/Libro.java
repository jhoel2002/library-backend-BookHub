package com.library.project.web.models;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	//@Column(name = "codigo", nullable = false)
	//private String codigo;
	
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "fecha_public", nullable = false)
	private Date fechaPublic;
	
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
	@JoinColumn(name = "pasillo_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Pasillo pasillo;
	
	@JoinColumn(name = "genero_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Genero genero;
	
	@ManyToMany(mappedBy = "libros")
	private List<Prestamo> prestamos;
}
