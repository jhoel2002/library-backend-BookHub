package com.library.project.web.services.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.library.project.web.models.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFullDTO {
	private Page<UsuarioDTO> usuarios;
	private List<Rol> roles;
}
