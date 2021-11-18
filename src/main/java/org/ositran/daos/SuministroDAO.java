/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Suministro;

public interface SuministroDAO{

	public void saveSuministro(Suministro objSum);

	public List<Suministro> findByExpediente(Integer idExpediente);

	public List<String> findByIdDocumento(Integer idDocumento);
	
	public Suministro getSuministro(int idSuministro);
}
