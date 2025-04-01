package com.library.project.web.services;

import com.library.project.web.services.dto.PrestamoFullDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.library.project.web.services.dto.PrestamoDTO;
import com.library.project.web.services.dto.PrestamoSaveDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface IPrestamoService {

	public PrestamoDTO findById(Long id);

	public PrestamoDTO update(String codigoPrestamo);

	public PrestamoDTO save(PrestamoSaveDTO prestamoSaveDTO, HttpServletRequest request);

	public List<PrestamoDTO> getFiltrarPrestamo(String palabraClave);

	public PrestamoFullDTO getListPrestamos(Pageable pageable);


}
