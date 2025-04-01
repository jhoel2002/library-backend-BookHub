package com.library.project.web.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.project.web.jwt.AuthRegisterRequest;
import com.library.project.web.jwt.AuthRequest;
import com.library.project.web.jwt.AuthResponse;
import com.library.project.web.services.IAccesoService;

import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/v1/auth/")
public class AccesoController {
	

	@Autowired
	private IAccesoService accesoService;
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request)
	{
		try {

			AuthResponse response = accesoService.login(request);

			if(Objects.isNull(response)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body("Credenciales incorrectas");

			}else {
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);			}
	
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(e.getLocalizedMessage());		}
	}
	
	@PostMapping("registro")
	public ResponseEntity<?> registro(@RequestBody AuthRegisterRequest request)
	{
		try {
			AuthResponse response = accesoService.registro(request);

			if(Objects.isNull(response)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body("Correo ya registrado");

			}else {
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);			}
	
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(e.getLocalizedMessage());
		}
	}

	@GetMapping("checkToken")
	public ResponseEntity<?> checkToken(HttpServletRequest request)
	{
		try {

			AuthResponse response = accesoService.checkUsuarioToken(request);

			if(Objects.isNull(response)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body("Token no valido");			
			}
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);			
		
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(e.getLocalizedMessage());		}
	}
}