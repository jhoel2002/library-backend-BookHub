package com.library.project.web.services.implementation;

import java.util.*;
import java.util.stream.Collectors;

import com.library.project.web.exception.*;
import com.library.project.web.models.Genero;
import com.library.project.web.models.Libro;
import com.library.project.web.repository.IGeneroRepository;
import com.library.project.web.repository.ILibroRepository;
import com.library.project.web.services.dto.AutorDTO;
import com.library.project.web.services.dto.AutorFullDTO;
import com.library.project.web.services.dto.AutorSaveDTO;
import com.library.project.web.services.dto.AutorUpdateDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.library.project.web.models.Autor;
import com.library.project.web.repository.IAutorRepository;
import com.library.project.web.services.IAutorService;

@Service
public class AutorServiceImpl implements IAutorService{

	@Autowired
	private ModelMapper mapper = new ModelMapper();

	@Autowired
	private IAutorRepository autorRepository;

	@Autowired
	private IGeneroRepository generoRepository;
	
	@Autowired
	private ILibroRepository libroRepository;
	
	@Override
	public AutorFullDTO getAll(Pageable pageable){ 
		AutorFullDTO autorFullDTO = new AutorFullDTO();
		Page<Autor> autores = autorRepository.findAll(pageable);
		Page<AutorDTO> autoresDTO = autores.map(autor -> {
            AutorDTO autorDTO = mapper.map(autor, AutorDTO.class);
            autorDTO.setGeneros(autor.getGeneros().stream()
                    .collect(Collectors.toList()));
            return autorDTO;
        });
		autorFullDTO.setAutores(autoresDTO);
		autorFullDTO.setGeneros(generoRepository.findAll());
		return autorFullDTO;
	}
	
	@Override
	public List<AutorDTO> getFiltrarAutor(String palabraClave){ 
		List<Autor> autores = autorRepository.findAll(palabraClave);
		List<AutorDTO> autoresDTO = autores.stream()
	            .map(autor -> {
	                AutorDTO autorDTO = mapper.map(autor, AutorDTO.class);
	                autorDTO.setGeneros(autor.getGeneros().stream()
	                        .collect(Collectors.toList()));
	                return autorDTO;
	            })
	            .collect(Collectors.toList());
		return autoresDTO;
	}

	@Override
	public AutorDTO findById(Long id) {
		Autor autor = autorRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("autor", "id", id));
		AutorDTO autorDTO = mapper.map(autor, AutorDTO.class);
		autorDTO.setGeneros(autor.getGeneros());
		return autorDTO;
	}

	@Override
	public AutorDTO save(AutorSaveDTO autorSaveDTO) {
		checkDuplicate(autorSaveDTO);
		Autor autorModel = mapper.map(autorSaveDTO, Autor.class);
		List<Genero> generosModel = generoRepository.findAllById(autorSaveDTO.getGeneros());
		autorModel.setGeneros(generosModel);
		if(autorModel.getApellidoMaterno() == null) {
            autorModel.setApellidoMaterno("");
        }
		Autor autorSave = autorRepository.save(autorModel);
		AutorDTO autorDTO = mapper.map(autorSave, AutorDTO.class);
		autorDTO.setGeneros(autorSave.getGeneros());
		return autorDTO;
	}

	private void checkDuplicate(AutorSaveDTO autorSaveDTO) {
		if (autorRepository.existsByNombre(autorSaveDTO.getNombre()) &&
				autorRepository.existsByApellidoPaterno(autorSaveDTO.getApellidoPaterno()) &&
				autorRepository.existsByApellidoMaterno(autorSaveDTO.getApellidoMaterno())) {
			throw new ConflictException("author", "name", autorSaveDTO.getNombre() + " " +
					autorSaveDTO.getApellidoPaterno() + " " +
					autorSaveDTO.getApellidoMaterno());
		}
	}

	@Override
	public AutorDTO update(AutorUpdateDTO autorUpdateDTO){
		Autor autor = autorRepository.findById(autorUpdateDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("autor", "id", autorUpdateDTO.getId()));
		mapper.map(autorUpdateDTO, autor);
		if(!autorUpdateDTO.getGeneros().isEmpty()) {
			List<Genero> generosModel = generoRepository.findAllById(autorUpdateDTO.getGeneros());
			autor.setGeneros(generosModel);
		}
		if(autor.getApellidoMaterno() == null) {
			autor.setApellidoMaterno("");
        }
		Autor autorUdpate = autorRepository.save(autor);
		AutorDTO autorDTO = mapper.map(autorUdpate, AutorDTO.class);
		autorDTO.setGeneros(autorUdpate.getGeneros());
		return  autorDTO;
	}
	
	@Override
	public AutorDTO delete(Long id){
		Autor autor = autorRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("autor", "id", id));
		List<Libro> libros = libroRepository.findAll();
		 boolean hasBooks = libros.stream().anyMatch(libro -> libro.getAutor().getId().equals(id));
	        if (hasBooks) {
	            throw new ConflictException("autor","libro");
	        }
		autorRepository.deleteById(id);
		AutorDTO autorDTO = mapper.map(autor, AutorDTO.class);
		autorDTO.setGeneros(autor.getGeneros());
		return autorDTO;
	}
}
