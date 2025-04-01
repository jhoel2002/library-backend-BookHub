package com.library.project.web.services;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.library.project.web.services.dto.LibroDTO;
import com.library.project.web.services.dto.LibroFullDTO;
import com.library.project.web.services.dto.LibroSaveDTO;
import com.library.project.web.services.dto.LibroUpdateDTO;

public interface ILibroService {

	public LibroFullDTO getAll(Pageable pageable);

	public LibroDTO save(LibroSaveDTO libro) throws ParseException;

	public LibroDTO findById(Long id);

	public LibroDTO update(LibroUpdateDTO libroUpdateDTO);

	public List<LibroDTO> getFiltrarAutor(String palabraClave);

	public LibroDTO delete(Long id);

}
