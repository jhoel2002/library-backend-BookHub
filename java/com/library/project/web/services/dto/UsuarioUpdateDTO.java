package com.library.project.web.services.dto;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {

	private Long id;
	private String username;
	private String password;
	private String password_change;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String numeroDocumento;
	private String correo;
	private Long rol;
}
