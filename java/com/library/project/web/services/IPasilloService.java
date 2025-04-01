package com.library.project.web.services;

import java.util.List;

import com.library.project.web.models.Pasillo;

public interface IPasilloService {

	public List<Pasillo> getListPasillo();

	public Pasillo findById(Long id);
	
	public Pasillo save(String pasillo);

	public Pasillo delete(Long id);

	public Pasillo update(Pasillo pasillo);


}
