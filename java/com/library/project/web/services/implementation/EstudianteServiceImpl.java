package com.library.project.web.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import com.library.project.web.exception.ConflictException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.library.project.web.exception.ResourceNotFoundException;
import com.library.project.web.models.Carrera;
import com.library.project.web.models.Estudiante;
import com.library.project.web.models.Prestamo;
import com.library.project.web.repository.ICarreraRepository;
import com.library.project.web.repository.IEstudianteRepository;
import com.library.project.web.services.IEstudianteService;
import com.library.project.web.services.dto.EstudianteDTO;
import com.library.project.web.services.dto.EstudianteFullDTO;
import com.library.project.web.services.dto.EstudianteSaveDTO;
import com.library.project.web.services.dto.EstudianteUpdateDTO;

@Service
public class EstudianteServiceImpl implements IEstudianteService{

	@Autowired
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private IEstudianteRepository estudianteRepository;
	
	@Autowired
	private ICarreraRepository carreraRepository;
	
	@Override
	public EstudianteFullDTO getAll(Pageable pageable){
		EstudianteFullDTO estudianteFullDTO = new EstudianteFullDTO();
		Page<Estudiante> estudiantes = estudianteRepository.findAll(pageable);
		Page<EstudianteDTO> estudiantesDTO = estudiantes.map(estudiante -> {
            EstudianteDTO estudianteDTO = mapper.map(estudiante, EstudianteDTO.class);
            estudianteDTO.setCarrera(estudiante.getCarrera());
            return estudianteDTO;
        });
	    estudianteFullDTO.setEstudiantes(estudiantesDTO);
	    estudianteFullDTO.setCarreras(carreraRepository.findAll());
	    return estudianteFullDTO;
	}
	
	@Override
	public List<EstudianteDTO> getFiltrarEstudiante(String palabraClave){
		List<Estudiante> estudiantes = estudianteRepository.findAll(palabraClave);
	    List<EstudianteDTO> estudiantesDTO = estudiantes.stream()
	            .map(estudiante -> {
	            	EstudianteDTO estudianteDTO = mapper.map(estudiante, EstudianteDTO.class);
	                estudianteDTO.setCarrera(estudiante.getCarrera());
	                return estudianteDTO;
	            })
	            .collect(Collectors.toList());
	    return estudiantesDTO;
	}
	
	@Override
	public EstudianteDTO findById(Long id) {
		Estudiante estudiante =  estudianteRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("estudiante", "id", id));
		EstudianteDTO estudianteDTO = mapper.map(estudiante, EstudianteDTO.class);
		estudianteDTO.setCarrera(estudiante.getCarrera());
		return estudianteDTO;
	}
	
	@Override
	public EstudianteDTO save(EstudianteSaveDTO estudianteSaveDTO) {
		checkDuplicateName(estudianteSaveDTO);
		checkDuplicateNumberDocument(estudianteSaveDTO);
		Estudiante estudianteModel = mapper.map(estudianteSaveDTO, Estudiante.class);
		estudianteModel.setCodigoEstudiante(generarCodigoEstudiante());
		Carrera carreraModel = carreraRepository.findById(estudianteSaveDTO.getCarrera()).
				orElseThrow(() -> new ResourceNotFoundException("carrera", "id",estudianteSaveDTO.getCarrera()));
		estudianteModel.setCarrera(carreraModel);
		Estudiante estudianteSave = estudianteRepository.save(estudianteModel);
		EstudianteDTO estudianteDTO = mapper.map(estudianteSave, EstudianteDTO.class);
		estudianteDTO.setCarrera(estudianteSave.getCarrera());
		return estudianteDTO;
	}
	
	private void checkDuplicateNumberDocument(EstudianteSaveDTO estudianteSaveDTO) {
		if (estudianteRepository.existsByNumeroDocumento(estudianteSaveDTO.getNumeroDocumento())) {
			throw new ConflictException("estudiante", "number document", estudianteSaveDTO.getNumeroDocumento());
		}
	}

	private void checkDuplicateName(EstudianteSaveDTO estudianteSaveDTO) {
		if (estudianteRepository.existsByNombre(estudianteSaveDTO.getNombre()) &&
				estudianteRepository.existsByApellidoPaterno(estudianteSaveDTO.getApellidoPaterno()) &&
				estudianteRepository.existsByApellidoMaterno(estudianteSaveDTO.getApellidoMaterno())) {
			throw new ConflictException("estudiante", "name", estudianteSaveDTO.getNombre() + " " +
					estudianteSaveDTO.getApellidoPaterno() + " " +
					estudianteSaveDTO.getApellidoMaterno());
		}
	}
	
	@Override
	public EstudianteDTO update(EstudianteUpdateDTO estudianteUpdateDTO){
		Estudiante estudiante = estudianteRepository.findByCodigoEstudiante(estudianteUpdateDTO.getCodigoEstudiante()).
				orElseThrow(() -> new ResourceNotFoundException("estudiante", "codigo", estudianteUpdateDTO.getCodigoEstudiante()));
		mapper.map(estudianteUpdateDTO,estudiante);
		if(estudianteUpdateDTO.getCarrera() != null) {
			Carrera carreraModel = carreraRepository.findById(estudianteUpdateDTO.getCarrera()).
					orElseThrow(() -> new ResourceNotFoundException("carrera", "id",estudianteUpdateDTO.getCarrera()));
			estudiante.setCarrera(carreraModel);
		}
		Estudiante estudianteUdpate = estudianteRepository.save(estudiante);
		EstudianteDTO estudianteDTO = mapper.map(estudianteUdpate, EstudianteDTO.class);
		estudianteDTO.setCarrera(estudianteUdpate.getCarrera());
		return estudianteDTO;
	}
	
	@Override
	public EstudianteDTO delete(Long id){
		Estudiante estudiante =  estudianteRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("estudiante", "id", id));
		List<Prestamo> prestamos = estudiante.getPrestamos();
		 boolean hasPrestamos = prestamos.stream().anyMatch(prestamo -> prestamo.getEstudiante().getId().equals(id));
	        if (hasPrestamos) {
	            throw new ConflictException("estudiante","prestamos");
	        }
		estudianteRepository.deleteById(id);
		EstudianteDTO estudianteDTO = mapper.map(estudiante, EstudianteDTO.class);
		return estudianteDTO;
	}
	
	private String generarCodigoEstudiante() {
		String codigo = "ES-";
		String correlacional = "";
		Estudiante ultimoEstudiante = estudianteRepository.findFirstByOrderByIdDesc();
		if (ultimoEstudiante != null) {
			Long numero = ultimoEstudiante.getId() + 1;
			correlacional = String.format("%04d", numero);
		} else {
			int numero = 1;
			correlacional = String.format("%04d", numero);
		}
		return codigo + correlacional;
	}
}
