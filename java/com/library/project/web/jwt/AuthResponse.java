package com.library.project.web.jwt;

import com.library.project.web.services.dto.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

	private UsuarioDTO usuario;
	private String accessToken;

}
