package com.library.project.web.services.implementation;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.library.project.web.exception.ResourceNotFoundException;
import com.library.project.web.exception.ConflictException;
import com.library.project.web.jwt.JwtTokenFilter;
import com.library.project.web.models.Estudiante;
import com.library.project.web.models.Libro;
import com.library.project.web.models.Prestamo;
import com.library.project.web.models.Usuario;
import com.library.project.web.repository.IEstudianteRepository;
import com.library.project.web.repository.ILibroRepository;
import com.library.project.web.repository.IPrestamoRepository;
import com.library.project.web.repository.IUsuarioRepository;
import com.library.project.web.services.IPrestamoService;
import com.library.project.web.services.dto.PrestamoFullDTO;
import com.library.project.web.services.dto.PrestamoDTO;
import com.library.project.web.services.dto.PrestamoSaveDTO;
import com.library.project.web.utilidades.Fijas;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PrestamoServiceImpl implements IPrestamoService {
	
	ZoneId zonaHoraria = ZoneId.of("America/Lima");
	ZonedDateTime zonedDateTime = ZonedDateTime.now(zonaHoraria);
	Date fechaActual = Date.from(zonedDateTime.toInstant());

	@Autowired
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private IPrestamoRepository prestamoRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IEstudianteRepository estudianteRepository;
	
	@Autowired
	private ILibroRepository libroRepository;
	
	@Autowired
	private JwtTokenFilter jwtFilter;
	
	@Override
	public PrestamoFullDTO getListPrestamos(Pageable pageable) {
	    Page<Prestamo> prestamos = prestamoRepository.findAll(pageable);
	    PrestamoFullDTO prestamosAndLibros = new PrestamoFullDTO();

	    Page<PrestamoDTO> prestamosDTO = prestamos.map(prestamo -> {
	        PrestamoDTO prestamoDTO = mapper.map(prestamo, PrestamoDTO.class);
	        prestamoDTO.setEstado(obtenerEstado(prestamo.getEstado()));
	        prestamoDTO.setUsuario(
	            new PrestamoDTO.UsuarioDTO(
	                prestamo.getUsuario().getId(),
	                prestamo.getUsuario().getUsername()
	            )
	        );
	        prestamoDTO.setEstudiante(
	            new PrestamoDTO.EstudianteDTO(
	                prestamo.getEstudiante().getId(),
	                prestamo.getEstudiante().getCodigoEstudiante()
	            )
	        );
	        prestamoDTO.setLibrosPrestados(prestamo.getLibros().stream()
	            .map(libroPrestado -> {
	                PrestamoDTO.LibroDTO libroDTO = mapper.map(libroPrestado, PrestamoDTO.LibroDTO.class);
	                return libroDTO;
	            })
	            .collect(Collectors.toList())
	        );
	        return prestamoDTO;
	    });
	    prestamosAndLibros.setPrestamos(prestamosDTO);
	    prestamosAndLibros.setLibros(libroRepository.findAll().stream()
                .map(libro -> {
                    PrestamoDTO.LibroDTO libroDTO = mapper.map(libro, PrestamoDTO.LibroDTO.class);
                    return libroDTO;
                	})
                .collect(Collectors.toList())
        );
	    return prestamosAndLibros;
	}
	
	@Override
	public List<PrestamoDTO> getFiltrarPrestamo(String palabraClave) {
	    List<Prestamo> prestamos = prestamoRepository.findAll(palabraClave);

	    List<PrestamoDTO> prestamosDTO = prestamos.stream()
	            .map(prestamo -> {
	                PrestamoDTO prestamoDTO = mapper.map(prestamo, PrestamoDTO.class);
	                prestamoDTO.setEstado(obtenerEstado(prestamo.getEstado()));
	                prestamoDTO.setUsuario(
	                		new PrestamoDTO.UsuarioDTO(
	                				prestamo.getUsuario().getId(),prestamo.getUsuario().getUsername()));
	                prestamoDTO.setEstudiante(
	                		new PrestamoDTO.EstudianteDTO(
	                				prestamo.getEstudiante().getId(),prestamo.getEstudiante().getCodigoEstudiante()));
	                prestamoDTO.setLibrosPrestados(prestamo.getLibros().stream()
	                        .map(libroPrestado -> {
	                            PrestamoDTO.LibroDTO libroDTO = mapper.map(libroPrestado, PrestamoDTO.LibroDTO.class);
	                            return libroDTO;
	                        	})
	                        .collect(Collectors.toList())
	                );
	                return prestamoDTO;
	            })
	            .collect(Collectors.toList());
	    return prestamosDTO;
	}
	
	@Override
	public PrestamoDTO findById(Long id) {
		Prestamo prestamo = prestamoRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("prestamo", "id", id));
		PrestamoDTO prestamoDTO = mapper.map(prestamo, PrestamoDTO.class);
		prestamoDTO.setEstado(obtenerEstado(prestamo.getEstado()));
		prestamoDTO.setUsuario(
        		new PrestamoDTO.UsuarioDTO(
        				prestamo.getUsuario().getId(),prestamo.getUsuario().getUsername()));
        prestamoDTO.setEstudiante(
        		new PrestamoDTO.EstudianteDTO(
        				prestamo.getEstudiante().getId(),prestamo.getEstudiante().getCodigoEstudiante()));
		prestamoDTO.setLibrosPrestados(prestamo.getLibros().stream()
                .map(libroPrestado -> {
                    PrestamoDTO.LibroDTO libroDTO = mapper.map(libroPrestado, PrestamoDTO.LibroDTO.class);
                    return libroDTO;
                	})
                .collect(Collectors.toList())
        );
		return prestamoDTO;
	}

	@Override
	public PrestamoDTO save(PrestamoSaveDTO prestamoSaveDTO, HttpServletRequest request) {
		String token = this.jwtFilter.getAccessToken(request);
		Usuario usuarioToken = this.jwtFilter.getUserDetails(token);
		
		Prestamo prestamo = new Prestamo();
		prestamo.setCodigoPrestamo(generarCodigoPrestamo());
        prestamo.setFechaPrestamo(fechaActual);
        prestamo.setFechaDevolucion(calcularFechaDevolucion(fechaActual));
		Estudiante estudiante = estudianteRepository.findByCodigoEstudiante(prestamoSaveDTO.getCodigoEstudiante()).
		orElseThrow(() -> new ResourceNotFoundException("estudiante", "codigo", prestamoSaveDTO.getCodigoEstudiante()));
		prestamo.setEstudiante(estudiante);
		Usuario usuario = usuarioRepository.findById(usuarioToken.getId()).
				orElseThrow(() -> new ResourceNotFoundException("usuario", "id", usuarioToken.getId()));
		prestamo.setUsuario(usuario);
		List<Libro> libros = libroRepository.findAllById(prestamoSaveDTO.getLibros());
		prestamo.setLibros(disminuirStockLibro(libros));
		prestamo.setCantidad(libros.size());
		prestamo.setEstado(Fijas.ESTADO_PRESTAMO_PRESTADO);
		Prestamo prestamoSave = prestamoRepository.save(prestamo);
		PrestamoDTO prestamoDTO = mapper.map(prestamoSave, PrestamoDTO.class);
		prestamoDTO.setEstado(obtenerEstado(prestamoSave.getEstado()));
		prestamoDTO.setUsuario(
        		new PrestamoDTO.UsuarioDTO(
        				prestamo.getUsuario().getId(),prestamo.getUsuario().getUsername()));
        prestamoDTO.setEstudiante(
        		new PrestamoDTO.EstudianteDTO(
        				prestamo.getEstudiante().getId(),prestamo.getEstudiante().getCodigoEstudiante()));
		prestamoDTO.setLibrosPrestados(prestamo.getLibros().stream()
                .map(libroPrestado -> {
                    PrestamoDTO.LibroDTO libroDTO = mapper.map(libroPrestado, PrestamoDTO.LibroDTO.class);
                    return libroDTO;
                	})
                .collect(Collectors.toList())
        );
		return prestamoDTO;
	}
	
	@Override
	public PrestamoDTO update(String codigoPrestamo){
		Prestamo prestamo = prestamoRepository.findByCodigoPrestamo(codigoPrestamo).
				orElseThrow(() -> new ResourceNotFoundException("prestamo", "codigo", codigoPrestamo));
		List<Libro> libros = prestamo.getLibros();
		aumentarStockLibro(libros);
		prestamo.setEstado(Fijas.ESTADO_PRESTAMO_DEVUELTO);
		Prestamo prestamoUdpate = prestamoRepository.save(prestamo);
		PrestamoDTO prestamoDTO = mapper.map(prestamoUdpate, PrestamoDTO.class);
		prestamoDTO.setEstado(obtenerEstado(prestamoUdpate.getEstado()));
		prestamoDTO.setUsuario(
        		new PrestamoDTO.UsuarioDTO(
        				prestamo.getUsuario().getId(),prestamo.getUsuario().getUsername()));
        prestamoDTO.setEstudiante(
        		new PrestamoDTO.EstudianteDTO(
        				prestamo.getEstudiante().getId(),prestamo.getEstudiante().getCodigoEstudiante()));
		prestamoDTO.setLibrosPrestados(prestamo.getLibros().stream()
                .map(libroPrestado -> {
                    PrestamoDTO.LibroDTO libroDTO = mapper.map(libroPrestado, PrestamoDTO.LibroDTO.class);
                    return libroDTO;
                	})
                .collect(Collectors.toList())
        );
		return  prestamoDTO;
	}
	
	private List<Libro> disminuirStockLibro(List<Libro> libros) {
		List<Libro> librosModel = new ArrayList<>();
		for (Libro libro : libros) {
	        if (libro.getStock() > 0) {
	            libro.setStock(libro.getStock() - 1);
	            librosModel.add(libro);
	        } else {
	            throw new ConflictException("El libro con ID " + libro.getId() + " no tiene stock disponible.");
	        }
	    }
		return librosModel;
	}
	
	private void aumentarStockLibro(List<Libro> libros) {
		for (Libro libro : libros) {
			libro.setStock(libro.getStock() + 1);
	    }
	}
	
	private Date calcularFechaDevolucion(Date fechaPrestamo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaPrestamo);
        calendar.add(Calendar.DAY_OF_YEAR, Fijas.DIAS_PRESTAMO);
        return calendar.getTime();
    }
	
	private String generarCodigoPrestamo() {
		String codigo = "CS-";
		String correlacional = "";
		Prestamo ultimoPrestamo = prestamoRepository.findFirstByOrderByIdDesc();
		if (ultimoPrestamo != null) {
			Long numero = ultimoPrestamo.getId() + 1;
			correlacional = String.format("%04d", numero);
		} else {
			int numero = 1;
			correlacional = String.format("%04d", numero);
		}
		return codigo + correlacional;
	}
	
	private String obtenerEstado(int codigoEstado) {
        switch (codigoEstado) {
            case 1:
                return "Prestado";
            case 2:
                return "Vencido";
            case 3:
                return "Devuelto";
            default:
                throw new IllegalArgumentException("Código de estado no válido: " + codigoEstado);
        }
    }
	
}
