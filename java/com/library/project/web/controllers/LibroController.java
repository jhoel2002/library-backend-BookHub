package com.library.project.web.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.project.web.services.ILibroService;
import com.library.project.web.services.dto.LibroDTO;
import com.library.project.web.services.dto.LibroFullDTO;
import com.library.project.web.services.dto.LibroSaveDTO;
import com.library.project.web.services.dto.LibroUpdateDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/libros/")
public class LibroController {
	
	@Autowired
	private ILibroService libroService;
	
	@GetMapping("getAll")
	public ResponseEntity<Object> getAllLibro(Pageable pageable) {
		LibroFullDTO response = this.libroService.getAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("getAllFilter/{palabraClave}")
	public ResponseEntity<Object> getAllLibro(@PathVariable String palabraClave) {
		List<LibroDTO> response = this.libroService.getFiltrarAutor(palabraClave);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<Object> findByIdLibro(@PathVariable Long id) {
		LibroDTO response = this.libroService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PostMapping("save")
	public ResponseEntity<Object> saveLibro(@RequestBody LibroSaveDTO libro) throws ParseException {
		LibroDTO response = this.libroService.save(libro);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PatchMapping("update")
	public ResponseEntity<Object> updateLibro(@RequestBody LibroUpdateDTO libro) {
		LibroDTO response = this.libroService.update(libro);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteEstudiante(@PathVariable Long id) {
		LibroDTO response = this.libroService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
}
