package com.library.project.web.services.dto;

import lombok.Data;

@Data
public class EstudianteSaveDTO {
	
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String numeroDocumento;
	private String correo;
	private Long carrera;
}
