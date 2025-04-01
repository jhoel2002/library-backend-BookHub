package com.library.project.web.services.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.library.project.web.models.Genero;

import lombok.Data;

@Data
public class AutorFullDTO {

	private Page<AutorDTO> autores;
	private List<Genero> generos;
}
