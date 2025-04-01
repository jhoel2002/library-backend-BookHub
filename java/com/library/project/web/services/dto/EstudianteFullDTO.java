package com.library.project.web.services.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.library.project.web.models.Carrera;

import lombok.Data;

@Data
public class EstudianteFullDTO {

	private Page<EstudianteDTO> estudiantes;
	private List<Carrera> carreras;
	
}

