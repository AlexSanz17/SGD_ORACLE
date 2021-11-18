package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Tiporesultado;

public interface TiporesultadoService {

	public Tiporesultado findByIdTiporesultado(Integer idTiporesultado);
	
	public List<Tiporesultado> findAll();
}
