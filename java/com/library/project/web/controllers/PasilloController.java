package com.library.project.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.library.project.web.models.Pasillo;
import com.library.project.web.services.IPasilloService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pasillos/")
public class PasilloController {
	
	@Autowired
	private IPasilloService pasilloService;
	
	@GetMapping("getAll")
	public ResponseEntity<Object> getAllPasillos() {
		List<Pasillo> response = this.pasilloService.getListPasillo();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<Object> findByIdPasillo(@PathVariable Long id) {
		Pasillo response = this.pasilloService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PostMapping("save/{nombre}")
	public ResponseEntity<Object> savePasillo(@PathVariable("nombre") String pasillo) {
		Pasillo response = this.pasilloService.save(pasillo);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PatchMapping("update")
	public ResponseEntity<Object> updatePasillo(@RequestBody Pasillo pasillo) {
		Pasillo response = this.pasilloService.update(pasillo);	
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deletePasillo(@PathVariable Long id){
		Pasillo response = this.pasilloService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
}
