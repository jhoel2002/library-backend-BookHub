package com.library.project.web.services.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.library.project.web.models.Pasillo;

import lombok.Data;

@Data
public class LibroFullDTO {

	private Page<LibroDTO> libros;
	private List<Pasillo> pasillos;
	private List<LibroDTO.AutorDTO> autores;
	
}
