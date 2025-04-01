package com.library.project.web.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.library.project.web.models.Rol;
import com.library.project.web.models.Usuario;
import com.library.project.web.repository.IUsuarioRepository;


@Component
public class UserLogin implements UserDetailsService{
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo(username);
		System.out.print("UserLogin.java : " + usuario.get().getNombre());

		if(usuario.get() == null) {

        	throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
        }

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		Rol role = usuario.get().getRol();
		authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		
		return new User(usuario.get().getCorreo(), usuario.get().getPassword(), authorities);
	}

}
