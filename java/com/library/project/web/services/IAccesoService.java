package com.library.project.web.services;

import com.library.project.web.jwt.AuthRegisterRequest;
import com.library.project.web.jwt.AuthRequest;
import com.library.project.web.jwt.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface IAccesoService {

	public AuthResponse login(AuthRequest request);

	public AuthResponse registro(AuthRegisterRequest request);

	public AuthResponse checkUsuarioToken(HttpServletRequest request);

}
