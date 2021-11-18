/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Motivo;

public interface MotivoDAO{
	
	public List<Motivo> findAll();

	public Motivo saveMotivo(Motivo motivo);

	public Motivo findByIdmotivo(Integer idmotivo);
	
	public List<Motivo> encontrarPorProceso(int idProceso);
	
}
