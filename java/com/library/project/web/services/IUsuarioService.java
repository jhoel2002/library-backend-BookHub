package com.library.project.web.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.library.project.web.services.dto.UsuarioDTO;
import com.library.project.web.services.dto.UsuarioFullDTO;
import com.library.project.web.services.dto.UsuarioSaveDTO;
import com.library.project.web.services.dto.UsuarioUpdateDTO;

public interface IUsuarioService {

	public UsuarioFullDTO getListUsuarios(Pageable pageable);

	public UsuarioDTO findById(Long id);

	public UsuarioDTO delete(Long id);

	public UsuarioDTO update(UsuarioUpdateDTO usuarioUpdateDTO);

	public UsuarioDTO save(UsuarioSaveDTO usuarioSaveDTO);

	public List<UsuarioDTO> getFiltrarUsuarios(String palabraClave);

}
