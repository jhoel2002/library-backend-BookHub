package com.library.project.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.project.web.models.Carrera;
import com.library.project.web.services.ICarreraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/carreras/")
public class CarreraController {

	@Autowired
	private ICarreraService carreraService;
	
	@GetMapping("getAll")
	public ResponseEntity<Object> getAllCarrera() {
		List<Carrera> response = this.carreraService.getAll();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<Object> findByIdCarrera(@PathVariable Long id) {
		Carrera response = this.carreraService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PostMapping("save/{nombre}")
	public ResponseEntity<Object> saveCarrera(@PathVariable("nombre") String carrera) {
		Carrera response = this.carreraService.save(carrera);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PatchMapping("update")
	public ResponseEntity<Object> updateCarrera(@RequestBody Carrera carrera) {
		Carrera response = this.carreraService.update(carrera);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteCarrera(@PathVariable Long id){
		Carrera response = this.carreraService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
}
