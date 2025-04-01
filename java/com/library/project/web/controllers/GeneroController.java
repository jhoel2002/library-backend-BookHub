package com.library.project.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.project.web.models.Genero;
import com.library.project.web.services.IGeneroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/generos/")
public class GeneroController {
	
	@Autowired
	private IGeneroService generoService;
	
	@GetMapping("getAll")
	public ResponseEntity<Object> getAllGenero() {
		List<Genero> response = this.generoService.getAll();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<Object> findByIdGenero(@PathVariable Long id) {
		Genero response = this.generoService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PostMapping("save/{nombre}")
	public ResponseEntity<Object> saveGenero(@PathVariable("nombre") String genero) {
		Genero response = this.generoService.save(genero);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PatchMapping("update")
	public ResponseEntity<Object> updateGenero(@RequestBody Genero genero) {
		Genero response = this.generoService.update(genero);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
}
