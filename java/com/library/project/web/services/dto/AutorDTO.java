package com.library.project.web.services.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import com.library.project.web.models.Genero;

@Data
public class AutorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private List<Genero> generos;
}
