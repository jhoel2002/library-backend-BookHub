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

import com.library.project.web.services.IUsuarioService;
import com.library.project.web.services.dto.UsuarioDTO;
import com.library.project.web.services.dto.UsuarioFullDTO;
import com.library.project.web.services.dto.UsuarioSaveDTO;
import com.library.project.web.services.dto.UsuarioUpdateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios/")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("getAll")
	public ResponseEntity<Object> getAllUsuarios(Pageable pageable) {
		UsuarioFullDTO response = this.usuarioService.getListUsuarios(pageable);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("getAllFilter/{palabraClave}")
	public ResponseEntity<Object> getAllFilter(@PathVariable String palabraClave) {
		List<UsuarioDTO> response = this.usuarioService.getFiltrarUsuarios(palabraClave);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<Object> findByIdUsuario(@PathVariable Long id) {
		UsuarioDTO response = this.usuarioService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PostMapping("save")
	public ResponseEntity<Object> saveUsuario(@RequestBody @Valid UsuarioSaveDTO usuario) {
		UsuarioDTO response = this.usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PatchMapping("update")
	public ResponseEntity<Object> updateUsuario(@RequestBody UsuarioUpdateDTO usuario) {
		UsuarioDTO response = this.usuarioService.update(usuario);	
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteUsuario(@PathVariable Long id){
		UsuarioDTO response = this.usuarioService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

}
