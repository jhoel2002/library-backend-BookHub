package com.library.project.web.services;

import java.util.List;

import com.library.project.web.models.Genero;

public interface IGeneroService {

	public List<Genero> getAll();

	public Genero findById(Long id);

	public Genero save(String genero);

	public Genero delete(Long id);

	public Genero update(Genero genero);

}
