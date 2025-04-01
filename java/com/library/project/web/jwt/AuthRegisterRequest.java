package com.library.project.web.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegisterRequest {

	private Long id;
	private String username;
	private String password;
	private String nombre;
	private String apellido_paterno;
	private String apellido_materno;
	private String numero_documento;
	private String correo;
	private Long rol;
}
