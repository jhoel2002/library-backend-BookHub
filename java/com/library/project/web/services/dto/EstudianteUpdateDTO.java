package com.library.project.web.services.dto;

import lombok.Data;

@Data
public class EstudianteUpdateDTO {
	
	private String codigoEstudiante;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correo;
	private Long carrera;
}
