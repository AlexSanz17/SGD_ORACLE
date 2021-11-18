package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Motivo;

public interface MotivoService {

	public Map<Integer,String> getMotivoMap();
   
	public List<Motivo> findAll();
   
	public Object[] findAllAjax();
	
	public List<Motivo> encontrarPorProceso(int idProceso);
	
}
