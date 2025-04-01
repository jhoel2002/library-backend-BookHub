package com.library.project.web.services.implementation;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.project.web.exception.ConflictException;
import com.library.project.web.exception.ResourceNotFoundException;
import com.library.project.web.models.Prestamo;
import com.library.project.web.models.Rol;
import com.library.project.web.models.Usuario;
import com.library.project.web.repository.IRolRepository;
import com.library.project.web.repository.IUsuarioRepository;
import com.library.project.web.services.IUsuarioService;
import com.library.project.web.services.dto.UsuarioDTO;
import com.library.project.web.services.dto.UsuarioFullDTO;
import com.library.project.web.services.dto.UsuarioSaveDTO;
import com.library.project.web.services.dto.UsuarioUpdateDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	private ModelMapper mapper = new ModelMapper();

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IRolRepository rolRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UsuarioFullDTO getListUsuarios(Pageable pageable){	
		UsuarioFullDTO usuarioFullDTO = new UsuarioFullDTO();
		Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
		Page<UsuarioDTO> usuariosDTO = usuarios.map(usuario -> {
	        UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
	        usuarioDTO.setRol(usuario.getRol());
	        return usuarioDTO;
	    });
		usuarioFullDTO.setUsuarios(usuariosDTO);
		usuarioFullDTO.setRoles(rolRepository.findAll());
	    return usuarioFullDTO;
	}
	
	@Override
	public List<UsuarioDTO> getFiltrarUsuarios(String palabraClave){		
		List<Usuario> usuarios = usuarioRepository.findAll(palabraClave);
		List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> {
                    UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
                    usuarioDTO.setRol(usuario.getRol());
                    return usuarioDTO;
                })
                .collect(Collectors.toList());

	    return usuariosDTO;
	}
	
	@Override
	public UsuarioDTO findById(Long id) {
		Usuario usuario =  usuarioRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("usuario", "id", id));
		UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
		usuarioDTO.setRol(usuario.getRol());
		return usuarioDTO;
	}
	
	@Override
	public UsuarioDTO save(UsuarioSaveDTO usuarioSaveDTO) {
		Usuario usuarioModel = mapper.map(usuarioSaveDTO, Usuario.class);
		usuarioModel.setPassword(passwordEncoder.encode(usuarioSaveDTO.getPassword()));
		Rol rolModel = rolRepository.findById(usuarioSaveDTO.getRol()).
				orElseThrow(() -> new ResourceNotFoundException("rol", "id", usuarioSaveDTO.getRol()));
		usuarioModel.setRol(rolModel);
		Usuario usuarioSave = usuarioRepository.save(usuarioModel);
		UsuarioDTO usuarioDTO = mapper.map(usuarioSave, UsuarioDTO.class);
		usuarioDTO.setRol(usuarioSave.getRol());
		return usuarioDTO;
	}
	
	@Override
	public UsuarioDTO update(UsuarioUpdateDTO usuarioUpdateDTO){
		Usuario usuario = usuarioRepository.findById(usuarioUpdateDTO.getId()).
				orElseThrow(() -> new ResourceNotFoundException("usuario", "id", usuarioUpdateDTO.getId()));
		if (usuarioUpdateDTO.getPassword() != null) {
	        if(!validarContrasena(usuarioUpdateDTO.getPassword(), usuario.getPassword())) {
	        	throw new ResourceNotFoundException("usuario", "password", usuarioUpdateDTO.getPassword());
	        	//falta exception correcta
	        }
		}	
		mapper.map(usuarioUpdateDTO, usuario);
		if(usuarioUpdateDTO.getRol() != null) {
			Rol rolModel = rolRepository.findById(usuarioUpdateDTO.getRol()).
					orElseThrow(() -> new ResourceNotFoundException("rol", "id", usuarioUpdateDTO.getRol()));
			usuario.setRol(rolModel);
		}
		if (usuarioUpdateDTO.getPassword_change() != null) {
			String contrasenaHasheada = passwordEncoder.encode(usuarioUpdateDTO.getPassword_change());
	        usuario.setPassword(contrasenaHasheada);
		}
		Usuario usuarioUdpate = usuarioRepository.save(usuario);
		UsuarioDTO usuarioDTO = mapper.map(usuarioUdpate, UsuarioDTO.class);
		usuarioDTO.setRol(usuarioUdpate.getRol());
		return usuarioDTO;
	}
	
	public boolean validarContrasena(String contrasenaEnTextoPlano, String contrasenaAlmacenada) {
	    return passwordEncoder.matches(contrasenaEnTextoPlano, contrasenaAlmacenada);
	}

	@Override
	public UsuarioDTO delete(Long id) {
		Usuario usuario = usuarioRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("usuario", "id", id));
		List<Prestamo> prestamos = usuario.getPrestamos();
		 boolean hasPrestamos = prestamos.stream().anyMatch(prestamo -> prestamo.getUsuario().getId().equals(id));
	        if (hasPrestamos) {
	            throw new ConflictException("usuario","prestamos");
	        }
		UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
		usuarioDTO.setRol(usuario.getRol());
		usuarioRepository.deleteById(id);
		return usuarioDTO;
	}


}
