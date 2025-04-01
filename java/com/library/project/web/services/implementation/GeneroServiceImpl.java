package com.library.project.web.services.implementation;

import java.util.List;

import com.library.project.web.exception.ConflictException;
import com.library.project.web.exception.ResourceNotFoundException;
import com.library.project.web.models.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.project.web.models.Genero;
import com.library.project.web.repository.IGeneroRepository;
import com.library.project.web.services.IGeneroService;

@Service
public class GeneroServiceImpl implements IGeneroService{

	@Autowired
	private IGeneroRepository generoRepository;
	
	@Override
	public List<Genero> getAll(){
		return generoRepository.findAll();
	}
	
	@Override
	public Genero findById(Long id) {
		return generoRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("genero", "id", id));
	}
	
	@Override
	public Genero save(String genero) {
		checkDuplicate(genero);
		Genero generoModel = Genero.builder().nombre(genero).build();
		return generoRepository.save(generoModel);
	}

	private void checkDuplicate(String genero) {
		if (generoRepository.existsByNombre(genero)) {
			throw new ConflictException("genero", "name", genero);
		}
	}

	@Override
	public Genero update(Genero genero) {
		Genero generoModel = generoRepository.findById(genero.getId()).
				orElseThrow(() -> new ResourceNotFoundException("genero", "id", genero.getId()));
		generoModel.setNombre(genero.getNombre());
		return generoRepository.save(generoModel);
	}
	
	@Override
	public Genero delete(Long id) {
		Genero genero = generoRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("genero", "id", id));
		List<Autor> autor = genero.getAutores();
		if(!autor.isEmpty()){
			throw new ConflictException("authors","genero");
		}
        generoRepository.deleteById(id);
		return genero;
    }
}
