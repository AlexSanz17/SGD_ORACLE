package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Etapa;

public interface EtapaService {
	
	public List<Etapa> findAll();
	
	public Etapa findByIdetapa(int Idetapa);

	public List<Etapa> getEtapaList(String descripcion, Integer duracion,String estado, String codigo);

	public void saveObject(Etapa etapa);
	
	public List<Etapa> findEtapabyProceso(Integer idProceso);
}
