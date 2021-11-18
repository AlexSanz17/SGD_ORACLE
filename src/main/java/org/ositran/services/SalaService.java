package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Sala;

public interface SalaService {

	public List<Sala> findAll();
	
	public Sala findByIdSala(Integer iIdSala);
}
