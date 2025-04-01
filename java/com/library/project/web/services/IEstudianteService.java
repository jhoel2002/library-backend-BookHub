package com.library.project.web.services;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.library.project.web.services.dto.EstudianteDTO;
import com.library.project.web.services.dto.EstudianteFullDTO;
import com.library.project.web.services.dto.EstudianteSaveDTO;
import com.library.project.web.services.dto.EstudianteUpdateDTO;

public interface IEstudianteService {

	public EstudianteFullDTO getAll(Pageable pageable);

	public EstudianteDTO findById(Long id);

	public EstudianteDTO save(EstudianteSaveDTO estudianteSaveDTO);

	public EstudianteDTO update(EstudianteUpdateDTO estudianteUpdateDTO);

	public List<EstudianteDTO> getFiltrarEstudiante(String palabraClave);

	public EstudianteDTO delete(Long id);

}
