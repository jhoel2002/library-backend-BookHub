package com.library.project.web.services.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.project.web.models.Pasillo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LibroDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String titulo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaPublic;
	private int stock;
	private Pasillo pasillo;
	private AutorDTO autor;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
    public static class AutorDTO {

        private Long id;
        private String nombreCompleto;
    }
	
}
