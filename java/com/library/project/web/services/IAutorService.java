package com.library.project.web.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.library.project.web.services.dto.AutorDTO;
import com.library.project.web.services.dto.AutorFullDTO;
import com.library.project.web.services.dto.AutorSaveDTO;
import com.library.project.web.services.dto.AutorUpdateDTO;

public interface IAutorService {

	public AutorFullDTO getAll(Pageable pageable);

	public AutorDTO save(AutorSaveDTO autor);

	public AutorDTO findById(Long id);

	public AutorDTO delete(Long id);

	public AutorDTO update(AutorUpdateDTO autor);

	public List<AutorDTO> getFiltrarAutor(String palabraClave);

}
