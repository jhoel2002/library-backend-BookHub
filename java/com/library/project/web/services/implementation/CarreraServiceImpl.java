package com.library.project.web.services.implementation;

import java.util.List;

import com.library.project.web.exception.ConflictException;
import com.library.project.web.exception.ResourceNotFoundException;
import com.library.project.web.models.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.project.web.models.Carrera;
import com.library.project.web.repository.ICarreraRepository;
import com.library.project.web.services.ICarreraService;

@Service
public class CarreraServiceImpl implements ICarreraService {

	@Autowired
	private ICarreraRepository carreraRepository;

	@Override
	public List<Carrera> getAll(){
		return carreraRepository.findAll();
	}
	
	@Override
	public Carrera findById(Long id) {
        return carreraRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("carrera", "id", id));
    }
	
	@Override
	public Carrera save(String carrera) {
		checkDuplicate(carrera);
		Carrera carreraModel = Carrera.builder().nombre(carrera).build();
		return carreraRepository.save(carreraModel);
	}

	private void checkDuplicate(String carrera) {
		if (carreraRepository.existsByNombre(carrera)) {
			throw new ConflictException("carrera", "name", carrera);
		}
	}

	@Override
	public Carrera update(Carrera carrera) {
		Carrera carreraModel = carreraRepository.findById(carrera.getId()).
				orElseThrow(() -> new ResourceNotFoundException("carrera", "id", carrera.getId()));
		carreraModel.setNombre(carrera.getNombre());
		return carreraRepository.save(carreraModel);
	}
	
	@Override
	public Carrera delete(Long id) {
        Carrera carrera = carreraRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("carrera", "id", id));
		List<Estudiante> estudiantes = carrera.getEstudiantes();
		if(!estudiantes.isEmpty()){
			throw new ConflictException("students","carrera");
		}
		carreraRepository.deleteById(id);
		return carrera;
    }
}
