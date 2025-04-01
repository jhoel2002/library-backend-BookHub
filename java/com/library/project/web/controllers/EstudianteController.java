package com.library.project.web.controllers;


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

import com.library.project.web.services.IEstudianteService;
import com.library.project.web.services.dto.EstudianteDTO;
import com.library.project.web.services.dto.EstudianteFullDTO;
import com.library.project.web.services.dto.EstudianteSaveDTO;
import com.library.project.web.services.dto.EstudianteUpdateDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/estudiantes/")
public class EstudianteController {

	@Autowired
	private IEstudianteService estudianteService;
	
	@GetMapping("getAll")
	public ResponseEntity<Object> getAllEstudiantes(Pageable pageable) {
		EstudianteFullDTO response = this.estudianteService.getAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("getAllFilter/{palabraClave}")
	public ResponseEntity<Object> getAllEstudiantes(@PathVariable String palabraClave) {
		List<EstudianteDTO> response = this.estudianteService.getFiltrarEstudiante(palabraClave);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<Object> findByIdEstudiante(@PathVariable Long id) {
		EstudianteDTO response = this.estudianteService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PostMapping("save")
	public ResponseEntity<Object> saveEstudiante(@RequestBody EstudianteSaveDTO estudiante) {
		EstudianteDTO response = this.estudianteService.save(estudiante);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PatchMapping("update")
	public ResponseEntity<Object> updateEstudiante(@RequestBody EstudianteUpdateDTO estudiante) {
		EstudianteDTO response = this.estudianteService.update(estudiante);	
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteEstudiante(@PathVariable Long id) {
		EstudianteDTO response = this.estudianteService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
}
