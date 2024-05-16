package com.library.project.web.services.implementation;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.project.web.models.Usuario;
import com.library.project.web.repository.IUsuarioRepository;
import com.library.project.web.services.IUsuarioService;
import com.library.project.web.services.dto.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService{
	
	private ModelMapper mapper = new ModelMapper();

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> getListUsuarios(){		
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> buscarPorCorreo(String nombre) {
		return usuarioRepository.findByCorreo(nombre);
	}
	
	@Override
	public UsuarioDTO buscarPorId(Long id) {
		Usuario usuario =  usuarioRepository.findById(id).orElse(null);
		UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
		
		return usuarioDTO;
	}
}
