package com.library.project.web.services.dto;

import java.util.List;

import lombok.Data;

@Data
public class PrestamoSaveDTO {

	private String codigoEstudiante;
	private List<Long> libros;
}
