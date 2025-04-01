package com.library.project.web.services.dto;

import java.io.Serializable;

import com.library.project.web.models.Rol;

import lombok.Data;

@Data
public class UsuarioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String numeroDocumento;
	private String correo;
	private Rol rol;
	
}
