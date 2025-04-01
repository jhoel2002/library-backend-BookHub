package com.library.project.web.models;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "libro")
public class Libro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "fecha_public", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaPublic;
	
	@Column(name = "stock", nullable = false)
	private Integer stock;
	
	@JoinColumn(name = "pasillo_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Pasillo pasillo;
	
	@JoinColumn(name = "autor_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Autor autor;
	
	@ManyToMany(mappedBy = "libros")
	@JsonIgnore
	private List<Prestamo> prestamos;

}
