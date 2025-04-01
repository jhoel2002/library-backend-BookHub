package com.library.project.web.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.library.project.web.models.Rol;
import com.library.project.web.models.Usuario;
import com.library.project.web.repository.IUsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenUtil jwtUtil;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	public JwtTokenFilter(JwtTokenUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
			@SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
			throws ServletException, IOException {

		if (!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getAccessToken(request);

		if (!jwtUtil.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);
	}


	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}

		return true;
	}

	// Se cambió a public
	public String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
	}

	private void setAuthenticationContext(String token, HttpServletRequest request) {
		Usuario usuario = getUserDetails(token);

		Optional<Usuario> user = usuarioRepository.findByCorreo(usuario.getCorreo());

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		Rol role = user.get().getRol();
		authorities.add(new SimpleGrantedAuthority(role.getNombre()));

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,
				user.get().getPassword(), authorities);

		authentication.setDetails(
				new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	// Se cambió a public
	public Usuario getUserDetails(String token) {
		Usuario userDetails = new Usuario();
		String[] jwtSubject = jwtUtil.getSubject(token).split(",");

		userDetails.setId(Long.parseLong(jwtSubject[0]));
		userDetails.setCorreo(jwtSubject[1]);
		userDetails.setUsername(jwtSubject[2]);

		return userDetails;
	}

}
