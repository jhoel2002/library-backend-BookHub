package com.library.project.web.services.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoFullDTO {

	private Page<PrestamoDTO> prestamos;
	private List<PrestamoDTO.LibroDTO> libros;
}
