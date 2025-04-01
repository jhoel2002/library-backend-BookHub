package com.library.project.web.services;

import java.util.List;

import com.library.project.web.models.Carrera;

public interface ICarreraService {

	public List<Carrera> getAll();

	public Carrera save(String carrera);

	public Carrera findById(Long id);

	public Carrera delete(Long id);

	public Carrera update(Carrera carrera);

}
