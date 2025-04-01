package com.library.project.web.services.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LibroUpdateDTO {

	private Long id;
	private String titulo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaPublic;
	private int stock;
	private Long pasillo;
	private Long autor;
}
