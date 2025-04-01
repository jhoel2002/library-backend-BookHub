package com.library.project.web.services.implementation;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.library.project.web.exception.ConflictException;
import com.library.project.web.exception.ResourceNotFoundException;
import com.library.project.web.models.Autor;
import com.library.project.web.models.Libro;
import com.library.project.web.models.Pasillo;
import com.library.project.web.models.Prestamo;
import com.library.project.web.repository.IAutorRepository;
import com.library.project.web.repository.ILibroRepository;
import com.library.project.web.repository.IPasilloRepository;
import com.library.project.web.services.ILibroService;
import com.library.project.web.services.dto.LibroDTO;
import com.library.project.web.services.dto.LibroFullDTO;
import com.library.project.web.services.dto.LibroSaveDTO;
import com.library.project.web.services.dto.LibroUpdateDTO;


@Service
public class LibroServiceImpl implements ILibroService{
	
	@Autowired
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private ILibroRepository libroRepository;
	
	@Autowired
	private IAutorRepository autorRepository;
	
	@Autowired
	private IPasilloRepository pasilloRepository;
	
	@Override
	public LibroFullDTO getAll(Pageable pageable){
		LibroFullDTO libroFullDTO = new LibroFullDTO();
		Page<Libro> libros = libroRepository.findAll(pageable);
		Page<LibroDTO> librosDTO = libros.map(libro -> {
	        LibroDTO libroDTO = mapper.map(libro, LibroDTO.class);
	        libroDTO.setPasillo(libro.getPasillo());
	        libroDTO.setAutor(
	        		new LibroDTO.AutorDTO(
	                        libro.getId(),
	                        libro.getAutor().getNombre() + " " +
	                        libro.getAutor().getApellidoPaterno() + " " +
	                        libro.getAutor().getApellidoMaterno()
	                ));
	        return libroDTO;
	    });
		libroFullDTO.setLibros(librosDTO);
		libroFullDTO.setPasillos(pasilloRepository.findAll());
		libroFullDTO.setAutores(autorRepository.findAll().stream()
                .map(autor -> {
                	LibroDTO.AutorDTO autorDTO = mapper.map(autor, LibroDTO.AutorDTO.class);
                	autorDTO.setNombreCompleto(
                			autor.getNombre()+" "+
                			autor.getApellidoPaterno()+" "+
                			autor.getApellidoMaterno()
    				);
                    return autorDTO;
                	})
                .collect(Collectors.toList())
        );
		return libroFullDTO;
	}
	
	@Override
	public List<LibroDTO> getFiltrarAutor(String palabraClave) {
	    List<Libro> libros = libroRepository.findAll(palabraClave);

	    List<LibroDTO> librosDTO = libros.stream()
	            .map(libro -> {
	                LibroDTO libroDTO = mapper.map(libro, LibroDTO.class);
	                libroDTO.setPasillo(libro.getPasillo());
	    	        libroDTO.setAutor(
	    	        		new LibroDTO.AutorDTO(
	    	                        libro.getId(),
	    	                        libro.getAutor().getNombre() + " " +
	    	                        libro.getAutor().getApellidoPaterno() + " " +
	    	                        libro.getAutor().getApellidoMaterno()
	    	                ));
	                return libroDTO;
	            })
	            .collect(Collectors.toList());
	    return librosDTO;
	}
	
	@Override
	public LibroDTO findById(Long id) {
		Libro libro =  libroRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("libro", "id", id));
		LibroDTO libroDTO = mapper.map(libro, LibroDTO.class);
		libroDTO.setPasillo(libro.getPasillo());
        libroDTO.setAutor(
        		new LibroDTO.AutorDTO(
                        libro.getId(),
                        libro.getAutor().getNombre() + " " +
                        libro.getAutor().getApellidoPaterno() + " " +
                        libro.getAutor().getApellidoMaterno()
                ));
		return libroDTO;
	}
	
	@Override
	public LibroDTO save(LibroSaveDTO libroSaveDTO) throws ParseException {
		Libro libro =  mapper.map(libroSaveDTO, Libro.class);
		Autor autorModel = autorRepository.findById(libroSaveDTO.getAutor()).
				orElseThrow(() -> new ResourceNotFoundException("autor", "id", libroSaveDTO.getAutor()));
		libro.setAutor(autorModel);
		Pasillo pasilloModel = pasilloRepository.findById(libroSaveDTO.getPasillo()).
				orElseThrow(() -> new ResourceNotFoundException("pasillo", "id", libroSaveDTO.getPasillo()));
		libro.setPasillo(pasilloModel);
		Libro LibroSave = libroRepository.save(libro);
		LibroDTO libroDTO = mapper.map(LibroSave, LibroDTO.class);
		libroDTO.setPasillo(libro.getPasillo());
        libroDTO.setAutor(
        		new LibroDTO.AutorDTO(
                        libro.getId(),
                        libro.getAutor().getNombre() + " " +
                        libro.getAutor().getApellidoPaterno() + " " +
                        libro.getAutor().getApellidoMaterno()
                ));
		return libroDTO;
	}
	
	@Override
	public LibroDTO update(LibroUpdateDTO libroUpdateDTO){
		Libro libro = libroRepository.findById(libroUpdateDTO.getId()).
				orElseThrow(() -> new ResourceNotFoundException("libro", "id", libroUpdateDTO.getId()));
		mapper.map(libroUpdateDTO, libro);
		if(libroUpdateDTO.getAutor() != null) {
			Autor autorModel = autorRepository.findById(libroUpdateDTO.getAutor()).
					orElseThrow(() -> new ResourceNotFoundException("autor", "id", libroUpdateDTO.getAutor()));;
			libro.setAutor(autorModel);
		}
		if(libroUpdateDTO.getPasillo() != null) {
			Pasillo pasilloModel = pasilloRepository.findById(libroUpdateDTO.getPasillo()).
					orElseThrow(() -> new ResourceNotFoundException("pasillo", "id", libroUpdateDTO.getPasillo()));
			libro.setPasillo(pasilloModel);
		}
		Libro libroUdpate = libroRepository.save(libro);
		LibroDTO libroDTO = mapper.map(libroUdpate, LibroDTO.class);
		libroDTO.setPasillo(libro.getPasillo());
        libroDTO.setAutor(
        		new LibroDTO.AutorDTO(
                        libro.getId(),
                        libro.getAutor().getNombre() + " " +
                        libro.getAutor().getApellidoPaterno() + " " +
                        libro.getAutor().getApellidoMaterno()
                ));
		return libroDTO;
	}
	
	@Override
	public LibroDTO delete(Long id){
		Libro libro =  libroRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("libro", "id", id));
		List<Prestamo> prestamos = libro.getPrestamos();
		boolean hasPrestamos = prestamos.stream()
                .flatMap(prestamo -> prestamo.getLibros().stream()) // Obtener la lista de libros por préstamo
                .anyMatch(prestamoLibro -> prestamoLibro.getId().equals(id)); // Verificar si algún libro tiene el mismo ID
		if (hasPrestamos) {
            throw new ConflictException("libros","prestamos");
        }
	    libroRepository.deleteById(id);
		LibroDTO libroDTO = mapper.map(libro, LibroDTO.class);
		return libroDTO;
	}
}
