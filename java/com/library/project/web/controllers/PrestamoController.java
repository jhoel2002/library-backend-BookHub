package com.library.project.web.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.project.web.services.IPrestamoService;
import com.library.project.web.services.dto.PrestamoFullDTO;
import com.library.project.web.services.dto.PrestamoDTO;
import com.library.project.web.services.dto.PrestamoSaveDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/prestamos/")
public class PrestamoController {
	
	@Autowired
	private IPrestamoService prestamoService;
	
	@GetMapping("getAll")
	public ResponseEntity<Object> getAll(Pageable pageable) {
		PrestamoFullDTO response = this.prestamoService.getListPrestamos(pageable);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("getAllFilter/{palabraClave}")
	public ResponseEntity<Object> getAllFilter(@PathVariable String palabraClave) {
		List<PrestamoDTO> response = this.prestamoService.getFiltrarPrestamo(palabraClave);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<Object> findByIdPrestamo(@PathVariable Long id) {
		PrestamoDTO response = this.prestamoService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PostMapping("save")
	public ResponseEntity<Object> savePrestamo(@RequestBody @Valid PrestamoSaveDTO prestamo, HttpServletRequest request) {
		PrestamoDTO response = this.prestamoService.save(prestamo, request);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PatchMapping("update/{codigoEstado}")
	public ResponseEntity<Object> updatePrestamo(@PathVariable String codigoEstado) {
		PrestamoDTO response = this.prestamoService.update(codigoEstado);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

}
