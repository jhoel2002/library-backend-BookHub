package com.library.project.web.services.implementation;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.project.web.jwt.AuthRegisterRequest;
import com.library.project.web.jwt.AuthRequest;
import com.library.project.web.jwt.AuthResponse;
import com.library.project.web.jwt.JwtTokenFilter;
import com.library.project.web.jwt.JwtTokenUtil;
import com.library.project.web.models.Rol;
import com.library.project.web.models.Usuario;
import com.library.project.web.repository.IRolRepository;
import com.library.project.web.repository.IUsuarioRepository;
import com.library.project.web.services.IAccesoService;
import com.library.project.web.services.dto.UsuarioDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccesoServiceImpl implements IAccesoService{
	
	@Autowired
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private JwtTokenFilter jwtFilter;

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IRolRepository rolRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public AuthResponse login(AuthRequest request) {
		AuthResponse authResponse = null;
		Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(request.getCorreo());
		if(usuarioOptional.isPresent()) {
			Usuario user = usuarioOptional.get();
			Authentication authentication = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword()));
			System.out.println("AccesoController: " + authentication);
			
			String accessToken = jwtUtil.generarToken(user);
			System.out.println("AccesoController: " + accessToken);

			UsuarioDTO usuarioDTO = this.mapper.map(user, UsuarioDTO.class);
			usuarioDTO.setRol(user.getRol());
			return AuthResponse.builder().accessToken(accessToken)
					.usuario(usuarioDTO)
					.build();
		}
		return authResponse;
	}
	
	@Override
	public AuthResponse registro(AuthRegisterRequest request) {
		AuthResponse authResponse = null;
		Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(request.getCorreo());
		if(usuarioOptional.isEmpty()) {
			Optional<Rol> rol = rolRepository.findById(request.getRol());
			Usuario user = Usuario.builder().
					nombre(request.getNombre()).
					apellidoMaterno(request.getApellido_materno()).
					apellidoPaterno(request.getApellido_paterno()).
					correo(request.getCorreo()).
					username(request.getUsername()).
					password(passwordEncoder.encode(request.getPassword())).
					numeroDocumento(request.getNumero_documento()).
					rol(rol.get()).
					build();
			
			usuarioRepository.save(user);
			
			String accessToken = jwtUtil.generarToken(user);
			System.out.println("AccesoController: " + accessToken);
			
			UsuarioDTO usuarioDTO = this.mapper.map(user, UsuarioDTO.class);
			
			return AuthResponse.builder().accessToken(accessToken)
					.usuario(usuarioDTO)
					.build();
		}
		return authResponse;
		
	}
	
	public AuthResponse checkUsuarioToken(HttpServletRequest request) {
			
			String token = this.jwtFilter.getAccessToken(request);
			
			if( token == null || !this.jwtUtil.validateAccessToken(token) ){
				return null;
			}
			
			Usuario usuario = this.jwtFilter.getUserDetails(token);
			String accessToken = jwtUtil.generarToken(usuario);

			UsuarioDTO usuarioDTO = this.mapper.map(usuario, UsuarioDTO.class);
			
			return AuthResponse.builder().accessToken(accessToken)
					.usuario(usuarioDTO)
					.build();
	}
}
