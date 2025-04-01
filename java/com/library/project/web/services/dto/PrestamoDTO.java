package com.library.project.web.services.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PrestamoDTO {
	
	private Long id;
	private String codigoPrestamo;
	private String estado;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fechaPrestamo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fechaDevolucion;
	private int cantidad;
	private UsuarioDTO usuario;
	private EstudianteDTO estudiante;
	private List<LibroDTO> librosPrestados;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
    public static class LibroDTO {

        private Long id;
        private String titulo;
    }
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
    public static class EstudianteDTO {

        private Long id;
        private String codigoEstudiante;
    }
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
    public static class UsuarioDTO {

        private Long id;
        private String username;
    }
}
